package presentationLayer.swingExtensions;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.time.LocalDate;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class DateInputField extends JPanel {
	
	private JComboBox<String> dateField, monthField, yearField;
	
	public DateInputField (Color bg) {
		
		this.setBackground(bg);
		this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		String[] dates = new String[32];
		dates[0] = "Day";
		for (int i = 1; i < 32; i++) {
			dates[i] = "" + i;
		}
		
		String[] months = new String[13];
		months[0] = "Month";
		for (int i = 1; i < 13; i++) {
			months[i] = "" + i;
		}
		
		int year = LocalDate.now().getYear();
		String[] years = {"Year", "" + year, "" + (year + 1)};
		
		
		dateField = new JComboBox<String>(dates);
		dateField.setFont(new Font("Arial", Font.PLAIN, 16));
		dateField.setPreferredSize(new Dimension(dateField.getMinimumSize().width + 20, dateField.getMinimumSize().height + 10));
		dateField.setMaximumRowCount(5);
		this.add(dateField);
		
		this.add(Box.createRigidArea(new Dimension(10, 10)));
		
		monthField = new JComboBox<String>(months);
		monthField.setFont(new Font("Arial", Font.PLAIN, 16));
		monthField.setPreferredSize(new Dimension(monthField.getMinimumSize().width + 20, monthField.getMinimumSize().height + 10));
		monthField.setMaximumRowCount(5);
		this.add(monthField);
		
		this.add(Box.createRigidArea(new Dimension(10, 10)));
		
		yearField = new JComboBox<String>(years);
		yearField.setPreferredSize(new Dimension(yearField.getMinimumSize().width + 20, yearField.getMinimumSize().height + 10));
		yearField.setFont(new Font("Arial", Font.PLAIN, 16));
		this.add(yearField);		
	}
	
	public void setSelectedDate(LocalDate date) {
		if (date == null)
			return;
		
		dateField.setSelectedItem("" + date.getDayOfMonth());
		monthField.setSelectedItem("" + date.getMonthValue());
		yearField.setSelectedItem("" + date.getYear());
	}
	
	public boolean isUnused() {
		return (dateField.getSelectedIndex() == 0 &&
				monthField.getSelectedIndex() == 0 && 
				yearField.getSelectedIndex() == 0);
	}
	
	public LocalDate getDate() {
		int date, month, year;
			
		try {
			date = Integer.parseInt((String) dateField.getSelectedItem());
			month = Integer.parseInt((String) monthField.getSelectedItem());
			year = Integer.parseInt((String) yearField.getSelectedItem());
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Please select valid date input", "Warning", JOptionPane.WARNING_MESSAGE);
			return null;
		}
		
		LocalDate dateObj = null;
		try {
			dateObj = LocalDate.of(year, month, date);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Please select valid date input", "Error", JOptionPane.ERROR_MESSAGE);
		}
		return dateObj;
	}


	public void clearInputs() {
		this.dateField.setSelectedIndex(0);
		this.monthField.setSelectedIndex(0);
		this.yearField.setSelectedIndex(0);		
	}

}
