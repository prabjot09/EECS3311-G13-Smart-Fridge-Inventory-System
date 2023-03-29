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
	
	public static void main(String[] args) {
		DBProxy.getInstance().setDB(new StubDB());
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
		
		JPanel restockingPanel = new CustomBoxPanel(Color.black, BoxLayout.Y_AXIS, 5);		
		JPanel consumptionPanel = new CustomBoxPanel(Color.black, BoxLayout.Y_AXIS, 5);
		
		for (Pair<FoodItem, ItemHistory> entry: data.getData()) {
			ItemHistory itemData = entry.getB();
			
			if (itemData.getConsumptionAmount(day) > 0) {
				JPanel consumptionLabel = buildUsageLabel(itemData.getConsumptionAmount(day), Color.red, "-", entry.getA());
				consumptionPanel.add(consumptionLabel);
			}
			
			if (itemData.getRestockingAmount(day) > 0) {
				JPanel restockingLabel = buildUsageLabel(itemData.getRestockingAmount(day), Color.green, "+", entry.getA());
				restockingPanel.add(restockingLabel);
			}
		}
		
		JPanel dataWrapper = new CustomPanel(Color.black, new GridBagLayout());
		dataWrapper.add(restockingPanel, GridConstraintsSpec.stretchableFillConstraints(0, 0, 1, 0, GridBagConstraints.HORIZONTAL));
		dataWrapper.add(consumptionPanel, GridConstraintsSpec.stretchableFillConstraints(0, 1, 1, 0, GridBagConstraints.HORIZONTAL));
		dataWrapper.add(new CustomPanel(Color.black, new BorderLayout()), GridConstraintsSpec.stretchableFillConstraints(0, 2, 1, 1, GridBagConstraints.BOTH));
		this.add(dataWrapper, GridConstraintsSpec.stretchableFillConstraints(0, 1, 1, 1, GridBagConstraints.VERTICAL));
	}
	
	
	private JPanel buildUsageLabel(int amount, Color fontColor, String type, FoodItem item) {
		JPanel wrapperPanel = new CustomPanel(Color.black, new FlowLayout(FlowLayout.LEFT));
		
		String text = type + amount;
		if (item.getStockType() == StockType.CONTINUOUS) {
			text += "%";
		}
		else {
			text += "  ";
		}
		text += "    " + item.getName();
		
		JLabel usageLabel = new JLabel(text);
		usageLabel.setForeground(fontColor);
		usageLabel.setFont(new Font("Arial", Font.BOLD, 14));
		usageLabel.setBorder(BorderFactory.createEmptyBorder(3, 0, 3, 0));
		
		wrapperPanel.add(usageLabel);
		return wrapperPanel;
	}
	
}
