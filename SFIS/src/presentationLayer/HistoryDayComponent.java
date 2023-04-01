package presentationLayer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.time.LocalDate;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import domainLayer.ApplicationClock;
import domainLayer.DBProxy;
import domainLayer.FoodItem;
import domainLayer.ItemHistory;
import domainLayer.Pair;
import domainLayer.UserHistory;
import persistenceLayer.StubDB;
import domainLayer.FoodItem.StockType;
import presentationLayer.swingExtensions.CustomBoxPanel;
import presentationLayer.swingExtensions.CustomPanel;
import presentationLayer.swingExtensions.GridConstraintsSpec;

public class HistoryDayComponent extends JPanel {
	
	private JPanel restockingPanel;
	private JPanel consumptionPanel;
	
	public static void main(String[] args) {
		DBProxy.getInstance().setDB(new StubDB());
		ApplicationClock.initRealClock();
		HistoryDayComponent lay = new HistoryDayComponent(DBProxy.getInstance().loadUserHistory(), 2);
		
		
		JFrame jframe = new JFrame("Hi");
		jframe.add(lay);
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    jframe.getContentPane().setBackground(Color.black);
	    //jframe.setPreferredSize(new Dimension(600, 600));
	    jframe.pack();
	    jframe.setLocationRelativeTo(null);
	    jframe.setVisible(true);
	}
	
	public HistoryDayComponent(UserHistory data, int day) {
		this.setLayout(new GridBagLayout());
		this.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 1, Color.white));
		this.setBackground(Color.black);
		
		JPanel datePanel = new CustomPanel(Color.black, new FlowLayout());
		datePanel.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.white));
		this.add(datePanel, GridConstraintsSpec.stretchableFillConstraints(0, 0, 1, 0, GridBagConstraints.HORIZONTAL));
		
		LocalDate date = ApplicationClock.getDate().minusDays(day);
		JLabel dateLabel = new JLabel(date.toString());
		dateLabel.setForeground(Color.white);
		dateLabel.setFont(new Font("Arial", Font.BOLD, 18));
		dateLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		datePanel.add(dateLabel);
		
		restockingPanel = new CustomPanel(Color.black, new GridBagLayout(), 5);		
		consumptionPanel = new CustomPanel(Color.black, new GridBagLayout(), 5);	
		
		int consumptionRow = 0;
		int restockingRow = 0;
		for (Pair<FoodItem, ItemHistory> entry: data.getData()) {
			ItemHistory itemData = entry.getB();
			
			if (itemData.getConsumptionAmount(day) > 0) {
				buildUsageLabel(consumptionPanel, itemData.getConsumptionAmount(day), consumptionRow, Color.red, "-", entry.getA());
				consumptionRow++;
			}
			
			if (itemData.getRestockingAmount(day) > 0) {
				buildUsageLabel(restockingPanel, itemData.getRestockingAmount(day), restockingRow, Color.green, "+", entry.getA());
				restockingRow++;
			}
		}
		
		JPanel dataWrapper = new CustomPanel(Color.black, new GridBagLayout());
		dataWrapper.add(restockingPanel, GridConstraintsSpec.stretchableFillConstraints(0, 0, 1, 0, GridBagConstraints.HORIZONTAL));
		dataWrapper.add(consumptionPanel, GridConstraintsSpec.stretchableFillConstraints(0, 1, 1, 0, GridBagConstraints.HORIZONTAL));
		dataWrapper.add(new CustomPanel(Color.black, new BorderLayout()), GridConstraintsSpec.stretchableFillConstraints(0, 2, 1, 1, GridBagConstraints.BOTH));
		this.add(dataWrapper, GridConstraintsSpec.stretchableFillConstraints(0, 1, 1, 1, GridBagConstraints.VERTICAL));
	}
	
	
	private void buildUsageLabel(JPanel parent, int amount, int row, Color fontColor, String type, FoodItem item) {
		String amountText = type + amount;
		if (item.getStockType() == StockType.CONTINUOUS) {
			amountText += "%";
		}
		String nameText = item.getName();
		
		JPanel amountWrapper = new CustomPanel(Color.black, new FlowLayout(FlowLayout.LEFT), 1);
		JLabel amountLabel = new JLabel(amountText);
		amountLabel.setForeground(new Color(fontColor.getRed() == 0 ? 100 : 255, fontColor.getGreen() == 0 ? 100 : 255, 80));
		amountLabel.setFont(new Font("Arial", Font.BOLD, 14));
		amountLabel.setBorder(BorderFactory.createEmptyBorder(3, 0, 3, 0));
		amountWrapper.add(amountLabel);
		
		JLabel nameLabel = new JLabel(nameText);
		nameLabel.setForeground(fontColor);
		nameLabel.setFont(new Font("Arial", Font.BOLD, 14));
		nameLabel.setBorder(BorderFactory.createEmptyBorder(3, 0, 3, 0));
		
		parent.add(amountWrapper, GridConstraintsSpec.stretchableFillConstraints(0, row, 0.1, 0, GridBagConstraints.HORIZONTAL));
		parent.add(nameLabel, GridConstraintsSpec.stretchableFillConstraints(1, row, 0.9, 0, GridBagConstraints.HORIZONTAL));
	}
	
}
