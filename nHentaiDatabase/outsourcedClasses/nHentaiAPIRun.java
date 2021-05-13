package outsourcedClasses;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.table.DefaultTableModel;

import nHentaiWebScaper.nHentaiWebBase;

public class nHentaiAPIRun {

	private nHentaiWebBase nHentaiAPI;
	private long initTime;
	
	private Methods methods = new Methods();
	
	private String Shlash;
	
	/**
	 * Constructor of nHentaiAPIRun
	 * nHentaiAPIRun Manages the creation of a new entry. It only modifies the given Array.
	 * 
	 * @param shlash Which type of slash is used
	 */
	public nHentaiAPIRun(String shlash) {
		nHentaiAPI = new nHentaiWebBase();
		Shlash = shlash;
	}
	
	public long getInitTime() {
		return initTime;
	}
	
	/**
	 * The creation of a new entry of the section "plan to read".
	 * 
	 * @param tableArr The source Array
	 * @param location The location where the pictures are saved
	 * @param code The nHentai id of the new entry
	 * @param URL The URL of the new entry
	 * @param rating The rating of the new entry
	 * @param status The status of the new entry
	 * @return String[][]
	 * @throws IOException
	 */
	public String[][] nHentaiAPIRun(String[][] tableArr, String location, String code, String URL, String rating, String status) throws IOException{
		String coverImage = "";
		String title = "";
		String id = "";
		String[] tags = new String[0];
		String artist = "";
		String pages = "";
		
		if(!code.equals("")) 
			initTime = nHentaiAPI.initDocWithCode(code);
		else if(!URL.equals("")) 
			initTime = nHentaiAPI.initDocWithURL(URL);
		
		coverImage = nHentaiAPI.getCoverImage();
		title = nHentaiAPI.getTitle();
		id = nHentaiAPI.getId();
		StringBuilder sb = new StringBuilder(id);
		sb.deleteCharAt(0);
		id = sb.toString();
		tags = nHentaiAPI.getTags();
		artist = nHentaiAPI.getArtist();
		pages = nHentaiAPI.getPages();
		
		nHentaiAPI.saveImageAsFile(coverImage, location + Shlash + id + "_original.jpg");
		methods.scaleImage(location + Shlash + id + "_original.jpg", location + "\\" + id, "_low.jpg", 50, 71);
		methods.scaleImage(location + Shlash + id + "_original.jpg", location + "\\" + id, "_medium.jpg", 150, 212);
		
		tableArr[tableArr.length - 1][1] = coverImage;
		tableArr[tableArr.length - 1][3] = title;
		tableArr[tableArr.length - 1][2] = id;
		for(int i=0;i<tags.length;i++){
			if(i == 0)
				tableArr[tableArr.length - 1][9] = tags[i] + ", ";
			else
				tableArr[tableArr.length - 1][9] = tableArr[tableArr.length - 1][9] + tags[i] + ", ";
		}
		tableArr[tableArr.length - 1][4] = artist;
		tableArr[tableArr.length - 1][5] = pages;
		tableArr[tableArr.length - 1][6] = status;
		tableArr[tableArr.length - 1][8] = rating;
		tableArr[tableArr.length - 1][7] = "0";
		
		tableArr = methods.expandArr(tableArr);
		return tableArr;
	}
	
	/**
	 * The creation of a new entry of the section "reading".
	 * 
	 * @param tableArrReading The source Array
	 * @param location The location where the pictures are saved
	 * @param code The nHentai id of the new entry
	 * @param URL The URL of the new entry
	 * @param rating The rating of the new entry
	 * @param status The status of the new entry
	 * @return String[][]
	 * @throws IOException
	 */
	public String[][] nHentaiAPIRunReading(String[][] tableArrReading, String location, String code, String URL, String rating, String status) throws IOException{
		String coverImage = "";
		String title = "";
		String id = "";
		String[] tags = new String[0];
		String artist = "";
		String pages = "";
		
		if(!code.equals("")) 
			initTime = nHentaiAPI.initDocWithCode(code);
		else if(!URL.equals("")) 
			initTime = nHentaiAPI.initDocWithURL(URL);
		
		coverImage = nHentaiAPI.getCoverImage();
		title = nHentaiAPI.getTitle();
		id = nHentaiAPI.getId();
		StringBuilder sb = new StringBuilder(id);
		sb.deleteCharAt(0);
		id = sb.toString();
		tags = nHentaiAPI.getTags();
		artist = nHentaiAPI.getArtist();
		pages = nHentaiAPI.getPages();
		
		nHentaiAPI.saveImageAsFile(coverImage, location + Shlash + id + "_original.jpg");
		methods.scaleImage(location + Shlash + id + "_original.jpg", location + "\\" + id, "_low.jpg", 50, 71);
		methods.scaleImage(location + Shlash + id + "_original.jpg", location + "\\" + id, "_medium.jpg", 150, 212);
		
		tableArrReading[tableArrReading.length - 1][1] = coverImage;
		tableArrReading[tableArrReading.length - 1][3] = title;
		tableArrReading[tableArrReading.length - 1][2] = id;
		for(int i=0;i<tags.length;i++){
			if(i == 0)
				tableArrReading[tableArrReading.length - 1][9] = tags[i] + ", ";
			else
				tableArrReading[tableArrReading.length - 1][9] = tableArrReading[tableArrReading.length - 1][9] + tags[i] + ", ";
		}
		tableArrReading[tableArrReading.length - 1][4] = artist;
		tableArrReading[tableArrReading.length - 1][5] = pages;
		tableArrReading[tableArrReading.length - 1][6] = rating;
		tableArrReading[tableArrReading.length - 1][7] = status;
		tableArrReading[tableArrReading.length - 1][8] = "0";
		
		tableArrReading = methods.expandArr(tableArrReading);
		return tableArrReading;
	}
	
	/**
	 * The creation of a new entry of the section "completed".
	 * 
	 * @param tableArrCompleted The source Array
	 * @param location The location where the pictures are saved
	 * @param code The nHentai id of the new entry
	 * @param URL The URL of the new entry
	 * @param rating The rating of the new entry
	 * @param status The status of the new entry
	 * @return String[][]
	 * @throws IOException
	 */
	public String[][] nHentaiAPIRunCompleted(String[][] tableArrCompleted, String location, String code, String URL, String rating, String status) throws IOException{
		String coverImage = "";
		String title = "";
		String id = "";
		String[] tags = new String[0];
		String artist = "";
		String pages = "";
		
		if(!code.equals("")) 
			initTime = nHentaiAPI.initDocWithCode(code);
		else if(!URL.equals("")) 
			initTime = nHentaiAPI.initDocWithURL(URL);
		
		coverImage = nHentaiAPI.getCoverImage();
		title = nHentaiAPI.getTitle();
		id = nHentaiAPI.getId();
		StringBuilder sb = new StringBuilder(id);
		sb.deleteCharAt(0);
		id = sb.toString();
		tags = nHentaiAPI.getTags();
		artist = nHentaiAPI.getArtist();
		pages = nHentaiAPI.getPages();
		
		nHentaiAPI.saveImageAsFile(coverImage, location + Shlash + id + "_original.jpg");
		methods.scaleImage(location + Shlash + id + "_original.jpg", location + "\\" + id, "_low.jpg", 50, 71);
		methods.scaleImage(location + Shlash + id + "_original.jpg", location + "\\" + id, "_medium.jpg", 150, 212);
		
		tableArrCompleted[tableArrCompleted.length - 1][1] = coverImage;
		tableArrCompleted[tableArrCompleted.length - 1][3] = title;
		tableArrCompleted[tableArrCompleted.length - 1][2] = id;
		for(int i=0;i<tags.length;i++){
			if(i == 0)
				tableArrCompleted[tableArrCompleted.length - 1][9] = tags[i] + ", ";
			else
				tableArrCompleted[tableArrCompleted.length - 1][9] = tableArrCompleted[tableArrCompleted.length - 1][9] + tags[i] + ", ";
		}
		tableArrCompleted[tableArrCompleted.length - 1][4] = artist;
		tableArrCompleted[tableArrCompleted.length - 1][5] = pages;
		tableArrCompleted[tableArrCompleted.length - 1][6] = rating;
		tableArrCompleted[tableArrCompleted.length - 1][7] = "1";
		tableArrCompleted[tableArrCompleted.length - 1][8] = status;
		
		tableArrCompleted = methods.expandArr(tableArrCompleted);
		return tableArrCompleted;
	}
}
