package stats;

import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import moreInformation.moreInformationPanel;
import outsourcedClasses.Methods;
import settings.settingsPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Window;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;

import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.awt.event.ActionEvent;

public class statsPanel extends JPanel {
	private JTextField readDoujins_TField;
	private JTextField readPages_TField;
	private JTextField timeSpend_TField;
	private Point mouseCoord;
	
	private Methods methods = new Methods();
	
	
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
		readDoujins_TField.setBackground(Color.WHITE);
		readDoujins_TField.setEditable(false);
		
		int readingDoujins = getReadDoujins(reading, 8);
		int completedDoujins = getReadDoujins(completed, 7);
		
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
		readPages_TField.setBackground(Color.WHITE);
		readPages_TField.setEditable(false);
		
		int readingPages = getReadPages(reading, 8);
		int completedPages = getReadPages(completed, 7);
		
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
		
		String OS = System.getProperty("os.name");
		System.out.println(OS);
		String appData = System.getenv("APPDATA");
		if(OS.equals("Linux")) {
			appData = System.getProperty("user.home");
		}
		
		JLabel picture_lbl = new JLabel("");
		picture_lbl.setIcon(new ImageIcon(appData + "/nHentaiDatabase/savedPhotos/" + getFavorideId(reading, completed) + "_medium.jpg"));
		picture_panel.add(picture_lbl);
		
		JLabel lblNewLabel_3 = new JLabel("time spend reading");
		lblNewLabel_3.setForeground(Color.WHITE);
		lblNewLabel_3.setBounds(10, 150, 127, 14);
		add(lblNewLabel_3);
		
		timeSpend_TField = new JTextField();
		timeSpend_TField.setBackground(Color.WHITE);
		timeSpend_TField.setEditable(false);
		
		String timeSpend = timeSpend(readingPages + completedPages);
		timeSpend_TField.setText(timeSpend);
		
		timeSpend_TField.setBounds(10, 165, 63, 20);
		add(timeSpend_TField);
		timeSpend_TField.setColumns(10);
		
		JButton inspectFavorite_btn = new JButton("");
		inspectFavorite_btn.setIcon(new ImageIcon(moreInformationPanel.class.getResource("/grafics/MainGUI/inspectButton.png")));
		inspectFavorite_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String[][] tableArr = new String[1][1];
				int modelRow = 0;
				String favoriteId = getFavorideId(reading, completed);
				for(int i=0;i<reading.length;i++) {
					if(reading[i][2].equals(favoriteId)) {
						tableArr = reading;
						modelRow = i;
					}
				}
				for(int i=0;i<completed.length;i++) {
					if(completed[i][2].equals(favoriteId)) {
						tableArr = completed;
						modelRow = i;
					}
				}
				
				
				String title = tableArr[modelRow][3];
				String id = tableArr[modelRow][2];
				String tags = tableArr[modelRow][9];
				String artist = tableArr[modelRow][4];
				String pages = tableArr[modelRow][5];
				String rating = tableArr[modelRow][8];
				String status = tableArr[modelRow][6];
				String URL = tableArr[modelRow][1];
				String timesRead = tableArr[modelRow][7];

				String OS = System.getProperty("os.name");
				System.out.println(OS);
				String appData = System.getenv("APPDATA");
				String photoLocation;
				if(OS.equals("Linux")) {
					appData = System.getProperty("user.home");
					photoLocation = appData + "/nHentaiDatabase/savedPhotos/" + favoriteId + "_medium.jpg";
				}
				photoLocation = appData + "\\nHentaiDatabase\\savedPhotos\\" + favoriteId + "_medium.jpg";

				
				moreInformationPanel moreInformation = new moreInformationPanel(id, title, artist, pages, rating,
						timesRead, status, tags,
						photoLocation, false);
				
				Component[] buttonText = methods.OKCancelButtonCreate();
				
				UIManager.put("OptionPane.minimumSize", new Dimension(500, 900));
				
				JOptionPane inspectPane = new JOptionPane(moreInformation, JOptionPane.PLAIN_MESSAGE,
						JOptionPane.OK_OPTION, null, buttonText, null);
				
				final JDialog dialog = new JDialog();
				
				inspectPane.addPropertyChangeListener(new PropertyChangeListener() {
		            @Override
		            public void propertyChange(PropertyChangeEvent evt) {
		                String name = evt.getPropertyName();
		                if ("value".equals(name)) {
		                	dialog.dispose();
		                    final Object value = inspectPane.getValue();
		                    System.out.println(value);
		                }
		            }
		        });
		        dialog.setUndecorated(true);
		        dialog.getContentPane().setLayout(new BorderLayout());
		        
		        JPanel dialogwindowToolbar = new JPanel();
		        dialogwindowToolbar.setBackground(new Color(17, 19, 22));
		        dialogwindowToolbar.setLayout(new BorderLayout());
		        dialogwindowToolbar.setPreferredSize(new Dimension(200, 25));
				
				mouseCoord = null;
				dialogwindowToolbar.addMouseListener(new MouseListener() {
					@Override
					public void mousePressed(MouseEvent e) {
						mouseCoord = e.getPoint();
					}

					@Override
					public void mouseReleased(MouseEvent e) {
						mouseCoord = null;
					}

					@Override
					public void mouseClicked(MouseEvent e) {
					}

					@Override
					public void mouseEntered(MouseEvent e) {
					}

					@Override
					public void mouseExited(MouseEvent e) {
					}
				});
				dialogwindowToolbar.addMouseMotionListener(new MouseMotionListener() {
					@Override
					public void mouseDragged(MouseEvent e) {
						Point currCoords = e.getLocationOnScreen();
						dialog.setLocation(currCoords.x - mouseCoord.x, currCoords.y - mouseCoord.y);
					}

					@Override
					public void mouseMoved(MouseEvent e) {
					}

				});
				JButton dialogcloseWindow_btn = new JButton();
				dialogcloseWindow_btn.setHorizontalTextPosition(SwingConstants.CENTER);
				dialogcloseWindow_btn.setIcon(new ImageIcon(settingsPanel.class.getResource("/grafics/Close.png")));
				dialogcloseWindow_btn.setPreferredSize(new Dimension(24, 24));
				dialogcloseWindow_btn.setRequestFocusEnabled(false);
				dialogcloseWindow_btn.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						dialog.dispose();
					}
					
				});
				dialogwindowToolbar.add(dialogcloseWindow_btn, BorderLayout.LINE_END);
				dialog.getContentPane().add(dialogwindowToolbar, BorderLayout.PAGE_START);
		        dialog.getContentPane().add(inspectPane);
		        dialog.pack();
		        dialog.setLocationRelativeTo(null);
		        dialog.setVisible(true);
			}
		});
		inspectFavorite_btn.setBounds(210, 75, 70, 60);
		add(inspectFavorite_btn);
	}
	
	/**
	 * Get how many Doujins were read.
	 * 
	 * @param data The Array with data
	 * @param positionTimes Were the value of how many times one doujins were read is stored
	 * @return int
	 */
	public int getReadDoujins(String[][] data, int positionTimes) {
		int counter = 0;
		for(int i=0;i<data.length;i++) {
			if((data[i][positionTimes] != null) && !(data[i][positionTimes].equals("")) && !(data[i][positionTimes].equals("null"))) {
				counter = counter + Integer.valueOf(data[i][positionTimes]);
			}
		}
		return counter;
	}
	
	/**
	 * Get how many pages were read.
	 * 
	 * @param data The Array with data
	 * @param positionTimes Were the value of how many times one doujins were read is stored
	 * @return int
	 */
	public int getReadPages(String[][] data, int positionTimes) {
		int pages = 0;
		for(int i=0;i<data.length;i++) {
			if(!(data[i][positionTimes] == null) && !data[i][positionTimes].equals("") && !(data[i][positionTimes].equals("null")))
				pages = pages + (Integer.valueOf(data[i][positionTimes]) * Integer.valueOf(data[i][5]));
		}
		return pages;
	}
	
	/**
	 * Get how many time was spend on reading doujins.
	 * 
	 * @param pages	The number of read pages.
	 * @return String
	 */
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
	
	/**
	 * Determines the favorite Doujin and return the id
	 * 
	 * @param reading The Array with currently reading douins
	 * @param completed The Array with completed doujins
	 * @return String
	 */
	public String getFavorideId(String[][] reading, String [][] completed) {
		String id = "";
		int timesRead = 0;
		
		String[][] together = new String[reading.length-1 + completed.length-1][3];
		int[] points = new int[together.length];
		int i;
		for(i=0;i<reading.length-1;i++) {
			together[i][0] = reading[i][6];
			together[i][1] = reading[i][8];
			together[i][2] = reading[i][2];
		}
		for(int j=i;j<together.length;j++) {
			together[j][0] = completed[j-i][6];
			together[j][1] = completed[j-i][7];
			together[j][2] = completed[j-i][2];
		}
		
		for(int j=0;j<together.length;j++) {
			int rating = Integer.valueOf(together[j][0]);
			int timesReadthis = Integer.valueOf(together[j][1]);
			points[j] = rating * timesReadthis;
		}
		
		int best = 0;
		
		for(int j=0;j<points.length;j++) {
			if(points[j] > best) {
				best = points[j];
				id = together[j][2];
			}
		}
		
		return id;
		
	}
}
