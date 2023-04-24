/*
 * Copyright (c) 2009,  Sarah Heckman, Laurie Williams, Dright Ho
 * All Rights Reserved.
 * 
 * Permission has been explicitly granted to the University of Minnesota 
 * Software Engineering Center to use and distribute this source for 
 * educational purposes, including delivering online education through
 * Coursera or other entities.  
 * 
 * No warranty is given regarding this software, including warranties as
 * to the correctness or completeness of this software, including 
 * fitness for purpose.
 * 
 * 
 * Modified 20171114 by Ian De Silva -- Updated to adhere to coding standards.
 * 
 */
package edu.ncsu.csc326.coffeemaker;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.List;
import java.util.Map;

import cucumber.api.DataTable;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import edu.ncsu.csc326.coffeemaker.UICmd.AddInventory;
import edu.ncsu.csc326.coffeemaker.UICmd.CheckInventory;
import edu.ncsu.csc326.coffeemaker.UICmd.ChooseRecipe;
import edu.ncsu.csc326.coffeemaker.UICmd.ChooseService;
import edu.ncsu.csc326.coffeemaker.UICmd.Command;
import edu.ncsu.csc326.coffeemaker.UICmd.DescribeRecipe;
import edu.ncsu.csc326.coffeemaker.UICmd.InsertMoney;
import edu.ncsu.csc326.coffeemaker.UICmd.TakeMoneyFromTray;

/**
 * Contains the step definitions for the cucumber tests.  This parses the 
 * Gherkin steps and translates them into meaningful test steps.
 */
public class TestSteps {
	
	private Recipe recipe1;
	private Recipe recipe2;
	private Recipe recipe3;
	private Recipe recipe4;
	private Recipe recipe5;
	private CoffeeMakerUI coffeeMakerMain; 
	private CoffeeMaker coffeeMaker;
	private RecipeBook recipeBook;

	
	private void initialize() {
		recipeBook = new RecipeBook();
		coffeeMaker = new CoffeeMaker(recipeBook, new Inventory());
		coffeeMakerMain = new CoffeeMakerUI(coffeeMaker);
	}
	
    @Given("^an empty recipe book$")
    public void an_empty_recipe_book() throws Throwable {
        initialize();
    }

    @Given("a default recipe book")
	public void a_default_recipe_book() throws Throwable {
    	initialize();
    	
		//Set up for r1
		recipe1 = new Recipe();
		recipe1.setName("Coffee");
		recipe1.setAmtChocolate("0");
		recipe1.setAmtCoffee("3");
		recipe1.setAmtMilk("1");
		recipe1.setAmtSugar("1");
		recipe1.setPrice("50");
		
		//Set up for r2
		recipe2 = new Recipe();
		recipe2.setName("Mocha");
		recipe2.setAmtChocolate("20");
		recipe2.setAmtCoffee("3");
		recipe2.setAmtMilk("1");
		recipe2.setAmtSugar("1");
		recipe2.setPrice("75");
		
		//Set up for r3
		recipe3 = new Recipe();
		recipe3.setName("Latte");
		recipe3.setAmtChocolate("0");
		recipe3.setAmtCoffee("3");
		recipe3.setAmtMilk("3");
		recipe3.setAmtSugar("1");
		recipe3.setPrice("100");
		
		//Set up for r4
		recipe4 = new Recipe();
		recipe4.setName("Hot Chocolate");
		recipe4.setAmtChocolate("4");
		recipe4.setAmtCoffee("0");
		recipe4.setAmtMilk("1");
		recipe4.setAmtSugar("1");
		recipe4.setPrice("65");
		
		//Set up for r5 (added by MWW)
		recipe5 = new Recipe();
		recipe5.setName("Super Hot Chocolate");
		recipe5.setAmtChocolate("6");
		recipe5.setAmtCoffee("0");
		recipe5.setAmtMilk("1");
		recipe5.setAmtSugar("1");
		recipe5.setPrice("100");

		recipeBook.addRecipe(recipe1);
		recipeBook.addRecipe(recipe2);
		recipeBook.addRecipe(recipe3);
		recipeBook.addRecipe(recipe4);
		
	}
    
    @When("^user enter the option (-?\\d+)$")
    public void get_choice(int choice) throws Throwable{
    	Command cmd = new ChooseService(choice);
    	coffeeMakerMain.UI_Input(cmd);
    	//System.out.println(cmd);
    }
    
    @Then("The user should return \"([^\"]*)\" mode$")
    public void mode_type(String mode) throws Throwable {
    	assertEquals(mode, coffeeMakerMain.getMode().toString());
    	//System.out.println(mode);
    }
    
    @When("^user insert a recipe$")
    public void user_insert_a_recipe(DataTable arg1) throws Throwable {
    	List <Map<String, String>> recipeIngredients = arg1.asMaps(String.class, String.class);
        
        
        for(Map<String, String> recipeIngredient: recipeIngredients) {
        	Recipe r = new Recipe();
        	r.setName(recipeIngredient.get("name"));
            r.setAmtChocolate(recipeIngredient.get("AmtChocolate"));
            r.setAmtCoffee(recipeIngredient.get("AmtCoffee"));
            r.setAmtMilk(recipeIngredient.get("AmtMilk"));
            r.setAmtSugar(recipeIngredient.get("AmtSugar"));
            r.setPrice(recipeIngredient.get("Price"));
            
            Command cmd1 = new ChooseService(1);
            coffeeMakerMain.UI_Input(cmd1);
            Command cmd2 = new DescribeRecipe(r);
            coffeeMakerMain.UI_Input(cmd2);
        }
          
    }
    

    @Then("^status message \"([^\"]*)\" should appear$")
    public void status_message_should_appear(String arg1) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
         assertEquals(arg1,coffeeMakerMain.getStatus().toString());
    }

    @Then("^mode is \"([^\"]*)\"$")
    public void mode_is(String arg1) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        assertEquals (arg1,coffeeMakerMain.getMode().toString());
    }
    
    @When("^user deletes recipe (\\d+)$")
    public void user_deletes_recipe(int arg1) throws Throwable {
        Command cmd1 = new ChooseService(2);
        coffeeMakerMain.UI_Input(cmd1);
        Command cmd2 = new ChooseRecipe(arg1);
        coffeeMakerMain.UI_Input(cmd2);

    }
    
    @When("^user edit recipe (\\d+)$")
    public void user_edit_recipe(int recipeNum, DataTable arg2) throws Throwable {
    	List <Map<String, String>> recipeIngredients = arg2.asMaps(String.class, String.class);
    	
    	for(Map<String, String> recipeIngredient: recipeIngredients) {
        	Recipe r = new Recipe();
        	r.setName(recipeIngredient.get("name"));
            r.setAmtChocolate(recipeIngredient.get("AmtChocolate"));
            r.setAmtCoffee(recipeIngredient.get("AmtCoffee"));
            r.setAmtMilk(recipeIngredient.get("AmtMilk"));
            r.setAmtSugar(recipeIngredient.get("AmtSugar"));
            r.setPrice(recipeIngredient.get("Price"));
            
            Command cmd1 = new ChooseService(3);
            coffeeMakerMain.UI_Input(cmd1);
            
            Command cmd2 = new ChooseRecipe(recipeNum);
            coffeeMakerMain.UI_Input(cmd2);
            
            Command cmd3 = new DescribeRecipe(r);
            coffeeMakerMain.UI_Input(cmd3);
        }
    	
    }
    
    
    @Then("^inventory should be$")
    public void inventory_should_be(DataTable arg1) throws Throwable {
    	List<Map<String, String>> recipeIngredients = arg1.asMaps(String.class, String.class);
    	
    	for(Map<String, String> recipeIngredient: recipeIngredients) {
    		
    		String coffee = recipeIngredient.get("AmtCoffee");
    		String milk = recipeIngredient.get("AmtMilk");
    		String sugar = recipeIngredient.get("AmtSugar");
    		String chocolate = recipeIngredient.get("AmtChocolate");
    		
    		StringBuffer buf = new StringBuffer();
        	buf.append("Coffee: ");
        	buf.append(coffee);
        	buf.append("\n");
        	buf.append("Milk: ");
        	buf.append(milk);
        	buf.append("\n");
        	buf.append("Sugar: ");
        	buf.append(sugar);
        	buf.append("\n");
        	buf.append("Chocolate: ");
        	buf.append(chocolate);
        	buf.append("\n");
    		assertEquals(buf.toString(),coffeeMaker.checkInventory());
    	}
    	
        
    }

    
    @When("^user checks the inventory$")
    public void user_checks_the_inventory() throws Throwable {
    	
    	Command cmd = new ChooseService(5);
    	coffeeMakerMain.UI_Input(cmd);
    	
    	Command cmd1 = new CheckInventory();
    	coffeeMakerMain.UI_Input(cmd1);
    }
    
    
    @When("^insert money (\\d+)$")
    public void insert_money(int arg1) throws Throwable {
    	// Write code here that turns the phrase above into concrete actions
    	Command cmd1 = new InsertMoney(arg1);
    	coffeeMakerMain.defaultCommands(cmd1);
    }
    
    
    @When("^insert money \"([^\"]*)\"$")
    public void insert_money(String arg1) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
    	int amount = Integer.parseInt(arg1);
    	Command cmd = new InsertMoney(amount);
        coffeeMakerMain.defaultCommands(cmd);
    }

    
    @When("^user select beverage (\\d+)$")
    public void user_select_beverage(int recipeNum) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        Command cmd1 =new ChooseService(6);
        coffeeMakerMain.UI_Input(cmd1);
        
        coffeeMakerMain.displayRecipes();
        
        Command cmd2 = new ChooseRecipe(recipeNum);
        coffeeMakerMain.UI_Input(cmd2);
    }

    @Then("^change should be (\\d+)$")
    public void change_should_be(int change) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
    	assertEquals(change, coffeeMakerMain.getMoneyInTray());
    }
    
    
    
    @Then("^user should take (\\d+) back$")
    public void user_should_take_back(int money) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        assertEquals(money, coffeeMakerMain.getMoneyInserted());
    }
    
    @When("^user add inventory$")
    public void user_add_inventory(DataTable arg1) throws Throwable {
    	List<Map<String, Integer>> recipeIngredients = arg1.asMaps(String.class, Integer.class);
    	
    	for(Map<String, Integer> recipeIngredient: recipeIngredients) {
    		Command cmd1 = new ChooseService(4);
    		coffeeMakerMain.UI_Input(cmd1);
    		int coffee = recipeIngredient.get("AmtCoffee");
    		int milk = recipeIngredient.get("AmtMilk");
    		int sugar = recipeIngredient.get("AmtSugar");
    		int chocolate = recipeIngredient.get("AmtChocolate");
    		
    		Command cmd2 = new AddInventory(coffee,milk,sugar,chocolate);
    		coffeeMakerMain.UI_Input(cmd2);
    	}
    }
    
    
    @When("^I add inventory to coffee maker$")
    public void i_add_inventory_to_coffee_maker(DataTable arg1) throws Throwable {
    	List<Map<String, String>> recipeIngredients = arg1.asMaps(String.class, String.class);
    	
    	for(Map<String, String> recipeIngredient: recipeIngredients) {
    		String coffee = recipeIngredient.get("AmtCoffee");
    		String milk = recipeIngredient.get("AmtMilk");
    		String sugar = recipeIngredient.get("AmtSugar");
    		String chocolate = recipeIngredient.get("AmtChocolate");
    		
    		coffeeMaker.addInventory(coffee, milk, sugar, chocolate);
    	}
    }
    
    
    @When("^user set coffee inventory to (\\d+)$")
    public void user_set_coffee_inventory_to(int arg1) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        //throw new PendingException();
    	Inventory inv = new Inventory();
    	inv.setCoffee(arg1);
    	coffeeMaker= new CoffeeMaker(recipeBook, inv);
    }
    
    @When("^user set milk inventory to (\\d+)$")
    public void user_set_milk_inventory_to(int arg1) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        //throw new PendingException();
    	Inventory inv = new Inventory();
    	inv.setMilk(arg1);
    	coffeeMaker= new CoffeeMaker(recipeBook, inv);
    }
    
    @When("^user set sugar inventory to (\\d+)$")
    public void user_set_sugar_inventory_to(int arg1) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        //throw new PendingException();
    	Inventory inv = new Inventory();
    	inv.setSugar(arg1);
    	coffeeMaker= new CoffeeMaker(recipeBook, inv);
    }
}
