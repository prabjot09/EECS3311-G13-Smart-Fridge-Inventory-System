package presentationLayer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import appLayer.App;
import domainLayer.Fridge;
import domainLayer.Recipe;
import domainLayer.StoredItem;
import presentationLayer.swingExtensions.CustomButton;
import presentationLayer.swingExtensions.CustomPanel;
import presentationLayer.swingExtensions.GridConstraintsSpec;

public class RecipeView extends AppFrameView implements ActionListener {
	
	private Fridge inv;
	private JButton backButton, randomButton, displayButton;
	private List<Recipe> allRecipes;
	private String recipeMissing;
	private JList<String> recipeView;
	private DefaultListModel<String> recipeViewItems;
	private JList<String> fullRecipeView;
	private DefaultListModel<String> fullRecipeItems;
	private int currentFullIndex;
	
	public RecipeView(Fridge inv) {
		this.inv = inv;
		this.allRecipes = new ArrayList<Recipe>();
		currentFullIndex = -1;
		//temp hard code recipe
		Recipe temp = new Recipe("Fried Eggs", App.getInstance().getFavorites().getItems());
		Recipe temp2 = new Recipe("juicejuice", App.getInstance().getGroceryList().getItems());
		allRecipes.add(temp);
		allRecipes.add(temp2);
		
		//main panel setup
		this.setBackground(Color.BLACK);
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		//title
		JPanel titleleftPanel = new CustomPanel(Color.black, new FlowLayout(FlowLayout.CENTER));
		titleleftPanel.setBorder(BorderFactory.createEmptyBorder(25,40,10,40));
		this.add(titleleftPanel, GridConstraintsSpec.stretchableFillConstraints(0, 0, 1, 0.1, GridBagConstraints.HORIZONTAL));
	    
	    JLabel recipeTitle = new JLabel("All Recipes");
	    recipeTitle.setForeground(Color.WHITE);
	    recipeTitle.setFont(new Font("Arial", Font.BOLD, 36));
	    titleleftPanel.add(recipeTitle);
	    
	    JPanel titlerightPanel = new CustomPanel(Color.black, new FlowLayout(FlowLayout.CENTER));
		titlerightPanel.setBorder(BorderFactory.createEmptyBorder(25,40,10,40));
		this.add(titlerightPanel, GridConstraintsSpec.stretchableFillConstraints(1, 0, 1, 0.1, GridBagConstraints.HORIZONTAL));
	    
	    JLabel fullrecipeTitle = new JLabel("Recipe Details");
	    fullrecipeTitle.setForeground(Color.WHITE);
	    fullrecipeTitle.setFont(new Font("Arial", Font.BOLD, 36));
	    titlerightPanel.add(fullrecipeTitle);
	    
	    //back button panel
	    JPanel backleftPanel = new CustomPanel(Color.black, new FlowLayout(FlowLayout.LEFT));
		backleftPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 15));
		backButton = new CustomButton("Back", this, 10);
		backleftPanel.add(backButton);
		
		displayButton = new CustomButton("Display Recipe", this, 10);
		backleftPanel.add(displayButton);
		
		this.add(backleftPanel, GridConstraintsSpec.stretchableFillConstraints(0, 1, 1, 0, GridBagConstraints.HORIZONTAL));
		
		JPanel randomrightPanel = new CustomPanel(Color.black, new FlowLayout(FlowLayout.LEFT));
		randomrightPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 15));
		randomButton = new CustomButton("Surprise me", this, 10);
		randomrightPanel.add(randomButton);	
		this.add(randomrightPanel, GridConstraintsSpec.stretchableFillConstraints(1, 1, 1, 0, GridBagConstraints.HORIZONTAL));
		
		
		//available recipes
		recipeViewItems = new DefaultListModel<String>();
		recipeView = new JList<String>();
		recipeView.setBackground(Color.GRAY);
		recipeView.setFont(new Font("Arial", Font.BOLD, 18));
		JScrollPane recipeViewingPane = new JScrollPane(recipeView);
		recipeViewingPane.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		
		JPanel viewWrapper = new CustomPanel(Color.black, new BorderLayout(), 5);
		viewWrapper.add(recipeViewingPane);
		
		c = GridConstraintsSpec.stretchableFillConstraints(0, 2, 1, 1, GridBagConstraints.BOTH);
		this.add(viewWrapper, c);
		
		for (Recipe recipe : allRecipes) {
			if (recipeChecker(recipe)) {
				recipeMissing = "";
			}
			else {
				recipeMissing = "(missing items)";
			}
			recipeViewItems.addElement("- " + recipe.getRecipeName() + recipeMissing);
		}
		recipeView.setModel(recipeViewItems);
		
		recipeView.addListSelectionListener(null);
		
		//full recipe view
		fullRecipeItems = new DefaultListModel<String>();
		fullRecipeView = new JList<String>();
		fullRecipeView.setBackground(Color.GRAY);
		fullRecipeView.setFont(new Font("Arial", Font.BOLD, 18));
		JScrollPane fullRecipePane = new JScrollPane(fullRecipeView);
		fullRecipePane.setBorder(BorderFactory.createLineBorder(Color.WHITE));
		
		JPanel fullWrapper = new CustomPanel(Color.black, new BorderLayout(), 5);
		fullWrapper.add(fullRecipePane);
		
		c = GridConstraintsSpec.stretchableFillConstraints(1, 2, 1, 1, GridBagConstraints.BOTH);
		this.add(fullWrapper, c);
		
		fullRecipeView.setModel(fullRecipeItems);
		
		revalidate();
		
	}
	
	public boolean recipeChecker(Recipe recipe) {
		for (StoredItem recipeItem : recipe.getItems()) {
			if (!recipeItemChecker(recipeItem)) {
				return false;
			}
		}
		return true;
	}
	public boolean recipeItemChecker(StoredItem recipeItem) {
		for (StoredItem fridgeItem : inv.getItems()) {
			if (fridgeItem.getFoodItem().sameAs(recipeItem.getFoodItem())) {
				if (recipeItem.getStockableItem().getStock() <= fridgeItem.getStockableItem().getStock()) {
					return true;
				}
			}
		}
		return false;
	}
	
	public void fullRecipeWindow(Recipe recipe) {
		fullRecipeItems.removeAllElements();
		int counter = 1;
		for (StoredItem recipeItem : recipe.getItems()) {
			if (recipeItemChecker(recipeItem)) {
				recipeMissing = "";
			}
			else {
				recipeMissing = "(missing items)";
			}
			fullRecipeItems.addElement("-" + recipeItem.getFoodItem().getName() + ", " + recipeItem.getStockableItem().getStock() + recipeMissing);
		}
		for (String instruction : recipe.getInstructions()) {
			fullRecipeItems.addElement(counter + ". " + instruction);
			counter++;
		}
		revalidate();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == backButton) {
			AppWindow.getWindow().loadPreviousWindow();
		}
		if (e.getSource() == randomButton) {
			Random random = new Random();
			int randomNumber = random.nextInt(allRecipes.size());
			while (randomNumber == currentFullIndex) {
				randomNumber = random.nextInt(allRecipes.size());
			}
			currentFullIndex = randomNumber;
			fullRecipeWindow(allRecipes.get(randomNumber));
		}
		if (e.getSource() == displayButton) {
			if (recipeView.getSelectedIndex() == -1) {
				JOptionPane.showMessageDialog(null, "Please select a recipe", "Notice", JOptionPane.WARNING_MESSAGE);
				return;
			}
			currentFullIndex = recipeView.getSelectedIndex();
			fullRecipeWindow(allRecipes.get(recipeView.getSelectedIndex()));
		}
	}

}
