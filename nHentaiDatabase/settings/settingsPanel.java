package settings;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.filechooser.FileSystemView;

import moreInformation.moreInformationPanel;
import stats.statsPanel;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.Color;
import java.awt.Dimension;

public class settingsPanel extends JPanel {
	private JTextField fileLocation_TField;
	private JFileChooser myJFileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
	private String fileLocation;
	/**
	 * Create the panel.
	 */
	public settingsPanel(String[][] planToRead, String[][] reading, String [][] completed) {
		setForeground(Color.WHITE);
		setBackground(new Color(34, 34, 34));
		setLayout(null);
		
		fileLocation_TField = new JTextField();
		fileLocation_TField.setBounds(10, 41, 270, 20);
		add(fileLocation_TField);
		fileLocation_TField.setColumns(10);
		
		JLabel fileLocation_btn = new JLabel("file location:");
		fileLocation_btn.setForeground(Color.WHITE);
		fileLocation_btn.setFont(new Font("Tahoma", Font.PLAIN, 15));
		fileLocation_btn.setBounds(10, 18, 92, 19);
		add(fileLocation_btn);
		
		JButton changeLocation_btn = new JButton("change");
		changeLocation_btn.setBounds(290, 40, 80, 23);
		changeLocation_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
            	UIManager.put("OptionPane.background", new Color(244, 244, 244));
            	UIManager.put("Panel.background", new Color(244, 244, 244));
            	
            	myJFileChooser.setDialogTitle("Choose a directory to save your file: ");
        		myJFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            	int returnValue = myJFileChooser.showOpenDialog(null);
            	
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = myJFileChooser.getSelectedFile();
                    System.out.println(selectedFile.getAbsolutePath());
                    fileLocation = selectedFile.getAbsolutePath();
                    fileLocation_TField.setText(fileLocation);
                }
                UIManager.put("OptionPane.background", new Color(35, 35, 35));
        		UIManager.put("Panel.background", new Color(35, 35, 35));
            }
        });
		add(changeLocation_btn);
		
		JButton save_btn = new JButton("save");
		save_btn.setBounds(380, 40, 60, 23);
		add(save_btn);
		
		JButton stats_btn = new JButton("stats");
		stats_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				statsPanel stats = new statsPanel(planToRead, reading, completed);
				UIManager.put("OptionPane.minimumSize", new Dimension(500, 900));
				JOptionPane inspectPane = new JOptionPane(stats, JOptionPane.PLAIN_MESSAGE,
						JOptionPane.OK_OPTION);
				int result = inspectPane.showOptionDialog(null, stats, "stats", 0,
						JOptionPane.PLAIN_MESSAGE, null, null, null);
			}
		});
		stats_btn.setBounds(10, 119, 89, 23);
		add(stats_btn);

	}
	
	public String getFileLocation() {
		return fileLocation;
	}
}
