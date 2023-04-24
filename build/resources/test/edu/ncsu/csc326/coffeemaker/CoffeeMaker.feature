Feature: CoffeeMakerFeature
  
  In this feature, we are going to test the user stories and use cases for the CoffeeMaker
  Example.  We have provided a CoffeeMakerMain.java file that you can use to examine the 
  modal behavior in the coffee maker and issue UI commands to use it, so that we can 
  adequately test the user stories.
  
  Hints: to catch the mistakes, you might need to add out of range cases for 
  recipes and amounts of ingredients.  Also, be sure to check machine state
  after running the user story:
  - Are the amounts of ingredients correct?
  - Is the system in the right mode?
  - Is the status what you expect?
  - Is the change produced correct?
  - etc.

  Scenario: Add Recipe State
        Priority: 1 Story Points: 2
        If the Coffee Maker is not in use it waits for user input. There are six different 
        options of user input: 1) add recipe, 2) delete a recipe, 3) edit a recipe, 
        4) add inventory, 5) check inventory, and 6) purchase beverage.
        
        For this scenario, what we will do is try each of the six user inputs and make sure 
        that the coffee maker ends up in the appropriate mode.  This would be a good place
        for a scenario outline with a table that described user inputs and expected final states.
        
        You might also want to try a couple of exceptional values to see what the 
        Coffee Maker does.

    Given a default recipe book
    When user enter the option 1
    Then The user should return "ADD_RECIPE" mode
    
  Scenario: delete Recipe State
	
		Given a default recipe book
    When user enter the option 2
    Then The user should return "DELETE_RECIPE" mode
    
    
	Scenario: Edit Recipe State
	
		Given a default recipe book
    When user enter the option 3
    Then The user should return "EDIT_RECIPE" mode
    
    
  Scenario: Add Inventory State
	
		Given a default recipe book
    When user enter the option 4
    Then The user should return "ADD_INVENTORY" mode
    
  Scenario: Check Inventory State
	
		Given a default recipe book
    When user enter the option 5
    Then The user should return "CHECK_INVENTORY" mode
    
  Scenario: Purchase Beverage State
	
		Given a default recipe book
    When user enter the option 6
    Then The user should return "PURCHASE_BEVERAGE" mode
    
   Scenario: Waiting State
	
		Given a default recipe book
    When user enter the option 10
    Then The user should return "WAITING" mode
    

  Scenario: Add a Recipe
        Priority: 1 Story Points: 2
    
        Only three recipes may be added to the CoffeeMaker. A recipe consists of a name, 
        price, units of coffee, units of milk, units of sugar, and units of chocolate. 
        Each recipe name must be unique in the recipe list. Price must be handled as an 
        integer. A status message is printed to specify if the recipe was successfully 
        added or not. Upon completion, the CoffeeMaker is returned to the waiting state.   
        
        For this scenario, you should try to add a recipe to the recipe book, and check to
        see whether the coffee maker returns to the Waiting state.

    Given an empty recipe book
    When user insert a recipe
    |name   ||AmtChocolate ||AmtCoffee|| AmtMilk||AmtSugar||Price|
    |"Latte"||    1        ||   2     ||   2    ||   4    ||100  |
    Then status message "OK" should appear
    And mode is "WAITING"
    
    

    
    Scenario: Add an existing recipe
    	Given an empty recipe book
    	When user insert a recipe
    	|name   ||AmtChocolate ||AmtCoffee|| AmtMilk||AmtSugar||Price|
    	|"Coffee"||    0        ||   2     ||   2    ||   1    ||100  |
    	|"Coffee"||    0        ||   2     ||   2    ||   1    ||100  |
    	Then status message "RECIPE_NOT_ADDED" should appear
    	And mode is "WAITING"
    	
    	Scenario: Add a recipe when recipe list is full
    	Given a default recipe book
    	When user insert a recipe
    	|name   				||AmtChocolate||AmtCoffee|| AmtMilk||AmtSugar||Price|
    	|"Hot Chocolate"||     6      ||   2     ||   1    ||   1    ||100  |
    	Then status message "RECIPE_NOT_ADDED" should appear
    	And mode is "WAITING"


  Scenario: Delete a Recipe
        Priority: 2 Story Points: 1
    
        A recipe may be deleted from the CoffeeMaker if it exists in the list of recipes in the
        CoffeeMaker. The recipes are listed by their name. Upon completion, a status message is
        printed and the Coffee Maker is returned to the waiting state.

    Given a default recipe book
    When user deletes recipe 0
    And status message "OK" should appear
    And mode is "WAITING"

Scenario: Delete a recipe that does not exist
		Given a default recipe book
    When user deletes recipe 6
    Then status message "OUT_OF_RANGE" should appear
    And mode is "WAITING"


  Scenario: Edit a Recipe
        Priority: 2 Story Points: 1
    
        A recipe may be edited in the CoffeeMaker if it exists in the list of recipes in the
        CoffeeMaker. The recipes are listed by their name. After selecting a recipe to edit, the user
        will then enter the new recipe information. A recipe name may not be changed. Upon
        completion, a status message is printed and the Coffee Maker is returned to the waiting
        state.

    Given a default recipe book
    When user edit recipe 1
    |name   ||AmtChocolate ||AmtCoffee|| AmtMilk||AmtSugar||Price|
    |"Mocha"||    15       ||   4     ||   2    ||   3    ||80  |
    Then status message "OK" should appear
		And mode is "WAITING"
	
	
	Scenario: Edit a recipe that does not exist
	
		Given a default recipe book
    When user edit recipe 8
    |name   ||AmtChocolate ||AmtCoffee|| AmtMilk||AmtSugar||Price|
    |"Coffee"||    1        ||   4     ||   2    ||   3    ||80  |
    Then status message "WRONG_MODE" should appear
		And mode is "WAITING"
		
		
  
  Scenario: Add Inventory
        Priority: 1 Story Points: 2
    
        Inventory may be added to the machine at any time from the main menu, and is added to
        the current inventory in the CoffeeMaker. The types of inventory in the CoffeeMaker are
        coffee, milk, sugar, and chocolate. The inventory is measured in integer units. Inventory
        may only be removed from the CoffeeMaker by purchasing a beverage. Upon completion, a
        status message is printed and the CoffeeMaker is returned to the waiting state.

    Given an empty recipe book
    When user add inventory
    |AmtChocolate ||AmtCoffee|| AmtMilk||AmtSugar|
    |    1        ||   2     ||   2    ||   2    |
    Then status message "OK" should appear
		And mode is "WAITING" 
		
		Scenario: Add negative chocolate
			Given an empty recipe book
    	When user add inventory
    	|AmtChocolate ||AmtCoffee|| AmtMilk||AmtSugar|
    	|    -1        ||   2     ||   2    ||   2    |
    	Then status message "OUT_OF_RANGE" should appear
			And mode is "WAITING" 

		Scenario: Add negative coffee
			Given an empty recipe book
    	When user add inventory
    	|AmtChocolate ||AmtCoffee|| AmtMilk||AmtSugar|
    	|    1        ||   -2     ||   2    ||   2    |
    	Then status message "OUT_OF_RANGE" should appear
			And mode is "WAITING"
			
		Scenario: Add negative milk
			Given an empty recipe book
    	When user add inventory
    	|AmtChocolate ||AmtCoffee|| AmtMilk||AmtSugar|
    	|    1        ||   2     ||   -2    ||   2    |
    	Then status message "OUT_OF_RANGE" should appear
			And mode is "WAITING"
			
		Scenario: Add negative sugar
			Given an empty recipe book
    	When user add inventory
    	|AmtChocolate ||AmtCoffee|| AmtMilk||AmtSugar|
    	|    1        ||   2     ||   2    ||   -2    |
    	Then status message "OUT_OF_RANGE" should appear
			And mode is "WAITING"

  Scenario: Check Inventory
        Priority: 2 Story Points: 1
    
        Inventory may be checked at any time from the main menu. The units of each item in the
        inventory are displayed. Upon completion, the Coffee Maker is returned to the waiting state.

    Given an empty recipe book
    When user checks the inventory
    Then status message "OK" should appear
		And mode is "WAITING" 


  Scenario: Purchase Beverage
        Priority: 1 Story Points: 2
    
        The user selects a beverage and inserts an amount of money. The money must be an
        integer. If the beverage is in the RecipeBook and the user paid enough money the
        beverage will be dispensed and any change will be returned. The user will not be able to
        purchase a beverage if they do not deposit enough money into the CoffeeMaker. A user's
        money will be returned if there is not enough inventory to make the beverage. Upon
        completion, the Coffee Maker displays a message about the purchase status and is
        returned to the main menu.

    Given a default recipe book
    When insert money 100
    And user select beverage 0
    Then change should be 50
    And status message "OK" should appear
		And mode is "WAITING"
		
		
	Scenario: Purchase Beverage without enough money
		Given a default recipe book
    When insert money 20
    And user select beverage 0
    Then user should take 20 back
    And status message "INSUFFICIENT_FUNDS" should appear
		And mode is "WAITING"
	
	Scenario: Purchase Beverage that does not exist
		Given an empty recipe book
    When insert money 100
    And user select beverage 0
    Then user should take 100 back
    And status message "OUT_OF_RANGE" should appear
		And mode is "WAITING"
	
	Scenario: Purchase Beverage with negative money
		Given a default recipe book
    When insert money "-50"
    And user select beverage 0
    And status message "INSUFFICIENT_FUNDS" should appear
		And mode is "WAITING"
		
	Scenario: Purchase Beverage without enough chocolate
		Given a default recipe book
    When insert money 100
    And user select beverage 1
    Then user should take 100 back
    And status message "INSUFFICIENT_FUNDS" should appear
		And mode is "WAITING"
		
	
	Scenario: purchase beverage without enough coffee
		Given a default recipe book
		 When user set coffee inventory to 2
    And insert money 100
    And user select beverage 0
    Then user should take 100 back
    And status message "INSUFFICIENT_FUNDS" should appear
		And mode is "WAITING"
		
	Scenario: purchase beverage without enough milk
		Given a default recipe book
		 When user set milk inventory to 2
    And insert money 100
    And user select beverage 2
    Then user should take 100 back
    And status message "INSUFFICIENT_FUNDS" should appear
		And mode is "WAITING"
		
	Scenario: purchase beverage without enough sugar
		Given a default recipe book
		 When user set sugar inventory to 0
    And insert money 100
    And user select beverage 2
    Then user should take 100 back
    And status message "INSUFFICIENT_FUNDS" should appear
		And mode is "WAITING"
	
# Add scenarios from the Use Cases here.  These can be Cucumber versions of the unit 
# tests that were required for course 1, or can be more direct expressions of the use
# case tests found in the Requirements-coffeemaker.pdf file. 
