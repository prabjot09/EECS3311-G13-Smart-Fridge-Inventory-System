# EECS3311-Group-13
Welcome to the SFIS (Smart Fridge Inventory System) application.
Here, we will describe how to get started with our application.

## Installation
You are welcome to clone this repository and run the files yourself through whichever IDE you choose, but if you simply would like to use the application without viewing any of the code, then run the SIFS.exe exectutable in this root directory.
Dependencies:
 - JUnit 5
 - JDBC 8.0.23
 - MySQL on local machine

## Usage Instructions
### Home Page
Here you can view what you have currently in the fridge
* By entering text in into the text field above the list of items in the fridge and clicking 'Search', you can view all the items in the fridge that match the input text in its name. 
* To add a new item to the fridge click the **+** icon.
* To change the quantity of an item, select any item in the list and use the **Increment** or **Decrement** buttons to increase/decrease its quantity.
* To remove an item from the fridge click the **Remove** button.
* To toggle between view layouts click the button with the square/stacked-lines icon.
* To add an item to the grocery list, click the **Add to Grocery List** button.
* To export the grocery list select an option for the dropdown underneath the list and click **Export**.
* To view or edit your 'Favorites List' click the **Favorites List** button.
* Use the dropdown next to the search panel to select Searching options.

### Add New Item Page
After pressing the **+** button on the home page you will be sent here to add your new item.
1) Select among the 2 choices to either choose an item from a generated list, or to define your own item manually.
2) Fill in the required fields to set the name and initial quantity of the item you are placing in the fridge.
3) If you choose to select an item from the pre-generated list, then you must first search (similarly to in the home page) the items in our catalog. Searching with no name will display all the items in the fridge.
4) Click 'Add' to add your item to the fridge. You can go back to the main screen to view these changes!


### Favorited Items Page
* To adjust the quanitities or remove items from the 'Favorites List' follow the same steps as in the *Home Page*
* To add an item from the Fridge to the 'Favorites List' follow these steps:
** Select an item from 'Your Fridge Items' selection.
** Enter a quantity in the input field at the bottom.
** Click the **Add to Favorites List** button to add the item to the Favorites List.

**Voila, now you're an expert on how to use this application. We hope you find it useful.**
