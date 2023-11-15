# stock-app-backend

## Introcution
This is the backend for StockApp, created by students at NTNU in the course IDATA2503 - Mobile Applications. 
The backend consist of a REST api buildt in springboot connected to a SQL database. The main purpose of the application is to simulate stock prices at the Oslo stock exchange, and provide this info througt 
API endpoints. 

## Functionality
Loging.
Creating lists of stocks for monitoring.
Purchasing stocks. 
Reading the current stock prices.

note that the stock purchases and prices are only simulated and not real data. 

## Endpoints
The REST api supports the following endpoints

1. **("/api/list")**
  - GET : gets the lists
  - POST : Creates a list 
  - GET ("/{id}") :  gets a specific list by the id
  - GET ("/listsbyuid/{uid}") : gets all list for a specified user by userId
  - DELETE ("/{id}") : deletes a list by listId
  - POST ("/addStock/{lid}") : adds a stock to a specified list by listId
  
2. **("/api/portfolio")**
  - 
