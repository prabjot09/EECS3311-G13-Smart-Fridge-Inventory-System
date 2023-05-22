package presentationLayer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import appLayer.App;
import domainLayer.FoodItem;
import domainLayer.Fridge;
import domainLayer.FridgeItem;
import domainLayer.Recipe;
import domainLayer.StockableItemFactory;
import domainLayer.StoredItem;
import domainLayer.FoodItem.StockType;
import presentationLayer.swingExtensions.CustomBoxPanel;
import presentationLayer.swingExtensions.CustomButton;
import presentationLayer.swingExtensions.CustomPanel;
import presentationLayer.swingExtensions.GridConstraintsSpec;

public class RecipeView extends AppFrameView implements ActionListener {
	
	private Fridge inv;
	private JButton backButton, randomButton;
	private List<Recipe> allRecipes;
	private Map<JButton, Recipe> buttonRecipeMap;
	private Recipe selectedRecipe;
	
	private JPanel recipeDescription;
	
	public RecipeView() {
		this.inv = App.getInstance().getInventory();
		this.allRecipes = App.getInstance().getRecipes();
		
		//main panel setup
		this.setBackground(Color.BLACK);
		this.setLayout(new GridLayout(1, 2));
		this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		JPanel recipesList = new CustomPanel(Color.black, new GridBagLayout(), 10);
		this.add(recipesList);
		JPanel recipeDetails = new CustomPanel(Color.black, new GridBagLayout(), 10);
		this.add(recipeDetails);
		
		//titles
		JPanel listTitleWrap = new CustomPanel(Color.black, new FlowLayout(FlowLayout.CENTER), 15);
		recipesList.add(listTitleWrap, GridConstraintsSpec.stretchableFillConstraints(0, 0, 1, 0, GridBagConstraints.BOTH));
	    
	    JLabel listTitle = new JLabel("All Recipes");
	    listTitle.setForeground(Color.WHITE);
	    listTitle.setFont(new Font("Arial", Font.BOLD, 32));
	    listTitleWrap.add(listTitle);
	    
	    JPanel listNavPanel = new CustomPanel(Color.black, new BorderLayout());
	    listNavPanel.setBorder(BorderFactory.createEmptyBorder(10, 2, 5, 2));
	    recipesList.add(listNavPanel, GridConstraintsSpec.stretchableFillConstraints(0, 1, 1, 0, GridBagConstraints.BOTH));
	    
	    JPanel listLeftNav = new CustomBoxPanel(Color.black, BoxLayout.X_AXIS);
	    listNavPanel.add(listLeftNav, BorderLayout.LINE_START);
	    
	    backButton = new CustomButton("Back", this, 5);
	    listLeftNav.add(backButton);
	    
	    listLeftNav.add(Box.createRigidArea(new Dimension(15, 5)));
	    
	    JPanel legendPanel = new CustomPanel(Color.black, new FlowLayout(FlowLayout.LEFT));
	    legendPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 25));
	    listLeftNav.add(legendPanel);
	    
	    JPanel redBlock = new CustomPanel(Color.black, new BorderLayout(), 1);
	    redBlock.add(new CustomPanel(Color.red, null));
	    legendPanel.add(redBlock);
	    JLabel legendLabel = new JLabel("- Insufficient");
	    legendLabel.setForeground(Color.red);
	    legendLabel.setFont(new Font("Arial", Font.BOLD, 14));
	    legendPanel.add(legendLabel);
	    
	    randomButton = new CustomButton("Surprise Me!", this, 5);
	    listNavPanel.add(randomButton, BorderLayout.LINE_END);
	    
	    JPanel recipesListPanelWrap = new CustomPanel(Color.black, new BorderLayout());
	    recipesListPanelWrap.setBorder(BorderFactory.createEmptyBorder(10, 2, 10, 2));
	    recipesList.add(recipesListPanelWrap, GridConstraintsSpec.stretchableFillConstraints(0, 2, 1, 1, GridBagConstraints.BOTH));
	    JPanel recipesListPanel = new CustomPanel(new Color(25, 25, 25), new BorderLayout());
	    recipesListPanelWrap.add(recipesListPanel);
	    
	    buttonRecipeMap = new HashMap<JButton, Recipe>();	    
	    JPanel scrollListView = new CustomBoxPanel(recipesListPanel.getBackground(), BoxLayout.Y_AXIS);
	   
	    for (Recipe recipe: allRecipes) {
	    	JPanel recipeItemWrap = new CustomPanel(scrollListView.getBackground(), new BorderLayout(), 5);
	    	scrollListView.add(recipeItemWrap);
	    	JPanel recipeItem = new CustomPanel(new Color(70, 70, 70), new BorderLayout());
	    	recipeItem.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
	    	recipeItemWrap.add(recipeItem);
	    	
	    	JButton recipeItemLabel = new CustomButton("<html>"+recipe.getRecipeName()+"</html>", this, 3);
	    	recipeItemLabel.setFont(new Font("Arial", Font.BOLD, 16));
	    	recipeItemLabel.setForeground(recipeChecker(recipe) ? Color.white : Color.red);
	    	recipeItemLabel.setBackground(recipeItem.getBackground());
	        recipeItemLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	    	recipeItemLabel.setFocusPainted(false);
	    	recipeItem.add(recipeItemLabel, BorderLayout.CENTER);	
	    	
	    	buttonRecipeMap.put(recipeItemLabel, recipe);
	    }
	    
	    JScrollPane listScroll = new JScrollPane(scrollListView);
	    listScroll.setViewportBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
	    listScroll.setBorder(BorderFactory.createEmptyBorder());
	    listScroll.setBackground(recipesListPanel.getBackground());
	    recipesListPanel.add(listScroll);
	    
	    
	    JPanel detailsTitleWrap = new CustomPanel(Color.black, new FlowLayout(FlowLayout.CENTER), 15);
		recipeDetails.add(detailsTitleWrap, GridConstraintsSpec.stretchableFillConstraints(0, 0, 1, 0, GridBagConstraints.BOTH));
	    
	    JLabel detailsTitle = new JLabel("Recipe Details");
	    detailsTitle.setForeground(Color.WHITE);
	    detailsTitle.setFont(new Font("Arial", Font.BOLD, 32));
	    detailsTitleWrap.add(detailsTitle);
	    
	    JPanel recipeDescWrap = new CustomPanel(this.getBackground(), new BorderLayout(), 10);
	    recipeDetails.add(recipeDescWrap, GridConstraintsSpec.stretchableFillConstraints(0, 1, 1, 1, GridBagConstraints.BOTH));
	    recipeDescription = new CustomPanel(new Color(25, 25, 25), new BorderLayout(), 15);
	    recipeDescWrap.add(recipeDescription);
	    
	    generateRecipeDesc();
	    
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
	
	private void generateRecipeDesc() {
		//clear window
		if (recipeDescription.getComponentCount() > 0)
			recipeDescription.remove(0);
		
		JPanel descPanel = new CustomPanel(recipeDescription.getBackground(), new GridBagLayout());
		JScrollPane scroll = new JScrollPane(descPanel);
		scroll.setBorder(BorderFactory.createEmptyBorder());
		recipeDescription.add(scroll);
		
		JPanel nameWrap = new CustomPanel(recipeDescription.getBackground(), new FlowLayout(FlowLayout.LEFT));
		nameWrap.setBorder(BorderFactory.createEmptyBorder(0, 3, 10, 0));
		descPanel.add(nameWrap, GridConstraintsSpec.stretchableFillConstraints(0, 0, 1, 0, GridBagConstraints.BOTH));
		if (selectedRecipe == null) {
			JLabel recipeName = new JLabel("No recipe selected");
			recipeName.setFont(new Font("Arial", Font.BOLD, 22));
			recipeName.setMaximumSize(nameWrap.getPreferredSize());
			recipeName.setForeground(Color.white);
			nameWrap.add(recipeName);
			
			JPanel rest = new CustomPanel(new Color(70, 70, 70), new BorderLayout(), 10);
			descPanel.add(rest, GridConstraintsSpec.stretchableFillConstraints(0, 1, 1, 1, GridBagConstraints.BOTH));
			return;
		}
		
		JLabel recipeName = new JLabel(selectedRecipe.getRecipeName());
		recipeName.setFont(new Font("Arial", Font.BOLD, 22));
		recipeName.setForeground(Color.white);
		nameWrap.add(recipeName);
		
		if (!recipeChecker(selectedRecipe)) {
			recipeName.setForeground(Color.red);
			recipeName.setText(selectedRecipe.getRecipeName() + " (Insufficient Ingredients)");
		}
		
		JPanel ingredientsPanel = new CustomPanel(new Color(40, 40, 40), new GridBagLayout(), 5);
		descPanel.add(ingredientsPanel, GridConstraintsSpec.stretchableFillConstraints(0, 1, 1, 0, GridBagConstraints.HORIZONTAL));
		
		JPanel ingredientsTitleWrap = new CustomPanel(ingredientsPanel.getBackground(), new FlowLayout(FlowLayout.LEFT));
		ingredientsPanel.add(ingredientsTitleWrap, GridConstraintsSpec.stretchableFillConstraints(0, 0, 1, 0, GridBagConstraints.BOTH));
		
		JLabel ingredientsTitle = new JLabel("Ingredients");
		ingredientsTitle.setForeground(Color.white);
		ingredientsTitle.setFont(new Font("Arial", Font.BOLD, 18));
		ingredientsTitleWrap.add(ingredientsTitle);
		
		int count = 1;
		for (StoredItem ingredient: selectedRecipe.getItems()) {
			JPanel ingredientWrap = new CustomPanel(ingredientsPanel.getBackground(), new FlowLayout(FlowLayout.LEFT));
			ingredientsPanel.add(ingredientWrap, GridConstraintsSpec.stretchableFillConstraints(0, count, 1, 0, GridBagConstraints.BOTH));
			JLabel ingredientLabel = new JLabel("+ " + ingredient.getFoodItem().getName() + ", " + ingredient.getStockableItem().getStock());
			ingredientLabel.setForeground(Color.white);
			ingredientLabel.setFont(new Font("Arial", Font.PLAIN, 16));
			ingredientWrap.add(ingredientLabel);
			
			if (!recipeItemChecker(ingredient)) {
				ingredientLabel.setForeground(Color.red);
				ingredientLabel.setText("- " + ingredientLabel.getText().substring(2) + " (Insufficient)");
			}
			count += 1;
		}
		
		JPanel spacing = new CustomPanel(recipeDescription.getBackground(), new BorderLayout(), 7);
		descPanel.add(spacing, GridConstraintsSpec.stretchableFillConstraints(0, 2, 1, 0, GridBagConstraints.HORIZONTAL));
		
		JPanel instructionsPanel = new CustomPanel(new Color(40, 40, 40), new GridBagLayout(), 5);
		descPanel.add(instructionsPanel, GridConstraintsSpec.stretchableFillConstraints(0, 3, 1, 1, GridBagConstraints.BOTH));
		
		GridBagConstraints c = GridConstraintsSpec.stretchableFillConstraints(0, 0, 1, 0, GridBagConstraints.BOTH);
		c.gridwidth = 2;
		JPanel instructionsTitleWrap = new CustomPanel(instructionsPanel.getBackground(), new FlowLayout(FlowLayout.LEFT));
		instructionsPanel.add(instructionsTitleWrap, c);
		
		JLabel instructionsTitle = new JLabel("Instructions");
		instructionsTitle.setForeground(Color.white);
		instructionsTitle.setFont(new Font("Arial", Font.BOLD, 18));
		instructionsTitleWrap.add(instructionsTitle);
		
		count = 1;
		for (String instruction: selectedRecipe.getInstructions()) {
			JPanel numberWrap = new CustomPanel(instructionsPanel.getBackground(), new FlowLayout(FlowLayout.RIGHT));
			instructionsPanel.add(numberWrap, GridConstraintsSpec.stretchableFillConstraints(0, count, 0, 0, GridBagConstraints.BOTH));
			JLabel numbersLabel = new JLabel(count + " )");
			numbersLabel.setForeground(Color.white);
			numbersLabel.setFont(new Font("Arial", Font.PLAIN, 16));
			numberWrap.add(numbersLabel);
			
			JPanel instructionWrap = new CustomPanel(instructionsPanel.getBackground(), new FlowLayout(FlowLayout.LEFT));
			instructionsPanel.add(instructionWrap, GridConstraintsSpec.stretchableFillConstraints(1, count, 1, 0, GridBagConstraints.BOTH));
			JLabel instructionLabel = new JLabel(instruction);
			instructionLabel.setForeground(Color.white);
			instructionLabel.setFont(new Font("Arial", Font.PLAIN, 16));
			instructionWrap.add(instructionLabel);
			count += 1;
		}
		
		JPanel whiteSpaceConsumer = new CustomPanel(instructionsPanel.getBackground(), new BorderLayout());
		c = GridConstraintsSpec.stretchableFillConstraints(0, count, 1, 1, GridBagConstraints.BOTH);
		c.gridwidth = 2;
		instructionsPanel.add(whiteSpaceConsumer, c);
		
		recipeDescription.revalidate();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == backButton) {
			AppWindow.getWindow().loadPreviousWindow();
		}
		if (e.getSource() == randomButton) {
			deselectCurrentRecipe();
			
			Random random = new Random();
			int randomNumber = random.nextInt(allRecipes.size());
			while (allRecipes.get(randomNumber) == selectedRecipe) {
				randomNumber = random.nextInt(allRecipes.size());
			}
			selectedRecipe = allRecipes.get(randomNumber);
			generateRecipeDesc();
		}
		if (buttonRecipeMap.keySet().contains(e.getSource())) {
			deselectCurrentRecipe();
			
			selectedRecipe = buttonRecipeMap.get(e.getSource());
			JButton src = (JButton) e.getSource();
			src.setBorder(BorderFactory.createLineBorder(Color.white, 3));
			src.setBackground(Color.black);
			generateRecipeDesc();
		}
	}

	private void deselectCurrentRecipe() {
		if (selectedRecipe == null)
			return;
		
		for (JButton button: buttonRecipeMap.keySet()) {
			if (buttonRecipeMap.get(button) == selectedRecipe) {
				button.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
				button.setBackground(new Color(70, 70, 70));
			}
		}
	}
}
