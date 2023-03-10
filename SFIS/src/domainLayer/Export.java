package domainLayer;

import java.io.PrintWriter;
import java.util.List;
import javax.swing.JOptionPane;

public class Export {
	
	private List<StoredItem> items;
	
	public Export(List<StoredItem> items) {
		this.items = items;
		StringBuilder outputBuffer;
		
		try {
			//Open printer to create or overwrite file 
			PrintWriter writeOut = new PrintWriter("GroceryList.txt", "UTF-8");
			writeOut.println("Grocery List");
			//Iterate through given list to print out names
			for (StoredItem item : this.items) {
				outputBuffer = new StringBuilder();
				outputBuffer.append("- ");
				outputBuffer.append(item.getFoodItem().getName());
				writeOut.println(outputBuffer);
			} 
			//Close printer
			writeOut.close();
			JOptionPane.showMessageDialog(null, "Export to file complete");
		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Could not output file", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
}
