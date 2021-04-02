package outsourcedClasses;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.table.DefaultTableModel;

import nHentaiWebScaper.nHentaiWebBase;

public class nHentaiAPIRun {

	private nHentaiWebBase nHentaiAPI;
	
	public nHentaiAPIRun() {
		nHentaiAPI = new nHentaiWebBase();
	}
	
	public String[][] nHentaiAPIRun(String[][] tableArr, String location, String code, String URL, String rating, String status){
		String coverImage = "";
		String title = "";
		String id = "";
		String[] tags = new String[0];
		String artist = "";
		String pages = "";
		
		if(!code.equals("")) 
			nHentaiAPI.initDocWithCode(code);
		else if(!URL.equals("")) 
			nHentaiAPI.initDocWithURL(URL);
		
		coverImage = nHentaiAPI.getCoverImage();
		title = nHentaiAPI.getTitle();
		id = nHentaiAPI.getId();
		StringBuilder sb = new StringBuilder(id);
		sb.deleteCharAt(0);
		id = sb.toString();
		tags = nHentaiAPI.getTags();
		artist = nHentaiAPI.getArtist();
		pages = nHentaiAPI.getPages();
		
		nHentaiAPI.saveImageAsFile(coverImage, location + "\\" + id + "_original.jpg");
		scaleImage(location + "\\" + id, "_low.jpg", 50, 71);
		scaleImage(location + "\\" + id, "_medium.jpg", 150, 212);
		
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
		
		tableArr = expandArr(tableArr);
		return tableArr;
	}
	
	public String[][] nHentaiAPIRunReading(String[][] tableArrReading, String location, String code, String URL, String rating, String status){
		String coverImage = "";
		String title = "";
		String id = "";
		String[] tags = new String[0];
		String artist = "";
		String pages = "";
		
		if(!code.equals("")) 
			nHentaiAPI.initDocWithCode(code);
		else if(!URL.equals("")) 
			nHentaiAPI.initDocWithURL(URL);
		
		coverImage = nHentaiAPI.getCoverImage();
		title = nHentaiAPI.getTitle();
		id = nHentaiAPI.getId();
		StringBuilder sb = new StringBuilder(id);
		sb.deleteCharAt(0);
		id = sb.toString();
		tags = nHentaiAPI.getTags();
		artist = nHentaiAPI.getArtist();
		pages = nHentaiAPI.getPages();
		
		nHentaiAPI.saveImageAsFile(coverImage, location + "\\" + id + "_original.jpg");
		scaleImage(location + "\\" + id, "_low.jpg", 50, 71);
		scaleImage(location + "\\" + id, "_medium.jpg", 150, 212);
		
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
		tableArrReading[tableArrReading.length - 1][6] = status;
		tableArrReading[tableArrReading.length - 1][7] = rating;
		
		tableArrReading = expandArr(tableArrReading);
		return tableArrReading;
	}
	
	public void scaleImage(String location, String name, int x, int y){
        try{
            ImageIcon ii = new ImageIcon(location + "_original.jpg");
            BufferedImage bi = new BufferedImage(x, y, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = (Graphics2D)bi.createGraphics();
            g2d.addRenderingHints(new RenderingHints(RenderingHints.KEY_RENDERING,
                    RenderingHints.VALUE_RENDER_QUALITY));
            boolean b = g2d.drawImage(ii.getImage(), 0, 0, x, y, null);
            System.out.println(b);
            ImageIO.write(bi, "jpg", new File(location + name));
        }
        catch (Exception e){
                e.printStackTrace();
        }
	}
	
	public String[][] expandArr(String[][] input) {
		String[][] tmp = new String[input.length +1][input[0].length];
		System.arraycopy(input, 0, tmp, 0, input.length);
		input = tmp;
		return input;
	}
	
}
