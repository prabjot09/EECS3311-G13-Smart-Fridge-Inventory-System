package presentationLayer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import domainLayer.ApplicationClock;
import domainLayer.DBProxy;
import domainLayer.UserHistory;
import persistenceLayer.StubDB;
import presentationLayer.swingExtensions.CustomButton;
import presentationLayer.swingExtensions.CustomPanel;
import presentationLayer.swingExtensions.GridConstraintsSpec;

public class HistoryView extends AppFrameView implements ActionListener {
	
	private JButton backButton;
	
	public static void main(String[] args) {
		DBProxy.getInstance().setDB(new StubDB());
		ApplicationClock.initSimulatedClock();
		HistoryView lay = new HistoryView(DBProxy.getInstance().loadUserHistory());
		
		JFrame jframe = new JFrame("Hi");
		jframe.add(lay);
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    jframe.getContentPane().setBackground(Color.black);
	    jframe.setPreferredSize(new Dimension(1140, 600));
	    jframe.pack();
	    jframe.setLocationRelativeTo(null);
	    jframe.setVisible(true);
	}
	
	public HistoryView(UserHistory data) {
		this.setBackground(Color.black);
		this.setLayout(new GridBagLayout());
		
		data.recalibrateHistory();
		
		JPanel titleWrapper = new CustomPanel(Color.black, new FlowLayout(FlowLayout.CENTER));
		JLabel titleLabel = new JLabel("User History");
		titleLabel.setForeground(Color.white);
		titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
		titleLabel.setBorder(BorderFactory.createEmptyBorder(15, 5, 10, 5));
		titleWrapper.add(titleLabel);
		this.add(titleWrapper, GridConstraintsSpec.stretchableFillConstraints(0, 0, 1, 0.1, GridBagConstraints.HORIZONTAL));
		
		JPanel backPanel = new CustomPanel(Color.black, new FlowLayout(FlowLayout.LEFT));
		backPanel.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 15));
		backButton = new CustomButton("Back", this, 10);
		backPanel.add(backButton);
		this.add(backPanel, GridConstraintsSpec.stretchableFillConstraints(0, 1, 1, 0, GridBagConstraints.HORIZONTAL));
		
		JPanel dataWrapper = new CustomPanel(Color.black, new BorderLayout(), 15);
		JPanel dataPanel = new CustomPanel(Color.black, new GridBagLayout());
		dataPanel.setBorder(BorderFactory.createMatteBorder(2, 1, 2, 1, Color.white));
		for (int day = 0; day < 7; day++) {
			HistoryDayComponent dayPanel = new HistoryDayComponent(data, 6-day);
			dataPanel.add(dayPanel, GridConstraintsSpec.stretchableFillConstraints(day, 0, 1, 1, GridBagConstraints.BOTH));
		}
		
		JScrollPane scroll = new JScrollPane(dataPanel);
		scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		dataWrapper.add(scroll);
		this.add(dataWrapper, GridConstraintsSpec.stretchableFillConstraints(0, 2, 1, 0.9, GridBagConstraints.BOTH));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == backButton) {
			AppWindow.getWindow().loadPreviousWindow();
		}		
	}
}
