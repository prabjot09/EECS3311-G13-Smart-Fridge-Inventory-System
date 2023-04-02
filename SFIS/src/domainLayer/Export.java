package domainLayer;

import java.io.File;
import java.io.PrintWriter;
import java.util.List;
import javax.swing.JOptionPane;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.DoubleBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TextAlignment;

public class Export {
	
	private List<StoredItem> items;
	
	public Export(List<StoredItem> items, File directory) {
		this.items = items;
		
		try {
			String outputPath = directory.toString() + "/GroceryList_" + ApplicationClock.getDate().toString() + ".pdf";
			PdfWriter writer = new PdfWriter(outputPath);
			PdfDocument pdfDoc = new PdfDocument(writer);
			Document doc = new Document(pdfDoc);
			
			Paragraph title = new Paragraph("Grocery List - " + ApplicationClock.getDate().toString());
			title.setBold();
			title.setFontSize(30F);
			title.setTextAlignment(TextAlignment.CENTER);
			doc.add(title);
			
			doc.add(new Paragraph());
			
			float[] columnWidths = {20F, 200F};
			Table table = new Table(columnWidths);
			table.setHorizontalAlignment(HorizontalAlignment.CENTER);
			table.setBorder(new DoubleBorder(5));
			
			for (StoredItem item : this.items) {
				Cell c1 = new Cell();
				c1.add(new Paragraph(" - "));
				c1.setBorder(Border.NO_BORDER);
				c1.setTextAlignment(TextAlignment.CENTER);
				table.addCell(c1);
				
				Cell c2 = new Cell();
				c2.add(new Paragraph(item.getFoodItem().getName()));
				c2.setBorder(Border.NO_BORDER);
				c2.setTextAlignment(TextAlignment.CENTER);
				table.addCell(c2);
			} 
			
			doc.setBorder(new DoubleBorder(5));
			
			doc.add(table);
			doc.close();
			JOptionPane.showMessageDialog(null, "Export to file complete");
		}
		catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Could not output file", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
}
