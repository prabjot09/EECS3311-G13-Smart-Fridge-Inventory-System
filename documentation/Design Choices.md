# Design Choices Following SOLID Principles

### Single Responsibility Principle:
- The responsibility of the food item descriptions and managing the number of available items is split into the FoodItem Class and Stock Interface.
- The responsibility of accessing and writing to different-purpose databases is split into the FridgeDatabase Class (for storing user's fridge items) and ItemDatabase for storing a list of available items that the user can add to their fridge.

### Open/Closed Principle:
- In the case that items outside of the fridge were to be stored we use inheritence with the StoredItem Interface and Inventory Interface which can support concrete classes for Fridge and FridgeItem and allow simple extension for new classes such as PantryItem.
- In the case where other forms of data must be stored, a reference to that item just needs to be stored in the StubDB class, and a new class definition for a new database.

### Liskov Substitution Principle:
- Items can be added to the fridge regardless of whether they were manually created by the user or selected from a list generated from the Database.
- The Page superclass has 2 main functions to create a child Page and to return to the previous Page to be able to create and display windows in a recursive manner while easily being able to go back to the previous window/page. All “something”-View subclasses inherit the functionality in the same way and are replaceable with the Page superclass

### Interface Segregation Principle:
- All interfaces (Stock, StoredItem, Inventory, DB) are kept simple so that they abstract only the functionalities that extending classes will have to implement.

### Dependency Inversion Principle:
- The DB Interface abstracts all the implementation of the actual database access and management. The client simply needs to specify which database they are using (StubDB or FunctionalDB) when creating a DB object and simply call the abstracted methods.
- The Stock Interface abstracts the implementation of the management of how much of each food/drink we have in the fridge. The client should simply instantiate either a ContinuousStock or DiscreteStock when calling the increment() and decrement() methods which will modify the storage of the item based on the implementation in the concrete classes.
