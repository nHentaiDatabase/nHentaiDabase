package loadingScreen;

import java.awt.EventQueue;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.Random;
import java.util.concurrent.ExecutionException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import nHentaiWebScaper.nHentaiWebBase;
import nHentaiMainGUI.nHentai;

import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class loadingScreenApp {

	private JFrame frame;

	String mainFolderLocation = "\\nHentaiDatabse";
	String photoFolderLocation = "\\savedPhotos";
	String userDataFolderLocation = "\\userData";
	String randomPhotoFolderLocation = "\\randomPhotos";
	String appdataLocation;
	
	private Task task;
	
	JProgressBar progressBar;
	
	nHentaiWebBase nHentaiAPI;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Windows".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
				}
			}
		} catch (Exception e) {

		}
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					loadingScreenApp window = new loadingScreenApp();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					//nHentai window = new nHentai();
					//window.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public loadingScreenApp() {
		nHentaiAPI = new nHentaiWebBase();
		appdataLocation = System.getenv("APPDATA");
		setUpAppData(appdataLocation);
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 352, 125);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setVisible(true);
		
		progressBar = new JProgressBar();
		progressBar.setForeground(Color.GREEN);
		progressBar.setBounds(10, 55, 316, 20);
		progressBar.setValue(0);
		frame.getContentPane().add(progressBar);
		
		JButton btnNewButton = new JButton("start nHentaiDatabase");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				task = new Task();
		        task.addPropertyChangeListener(new PropertyChangeListener() {

					@Override
					public void propertyChange(PropertyChangeEvent evt) {
		                if ("progress" == evt.getPropertyName()) {
		                    int progress = (Integer) evt.getNewValue();
		                    progressBar.setValue(progress);
		                } 
		            }
		        });
		        task.execute();
			}
		});
		btnNewButton.setBounds(83, 11, 168, 23);
		frame.getContentPane().add(btnNewButton);
		
		
		
		
	}
	
	public void setUpAppData(String appData) {
		new File(appData + mainFolderLocation).mkdirs();
		new File(appData + mainFolderLocation + photoFolderLocation).mkdirs();
		new File(appData + mainFolderLocation + userDataFolderLocation).mkdirs();
		new File(appData + mainFolderLocation + randomPhotoFolderLocation).mkdirs();
	}
	
	public void scaleImage(String locationOriginal, String locationNew, String name, int x, int y) {
		try {
			ImageIcon ii = new ImageIcon(locationOriginal);
			BufferedImage bi = new BufferedImage(x, y, BufferedImage.TYPE_INT_RGB);
			Graphics2D g2d = (Graphics2D) bi.createGraphics();
			g2d.addRenderingHints(
					new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY));
			boolean b = g2d.drawImage(ii.getImage(), 0, 0, x, y, null);
			System.out.println(b);
			ImageIO.write(bi, "jpg", new File(locationNew + name));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	class Task extends SwingWorker<Void, Void> {
        /*
         * Main task. Executed in background thread.
         */
        @Override
        public Void doInBackground() {
        	int progress = 0;
        	int second = 0;
        	setProgress(0);
        	for(int i=0;i<200;i++) {
        		if(i == 99)
        			System.out.println("99");
    			File f = new File(appdataLocation + mainFolderLocation + randomPhotoFolderLocation + "\\" + i + "_medium.jpg");
    			if(!f.exists()) {
    				nHentaiAPI.saveImageAsFile("https://picsum.photos/150/212", appdataLocation + mainFolderLocation + randomPhotoFolderLocation + "\\" + i + "_medium.jpg");
    				scaleImage(appdataLocation + mainFolderLocation + randomPhotoFolderLocation + "\\" + i + "_medium.jpg", appdataLocation + mainFolderLocation + randomPhotoFolderLocation + "\\" + i, "_low.jpg", 50, 71);
    			}
    			second++;
    			if(second == 2) {
    				second = 0;
    				progress++;
    				setProgress(progress);
    			}
        	}
            return null;
        }
 
        /*
         * Executed in event dispatching thread
         */
        @Override
        public void done() {
        	try {
				get();
			} catch (InterruptedException | ExecutionException e1) {
				e1.printStackTrace();
			}
            System.out.println("finish");
            frame.setVisible(false);
            EventQueue.invokeLater(new Runnable() {
    			public void run() {
    				try {
    					nHentai window = new nHentai();
    					window.setVisible(true);
    				} catch (Exception e) {
    					e.printStackTrace();
    				}
    			}
    		});
        }
    }
	
	
}
