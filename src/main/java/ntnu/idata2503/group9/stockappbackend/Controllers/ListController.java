package ntnu.idata2503.group9.stockappbackend.Controllers;

import ntnu.idata2503.group9.stockappbackend.Models.Stock;
import ntnu.idata2503.group9.stockappbackend.Models.User;
import ntnu.idata2503.group9.stockappbackend.Services.ListService;
import ntnu.idata2503.group9.stockappbackend.Services.StockService;
import ntnu.idata2503.group9.stockappbackend.Services.UserService;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.logging.Logger;

/**
 * Rest controller that controls the endpoint for the list.
 *
 * @author Gruppe..
 * @version 1.0
 */
@Controller
@RequestMapping("/api/list")
public class ListController {

    @Autowired
    private ListService listService;

    @Autowired
    private UserService userService;

    @Autowired
    private StockService stockService;

    private static final String JSONEEXCEPTIONMESSAGE = "The Field(s) in the request is missing or is null";
    private static final String SEVERE = "An error occurred: ";
    private static final Logger LOGGER = Logger.getLogger(ListController.class.getName());

    /**
     * Endpoint that returns all users.
     * @return Returns all list and HTTP status OK or HTTP status NOT_FOUND is list was not found
     */
    @GetMapping("")
    public ResponseEntity<List<ntnu.idata2503.group9.stockappbackend.Models.List>> getLists() {
        Iterable<ntnu.idata2503.group9.stockappbackend.Models.List> lists = this.listService.getAll();
        if(!lists.iterator().hasNext()) {
            return new ResponseEntity("Didn't find lists", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok((List<ntnu.idata2503.group9.stockappbackend.Models.List>) lists);
    }

    /**
     * Endpoint that returns a list based on the list id
     * @param id the id of the list that you want to return
     * @return the list and HTTP status OK or http status NOT_FOUNd if list was not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<ntnu.idata2503.group9.stockappbackend.Models.List> getListFromId(@PathVariable long id) {
        ntnu.idata2503.group9.stockappbackend.Models.List list = this.listService.findById(id);
        if(list == null) {
            return new ResponseEntity("Didn't find list", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(list);
    }

    @GetMapping("/listsbyuid/{uid}")
    public ResponseEntity<List<ntnu.idata2503.group9.stockappbackend.Models.List>> getListFormUid(@PathVariable long uid) {
        User user = this.userService.findById(uid);
        Iterable<ntnu.idata2503.group9.stockappbackend.Models.List> lists = this.listService.findAllByUser(user);
        return ResponseEntity.ok((List<ntnu.idata2503.group9.stockappbackend.Models.List>) lists);
    }

    /**
     * Endpoint that creates a new list.
     * @param list the body of list that you want to create.
     * @return HTTP status CREATED if created, if not the INTERNAL_SERVER_ERROR.
     * @exception JSONException  if an error occurs while creating the list.
     */
    @PostMapping("")
    public ResponseEntity<ntnu.idata2503.group9.stockappbackend.Models.List> createList(@RequestBody ntnu.idata2503.group9.stockappbackend.Models.List list) {
        try {
            if(!this.listService.add(list)) {
                return new ResponseEntity("List was not added", HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return new ResponseEntity("List was added", HttpStatus.CREATED);
        }
        catch (JSONException e) {
            LOGGER.severe(SEVERE + e.getMessage());
            return new ResponseEntity(JSONEEXCEPTIONMESSAGE, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Endpoint that update a list.
     * @param id the id of the list that you want to update.
     * @param list the new list that you want the old user to be updated to.
     * @return HTTP status OK if updated, if not INTERNAL_SERVER_ERROR.
     * @exception JSONException  if an error occurs while updating the list.
     */
    @PutMapping("")
    public ResponseEntity updateList(@PathVariable long id, @RequestBody ntnu.idata2503.group9.stockappbackend.Models.List list) {
        try {
            ntnu.idata2503.group9.stockappbackend.Models.List oldList = this.listService.findById(id);
            if(oldList == null) {
                return new ResponseEntity("Didn't find list", HttpStatus.NOT_FOUND);
            }
            this.listService.update(id,list);
            if(this.listService.findById(id) == null) {
                return new ResponseEntity("List didn't update", HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return new ResponseEntity("List was updated", HttpStatus.OK);
        }
        catch (JSONException e) {
            LOGGER.severe(SEVERE + e.getMessage());
            return new ResponseEntity(JSONEEXCEPTIONMESSAGE, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Endpoint that deletes a list.
     * @param id the id of the list that you want to delete
     * @return HTTP status OK if deleted, if not INTERNAL_SERVER_ERROR.
     * @exception JSONException  if an error occurs while deleting the list.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity deleteUser(@PathVariable long id) {
        try {
            if(!this.listService.delete(id)) {
                return new ResponseEntity("List was not removed", HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return new ResponseEntity("List was removed", HttpStatus.OK);
        }
        catch (JSONException e) {
            LOGGER.severe(SEVERE + e.getMessage());
            return new ResponseEntity(JSONEEXCEPTIONMESSAGE, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/addStock/{lid}")
    public ResponseEntity addStockToList(@PathVariable long lid, @RequestBody Stock stock) {
        Stock stock1 = stockService.getStockById(stock.getId());
        this.listService.addStockToList(lid, stock1);
        return new ResponseEntity(HttpStatus.OK);
    }
}
