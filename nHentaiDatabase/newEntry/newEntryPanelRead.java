package newEntry;

import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class newEntryPanelRead extends JPanel {

	private JTextField code_TField;
	private JTextField URL_TField;
	JComboBox rating_CBox;
	/**
	 * Create the panel.
	 */
	public newEntryPanelRead() {
		setLayout(null);
		
		JLabel info_lbl = new JLabel("<html><body>insert new entry by entering the  code (ex. 177013),<br>or entring the full URL.</body></html>");
		info_lbl.setFont(new Font("Tahoma", Font.PLAIN, 15));
		info_lbl.setBounds(10, 11, 414, 38);
		add(info_lbl);
		
		JLabel code_lbl = new JLabel("code:");
		code_lbl.setBounds(10, 83, 46, 14);
		add(code_lbl);
		
		code_TField = new JTextField();
		code_TField.setBounds(10, 103, 130, 20);
		add(code_TField);
		code_TField.setColumns(10);
		
		JLabel URL_lbl = new JLabel("URL:");
		URL_lbl.setBounds(10, 134, 46, 14);
		add(URL_lbl);
		
		URL_TField = new JTextField();
		URL_TField.setBounds(10, 153, 338, 20);
		add(URL_TField);
		URL_TField.setColumns(10);
		
		JLabel rating_lbl = new JLabel("rating:");
		rating_lbl.setBounds(10, 205, 46, 14);
		add(rating_lbl);
		
		rating_CBox = new JComboBox();
		rating_CBox.setModel(new DefaultComboBoxModel(new String[] {"N/A", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"}));
		rating_CBox.setBounds(10, 228, 60, 24);
		add(rating_CBox);
	}

	public String getCode() {
		return code_TField.getText();
	}
	
	public String getURL() {
		return URL_TField.getText();
	}
	
	public String getRating() {
		return (String)rating_CBox.getSelectedItem();
	}
}
