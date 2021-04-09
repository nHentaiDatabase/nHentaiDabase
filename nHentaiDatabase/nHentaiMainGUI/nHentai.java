package nHentaiMainGUI;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;

import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import settings.settingsPanel;
import datamanager.dataManager;
import moreInformation.moreInformationPanel;
import nHentaiWebScaper.nHentaiWebBase;
import newEntry.newEntry;
import newEntry.newEntryGeneral;
import outsourcedClasses.ButtonColumnAll;
import outsourcedClasses.nHentaiAPIRun;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;

import javax.swing.filechooser.FileSystemView;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.JScrollPane;

public class nHentai {

	private JFrame frmNhentaidatabase;
	private JTable table_panel1;
	private DefaultTableModel model;
	private DefaultTableModel modelReading;
	private DefaultTableModel modelCompleted;

	private dataManager dataManager;
	private newEntry addNewEntry;
	private nHentaiWebBase nHentaiAPI;
	private nHentaiAPIRun nHentaiAPIRun;

	private String[][] tableArr;
	private String[][] tableArrReading;
	private String[][] tableArrCompleted;
	private String appdataLocation;
	private boolean SFW = false;
	private String mainFolderLocation = "\\nHentaiDatabase";
	private String photoFolderLocation = "\\savedPhotos";
	private String userDataFolderLocation = "\\userData";
	private String randomPhotoFolderLocation = "\\randomPhotos";

	private Point mouseCoord;
	private JTable table_panel2;
	private JTable table_panel3;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) throws IOException {
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
					nHentai window = new nHentai();
					window.frmNhentaidatabase.setVisible(true);
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
		dataManager = new dataManager();
		addNewEntry = new newEntry();
		nHentaiAPI = new nHentaiWebBase();
		nHentaiAPIRun = new nHentaiAPIRun();
		tableArr = new String[1][10];
		tableArrReading = new String[1][10];
		tableArrCompleted = new String[1][10];
		appdataLocation = System.getenv("APPDATA");
		initialize();
		getSave();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		UIManager.put("OptionPane.background", new Color(35, 35, 35));
		UIManager.put("Panel.background", new Color(35, 35, 35));
		
		frmNhentaidatabase = new JFrame();
		frmNhentaidatabase.getContentPane().setBackground(new java.awt.Color(54, 57, 63));
		frmNhentaidatabase.setTitle("nHentaiDatabase");
		frmNhentaidatabase.setBounds(100, 100, 947, 721);
		frmNhentaidatabase.setUndecorated(true);
		frmNhentaidatabase.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmNhentaidatabase.getContentPane().setLayout(null);

		JPanel windowToolbar = new JPanel();
		windowToolbar.setBackground(new java.awt.Color(28, 31, 34));
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
				
				exitConfirm confirm = new exitConfirm();
        		JOptionPane pane = new JOptionPane(confirm, JOptionPane.PLAIN_MESSAGE, JOptionPane.YES_NO_CANCEL_OPTION);
				
				final JButton saveButton = new JButton();
				saveButton.setPreferredSize(new Dimension(47,25));
				saveButton.setIcon(new ImageIcon(moreInformationPanel.class.getResource("/grafics/close/save.png")));
				saveButton.setHorizontalTextPosition(SwingConstants.CENTER);
				saveButton.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						JOptionPane paneAP = getOptionPane((JComponent)e.getSource());
						paneAP.setValue(saveButton);
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
						JOptionPane paneAP = getOptionPane((JComponent)e.getSource());
						paneAP.setValue(closeButton);
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
						JOptionPane paneAP = getOptionPane((JComponent)e.getSource());
						paneAP.setValue(cancelButton);
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
				
        		String[] options = new String[] {"save", "close", "cancel"};
				int result = pane.showOptionDialog(null, confirm, "confirm", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, buttonText
						, null);
				if(result == JOptionPane.YES_OPTION) {
					String SaveFileLocation = appdataLocation + mainFolderLocation + userDataFolderLocation;
					dataManager.saveTable(tableArr, SaveFileLocation + "\\nHentaiDatabasePlanToRead.nhdb");
					dataManager.saveTable(tableArrReading, SaveFileLocation + "\\nHentaiDatabaseReading.nhdb");
					dataManager.saveTable(tableArrCompleted, SaveFileLocation + "\\nHentaiDatabaseCompleted.nhdb");
					System.exit(0);
				}else if(result == JOptionPane.NO_OPTION) {
					System.exit(0);
				}
			}
		});
		windowToolbar.add(closeWindow_btn);

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

				actionPerformedNewEntry("plan to read");
				
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
				actionPerformedSafe(tableArr);
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
				tableArr = actionPerformedLoad();
				model = ArrToTable(model);
			}
		});
		panel_panel1.add(loadTable__panel1_btn);

		JScrollPane scrollPane_panel1 = new JScrollPane();
		scrollPane_panel1.setBounds(250, 11, 666, 632);
		scrollPane_panel1.getViewport().setBackground(new Color(54, 57, 63));
		
		JScrollBar scrollBar_panel1 = scrollPane_panel1.getVerticalScrollBar();
        Dimension scrollBarDim_panel1 = new Dimension(15, scrollBar_panel1
              .getPreferredSize().height);
        scrollBar_panel1.setPreferredSize(scrollBarDim_panel1);
        scrollBar_panel1.setUI(new BasicScrollBarUI() {
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
       });
		
		
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
		model = new DefaultTableModel(new Object[][] {

		}, new String[] { "number", "title picture", "id", "title", "author", "pages", "status", "" });
		table_panel1.setModel(model);

		ButtonColumnAll buttonColumnTable_panel1 = new ButtonColumnAll(table_panel1, deleteTableArrRow, 7);
		buttonColumnTable_panel1.setMnemonic(KeyEvent.VK_D);

		table_panel1.getColumnModel().getColumn(0).setResizable(false);
		table_panel1.getColumnModel().getColumn(1).setResizable(false);
		table_panel1.getColumnModel().getColumn(2).setResizable(false);
		table_panel1.getColumnModel().getColumn(3).setResizable(false);
		table_panel1.getColumnModel().getColumn(4).setResizable(false);
		table_panel1.getColumnModel().getColumn(5).setResizable(false);
		table_panel1.getColumnModel().getColumn(6).setResizable(false);
		table_panel1.setRowHeight(71);
		table_panel1.getColumnModel().getColumn(1).setCellRenderer(new TableCellRenderer() {

			@Override
			public Component getTableCellRendererComponent(JTable jtable, Object value, boolean bln, boolean bln1,
					int i, int i1) {
				JLabel lbl = new JLabel();
				lbl.setIcon((ImageIcon) value);
				return lbl;
			}
		});
		scrollPane_panel1.setViewportView(table_panel1);
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
				
				actionPerformedNewEntry("reading");
				
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
				actionPerformedSafe(tableArrReading);
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
				tableArrReading = actionPerformedLoad();
				modelReading = ArrToTableReading(modelReading);
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

		JScrollPane scrollPane_panel2 = new JScrollPane();
		scrollPane_panel2.setBounds(250, 11, 666, 632);
		scrollPane_panel2.getViewport().setBackground(new Color(54, 57, 63));
		
		JScrollBar scrollBar_panel2 = scrollPane_panel2.getVerticalScrollBar();
        Dimension scrollBarDim_panel2 = new Dimension(15, scrollBar_panel2
              .getPreferredSize().height);
        scrollBar_panel2.setPreferredSize(scrollBarDim_panel2);
        scrollBar_panel2.setUI(new BasicScrollBarUI() {
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
       });
        
		
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

		}, new String[] { "number", "title picture", "id", "title", "author", "pages", "rating", "status", "" });
		table_panel2.setModel(modelReading);
		table_panel2.getTableHeader().setBackground(Color.RED);
		table_panel2.getTableHeader().setDefaultRenderer(new renderEngine.HeaderColor());
		
		ButtonColumnAll buttonColumnTable_panel2 = new ButtonColumnAll(table_panel2, deleteTableArrReadingRow, 8);
		buttonColumnTable_panel2.setMnemonic(KeyEvent.VK_D);
		
		table_panel2.getColumnModel().getColumn(0).setResizable(false);
		table_panel2.getColumnModel().getColumn(1).setResizable(false);
		table_panel2.getColumnModel().getColumn(2).setResizable(false);
		table_panel2.getColumnModel().getColumn(3).setResizable(false);
		table_panel2.getColumnModel().getColumn(4).setResizable(false);
		table_panel2.getColumnModel().getColumn(5).setResizable(false);
		table_panel2.getColumnModel().getColumn(6).setResizable(false);
		table_panel2.getColumnModel().getColumn(7).setResizable(false);
		table_panel2.getColumnModel().getColumn(8).setResizable(false);
		table_panel2.setRowHeight(71);
		table_panel2.getColumnModel().getColumn(1).setCellRenderer(new TableCellRenderer() {

			@Override
			public Component getTableCellRendererComponent(JTable jtable, Object value, boolean bln, boolean bln1,
					int i, int i1) {
				JLabel lbl = new JLabel();
				lbl.setIcon((ImageIcon) value);
				return lbl;
			}
		});
		scrollPane_panel2.setViewportView(table_panel2);
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
				
				actionPerformedNewEntry("completed");
				
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
				actionPerformedSafe(tableArrCompleted);
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
				tableArrCompleted = actionPerformedLoad();
				modelCompleted = ArrToTableCompleted(modelCompleted);
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
		
		JScrollPane scrollPane_panel3 = new JScrollPane();
		scrollPane_panel3.getViewport().setBackground(new Color(54, 57, 63));
		scrollPane_panel3.setBounds(250, 11, 666, 632);
		
		JScrollBar scrollBar_panel3 = scrollPane_panel2.getVerticalScrollBar();
        Dimension scrollBarDim_panel3 = new Dimension(15, scrollBar_panel3
              .getPreferredSize().height);
        scrollBar_panel3.setPreferredSize(scrollBarDim_panel3);
        scrollBar_panel3.setUI(new BasicScrollBarUI() {
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
       });
		
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

		}, new String[] { "number", "title picture", "id", "title", "author", "pages", "rating", "times Read", "status", "" });
		table_panel3.setModel(modelCompleted);
		table_panel3.getTableHeader().setBackground(Color.RED);
		table_panel3.getTableHeader().setDefaultRenderer(new renderEngine.HeaderColor());
		
		ButtonColumnAll buttonColumnTable_panel3 = new ButtonColumnAll(table_panel3, deleteTableArrCompletedRow, 9);
		buttonColumnTable_panel3.setMnemonic(KeyEvent.VK_D);
		
		table_panel3.getColumnModel().getColumn(0).setResizable(false);
		table_panel3.getColumnModel().getColumn(1).setResizable(false);
		table_panel3.getColumnModel().getColumn(2).setResizable(false);
		table_panel3.getColumnModel().getColumn(3).setResizable(false);
		table_panel3.getColumnModel().getColumn(4).setResizable(false);
		table_panel3.getColumnModel().getColumn(5).setResizable(false);
		table_panel3.getColumnModel().getColumn(6).setResizable(false);
		table_panel3.getColumnModel().getColumn(7).setResizable(false);
		table_panel3.getColumnModel().getColumn(8).setResizable(false);
		table_panel3.getColumnModel().getColumn(9).setResizable(false);
		table_panel3.setRowHeight(71);
		table_panel3.getColumnModel().getColumn(1).setCellRenderer(new TableCellRenderer() {

			@Override
			public Component getTableCellRendererComponent(JTable jtable, Object value, boolean bln, boolean bln1,
					int i, int i1) {
				JLabel lbl = new JLabel();
				lbl.setIcon((ImageIcon) value);
				return lbl;
			}
		});
		scrollPane_panel3.setViewportView(table_panel3);

		/*
		 * end panel 3
		 */
		
	}

	
	public String[][] expandArr(String[][] input) {
		String[][] tmp = new String[input.length + 1][input[0].length];
		System.arraycopy(input, 0, tmp, 0, input.length);
		input = tmp;
		return input;
	}

	public String[][] rearangeArr(String[][] input, int index) {
		String[][] tmp = new String[input.length - 1][input[0].length];
		for (int i = 0; i < index; i++) {
			for (int j = 0; j < input[0].length; j++) {
				tmp[i][j] = input[i][j];
			}
		}
		for (int i = index + 1; i < input.length; i++) {
			for (int j = 0; j < input[0].length; j++) {
				tmp[i - 1][j] = input[i][j];
			}
		}
		return tmp;
	}

	public String[][] deleteLastArrRow(String[][] input) {
		String[][] tmp = new String[input.length - 1][input[0].length];
		for (int i = 0; i < input.length - 1; i++) {
			for (int j = 0; j < input[0].length; j++) {
				tmp[i][j] = input[i][j];
			}
		}
		return tmp;
	}

	public DefaultTableModel ArrToTable(DefaultTableModel inputModel) {
		for (int i = 0; i < tableArr.length - 1; i++) {
			Object[] tmp = new Object[tableArr[0].length];
			for (int j = 0; j < tableArr[0].length; j++) {
				tmp[j] = tableArr[i][j];
			}
			String Id = tableArr[i][2];
			tmp[0] = String.valueOf(i + 1);
			checkOneImage(Id, tableArr[i][1]);
			Icon img;
			if(SFW == false) {
				img = new ImageIcon(appdataLocation + mainFolderLocation + photoFolderLocation + "\\" + Id + "_low.jpg");			
			}else {
				int random = (int)(Math.random()*200);
				img = new ImageIcon(appdataLocation + mainFolderLocation + randomPhotoFolderLocation + "\\" + random + "_low.jpg");
			}
			tmp[1] = img;
			inputModel.addRow(tmp);
		}
		return inputModel;
	}

	public DefaultTableModel expandTable(DefaultTableModel inputModel, String Id) {
		Object[] tmp = new Object[tableArr[0].length];
		for (int j = 0; j < tableArr[0].length; j++) {
			tmp[j] = tableArr[tableArr.length - 2][j];
		}
		tmp[0] = String.valueOf(tableArr.length - 1);
		Icon img;
		if(SFW == false) {
			img = new ImageIcon(appdataLocation + mainFolderLocation + photoFolderLocation + "\\" + Id + "_low.jpg");			
		}else {
			int random = (int)(Math.random()*200);
			img = new ImageIcon(appdataLocation + mainFolderLocation + randomPhotoFolderLocation + "\\" + random + "_low.jpg");
		}
		tmp[1] = img;
		inputModel.addRow(tmp);
		return inputModel;
	}

	public DefaultTableModel ArrToTableReading(DefaultTableModel inputModel) {
		for (int i = 0; i < tableArrReading.length - 1; i++) {
			Object[] tmp = new Object[tableArrReading[0].length];
			for (int j = 0; j < tableArrReading[0].length; j++) {
				tmp[j] = tableArrReading[i][j];
			}
			String Id = tableArrReading[i][2];
			tmp[0] = String.valueOf(i + 1);
			checkOneImage(Id, tableArrReading[i][1]);
			Icon img;
			if(SFW == false) {
				img = new ImageIcon(appdataLocation + mainFolderLocation + photoFolderLocation + "\\" + Id + "_low.jpg");			
			}else {
				int random = (int)(Math.random()*200);
				img = new ImageIcon(appdataLocation + mainFolderLocation + randomPhotoFolderLocation + "\\" + random + "_low.jpg");
			}
			tmp[1] = img;
			inputModel.addRow(tmp);
		}
		return inputModel;
	}
	
	public DefaultTableModel expandTableReading(DefaultTableModel inputModel, String Id) {
		Object[] tmp = new Object[tableArrReading[0].length];
		for (int j = 0; j < tableArrReading[0].length; j++) {
			tmp[j] = tableArrReading[tableArrReading.length - 2][j];
		}
		tmp[0] = String.valueOf(tableArrReading.length - 1);
		Icon img;
		if(SFW == false) {
			img = new ImageIcon(appdataLocation + mainFolderLocation + photoFolderLocation + "\\" + Id + "_low.jpg");			
		}else {
			int random = (int)(Math.random()*200);
			img = new ImageIcon(appdataLocation + mainFolderLocation + randomPhotoFolderLocation + "\\" + random + "_low.jpg");
		}
		tmp[1] = img;
		inputModel.addRow(tmp);
		return inputModel;
	}
	
	public DefaultTableModel ArrToTableCompleted(DefaultTableModel inputModel) {
		for (int i = 0; i < tableArrCompleted.length - 1; i++) {
			Object[] tmp = new Object[tableArrCompleted[0].length];
			for (int j = 0; j < tableArrCompleted[0].length; j++) {
				tmp[j] = tableArrCompleted[i][j];
			}
			String Id = tableArrCompleted[i][2];
			tmp[0] = String.valueOf(i + 1);
			checkOneImage(Id, tableArrCompleted[i][1]);
			Icon img;
			if(SFW == false) {
				img = new ImageIcon(appdataLocation + mainFolderLocation + photoFolderLocation + "\\" + Id + "_low.jpg");			
			}else {
				int random = (int)(Math.random()*200);
				img = new ImageIcon(appdataLocation + mainFolderLocation + randomPhotoFolderLocation + "\\" + random + "_low.jpg");
			}
			tmp[1] = img;
			inputModel.addRow(tmp);
		}
		return inputModel;
	}
	
	public DefaultTableModel expandTableCompleted(DefaultTableModel inputModel, String Id) {
		Object[] tmp = new Object[tableArrCompleted[0].length];
		for (int j = 0; j < tableArrCompleted[0].length; j++) {
			tmp[j] = tableArrCompleted[tableArrCompleted.length - 2][j];
		}
		tmp[0] = String.valueOf(tableArrCompleted.length - 1);
		Icon img;
		if(SFW == false) {
			img = new ImageIcon(appdataLocation + mainFolderLocation + photoFolderLocation + "\\" + Id + "_low.jpg");			
		}else {
			int random = (int)(Math.random()*200);
			img = new ImageIcon(appdataLocation + mainFolderLocation + randomPhotoFolderLocation + "\\" + random + "_low.jpg");
		}
		tmp[1] = img;
		inputModel.addRow(tmp);
		return inputModel;
	}

	public void setUpRandomPhotos() {
		for(int i=0;i<200;i++) {
			File f = new File(appdataLocation + mainFolderLocation + randomPhotoFolderLocation + "\\" + i + "_medium.jpg");
			if(!f.exists()) {
				nHentaiAPI.saveImageAsFile("https://picsum.photos/150/212", appdataLocation + mainFolderLocation + randomPhotoFolderLocation + "\\" + i + "_medium.jpg");
				scaleImage(appdataLocation + mainFolderLocation + randomPhotoFolderLocation + "\\" + i + "_medium.jpg", appdataLocation + mainFolderLocation + randomPhotoFolderLocation + "\\" + i, "_low.jpg", 50, 71);
			}
		}
	}
	
	public void setUpAppData(String appData) {
		new File(appData + mainFolderLocation).mkdirs();
		new File(appData + mainFolderLocation + photoFolderLocation).mkdirs();
		new File(appData + mainFolderLocation + userDataFolderLocation).mkdirs();
		new File(appData + mainFolderLocation + randomPhotoFolderLocation).mkdirs();
	}

	public BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight)
			throws IOException {
		BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
		Graphics2D graphics2D = resizedImage.createGraphics();
		graphics2D.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);
		graphics2D.dispose();
		return resizedImage;
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
	
	public void checkAllImages(String[][] inputArr) {
		String[] URLs = new String[inputArr.length-1];
		String[] IDs = new String[inputArr.length-1];
		for(int i=0;i<URLs.length;i++) {
			URLs[i] = inputArr[i][1];
			IDs[i] = inputArr[i][2];
		}
		for(int i=0;i<URLs.length;i++) {
			String MainLocation = appdataLocation + mainFolderLocation + photoFolderLocation +"\\" + IDs[i];
			File f = new File(MainLocation + "_original.jpg");
			if(!f.exists()) {
				nHentaiAPI.saveImageAsFile(URLs[i], MainLocation + "_original.jpg");
			}
			f = new File(MainLocation + "_medium.jpg");
			if(!f.exists()) {
				scaleImage(MainLocation + "_original.jpg", MainLocation , "_medium.jpg", 150, 212);
			}
			f = new File(MainLocation + "low.jpg");
			if(!f.exists()) {
				scaleImage(MainLocation + "_original.jpg", MainLocation,  "_low.jpg", 50, 71);
			}
		}
	}
	
	public void checkOneImage(String Id, String URL) {
		String MainLocation = appdataLocation + mainFolderLocation + photoFolderLocation +"\\" + Id ;
		File f = new File(MainLocation + "_original.jpg");
		if(!f.exists()) {
			nHentaiAPI.saveImageAsFile(URL, MainLocation + "_original.jpg");
		}
		f = new File(MainLocation + "_medium.jpg");
		if(!f.exists()) {
			scaleImage(MainLocation + "_original.jpg", MainLocation,  "_medium.jpg", 150, 212);
		}
		f = new File(MainLocation + "low.jpg");
		if(!f.exists()) {
			scaleImage(MainLocation + "_original.jpg", MainLocation, "_low.jpg", 50, 71);
		}
	}
	
	Action deleteTableArrRow = new AbstractAction()
	{
	    public void actionPerformed(ActionEvent e)
	    {
	        JTable table = (JTable)e.getSource();
	        int modelRow = Integer.valueOf( e.getActionCommand() );
	        
	        String title = tableArr[modelRow][3];
			String id = tableArr[modelRow][2];
			String tags = tableArr[modelRow][9];
			String artist = tableArr[modelRow][4];
			String pages = tableArr[modelRow][5];
			String rating = tableArr[modelRow][8];
			String status = tableArr[modelRow][6];
			String URL = tableArr[modelRow][1];
			String timesRead = tableArr[modelRow][7];

			String photoLocation;
			if(SFW) {
				int random = (int)(Math.random()*200);
				photoLocation = appdataLocation + mainFolderLocation + randomPhotoFolderLocation + "\\" + random + "_medium.jpg";
			}else {
				photoLocation = appdataLocation + mainFolderLocation + photoFolderLocation + "\\" + id + "_medium.jpg";
			}
			
			
			moreInformationPanel moreInformation = new moreInformationPanel(id, title, artist, pages, rating,
					timesRead, status, tags,
					photoLocation, SFW);
			UIManager.put("OptionPane.minimumSize", new Dimension(500, 900));
			JOptionPane inspectPane = new JOptionPane(moreInformation, JOptionPane.PLAIN_MESSAGE,
					JOptionPane.OK_OPTION);
			int result = inspectPane.showOptionDialog(null, moreInformation, "newEntry", 0,
					JOptionPane.PLAIN_MESSAGE, null, null, null);
			System.out.println("pressed" + e.getActionCommand());
			
			
			if (result == JOptionPane.OK_OPTION) {
				rating = moreInformation.getRating();
				timesRead = moreInformation.getTimesRead();
				boolean deleteEntry = moreInformation.getDeleteEntry();
				
				if(deleteEntry == true) {
					((DefaultTableModel) table_panel1.getModel()).removeRow(modelRow);
					tableArr = rearangeArr(tableArr, modelRow);
				}
				
				tableArr[modelRow][8] = rating;
				tableArr[modelRow][7] = timesRead;

				String newStatus = moreInformation.getStatus();
				
				if (newStatus.equals("reading")) {
					//actionPerformedNewStatusReading(modelRow, URL, title, id, tags, artist, pages, rating, timesRead);
					((DefaultTableModel) table_panel1.getModel()).removeRow(modelRow);
					tableArr = rearangeArr(tableArr, modelRow);
					tableArrReading[tableArrReading.length - 1][1] = URL;
					tableArrReading[tableArrReading.length - 1][3] = title;
					tableArrReading[tableArrReading.length - 1][2] = id;
					tableArrReading[tableArrReading.length - 1][9] = tags;
					tableArrReading[tableArrReading.length - 1][4] = artist;
					tableArrReading[tableArrReading.length - 1][5] = pages;
					tableArrReading[tableArrReading.length - 1][8] = timesRead;
					tableArrReading[tableArrReading.length - 1][6] = rating;
					tableArrReading[tableArrReading.length - 1][7] = "reading";

					tableArrReading = expandArr(tableArrReading);
					expandTableReading(modelReading, id);
					
				}
				else if(newStatus.equals("completed")) {
					//actionPerformedNewStatusCompleted(modelRow, URL, title, id, tags, artist, pages, rating, timesRead);
					((DefaultTableModel) table_panel2.getModel()).removeRow(modelRow);
					tableArrReading = rearangeArr(tableArrReading, modelRow);
					tableArrCompleted[tableArrCompleted.length - 1][1] = URL;
					tableArrCompleted[tableArrCompleted.length - 1][3] = title;
					tableArrCompleted[tableArrCompleted.length - 1][2] = id;
					tableArrCompleted[tableArrCompleted.length - 1][9] = tags;
					tableArrCompleted[tableArrCompleted.length - 1][4] = artist;
					tableArrCompleted[tableArrCompleted.length - 1][5] = pages;
					tableArrCompleted[tableArrCompleted.length - 1][8] = "completed";
					tableArrCompleted[tableArrCompleted.length - 1][6] = rating;
					tableArrCompleted[tableArrCompleted.length - 1][7] = timesRead;

					tableArrCompleted = expandArr(tableArrCompleted);
					expandTable(modelCompleted, id);
				}
			}
	    }
	};
	
	Action deleteTableArrReadingRow = new AbstractAction()
	{
	    public void actionPerformed(ActionEvent e)
	    {
	        JTable table = (JTable)e.getSource();
	        int modelRow = Integer.valueOf( e.getActionCommand() );
	        
	        String title = tableArrReading[modelRow][3];
			String id = tableArrReading[modelRow][2];
			String tags = tableArrReading[modelRow][9];
			String artist = tableArrReading[modelRow][4];
			String pages = tableArrReading[modelRow][5];
			String rating = tableArrReading[modelRow][6];
			String status = tableArrReading[modelRow][7];
			String URL = tableArrReading[modelRow][1];
			String timesRead = tableArrReading[modelRow][8];

			String photoLocation;
			if(SFW) {
				int random = (int)(Math.random()*200);
				photoLocation = appdataLocation + mainFolderLocation + randomPhotoFolderLocation + "\\" + random + "_medium.jpg";
			}else {
				photoLocation = appdataLocation + mainFolderLocation + photoFolderLocation + "\\" + id + "_medium.jpg";
			}
			
			moreInformationPanel moreInformation = new moreInformationPanel(id, title, artist, pages, rating,
					timesRead, status, tags,
					photoLocation, SFW);
			UIManager.put("OptionPane.minimumSize", new Dimension(500, 900));
			JOptionPane inspectPane = new JOptionPane(moreInformation, JOptionPane.PLAIN_MESSAGE,
					JOptionPane.OK_OPTION);
			int result = inspectPane.showOptionDialog(null, moreInformation, "newEntry", 0,
					JOptionPane.PLAIN_MESSAGE, null, null, null);
			System.out.println("pressed" + e.getActionCommand());

			if (result == JOptionPane.OK_OPTION) {
				rating = moreInformation.getRating();
				timesRead = moreInformation.getTimesRead();
				tableArrReading[modelRow][6] = rating;
				tableArrReading[modelRow][8] = timesRead;

				String newStatus = moreInformation.getStatus();
				
				if (newStatus.equals("plan to read")) {
					//actionPerformedNewStatusPlanToRead(modelRow, URL, title, id, tags, artist, pages, rating, timesRead);
					((DefaultTableModel) table_panel2.getModel()).removeRow(modelRow);
					tableArrReading = rearangeArr(tableArrReading, modelRow);
					tableArr[tableArr.length - 1][1] = URL;
					tableArr[tableArr.length - 1][3] = title;
					tableArr[tableArr.length - 1][2] = id;
					tableArr[tableArr.length - 1][9] = tags;
					tableArr[tableArr.length - 1][4] = artist;
					tableArr[tableArr.length - 1][5] = pages;
					tableArr[tableArr.length - 1][6] = "plan to read";
					tableArr[tableArr.length - 1][8] = rating;
					tableArr[tableArr.length - 1][7] = timesRead;

					tableArr = expandArr(tableArr);
					expandTable(model, id);
				}
				else if(newStatus.equals("completed")) {
					//actionPerformedNewStatusCompleted(modelRow, URL, title, id, tags, artist, pages, rating, timesRead);
					((DefaultTableModel) table_panel2.getModel()).removeRow(modelRow);
					tableArrReading = rearangeArr(tableArrReading, modelRow);
					tableArrCompleted[tableArrCompleted.length - 1][1] = URL;
					tableArrCompleted[tableArrCompleted.length - 1][3] = title;
					tableArrCompleted[tableArrCompleted.length - 1][2] = id;
					tableArrCompleted[tableArrCompleted.length - 1][9] = tags;
					tableArrCompleted[tableArrCompleted.length - 1][4] = artist;
					tableArrCompleted[tableArrCompleted.length - 1][5] = pages;
					tableArrCompleted[tableArrCompleted.length - 1][8] = "completed";
					tableArrCompleted[tableArrCompleted.length - 1][6] = rating;
					tableArrCompleted[tableArrCompleted.length - 1][7] = timesRead;

					tableArrCompleted = expandArr(tableArrCompleted);
					expandTable(modelCompleted, id);
				}
			}
	    }
	};
	
	Action deleteTableArrCompletedRow = new AbstractAction()
	{
	    public void actionPerformed(ActionEvent e)
	    {
	        JTable table = (JTable)e.getSource();
	        int modelRow = Integer.valueOf( e.getActionCommand() );
	        
	        String title = tableArrCompleted[modelRow][3];
			String id = tableArrCompleted[modelRow][2];
			String tags = tableArrCompleted[modelRow][9];
			String artist = tableArrCompleted[modelRow][4];
			String pages = tableArrCompleted[modelRow][5];
			String rating = tableArrCompleted[modelRow][6];
			String status = tableArrCompleted[modelRow][8];
			String URL = tableArrCompleted[modelRow][1];
			String timesRead = tableArrCompleted[modelRow][7];

			String photoLocation;
			if(SFW) {
				int random = (int)(Math.random()*200);
				photoLocation = appdataLocation + mainFolderLocation + randomPhotoFolderLocation + "\\" + random + "_medium.jpg";
			}else {
				photoLocation = appdataLocation + mainFolderLocation + photoFolderLocation + "\\" + id + "_medium.jpg";
			}
			
			moreInformationPanel moreInformation = new moreInformationPanel(id, title, artist, pages, rating,
					timesRead, status, tags,
					photoLocation, SFW);
			UIManager.put("OptionPane.minimumSize", new Dimension(500, 900));
			JOptionPane inspectPane = new JOptionPane(moreInformation, JOptionPane.PLAIN_MESSAGE,
					JOptionPane.OK_OPTION);
			int result = inspectPane.showOptionDialog(null, moreInformation, "newEntry", 0,
					JOptionPane.PLAIN_MESSAGE, null, null, null);
			System.out.println("pressed" + e.getActionCommand());

			if (result == JOptionPane.OK_OPTION) {
				rating = moreInformation.getRating();
				timesRead = moreInformation.getTimesRead();
				tableArrCompleted[modelRow][6] = rating;
				tableArrCompleted[modelRow][7] = timesRead;

				String newStatus = moreInformation.getStatus();
				
				if (newStatus.equals("plan to read")) {
					//actionPerformedNewStatusPlanToRead(modelRow, URL, title, id, tags, artist, pages, rating, timesRead);
					((DefaultTableModel) table_panel3.getModel()).removeRow(modelRow);
					tableArrCompleted = rearangeArr(tableArrReading, modelRow);
					tableArr[tableArr.length - 1][1] = URL;
					tableArr[tableArr.length - 1][3] = title;
					tableArr[tableArr.length - 1][2] = id;
					tableArr[tableArr.length - 1][9] = tags;
					tableArr[tableArr.length - 1][4] = artist;
					tableArr[tableArr.length - 1][5] = pages;
					tableArr[tableArr.length - 1][6] = "plan to read";
					tableArr[tableArr.length - 1][8] = rating;
					tableArr[tableArr.length - 1][7] = timesRead;

					tableArr = expandArr(tableArr);
					expandTable(model, id);
				}
				else if (newStatus.equals("reading")) {
					//actionPerformedNewStatusReading(modelRow, URL, title, id, tags, artist, pages, rating, timesRead);
					((DefaultTableModel) table_panel3.getModel()).removeRow(modelRow);
					tableArrCompleted = rearangeArr(tableArr, modelRow);
					tableArrReading[tableArrReading.length - 1][1] = URL;
					tableArrReading[tableArrReading.length - 1][3] = title;
					tableArrReading[tableArrReading.length - 1][2] = id;
					tableArrReading[tableArrReading.length - 1][9] = tags;
					tableArrReading[tableArrReading.length - 1][4] = artist;
					tableArrReading[tableArrReading.length - 1][5] = pages;
					tableArrReading[tableArrReading.length - 1][8] = timesRead;
					tableArrReading[tableArrReading.length - 1][6] = rating;
					tableArrReading[tableArrReading.length - 1][7] = "reading";

					tableArrReading = expandArr(tableArrReading);
					expandTableReading(modelReading, id);
					
				}
			}
	    }
	};
	
	public void actionPerformedNewEntry(String start) {
		newEntryGeneral EntryGeneral = new newEntryGeneral(start);
		UIManager.put("OptionPane.minimumSize", new Dimension(400, 550));
		JOptionPane pane2 = new JOptionPane(EntryGeneral, JOptionPane.PLAIN_MESSAGE,
				JOptionPane.OK_CANCEL_OPTION);
		
		final JButton OKButton = new JButton();
		OKButton.setPreferredSize(new Dimension(75,25));
		OKButton.setIcon(new ImageIcon(moreInformationPanel.class.getResource("/grafics/settings/OK.png")));
		OKButton.setHorizontalTextPosition(SwingConstants.CENTER);
		OKButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane paneAP = getOptionPane((JComponent)e.getSource());
				paneAP.setValue(OKButton);
				Window w = SwingUtilities.getWindowAncestor(OKButton);

			    if (w != null) {
			      w.setVisible(false);
			    }
			}
			
		});
		OKButton.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent evt) {
				OKButton.setIcon(new ImageIcon(moreInformationPanel.class.getResource("/grafics/settings/OKHover.png")));
			}

			public void mouseExited(MouseEvent evt) {
				OKButton.setIcon(new ImageIcon(moreInformationPanel.class.getResource("/grafics/settings/OK.png")));
			}

			public void mousePressed(MouseEvent evt) {
				OKButton.setIcon(new ImageIcon(moreInformationPanel.class.getResource("/grafics/settings/OKSelected.png")));
			}

			public void mouseReleased(MouseEvent evt) {
				OKButton.setIcon(new ImageIcon(moreInformationPanel.class.getResource("/grafics/settings/OKHover.png")));
			}
		});
		
		final JButton cancelButton = new JButton();
		cancelButton.setPreferredSize(new Dimension(58,25));
		cancelButton.setIcon(new ImageIcon(moreInformationPanel.class.getResource("/grafics/close/cancel.png")));
		cancelButton.setHorizontalTextPosition(SwingConstants.CENTER);
		cancelButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane paneAP = getOptionPane((JComponent)e.getSource());
				paneAP.setValue(cancelButton);
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
		
		Component[] buttonText = new Component[]{	OKButton, cancelButton};
		
		int result2 = pane2.showOptionDialog(null, EntryGeneral, "new Entry", 0, JOptionPane.PLAIN_MESSAGE, null, buttonText, null);
		if (result2 == JOptionPane.OK_OPTION) {
			String code = EntryGeneral.getCode();
			String URL = EntryGeneral.getURL();
			String rating = EntryGeneral.getRating();
			String status = EntryGeneral.getStatus();
			boolean selected = EntryGeneral.getSelected();
			//TODO outsource following
			switch (status){
				case "plan to read":
					if (!code.equals("") || !URL.equals("")) {
						tableArr = nHentaiAPIRun.nHentaiAPIRun(tableArr, appdataLocation + mainFolderLocation + photoFolderLocation, code, "", rating, "plan to read");
						model = expandTable(model, code);
					}
					if (selected == true) {
						String[] TextAreaData = EntryGeneral.getDataInTextArea();
						for (int i = 0; i < TextAreaData.length; i++) {
							String rawData = TextAreaData[i];
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
							tableArr = nHentaiAPIRun.nHentaiAPIRun(tableArr, appdataLocation + mainFolderLocation + photoFolderLocation, rawCode, "", rawRating, "plan to read");
							model = expandTable(model, rawCode);
						}
					}
					break;
					
				case "reading":
					if (!code.equals("") || !URL.equals("")) {
						tableArrReading = nHentaiAPIRun.nHentaiAPIRunReading(tableArrReading, appdataLocation + mainFolderLocation + photoFolderLocation, code, "", rating, "reading");
						modelReading = expandTableReading(modelReading, code);
					}
					if (selected == true) {
						String[] TextAreaData = EntryGeneral.getDataInTextArea();
						for (int i = 0; i < TextAreaData.length; i++) {
							String rawData = TextAreaData[i];
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
							if(!rawRating.equals(""))
								rawRating = rawRating.substring(0, rawRating.length() - 1);
							tableArrReading = nHentaiAPIRun.nHentaiAPIRunReading(tableArrReading, appdataLocation + mainFolderLocation + photoFolderLocation, rawCode, "", rawRating, "reading");
							modelReading = expandTableReading(modelReading, rawCode);
						}
					}
					break;
				case "completed":
					if (!code.equals("") || !URL.equals("")) {
						tableArrCompleted = nHentaiAPIRun.nHentaiAPIRunCompleted(tableArrCompleted, appdataLocation + mainFolderLocation + photoFolderLocation, code, "", rating, "completed");
						modelCompleted = expandTableCompleted(modelCompleted, code);
					}
					if (selected == true) {
						String[] TextAreaData = EntryGeneral.getDataInTextArea();
						for (int i = 0; i < TextAreaData.length; i++) {
							String rawData = TextAreaData[i];
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
							if(!rawRating.equals(""))
								rawRating = rawRating.substring(0, rawRating.length() - 1);
							tableArrCompleted = nHentaiAPIRun.nHentaiAPIRunCompleted(tableArrCompleted, appdataLocation + mainFolderLocation + photoFolderLocation, rawCode, "", rawRating, "completed");
							modelCompleted = expandTableCompleted(modelCompleted, rawCode);
						}
					}
					break;
			}
		}
	}
	
	public void setVisible(boolean visible) {
		frmNhentaidatabase.setVisible(visible);
	}
	
	public void actionPerformedSetting() {
		settingsPanel settings = new settingsPanel(tableArr, tableArrReading, tableArrCompleted, SFW);
		UIManager.put("OptionPane.minimumSize", new Dimension(500, 250));
		JOptionPane pane = new JOptionPane(settings, JOptionPane.PLAIN_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
		
		final JButton OKButton = new JButton();
		OKButton.setPreferredSize(new Dimension(75,25));
		OKButton.setIcon(new ImageIcon(moreInformationPanel.class.getResource("/grafics/settings/OK.png")));
		OKButton.setHorizontalTextPosition(SwingConstants.CENTER);
		OKButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane paneAP = getOptionPane((JComponent)e.getSource());
				paneAP.setValue(OKButton);
				Window w = SwingUtilities.getWindowAncestor(OKButton);

			    if (w != null) {
			      w.setVisible(false);
			    }
			}
			
		});
		OKButton.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent evt) {
				OKButton.setIcon(new ImageIcon(moreInformationPanel.class.getResource("/grafics/settings/OKHover.png")));
			}

			public void mouseExited(MouseEvent evt) {
				OKButton.setIcon(new ImageIcon(moreInformationPanel.class.getResource("/grafics/settings/OK.png")));
			}

			public void mousePressed(MouseEvent evt) {
				OKButton.setIcon(new ImageIcon(moreInformationPanel.class.getResource("/grafics/settings/OKSelected.png")));
			}

			public void mouseReleased(MouseEvent evt) {
				OKButton.setIcon(new ImageIcon(moreInformationPanel.class.getResource("/grafics/settings/OKHover.png")));
			}
		});
		
		final JButton cancelButton = new JButton();
		cancelButton.setPreferredSize(new Dimension(58,25));
		cancelButton.setIcon(new ImageIcon(moreInformationPanel.class.getResource("/grafics/close/cancel.png")));
		cancelButton.setHorizontalTextPosition(SwingConstants.CENTER);
		cancelButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane paneAP = getOptionPane((JComponent)e.getSource());
				paneAP.setValue(cancelButton);
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
		
		Component[] buttonText = new Component[]{	OKButton, cancelButton};
		
		int result = pane.showOptionDialog(null, settings, "settings", 0, JOptionPane.PLAIN_MESSAGE, null, buttonText, null);
		if (result == JOptionPane.OK_OPTION) {
			SFW = settings.getSFW();
			
				for(int i=0;i<tableArr.length-1;i++) {
					reloadRowImage(i, "plan to read", tableArr[i][2]);
				}
				for(int i=0;i<tableArrReading.length-1;i++) {
					reloadRowImage(i, "reading", tableArrReading[i][2]);
				}
				for(int i=0;i<tableArrCompleted.length-1;i++) {
					reloadRowImage(i, "completed", tableArrCompleted[i][2]);
				}
				
			boolean delete = settings.getDelete();
			if(delete == true) {
				for(int i=0;i<tableArr.length-1;i++) {
					((DefaultTableModel) table_panel1.getModel()).removeRow(i);
				}
				for(int i=0;i<tableArrReading.length-1;i++) {
					((DefaultTableModel) table_panel2.getModel()).removeRow(i);
				}
				tableArr = new String[1][10];
				tableArrReading = new String[1][10];
				File file = new File(appdataLocation + mainFolderLocation + photoFolderLocation);
				Path fromFile = file.toPath();
				try {
					deleteDirectoryRecursion(fromFile);
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				file = new File(appdataLocation + mainFolderLocation + userDataFolderLocation);
				fromFile = file.toPath();
				try {
					deleteDirectoryRecursion(fromFile);
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				file = new File(appdataLocation + mainFolderLocation + randomPhotoFolderLocation);
				fromFile = file.toPath();
				try {
					deleteDirectoryRecursion(fromFile);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void deleteDirectoryRecursion(Path path) throws IOException {
		  if (Files.isDirectory(path, LinkOption.NOFOLLOW_LINKS)) {
		    try (DirectoryStream<Path> entries = Files.newDirectoryStream(path)) {
		      for (Path entry : entries) {
		        deleteDirectoryRecursion(entry);
		      }
		    }
		  }
		  Files.delete(path);
	}
	
	public void actionPerformedSafe(String[][] inputArr) {
		UIManager.put("OptionPane.background", new Color(244, 244, 244));
		UIManager.put("Panel.background", new Color(244, 244, 244));

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
        UIManager.put("OptionPane.background", new Color(35, 35, 35));
		UIManager.put("Panel.background", new Color(35, 35, 35));
	}
	
	public String[][] actionPerformedLoad() {
		UIManager.put("OptionPane.background", new Color(244, 244, 244));
		UIManager.put("Panel.background", new Color(244, 244, 244));
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
            return dataManager.readTable(LoadFileLocation);
        }
        UIManager.put("OptionPane.background", new Color(35, 35, 35));
        UIManager.put("Panel.background", new Color(35, 35, 35));

		return new String[1][10];
	}
	
	public void getSave() {
		String SaveFileLocation = appdataLocation + mainFolderLocation + userDataFolderLocation;
		
		tableArr = dataManager.readTable(SaveFileLocation + "\\nHentaiDatabasePlanToRead.nhdb");
		tableArrReading = dataManager.readTable(SaveFileLocation + "\\nHentaiDatabaseReading.nhdb");
		tableArrCompleted = dataManager.readTable(SaveFileLocation + "\\nHentaiDatabaseCompleted.nhdb");
		
		model = ArrToTable(model);
		modelReading = ArrToTableReading(modelReading);
		modelCompleted = ArrToTableCompleted(modelCompleted);
	}
	
	public void reloadRowImage(int index, String table, String Id) {			
		
		Icon img;
		if(SFW == false) {
			img = new ImageIcon(appdataLocation + mainFolderLocation + photoFolderLocation + "\\" + Id + "_low.jpg");			
		}else {
			int random = (int)(Math.random()*200);
			img = new ImageIcon(appdataLocation + mainFolderLocation + randomPhotoFolderLocation + "\\" + random + "_low.jpg");
		}
		
		Object[] data;
		
		switch (table){
		case "plan to read":
			data = new Object[8];
			for(int i=0;i<8;i++) {
				data[i] = tableArr [index][i];
			}
			data[0] = index+1;
			data[1] = img;
			model = updateRow(model, index, data);
			break;
		case "reading":
			data = new Object[9];
			for(int i=0;i<9;i++) {
				data[i] = tableArrReading [index][i];
			}
			data[0] = index+1;
			data[1] = img;
			modelReading = updateRow(modelReading, index, data);
			break;
		case "completed":
			data = new Object[10];
			for(int i=0;i<10;i++) {
				data[i] = tableArrCompleted [index][i];
			}
			data[0] = index+1;
			data[1] = img;
			modelCompleted = updateRow(modelCompleted, index, data);
			break;
		}
	}
	
	public DefaultTableModel updateRow(DefaultTableModel inputModel, int index,Object[] values)
    {
        for (int i = 0 ; i < values.length ; i++)
        {
        	inputModel.setValueAt(values[i],index,i);
        }
        return inputModel;
    }
	
	protected JOptionPane getOptionPane(JComponent parent) {
        JOptionPane pane = null;
        if (!(parent instanceof JOptionPane)) {
            pane = getOptionPane((JComponent)parent.getParent());
        } else {
            pane = (JOptionPane) parent;
        }
        return pane;
    }
	
	protected JButton createZeroButton() {
	    JButton button = new JButton("zero button");
	    Dimension zeroDim = new Dimension(0,0);
	    button.setPreferredSize(zeroDim);
	    button.setMinimumSize(zeroDim);
	    button.setMaximumSize(zeroDim);
	    return button;
	}
}