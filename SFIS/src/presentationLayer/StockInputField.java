package presentationLayer;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

import domainLayer.ContinuousStockableItem;
import domainLayer.FoodItem;
import domainLayer.FoodItem.StockType;
import domainLayer.Pair;
import domainLayer.StockableItem;
import presentationLayer.swingExtensions.DropDown;
import presentationLayer.swingExtensions.InputField;
import presentationLayer.swingExtensions.TextInput;

public class StockInputField {
	
	private TextInput discreteAmtField;
	private DropDown<String> continuousAmtField;
	
	private Map<String, Pair<FoodItem.StockType, Component>> inputFieldMap;
	private Map<String, Integer> continuousValueMap;
	
	public StockInputField() {
		discreteAmtField = new TextInput("Amount Remaining");
	    discreteAmtField.setFont(new Font("Arial", Font.PLAIN, 16));
	    discreteAmtField.setBackground(new Color(185, 185, 185));
		//discreteAmtField.setBounds(0,100,300,500);
	    discreteAmtField.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
	    discreteAmtField.setPreferredSize(new Dimension(discreteAmtField.getPreferredSize().width, discreteAmtField.getPreferredSize().height));
		//discreteAmtField.setPreferredSize(new Dimension(300,50));
		
		List<String> continuousValues = new ArrayList<>();
	    continuousValueMap = new HashMap<>();
	    
	    ContinuousStockableItem stock = new ContinuousStockableItem();
	    for (int i = 0; i <= 100; i += ContinuousStockableItem.INCREMENT) {
	    	stock.setStock(i);
	    	continuousValues.add(stock.getDescription());
	    	continuousValueMap.put(stock.getDescription(), stock.getStock());
	    }
		String[] valuesCopy = new String[continuousValues.size()];
	    for (int i = 0; i < valuesCopy.length; i++) {
	    	valuesCopy[i] = continuousValues.get(i);
	    }
	    
	    continuousAmtField = new DropDown<>(valuesCopy);
	    continuousAmtField.setFont(new Font("Arial", Font.PLAIN, 16));
	    //continuousAmtField.setPreferredSize(new Dimension(300,50));
	    
	    inputFieldMap = new HashMap<>();
		inputFieldMap.put(StockType.CONTINUOUS.toString(), new Pair<>(StockType.CONTINUOUS, continuousAmtField));
		inputFieldMap.put(StockType.DISCRETE.toString(), new Pair<>(StockType.DISCRETE, discreteAmtField));    
	}
	
	public Component getAmountField(String type) {
		Pair<StockType, Component> result = inputFieldMap.get(type);
		
		if (result == null) {
			return null;
		}
		return result.getB();
	}
	
	public Pair<StockType, Integer> getAmountValue(String type) {
		Pair<StockType, Component> result = inputFieldMap.get(type);
		if (result == null) {
			return null;
		}
		
		InputField input = (InputField) result.getB();
		String strValue = input.getInput();
		
		Integer value = null;
		if (result.getA() == StockType.CONTINUOUS) {
			value = continuousValueMap.get(strValue);
		}
		else if (result.getA() == StockType.DISCRETE) {
			try {
				value = Integer.parseInt(strValue);
			} catch (Exception e) {
				value = null;
			}
		}
		
		return new Pair<>(result.getA(), value);
	}
	
	
	public void setFieldValue(String type, int stock) {
		Pair<StockType, Component> result = inputFieldMap.get(type);
		if (result == null) {
			return;
		}
		
		if (result.getB() == this.discreteAmtField) {
			discreteAmtField.setText("" + stock);
		}
		else if (result.getB() == this.continuousAmtField) {
			for (String key: this.continuousValueMap.keySet()) {
				if (continuousValueMap.get(key) == stock) {
					continuousAmtField.setSelectedItem(key);
					break;
				}
			}
		}
	}
	
	
	public void clear() {
		discreteAmtField.clearField();
		continuousAmtField.clearField();
	}

}
