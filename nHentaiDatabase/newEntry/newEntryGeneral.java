package newEntry;

import javax.swing.JPanel;
import javax.swing.JTextField;import javax.swing.UIManager;
import javax.swing.filechooser.FileSystemView;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JCheckBox;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.awt.event.ActionEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.JScrollPane;
import java.awt.Color;

public class newEntryGeneral extends JPanel {

	private JTextField code_TField;
	private JTextField URL_TField;
	private JComboBox rating_CBox;
	private JTextArea textArea;
	private JComboBox status_CBox;
	private JCheckBox insertMultipleId_ChBox;
	
	private JFileChooser myJFileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
	private String fileLocation;
	
	private String[][] Data;
	
	/**
	 * Create the panel.
	 */
	public newEntryGeneral() {
		setBackground(new Color(35, 35, 35));
		setLayout(null);
		
		JLabel lblNewLabel = new JLabel("<html><body>insert new entry by entering the  code (ex. 177013),<br>or entring the full URL.</body></html>");
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel.setBounds(10, 11, 549, 50);
		add(lblNewLabel);
		
		JLabel code_lbl = new JLabel("code:");
		code_lbl.setForeground(Color.WHITE);
		code_lbl.setBounds(10, 83, 46, 14);
		add(code_lbl);
		
		code_TField = new JTextField();
		code_TField.setBounds(10, 103, 130, 20);
		add(code_TField);
		code_TField.setColumns(10);
		
		JLabel URL_lbl = new JLabel("URL:");
		URL_lbl.setForeground(Color.WHITE);
		URL_lbl.setBounds(10, 134, 46, 14);
		add(URL_lbl);
		
		URL_TField = new JTextField();
		URL_TField.setBounds(10, 153, 338, 20);
		add(URL_TField);
		URL_TField.setColumns(10);
		
		JLabel rating_lbl = new JLabel("rating:");
		rating_lbl.setForeground(Color.WHITE);
		rating_lbl.setBounds(10, 205, 46, 14);
		add(rating_lbl);
		
		rating_CBox = new JComboBox();
		rating_CBox.setModel(new DefaultComboBoxModel(new String[] {"N/A", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"}));
		rating_CBox.setBounds(10, 228, 60, 24);
		add(rating_CBox);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setEnabled(false);
		scrollPane.setBounds(10, 366, 359, 123);
		add(scrollPane);
		
		textArea = new JTextArea();
		textArea.setEnabled(false);
		textArea.setBounds(10, 366, 359, 83);
		scrollPane.setViewportView(textArea);
		
		JLabel infoMultipleCodes_lbl = new JLabel("<html><body>insert the codes in the text area below.<br>Each row represents a new entry in the Database.<br>You can also import a .txt file. (rating after the code with a space between)</body></html>");
		infoMultipleCodes_lbl.setForeground(Color.WHITE);
		infoMultipleCodes_lbl.setEnabled(false);
		infoMultipleCodes_lbl.setBounds(10, 284, 338, 50);
		add(infoMultipleCodes_lbl);
		
		JButton importFromFile_btn = new JButton("import from .txt");
		importFromFile_btn.setEnabled(false);
		importFromFile_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				myJFileChooser.setDialogTitle("Choose a directory to save your file: ");
        		myJFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            	int returnValue = myJFileChooser.showOpenDialog(null);
            	
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = myJFileChooser.getSelectedFile();
                    System.out.println(selectedFile.getAbsolutePath());
                    fileLocation = selectedFile.getAbsolutePath();
                    importTxtInTField();
                }
			}
		});
		importFromFile_btn.setBounds(260, 332, 109, 23);
		add(importFromFile_btn);
		
		insertMultipleId_ChBox = new JCheckBox("use multiple codes");
		insertMultipleId_ChBox.setBackground(new Color(35, 35, 35));
		insertMultipleId_ChBox.setForeground(Color.WHITE);
		insertMultipleId_ChBox.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if(insertMultipleId_ChBox.isSelected()) {
					textArea.setEnabled(true);
					infoMultipleCodes_lbl.setEnabled(true);
					importFromFile_btn.setEnabled(true);
					importFromFile_btn.setEnabled(true);
					scrollPane.setEnabled(true);
				}else {
					textArea.setEnabled(false);
					infoMultipleCodes_lbl.setEnabled(false);
					importFromFile_btn.setEnabled(false);
					importFromFile_btn.setEnabled(false);
					scrollPane.setEnabled(false);
				}
			}
		});
		insertMultipleId_ChBox.setBounds(6, 259, 122, 23);
		add(insertMultipleId_ChBox);
		
		JLabel status_lbl = new JLabel("status:");
		status_lbl.setForeground(Color.WHITE);
		status_lbl.setBounds(195, 205, 46, 14);
		add(status_lbl);
		
		status_CBox = new JComboBox();
		status_CBox.setBackground(Color.DARK_GRAY);
		status_CBox.setModel(new DefaultComboBoxModel(new String[] {"plan to read", "reading", "completed"}));
		status_CBox.setBounds(195, 228, 84, 24);
		add(status_CBox);
		
		
	}
	
	private void importTxtInTField() {
		
		try {
			File myObj = new File(fileLocation);
			Scanner myReader = new Scanner(myObj);
			
			int rows = 0;
			while (myReader.hasNextLine()) {
		        String data = myReader.nextLine();
		        rows++;
			}
			
			Data = new String[rows][2];
			myReader = new Scanner(myObj);
			int index = 0;
			while (myReader.hasNextLine()) {
		        String data = myReader.nextLine();
		        Data[index][0] = data;
		        Data[index][1] = " \n";
		        index++;
			}
			
			for(int i=0;i<Data.length;i++){
				textArea.append(Data[i][0]);
				textArea.append(Data[i][1]);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
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
	
	public String getStatus() {
		return (String)status_CBox.getSelectedItem();
	}
	
	public boolean getSelected() {
		return insertMultipleId_ChBox.isSelected();
	}
	
	public String[] getDataInTextArea(){
		String[] lowData = new String[Data.length];
		String rawData = textArea.getText();
		char[] rawDataChar = rawData.toCharArray();
		int index = 0;
		for(int i=0;i<rawDataChar.length;i++) {
			if(rawDataChar[i] == '\n'){
				index++;
			}else {
				if(lowData[index] == null)
					lowData[index] = ""+rawDataChar[i];
				else
					lowData[index] = lowData[index] + rawDataChar[i];
			}
		}
		return lowData;
	}
}
