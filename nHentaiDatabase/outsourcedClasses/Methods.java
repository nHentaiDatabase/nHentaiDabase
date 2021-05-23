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
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
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

import org.imgscalr.Scalr;

import SaveObj.doujinObj;
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
		
		
		BufferedImage bi = null;
		try {
			bi = ImageIO.read(new File(locationOriginal));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		BufferedImage rezise = Scalr.resize(bi, Scalr.Method.QUALITY, x, y, Scalr.OP_ANTIALIAS);
		try {
			ImageIO.write(rezise, "jpg", new File(locationNew + name));
		} catch (IOException e) {
			// TODO Auto-generated catch block
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
			if(tagsChar[i] == ',') {
				if(currTag+1<tagsArr.length)
					currTag++;
			}
			else {
				if(tagsArr[currTag] == null) {
					if(tagsChar[i] != ' ')
						tagsArr[currTag] = "" + tagsChar[i];
				}
				else
					tagsArr[currTag] = tagsArr[currTag] + tagsChar[i];				
			}
		}
		return tagsArr;
	}
	
	/**
	 * Checks if a new Entry already exists in the given Array.
	 * 
	 * @param Arr The Array to search in
	 * @param id the code of the new Entry
	 * @return int the position of the same Entry (When there is no double Entry it returns -1)
	 */
	public int checkIfIdExists(String[][] Arr, String id) {
		int index = -1;

		for(int i=0;i<Arr.length-1;i++) {
			if(id.equals(Arr[i][2])) {
				index = i;
			}
		}
		return index;
	}
	
	/**
	 * Extract the id from the URL
	 * 
	 * @param URL the URL to the doujin
	 * @return the true id
	 */
	public String URLToId(String URL) {
		String tmp = URL;
		StringBuilder sb = new StringBuilder(tmp);
		sb.delete(0, 14);
		sb.deleteCharAt(sb.length()-1);
		tmp = sb.toString();
		return tmp;
	}
	
	/**
	 * Copy the source to the target.
	 * 
	 * @param sourceLocation
	 * @param targetLocation
	 * @throws IOException
	 */
	public static void copyFile(File sourceFile, File destFile)
	        throws IOException {
	    if (!sourceFile.exists()) {
	        return;
	    }
	    if (!destFile.exists()) {
	        destFile.createNewFile();
	    }
	    FileChannel source = null;
	    FileChannel destination = null;
	    source = new FileInputStream(sourceFile).getChannel();
	    destination = new FileOutputStream(destFile).getChannel();
	    if (destination != null && source != null) {
	        destination.transferFrom(source, 0, source.size());
	    }
	    if (source != null) {
	        source.close();
	    }
	    if (destination != null) {
	        destination.close();
	    }

	}
	
	public String[][] insertObjInTableArr(String[][] tableArr, doujinObj Obj){
		tableArr[tableArr.length - 1][1] = Obj.coverImage;
		tableArr[tableArr.length - 1][3] = Obj.title;
		tableArr[tableArr.length - 1][2] = Obj.id;
		for(int i=0;i<Obj.tags.length;i++){
			if(i == 0)
				tableArr[tableArr.length - 1][9] = Obj.tags[i] + ", ";
			else
				tableArr[tableArr.length - 1][9] = tableArr[tableArr.length - 1][9] + Obj.tags[i] + ", ";
		}
		tableArr[tableArr.length - 1][4] = Obj.artist;
		tableArr[tableArr.length - 1][5] = Obj.pages;
		tableArr[tableArr.length - 1][6] = Obj.status;
		tableArr[tableArr.length - 1][8] = Obj.rating;
		tableArr[tableArr.length - 1][7] = "0";
		
		tableArr = expandArr(tableArr);
		return tableArr;
	}
	
	public String[][] insertObjInTableArrReading(String[][] tableArrReading, doujinObj Obj){
		tableArrReading[tableArrReading.length - 1][1] = Obj.coverImage;
		tableArrReading[tableArrReading.length - 1][3] = Obj.title;
		tableArrReading[tableArrReading.length - 1][2] = Obj.id;
		for(int i=0;i<Obj.tags.length;i++){
			if(i == 0)
				tableArrReading[tableArrReading.length - 1][9] = Obj.tags[i] + ", ";
			else
				tableArrReading[tableArrReading.length - 1][9] = tableArrReading[tableArrReading.length - 1][9] + Obj.tags[i] + ", ";
		}
		tableArrReading[tableArrReading.length - 1][4] = Obj.artist;
		tableArrReading[tableArrReading.length - 1][5] = Obj.pages;
		tableArrReading[tableArrReading.length - 1][6] = Obj.rating;
		tableArrReading[tableArrReading.length - 1][7] = Obj.status;
		tableArrReading[tableArrReading.length - 1][8] = "0";
		
		tableArrReading = expandArr(tableArrReading);
		return tableArrReading;
	}
	
	public String[][] insertObjInTableArrCompleted(String[][] tableArrCompleted, doujinObj Obj){
		tableArrCompleted[tableArrCompleted.length - 1][1] = Obj.coverImage;
		tableArrCompleted[tableArrCompleted.length - 1][3] = Obj.title;
		tableArrCompleted[tableArrCompleted.length - 1][2] = Obj.id;
		for(int i=0;i<Obj.tags.length;i++){
			if(i == 0)
				tableArrCompleted[tableArrCompleted.length - 1][9] = Obj.tags[i] + ", ";
			else
				tableArrCompleted[tableArrCompleted.length - 1][9] = tableArrCompleted[tableArrCompleted.length - 1][9] + Obj.tags[i] + ", ";
		}
		tableArrCompleted[tableArrCompleted.length - 1][4] = Obj.artist;
		tableArrCompleted[tableArrCompleted.length - 1][5] = Obj.pages;
		tableArrCompleted[tableArrCompleted.length - 1][6] = Obj.rating;
		tableArrCompleted[tableArrCompleted.length - 1][7] = "1";
		tableArrCompleted[tableArrCompleted.length - 1][8] = Obj.status;
		
		tableArrCompleted = expandArr(tableArrCompleted);
		return tableArrCompleted;
	}
}
