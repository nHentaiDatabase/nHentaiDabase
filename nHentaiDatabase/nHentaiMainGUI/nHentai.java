package nHentaiMainGUI;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Point;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollBar;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import com.formdev.flatlaf.FlatDarkLaf;

import SaveObj.doujinObj;
import ThreadPoolClasses.ThreadPool;
import settings.settingsPanel;
import updater.LocationFinder;
import updater.gitHubUpdater;
import datamanager.dataManager;
import moreInformation.moreInformationPanel;
import nHentaiWebScaper.nHentaiWebBase;
import newEntry.newEntryGeneral;
import outsourcedClasses.ButtonColumnAll;
import outsourcedClasses.Methods;
import outsourcedClasses.nHentaiAPIRun;
import nHentaiWebScaper.Error;
import search.searchEngine;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import javax.swing.filechooser.FileSystemView;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JCheckBox;

/**
 * 
 * @author Philipp Bleimund
 * @version 1.4.2
 */
public class nHentai {

	private JFrame frmNhentaidatabase;
	private JTable table_panel1;
	private DefaultTableModel model;
	private DefaultTableModel modelReading;
	private DefaultTableModel modelCompleted;

	private dataManager dataManager;
	private nHentaiWebBase nHentaiAPI;
	private nHentaiAPIRun nHentaiAPIRun;
	private Methods methods;
	private searchEngine searchEngine;
	private gitHubUpdater Updater;
	private LocationFinder finder;

	private String[] settings;
	
	private String[][] tableArr;
	private String[][] tableArrSave;
	private boolean inSearchViewPlanToRead = false;
	private String[][] tableArrReading;
	private String[][] tableArrReadingSave;
	private boolean inSearchViewReading = false;
	private String[][] tableArrCompleted;
	private String[][] tableArrCompletedSave;
	private boolean inSearchViewCompleted = false;
	private String appdataLocation;
	
	private boolean SFW = false;
	private boolean safed = true;
	
	private String mainFolderLocation;
	private String photoFolderLocation;
	private String userDataFolderLocation;
	private String randomPhotoFolderLocation;
	private String Slash;

	private Point mouseCoord;
	private JTable table_panel2;
	private JTable table_panel3;
	
	private JProgressBar EntryLoader_panel1_PBar;
	private JLabel responseTime_panel1_lbl;
	private JProgressBar EntryLoader_panel2_PBar;
	private JLabel responseTime_panel2_lbl;
	private JProgressBar EntryLoader_panel3_PBar;
	private JLabel responseTime_panel3_lbl;
	
	private loadingScreenMainGUI loadingScreen;

	private JCheckBox searchId_panel1_CBox;
	private JCheckBox searchTitle_panel1_CBox;
	private JCheckBox searchAuthor_panel1_CBox;
	private JCheckBox searchTags_panel1_CBox;
	private JCheckBox searchId_panel2_CBox;
	private JCheckBox searchTitle_panel2_CBox;
	private JCheckBox searchAuthor_panel2_CBox;
	private JCheckBox searchTags_panel2_CBox;
	private JCheckBox searchId_panel3_CBox;
	private JCheckBox searchTitle_panel3_CBox;
	private JCheckBox searchAuthor_panel3_CBox;
	private JCheckBox searchTags_panel3_CBox;
	
	private String title;
	private String id;
	private String tags;
	private String artist;
	private String pages;
	private String rating;
	private String status;
	private String URL;
	private String timesRead;
	
	private boolean start = false;
	
	private String goodRes = "good";
	private boolean resScroll = false;
	
	public String OLDVERSION = "1.5.8";
	public String VERSION = "1.5.8";

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) throws IOException {
		
		
		
		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Windows".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					
				}
				System.out.println(info.getClassName());
			}
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {

		}

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					nHentai window = new nHentai();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public nHentai() {
		
		
		
		tableArr = new String[1][10];
		tableArrSave = new String[1][10];
		tableArrReading = new String[1][10];
		tableArrReadingSave = new String[1][10];
		tableArrCompleted = new String[1][10];
		tableArrCompletedSave = new String[1][10];
		String OS = System.getProperty("os.name");
		System.out.println(OS);
		if(OS.indexOf("Windows") >= 0) {
			appdataLocation = System.getenv("APPDATA");
			mainFolderLocation = "\\nHentaiDatabase";
			photoFolderLocation = "\\savedPhotos";
			userDataFolderLocation = "\\userData";
			randomPhotoFolderLocation = "\\randomPhotos";
			Slash = "\\";
		}else {
			appdataLocation = System.getProperty("user.home");
			mainFolderLocation = "/.nHentaiDatabase";
			photoFolderLocation = "/savedPhotos";
			userDataFolderLocation = "/userData";
			randomPhotoFolderLocation = "/randomPhotos";
			Slash = "/";
		}
		
		System.out.println(appdataLocation);
		System.out.println(mainFolderLocation);
		System.out.println(photoFolderLocation);
		System.out.println(userDataFolderLocation);
		System.out.println(randomPhotoFolderLocation);

		GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
		int width = gd.getDisplayMode().getWidth();
		int height = gd.getDisplayMode().getHeight();
		
		if(width < 950) {
			goodRes = "not usable";
			resScroll = true;
		}
		else if(height < 925) {
			goodRes = "inspect not usable";
			resScroll = true;
		}
		dataManager = new dataManager();
		nHentaiAPI = new nHentaiWebBase();
		nHentaiAPIRun = new nHentaiAPIRun(Slash);
		searchEngine = new searchEngine();
		Updater = new gitHubUpdater();
		methods = new Methods(appdataLocation, mainFolderLocation, photoFolderLocation, Slash,  randomPhotoFolderLocation, userDataFolderLocation);
		
		File jarDir = new File(ClassLoader.getSystemClassLoader().getResource(".").getPath());
		File dir = jarDir.getAbsoluteFile().getParentFile();
		String path = dir.toString();
		System.out.println(path);
		
		initialize();
		
		
		loadingScreen = new loadingScreenMainGUI();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		UIManager.put("Button.focus", new Color(0, 0, 0, 0));
		UIManager.put("CheckBox.focus", new Color(0, 0, 0, 0));
		UIManager.put("OptionPane.background", new Color(35, 35, 35));
		UIManager.put("Panel.background", new Color(35, 35, 35));
		
		frmNhentaidatabase = new JFrame();
		frmNhentaidatabase.getContentPane().setBackground(new java.awt.Color(54, 57, 63));
		frmNhentaidatabase.setTitle("nHentaiDatabase");
		frmNhentaidatabase.setBounds(100, 100, 947, 721);
		frmNhentaidatabase.setUndecorated(true);
		frmNhentaidatabase.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmNhentaidatabase.getContentPane().setLayout(null);
		frmNhentaidatabase.setVisible(false);

		JPanel windowToolbar = new JPanel();
		windowToolbar.setBackground(new Color(17, 19, 22));
		windowToolbar.setBounds(0, 0, 946, 25);
		mouseCoord = null;
		windowToolbar.addMouseListener(new MouseListener() {
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
		windowToolbar.addMouseMotionListener(new MouseMotionListener() {
			@Override
			public void mouseDragged(MouseEvent e) {
				Point currCoords = e.getLocationOnScreen();
				frmNhentaidatabase.setLocation(currCoords.x - mouseCoord.x, currCoords.y - mouseCoord.y);
			}

			@Override
			public void mouseMoved(MouseEvent e) {
			}

		});
		frmNhentaidatabase.getContentPane().add(windowToolbar);
		windowToolbar.setLayout(null);

		JButton closeWindow_btn = new JButton();
		closeWindow_btn.setHorizontalTextPosition(SwingConstants.CENTER);
		closeWindow_btn.setIcon(new ImageIcon(nHentai.class.getResource("/grafics/Close.png")));
		closeWindow_btn.setBounds(922, 0, 24, 24);
		closeWindow_btn.setRequestFocusEnabled(false);
		closeWindow_btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				
				if(safed == false) {
				
					exitConfirm confirm = new exitConfirm();
	        		
					
					final JButton saveButton = new JButton();
					saveButton.setPreferredSize(new Dimension(47,25));
					saveButton.setIcon(new ImageIcon(moreInformationPanel.class.getResource("/grafics/close/save.png")));
					saveButton.setHorizontalTextPosition(SwingConstants.CENTER);
					saveButton.addActionListener(new ActionListener() {
	
						@Override
						public void actionPerformed(ActionEvent e) {
							JOptionPane paneAP = methods.getOptionPane((JComponent)e.getSource());
							paneAP.setValue("save");
							Window w = SwingUtilities.getWindowAncestor(saveButton);
	
						    if (w != null) {
						      w.setVisible(false);
						    }
						}
						
					});
					saveButton.addMouseListener(new MouseAdapter() {
						public void mouseEntered(MouseEvent evt) {
							saveButton.setIcon(new ImageIcon(moreInformationPanel.class.getResource("/grafics/close/saveHover.png")));
						}
	
						public void mouseExited(MouseEvent evt) {
							saveButton.setIcon(new ImageIcon(moreInformationPanel.class.getResource("/grafics/close/save.png")));
						}
	
						public void mousePressed(MouseEvent evt) {
							saveButton.setIcon(new ImageIcon(moreInformationPanel.class.getResource("/grafics/close/saveSelected.png")));
						}
	
						public void mouseReleased(MouseEvent evt) {
							saveButton.setIcon(new ImageIcon(moreInformationPanel.class.getResource("/grafics/close/saveHover.png")));
						}
					});
					
					final JButton closeButton = new JButton();
					closeButton.setPreferredSize(new Dimension(51,25));
					closeButton.setIcon(new ImageIcon(moreInformationPanel.class.getResource("/grafics/close/close.png")));
					closeButton.setHorizontalTextPosition(SwingConstants.CENTER);
					closeButton.addActionListener(new ActionListener() {
	
						@Override
						public void actionPerformed(ActionEvent e) {
							JOptionPane paneAP = methods.getOptionPane((JComponent)e.getSource());
							paneAP.setValue("close");
							Window w = SwingUtilities.getWindowAncestor(closeButton);
	
						    if (w != null) {
						      w.setVisible(false);
						    }
						}
						
					});
					closeButton.addMouseListener(new MouseAdapter() {
						public void mouseEntered(MouseEvent evt) {
							closeButton.setIcon(new ImageIcon(moreInformationPanel.class.getResource("/grafics/close/closeHover.png")));
						}
	
						public void mouseExited(MouseEvent evt) {
							closeButton.setIcon(new ImageIcon(moreInformationPanel.class.getResource("/grafics/close/close.png")));
						}
	
						public void mousePressed(MouseEvent evt) {
							closeButton.setIcon(new ImageIcon(moreInformationPanel.class.getResource("/grafics/close/closeSelected.png")));
						}
	
						public void mouseReleased(MouseEvent evt) {
							closeButton.setIcon(new ImageIcon(moreInformationPanel.class.getResource("/grafics/close/closeHover.png")));
						}
					});
					
					final JButton cancelButton = new JButton();
					cancelButton.setPreferredSize(new Dimension(58,25));
					cancelButton.setIcon(new ImageIcon(moreInformationPanel.class.getResource("/grafics/close/cancel.png")));
					cancelButton.setHorizontalTextPosition(SwingConstants.CENTER);
					cancelButton.addActionListener(new ActionListener() {
	
						@Override
						public void actionPerformed(ActionEvent e) {
							JOptionPane paneAP = methods.getOptionPane((JComponent)e.getSource());
							paneAP.setValue("cancel");
							Window w = SwingUtilities.getWindowAncestor(cancelButton);
	
						    if (w != null) {
						      w.setVisible(false);
						    }
						}
						
					});
					cancelButton.addMouseListener(new MouseAdapter() {
						public void mouseEntered(MouseEvent evt) {
							cancelButton.setIcon(new ImageIcon(moreInformationPanel.class.getResource("/grafics/close/cancelHover.png")));
						}
	
						public void mouseExited(MouseEvent evt) {
							cancelButton.setIcon(new ImageIcon(moreInformationPanel.class.getResource("/grafics/close/cancel.png")));
						}
	
						public void mousePressed(MouseEvent evt) {
							cancelButton.setIcon(new ImageIcon(moreInformationPanel.class.getResource("/grafics/close/cancelSelected.png")));
						}
	
						public void mouseReleased(MouseEvent evt) {
							cancelButton.setIcon(new ImageIcon(moreInformationPanel.class.getResource("/grafics/close/cancelHover.png")));
						}
					});
					
					Component[] buttonText = new Component[]{	saveButton, closeButton, cancelButton};
					UIManager.put("OptionPane.minimumSize", new Dimension(200, 100));
					JOptionPane pane = new JOptionPane(confirm, JOptionPane.PLAIN_MESSAGE, JOptionPane.YES_NO_CANCEL_OPTION, null, buttonText, null);
					
					final JDialog dialog = new JDialog((Frame)null, "Boo");
					
			        pane.addPropertyChangeListener(new PropertyChangeListener() {
			            @Override
			            public void propertyChange(PropertyChangeEvent evt) {
			                String name = evt.getPropertyName();
			                if ("value".equals(name)) {
			                	dialog.dispose();
			                    final Object value = pane.getValue();
			                    System.out.println(value);
			                    if(value.equals("save")) {
									String SaveFileLocation = appdataLocation + mainFolderLocation + userDataFolderLocation;
									
									if(inSearchViewPlanToRead == false)
										dataManager.saveTable(tableArr, SaveFileLocation + Slash + "nHentaiDatabasePlanToRead.nhdb");
									else
										dataManager.saveTable(tableArrSave, SaveFileLocation + Slash + "nHentaiDatabasePlanToRead.nhdb");
									
									if(inSearchViewPlanToRead == false)
										dataManager.saveTable(tableArrReading, SaveFileLocation + Slash + "nHentaiDatabaseReading.nhdb");
									else
										dataManager.saveTable(tableArrReadingSave, SaveFileLocation + Slash + "nHentaiDatabaseReading.nhdb");
									
									if(inSearchViewPlanToRead == false)
										dataManager.saveTable(tableArrCompleted, SaveFileLocation + Slash +"nHentaiDatabaseCompleted.nhdb");
									else
										dataManager.saveTable(tableArrCompletedSave, SaveFileLocation + Slash +"nHentaiDatabaseCompleted.nhdb");
									
									
									String[] settings = new String[1];
					    			settings[0] = "SFW: " + String.valueOf(SFW);
					    			dataManager.saveSettings(settings, SaveFileLocation + Slash + "nHentaiDatabaseSettings.nhdb");
									System.exit(0);
								}else if(value.equals("close")) {
									System.exit(0);
								}
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
					dialogcloseWindow_btn.setIcon(new ImageIcon(nHentai.class.getResource("/grafics/Close.png")));
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
			        dialog.getContentPane().add(pane);
			        dialog.pack();
			        dialog.setLocationRelativeTo(null);
			        dialog.setVisible(true);
				}else {
					System.exit(0);
				}
			}
		});
		windowToolbar.add(closeWindow_btn);
		
		JButton safeWindow_btn = new JButton();
		safeWindow_btn.setIcon(new ImageIcon(nHentai.class.getResource("/grafics/saveIcon.png")));
		safeWindow_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				safed = true;
				
				String SaveFileLocation = appdataLocation + mainFolderLocation + userDataFolderLocation;
				
				
				class Task extends SwingWorker<Void, Void> {
					String[][] table;
					String location;
					PrintWriter outputStream;
					public Task(String[][] table, String location) {
						this.table = table;
						this.location = location;
					}
					
				    @Override
				    public Void doInBackground() {
				    	int progress = 0;
				    	setProgress(0);
				    	
				    	double secondEnd;
				    	double second = 0;
				    	
				    	double length = table.length;
						secondEnd = length / 100;
				    	
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
							second++;
							if(second > secondEnd) {
								if(secondEnd < 1) {
									progress = progress + (int)(1 / secondEnd);
								}else
									progress++;
								setProgress(progress);
								second = 0;
							}
						}
						outputStream.close();
						return null;
				    }
				    
				    @Override
				    public void done() {
				    	EntryLoader_panel1_PBar.setVisible(false);
				    	EntryLoader_panel1_PBar.setValue(0);
						EntryLoader_panel2_PBar.setVisible(false);
						EntryLoader_panel2_PBar.setValue(0);
						EntryLoader_panel3_PBar.setVisible(false);
						EntryLoader_panel3_PBar.setValue(0);
				    }
				    
				}
				Task task;
				EntryLoader_panel1_PBar.setVisible(true);
				EntryLoader_panel2_PBar.setVisible(true);
				EntryLoader_panel3_PBar.setVisible(true);
				if(inSearchViewPlanToRead == false)
					task = new Task(tableArr, SaveFileLocation + Slash + "nHentaiDatabasePlanToRead.nhdb");
				else
					task = new Task(tableArrSave, SaveFileLocation + Slash + "nHentaiDatabasePlanToRead.nhdb");
				
				task.addPropertyChangeListener(new PropertyChangeListener() {

					@Override
					public void propertyChange(PropertyChangeEvent evt) {
		                if ("progress" == evt.getPropertyName()) {
		                    int progress = (Integer) evt.getNewValue();
		                    EntryLoader_panel1_PBar.setValue(progress);
		                    EntryLoader_panel2_PBar.setValue(progress);
		                    EntryLoader_panel3_PBar.setValue(progress);
		                } 
		            }
		        });
				task.execute();
				
				EntryLoader_panel1_PBar.setVisible(true);
				EntryLoader_panel2_PBar.setVisible(true);
				EntryLoader_panel3_PBar.setVisible(true);
				if(inSearchViewPlanToRead == false)
					task = new Task(tableArrReading, SaveFileLocation + Slash + "nHentaiDatabaseReading.nhdb");
				else
					task = new Task(tableArrReadingSave, SaveFileLocation + Slash + "nHentaiDatabaseReading.nhdb");
				
				task.addPropertyChangeListener(new PropertyChangeListener() {

					@Override
					public void propertyChange(PropertyChangeEvent evt) {
		                if ("progress" == evt.getPropertyName()) {
		                    int progress = (Integer) evt.getNewValue();
		                    EntryLoader_panel1_PBar.setValue(progress);
		                    EntryLoader_panel2_PBar.setValue(progress);
		                    EntryLoader_panel3_PBar.setValue(progress);
		                } 
		            }
		        });
				task.execute();
				
				EntryLoader_panel1_PBar.setVisible(true);
				EntryLoader_panel2_PBar.setVisible(true);
				EntryLoader_panel3_PBar.setVisible(true);
				if(inSearchViewPlanToRead == false)
					task = new Task(tableArrCompleted, SaveFileLocation + Slash + "nHentaiDatabaseCompleted.nhdb");
				else
					task = new Task(tableArrCompleted, SaveFileLocation + Slash + "nHentaiDatabaseCompleted.nhdb");
				
				task.addPropertyChangeListener(new PropertyChangeListener() {

					@Override
					public void propertyChange(PropertyChangeEvent evt) {
		                if ("progress" == evt.getPropertyName()) {
		                    int progress = (Integer) evt.getNewValue();
		                    EntryLoader_panel1_PBar.setValue(progress);
		                    EntryLoader_panel2_PBar.setValue(progress);
		                    EntryLoader_panel3_PBar.setValue(progress);
		                } 
		            }
		        });
				task.execute();

				String[] settings = new String[1];
    			settings[0] = "SFW: " + String.valueOf(SFW);
    			dataManager.saveSettings(settings, SaveFileLocation + Slash + "nHentaiDatabaseSettings.nhdb");
			}
		});
		safeWindow_btn.setBounds(3, 3, 22, 22);
		windowToolbar.add(safeWindow_btn);
		
		JButton minimizeWindow_btn = new JButton("");
		minimizeWindow_btn.setIcon(new ImageIcon(nHentai.class.getResource("/grafics/minimize.png")));
		minimizeWindow_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmNhentaidatabase.setState(frmNhentaidatabase.ICONIFIED);
			}
		});
		minimizeWindow_btn.setBounds(888, 1, 24, 23);
		windowToolbar.add(minimizeWindow_btn);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBorder(null);
		tabbedPane.setBounds(0, 25, 946, 696);
		tabbedPane.setBackground(new java.awt.Color(32, 34, 37));
		tabbedPane.setUI(new renderEngine.CustomTabbedPaneUI());
		tabbedPane.setBorder(BorderFactory.createLineBorder(new java.awt.Color(32, 34, 37), 1));
		frmNhentaidatabase.getContentPane().add(tabbedPane);

		JPanel planToRead_tab = new JPanel();
		planToRead_tab.setBorder(null);
		planToRead_tab.setOpaque(true);
		planToRead_tab.setBorder(BorderFactory.createLineBorder(new java.awt.Color(32, 34, 37), 1));
		planToRead_tab.setBackground(new java.awt.Color(32, 34, 37));
		tabbedPane.addTab("plan to read", null, planToRead_tab, null);
		planToRead_tab.setLayout(null);
		
		

		JPanel panel_panel1 = new JPanel();
		panel_panel1.setBackground(new Color(0, 44, 88));
		panel_panel1.setBounds(10, 11, 230, 632);
		planToRead_tab.add(panel_panel1);
		panel_panel1.setLayout(null);

		/*
		 * Start panel 1
		 */
		JButton newEntry_panel1_bnt = new JButton("new Entry");
		newEntry_panel1_bnt.setSelectedIcon(new ImageIcon(nHentai.class.getResource("/grafics/Button.png")));
		newEntry_panel1_bnt.setForeground(Color.WHITE);
		newEntry_panel1_bnt.setFont(new Font("Tahoma", Font.PLAIN, 20));
		newEntry_panel1_bnt.setHorizontalTextPosition(SwingConstants.CENTER);
		newEntry_panel1_bnt.setIcon(new ImageIcon(nHentai.class.getResource("/grafics/Button.png")));
		newEntry_panel1_bnt.setBounds(10, 243, 210, 35);
		newEntry_panel1_bnt.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent evt) {
				newEntry_panel1_bnt.setIcon(new ImageIcon(nHentai.class.getResource("/grafics/ButtonHower.png")));
			}

			public void mouseExited(MouseEvent evt) {
				newEntry_panel1_bnt.setIcon(new ImageIcon(nHentai.class.getResource("/grafics/Button.png")));
			}

			public void mousePressed(MouseEvent evt) {
				newEntry_panel1_bnt.setIcon(new ImageIcon(nHentai.class.getResource("/grafics/ButtonSelected.png")));
			}

			public void mouseReleased(MouseEvent evt) {
				newEntry_panel1_bnt.setIcon(new ImageIcon(nHentai.class.getResource("/grafics/ButtonHower.png")));
			}
		});
		newEntry_panel1_bnt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {

				actionPerformedNewEntry(tableArr, model, responseTime_panel1_lbl, EntryLoader_panel1_PBar, "plan to read");				
			}
		});
		panel_panel1.add(newEntry_panel1_bnt);

		JButton settings_panel1_bnt = new JButton("");
		settings_panel1_bnt.setBounds(10, 586, 112, 35);
		settings_panel1_bnt.requestFocus();
		settings_panel1_bnt.setIcon(new ImageIcon(nHentai.class.getResource("/grafics/MainGUI/settings.png")));
		settings_panel1_bnt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				actionPerformedSetting();
			}
		});
		settings_panel1_bnt.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent evt) {
				settings_panel1_bnt.setIcon(new ImageIcon(nHentai.class.getResource("/grafics/MainGUI/settingsHover.png")));
			}

			public void mouseExited(MouseEvent evt) {
				settings_panel1_bnt.setIcon(new ImageIcon(nHentai.class.getResource("/grafics/MainGUI/settings.png")));
			}

			public void mousePressed(MouseEvent evt) {
				settings_panel1_bnt.setIcon(new ImageIcon(nHentai.class.getResource("/grafics/MainGUI/settingsSelected.png")));
			}

			public void mouseReleased(MouseEvent evt) {
				settings_panel1_bnt.setIcon(new ImageIcon(nHentai.class.getResource("/grafics/MainGUI/settingsHover.png")));
			}
		});
		panel_panel1.add(settings_panel1_bnt);

		JButton saveTable_panel1_btn = new JButton("");
		saveTable_panel1_btn.setFont(new Font("Tahoma", Font.PLAIN, 20));
		saveTable_panel1_btn.setForeground(Color.WHITE);
		saveTable_panel1_btn.setIcon(new ImageIcon(nHentai.class.getResource("/grafics/saveButton.png")));
		saveTable_panel1_btn.setBounds(10, 552, 80, 25);
		saveTable_panel1_btn.setHorizontalTextPosition(SwingConstants.CENTER);
		saveTable_panel1_btn.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent evt) {
				saveTable_panel1_btn.setIcon(new ImageIcon(nHentai.class.getResource("/grafics/saveButtonHover.png")));
			}

			public void mouseExited(MouseEvent evt) {
				saveTable_panel1_btn.setIcon(new ImageIcon(nHentai.class.getResource("/grafics/saveButton.png")));
			}

			public void mousePressed(MouseEvent evt) {
				saveTable_panel1_btn.setIcon(new ImageIcon(nHentai.class.getResource("/grafics/saveButtonSelected.png")));
			}

			public void mouseReleased(MouseEvent evt) {
				saveTable_panel1_btn.setIcon(new ImageIcon(nHentai.class.getResource("/grafics/saveButtonHover.png")));
			}
		});
		saveTable_panel1_btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				if(inSearchViewPlanToRead == false)
					actionPerformedSafe(tableArr);
				else
					actionPerformedSafe(tableArrSave);
			}
		});
		panel_panel1.add(saveTable_panel1_btn);

		JButton loadTable__panel1_btn = new JButton("");
		loadTable__panel1_btn.setIcon(new ImageIcon(nHentai.class.getResource("/grafics/loadButton.png")));
		loadTable__panel1_btn.setBounds(100, 552, 80, 25);
		loadTable__panel1_btn.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent evt) {
				loadTable__panel1_btn.setIcon(new ImageIcon(nHentai.class.getResource("/grafics/loadButtonHover.png")));
			}

			public void mouseExited(MouseEvent evt) {
				loadTable__panel1_btn.setIcon(new ImageIcon(nHentai.class.getResource("/grafics/loadButton.png")));
			}

			public void mousePressed(MouseEvent evt) {
				loadTable__panel1_btn.setIcon(new ImageIcon(nHentai.class.getResource("/grafics/loadButtonSelected.png")));
			}

			public void mouseReleased(MouseEvent evt) {
				loadTable__panel1_btn.setIcon(new ImageIcon(nHentai.class.getResource("/grafics/loadButtonHover.png")));
			}
		});
		loadTable__panel1_btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				tableArr = actionPerformedLoad("plan to read");
				model = methods.ArrToTable(model, tableArr, SFW);
			}
		});
		panel_panel1.add(loadTable__panel1_btn);
		
		JTextField search_panel1_TField = new JTextField();
		search_panel1_TField.setBackground(Color.WHITE);
		search_panel1_TField.setToolTipText("");
		search_panel1_TField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					if(!search_panel1_TField.getText().equals("")) {
						String text = search_panel1_TField.getText();
						actionPerformedSearch(text);
					}else {
						for(int i=0;i<tableArr.length-1;i++) {
							model.removeRow(0);
						}
						tableArr = tableArrSave;
						methods.ArrToTable(model, tableArr, SFW);
						inSearchViewPlanToRead = false;
						search_panel1_TField.setText("");
					}
			}
		});
		search_panel1_TField.setBounds(10, 11, 157, 23);
		panel_panel1.add(search_panel1_TField);
		search_panel1_TField.setColumns(10);
		
		JButton search_panel1_btn = new JButton();
		search_panel1_btn.setHorizontalTextPosition(SwingConstants.CENTER);
		search_panel1_btn.setIcon(new ImageIcon(nHentai.class.getResource("/grafics/searchClear/search.png")));
		search_panel1_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!search_panel1_TField.getText().equals("")) {
					String text = search_panel1_TField.getText();
					actionPerformedSearch(text);
				}else {
					for(int i=0;i<tableArr.length-1;i++) {
						model.removeRow(0);
					}
					tableArr = tableArrSave;
					methods.ArrToTable(model, tableArr, SFW);
					inSearchViewPlanToRead = false;
					search_panel1_TField.setText("");
				}
			}
		});
		search_panel1_btn.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent evt) {
					search_panel1_btn.setIcon(new ImageIcon(nHentai.class.getResource("/grafics/searchClear/searchHover.png")));
			}

			public void mouseExited(MouseEvent evt) {
					search_panel1_btn.setIcon(new ImageIcon(nHentai.class.getResource("/grafics/searchClear/search.png")));
			}

			public void mousePressed(MouseEvent evt) {
					search_panel1_btn.setIcon(new ImageIcon(nHentai.class.getResource("/grafics/searchClear/searchSelected.png")));
			}

			public void mouseReleased(MouseEvent evt) {
					search_panel1_btn.setIcon(new ImageIcon(nHentai.class.getResource("/grafics/searchClear/searchHover.png")));
			}
		});
		search_panel1_btn.setBounds(197, 11, 22, 23);
		panel_panel1.add(search_panel1_btn);
		
		
		JButton clear_panel1_btn = new JButton();
		clear_panel1_btn.setHorizontalTextPosition(SwingConstants.CENTER);
		clear_panel1_btn.setIcon(new ImageIcon(nHentai.class.getResource("/grafics/searchClear/clear.png")));
		clear_panel1_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for(int i=0;i<tableArr.length-1;i++) {
					model.removeRow(0);
				}
				tableArr = tableArrSave;
				methods.ArrToTable(model, tableArr, SFW);
				inSearchViewPlanToRead = false;
				search_panel1_TField.setText("");
			}
		});
		clear_panel1_btn.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent evt) {
				clear_panel1_btn.setIcon(new ImageIcon(nHentai.class.getResource("/grafics/searchClear/clearHover.png")));
			}

			public void mouseExited(MouseEvent evt) {
				clear_panel1_btn.setIcon(new ImageIcon(nHentai.class.getResource("/grafics/searchClear/clear.png")));
			}

			public void mousePressed(MouseEvent evt) {
				clear_panel1_btn.setIcon(new ImageIcon(nHentai.class.getResource("/grafics/searchClear/clearSelected.png")));
			}

			public void mouseReleased(MouseEvent evt) {
				clear_panel1_btn.setIcon(new ImageIcon(nHentai.class.getResource("/grafics/searchClear/clearHover.png")));
			}
		});
		clear_panel1_btn.setBounds(169, 11, 22, 23);
		panel_panel1.add(clear_panel1_btn);
		
		searchId_panel1_CBox = new JCheckBox("id");
		searchId_panel1_CBox.setForeground(Color.WHITE);
		searchId_panel1_CBox.setBackground(new Color(0, 44, 88));
		searchId_panel1_CBox.setBounds(10, 64, 52, 23);
		searchId_panel1_CBox.setVisible(false);
		searchId_panel1_CBox.setSelected(true);
		panel_panel1.add(searchId_panel1_CBox);
		
		searchTitle_panel1_CBox = new JCheckBox("title");
		searchTitle_panel1_CBox.setBackground(new Color(0, 44, 88));
		searchTitle_panel1_CBox.setForeground(Color.WHITE);
		searchTitle_panel1_CBox.setBounds(10, 90, 52, 23);
		searchTitle_panel1_CBox.setVisible(false);
		searchTitle_panel1_CBox.setSelected(true);
		panel_panel1.add(searchTitle_panel1_CBox);
		
		searchAuthor_panel1_CBox = new JCheckBox("author");
		searchAuthor_panel1_CBox.setBackground(new Color(0, 44, 88));
		searchAuthor_panel1_CBox.setForeground(Color.WHITE);
		searchAuthor_panel1_CBox.setBounds(83, 64, 68, 23);
		searchAuthor_panel1_CBox.setVisible(false);
		searchAuthor_panel1_CBox.setSelected(true);
		panel_panel1.add(searchAuthor_panel1_CBox);
		
		searchTags_panel1_CBox = new JCheckBox("tags");
		searchTags_panel1_CBox.setBackground(new Color(0, 44, 88));
		searchTags_panel1_CBox.setForeground(Color.WHITE);
		searchTags_panel1_CBox.setBounds(83, 90, 68, 23);
		searchTags_panel1_CBox.setVisible(false);
		searchTags_panel1_CBox.setSelected(true);
		panel_panel1.add(searchTags_panel1_CBox);

		JCheckBox showMore_panel1_CBox = new JCheckBox("show more");
		showMore_panel1_CBox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED) {//checkbox has been selected
					searchId_panel1_CBox.setVisible(true);
					searchTitle_panel1_CBox.setVisible(true);
					searchAuthor_panel1_CBox.setVisible(true);
					searchTags_panel1_CBox.setVisible(true);
				} else {//checkbox has been deselected
					searchId_panel1_CBox.setVisible(false);
					searchTitle_panel1_CBox.setVisible(false);
					searchAuthor_panel1_CBox.setVisible(false);
					searchTags_panel1_CBox.setVisible(false);
				};
			}
		});
		showMore_panel1_CBox.setForeground(Color.WHITE);
		showMore_panel1_CBox.setBackground(new Color(0, 44, 88));
		showMore_panel1_CBox.setBounds(10, 38, 80, 23);
		panel_panel1.add(showMore_panel1_CBox);
		
		
		JScrollPane scrollPane_panel1 = new JScrollPane();
		scrollPane_panel1.setBounds(250, 11, 666, 632);
		scrollPane_panel1.setBackground(Color.RED);
		
		JButton cornerButton_panel1 = new JButton();
		cornerButton_panel1.setIcon(new ImageIcon(nHentai.class.getResource("/grafics/jumpButton/jump.png")));
		cornerButton_panel1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				scrollPane_panel1.getVerticalScrollBar().setValue(0);
			}
			
		});
		cornerButton_panel1.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent evt) {
				cornerButton_panel1.setIcon(new ImageIcon(nHentai.class.getResource("/grafics/jumpButton/jumpHover.png")));
			}

			public void mouseExited(MouseEvent evt) {
				cornerButton_panel1.setIcon(new ImageIcon(nHentai.class.getResource("/grafics/jumpButton/jump.png")));
			}

			public void mousePressed(MouseEvent evt) {
				cornerButton_panel1.setIcon(new ImageIcon(nHentai.class.getResource("/grafics/jumpButton/jumpSelected.png")));
			}

			public void mouseReleased(MouseEvent evt) {
				cornerButton_panel1.setIcon(new ImageIcon(nHentai.class.getResource("/grafics/jumpButton/jumpHover.png")));
			}
		});
		
		scrollPane_panel1.setCorner(JScrollPane.UPPER_TRAILING_CORNER, cornerButton_panel1);
		scrollPane_panel1.getViewport().setBackground(new Color(54, 57, 63));
		
		JScrollBar scrollBar_panel1 = scrollPane_panel1.getVerticalScrollBar();
        Dimension scrollBarDim_panel1 = new Dimension(15, scrollBar_panel1
              .getPreferredSize().height);
        scrollBar_panel1.setPreferredSize(scrollBarDim_panel1);
        /*scrollBar_panel1.setUI(new BasicScrollBarUI() {
           @Override 
           protected void configureScrollBarColors(){
               this.thumbColor = new Color(10, 10, 10);
               this.thumbDarkShadowColor = new Color(10, 10, 10);
               this.thumbLightShadowColor = new Color(10, 10, 10);
               this.trackColor = new Color(59, 59, 59);
               this.trackHighlightColor = new Color(59, 59, 59);
           }
          @Override
         protected JButton createDecreaseButton(int orientation) {
             return createZeroButton();
         }

         @Override
         protected JButton createIncreaseButton(int orientation) {
             return createZeroButton();
         }
       });*/
		
		
		planToRead_tab.add(scrollPane_panel1);

		table_panel1 = new JTable() {
	        private static final long serialVersionUID = 1L;

	        public boolean isCellEditable(int row, int column) { 
	        	if(column == 7) {
	        		return true;
	        	}else {
	        		return false;               
	        	}
	        };
	    };
		table_panel1.setForeground(Color.WHITE);
		table_panel1.setBackground(new Color(54, 57, 63));
		table_panel1.getTableHeader().setDefaultRenderer(new renderEngine.HeaderColor());
		table_panel1.setRowSelectionAllowed(false);
		
		model = new DefaultTableModel(new Object[][] {}, new String[] { "number", "cover", "id", "title", "author", "pages", "status", "" });
		table_panel1.setModel(model);
		table_panel1.getColumnModel().getColumn(0).setResizable(false);
		table_panel1.getColumnModel().getColumn(0).setPreferredWidth(47);
		table_panel1.getColumnModel().getColumn(1).setResizable(false);
		table_panel1.getColumnModel().getColumn(1).setPreferredWidth(42);
		table_panel1.getColumnModel().getColumn(2).setResizable(false);
		table_panel1.getColumnModel().getColumn(3).setResizable(false);
		table_panel1.getColumnModel().getColumn(3).setPreferredWidth(126);
		table_panel1.getColumnModel().getColumn(4).setResizable(false);
		table_panel1.getColumnModel().getColumn(4).setPreferredWidth(95);
		table_panel1.getColumnModel().getColumn(5).setResizable(false);
		table_panel1.getColumnModel().getColumn(5).setPreferredWidth(65);
		table_panel1.getColumnModel().getColumn(6).setResizable(false);
		table_panel1.getColumnModel().getColumn(1).setCellRenderer(new TableCellRenderer() {

			@Override
			public Component getTableCellRendererComponent(JTable jtable, Object value, boolean bln, boolean bln1,
					int i, int i1) {
				JLabel lbl = new JLabel();
				lbl.setIcon((ImageIcon) value);
				return lbl;
			}
		});

		table_panel1.getTableHeader().setBackground(Color.RED);
		table_panel1.getTableHeader().setDefaultRenderer(new renderEngine.HeaderColor());
		
		
		ButtonColumnAll buttonColumnTable_panel1 = new ButtonColumnAll(table_panel1, deleteTableArrRow, 7);
		buttonColumnTable_panel1.setMnemonic(KeyEvent.VK_D);
		table_panel1.setRowHeight(71);
		scrollPane_panel1.setViewportView(table_panel1);
		
		EntryLoader_panel1_PBar = new JProgressBar();
		EntryLoader_panel1_PBar.setBackground(Color.WHITE);
		EntryLoader_panel1_PBar.setForeground(Color.DARK_GRAY);
		EntryLoader_panel1_PBar.setVisible(false);
		EntryLoader_panel1_PBar.setBounds(826, 646, 90, 14);
		planToRead_tab.add(EntryLoader_panel1_PBar);
		
		responseTime_panel1_lbl = new JLabel("nHentai response:");
		responseTime_panel1_lbl.setForeground(Color.GRAY);
		responseTime_panel1_lbl.setBounds(688, 646, 128, 14);
		responseTime_panel1_lbl.setVisible(false);
		planToRead_tab.add(responseTime_panel1_lbl);
		/*
		 * end panel 1
		 */

		
		/*
		 * Start panel 2
		 */
		JPanel reading_tab = new JPanel();
		reading_tab.setBorder(null);
		reading_tab.setOpaque(true);
		reading_tab.setBorder(BorderFactory.createLineBorder(new java.awt.Color(32, 34, 37), 1));
		reading_tab.setBackground(new java.awt.Color(32, 34, 37));
		tabbedPane.addTab("reading", null, reading_tab, null);
		reading_tab.setLayout(null);

		JPanel panel_panel2 = new JPanel();
		panel_panel2.setLayout(null);
		panel_panel2.setBackground(new Color(0, 44, 88));
		panel_panel2.setBounds(10, 11, 230, 632);
		reading_tab.add(panel_panel2);

		JButton newEntry_panel2_bnt = new JButton("new Entry");
		newEntry_panel2_bnt.setIcon(new ImageIcon(nHentai.class.getResource("/grafics/Button.png")));
		newEntry_panel2_bnt.setHorizontalTextPosition(SwingConstants.CENTER);
		newEntry_panel2_bnt.setForeground(Color.WHITE);
		newEntry_panel2_bnt.setFont(new Font("Tahoma", Font.PLAIN, 20));
		newEntry_panel2_bnt.setBounds(10, 243, 210, 35);
		newEntry_panel2_bnt.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent evt) {
				newEntry_panel2_bnt.setIcon(new ImageIcon(nHentai.class.getResource("/grafics/ButtonHower.png")));
			}

			public void mouseExited(MouseEvent evt) {
				newEntry_panel2_bnt.setIcon(new ImageIcon(nHentai.class.getResource("/grafics/Button.png")));
			}

			public void mousePressed(MouseEvent evt) {
				newEntry_panel2_bnt.setIcon(new ImageIcon(nHentai.class.getResource("/grafics/ButtonSelected.png")));
			}

			public void mouseReleased(MouseEvent evt) {
				newEntry_panel2_bnt.setIcon(new ImageIcon(nHentai.class.getResource("/grafics/ButtonHower.png")));
			}
		});
		newEntry_panel2_bnt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				
				actionPerformedNewEntry(tableArrReading, modelReading, responseTime_panel2_lbl, EntryLoader_panel2_PBar, "reading");				
			}
		});
		panel_panel2.add(newEntry_panel2_bnt);

		JButton settings_panel2_bnt = new JButton("");
		settings_panel2_bnt.setBounds(10, 586, 112, 35);
		settings_panel2_bnt.setIcon(new ImageIcon(nHentai.class.getResource("/grafics/MainGUI/settings.png")));
		settings_panel2_bnt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				actionPerformedSetting();
			}
		});
		settings_panel2_bnt.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent evt) {
				settings_panel2_bnt.setIcon(new ImageIcon(nHentai.class.getResource("/grafics/MainGUI/settingsHover.png")));
			}

			public void mouseExited(MouseEvent evt) {
				settings_panel2_bnt.setIcon(new ImageIcon(nHentai.class.getResource("/grafics/MainGUI/settings.png")));
			}

			public void mousePressed(MouseEvent evt) {
				settings_panel2_bnt.setIcon(new ImageIcon(nHentai.class.getResource("/grafics/MainGUI/settingsSelected.png")));
			}

			public void mouseReleased(MouseEvent evt) {
				settings_panel2_bnt.setIcon(new ImageIcon(nHentai.class.getResource("/grafics/MainGUI/settingsHover.png")));
			}
		});
		panel_panel2.add(settings_panel2_bnt);

		JButton saveTable_panel2_btn = new JButton("");
		saveTable_panel2_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(inSearchViewReading == false)
					actionPerformedSafe(tableArrReading);
				else
					actionPerformedSafe(tableArrReadingSave);
			}
		});
		saveTable_panel2_btn.setIcon(new ImageIcon(nHentai.class.getResource("/grafics/saveButton.png")));
		saveTable_panel2_btn.setBounds(10, 552, 80, 25);
		saveTable_panel2_btn.setHorizontalTextPosition(SwingConstants.CENTER);
		saveTable_panel2_btn.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent evt) {
				saveTable_panel2_btn.setIcon(new ImageIcon(nHentai.class.getResource("/grafics/saveButtonHover.png")));
			}

			public void mouseExited(MouseEvent evt) {
				saveTable_panel2_btn.setIcon(new ImageIcon(nHentai.class.getResource("/grafics/saveButton.png")));
			}

			public void mousePressed(MouseEvent evt) {
				saveTable_panel2_btn.setIcon(new ImageIcon(nHentai.class.getResource("/grafics/saveButtonSelected.png")));
			}

			public void mouseReleased(MouseEvent evt) {
				saveTable_panel2_btn.setIcon(new ImageIcon(nHentai.class.getResource("/grafics/saveButtonHover.png")));
			}
		});
		panel_panel2.add(saveTable_panel2_btn);

		JButton loadTable__panel2_btn = new JButton("");
		loadTable__panel2_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tableArrReading = actionPerformedLoad("reading");
				modelReading = methods.ArrToTable(modelReading, tableArrReading, SFW);
			}
		});
		loadTable__panel2_btn.setIcon(new ImageIcon(nHentai.class.getResource("/grafics/loadButton.png")));
		loadTable__panel2_btn.setBounds(100, 552, 80, 25);
		loadTable__panel2_btn.setHorizontalTextPosition(SwingConstants.CENTER);
		loadTable__panel2_btn.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent evt) {
				loadTable__panel2_btn.setIcon(new ImageIcon(nHentai.class.getResource("/grafics/loadButtonHover.png")));
			}

			public void mouseExited(MouseEvent evt) {
				loadTable__panel2_btn.setIcon(new ImageIcon(nHentai.class.getResource("/grafics/loadButton.png")));
			}

			public void mousePressed(MouseEvent evt) {
				loadTable__panel2_btn.setIcon(new ImageIcon(nHentai.class.getResource("/grafics/loadButtonSelected.png")));
			}

			public void mouseReleased(MouseEvent evt) {
				loadTable__panel2_btn.setIcon(new ImageIcon(nHentai.class.getResource("/grafics/loadButtonHover.png")));
			}
		});
		panel_panel2.add(loadTable__panel2_btn);

		JTextField search_panel2_TField = new JTextField();
		search_panel2_TField.setBackground(Color.WHITE);
		search_panel2_TField.setToolTipText("");
		search_panel2_TField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					if(!search_panel2_TField.getText().equals("")) {
						String text = search_panel2_TField.getText();
						actionPerformedSearchReading(text);
					}else {
						for(int i=0;i<tableArrReading.length-1;i++) {
							modelReading.removeRow(0);
						}
						tableArrReading = tableArrReadingSave;
						modelReading = methods.ArrToTable(modelReading, tableArrReading, SFW);
						inSearchViewReading = false;
						search_panel2_TField.setText("");
					}
			}
		});
		search_panel2_TField.setBounds(10, 11, 157, 23);
		panel_panel2.add(search_panel2_TField);
		search_panel2_TField.setColumns(10);
		
		JButton search_panel2_btn = new JButton();
		search_panel2_btn.setHorizontalTextPosition(SwingConstants.CENTER);
		search_panel2_btn.setIcon(new ImageIcon(nHentai.class.getResource("/grafics/searchClear/search.png")));
		search_panel2_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!search_panel2_TField.getText().equals("")) {
					String text = search_panel2_TField.getText();
					actionPerformedSearchReading(text);
				}else {
					for(int i=0;i<tableArrReading.length-1;i++) {
						modelReading.removeRow(0);
					}
					tableArrReading = tableArrReadingSave;
					modelReading = methods.ArrToTable(modelReading, tableArrReading, SFW);
					inSearchViewReading = false;
					search_panel2_TField.setText("");
				}
			}
		});
		search_panel2_btn.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent evt) {
					search_panel2_btn.setIcon(new ImageIcon(nHentai.class.getResource("/grafics/searchClear/searchHover.png")));
			}

			public void mouseExited(MouseEvent evt) {
					search_panel2_btn.setIcon(new ImageIcon(nHentai.class.getResource("/grafics/searchClear/search.png")));
			}

			public void mousePressed(MouseEvent evt) {
					search_panel2_btn.setIcon(new ImageIcon(nHentai.class.getResource("/grafics/searchClear/searchSelected.png")));
			}

			public void mouseReleased(MouseEvent evt) {
					search_panel2_btn.setIcon(new ImageIcon(nHentai.class.getResource("/grafics/searchClear/searchHover.png")));
			}
		});
		search_panel2_btn.setBounds(197, 11, 22, 23);
		panel_panel2.add(search_panel2_btn);
		
		
		JButton clear_panel2_btn = new JButton();
		clear_panel2_btn.setHorizontalTextPosition(SwingConstants.CENTER);
		clear_panel2_btn.setIcon(new ImageIcon(nHentai.class.getResource("/grafics/searchClear/clear.png")));
		clear_panel2_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for(int i=0;i<tableArr.length-1;i++) {
					model.removeRow(0);
				}
				tableArr = tableArrSave;
				methods.ArrToTable(modelReading, tableArrReading, SFW);
				inSearchViewPlanToRead = false;
				search_panel2_TField.setText("");
			}
		});
		clear_panel2_btn.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent evt) {
				clear_panel2_btn.setIcon(new ImageIcon(nHentai.class.getResource("/grafics/searchClear/clearHover.png")));
			}

			public void mouseExited(MouseEvent evt) {
				clear_panel2_btn.setIcon(new ImageIcon(nHentai.class.getResource("/grafics/searchClear/clear.png")));
			}

			public void mousePressed(MouseEvent evt) {
				clear_panel2_btn.setIcon(new ImageIcon(nHentai.class.getResource("/grafics/searchClear/clearSelected.png")));
			}

			public void mouseReleased(MouseEvent evt) {
				clear_panel2_btn.setIcon(new ImageIcon(nHentai.class.getResource("/grafics/searchClear/clearHover.png")));
			}
		});
		clear_panel2_btn.setBounds(169, 11, 22, 23);
		panel_panel2.add(clear_panel2_btn);
		
		searchId_panel2_CBox = new JCheckBox("id");
		searchId_panel2_CBox.setForeground(Color.WHITE);
		searchId_panel2_CBox.setBackground(new Color(0, 44, 88));
		searchId_panel2_CBox.setBounds(10, 64, 52, 23);
		searchId_panel2_CBox.setVisible(false);
		searchId_panel2_CBox.setSelected(true);
		panel_panel2.add(searchId_panel2_CBox);
		
		searchTitle_panel2_CBox = new JCheckBox("title");
		searchTitle_panel2_CBox.setBackground(new Color(0, 44, 88));
		searchTitle_panel2_CBox.setForeground(Color.WHITE);
		searchTitle_panel2_CBox.setBounds(10, 90, 52, 23);
		searchTitle_panel2_CBox.setVisible(false);
		searchTitle_panel2_CBox.setSelected(true);
		panel_panel2.add(searchTitle_panel2_CBox);
		
		searchAuthor_panel2_CBox = new JCheckBox("author");
		searchAuthor_panel2_CBox.setBackground(new Color(0, 44, 88));
		searchAuthor_panel2_CBox.setForeground(Color.WHITE);
		searchAuthor_panel2_CBox.setBounds(83, 64, 68, 23);
		searchAuthor_panel2_CBox.setVisible(false);
		searchAuthor_panel2_CBox.setSelected(true);
		panel_panel2.add(searchAuthor_panel2_CBox);
		
		searchTags_panel2_CBox = new JCheckBox("tags");
		searchTags_panel2_CBox.setBackground(new Color(0, 44, 88));
		searchTags_panel2_CBox.setForeground(Color.WHITE);
		searchTags_panel2_CBox.setBounds(83, 90, 68, 23);
		searchTags_panel2_CBox.setVisible(false);
		searchTags_panel2_CBox.setSelected(true);
		panel_panel2.add(searchTags_panel2_CBox);

		JCheckBox showMore_panel2_CBox = new JCheckBox("show more");
		showMore_panel2_CBox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED) {//checkbox has been selected
					searchId_panel2_CBox.setVisible(true);
					searchTitle_panel2_CBox.setVisible(true);
					searchAuthor_panel2_CBox.setVisible(true);
					searchTags_panel2_CBox.setVisible(true);
				} else {//checkbox has been deselected
					searchId_panel2_CBox.setVisible(false);
					searchTitle_panel2_CBox.setVisible(false);
					searchAuthor_panel2_CBox.setVisible(false);
					searchTags_panel2_CBox.setVisible(false);
				};
			}
		});
		showMore_panel2_CBox.setForeground(Color.WHITE);
		showMore_panel2_CBox.setBackground(new Color(0, 44, 88));
		showMore_panel2_CBox.setBounds(10, 38, 80, 23);
		panel_panel2.add(showMore_panel2_CBox);
		
		JScrollPane scrollPane_panel2 = new JScrollPane();
		scrollPane_panel2.setBounds(250, 11, 666, 632);
		scrollPane_panel2.getViewport().setBackground(new Color(54, 57, 63));
		
		JButton cornerButton_panel2 = new JButton();
		cornerButton_panel2.setIcon(new ImageIcon(nHentai.class.getResource("/grafics/jumpButton/jump.png")));
		cornerButton_panel2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				scrollPane_panel2.getVerticalScrollBar().setValue(0);
			}
			
		});
		cornerButton_panel1.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent evt) {
				cornerButton_panel2.setIcon(new ImageIcon(nHentai.class.getResource("/grafics/jumpButton/jumpHover.png")));
			}

			public void mouseExited(MouseEvent evt) {
				cornerButton_panel2.setIcon(new ImageIcon(nHentai.class.getResource("/grafics/jumpButton/jump.png")));
			}

			public void mousePressed(MouseEvent evt) {
				cornerButton_panel2.setIcon(new ImageIcon(nHentai.class.getResource("/grafics/jumpButton/jumpSelected.png")));
			}

			public void mouseReleased(MouseEvent evt) {
				cornerButton_panel2.setIcon(new ImageIcon(nHentai.class.getResource("/grafics/jumpButton/jumpHover.png")));
			}
		});
		
		scrollPane_panel2.setCorner(JScrollPane.UPPER_TRAILING_CORNER, cornerButton_panel2);
		
		
		JScrollBar scrollBar_panel2 = scrollPane_panel2.getVerticalScrollBar();
        Dimension scrollBarDim_panel2 = new Dimension(15, scrollBar_panel2
              .getPreferredSize().height);
        scrollBar_panel2.setPreferredSize(scrollBarDim_panel2);
        /*scrollBar_panel2.setUI(new BasicScrollBarUI() {
           @Override 
           protected void configureScrollBarColors(){
               this.thumbColor = new Color(10, 10, 10);
               this.thumbDarkShadowColor = new Color(10, 10, 10);
               this.thumbLightShadowColor = new Color(10, 10, 10);
               this.trackColor = new Color(59, 59, 59);
               this.trackHighlightColor = new Color(59, 59, 59);
           }
          @Override
         protected JButton createDecreaseButton(int orientation) {
             return createZeroButton();
         }

         @Override
         protected JButton createIncreaseButton(int orientation) {
             return createZeroButton();
         }
       });*/
        
		
		reading_tab.add(scrollPane_panel2);

		table_panel2 = new JTable(){
	        private static final long serialVersionUID = 1L;

	        public boolean isCellEditable(int row, int column) { 
	        	if(column == 8) {
	        		return true;
	        	}else {
	        		return false;               
	        	}
	        };
	    };
	    table_panel2.setForeground(Color.WHITE);
		table_panel2.setBackground(new Color(54, 57, 63));
		table_panel2.setRowSelectionAllowed(false);
		modelReading = new DefaultTableModel(new Object[][] {

		}, new String[] { "number", "cover", "id", "title", "author", "pages", "rating", "status", "" });
		table_panel2.setModel(modelReading);
		table_panel2.getColumnModel().getColumn(0).setResizable(false);
		table_panel2.getColumnModel().getColumn(0).setPreferredWidth(47);
		table_panel2.getColumnModel().getColumn(1).setResizable(false);
		table_panel2.getColumnModel().getColumn(1).setPreferredWidth(42);
		table_panel2.getColumnModel().getColumn(2).setResizable(false);
		table_panel2.getColumnModel().getColumn(2).setPreferredWidth(65);
		table_panel2.getColumnModel().getColumn(3).setResizable(false);
		table_panel2.getColumnModel().getColumn(3).setPreferredWidth(113);
		table_panel2.getColumnModel().getColumn(4).setResizable(false);
		table_panel2.getColumnModel().getColumn(4).setPreferredWidth(95);
		table_panel2.getColumnModel().getColumn(5).setResizable(false);
		table_panel2.getColumnModel().getColumn(5).setPreferredWidth(65);
		table_panel2.getColumnModel().getColumn(6).setResizable(false);
		table_panel2.getColumnModel().getColumn(6).setPreferredWidth(45);
		table_panel2.getColumnModel().getColumn(7).setResizable(false);
		table_panel2.getColumnModel().getColumn(8).setResizable(false);
		table_panel2.getColumnModel().getColumn(1).setCellRenderer(new TableCellRenderer() {

			@Override
			public Component getTableCellRendererComponent(JTable jtable, Object value, boolean bln, boolean bln1,
					int i, int i1) {
				JLabel lbl = new JLabel();
				lbl.setIcon((ImageIcon) value);
				return lbl;
			}
		});

		table_panel2.getTableHeader().setBackground(Color.RED);
		table_panel2.getTableHeader().setDefaultRenderer(new renderEngine.HeaderColor());
		
		
		ButtonColumnAll buttonColumnTable_panel2 = new ButtonColumnAll(table_panel2, deleteTableArrReadingRow, 8);
		buttonColumnTable_panel2.setMnemonic(KeyEvent.VK_D);
		table_panel2.setRowHeight(71);
		scrollPane_panel2.setViewportView(table_panel2);
		
		EntryLoader_panel2_PBar = new JProgressBar();
		EntryLoader_panel2_PBar.setForeground(Color.DARK_GRAY);
		EntryLoader_panel2_PBar.setBackground(Color.WHITE);
		EntryLoader_panel2_PBar.setBounds(826, 646, 90, 14);
		EntryLoader_panel2_PBar.setVisible(false);
		reading_tab.add(EntryLoader_panel2_PBar);
		
		responseTime_panel2_lbl = new JLabel("nHentai response:");
		responseTime_panel2_lbl.setForeground(Color.GRAY);
		responseTime_panel2_lbl.setBounds(688, 646, 128, 14);
		responseTime_panel2_lbl.setVisible(false);
		reading_tab.add(responseTime_panel2_lbl);
		/*
		 * end panel 2
		 */

		
		/*
		 * start panel 3
		 */
		JPanel completed_tab = new JPanel();
		completed_tab.setBackground(new Color(32, 34, 37));
		tabbedPane.addTab("completed", null, completed_tab, null);
		completed_tab.setLayout(null);
		
		JPanel panel_panel3 = new JPanel();
		panel_panel3.setLayout(null);
		panel_panel3.setBackground(new Color(0, 44, 88));
		panel_panel3.setBounds(10, 11, 230, 632);
		completed_tab.add(panel_panel3);
		
		JButton newEntry_panel3_bnt = new JButton("new Entry");
		newEntry_panel3_bnt.setIcon(new ImageIcon(nHentai.class.getResource("/grafics/Button.png")));
		newEntry_panel3_bnt.setHorizontalTextPosition(SwingConstants.CENTER);
		newEntry_panel3_bnt.setForeground(Color.WHITE);
		newEntry_panel3_bnt.setFont(new Font("Tahoma", Font.PLAIN, 20));
		newEntry_panel3_bnt.setBounds(10, 243, 210, 35);
		newEntry_panel3_bnt.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent evt) {
				newEntry_panel3_bnt.setIcon(new ImageIcon(nHentai.class.getResource("/grafics/ButtonHower.png")));
			}

			public void mouseExited(MouseEvent evt) {
				newEntry_panel3_bnt.setIcon(new ImageIcon(nHentai.class.getResource("/grafics/Button.png")));
			}

			public void mousePressed(MouseEvent evt) {
				newEntry_panel3_bnt.setIcon(new ImageIcon(nHentai.class.getResource("/grafics/ButtonSelected.png")));
			}

			public void mouseReleased(MouseEvent evt) {
				newEntry_panel3_bnt.setIcon(new ImageIcon(nHentai.class.getResource("/grafics/ButtonHower.png")));
			}
		});
		newEntry_panel3_bnt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				
				actionPerformedNewEntry(tableArrCompleted, modelCompleted, responseTime_panel3_lbl, EntryLoader_panel3_PBar, "completed");
				
			}
		});
		panel_panel3.add(newEntry_panel3_bnt);
		
		JButton settings_panel3_bnt = new JButton("");
		settings_panel3_bnt.setIcon(new ImageIcon(nHentai.class.getResource("/grafics/MainGUI/settings.png")));
		settings_panel3_bnt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actionPerformedSetting();
			}
		});
		settings_panel3_bnt.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent evt) {
				settings_panel3_bnt.setIcon(new ImageIcon(nHentai.class.getResource("/grafics/MainGUI/settingsHover.png")));
			}

			public void mouseExited(MouseEvent evt) {
				settings_panel3_bnt.setIcon(new ImageIcon(nHentai.class.getResource("/grafics/MainGUI/settings.png")));
			}

			public void mousePressed(MouseEvent evt) {
				settings_panel3_bnt.setIcon(new ImageIcon(nHentai.class.getResource("/grafics/MainGUI/settingsSelected.png")));
			}

			public void mouseReleased(MouseEvent evt) {
				settings_panel3_bnt.setIcon(new ImageIcon(nHentai.class.getResource("/grafics/MainGUI/settingsHover.png")));
			}
		});
		settings_panel3_bnt.setBounds(10, 586, 112, 35);
		panel_panel3.add(settings_panel3_bnt);
		
		JButton saveTable_panel3_btn = new JButton("");
		saveTable_panel3_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(inSearchViewCompleted == true)
					actionPerformedSafe(tableArrCompleted);
				else
					actionPerformedSafe(tableArrCompletedSave);
			}
		});
		saveTable_panel3_btn.setIcon(new ImageIcon(nHentai.class.getResource("/grafics/saveButton.png")));
		saveTable_panel3_btn.setHorizontalTextPosition(SwingConstants.CENTER);
		saveTable_panel3_btn.setForeground(Color.WHITE);
		saveTable_panel3_btn.setFont(new Font("Tahoma", Font.PLAIN, 20));
		saveTable_panel3_btn.setBounds(10, 552, 80, 25);
		saveTable_panel3_btn.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent evt) {
				saveTable_panel3_btn.setIcon(new ImageIcon(nHentai.class.getResource("/grafics/saveButtonHover.png")));
			}

			public void mouseExited(MouseEvent evt) {
				saveTable_panel3_btn.setIcon(new ImageIcon(nHentai.class.getResource("/grafics/saveButton.png")));
			}

			public void mousePressed(MouseEvent evt) {
				saveTable_panel3_btn.setIcon(new ImageIcon(nHentai.class.getResource("/grafics/saveButtonSelected.png")));
			}

			public void mouseReleased(MouseEvent evt) {
				saveTable_panel3_btn.setIcon(new ImageIcon(nHentai.class.getResource("/grafics/saveButtonHover.png")));
			}
		});
		panel_panel3.add(saveTable_panel3_btn);
		
		JButton loadTable__panel3_btn = new JButton("");
		loadTable__panel3_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				tableArrCompleted = actionPerformedLoad("completed");
				modelCompleted = methods.ArrToTable(modelCompleted, tableArrCompleted, SFW);
			}
		});
		loadTable__panel3_btn.setIcon(new ImageIcon(nHentai.class.getResource("/grafics/loadButton.png")));
		loadTable__panel3_btn.setHorizontalTextPosition(SwingConstants.CENTER);
		loadTable__panel3_btn.setForeground(Color.WHITE);
		loadTable__panel3_btn.setFont(new Font("Tahoma", Font.PLAIN, 20));
		loadTable__panel3_btn.setBounds(100, 552, 80, 25);
		loadTable__panel3_btn.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent evt) {
				loadTable__panel3_btn.setIcon(new ImageIcon(nHentai.class.getResource("/grafics/loadButtonHover.png")));
			}

			public void mouseExited(MouseEvent evt) {
				loadTable__panel3_btn.setIcon(new ImageIcon(nHentai.class.getResource("/grafics/loadButton.png")));
			}

			public void mousePressed(MouseEvent evt) {
				loadTable__panel3_btn.setIcon(new ImageIcon(nHentai.class.getResource("/grafics/loadButtonSelected.png")));
			}

			public void mouseReleased(MouseEvent evt) {
				loadTable__panel3_btn.setIcon(new ImageIcon(nHentai.class.getResource("/grafics/loadButtonHover.png")));
			}
		});
		panel_panel3.add(loadTable__panel3_btn);
		
		JTextField search_panel3_TField = new JTextField();
		search_panel3_TField.setBackground(Color.WHITE);
		search_panel3_TField.setToolTipText("");
		search_panel3_TField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					if(!search_panel3_TField.getText().equals("")) {
						String text = search_panel3_TField.getText();
						actionPerformedSearchCompleted(text);
					}else {
						for(int i=0;i<tableArrCompleted.length-1;i++) {
							modelCompleted.removeRow(0);
						}
						tableArrCompleted = tableArrCompletedSave;
						modelCompleted = methods.ArrToTable(modelCompleted, tableArrCompleted, SFW);
						inSearchViewCompleted = false;
						search_panel3_TField.setText("");
					}
			}
		});
		search_panel3_TField.setBounds(10, 11, 157, 23);
		panel_panel3.add(search_panel3_TField);
		search_panel3_TField.setColumns(10);
		
		JButton search_panel3_btn = new JButton();
		search_panel3_btn.setHorizontalTextPosition(SwingConstants.CENTER);
		search_panel3_btn.setIcon(new ImageIcon(nHentai.class.getResource("/grafics/searchClear/search.png")));
		search_panel3_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!search_panel3_TField.getText().equals("")) {
					String text = search_panel3_TField.getText();
					actionPerformedSearchCompleted(text);
				}else {
					for(int i=0;i<tableArrCompleted.length-1;i++) {
						modelCompleted.removeRow(0);
					}
					tableArrCompleted = tableArrCompletedSave;
					modelCompleted = methods.ArrToTable(modelCompleted, tableArrCompleted, SFW);
					inSearchViewCompleted = false;
					search_panel3_TField.setText("");
				}
			}
		});
		search_panel3_btn.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent evt) {
					search_panel3_btn.setIcon(new ImageIcon(nHentai.class.getResource("/grafics/searchClear/searchHover.png")));
			}

			public void mouseExited(MouseEvent evt) {
					search_panel3_btn.setIcon(new ImageIcon(nHentai.class.getResource("/grafics/searchClear/search.png")));
			}

			public void mousePressed(MouseEvent evt) {
					search_panel3_btn.setIcon(new ImageIcon(nHentai.class.getResource("/grafics/searchClear/searchSelected.png")));
			}

			public void mouseReleased(MouseEvent evt) {
					search_panel3_btn.setIcon(new ImageIcon(nHentai.class.getResource("/grafics/searchClear/searchHover.png")));
			}
		});
		search_panel3_btn.setBounds(197, 11, 22, 23);
		panel_panel3.add(search_panel3_btn);
		
		
		JButton clear_panel3_btn = new JButton();
		clear_panel3_btn.setHorizontalTextPosition(SwingConstants.CENTER);
		clear_panel3_btn.setIcon(new ImageIcon(nHentai.class.getResource("/grafics/searchClear/clear.png")));
		clear_panel3_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for(int i=0;i<tableArrCompleted.length-1;i++) {
					modelCompleted.removeRow(0);
				}
				tableArrCompleted = tableArrCompletedSave;
				modelCompleted = methods.ArrToTable(modelCompleted, tableArrCompleted, SFW);
				inSearchViewCompleted = false;
				search_panel3_TField.setText("");
			}
		});
		clear_panel3_btn.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent evt) {
				clear_panel3_btn.setIcon(new ImageIcon(nHentai.class.getResource("/grafics/searchClear/clearHover.png")));
			}

			public void mouseExited(MouseEvent evt) {
				clear_panel3_btn.setIcon(new ImageIcon(nHentai.class.getResource("/grafics/searchClear/clear.png")));
			}

			public void mousePressed(MouseEvent evt) {
				clear_panel3_btn.setIcon(new ImageIcon(nHentai.class.getResource("/grafics/searchClear/clearSelected.png")));
			}

			public void mouseReleased(MouseEvent evt) {
				clear_panel3_btn.setIcon(new ImageIcon(nHentai.class.getResource("/grafics/searchClear/clearHover.png")));
			}
		});
		clear_panel3_btn.setBounds(169, 11, 22, 23);
		panel_panel3.add(clear_panel3_btn);
		
		searchId_panel3_CBox = new JCheckBox("id");
		searchId_panel3_CBox.setForeground(Color.WHITE);
		searchId_panel3_CBox.setBackground(new Color(0, 44, 88));
		searchId_panel3_CBox.setBounds(10, 64, 52, 23);
		searchId_panel3_CBox.setVisible(false);
		searchId_panel3_CBox.setSelected(true);
		panel_panel3.add(searchId_panel3_CBox);
		
		searchTitle_panel3_CBox = new JCheckBox("title");
		searchTitle_panel3_CBox.setBackground(new Color(0, 44, 88));
		searchTitle_panel3_CBox.setForeground(Color.WHITE);
		searchTitle_panel3_CBox.setBounds(10, 90, 52, 23);
		searchTitle_panel3_CBox.setVisible(false);
		searchTitle_panel3_CBox.setSelected(true);
		panel_panel3.add(searchTitle_panel3_CBox);
		
		searchAuthor_panel3_CBox = new JCheckBox("author");
		searchAuthor_panel3_CBox.setBackground(new Color(0, 44, 88));
		searchAuthor_panel3_CBox.setForeground(Color.WHITE);
		searchAuthor_panel3_CBox.setBounds(83, 64, 68, 23);
		searchAuthor_panel3_CBox.setVisible(false);
		searchAuthor_panel3_CBox.setSelected(true);
		panel_panel3.add(searchAuthor_panel3_CBox);
		
		searchTags_panel3_CBox = new JCheckBox("tags");
		searchTags_panel3_CBox.setBackground(new Color(0, 44, 88));
		searchTags_panel3_CBox.setForeground(Color.WHITE);
		searchTags_panel3_CBox.setBounds(83, 90, 68, 23);
		searchTags_panel3_CBox.setVisible(false);
		searchTags_panel3_CBox.setSelected(true);
		panel_panel3.add(searchTags_panel3_CBox);

		JCheckBox showMore_panel3_CBox = new JCheckBox("show more");
		showMore_panel3_CBox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED) {//checkbox has been selected
					searchId_panel3_CBox.setVisible(true);
					searchTitle_panel3_CBox.setVisible(true);
					searchAuthor_panel3_CBox.setVisible(true);
					searchTags_panel3_CBox.setVisible(true);
				} else {//checkbox has been deselected
					searchId_panel3_CBox.setVisible(false);
					searchTitle_panel3_CBox.setVisible(false);
					searchAuthor_panel3_CBox.setVisible(false);
					searchTags_panel3_CBox.setVisible(false);
				};
			}
		});
		showMore_panel3_CBox.setForeground(Color.WHITE);
		showMore_panel3_CBox.setBackground(new Color(0, 44, 88));
		showMore_panel3_CBox.setBounds(10, 38, 80, 23);
		panel_panel3.add(showMore_panel3_CBox);
		
		JScrollPane scrollPane_panel3 = new JScrollPane();
		scrollPane_panel3.getViewport().setBackground(new Color(54, 57, 63));
		scrollPane_panel3.setBounds(250, 11, 666, 632);
		
		JButton cornerButton_panel3 = new JButton();
		cornerButton_panel3.setIcon(new ImageIcon(nHentai.class.getResource("/grafics/jumpButton/jump.png")));
		cornerButton_panel3.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				scrollPane_panel3.getVerticalScrollBar().setValue(0);
			}
			
		});
		//je
		cornerButton_panel1.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent evt) {
				cornerButton_panel3.setIcon(new ImageIcon(nHentai.class.getResource("/grafics/jumpButton/jumpHover.png")));
			}

			public void mouseExited(MouseEvent evt) {
				cornerButton_panel3.setIcon(new ImageIcon(nHentai.class.getResource("/grafics/jumpButton/jump.png")));
			}

			public void mousePressed(MouseEvent evt) {
				cornerButton_panel3.setIcon(new ImageIcon(nHentai.class.getResource("/grafics/jumpButton/jumpSelected.png")));
			}

			public void mouseReleased(MouseEvent evt) {
				cornerButton_panel3.setIcon(new ImageIcon(nHentai.class.getResource("/grafics/jumpButton/jumpHover.png")));
			}
		});
		
		scrollPane_panel3.setCorner(JScrollPane.UPPER_TRAILING_CORNER, cornerButton_panel3);
		
		JScrollBar scrollBar_panel3 = scrollPane_panel3.getVerticalScrollBar();
        Dimension scrollBarDim_panel3 = new Dimension(15, scrollBar_panel3
              .getPreferredSize().height);
        scrollBar_panel3.setPreferredSize(scrollBarDim_panel3);
        /*scrollBar_panel3.setUI(new BasicScrollBarUI() {
           @Override 
           protected void configureScrollBarColors(){
               this.thumbColor = new Color(10, 10, 10);
               this.thumbDarkShadowColor = new Color(10, 10, 10);
               this.thumbLightShadowColor = new Color(10, 10, 10);
               this.trackColor = new Color(59, 59, 59);
               this.trackHighlightColor = new Color(59, 59, 59);
           }
          @Override
         protected JButton createDecreaseButton(int orientation) {
             return createZeroButton();
         }

         @Override
         protected JButton createIncreaseButton(int orientation) {
             return createZeroButton();
         }
       });*/
		
		completed_tab.add(scrollPane_panel3);
		
		table_panel3 = new JTable(){
	        private static final long serialVersionUID = 1L;

	        public boolean isCellEditable(int row, int column) { 
	        	if(column == 9) {
	        		return true;
	        	}else {
	        		return false;               
	        	}
	        };
	    };
	    table_panel3.setForeground(Color.WHITE);
	    table_panel3.setBackground(new Color(54, 57, 63));
	    table_panel3.setRowSelectionAllowed(false);
		modelCompleted = new DefaultTableModel(new Object[][] {

		}, new String[] { "number", "cover", "id", "title", "author", "pages", "rating", "times Read", "status", "" });
		table_panel3.setModel(modelCompleted);
		table_panel3.getColumnModel().getColumn(0).setResizable(false);
		table_panel3.getColumnModel().getColumn(0).setPreferredWidth(47);
		table_panel3.getColumnModel().getColumn(1).setResizable(false);
		table_panel3.getColumnModel().getColumn(1).setPreferredWidth(42);
		table_panel3.getColumnModel().getColumn(2).setResizable(false);
		table_panel3.getColumnModel().getColumn(2).setPreferredWidth(60);
		table_panel3.getColumnModel().getColumn(3).setResizable(false);
		table_panel3.getColumnModel().getColumn(3).setPreferredWidth(100);
		table_panel3.getColumnModel().getColumn(4).setResizable(false);
		table_panel3.getColumnModel().getColumn(4).setPreferredWidth(79);
		table_panel3.getColumnModel().getColumn(5).setResizable(false);
		table_panel3.getColumnModel().getColumn(5).setPreferredWidth(50);
		table_panel3.getColumnModel().getColumn(6).setResizable(false);
		table_panel3.getColumnModel().getColumn(6).setPreferredWidth(46);
		table_panel3.getColumnModel().getColumn(7).setResizable(false);
		table_panel3.getColumnModel().getColumn(7).setPreferredWidth(64);
		table_panel3.getColumnModel().getColumn(8).setResizable(false);
		table_panel3.getColumnModel().getColumn(8).setPreferredWidth(59);
		table_panel3.getColumnModel().getColumn(9).setResizable(false);
		table_panel3.getColumnModel().getColumn(1).setCellRenderer(new TableCellRenderer() {

			@Override
			public Component getTableCellRendererComponent(JTable jtable, Object value, boolean bln, boolean bln1,
					int i, int i1) {
				JLabel lbl = new JLabel();
				lbl.setIcon((ImageIcon) value);
				return lbl;
			}
		});
		table_panel3.getTableHeader().setBackground(Color.RED);
		table_panel3.getTableHeader().setDefaultRenderer(new renderEngine.HeaderColor());
		
		ButtonColumnAll buttonColumnTable_panel3 = new ButtonColumnAll(table_panel3, deleteTableArrCompletedRow, 9);
		buttonColumnTable_panel3.setMnemonic(KeyEvent.VK_D);
		table_panel3.setRowHeight(71);
		scrollPane_panel3.setViewportView(table_panel3);
		
		EntryLoader_panel3_PBar = new JProgressBar();
		EntryLoader_panel3_PBar.setForeground(Color.DARK_GRAY);
		EntryLoader_panel3_PBar.setBackground(Color.WHITE);
		EntryLoader_panel3_PBar.setBounds(826, 646, 90, 14);
		EntryLoader_panel3_PBar.setVisible(false);
		completed_tab.add(EntryLoader_panel3_PBar);
		
		responseTime_panel3_lbl = new JLabel("nHentai response:");
		responseTime_panel3_lbl.setForeground(Color.GRAY);
		responseTime_panel3_lbl.setBounds(688, 646, 128, 14);
		responseTime_panel3_lbl.setVisible(false);
		completed_tab.add(responseTime_panel3_lbl);

		/*
		 * end panel 3
		 */
		
	}

	
	

	

	

	

	

	

	
	
	
	Action deleteTableArrRow = new AbstractAction()
	{
	    public void actionPerformed(ActionEvent e)
	    {
	        JTable table = (JTable)e.getSource();
	        int modelRow = Integer.valueOf( e.getActionCommand() );
	        
	        title = tableArr[modelRow][3];
			id = tableArr[modelRow][2];
			tags = tableArr[modelRow][9];
			artist = tableArr[modelRow][4];
			pages = tableArr[modelRow][5];
			rating = tableArr[modelRow][8];
			status = tableArr[modelRow][6];
			URL = tableArr[modelRow][1];
			timesRead = tableArr[modelRow][7];

			String photoLocation;
			if(SFW) {
				int random = (int)(Math.random()*200);
				photoLocation = appdataLocation + mainFolderLocation + randomPhotoFolderLocation + Slash + random + "_medium.jpg";
			}else {
				photoLocation = appdataLocation + mainFolderLocation + photoFolderLocation + Slash + id + "_medium.jpg";
			}
			
			
			moreInformationPanel moreInformation = new moreInformationPanel(id, title, artist, pages, rating,
					timesRead, status, tags,
					photoLocation, SFW, resScroll);
			
			Component[] buttonText = methods.OKCancelButtonCreate();
			
			if(resScroll == true)
				UIManager.put("OptionPane.minimumSize", new Dimension(500, 600));
			else
				UIManager.put("OptionPane.minimumSize", new Dimension(500, 900));
			
			JOptionPane inspectPane = new JOptionPane(moreInformation, JOptionPane.PLAIN_MESSAGE,
					JOptionPane.OK_OPTION, null, buttonText, null);
			
			final JDialog dialog = new JDialog((Frame)null, "Boo");
			
	        inspectPane.addPropertyChangeListener(new PropertyChangeListener() {
	            @Override
	            public void propertyChange(PropertyChangeEvent evt) {
	                String name = evt.getPropertyName();
	                if ("value".equals(name)) {
	                	dialog.dispose();
	                    final Object value = inspectPane.getValue();
	                    System.out.println(value);
	                    if (value.equals("OK")) {
	        				
	        				safed = false;
	        				
	        				rating = moreInformation.getRating();
	        				timesRead = moreInformation.getTimesRead();
	        				boolean deleteEntry = moreInformation.getDeleteEntry();
	        				
	        				
	        				tableArr[modelRow][8] = rating;
	        				tableArr[modelRow][7] = timesRead;

	        				String newStatus = moreInformation.getStatus();
	        				
	        				if(deleteEntry == true) {
	        					((DefaultTableModel) table_panel1.getModel()).removeRow(modelRow);
	        					tableArr = methods.rearangeArr(tableArr, modelRow);
	        					tableArr = methods.newArrIndex(tableArr);
	        					for(int i=0;i<tableArr.length-1;i++) {
	        						table_panel1.setValueAt(tableArr[i][0], i, 0);
	        					}
	        				}
	        				else if (newStatus.equals("reading")) {
	        					//actionPerformedNewStatusReading(modelRow, URL, title, id, tags, artist, pages, rating, timesRead);
	        					((DefaultTableModel) table_panel1.getModel()).removeRow(modelRow);
	        					tableArr = methods.rearangeArr(tableArr, modelRow);
	        					tableArr = methods.newArrIndex(tableArr);
	        					for(int i=0;i<tableArr.length-1;i++) {
	        						table_panel1.setValueAt(tableArr[i][0], i, 0);
	        					}
	        					tableArrReading[tableArrReading.length - 1][1] = URL;
	        					tableArrReading[tableArrReading.length - 1][3] = title;
	        					tableArrReading[tableArrReading.length - 1][2] = id;
	        					tableArrReading[tableArrReading.length - 1][9] = tags;
	        					tableArrReading[tableArrReading.length - 1][4] = artist;
	        					tableArrReading[tableArrReading.length - 1][5] = pages;
	        					tableArrReading[tableArrReading.length - 1][8] = timesRead;
	        					tableArrReading[tableArrReading.length - 1][6] = rating;
	        					tableArrReading[tableArrReading.length - 1][7] = "reading";

	        					tableArrReading = methods.expandArr(tableArrReading);
	        					methods.expandTable(tableArrReading, modelReading, id, SFW);
	        					
	        				}
	        				else if(newStatus.equals("completed")) {
	        					//actionPerformedNewStatusCompleted(modelRow, URL, title, id, tags, artist, pages, rating, timesRead);
	        					((DefaultTableModel) table_panel1.getModel()).removeRow(modelRow);
	        					tableArr = methods.rearangeArr(tableArr, modelRow);
	        					tableArr = methods.newArrIndex(tableArr);
	        					for(int i=0;i<tableArr.length-1;i++) {
	        						table_panel1.setValueAt(tableArr[i][0], i, 0);
	        					}
	        					tableArrCompleted[tableArrCompleted.length - 1][1] = URL;
	        					tableArrCompleted[tableArrCompleted.length - 1][3] = title;
	        					tableArrCompleted[tableArrCompleted.length - 1][2] = id;
	        					tableArrCompleted[tableArrCompleted.length - 1][9] = tags;
	        					tableArrCompleted[tableArrCompleted.length - 1][4] = artist;
	        					tableArrCompleted[tableArrCompleted.length - 1][5] = pages;
	        					tableArrCompleted[tableArrCompleted.length - 1][8] = "completed";
	        					tableArrCompleted[tableArrCompleted.length - 1][6] = rating;
	        					tableArrCompleted[tableArrCompleted.length - 1][7] = String.valueOf(Integer.valueOf(timesRead) + 1);

	        					tableArrCompleted = methods.expandArr(tableArrCompleted);
	        					methods.expandTable(tableArrCompleted, modelCompleted, id, SFW);
	        				}
	        			}
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
			dialogcloseWindow_btn.setIcon(new ImageIcon(nHentai.class.getResource("/grafics/Close.png")));
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
			
			System.out.println("pressed" + e.getActionCommand());
			
			
			
	    }
	};
	
	Action deleteTableArrReadingRow = new AbstractAction()
	{
	    public void actionPerformed(ActionEvent e)
	    {
	        JTable table = (JTable)e.getSource();
	        int modelRow = Integer.valueOf( e.getActionCommand() );
	        
	        title = tableArrReading[modelRow][3];
			id = tableArrReading[modelRow][2];
			tags = tableArrReading[modelRow][9];
			artist = tableArrReading[modelRow][4];
			pages = tableArrReading[modelRow][5];
			rating = tableArrReading[modelRow][6];
			status = tableArrReading[modelRow][7];
			URL = tableArrReading[modelRow][1];
			timesRead = tableArrReading[modelRow][8];

			String photoLocation;
			if(SFW) {
				int random = (int)(Math.random()*200);
				photoLocation = appdataLocation + mainFolderLocation + randomPhotoFolderLocation + Slash + random + "_medium.jpg";
			}else {
				photoLocation = appdataLocation + mainFolderLocation + photoFolderLocation + Slash + id + "_medium.jpg";
			}
			
			moreInformationPanel moreInformation = new moreInformationPanel(id, title, artist, pages, rating,
					timesRead, status, tags,
					photoLocation, SFW, resScroll);
			
			Component[] buttonText = methods.OKCancelButtonCreate();
			
			if(resScroll == true)
				UIManager.put("OptionPane.minimumSize", new Dimension(500, 600));
			else
				UIManager.put("OptionPane.minimumSize", new Dimension(500, 900));
			
			JOptionPane inspectPane = new JOptionPane(moreInformation, JOptionPane.PLAIN_MESSAGE,
					JOptionPane.OK_OPTION, null, buttonText, null);
			
			final JDialog dialog = new JDialog((Frame)null, "Boo");
			
			inspectPane.addPropertyChangeListener(new PropertyChangeListener() {
	            @Override
	            public void propertyChange(PropertyChangeEvent evt) {
	                String name = evt.getPropertyName();
	                if ("value".equals(name)) {
	                	dialog.dispose();
	                    final Object value = inspectPane.getValue();
	                    System.out.println(value);
	                    if (value.equals("OK")) {
	        				
	        				safed = false;
	        				
	        				rating = moreInformation.getRating();
	        				timesRead = moreInformation.getTimesRead();
	        				boolean deleteEntry = moreInformation.getDeleteEntry();
	        				
	        				
	        				tableArrReading[modelRow][6] = rating;
	        				tableArrReading[modelRow][8] = timesRead;

	        				String newStatus = moreInformation.getStatus();
	        				
	        				if(deleteEntry == true) {
	        					((DefaultTableModel) table_panel2.getModel()).removeRow(modelRow);
	        					tableArrReading = methods.rearangeArr(tableArrReading, modelRow);
	        					tableArrReading = methods.newArrIndex(tableArrReading);
	        					for(int i=0;i<tableArr.length-1;i++) {
	        						table_panel2.setValueAt(tableArrReading[i][0], i, 0);
	        					}
	        				}
	        				else if (newStatus.equals("plan to read")) {
	        					//actionPerformedNewStatusPlanToRead(modelRow, URL, title, id, tags, artist, pages, rating, timesRead);
	        					((DefaultTableModel) table_panel2.getModel()).removeRow(modelRow);
	        					tableArrReading = methods.rearangeArr(tableArrReading, modelRow);
	        					tableArrReading = methods.newArrIndex(tableArrReading);
	        					for(int i=0;i<tableArrReading.length-1;i++) {
	        						table_panel2.setValueAt(tableArrReading[i][0], i, 0);
	        					}
	        					tableArr[tableArr.length - 1][1] = URL;
	        					tableArr[tableArr.length - 1][3] = title;
	        					tableArr[tableArr.length - 1][2] = id;
	        					tableArr[tableArr.length - 1][9] = tags;
	        					tableArr[tableArr.length - 1][4] = artist;
	        					tableArr[tableArr.length - 1][5] = pages;
	        					tableArr[tableArr.length - 1][6] = "plan to read";
	        					tableArr[tableArr.length - 1][8] = rating;
	        					tableArr[tableArr.length - 1][7] = timesRead;

	        					tableArr = methods.expandArr(tableArr);
	        					methods.expandTable(tableArr, model, id, SFW);
	        				}
	        				else if(newStatus.equals("completed")) {
	        					//actionPerformedNewStatusCompleted(modelRow, URL, title, id, tags, artist, pages, rating, timesRead);
	        					((DefaultTableModel) table_panel2.getModel()).removeRow(modelRow);
	        					tableArrReading = methods.rearangeArr(tableArrReading, modelRow);
	        					tableArrReading = methods.newArrIndex(tableArrReading);
	        					for(int i=0;i<tableArrReading.length-1;i++) {
	        						table_panel2.setValueAt(tableArrReading[i][0], i, 0);
	        					}
	        					tableArrCompleted[tableArrCompleted.length - 1][1] = URL;
	        					tableArrCompleted[tableArrCompleted.length - 1][3] = title;
	        					tableArrCompleted[tableArrCompleted.length - 1][2] = id;
	        					tableArrCompleted[tableArrCompleted.length - 1][9] = tags;
	        					tableArrCompleted[tableArrCompleted.length - 1][4] = artist;
	        					tableArrCompleted[tableArrCompleted.length - 1][5] = pages;
	        					tableArrCompleted[tableArrCompleted.length - 1][8] = "completed";
	        					tableArrCompleted[tableArrCompleted.length - 1][6] = rating;
	        					tableArrCompleted[tableArrCompleted.length - 1][7] = String.valueOf(Integer.valueOf(timesRead) + 1);

	        					tableArrCompleted = methods.expandArr(tableArrCompleted);
	        					methods.expandTable(tableArrCompleted, modelCompleted, id, SFW);
	        				}
	        			}
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
			dialogcloseWindow_btn.setIcon(new ImageIcon(nHentai.class.getResource("/grafics/Close.png")));
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
			
			System.out.println("pressed" + e.getActionCommand());

			
	    }
	};
	
	Action deleteTableArrCompletedRow = new AbstractAction()
	{
	    public void actionPerformed(ActionEvent e)
	    {
	        JTable table = (JTable)e.getSource();
	        int modelRow = Integer.valueOf( e.getActionCommand() );
	        
	        title = tableArrCompleted[modelRow][3];
			id = tableArrCompleted[modelRow][2];
			tags = tableArrCompleted[modelRow][9];
			artist = tableArrCompleted[modelRow][4];
			pages = tableArrCompleted[modelRow][5];
			rating = tableArrCompleted[modelRow][6];
			status = tableArrCompleted[modelRow][8];
			URL = tableArrCompleted[modelRow][1];
			timesRead = tableArrCompleted[modelRow][7];

			String photoLocation;
			if(SFW) {
				int random = (int)(Math.random()*200);
				photoLocation = appdataLocation + mainFolderLocation + randomPhotoFolderLocation + Slash + random + "_medium.jpg";
			}else {
				photoLocation = appdataLocation + mainFolderLocation + photoFolderLocation + Slash + id + "_medium.jpg";
			}
			
			moreInformationPanel moreInformation = new moreInformationPanel(id, title, artist, pages, rating,
					timesRead, status, tags,
					photoLocation, SFW, resScroll);
			
			Component[] buttonText = methods.OKCancelButtonCreate();
			
			if(resScroll == true)
				UIManager.put("OptionPane.minimumSize", new Dimension(500, 600));
			else
				UIManager.put("OptionPane.minimumSize", new Dimension(500, 900));
			JOptionPane inspectPane = new JOptionPane(moreInformation, JOptionPane.PLAIN_MESSAGE,
					JOptionPane.OK_OPTION, null, buttonText, null);
			
			final JDialog dialog = new JDialog((Frame)null, "Boo");
			
			inspectPane.addPropertyChangeListener(new PropertyChangeListener() {
	            @Override
	            public void propertyChange(PropertyChangeEvent evt) {
	                String name = evt.getPropertyName();
	                if ("value".equals(name)) {
	                	dialog.dispose();
	                    final Object value = inspectPane.getValue();
	                    System.out.println(value);
	                    if (value.equals("OK")) {
	        				
	        				safed = false;
	        				
	        				rating = moreInformation.getRating();
	        				timesRead = moreInformation.getTimesRead();
	        				boolean deleteEntry = moreInformation.getDeleteEntry();
	        				
	        				
	        				tableArrCompleted[modelRow][6] = rating;
	        				tableArrCompleted[modelRow][7] = timesRead;

	        				String newStatus = moreInformation.getStatus();
	        				
	        				if(deleteEntry == true) {
	        					((DefaultTableModel) table_panel3.getModel()).removeRow(modelRow);
	        					tableArrCompleted = methods.rearangeArr(tableArrCompleted, modelRow);
	        					tableArrCompleted = methods.newArrIndex(tableArrCompleted);
	        					for(int i=0;i<tableArrCompleted.length-1;i++) {
	        						table_panel3.setValueAt(tableArrCompleted[i][0], i, 0);
	        					}
	        				}
	        				else if (newStatus.equals("plan to read")) {
	        					//actionPerformedNewStatusPlanToRead(modelRow, URL, title, id, tags, artist, pages, rating, timesRead);
	        					((DefaultTableModel) table_panel3.getModel()).removeRow(modelRow);
	        					tableArrCompleted = methods.rearangeArr(tableArrReading, modelRow);
	        					tableArrCompleted = methods.newArrIndex(tableArrCompleted);
	        					for(int i=0;i<tableArrCompleted.length-1;i++) {
	        						table_panel3.setValueAt(tableArrCompleted[i][0], i, 0);
	        					}
	        					tableArr[tableArr.length - 1][1] = URL;
	        					tableArr[tableArr.length - 1][3] = title;
	        					tableArr[tableArr.length - 1][2] = id;
	        					tableArr[tableArr.length - 1][9] = tags;
	        					tableArr[tableArr.length - 1][4] = artist;
	        					tableArr[tableArr.length - 1][5] = pages;
	        					tableArr[tableArr.length - 1][6] = "plan to read";
	        					tableArr[tableArr.length - 1][8] = rating;
	        					tableArr[tableArr.length - 1][7] = timesRead;

	        					tableArr = methods.expandArr(tableArr);
	        					methods.expandTable(tableArr, model, id, SFW);
	        				}
	        				else if (newStatus.equals("reading")) {
	        					//actionPerformedNewStatusReading(modelRow, URL, title, id, tags, artist, pages, rating, timesRead);
	        					((DefaultTableModel) table_panel3.getModel()).removeRow(modelRow);
	        					tableArrCompleted = methods.rearangeArr(tableArrCompleted, modelRow);
	        					tableArrCompleted = methods.newArrIndex(tableArrCompleted);
	        					for(int i=0;i<tableArr.length-1;i++) {
	        						table_panel3.setValueAt(tableArrCompleted[i][0], i, 0);
	        					}
	        					tableArrReading[tableArrReading.length - 1][1] = URL;
	        					tableArrReading[tableArrReading.length - 1][3] = title;
	        					tableArrReading[tableArrReading.length - 1][2] = id;
	        					tableArrReading[tableArrReading.length - 1][9] = tags;
	        					tableArrReading[tableArrReading.length - 1][4] = artist;
	        					tableArrReading[tableArrReading.length - 1][5] = pages;
	        					tableArrReading[tableArrReading.length - 1][8] = timesRead;
	        					tableArrReading[tableArrReading.length - 1][6] = rating;
	        					tableArrReading[tableArrReading.length - 1][7] = "reading";

	        					tableArrReading = methods.expandArr(tableArrReading);
	        					methods.expandTable(tableArrReading, modelReading, id, SFW);
	        					
	        				}
	        			}
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
			dialogcloseWindow_btn.setIcon(new ImageIcon(nHentai.class.getResource("/grafics/Close.png")));
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
	        
			System.out.println("pressed" + e.getActionCommand());

			
	    }
	};

	public void actionPerformedNewEntry(String[][] Arr, DefaultTableModel Model, JLabel label, JProgressBar Bar, String wichTable) {
		
		newEntryGeneral EntryGeneral = new newEntryGeneral(wichTable);
		UIManager.put("OptionPane.minimumSize", new Dimension(400, 550));
		Component[] buttonText = methods.OKCancelButtonCreate();
		JOptionPane pane2 = new JOptionPane(EntryGeneral, JOptionPane.PLAIN_MESSAGE,
				JOptionPane.OK_CANCEL_OPTION, null, buttonText, null);
		
		final JDialog dialog = new JDialog((Frame)null, "Boo");
		
        pane2.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                String name = evt.getPropertyName();
                if ("value".equals(name)) {

                    dialog.dispose();
                    final Object value = pane2.getValue();
                    System.out.println(value);
                    if (value.equals("OK")) {
            			String code = EntryGeneral.getCode();
            			String URL = EntryGeneral.getURL();
            			String rating = EntryGeneral.getRating();
            			String status = EntryGeneral.getStatus();
            			
            			
            			
            			boolean selected = EntryGeneral.getSelected();
            			
            			safed = false;
            			
            					
            					class Task extends SwingWorker<Void, Void> {
            						@Override
            						protected Void doInBackground() throws Exception {
            							int index;
            							if ((!code.equals("") || !URL.equals(""))) {
            								
            								
            								try {
            									System.out.println("§");
            									switch(status) {
            										case "plan to read":
            											index = methods.checkIfIdExists(Arr, code);
            											if(index == -1) {
	            											doujinObj obj = nHentaiAPIRun.nHentaiAPIRun(appdataLocation + mainFolderLocation + photoFolderLocation, code, "", rating, "plan to read", new nHentaiWebBase());
	            											tableArr = methods.insertObjInTableArr(tableArr, obj);
	                    									model = methods.expandTable(tableArr, Model, code, SFW);
            											}else
            												tableArr[index][7] = String.valueOf(Integer.valueOf(tableArr[index][7]) +1);
                    									break;
            										case "reading":
            											index = methods.checkIfIdExists(Arr, code);
            											if(index == -1) {
            												doujinObj obj = nHentaiAPIRun.nHentaiAPIRunReading(appdataLocation + mainFolderLocation + photoFolderLocation, code, "", rating, "reading", new nHentaiWebBase());
            												tableArrReading = methods.insertObjInTableArr(tableArrReading, obj);
	                    									modelReading = methods.expandTable(tableArrReading, Model, code, SFW);
            											}else
            												tableArrReading[index][8] = String.valueOf(Integer.valueOf(tableArrReading[index][8]) +1);
                    									break;
            										case "completed":
            											index = methods.checkIfIdExists(Arr, code);
            											if(index == -1) {
            												doujinObj obj = nHentaiAPIRun.nHentaiAPIRunCompleted(appdataLocation + mainFolderLocation + photoFolderLocation, code, "", rating, "completed", new nHentaiWebBase());
            												tableArrCompleted = methods.insertObjInTableArr(tableArrCompleted, obj);
	                    									modelCompleted = methods.expandTable(tableArrCompleted, Model, code, SFW);
            											}else
            												tableArrCompleted[index][7] = String.valueOf(Integer.valueOf(tableArrCompleted[index][7]) +1);
                    									break;
            									}
            									long time = nHentaiAPIRun.getInitTime();
            									String unit = "ms";
            									time  = time / 1000000;
            									if(time > 999) {
            										time = time / 1000;
            										unit = "s";
            									}
            										
            									label.setText("nHentai response: " + time + unit);
            								} catch (IOException e) {
            									UIManager.put("OptionPane.minimumSize", new Dimension(200, 100));
            									Error errorPanel = new Error(code);
            									JOptionPane error = new JOptionPane();
            									error.showMessageDialog(null, errorPanel, "error", 0);
            									e.printStackTrace();
            								}
            							}
            							if (selected == true) {
            								String[] TextAreaData = EntryGeneral.getDataInTextArea();
            								doujinObj[] threadPoolResult = new doujinObj[TextAreaData.length];
            								
            								class TaskPool implements Runnable{
            									
            									int taskNum;
            									
            									public TaskPool(int i) {
            										taskNum = i;
            										System.out.println(i);
            									}

												@Override
												public void run(){
													// TODO Auto-generated method stub
													int index2;
            										

            										String rawData = TextAreaData[taskNum];
                									String rawCode = "";
                									String rawRating = "";
                									boolean ratingTurn = false;
                									char[] rawDataChar = rawData.toCharArray();
                									for (int j = 0; j < rawDataChar.length; j++) {
                										if (ratingTurn == true) {
                											rawRating = rawRating + rawDataChar[j];
                										}
                										if (rawDataChar[j] == ' ') {
                											ratingTurn = true;
                										} else if (ratingTurn == false) {
                											rawCode = rawCode + rawDataChar[j];
                										}
                									}
                									if(!rawRating.equals("") && rawRating.substring(rawRating.length()-1).equals(" "))
                										rawRating = rawRating.substring(0, rawRating.length() - 1);
                									try {
                										switch(status) {
                										case "plan to read":
                											index2 = methods.checkIfIdExists(Arr, rawCode);
                											if(index2 == -1) {
                												threadPoolResult[taskNum] = nHentaiAPIRun.nHentaiAPIRun(appdataLocation + mainFolderLocation + photoFolderLocation, rawCode, "", rawRating, "plan to read", new nHentaiWebBase());
                											}else
                												tableArr[index2][7] = String.valueOf(Integer.valueOf(tableArr[index2][7]) +1);
                        									break;
                										case "reading":
                											index2 = methods.checkIfIdExists(Arr, rawCode);
                											if(index2 == -1) {
                												threadPoolResult[taskNum] = nHentaiAPIRun.nHentaiAPIRunReading(appdataLocation + mainFolderLocation + photoFolderLocation, rawCode, "", rawRating, "reading", new nHentaiWebBase());
                											}else
                												tableArrReading[index2][8] = String.valueOf(Integer.valueOf(tableArrReading[index2][8]) +1);
                        									break;
                										case "completed":
                											index2 = methods.checkIfIdExists(Arr, rawCode);
                											if(index2 == -1) {
                												threadPoolResult[taskNum] = nHentaiAPIRun.nHentaiAPIRunCompleted(appdataLocation + mainFolderLocation + photoFolderLocation, rawCode, "", rawRating, "completed", new nHentaiWebBase());
                											}else
                												tableArrCompleted[index2][7] = String.valueOf(Integer.valueOf(tableArrCompleted[index2][7]) +1);
                        									break;
                									}
                										long time = nHentaiAPIRun.getInitTime();
                    									String unit = "ms";
                    									time  = time / 1000000;
                    									if(time > 999) {
                    										time = time / 1000;
                    										unit = "s";
                    									}
                    										
                    									label.setText("nHentai response: " + time + unit);
                									} catch (IOException e) {
                										UIManager.put("OptionPane.minimumSize", new Dimension(200, 100));
                										Error errorPanel = new Error(rawCode);
                										JOptionPane error = new JOptionPane();
                										error.showMessageDialog(null, errorPanel, "error", 0);
                										e.printStackTrace();
                									}
                									System.out.println("§2");
												}
            									
												
            									
            								}
            								
            								int cores = Runtime.getRuntime().availableProcessors(); 
            								
            								ExecutorService pool = Executors.newFixedThreadPool(cores);  
            						        // passes the Task objects to the pool to execute (Step 3)
            								
            								for (int i = 0; i < TextAreaData.length; i++) {
            									int taskNum = i;
            									
            									pool.execute(new TaskPool(i));
            									
            									Thread.sleep(200);
            								}
            								pool.shutdown();
            								pool.awaitTermination(20 * TextAreaData.length, TimeUnit.SECONDS);
            								for(int i=0;i<threadPoolResult.length;i++) {
            									if(threadPoolResult[i] != null) {
	            									switch(status) {
	        										case "plan to read":
	        											
	            											tableArr = methods.insertObjInTableArr(tableArr, threadPoolResult[i]);
	                    									model = methods.expandTable(tableArr, Model, threadPoolResult[i].id, SFW);
	
	                									break;
	        										case "reading":
	        											
	        												tableArrReading = methods.insertObjInTableArr(tableArrReading, threadPoolResult[i]);
	                    									modelReading = methods.expandTable(tableArrReading, Model, threadPoolResult[i].id, SFW);
	                									break;
	        										case "completed":
	        											
	        												tableArrCompleted = methods.insertObjInTableArr(tableArrCompleted, threadPoolResult[i]);
	                    									modelCompleted = methods.expandTable(tableArrCompleted, Model, threadPoolResult[i].id, SFW);
	
	                									break;
            										}
            									}
            								}
            								
            								System.out.println("§1");
            							}
            							tableArrSave = tableArr;
            							return null;
            						}
            						
            						@Override
            						protected void done() {
            							Bar.setIndeterminate(false);
            							Bar.setVisible(false);
            							label.setVisible(false);
            						}
            					}
            					Bar.setVisible(true);
            					Bar.setIndeterminate(true);
            					label.setVisible(true);
            					Task task = new Task();
            					task.execute();

            		}
                }
                System.out.println(name);
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
		dialogcloseWindow_btn.setIcon(new ImageIcon(nHentai.class.getResource("/grafics/Close.png")));
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
        dialog.getContentPane().add(pane2);
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
	}
	
	public void actionPerformedSetting() {
		settingsPanel settings = new settingsPanel(tableArr, tableArrReading, tableArrCompleted, SFW, resScroll);
		UIManager.put("OptionPane.minimumSize", new Dimension(500, 300));
		Component[] buttonText = methods.OKCancelButtonCreate();
		JOptionPane pane = new JOptionPane(settings, JOptionPane.PLAIN_MESSAGE, JOptionPane.OK_CANCEL_OPTION, null, buttonText, null);
		
		final JDialog dialog = new JDialog((Frame)null, "Boo");
		
        pane.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                String name = evt.getPropertyName();
                if ("value".equals(name)) {
                	dialog.dispose();
                    final Object value = pane.getValue();
                    System.out.println(value);
                	if (value.equals("OK")) {
            			SFW = settings.getSFW();
            			safed = false;
            			
            				for(int i=0;i<tableArr.length-1;i++) {
            					model = methods.reloadRowImage(i, model, tableArr, tableArr[i][2], SFW);
            				}
            				for(int i=0;i<tableArrReading.length-1;i++) {
            					modelReading = methods.reloadRowImage(i, modelReading, tableArrReading, tableArrReading[i][2], SFW);
            				}
            				for(int i=0;i<tableArrCompleted.length-1;i++) {
            					modelCompleted = methods.reloadRowImage(i, modelCompleted, tableArrCompleted, tableArrCompleted[i][2], SFW);
            				}
            				
            			boolean delete = settings.getDelete();
            			if(delete == true) {
            				for(int i=0;i<tableArr.length-1;i++) {
            					((DefaultTableModel) table_panel1.getModel()).removeRow(i);
            				}
            				for(int i=0;i<tableArrReading.length-1;i++) {
            					((DefaultTableModel) table_panel2.getModel()).removeRow(i);
            				}
            				for(int i=0;i<tableArrCompleted.length-1;i++) {
            					((DefaultTableModel) table_panel3.getModel()).removeRow(i);
            				}
            				tableArr = new String[1][10];
            				tableArrReading = new String[1][10];
            				tableArrCompleted = new String[1][10];
            				File file = new File(appdataLocation + mainFolderLocation + photoFolderLocation);
            				Path fromFile = file.toPath();
            				try {
            					methods.deleteDirectoryRecursion(fromFile);
            				} catch (IOException e) {
            					e.printStackTrace();
            				}
            				
            				file = new File(appdataLocation + mainFolderLocation + userDataFolderLocation);
            				fromFile = file.toPath();
            				try {
            					methods.deleteDirectoryRecursion(fromFile);
            				} catch (IOException e) {
            					e.printStackTrace();
            				}
            				
            				file = new File(appdataLocation + mainFolderLocation + randomPhotoFolderLocation);
            				fromFile = file.toPath();
            				try {
            					methods.deleteDirectoryRecursion(fromFile);
            				} catch (IOException e) {
            					e.printStackTrace();
            				}
            			}
            		}
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
		dialogcloseWindow_btn.setIcon(new ImageIcon(nHentai.class.getResource("/grafics/Close.png")));
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
        dialog.getContentPane().add(pane);
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
		
		
	}
	
	public void actionPerformedSafe(String[][] inputArr) {
		FlatDarkLaf.install();

		JFileChooser myJFileChooserSave = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

		myJFileChooserSave.setDialogTitle("Chosse the save location");
		myJFileChooserSave.setFileSelectionMode(JFileChooser.FILES_ONLY);
    	int returnValue = myJFileChooserSave.showOpenDialog(null);
    	String SaveFileLocation = "";
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = myJFileChooserSave.getSelectedFile();
            System.out.println(selectedFile.getAbsolutePath());
            SaveFileLocation = selectedFile.getAbsolutePath();
            
            char[] location = SaveFileLocation.toCharArray();
            String end = "";
            for(int i=location.length-1;i>location.length-6;i--) {
            	end = end + location[i];
            }
            
            String reversed = "";
            for ( int j = end.length()-1; j >= 0; j-- ) {
            	reversed += end.charAt(j);
            }
            	            
            if(!end.equals(".nhdb")) {
            	SaveFileLocation = SaveFileLocation + ".nhdb";
            }
            UIManager.put("OptionPane.background", new Color(35, 35, 35));
            UIManager.put("Panel.background", new Color(35, 35, 35));

            dataManager.saveTable(inputArr, SaveFileLocation);
        }
        
        try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String[][] actionPerformedLoad(String table) {
		
		safed = false;
		
		FlatDarkLaf.install();
		
		JFileChooser myJFileChooserLoad = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
		myJFileChooserLoad.setDialogTitle("Choose a file to load");
		myJFileChooserLoad.setFileSelectionMode(JFileChooser.FILES_ONLY);
    	int returnValue = myJFileChooserLoad.showOpenDialog(null);
    	String LoadFileLocation = "";
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = myJFileChooserLoad.getSelectedFile();
            System.out.println(selectedFile.getAbsolutePath());
            LoadFileLocation = selectedFile.getAbsolutePath();
            UIManager.put("OptionPane.background", new Color(35, 35, 35));
            UIManager.put("Panel.background", new Color(35, 35, 35));
            switch (table) {
            case "plan to read":
            	for(int i=0;i<tableArr.length-1;i++) {
					((DefaultTableModel) table_panel1.getModel()).removeRow(i);
				}
            	break;
            case "reading":
            	for(int i=0;i<tableArrReading.length-1;i++) {
					((DefaultTableModel) table_panel2.getModel()).removeRow(i);
				}
            	break;
            case "completed":
            	for(int i=0;i<tableArrCompleted.length-1;i++) {
					((DefaultTableModel) table_panel3.getModel()).removeRow(i);
				}
            	break;
            }
            return dataManager.readTable(LoadFileLocation);
        }
        
        try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return new String[1][10];
	}	
	
	public class loadingScreenMainGUI {

		private JFrame frame;
		
		private Task task;
		private Task2 task2;
		
		JProgressBar progressBar;
		
		nHentaiWebBase nHentaiAPI;

		JCheckBox update_CBox;
		/**
		 * Create the application.
		 */
		public loadingScreenMainGUI() {
			nHentaiAPI = new nHentaiWebBase();
			methods.setUpAppData(appdataLocation);
			initialize();
		}

		/**
		 * Initialize the contents of the frame.
		 */
		private void initialize() {
			frame = new JFrame();
			frame.getContentPane().setBackground(new Color(35, 35, 35));
			frame.setBounds(100, 100, 352, 130);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.getContentPane().setLayout(null);
			frame.setUndecorated(true);
			frame.setVisible(true);
			
			JPanel windowToolbar = new JPanel();
			windowToolbar.setBackground(new Color(17, 19, 22));
			windowToolbar.setBounds(0, 0, 352, 24);
			mouseCoord = null;
			windowToolbar.addMouseListener(new MouseListener() {
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
			windowToolbar.addMouseMotionListener(new MouseMotionListener() {
				@Override
				public void mouseDragged(MouseEvent e) {
					Point currCoords = e.getLocationOnScreen();
					frame.setLocation(currCoords.x - mouseCoord.x, currCoords.y - mouseCoord.y);
				}

				@Override
				public void mouseMoved(MouseEvent e) {
				}

			});
			frame.getContentPane().add(windowToolbar);
			windowToolbar.setLayout(null);

			JButton closeWindow_btn = new JButton();
			closeWindow_btn.setHorizontalTextPosition(SwingConstants.CENTER);
			closeWindow_btn.setIcon(new ImageIcon(nHentai.class.getResource("/grafics/Close.png")));
			closeWindow_btn.setBounds(328, 0, 24, 24);
			closeWindow_btn.setRequestFocusEnabled(false);
			closeWindow_btn.setVisible(true);
			closeWindow_btn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent evt) {
					System.exit(0);
				}
			});
			windowToolbar.add(closeWindow_btn);
			
			UIManager.put("ProgressBar.selectionForeground", Color.black);
			UIManager.put("ProgressBar.selectionBackground", Color.black);
			progressBar = new JProgressBar();
			progressBar.setBackground(Color.DARK_GRAY);
			progressBar.setForeground(Color.GREEN);
			progressBar.setBounds(10, 80, 316, 20);
			progressBar.setValue(0);
			progressBar.setStringPainted(true);
			frame.getContentPane().add(progressBar);
			
			JButton btnNewButton = new JButton("");
			btnNewButton.setIcon(new ImageIcon(loadingScreenMainGUI.class.getResource("/grafics/loadingScreen/startnHentaiDatabase.png")));
			btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					task = new Task();
			        task.addPropertyChangeListener(new PropertyChangeListener() {

						@Override
						public void propertyChange(PropertyChangeEvent evt) {
			                if ("progress" == evt.getPropertyName()) {
			                    int progress = (Integer) evt.getNewValue();
			                    progressBar.setValue(progress);
			                    progressBar.setString("download Photos :" + progress + "%");
			                } 
			            }
			        });
			        task.execute();
			        task2 = new Task2();
			        task2.addPropertyChangeListener(new PropertyChangeListener() {

						@Override
						public void propertyChange(PropertyChangeEvent evt) {
			                if ("progress" == evt.getPropertyName()) {
			                    int progress = (Integer) evt.getNewValue();
			                    progressBar.setValue(progress);
			                    progressBar.setString("load Data :" + progress + "%");
			                } 
			            }
			        });
			        task2.execute();
				}
			});
			btnNewButton.addMouseListener(new MouseAdapter() {
				public void mouseEntered(MouseEvent evt) {
					btnNewButton.setIcon(new ImageIcon(loadingScreenMainGUI.class.getResource("/grafics/loadingScreen/startnHentaiDatabaseHover.png")));
				}

				public void mouseExited(MouseEvent evt) {
					btnNewButton.setIcon(new ImageIcon(loadingScreenMainGUI.class.getResource("/grafics/loadingScreen/startnHentaiDatabase.png")));
				}

				public void mousePressed(MouseEvent evt) {
					btnNewButton.setIcon(new ImageIcon(loadingScreenMainGUI.class.getResource("/grafics/loadingScreen/startnHentaiDatabaseSelected.png")));
				}

				public void mouseReleased(MouseEvent evt) {
					btnNewButton.setIcon(new ImageIcon(loadingScreenMainGUI.class.getResource("/grafics/loadingScreen/startnHentaiDatabaseHover.png")));
				}
			});
			btnNewButton.setBounds(83, 40, 166, 21);
			frame.getContentPane().add(btnNewButton);
			
			JLabel display_lbl = new JLabel("Display Status: good");
			display_lbl.setForeground(Color.WHITE);
			display_lbl.setBackground(Color.red);
			display_lbl.setBounds(10, 105, 400, 21);
			display_lbl.setVisible(true);
			if(goodRes.equals("not usable"))
				display_lbl.setText("The display is to small. (full HD recomended)");
			if(goodRes.equals("inspect not usable"))
				display_lbl.setText("The display is to small for all features. (1080p recomended)");
			frame.getContentPane().add(display_lbl);
			
			update_CBox = new JCheckBox("update");
			update_CBox.setForeground(Color.WHITE);
			update_CBox.setBackground(new Color(35, 35, 35));
			update_CBox.setBounds(10, 55, 70, 23);
			if(Updater.getIfNewVersion(VERSION)){
				update_CBox.setVisible(true);
			}else {
				update_CBox.setVisible(false);
			}
			update_CBox.setSelected(false);
			frame.getContentPane().add(update_CBox);
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
	        	
	        	//setUp random Photos
	        	for(int i=0;i<200;i++) {
	    			File f = new File(appdataLocation + mainFolderLocation + randomPhotoFolderLocation + Slash + i + "_medium.jpg");
	    			if(!f.exists()) {
	    				nHentaiAPI.saveImageAsFile("https://picsum.photos/150/212", appdataLocation + mainFolderLocation + randomPhotoFolderLocation + Slash + i + "_medium.jpg");
	    				methods.scaleImage(appdataLocation + mainFolderLocation + randomPhotoFolderLocation + Slash + i + "_medium.jpg", appdataLocation + mainFolderLocation + randomPhotoFolderLocation + Slash + i, "_low.jpg", 50, 71);
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
	        }
	    }	
		
		class Task2 extends SwingWorker<Void, Void> {
	        /*
	         * Main task. Executed in background thread.
	         */
	        @Override
	        public Void doInBackground() {
	        	int progress = 0;
	        	double secondEnd = 0;
	        	double second = 0;
	        	setProgress(0);
	        	
	        	String SaveFileLocation = appdataLocation + mainFolderLocation + userDataFolderLocation;
	    		
	        	File f = new File(SaveFileLocation + Slash + "nHentaiDatabaseSettings.nhdb");
	    		if(f.exists()) {
	    			settings = new String[2];
	    			settings = dataManager.readSettings(SaveFileLocation + Slash + "nHentaiDatabaseSettings.nhdb");
	    			
	    			settings[1] = VERSION;
	    			
	    			if(settings[0].indexOf("true") >= 0) {
	    				SFW = true;
	    			}else if(settings[0].indexOf("false") >= 0) {
	    				SFW = false;
	    			}
	    			
	    			if(update_CBox.isSelected()){

						File currLocation = finder.getJarDir(nHentai.class);
						System.out.println(currLocation);
						
						dataManager.saveSettings(new String[]{currLocation + "", VERSION}, SaveFileLocation +  Slash + "updateInformation" );
						
						File file = new File(currLocation + Slash + "updater.jar");
						try {
							Desktop.getDesktop().open(file);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

				        System.out.println("Ending");
				        System.exit(0);
	    			}
	    		}else {
	    			String[] settings = new String[2];
	    			settings[0] = "SFW: " + String.valueOf(SFW);
	    			settings[1] = VERSION;
	    			dataManager.saveSettings(settings, SaveFileLocation + Slash + "nHentaiDatabaseSettings.nhdb");
	    		}
	        	
	    		f = new File(SaveFileLocation + Slash + "nHentaiDatabasePlanToRead.nhdb");
	    		if(f.exists()) {
	    			tableArr = dataManager.readTable(SaveFileLocation + Slash + "nHentaiDatabasePlanToRead.nhdb");
	    			double length = tableArr.length;
	    			secondEnd = length / 100;
	    			progress = 0;
	    			setProgress(progress);
	    			for (int i = 0; i < tableArr.length - 1; i++) {
	    				Object[] tmp = new Object[tableArr[0].length];
	    				for (int j = 0; j < tableArr[0].length; j++) {
	    					tmp[j] = tableArr[i][j];
	    				}
	    				String Id = tableArr[i][2];
	    				tmp[0] = String.valueOf(i + 1);
	    				methods.checkOneImage(Id, tableArr[i][1]);
	    				Icon img;
	    				if(SFW == false) {
	    					img = new ImageIcon(appdataLocation + mainFolderLocation + photoFolderLocation + Slash + Id + "_low.jpg");			
	    				}else {
	    					int random = (int)(Math.random()*200);
	    					img = new ImageIcon(appdataLocation + mainFolderLocation + randomPhotoFolderLocation + Slash + random + "_low.jpg");
	    				}
	    				tmp[1] = img;
	    				model.addRow(tmp);
	    				second++;
	    				if(second > secondEnd) {
	    					if(secondEnd < 1) {
	    						progress = progress + (int)(1 / secondEnd);
	    					}else
	    						progress++;
	    					setProgress(progress);
	    					second = 0;
	    				}
	    			}
	    			tableArrSave = tableArr;
	    		}else {
	    			dataManager.saveTable(tableArr, SaveFileLocation + Slash + "nHentaiDatabasePlanToRead.nhdb");
	    		}
	    		
	    		f = new File(SaveFileLocation + Slash + "nHentaiDatabaseReading.nhdb");
	    		if(f.exists()) {
	    			tableArrReading = dataManager.readTable(SaveFileLocation + Slash + "nHentaiDatabaseReading.nhdb");
	    			double length = tableArrReading.length;
	    			secondEnd = length / 100;
	    			progress = 0;
	    			setProgress(progress);
	    			for (int i = 0; i < tableArrReading.length - 1; i++) {
	    				Object[] tmp = new Object[tableArrReading[0].length];
	    				for (int j = 0; j < tableArrReading[0].length; j++) {
	    					tmp[j] = tableArrReading[i][j];
	    				}
	    				String Id = tableArrReading[i][2];
	    				tmp[0] = String.valueOf(i + 1);
	    				methods.checkOneImage(Id, tableArrReading[i][1]);
	    				Icon img;
	    				if(SFW == false) {
	    					img = new ImageIcon(appdataLocation + mainFolderLocation + photoFolderLocation + Slash + Id + "_low.jpg");			
	    				}else {
	    					int random = (int)(Math.random()*200);
	    					img = new ImageIcon(appdataLocation + mainFolderLocation + randomPhotoFolderLocation + Slash + random + "_low.jpg");
	    				}
	    				tmp[1] = img;
	    				modelReading.addRow(tmp);
	    			
	    				second++;
	    				if(second > secondEnd) {
	    					if(secondEnd < 1) {
	    						progress = progress + (int)(1 / secondEnd);
	    					}else
	    						progress++;
	    					setProgress(progress);
	    					second = 0;
	    				}
	    			}
	    			tableArrReadingSave = tableArrReading;
	    		}else {
	    			dataManager.saveTable(tableArrReading, SaveFileLocation + Slash + "nHentaiDatabaseReading.nhdb");
	    		}
	    		
	    		f = new File(SaveFileLocation + Slash + "nHentaiDatabaseCompleted.nhdb");
	    		if(f.exists()) {
	    			tableArrCompleted = dataManager.readTable(SaveFileLocation + Slash + "nHentaiDatabaseCompleted.nhdb");
	    			double length = tableArrCompleted.length;
	    			secondEnd = length / 100;
	    			progress = 0;
	    			setProgress(progress);
	    			for (int i = 0; i < tableArrCompleted.length - 1; i++) {
	    				Object[] tmp = new Object[tableArrCompleted[0].length];
	    				for (int j = 0; j < tableArrCompleted[0].length; j++) {
	    					tmp[j] = tableArrCompleted[i][j];
	    				}
	    				String Id = tableArrCompleted[i][2];
	    				tmp[0] = String.valueOf(i + 1);
	    				methods.checkOneImage(Id, tableArrCompleted[i][1]);
	    				Icon img;
	    				if(SFW == false) {
	    					img = new ImageIcon(appdataLocation + mainFolderLocation + photoFolderLocation + Slash + Id + "_low.jpg");			
	    				}else {
	    					int random = (int)(Math.random()*200);
	    					img = new ImageIcon(appdataLocation + mainFolderLocation + randomPhotoFolderLocation + Slash + random + "_low.jpg");
	    				}
	    				tmp[1] = img;
	    				modelCompleted.addRow(tmp);
	    				
	    				if(progress == 99) {
	    					System.out.println("99");
	    				}
	    				second++;
	    				if(second > secondEnd) {
	    					if(secondEnd < 1) {
	    						progress = progress + (int)(1 / secondEnd);
	    					}else
	    						progress++;
	    					setProgress(progress);
	    					second = 0;
	    				}
	    			}
	    			tableArrCompletedSave = tableArrCompleted;
	    		}else {
	    			dataManager.saveTable(tableArrCompleted, SaveFileLocation + Slash + "nHentaiDatabaseCompleted.nhdb");
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
	            System.out.println("finish2");
	            frame.setVisible(false);
	            frmNhentaidatabase.setVisible(true);
	            start = true;
	        }
	    }	
	}
	
	public void actionPerformedSearch(String input) {
		String searchInput = input;
		String[] filteredTable;
		filteredTable = searchEngine.search(tableArrSave, searchInput, getSearchConfig("plan to read"));
		
		if(inSearchViewPlanToRead == false)
			tableArrSave = tableArr;

		String[][] resultArr = new String[filteredTable.length+1][10];
		int k = 0;
		for(int i=0;i<tableArrSave.length-1;i++) {
			if(filteredTable[k] != null && tableArrSave[i][2].equals(filteredTable[k])) {
				for(int j=0;j<tableArrSave[0].length;j++) {
					resultArr[k][j] = tableArrSave[i][j];
				}
				k++;
				if(k == filteredTable.length) {
					i = tableArrSave.length;
				}
			}
		}
		
		
		for(int i=0;i<tableArr.length-1;i++) {
			model.removeRow(0);
		}
		tableArr = resultArr;
		model = methods.ArrToTable(model, tableArr, SFW);
		inSearchViewPlanToRead = true;
	}
	
	public void actionPerformedSearchReading(String input) {
		String searchInput = input;
		String[] filteredTable;
		filteredTable = searchEngine.search(tableArrReading, searchInput, getSearchConfig("reading"));
		
		if(inSearchViewReading == false)
			tableArrReadingSave = tableArrReading;

		String[][] resultArr = new String[filteredTable.length+1][10];
		int k = 0;
		for(int i=0;i<tableArrReading.length-1;i++) {
			if(filteredTable[k] != null && tableArrReading[i][2].equals(filteredTable[k])) {
				for(int j=0;j<tableArrReading[0].length;j++) {
					resultArr[k][j] = tableArrReading[i][j];
				}
				k++;
				if(k == filteredTable.length) {
					i = tableArrReading.length;
				}
			}
		}
		
		
		for(int i=0;i<tableArrReading.length-1;i++) {
			modelReading.removeRow(0);
		}
		tableArrReading = resultArr;
		modelReading = methods.ArrToTable(modelReading, tableArrReading, SFW);
		inSearchViewReading = true;
	}
	
	public void actionPerformedSearchCompleted(String input) {
		String searchInput = input;
		String[] filteredTable;
		filteredTable = searchEngine.search(tableArrCompletedSave, searchInput, getSearchConfig("completed"));
		
		if(inSearchViewCompleted == false)
			tableArrCompletedSave = tableArrCompleted;

		String[][] resultArr = new String[filteredTable.length+1][10];
		int k = 0;
		for(int i=0;i<tableArrCompletedSave.length-1;i++) {
			if(filteredTable[k] != null && tableArrCompletedSave[i][2].equals(filteredTable[k])) {
				for(int j=0;j<tableArrCompletedSave[0].length;j++) {
					resultArr[k][j] = tableArrCompletedSave[i][j];
				}
				k++;
				if(k == filteredTable.length) {
					i = tableArrCompletedSave.length;
				}
			}
		}
		
		
		for(int i=0;i<tableArrCompleted.length-1;i++) {
			modelCompleted.removeRow(0);
		}
		tableArrCompleted = resultArr;
		modelCompleted = methods.ArrToTable(modelCompleted, tableArrCompleted, SFW);
		inSearchViewCompleted = true;
	}
	
	public boolean[] getSearchConfig(String panel) {
		boolean[] config = new boolean[4];
		switch (panel) {
		case "plan to read":
			config[0] = searchId_panel1_CBox.isSelected();
			config[1] = searchTitle_panel1_CBox.isSelected();
			config[2] = searchAuthor_panel1_CBox.isSelected();
			config[3] = searchTags_panel1_CBox.isSelected();
			break;
		case "reading":
			config[0] = searchId_panel2_CBox.isSelected();
			config[1] = searchTitle_panel2_CBox.isSelected();
			config[2] = searchAuthor_panel2_CBox.isSelected();
			config[3] = searchTags_panel2_CBox.isSelected();
			break;
		case "completed":
			config[0] = searchId_panel3_CBox.isSelected();
			config[1] = searchTitle_panel3_CBox.isSelected();
			config[2] = searchAuthor_panel3_CBox.isSelected();
			config[3] = searchTags_panel3_CBox.isSelected();
			break;
		}
		return config;
	}
}