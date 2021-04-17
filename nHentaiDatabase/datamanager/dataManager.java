package datamanager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.SwingWorker;


public class dataManager {
	
	PrintWriter outputStream;
	
	
	public dataManager() {
		
	}
	
	public void saveTable(String[][] table, String location) {
		try {
            outputStream = new PrintWriter(location);
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
		for(int i=0;i<table.length;i++) {
			for(int j=0;j<table[0].length;j++) {
				outputStream.println(table[i][j]);
			}
			outputStream.println("*-*");
		}
		outputStream.close();
	}
	
	public void saveSettings(String[] settings, String location) {
		try {
            outputStream = new PrintWriter(location);
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
		for(int i=0;i<settings.length;i++) {
			outputStream.println(settings[i]);
		}
		outputStream.close();
	}
	
	public String[][] readTable(String location) {
		String[][] tmp = new String[][] {{""},{""}};
		try {
			File myObj = new File(location);
			Scanner myReader = new Scanner(myObj);
			int lines = 0;
			while (myReader.hasNextLine()) {
		        String data = myReader.nextLine();
		        lines++;
		    }
		    String[] fileData = new String[lines];
		    myReader = new Scanner(myObj);
		    for (int i=0;i<lines;i++) {
		    	fileData[i] = myReader.nextLine();
		    }
		    tmp = splitData(fileData);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return tmp;
	}
	
	public String[] readSettings(String location) {
		String[] tmp = new String[1];
		try {
			File myObj = new File(location);
			Scanner myReader;
			myReader = new Scanner(myObj);
			int index = 0;
			while (myReader.hasNextLine()) {
				String data = myReader.nextLine();
				tmp[index] = data;
				index++;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return tmp;
	}
	
	private String[][] splitData(String[] fileData) {
		int rows = 0;
		for(int i=0;i<fileData.length;i++){
			if(fileData[i].equals("*-*")) {
				rows++;
			}
		}
		String[][] outputData = new String[rows][10];
		int Jrefrence = 0;
		//TODO
		for(int i=0;i<rows;i++) {
			for(int j=Jrefrence;j<Jrefrence+10;j++) {
				outputData[i][j-Jrefrence] = fileData[j];
			}
			Jrefrence = Jrefrence+11;
		}
		return outputData;
	}
}