import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.EventObject;
import java.util.Vector;

import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.imageio.ImageIO;
import javax.swing.AbstractCellEditor;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.text.View;

import settings.settings;
import settings.settingsPanel;
import datamanager.dataManager;
import moreInformation.moreInformationPanel;
import nHentaiWebScaper.nHentaiWebBase;
import newEntry.newEntry;
import newEntry.newEntryDialog;
import newEntry.newEntryGeneral;
import newEntry.newEntryPanel;
import newEntry.newEntryPanelRead;
import outsourcedClasses.nHentaiAPIRun;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.border.LineBorder;
import javax.swing.event.CellEditorListener;
import javax.swing.filechooser.FileSystemView;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicGraphicsUtils;
import javax.swing.plaf.basic.BasicTabbedPaneUI;
import javax.swing.JScrollPane;

public class nHentai {

	private JFrame frmNhentaidatabase;
	private JTable table_panel1;
	private String fileLocation;
	private DefaultTableModel model;
	private DefaultTableModel modelReading;

	private dataManager dataManager;
	private newEntry addNewEntry;
	private nHentaiWebBase nHentaiAPI;
	private nHentaiAPIRun nHentaiAPIRun;

	private String[][] tableArr;
	private String[][] tableArrReading;
	private String appdataLocation;
	String mainFolderLocation = "\\nHentaiDatabse";
	String photoFolderLocation = "\\savedPhotos";
	String userDataFolderLocation = "\\userData";

	Point mouseCoord;
	private JTable table_panel2;

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
		appdataLocation = System.getenv("APPDATA");
		setUpAppData(appdataLocation);
		initialize();

	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmNhentaidatabase = new JFrame();
		frmNhentaidatabase.getContentPane().setBackground(Color.DARK_GRAY);
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

		JButton closeWindow_btn = new JButton("");
		closeWindow_btn.setHorizontalTextPosition(SwingConstants.CENTER);
		closeWindow_btn.setIcon(new ImageIcon(nHentai.class.getResource("/grafics/Close.png")));
		closeWindow_btn.setBounds(922, 0, 24, 24);
		closeWindow_btn.setRequestFocusEnabled(false);
		closeWindow_btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				System.exit(0);
			}
		});
		windowToolbar.add(closeWindow_btn);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, 25, 946, 696);
		tabbedPane.setBackground(Color.BLACK);
		tabbedPane.setUI(new renderEngine.CustomTabbedPaneUI());
		frmNhentaidatabase.getContentPane().add(tabbedPane);

		JPanel planToRead_tab = new JPanel();
		planToRead_tab.setOpaque(true);
		planToRead_tab.setBackground(Color.BLACK);
		tabbedPane.addTab("plan to read", null, planToRead_tab, null);
		planToRead_tab.setLayout(null);
		
		

		JPanel panel_panel1 = new JPanel();
		panel_panel1.setBackground(new Color(0, 0, 102));
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

				newEntryGeneral EntryGeneral = new newEntryGeneral();
				UIManager.put("OptionPane.minimumSize", new Dimension(600, 800));
				JOptionPane pane2 = new JOptionPane(EntryGeneral, JOptionPane.PLAIN_MESSAGE,
						JOptionPane.OK_CANCEL_OPTION);
				int result2 = pane2.showOptionDialog(null, EntryGeneral, "settings", 0, JOptionPane.PLAIN_MESSAGE, null,
						null, null);
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
								//model = nHentaiAPIRun(code, URL, "N/A", "plan to read");
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
									tableArr = nHentaiAPIRun.nHentaiAPIRun(tableArr, appdataLocation + mainFolderLocation + photoFolderLocation, rawCode, "", rawRating, "plan to read");
									model = expandTable(model, rawCode);
									//model = nHentaiAPIRun(rawCode, "", rawRating, "plan to read");
								}
							}
						case "reading":
							if (!code.equals("") || !URL.equals("")) {
								tableArrReading = nHentaiAPIRun.nHentaiAPIRunReading(tableArrReading, appdataLocation + mainFolderLocation + photoFolderLocation, code, "", rating, "reading");
								modelReading = expandTableReading(modelReading, code);
								//model = nHentaiAPIRun(code, URL, "N/A", "plan to read");
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
									//model = nHentaiAPIRun(rawCode, "", rawRating, "plan to read");
								}
							}
					}
				}
			}
		});
		panel_panel1.add(newEntry_panel1_bnt);

		JButton settings_panel1_bnt = new JButton("settings");
		settings_panel1_bnt.setBounds(10, 586, 80, 35);
		settings_panel1_bnt.requestFocus();
		settings_panel1_bnt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				settingsPanel settings = new settingsPanel();
				UIManager.put("OptionPane.minimumSize", new Dimension(550, 200));
				JOptionPane pane = new JOptionPane(settings, JOptionPane.PLAIN_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
				int result = pane.showOptionDialog(null, settings, "settings", 0, JOptionPane.PLAIN_MESSAGE, null, null,
						null);
				if (result == JOptionPane.OK_OPTION) {
					fileLocation = settings.getFileLocation();
				}
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
				JFileChooser myJFileChooserSave = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
				myJFileChooserSave.setDialogTitle("Choose a file to load: ");
				myJFileChooserSave.setFileSelectionMode(JFileChooser.FILES_ONLY);
            	int returnValue = myJFileChooserSave.showOpenDialog(null);
            	String SaveFileLocation = "";
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = myJFileChooserSave.getSelectedFile();
                    System.out.println(selectedFile.getAbsolutePath());
                    SaveFileLocation = selectedFile.getAbsolutePath();
                }
				dataManager.saveTable(tableArr, SaveFileLocation);
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
				JFileChooser myJFileChooserLoad = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
				myJFileChooserLoad.setDialogTitle("Choose a file to load: ");
				myJFileChooserLoad.setFileSelectionMode(JFileChooser.FILES_ONLY);
            	int returnValue = myJFileChooserLoad.showOpenDialog(null);
            	String LoadFileLocation = "";
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = myJFileChooserLoad.getSelectedFile();
                    System.out.println(selectedFile.getAbsolutePath());
                    LoadFileLocation = selectedFile.getAbsolutePath();
                }
				tableArr = dataManager.readTable(LoadFileLocation);
				model = ArrToTable(model);
			}
		});
		panel_panel1.add(loadTable__panel1_btn);

		JScrollPane scrollPane_panel1 = new JScrollPane();
		scrollPane_panel1.setBounds(250, 11, 666, 632);
		planToRead_tab.add(scrollPane_panel1);

		table_panel1 = new JTable();
		table_panel1.setForeground(Color.WHITE);
		table_panel1.setBackground(new java.awt.Color(63, 63, 63));
		table_panel1.getTableHeader().setDefaultRenderer(new renderEngine.HeaderColor());;
		table_panel1.setRowSelectionAllowed(false);
		model = new DefaultTableModel(new Object[][] {

		}, new String[] { "number", "title picture", "id", "title", "author", "pages", "status", "" });
		table_panel1.setModel(model);
		table_panel1.getColumn(table_panel1.getColumnName(7)).setCellRenderer(new ButtonColumn());
		table_panel1.getColumn(table_panel1.getColumnName(7)).setCellEditor(new ButtonColumn());
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
		tabbedPane.addTab("reading", null, reading_tab, null);
		reading_tab.setLayout(null);

		JPanel panel_panel2 = new JPanel();
		panel_panel2.setLayout(null);
		panel_panel2.setBackground(new Color(0, 0, 102));
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
				newEntryPanelRead newEntryRead = new newEntryPanelRead();
				UIManager.put("OptionPane.minimumSize", new Dimension(450, 400));
				int result = JOptionPane.showConfirmDialog(null, newEntryRead);
				if (result == JOptionPane.OK_OPTION) {
					String code = newEntryRead.getCode();
					String URL = newEntryRead.getURL();
					String rating = newEntryRead.getRating();
					tableArrReading = nHentaiAPIRun.nHentaiAPIRunReading(tableArrReading, appdataLocation + mainFolderLocation + photoFolderLocation, code, URL, rating, "reading");
					modelReading = expandTableReading(modelReading, code);
					//modelReading = nHentaiAPIRunReading(code, URL, rating, "reading");
				}
			}
		});
		panel_panel2.add(newEntry_panel2_bnt);

		JButton settings_panel2_bnt = new JButton("settings");
		settings_panel2_bnt.setBounds(10, 586, 80, 35);
		settings_panel2_bnt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				settingsPanel settings = new settingsPanel();
				UIManager.put("OptionPane.minimumSize", new Dimension(550, 200));
				JOptionPane pane = new JOptionPane(settings, JOptionPane.PLAIN_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
				int result = pane.showOptionDialog(null, settings, "settings", 0, JOptionPane.PLAIN_MESSAGE, null, null,
						null);
				if (result == JOptionPane.OK_OPTION) {
					fileLocation = settings.getFileLocation();
				}
			}
		});
		panel_panel2.add(settings_panel2_bnt);

		JButton saveTable_panel2_btn = new JButton("save");
		saveTable_panel2_btn.setBounds(10, 552, 80, 23);
		panel_panel2.add(saveTable_panel2_btn);

		JButton loadTable__panel2_btn = new JButton("load");
		loadTable__panel2_btn.setBounds(100, 552, 80, 23);
		panel_panel2.add(loadTable__panel2_btn);

		JScrollPane scrollPane_panel2 = new JScrollPane();
		scrollPane_panel2.setBounds(250, 11, 666, 632);
		reading_tab.add(scrollPane_panel2);

		table_panel2 = new JTable();
		modelReading = new DefaultTableModel(new Object[][] {

		}, new String[] { "number", "title picture", "id", "title", "author", "pages", "rating", "status", "" });
		table_panel2.setModel(modelReading);
		table_panel2.getColumn(table_panel2.getColumnName(8)).setCellRenderer(new ButtonColumnReading());
		table_panel2.getColumn(table_panel2.getColumnName(8)).setCellEditor(new ButtonColumnReading());
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

		JPanel completed_tab = new JPanel();
		tabbedPane.addTab("completed", null, completed_tab, null);
		completed_tab.setLayout(null);

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
			Icon img = new ImageIcon(
					appdataLocation + mainFolderLocation + photoFolderLocation + "\\" + Id + "_low.jpg");
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
		Icon img = new ImageIcon(appdataLocation + mainFolderLocation + photoFolderLocation + "\\" + Id + "_low.jpg");
		tmp[1] = img;
		inputModel.addRow(tmp);
		return inputModel;
	}

	public DefaultTableModel expandTableReading(DefaultTableModel inputModel, String Id) {
		Object[] tmp = new Object[tableArrReading[0].length];
		for (int j = 0; j < tableArrReading[0].length; j++) {
			tmp[j] = tableArrReading[tableArrReading.length - 2][j];
		}
		tmp[0] = String.valueOf(tableArrReading.length - 1);
		Icon img = new ImageIcon(appdataLocation + mainFolderLocation + photoFolderLocation + "\\" + Id + "_low.jpg");
		tmp[1] = img;
		inputModel.addRow(tmp);
		return inputModel;
	}

	public void setUpAppData(String appData) {
		new File(appData + mainFolderLocation).mkdirs();
		new File(appData + mainFolderLocation + photoFolderLocation).mkdirs();
		new File(appData + mainFolderLocation + userDataFolderLocation).mkdirs();
	}

	public BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight)
			throws IOException {
		BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
		Graphics2D graphics2D = resizedImage.createGraphics();
		graphics2D.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);
		graphics2D.dispose();
		return resizedImage;
	}

	public void scaleImage(String location, String name, int x, int y) {
		try {
			ImageIcon ii = new ImageIcon(location + "_original.jpg");
			BufferedImage bi = new BufferedImage(x, y, BufferedImage.TYPE_INT_RGB);
			Graphics2D g2d = (Graphics2D) bi.createGraphics();
			g2d.addRenderingHints(
					new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY));
			boolean b = g2d.drawImage(ii.getImage(), 0, 0, x, y, null);
			System.out.println(b);
			ImageIO.write(bi, "jpg", new File(location + name));
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
				scaleImage(MainLocation, "_medium.jpg", 150, 212);
			}
			f = new File(MainLocation + "low.jpg");
			if(!f.exists()) {
				scaleImage(MainLocation, "_low.jpg", 50, 71);
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
			scaleImage(MainLocation, "_medium.jpg", 150, 212);
		}
		f = new File(MainLocation + "low.jpg");
		if(!f.exists()) {
			scaleImage(MainLocation, "_low.jpg", 50, 71);
		}
	}

	public class ButtonColumn extends AbstractCellEditor implements TableCellRenderer, TableCellEditor {

		private JButton button;
		private String value;

		public ButtonColumn() {
			button = new JButton();
			button.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {

					String row = value;

					String title = tableArr[Integer.valueOf(row)][3];
					String id = tableArr[Integer.valueOf(row)][2];
					String tags = tableArr[Integer.valueOf(row)][9];
					String artist = tableArr[Integer.valueOf(row)][4];
					String pages = tableArr[Integer.valueOf(row)][5];
					String rating = tableArr[Integer.valueOf(row)][8];
					String status = tableArr[Integer.valueOf(row)][6];
					String URL = tableArr[Integer.valueOf(row)][1];

					moreInformationPanel moreInformation = new moreInformationPanel(id, title, artist, pages, rating,
							"1", status, tags,
							appdataLocation + mainFolderLocation + photoFolderLocation + "\\" + id + "_medium.jpg");
					UIManager.put("OptionPane.minimumSize", new Dimension(500, 900));
					JOptionPane inspectPane = new JOptionPane(moreInformation, JOptionPane.PLAIN_MESSAGE,
							JOptionPane.OK_OPTION);
					int result = inspectPane.showOptionDialog(null, moreInformation, "settings", 0,
							JOptionPane.PLAIN_MESSAGE, null, null, null);
					System.out.println("pressed" + value);

					if (result == JOptionPane.OK_OPTION) {
						String newRating = moreInformation.getRating();
						String newStatus = moreInformation.getStatus();
						if (!newRating.equals(rating)) {
							tableArr[Integer.valueOf(row)][7] = newRating;
						}
						if (newStatus.equals("reading")) {
							((DefaultTableModel) table_panel1.getModel()).removeRow(Integer.valueOf(row));
							tableArr = rearangeArr(tableArr, Integer.valueOf(row));
							tableArrReading[tableArrReading.length - 1][1] = URL;
							tableArrReading[tableArrReading.length - 1][3] = title;
							tableArrReading[tableArrReading.length - 1][2] = id;
							tableArrReading[tableArrReading.length - 1][9] = tags;
							tableArrReading[tableArrReading.length - 1][4] = artist;
							tableArrReading[tableArrReading.length - 1][5] = pages;
							tableArrReading[tableArrReading.length - 1][7] = "reading";
							tableArrReading[tableArrReading.length - 1][6] = rating;

							tableArrReading = expandArr(tableArrReading);
							expandTableReading(modelReading, id);
						}
					}
				}
			});
		}

		@Override
		public Component getTableCellRendererComponent(JTable arg0, Object arg1, boolean arg2, boolean arg3, int arg4,
				int arg5) {
			button.setText("inspect");
			value = " " + arg4;
			return button;
		}

		@Override
		public Object getCellEditorValue() {
			return null;
		}

		@Override
		public Component getTableCellEditorComponent(JTable arg0, Object arg1, boolean arg2, int arg3, int arg4) {
			button.setText("inspect");
			value = "" + arg3;
			return button;
		}
	}

	public class ButtonColumnReading extends AbstractCellEditor implements TableCellRenderer, TableCellEditor {

		private JButton button;
		private String value;

		public ButtonColumnReading() {
			button = new JButton();
			button.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {

					String row = value;

					String title = tableArrReading[Integer.valueOf(row)][3];
					String id = tableArrReading[Integer.valueOf(row)][2];
					String tags = tableArrReading[Integer.valueOf(row)][9];
					String artist = tableArrReading[Integer.valueOf(row)][4];
					String pages = tableArrReading[Integer.valueOf(row)][5];
					String rating = tableArrReading[Integer.valueOf(row)][8];
					String status = tableArrReading[Integer.valueOf(row)][6];
					String URL = tableArrReading[Integer.valueOf(row)][1];

					moreInformationPanel moreInformation = new moreInformationPanel(id, title, artist, pages, rating,
							"1", status, tags,
							appdataLocation + mainFolderLocation + photoFolderLocation + "\\" + id + "_medium.jpg");
					UIManager.put("OptionPane.minimumSize", new Dimension(500, 900));
					JOptionPane inspectPane = new JOptionPane(moreInformation, JOptionPane.PLAIN_MESSAGE,
							JOptionPane.OK_OPTION);
					int result = inspectPane.showOptionDialog(null, moreInformation, "settings", 0,
							JOptionPane.PLAIN_MESSAGE, null, null, null);
					System.out.println("pressed" + value);

					if (result == JOptionPane.OK_OPTION) {
						String newRating = moreInformation.getRating();
						String newStatus = moreInformation.getStatus();
						if (!newRating.equals(rating)) {
							tableArrReading[Integer.valueOf(row)][7] = newRating;
						}
						if (newStatus.equals("plan to read")) {
							((DefaultTableModel) table_panel2.getModel()).removeRow(Integer.valueOf(row));
							tableArrReading = rearangeArr(tableArrReading, Integer.valueOf(row));
							tableArr[tableArr.length - 1][1] = URL;
							tableArr[tableArr.length - 1][3] = title;
							tableArr[tableArr.length - 1][2] = id;
							tableArr[tableArr.length - 1][9] = tags;
							tableArr[tableArr.length - 1][4] = artist;
							tableArr[tableArr.length - 1][5] = pages;
							tableArr[tableArr.length - 1][6] = "plan to read";
							tableArr[tableArr.length - 1][8] = rating;

							tableArr = expandArr(tableArr);
							expandTable(model, id);
						}
					}
				}
			});
		}

		@Override
		public Component getTableCellRendererComponent(JTable arg0, Object arg1, boolean arg2, boolean arg3, int arg4,
				int arg5) {
			button.setText("inspect");
			value = " " + arg4;
			return button;
		}

		@Override
		public Object getCellEditorValue() {
			return null;
		}

		@Override
		public Component getTableCellEditorComponent(JTable arg0, Object arg1, boolean arg2, int arg3, int arg4) {
			button.setText("inspect");
			value = "" + arg3;
			return button;
		}
	}
}
