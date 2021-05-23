package outsourcedClasses;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.table.DefaultTableModel;

import SaveObj.doujinObj;
import nHentaiWebScaper.nHentaiWebBase;

public class nHentaiAPIRun {

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
	public doujinObj nHentaiAPIRun(String location, String code, String URL, String rating, String status, nHentaiWebBase API) throws IOException{
		String coverImage = "";
		String title = "";
		String id = "";
		String[] tags = new String[0];
		String artist = "";
		String pages = "";
		
		if(!code.equals("")) 
			initTime = API.initDocWithCode(code);
		else if(!URL.equals("")) 
			initTime = API.initDocWithURL(URL);
		
		coverImage = API.getCoverImage();
		title = API.getTitle();
		id = API.getId();
		StringBuilder sb = new StringBuilder(id);
		sb.deleteCharAt(0);
		id = sb.toString();
		tags = API.getTags();
		artist = API.getArtist();
		pages = API.getPages();
		
		API.saveImageAsFile(coverImage, location + Shlash + id + "_original.jpg");
		methods.scaleImage(location + Shlash + id + "_original.jpg", location + "\\" + id, "_low.jpg", 50, 71);
		methods.scaleImage(location + Shlash + id + "_original.jpg", location + "\\" + id, "_medium.jpg", 150, 212);
		
		
		return new doujinObj(coverImage, title, id, artist, pages, status, rating, "0", tags);
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
	public doujinObj nHentaiAPIRunReading(String location, String code, String URL, String rating, String status, nHentaiWebBase API) throws IOException{
		String coverImage = "";
		String title = "";
		String id = "";
		String[] tags = new String[0];
		String artist = "";
		String pages = "";
		
		if(!code.equals("")) 
			initTime = API.initDocWithCode(code);
		else if(!URL.equals("")) 
			initTime = API.initDocWithURL(URL);
		
		coverImage = API.getCoverImage();
		title = API.getTitle();
		id = API.getId();
		StringBuilder sb = new StringBuilder(id);
		sb.deleteCharAt(0);
		id = sb.toString();
		tags = API.getTags();
		artist = API.getArtist();
		pages = API.getPages();
		
		API.saveImageAsFile(coverImage, location + Shlash + id + "_original.jpg");
		methods.scaleImage(location + Shlash + id + "_original.jpg", location + "\\" + id, "_low.jpg", 50, 71);
		methods.scaleImage(location + Shlash + id + "_original.jpg", location + "\\" + id, "_medium.jpg", 150, 212);

		return new doujinObj(coverImage, title, id, artist, pages, status, rating, "0", tags);
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
	public doujinObj nHentaiAPIRunCompleted(String location, String code, String URL, String rating, String status, nHentaiWebBase API) throws IOException{
		String coverImage = "";
		String title = "";
		String id = "";
		String[] tags = new String[0];
		String artist = "";
		String pages = "";
		
		if(!code.equals("")) 
			initTime = API.initDocWithCode(code);
		else if(!URL.equals("")) 
			initTime = API.initDocWithURL(URL);
		
		coverImage = API.getCoverImage();
		title = API.getTitle();
		id = API.getId();
		StringBuilder sb = new StringBuilder(id);
		sb.deleteCharAt(0);
		id = sb.toString();
		tags = API.getTags();
		artist = API.getArtist();
		pages = API.getPages();
		
		API.saveImageAsFile(coverImage, location + Shlash + id + "_original.jpg");
		methods.scaleImage(location + Shlash + id + "_original.jpg", location + "\\" + id, "_low.jpg", 50, 71);
		methods.scaleImage(location + Shlash + id + "_original.jpg", location + "\\" + id, "_medium.jpg", 150, 212);
		
		return new doujinObj(coverImage, title, id, artist, pages, status, rating, "0", tags);
	}
}
