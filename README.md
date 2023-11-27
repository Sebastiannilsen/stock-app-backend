# stock-app-backend

## Introduction
This is the backend for StockApp, created by students at NTNU in the course IDATA2503 - Mobile Applications. The backend consists of a REST API built in Spring Boot connected to a SQL database. The main purpose of the application is to simulate stock prices at the Oslo stock exchange and provide this information through API endpoints.

## Functionality
- Logging.
- Creating lists of stocks for monitoring.
- Purchasing stocks.
- Reading the current stock prices.
- Portfolio history tracking.
- Stock price history tracking.
- Stock purchase tracking.
- User management and authentication.

**Note** that the stock purchases and prices are only simulated, and portfolio, stock price history, stock purchase details, and user authentication are available through additional endpoints.

## Endpoints
The REST API supports the following endpoints:

1. **("/api/list")**
   - GET: Gets the lists.
   - POST: Creates a list.
   - GET("/{id}"): Gets a specific list by ID.
   - GET("/listsbyuid/{uid}"): Gets all lists for a specified user by userID.
   - DELETE("/{id}"): Deletes a list by listID.
   - POST("/addStock/{lid}"): Adds a stock to a specified list by listID.

2. **("/api/portfolio")**
   - GET: Returns all portfolios.
   - GET("/{id}"): Returns a portfolio based on the portfolio ID.
   - GET("/stocks/{uid}"): Returns a list of unique stocks from the portfolio.
   - POST: Creates a new portfolio.
   - PUT("/{id}"): Updates a portfolio.
   - DELETE("/{id}"): Deletes a portfolio.

3. **("/api/portfoliohistory")**
   - GET("/portfolios/{pid}"): Returns portfolio history by portfolio ID.

4. **("/api/stocks")**
   - GET: Returns all stocks.
   - GET("/{id}"): Returns a stock based on the stock ID.
   - PUT("/{id}"): Updates a stock.
   - GET("/lists/{lid}/stocks"): Returns all stocks for a specified list by listID.

5. **("/api/stockhistory")**
   - GET("/stocks/{id}"): Returns stock price history by stock ID.

6. **("/api/stockpurchase")**
   - GET: Returns all stock purchases.
   - GET("/{id}"): Returns a stock purchase based on the stock purchase ID.
   - POST: Creates a new stock purchase.
   - PUT("/{id}"): Updates a stock purchase.
   - DELETE("/{id}"): Deletes a stock purchase.
   - GET("/{id}/stockpurchase"): Returns a stock purchase by stock ID.

7. **("/api/user")**
   - GET: Returns all users.
   - GET("/{id}"): Returns a user based on the user ID.
   - GET("/{email}"): Returns a user based on the user email.
   - POST: Creates a new user.
   - PUT("/{id}"): Updates a user.
   - DELETE("/{id}"): Deletes a user.
   - POST("/authenticate"): Authenticates a user and returns a JWT token.
   - GET("/sessionuser"): Returns the current session user.


# Running the Project

Before running the project, ensure that you have set up a PostgreSQL database and update the database configuration in both the `application.properties` and `env.properties` files.

**application.properties:**
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/your_database_name
spring.datasource.username=your_database_username
spring.datasource.password=your_database_password
```

After configuring the database, navigate to the root directory of the project and run the following command in the terminal:
```shell
mvn spring-boot:run
```
This command will build and run the Spring Boot application. Make sure Maven is installed on your system before executing the command.

Once the application is running, you can access it in your web browser at the specified address. If any issues arise during the setup, double-check your database configuration and ensure that all dependencies are installed.