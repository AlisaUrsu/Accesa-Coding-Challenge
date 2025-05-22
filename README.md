# Accesa Coding Challenge
My solution for the coding challenge for Accesa Java Internship 2025
## Overview
This projects implements the backend of a price comparison application. The main functionalities include comparing product prices across stores, highligthing the cheapest option based on unit price, optimizing shopping baskets and generating shopping lists specific to a store, displaying currently active discounts, tracking the evolution of prices of products found in each store, and allowing users to select preferred stores.
## Project Structure
### Database Structure
The database schema was designed with modularity and extensibility in mind to support a scalable backend, which can simplify future expansion, even if my current focus was on the main tasks.
![PriceComp](https://github.com/user-attachments/assets/b1d3cd04-6cca-4ac2-8ccf-88a872328c9e)
Products and Discounts are parsed from the .csv files.
- **Product**: keeps general information about a specific product, like id, name, package quantity and unit. The rest represent foreign keys to other tables.
- **Brand**: stores an id and name. Every time a new brand is parsed in the file, it will be added in the table.
- **Category**: stores an id and name. Every time a new category is parsed in the file, it will be added in the table.
- **Unit**: is used for computing the price per unit for an item by knowing the conversion factor for a certain unit. (e.g 1g = 0.001kg)
- **Store**
### The usage of AOP
As an extra functionality, I thought about letting users select their favorite stores, because the way I thought this is that while users can compare prices online, they still have to go in person to stores, and probably some stores are too far away from them or they simply had a bad experience with that. 

For this implementation I used the concept of AOP, or aspect-oriented programming to add this filtering based on each user’s favorite stores. This means that whenever products or discounts are fetched, only the items from the user's selected stores will be displayed. I created a custom aspect `StorePreferencesAspect` with an annotation `@FilterByStorePreferences` that helps me separate this logic from the main code, and I used it on service methods that returned a collection containing products or discounts. 

## Task Implementation
For the main functionalities, I created some sequence diagrams to show the flow of data. I would say they are mostly accurate, but some small mistakes may appear.
### Shopping Basket Optimization
`http://localhost:8080/price-comparator/shopping/optimize`
![optimize](https://github.com/user-attachments/assets/18c244a9-85dd-4c92-95c8-651371834f35)

### Best Discounts
`http://localhost:8080/price-comparator/discounts/best`

This endpoint returns a list of the best currently active discounts, in descending order, from the highest discount percentage to the lowest. The list is filtered by user store preferences, meaning users won't see discounts from stores they haven't selected. The resulted list consists of DiscountDTOs, which includes details about the discounted product, the base price and price per unit, the discount percentage, and the discounted base price and price per unit. Example: 
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

This endpoint returns a list of the newly appeared discounts that have become active within the last `n` days, where `n` is specified via the days query parameter. This list is also filtered by user store preferences, meaning users won't see discounts from stores they haven't selected. The resulted list consists of DiscountDTOs, exactly like the result of the endpoint before.

![new](https://github.com/user-attachments/assets/755233e7-4571-401a-94a9-50d8c50b877c)

### Price History For A Product
`http://localhost:8080/price-comparator/price-trends/:productId`
![productHistory](https://github.com/user-attachments/assets/c92b66e7-0f23-40d8-bb75-300611290db8)

### Price History For Every Product
`http://localhost:8080/price-comparator/price-trends?storeName=&brandName=&categoryName=`
![productHistories](https://github.com/user-attachments/assets/e97a752a-3078-4d62-a619-7207922bd562)

### Product Substitutes & Recommendations

#### Price comparison by product id
`http://localhost:8080/price-comparator/products/compare/{productId}`

This endpoint retrieves and compares the prices of a specific product across all available stores, based on user's preference. The response is a list of ProductDto objects, each representing the price of the product in different stores. Each ProductDto includes details about pricing such as the base price, discount percentage (if applicable), discounted price, and price per unit. The list is sorted by the discounted price per unit, to help users choose the best available option.
![compare](https://github.com/user-attachments/assets/00c65d40-a633-4dbc-b6fd-f4330a012307)

#### Price comparison by product name
`http://localhost:8080/price-comparator/products/{productName}`

This endpoint works in a similar manner, but based on the name of the product instead of its ID. It retrieves all products with the matching name, even those with different quantities, across all preferred stores of users and returns a list of ProductDtos with the same pricing and discount structure. Like before, the list is sorted by discounted price per unit to highlight the best deal.
![compareName](https://github.com/user-attachments/assets/86ce8ca3-90f5-4494-8c6e-307e58b9b410)


### Custom Price Alert
`http://localhost:8080/price-comparator/alerts`
![priceAlert](https://github.com/user-attachments/assets/41508f94-37e5-4aa2-9676-662d45db965b)


