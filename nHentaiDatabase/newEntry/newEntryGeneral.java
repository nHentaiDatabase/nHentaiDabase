package newEntry;

import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.ListCellRenderer;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileSystemView;
import javax.swing.plaf.FileChooserUI;
import javax.swing.plaf.basic.BasicArrowButton;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.plaf.basic.BasicScrollBarUI;

import com.formdev.flatlaf.FlatDarkLaf;

import moreInformation.moreInformationPanel;
import outsourcedClasses.Methods;
import renderEngine.colorComboBoxPopUp;
import renderEngine.renderMethods;

import javax.accessibility.AccessibleContext;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;

import java.awt.Font;
import java.awt.Frame;
import java.awt.HeadlessException;
import java.awt.Point;

import javax.swing.JCheckBox;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.awt.event.ActionEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.event.ChangeEvent;
import javax.swing.JScrollPane;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Dimension;
import javax.swing.JProgressBar;

public class newEntryGeneral extends JPanel {
	public newEntryGeneral() {
	}

	private JTextField code_TField;
	private JTextField URL_TField;
	private JComboBox rating_CBox;
	private JTextArea textArea;
	private JComboBox status_CBox;
	private JCheckBox insertMultipleId_ChBox;
	private Point mouseCoord;
	private String fileLocation;
	
	private String[][] Data;
	
	private Methods methods = new Methods();
	private renderMethods RenderMethods = new renderMethods();
	
	/**
	 * Create the panel.
	 */
	public newEntryGeneral(String start) {
		setBackground(new Color(35, 35, 35));
		setLayout(null);
		
		JLabel lblNewLabel = new JLabel("<html><body>insert new entry by entering the  code (ex. 177013),<br>or entring the full URL.</body></html>");
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel.setBounds(10, 22, 549, 50);
		add(lblNewLabel);
		
		JLabel code_lbl = new JLabel("code:");
		code_lbl.setForeground(Color.WHITE);
		code_lbl.setBounds(10, 83, 46, 14);
		add(code_lbl);
		
		code_TField = new JTextField();
		code_TField.setForeground(Color.WHITE);
		code_TField.setBackground(new Color(59, 59, 59));
		code_TField.setBounds(10, 103, 130, 20);
		code_TField.setBorder(null);
		code_TField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		add(code_TField);
		code_TField.setColumns(10);
		
		JLabel URL_lbl = new JLabel("URL:");
		URL_lbl.setForeground(Color.WHITE);
		URL_lbl.setBounds(10, 134, 46, 14);
		add(URL_lbl);
		
		URL_TField = new JTextField();
		URL_TField.setForeground(Color.WHITE);
		URL_TField.setBackground(new Color(59, 59, 59));
		URL_TField.setBounds(10, 153, 338, 20);
		URL_TField.setBorder(null);
		add(URL_TField);
		URL_TField.setColumns(10);
		
		JLabel rating_lbl = new JLabel("rating:");
		rating_lbl.setForeground(Color.WHITE);
		rating_lbl.setBounds(10, 205, 46, 14);
		add(rating_lbl);
		
		rating_CBox = new JComboBox();
		rating_CBox.addPopupMenuListener(new PopupMenuListener() {
        	public void popupMenuCanceled(PopupMenuEvent e) {
        	}
        	public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
        	}
        	public void popupMenuWillBecomeVisible(PopupMenuEvent e)
     	   {
        		RenderMethods.handlePopupMenuWillBecomeVisible(e);
     	   }
        });
        rating_CBox.setUI(new BasicComboBoxUI(){
        	   @Override
        	   protected JButton createArrowButton() {
        	       JButton arrowButton = new BasicArrowButton( 
        	        BasicArrowButton.SOUTH,
        	        new Color(40, 40, 40), 
        	        new Color(59, 59, 59),
        	        new Color(114, 118, 125),
        	        new Color(59, 59, 59));
        	       arrowButton.setBorder(BorderFactory.createLineBorder(new Color(59, 59, 59)));
        	       return arrowButton;
        	    }});
        rating_CBox.setRenderer(new renderEngine.colorComboBoxPopUp());
        rating_CBox.setBackground(new Color(59, 59, 59));
        rating_CBox.setForeground(Color.WHITE);
		rating_CBox.setModel(new DefaultComboBoxModel(new String[] {"N/A", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"}));
		rating_CBox.setBounds(10, 228, 60, 24);
		add(rating_CBox);
		
		JScrollPane scrollPane = new JScrollPane();
		JScrollBar scrollBar = scrollPane.getVerticalScrollBar();;
        Dimension scrollBarDim = new Dimension(15, scrollBar
              .getPreferredSize().height);
        scrollBar.setPreferredSize(scrollBarDim);
        scrollBar.setUI(new BasicScrollBarUI() {
           @Override 
           protected void configureScrollBarColors(){
               this.thumbColor = new Color(10, 10, 10);
               this.thumbDarkShadowColor = new Color(10, 10, 10);
               this.thumbLightShadowColor = new Color(10, 10, 10);
               this.trackColor = new Color(59, 59, 59);
               this.trackHighlightColor = new Color(59, 59, 59);
           }
          @Override
         protected JButton createDecreaseButton(int orientation) {
             return methods.createZeroButton();
         }

         @Override
         protected JButton createIncreaseButton(int orientation) {
             return methods.createZeroButton();
         }
       });
		scrollPane.setEnabled(false);
		scrollPane.setBounds(10, 366, 359, 123);
		add(scrollPane);
		
		textArea = new JTextArea();
		textArea.setForeground(Color.WHITE);
		textArea.setBackground(Color.DARK_GRAY);
		textArea.setEnabled(false);
		textArea.setBounds(10, 366, 359, 83);
		scrollPane.setViewportView(textArea);
		
		JLabel infoMultipleCodes_lbl = new JLabel("<html><body>insert the codes in the text area below.<br>Each row represents a new entry in the Database.<br>You can also import a .txt file. (rating after the code with a space between)</body></html>");
		infoMultipleCodes_lbl.setForeground(Color.WHITE);
		infoMultipleCodes_lbl.setEnabled(false);
		infoMultipleCodes_lbl.setBounds(10, 284, 338, 50);
		add(infoMultipleCodes_lbl);
		
		JButton importFromFile_btn = new JButton();
		importFromFile_btn.setEnabled(false);
		importFromFile_btn.setIcon(new ImageIcon(newEntryGeneral.class.getResource("/grafics/newEntryButtons/importFromTxt.png")));
		importFromFile_btn.setHorizontalTextPosition(SwingConstants.CENTER);
		importFromFile_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				FlatDarkLaf.install();
				
				JFileChooser myJFileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
				
				myJFileChooser.setDialogTitle("Choose a directory to save your file");
        		myJFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            	int returnValue = myJFileChooser.showOpenDialog(null);
            	
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = myJFileChooser.getSelectedFile();
                    System.out.println(selectedFile.getAbsolutePath());
                    fileLocation = selectedFile.getAbsolutePath();
                    importTxtInTField(fileLocation);
                }
                
    			try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
						| UnsupportedLookAndFeelException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});
		importFromFile_btn.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent evt) {
				importFromFile_btn.setIcon(new ImageIcon(newEntryGeneral.class.getResource("/grafics/newEntryButtons/importFromTxtHover.png")));
			}

			public void mouseExited(MouseEvent evt) {
				importFromFile_btn.setIcon(new ImageIcon(newEntryGeneral.class.getResource("/grafics/newEntryButtons/importFromTxt.png")));
			}

			public void mousePressed(MouseEvent evt) {
				importFromFile_btn.setIcon(new ImageIcon(newEntryGeneral.class.getResource("/grafics/newEntryButtons/importFromTxtSelected.png")));
			}

			public void mouseReleased(MouseEvent evt) {
				importFromFile_btn.setIcon(new ImageIcon(newEntryGeneral.class.getResource("/grafics/newEntryButtons/importFromTxtHover.png")));
			}
		});
		importFromFile_btn.setBounds(214, 332, 155, 23);
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
		status_CBox.addPopupMenuListener(new PopupMenuListener() {
        	public void popupMenuCanceled(PopupMenuEvent e) {
        	}
        	public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
        	}
        	public void popupMenuWillBecomeVisible(PopupMenuEvent e)
     	   {
        		RenderMethods.handlePopupMenuWillBecomeVisible(e);
     	   }
        });
        status_CBox.setUI(new BasicComboBoxUI(){
        	   @Override
        	   protected JButton createArrowButton() {
        	       JButton arrowButton = new BasicArrowButton( 
        	        BasicArrowButton.SOUTH,
        	        new Color(40, 40, 40), 
        	        new Color(59, 59, 59),
        	        new Color(114, 118, 125),
        	        new Color(59, 59, 59));
        	       arrowButton.setBorder(BorderFactory.createLineBorder(new Color(59, 59, 59)));
        	       return arrowButton;
        	    }});
        status_CBox.setRenderer(new renderEngine.colorComboBoxPopUp());
        status_CBox.setBackground(new Color(59, 59, 59));
        status_CBox.setForeground(Color.WHITE);
		status_CBox.setModel(new DefaultComboBoxModel(new String[] {"plan to read", "reading", "completed"}));
		status_CBox.setSelectedItem(start);
		status_CBox.setBounds(195, 228, 84, 24);
		add(status_CBox);
		
		code_TField.requestFocus();
	}
	
	/**
	 * Import the txt file with the Id's in the TextArrea.
	 * 
	 * @param TXTFileLocation The location of the txt file
	 */
	public void importTxtInTField(String TXTFileLocation) {
		
		try {
			File myObj = new File(TXTFileLocation);
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
	
	/**
	 * get the Id/Code
	 * @return String
	 */
	public String getCode() {
		return code_TField.getText();
	}
	
	/**
	 * get the URL
	 * @return String
	 */
	public String getURL() {
		return URL_TField.getText();
	}
	
	/**
	 * get the rating
	 * @return String
	 */
	public String getRating() {
		return (String)rating_CBox.getSelectedItem();
	}
	
	/**
	 * get the Status
	 * @return String
	 */
	public String getStatus() {
		return (String)status_CBox.getSelectedItem();
	}
	
	/**
	 * get the FileLocation
	 * @return String
	 */
	public String getFileLocation() {
		return fileLocation;
	}
	
	/**
	 * get if more than one Id was entered
	 * @return boolean
	 */
	public boolean getSelected() {
		return insertMultipleId_ChBox.isSelected();
	}
	
	/**
	 * Reads and formats the Data in the text area.
	 * 
	 * @return String[]
	 */
	public String[] getDataInTextArea(){
		String[] lowData = new String[1];
		String rawData = textArea.getText();
		char[] rawDataChar = rawData.toCharArray();
		int index = 0;
		for(int i=0;i<rawDataChar.length;i++) {
			if(rawDataChar[i] == '\n'){
				index++;
			}else {
				if(index == 0 && lowData[index] == null) {
					lowData[index] = ""+rawDataChar[i];
					lowData = expandArr(lowData);
				}
				else if(index != 0 && lowData[index] == null) {
					lowData[index] = ""+rawDataChar[i];
					lowData = expandArr(lowData);
				}
				else
					lowData[index] = lowData[index] + rawDataChar[i];
			}
		}
		String[] tmp = new String[lowData.length-1];
		System.arraycopy(lowData, 0, tmp, 0, lowData.length-1);
		return tmp;
	}
	
	/**
	 * Expands the Array with one index.
	 * 
	 * @param input the input Array
	 * @return String[]
	 */
	public String[] expandArr(String[] input) {
		String[] tmp = new String[input.length + 1];
		System.arraycopy(input, 0, tmp, 0, input.length);
		input = tmp;
		return input;
	}
}
