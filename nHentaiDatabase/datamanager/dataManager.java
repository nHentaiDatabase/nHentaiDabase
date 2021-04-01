package datamanager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;


public class dataManager {
	
	final String fileName = "nHentaiDatabaseData";
	PrintWriter outputStream;
	
	
	public dataManager() {
		
	}
	
	public void saveTable(String[][] table, String location) {
		try {
            outputStream = new PrintWriter(location+"\\"+fileName+".txt");
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
	
	public String[][] readTable(String location) {
		String[][] tmp = new String[][] {{""},{""}};
		try {
			File myObj = new File(location+"\\"+fileName+".txt");
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
	
	private String[][] splitData(String[] fileData) {
		int rows = 0;
		for(int i=0;i<fileData.length;i++){
			if(fileData[i].equals("*-*")) {
				rows++;
			}
		}
		String[][] outputData = new String[rows][9];
		for(int i=0;i<rows;i++) {
			for(int j=0;j<9;j++) {
				outputData[i][j] = fileData[j+(i*10)];
			}
		}
		return outputData;
	}
}
