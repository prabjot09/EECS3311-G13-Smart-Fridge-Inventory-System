package domainLayer;

import java.util.ArrayList;
import java.util.List;

public class Recipe extends ItemManager{
	private String recipeName;
	private List<String> instructions;
	
	public Recipe(String name) {
		super();
		this.recipeName = name;
		this.instructions = new ArrayList<String>();
	}
	
	public Recipe(String name, List<StoredItem> items) {
		super(items);
		this.recipeName = name;
		this.instructions = new ArrayList<String>();
	}
	
	public String getRecipeName() {
		return recipeName; 
	}
	
	public void setRecipeName(String name) {
		recipeName = name;
	}
	
	public List<String> getInstructions() {
		return instructions;
	}
	
	public void setInstructions(List<String> newInstructions) {
		instructions = new ArrayList<String>(newInstructions);
	}
}
