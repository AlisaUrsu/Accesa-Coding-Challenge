# Accesa Coding Challenge
My solution for the coding challenge for Accesa Java Internship 2025
## Overview
This projects implements the backend of a price comparison application. The main functionalities include comparing product prices across stores, highligthing the cheapest option based on unit price, optimizing shopping baskets and generating shopping lists specific to a store, displaying currently active discounts, tracking the evolution of prices of products found in each store, and allowing users to select preferred stores.
## Project Structure
The application follows a layered architecture that separates concerns to ensure maintainability and clarity.

- **aop** – store filtering via custom annotation
- **config** – security and application configuration
- **controller** – API endpoints
- **converter** – entity to DTO and vice versa mapping
- **dto** – data transfer objects
- **exceptions** – custom exception handler
- **model** – domain entities mapped by JPA to database tables
- **repository** – data access layer
- **service** – business logic layer
- **util** – Result and AlertScheduler class

### Database Structure
The database schema was designed with modularity and extensibility in mind to support a scalable backend, which can simplify future expansion, even if my current focus was on the main tasks.

![PriceComp](https://github.com/user-attachments/assets/b1d3cd04-6cca-4ac2-8ccf-88a872328c9e)

The `CsvImportService` handles importing product and discount data from CSV files and populates the database accordingly.
- **Product**: keeps general information about a specific product, like id, name, package quantity and unit. The rest represent foreign keys to other tables, like Brand, Category and Store.
- **Unit**: is used for computing the price per unit for an item by knowing the conversion factor for a certain unit. (e.g 1g = 0.001kg)
- **StoreProduct**: represents a m:n relationship between stores and products, because products can be found at multiple different stores, and they have different prices. Price per unit prices are also stored to highlight the best options later.
- **PriceHistory**: tracks every price a product has ever had. No discounts are applied.
- **Discount**: stores discounts for StoreProducts
- **UserStorePreferrence**: implements a m:n relationship between users and their preferred stores, used for filtering search results and recommendations.
### The usage of AOP
As an extra functionality, I thought about letting users select their favorite stores, to enhance user experience, since users might only want to see products or discounts from their favorite stores, either due to proximity or personal preference. For this, I used the concept of AOP, or aspect-oriented programming to add this filtering based on each user’s favorite stores. I created a custom aspect `StorePreferencesAspect` with an annotation `@FilterByStorePreferences` that separates this logic from the main code and marks service methods that return collections of products or discounts. This means that whenever a marked method executes, the aspect retrieves the current user’s store preferences and modifies the returned data by filtering out any entities not associated with those stores.

## Tasks Implementation
For the main functionalities, I created some sequence diagrams to show the flow of data. While they represent the overall logic, there may be a few minor innacuracies. Also, please note that the reply arrows should be depicted using dashed lines to follow standard UML notation.
### Shopping Basket Optimization
`http://localhost:8080/price-comparator/shopping/optimize`
**Request Body: **
```
[
  {
    "productId": "string",
    "quantity": 0
  }
]
```
The endpoint allows users to submit a list of desired products with quantities (the basket) and returns optimized shopping lists for each store, selecting the lowest prices for the requested products, including applying any active discounts. The service checks the user’s preferred stores and tries to find the cheapest option per product. The `@FilterByStorePreferences` annotation isn't used by the service method, but by the repository method that retrieves a product (the StorePreferencesAspect was mistakenly not included in the diagram), so if a product isn’t available in any of the user's preferred store, it is added to an “unavailable” list that will be displayed in the response to inform the user which items couldn’t be found. Then, the service method generates one shopping list per store with the selected products and their total prices, returning these lists wrapped in a DTO along with any unavailable product names. Example for response:
```
{
  "shoppingLists": [
    {
      "storeName": "Lidl",
      "listName": "Shopping List - Lidl | 2025-05-22",
      "items": [
        {
          "productId": "P001",
          "productName": "lapte zuzu",
          "quantity": 2,
          "finalPrice": 16.66
        },
        {
          "productId": "P011",
          "productName": "pâine albă",
          "quantity": 3,
          "finalPrice": 10.2
        }
      ],
      "totalPrice": 26.86
    }
  ],
  "unavailable": [
    "cașcaval"
  ]
}
```

![optimize](https://github.com/user-attachments/assets/18c244a9-85dd-4c92-95c8-651371834f35)

### Best Discounts
`http://localhost:8080/price-comparator/discounts/best`

This endpoint returns a list of the best currently active discounts, in descending order, from the highest discount percentage to the lowest. The list is filtered by user store preferences, meaning users won't see discounts from stores they haven't selected. The resulted list consists of DiscountDtos, which includes details about the discounted product, the base price and price per unit, the discount percentage, and the discounted base price and price per unit. Example for DiscountDto: 
```
{
  "productId": "P045",
  "productName": "hârtie igienică 3 straturi",
  "brandName": "Motto",
  "storeName": "Profi",
  "discountPercentage": 30,
  "fromDate": "2025-05-21",
  "toDate": "2025-05-26",
  "originalPrice": 18.5,
  "packageQuantity": 10,
  "unit": "role",
  "originalPricePerUnit": 1.85,
  "standardUnit": "RON/rola",
  "discountedPrice": 12.95,
  "discountedPricePerUnit": 1.295
}
```
![best](https://github.com/user-attachments/assets/52ff7623-5774-4add-a498-6ea288438099)

### New Discounts
`http://localhost:8080/price-comparator/discounts/new?days=`

This endpoint returns a list of the newly appeared discounts that have become active within the last `n` days, where `n` is specified via the days query parameter. This list is also filtered by user store preferences, meaning users won't see discounts from stores they haven't selected. The returned list consists of DiscountDtos, exactly like the result of the previous endpoint.

![new](https://github.com/user-attachments/assets/755233e7-4571-401a-94a9-50d8c50b877c)

### Price History For A Product
`http://localhost:8080/price-comparator/price-trends/:productId`

The endpoint returns the price evolution of a specific product across all stores. It retrieves every price a product has ever had (no discounts applied!) using the product ID and organizes it into a list of StoreProductHistoryDto objects, where each DTO represents a store and contains a history of price changes for the product over time. StoreProductHistoryDtoConverter groups the price history data by store and then by product, to help users visualize how the price of a product has changed in different stores. This functionality is not affected by the AOP. Example of a response:
```
[
  {
    "storeName": "Lidl",
    "products": [
      {
        "id": "P001",
        "productName": "lapte zuzu",
        "brand": "Zuzu",
        "category": "lactate",
        "pricePoints": [
          {
            "date": "2025-05-01",
            "price": 9.9,
            "pricePerUnit": 9.9
          },
          {
            "date": "2025-05-08",
            "price": 9.8,
            "pricePerUnit": 9.8
          }
        ]
      }
    ]
  },
  {
    "storeName": "Kaufland",
    ...
  }
]
```
![productHistory](https://github.com/user-attachments/assets/c92b66e7-0f23-40d8-bb75-300611290db8)

### Price History For Every Product
`http://localhost:8080/price-comparator/price-trends?storeName=&brandName=&categoryName=`

This endpoint retrieves the price trends of all products, optionally filtered by store name, brand or category using query parameters. Results have the same format as the previous endpoint. This functionality is not affected by the AOP.
![productHistories](https://github.com/user-attachments/assets/e97a752a-3078-4d62-a619-7207922bd562)

### Product Substitutes & Recommendations
#### Price comparison by product id
`http://localhost:8080/price-comparator/products/compare/{productId}`

This endpoint retrieves and compares the prices of a specific product across all available stores, based on user's preference. The response is a list of ProductDto objects, each representing the price of the product in different stores. Each ProductDto includes details about pricing such as the base price, discount percentage (if applicable), discounted price, and price per unit. The list is sorted by the discounted price per unit, to help users choose the best available option.

```
{
  "storeName": "Profi",
  "originalPrice": 12.7,
  "packageQuantity": 0.3,
  "unit": "kg",
  "originalPricePerUnit": 42.33,
  "standardUnit": "RON/kg",
  "discountPercentage": 10,
  "discountedPrice": 11.43,
  "discountedPricePerUnit": 38.097
}
```
![compare](https://github.com/user-attachments/assets/00c65d40-a633-4dbc-b6fd-f4330a012307)

#### Price comparison by product name
`http://localhost:8080/price-comparator/products/{productName}`

This endpoint works in a similar manner, but based on the name of the product instead of its ID. It retrieves all products with the matching name, even those with different quantities, across all preferred stores of users and returns a list of ProductDtos with the same pricing and discount structure. Like before, the list is sorted by discounted price per unit to highlight the best deal.
![compareName](https://github.com/user-attachments/assets/86ce8ca3-90f5-4494-8c6e-307e58b9b410)

The logic used here can be extended to brand-based or category-based comparisons.

### Custom Price Alert
`http://localhost:8080/price-comparator/alerts`

**Request Body:**
```
{
  "productId": "string",
  "targetPrice": 0
}
```
This endpoint lets users create price alerts by setting a target price for a product. The system saves the alert linked to the user and checks daily for changes in store prices, including discounts. When the price of a product drops below the target, the alert is marked triggered and the user is notified. The result returns only a message confirming the creation of the alert. This feature helps users automatically track price drops and get notified without minimal effort.

![priceAlert](https://github.com/user-attachments/assets/41508f94-37e5-4aa2-9676-662d45db965b)


