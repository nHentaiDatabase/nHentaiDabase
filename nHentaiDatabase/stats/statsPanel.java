package stats;

import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Color;

public class statsPanel extends JPanel {
	private JTextField readDoujins_TField;
	private JTextField readPages_TField;
	private JTextField timeSpend_TField;

	/**
	 * Create the panel.
	 */
	public statsPanel(String[][] planToRead, String[][] reading, String [][] completed) {
		setLayout(null);
		setBackground(new Color(35, 35, 35));
		
		JLabel lblNewLabel = new JLabel("read Doujins");
		lblNewLabel.setForeground(Color.WHITE);
		lblNewLabel.setBounds(10, 50, 80, 14);
		add(lblNewLabel);
		
		readDoujins_TField = new JTextField();
		readDoujins_TField.setEditable(false);
		
		int readingDoujins = getReadDoujins(reading, 8);
		int completedDoujins = getReadDoujins(completed, 8);
		
		readDoujins_TField.setText(String.valueOf(completedDoujins + readingDoujins));
		
		readDoujins_TField.setBounds(10, 65, 63, 20);
		add(readDoujins_TField);
		readDoujins_TField.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("read pages");
		lblNewLabel_1.setForeground(Color.WHITE);
		lblNewLabel_1.setBackground(Color.WHITE);
		lblNewLabel_1.setBounds(10, 100, 80, 14);
		add(lblNewLabel_1);
		
		readPages_TField = new JTextField();
		readPages_TField.setEditable(false);
		
		int readingPages = getReadPages(reading, 8);
		int completedPages = getReadPages(completed, 8);
		
		readPages_TField.setText(String.valueOf(readingPages + completedPages));
		
		readPages_TField.setBounds(10, 115, 86, 20);
		add(readPages_TField);
		readPages_TField.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("favorite doujin");
		lblNewLabel_2.setForeground(Color.WHITE);
		lblNewLabel_2.setBounds(211, 50, 80, 14);
		add(lblNewLabel_2);
		
		JPanel picture_panel = new JPanel();
		picture_panel.setBounds(301, 50, 150, 212);
		add(picture_panel);
		
		JLabel picture_lbl = new JLabel("");
		picture_lbl.setIcon(new ImageIcon(getPictureLocation(reading, completed)));
		picture_panel.add(picture_lbl);
		
		JLabel lblNewLabel_3 = new JLabel("time spend reading");
		lblNewLabel_3.setForeground(Color.WHITE);
		lblNewLabel_3.setBounds(10, 150, 127, 14);
		add(lblNewLabel_3);
		
		timeSpend_TField = new JTextField();
		timeSpend_TField.setEditable(false);
		
		String timeSpend = timeSpend(readingPages + completedPages);
		timeSpend_TField.setText(timeSpend);
		
		timeSpend_TField.setBounds(10, 165, 63, 20);
		add(timeSpend_TField);
		timeSpend_TField.setColumns(10);
	}
	
	public int getReadDoujins(String[][] data, int positionTimes) {
		int counter = 0;
		for(int i=0;i<data.length;i++) {
			if(!(data[i][positionTimes] == null) && !data[i][positionTimes].equals(""))
				counter = counter + Integer.valueOf(data[i][positionTimes]);
		}
		return counter;
	}
	
	public int getReadPages(String[][] data, int positionTimes) {
		int pages = 0;
		for(int i=0;i<data.length;i++) {
			if(!(data[i][positionTimes] == null) && !data[i][positionTimes].equals(""))
				pages = pages + (Integer.valueOf(data[i][positionTimes]) * Integer.valueOf(data[i][5]));
		}
		return pages;
	}
	
	public String timeSpend(int pages) {
		
		int seconds = pages * 30;
		int minutes = seconds / 60;
		int hours = minutes / 60;
		int days = hours / 24;
		
		if(seconds < 600) {
			return String.valueOf(seconds) + "s";
		}else if(minutes < 60) {
			return String.valueOf(minutes) + "m";
		}else if(hours < 24) {
			return String.valueOf(hours) + "h";
		}else{
			return String.valueOf(days) + "d";
		}
	}
	
	public String getPictureLocation(String[][] reading, String [][] completed) {
		String id = "";
		int timesRead = 0;
		
		String[][] together = new String[reading.length-1 + completed.length-1][3];
		int[] points = new int[together.length];
		for(int i=0;i<reading.length-1;i++) {
			together[i][0] = reading[i][6];
			together[i][1] = reading[i][8];
			together[i][2] = reading[i][2];
		}
		for(int i=0;i<completed.length-1;i++) {
			together[i][0] = completed[i][6];
			together[i][1] = completed[i][8];
			together[i][2] = completed[i][2];
		}
		
		for(int i=0;i<together.length;i++) {
			int rating = Integer.valueOf(together[i][0]);
			int timesReadthis = Integer.valueOf(together[i][1]);
			points[i] = rating * timesReadthis;
		}
		
		int best = 0;
		
		for(int i=0;i<points.length;i++) {
			if(points[i] > best) {
				best = points[i];
				id = together[i][2];
			}
		}
		
		String appData = System.getenv("APPDATA");
		
		return appData + "\\nHentaiDatabse\\savedPhotos\\" + id + "_medium.jpg";
		
	}
}
