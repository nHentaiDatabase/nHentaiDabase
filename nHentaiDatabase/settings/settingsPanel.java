package settings;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileSystemView;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class settingsPanel extends JPanel {
	private JTextField fileLocation_TField;
	private JFileChooser myJFileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
	private String fileLocation;
	/**
	 * Create the panel.
	 */
	public settingsPanel() {
		setLayout(null);
		
		fileLocation_TField = new JTextField();
		fileLocation_TField.setBounds(10, 41, 270, 20);
		add(fileLocation_TField);
		fileLocation_TField.setColumns(10);
		
		JLabel fileLocation_btn = new JLabel("file location:");
		fileLocation_btn.setFont(new Font("Tahoma", Font.PLAIN, 15));
		fileLocation_btn.setBounds(10, 18, 92, 19);
		add(fileLocation_btn);
		
		JButton changeLocation_btn = new JButton("change");
		changeLocation_btn.setBounds(290, 40, 80, 23);
		changeLocation_btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
            	myJFileChooser.setDialogTitle("Choose a directory to save your file: ");
        		myJFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            	int returnValue = myJFileChooser.showOpenDialog(null);
            	
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = myJFileChooser.getSelectedFile();
                    System.out.println(selectedFile.getAbsolutePath());
                    fileLocation = selectedFile.getAbsolutePath();
                    fileLocation_TField.setText(fileLocation);
                }
            }
        });
		add(changeLocation_btn);
		
		JButton save_btn = new JButton("save");
		save_btn.setBounds(380, 40, 60, 23);
		add(save_btn);

	}
	
	public String getFileLocation() {
		return fileLocation;
	}
}
