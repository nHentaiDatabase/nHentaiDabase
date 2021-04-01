package settings;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.filechooser.FileSystemView;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JFileChooser;

public class settings {

	private JFrame frame;
	private JTextField location_TField;
	private String fileLocation;
	
	private JFileChooser myJFileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		try {
			for(LookAndFeelInfo info:UIManager.getInstalledLookAndFeels()) {
				System.out.println(info.getName());
				if("Windows".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
				}
			}
		}catch(Exception e) {
			
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					settings window = new settings();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	/**
	 * Create the application.
	 */
	public settings() {
		myJFileChooser.setDialogTitle("Choose a directory to save your file: ");
		myJFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 500, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		location_TField = new JTextField();
		location_TField.setBounds(10, 47, 281, 20);
		frame.getContentPane().add(location_TField);
		location_TField.setColumns(10);
		location_TField.setText(fileLocation);
		
		JLabel fileLocation_lb = new JLabel("file location");
		fileLocation_lb.setBounds(10, 27, 77, 14);
		frame.getContentPane().add(fileLocation_lb);
		
		JButton changeLocation = new JButton("change location");
		changeLocation.setBounds(301, 46, 107, 23);
		changeLocation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
            	int returnValue = myJFileChooser.showOpenDialog(null);
                // int returnValue = jfc.showSaveDialog(null);

                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = myJFileChooser.getSelectedFile();
                    System.out.println(selectedFile.getAbsolutePath());
                    fileLocation = selectedFile.getAbsolutePath();
                    location_TField.setText(fileLocation);
                }
            }
        });
		frame.getContentPane().add(changeLocation);
		
		JButton back = new JButton("back");
		back.setBounds(10, 327, 89, 23);
		back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
            	setUnvisible();
            }
        });
		frame.getContentPane().add(back);
	}
	
	public String getFileLocation() {
		return fileLocation;
	}
	
	public void setVisible() {
		frame.setVisible(true);
	}
	
	public void setUnvisible() {
		frame.setVisible(false);
	}
}
