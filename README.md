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

