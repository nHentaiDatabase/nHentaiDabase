package outsourcedClasses;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import moreInformation.moreInformationPanel;
import nHentaiWebScaper.nHentaiWebBase;

public class Methods {

	private String appdataLocation;
	private String mainFolderLocation;
	private String photoFolderLocation;
	private String Slash;
	private String randomPhotoFolderLocation;
	private String userDataFolderLocation;
	
	private nHentaiWebBase nHentaiAPI;
	
	/**
	 * Constructor of the class Method.
	 * The class Method contains Methods for the nHentaiDatabase
	 * 
	 * @param AL is the AppdataLocation	
	 * @param MFL is the mainFolderLocation
	 * @param PFL photoFolderLocation
	 * @param S	the type of Slash
	 * @param RPFL is the randomFolderLocation
	 * @param UDFL is the userDataFolderLocation
	 */
	public Methods(String AL, String MFL, String PFL, String S, String RPFL, String UDFL) {
		appdataLocation = AL;
		mainFolderLocation = MFL;
		photoFolderLocation = PFL;
		Slash = S;
		randomPhotoFolderLocation = RPFL;
		userDataFolderLocation = UDFL;
		
		nHentaiAPI = new nHentaiWebBase();
	}
	
	public Methods() {
		
	}
	/**
	 * Expands the Array with one index.
	 * 
	 * @param input the input Array
	 * @return String[][]
	 */
	public String[][] expandArr(String[][] input) {
		String[][] tmp = new String[input.length + 1][input[0].length];
		System.arraycopy(input, 0, tmp, 0, input.length);
		input = tmp;
		return input;
	}
	
	/**
	 * Deletes the index and rearranges the Array.
	 * 
	 * @param input the input Array
	 * @param index	the deleted index
	 * @return String[][]
	 */
	public String[][] rearangeArr(String[][] input, int index) {
		String[][] tmp = new String[input.length - 1][input[0].length];
		for (int i = 0; i < index; i++) {
			for (int j = 0; j < input[0].length; j++) {
				tmp[i][j] = input[i][j];
			}
		}
		for (int i = index + 1; i < input.length; i++) {
			for (int j = 0; j < input[0].length; j++) {
				tmp[i - 1][j] = input[i][j];
			}
		}
		return tmp;
	}
	
	/**
	 * Takes an Array and a DefaultTableModel with it.
	 * 
	 * @param inputModel the existing DefaultTableModel
	 * @param inputArr the source Array
	 * @param SFW (safe for work) decides with pictures are used
	 * @return DefaultTableModel
	 */
	public DefaultTableModel ArrToTable(DefaultTableModel inputModel, String[][] inputArr, boolean SFW) {
		for (int i = 0; i < inputArr.length - 1; i++) {
			Object[] tmp = new Object[inputArr[0].length];
			for (int j = 0; j < inputArr[0].length; j++) {
				tmp[j] = inputArr[i][j];
			}
			String Id = inputArr[i][2];
			tmp[0] = String.valueOf(i + 1);
			checkOneImage(Id, inputArr[i][1]);
			Icon img;
			if(SFW == false) {
				img = new ImageIcon(appdataLocation + mainFolderLocation + photoFolderLocation + Slash + Id + "_low.jpg");			
			}else {
				int random = (int)(Math.random()*200);
				img = new ImageIcon(appdataLocation + mainFolderLocation + randomPhotoFolderLocation + Slash + random + "_low.jpg");
			}
			tmp[1] = img;
			inputModel.addRow(tmp);
		}
		return inputModel;
	}
	
	/**
	 * expands a existing DefaultTableModel with the last valid index of an Array.
	 * 
	 * @param tableArr the source Array
	 * @param inputModel the existing DefaultTableModel
	 * @param Id the id of the entry (for Picture detection)
	 * @param SFW (safe for work) decides with pictures are used
	 * @return DefaultTableModel
	 */
	public DefaultTableModel expandTable(String[][] tableArr, DefaultTableModel inputModel, String Id, boolean SFW) {
		Object[] tmp = new Object[tableArr[0].length];
		for (int j = 0; j < tableArr[0].length; j++) {
			tmp[j] = tableArr[tableArr.length - 2][j];
		}
		tmp[0] = String.valueOf(tableArr.length - 1);
		Icon img;
		if(SFW == false) {
			img = new ImageIcon(appdataLocation + mainFolderLocation + photoFolderLocation + Slash + Id + "_low.jpg");			
		}else {
			int random = (int)(Math.random()*200);
			img = new ImageIcon(appdataLocation + mainFolderLocation + randomPhotoFolderLocation + Slash + random + "_low.jpg");
		}
		tmp[1] = img;
		inputModel.addRow(tmp);
		return inputModel;
	}
	
	/**
	 * Checks if a image with a specific Id already exists.
	 * Downloads the image when false.
	 * 
	 * @param Id the search ID
	 * @param URL the URL of the picture. Only used when picture isnt there.
	 */
	public void checkOneImage(String Id, String URL) {
		String MainLocation = appdataLocation + mainFolderLocation + photoFolderLocation + Slash + Id ;
		File f = new File(MainLocation + "_original.jpg");
		if(!f.exists()) {
			nHentaiAPI.saveImageAsFile(URL, MainLocation + "_original.jpg");
		}
		f = new File(MainLocation + "_medium.jpg");
		if(!f.exists()) {
			scaleImage(MainLocation + "_original.jpg", MainLocation,  "_medium.jpg", 150, 212);
		}
		f = new File(MainLocation + "_low.jpg");
		if(!f.exists()) {
			scaleImage(MainLocation + "_original.jpg", MainLocation, "_low.jpg", 50, 71);
		}
	}
	
	/**
	 * Scales an Image to the preferred size.
	 * 
	 * @param locationOriginal Where the original picture is located
	 * @param locationNew Where the scaled image should be saved
	 * @param name The name if the new image
	 * @param x The x parameter for the scaled image
	 * @param y The y parameter for the scaled image
	 */
	public void scaleImage(String locationOriginal, String locationNew, String name, int x, int y) {
		try {
			ImageIcon ii = new ImageIcon(locationOriginal);
			BufferedImage bi = new BufferedImage(x, y, BufferedImage.TYPE_INT_RGB);
			Graphics2D g2d = (Graphics2D) bi.createGraphics();
			g2d.addRenderingHints(
					new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY));
			boolean b = g2d.drawImage(ii.getImage(), 0, 0, x, y, null);
			System.out.println(b);
			ImageIO.write(bi, "jpg", new File(locationNew + name));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Sets up the folder structure.
	 * 
	 * @param appData The source folder for the new Data
	 */
	public void setUpAppData(String appData) {
		new File(appData + mainFolderLocation).mkdirs();
		new File(appData + mainFolderLocation + photoFolderLocation).mkdirs();
		new File(appData + mainFolderLocation + userDataFolderLocation).mkdirs();
		new File(appData + mainFolderLocation + randomPhotoFolderLocation).mkdirs();
	}
	
	/**
	 * Downloads and saves random pictures for the SFW mode
	 */
	public void setUpRandomPhotos() {
		for(int i=0;i<200;i++) {
			File f = new File(appdataLocation + mainFolderLocation + randomPhotoFolderLocation + Slash + i + "_medium.jpg");
			if(!f.exists()) {
				nHentaiAPI.saveImageAsFile("https://picsum.photos/150/212", appdataLocation + mainFolderLocation + randomPhotoFolderLocation + Slash + i + "_medium.jpg");
				scaleImage(appdataLocation + mainFolderLocation + randomPhotoFolderLocation + Slash + i + "_medium.jpg", appdataLocation + mainFolderLocation + randomPhotoFolderLocation + Slash + i, "_low.jpg", 50, 71);
			}
		}
	}
	
	/**
	 * Deletes all stored Data when needed.
	 * 
	 * @param path The Directory that should be deleted
	 * @throws IOException
	 */
	public void deleteDirectoryRecursion(Path path) throws IOException {
		  if (Files.isDirectory(path, LinkOption.NOFOLLOW_LINKS)) {
		    try (DirectoryStream<Path> entries = Files.newDirectoryStream(path)) {
		      for (Path entry : entries) {
		        deleteDirectoryRecursion(entry);
		      }
		    }
		  }
		  Files.delete(path);
	}
	
	/**
	 * Reloads the image of a DefaultTableModel with a new one.
	 * 
	 * @param index The row that should be reloaded
	 * @param table	The source DefaultTableModel
	 * @param Arr The source Array with all the necessary information
	 * @param Id The id for the image when SFW == false
	 * @param SFW (safe for work) decides with pictures are used
	 * @return DefaultTableModel
	 */
	public DefaultTableModel reloadRowImage(int index, DefaultTableModel table, String[][] Arr, String Id, boolean SFW) {			
		
		Icon img;
		if(SFW == false) {
			img = new ImageIcon(appdataLocation + mainFolderLocation + photoFolderLocation + Slash + Id + "_low.jpg");			
		}else {
			int random = (int)(Math.random()*200);
			img = new ImageIcon(appdataLocation + mainFolderLocation + randomPhotoFolderLocation + Slash + random + "_low.jpg");
		}
		
		Object[] data;
		
			data = new Object[8];
			for(int i=0;i<8;i++) {
				data[i] = Arr [index][i];
			}
			data[0] = index+1;
			data[1] = img;
			table = updateRow(table, index, data);
			
		return table;
	}
	
	/**
	 * updates one Row with new given Data
	 * 
	 * @param inputModel The source DefaultTableModel
	 * @param index The index of the row
	 * @param values The new values for the row
	 * @return DefaultTableModel
	 */
	public DefaultTableModel updateRow(DefaultTableModel inputModel, int index,Object[] values)
    {
        for (int i = 0 ; i < values.length ; i++)
        {
        	inputModel.setValueAt(values[i],index,i);
        }
        return inputModel;
    }
	
	/**
	 * Returns the mother OptionPane of a parent JComponend
	 * 
	 * @param parent The JComponend
	 * @return JOptionPane
	 */
	public JOptionPane getOptionPane(JComponent parent) {
        JOptionPane pane = null;
        if (!(parent instanceof JOptionPane)) {
            pane = getOptionPane((JComponent)parent.getParent());
        } else {
            pane = (JOptionPane) parent;
        }
        return pane;
    }
	
	/**
	 * Creates a jButton with no attributes.
	 * 
	 * @return JButton
	 */
	public JButton createZeroButton() {
	    JButton button = new JButton("zero button");
	    Dimension zeroDim = new Dimension(0,0);
	    button.setPreferredSize(zeroDim);
	    button.setMinimumSize(zeroDim);
	    button.setMaximumSize(zeroDim);
	    return button;
	}
	
	/**
	 * Edits the index of the given Array.
	 * note: not the actual ArrayIndex. The index displayed by the table.
	 * 
	 * @param inputArr The source Array
	 * @return String[][]
	 */
	public String[][] newArrIndex(String[][] inputArr){
		for(int i=0;i<inputArr.length-1;i++) {
			inputArr[i][0] = String.valueOf(i+1);
		}
		return inputArr;
	}
	
	/**
	 * Creates a Component Array with two types of buttons.
	 * The buttons are build for a JOptionPane.
	 * 
	 * @return Component[]
	 */
	public Component[] OKCancelButtonCreate() {
		final JButton OKButton = new JButton();
		OKButton.setPreferredSize(new Dimension(75,25));
		OKButton.setIcon(new ImageIcon(moreInformationPanel.class.getResource("/grafics/settings/OK.png")));
		OKButton.setHorizontalTextPosition(SwingConstants.CENTER);
		OKButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane paneAP = getOptionPane((JComponent)e.getSource());
				paneAP.setValue("OK");
				Window w = SwingUtilities.getWindowAncestor(OKButton);

			    if (w != null) {
			      w.setVisible(false);
			    }
			}
			
		});
		OKButton.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent evt) {
				OKButton.setIcon(new ImageIcon(moreInformationPanel.class.getResource("/grafics/settings/OKHover.png")));
			}

			public void mouseExited(MouseEvent evt) {
				OKButton.setIcon(new ImageIcon(moreInformationPanel.class.getResource("/grafics/settings/OK.png")));
			}

			public void mousePressed(MouseEvent evt) {
				OKButton.setIcon(new ImageIcon(moreInformationPanel.class.getResource("/grafics/settings/OKSelected.png")));
			}

			public void mouseReleased(MouseEvent evt) {
				OKButton.setIcon(new ImageIcon(moreInformationPanel.class.getResource("/grafics/settings/OKHover.png")));
			}
		});
		
		final JButton cancelButton = new JButton();
		cancelButton.setPreferredSize(new Dimension(58,25));
		cancelButton.setIcon(new ImageIcon(moreInformationPanel.class.getResource("/grafics/close/cancel.png")));
		cancelButton.setHorizontalTextPosition(SwingConstants.CENTER);
		cancelButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane paneAP = getOptionPane((JComponent)e.getSource());
				paneAP.setValue("cancel");
				Window w = SwingUtilities.getWindowAncestor(cancelButton);

			    if (w != null) {
			      w.setVisible(false);
			    }
			}
			
		});
		cancelButton.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent evt) {
				cancelButton.setIcon(new ImageIcon(moreInformationPanel.class.getResource("/grafics/close/cancelHover.png")));
			}

			public void mouseExited(MouseEvent evt) {
				cancelButton.setIcon(new ImageIcon(moreInformationPanel.class.getResource("/grafics/close/cancel.png")));
			}

			public void mousePressed(MouseEvent evt) {
				cancelButton.setIcon(new ImageIcon(moreInformationPanel.class.getResource("/grafics/close/cancelSelected.png")));
			}

			public void mouseReleased(MouseEvent evt) {
				cancelButton.setIcon(new ImageIcon(moreInformationPanel.class.getResource("/grafics/close/cancelHover.png")));
			}
		});
		
		Component[] buttonText = new Component[]{	OKButton, cancelButton};
		return buttonText;
	}
	
	/**
	 * Split the single String in a Array.
	 * 
	 * @param tags Single String of tags
	 * @return String[]
	 */
	public String[] splitTagsUp(String tags) {
		char[] tagsChar = tags.toCharArray();
		int tagNumber = 0;
		for(int i=0;i<tagsChar.length;i++) {
			if(tagsChar[i] == ',') {
				tagNumber++;
			}
		}
		String[] tagsArr = new String[tagNumber];
		int currTag = 0;
		for(int i=0;i<tagsChar.length;i++) {
			if(tagsChar[i] == ',' && currTag+1<tagsArr.length)
				currTag++;
			else {
				if(tagsArr[currTag] == null)
					tagsArr[currTag] = "" + tagsChar[i];
				else
					tagsArr[currTag] = tagsArr[currTag] + tagsChar[i];				
			}
		}
		for(int i=1;i<tagsArr.length;i++) {
			String tmp = tagsArr[i];
			StringBuilder sb = new StringBuilder(tmp);
			sb.deleteCharAt(0);
			tmp = sb.toString();
			tagsArr[i] = tmp;
		}
		return tagsArr;
	}
}
