package newEntry;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Font;

public class newEntryDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField code_TField;
	private JTextField URL_TField;
	
	
	public newEntryDialog() {
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(null);
		contentPanel.setBounds(0, 0, 434, 238);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel);
		contentPanel.setLayout(null);
		{
			JLabel info_lbl = new JLabel("<html><body>insert new entry by entering the  code (ex. 177013),<br>or entring the full URL.</body></html>");
			info_lbl.setFont(new Font("Tahoma", Font.PLAIN, 15));
			info_lbl.setBounds(10, 11, 414, 38);
			contentPanel.add(info_lbl);
		}
		{
			JLabel code_lbl = new JLabel("code:");
			code_lbl.setBounds(10, 83, 46, 14);
			contentPanel.add(code_lbl);
		}
		{
			code_TField = new JTextField();
			code_TField.setBounds(10, 103, 130, 20);
			contentPanel.add(code_TField);
			code_TField.setColumns(10);
		}
		{
			JLabel URL_lbl = new JLabel("URL:");
			URL_lbl.setBounds(10, 134, 46, 14);
			contentPanel.add(URL_lbl);
		}
		{
			URL_TField = new JTextField();
			URL_TField.setBounds(10, 153, 338, 20);
			contentPanel.add(URL_TField);
			URL_TField.setColumns(10);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setBounds(0, 238, 434, 23);
			getContentPane().add(buttonPane);
			buttonPane.setLayout(null);
			{
				JButton okButton = new JButton("OK");
				okButton.setBounds(0, 0, 100, 23);
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setBounds(334, 0, 100, 23);
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
	
	public String getCode() {
		return code_TField.getText();
	}
	
	public String getURL() {
		return URL_TField.getText();
	}

}
