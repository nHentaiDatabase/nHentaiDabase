package jPanels;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import nHentaiMainGUI.nHentai;
import outsourcedClasses.AnimationExpandExportPanel;

public class ControlPanel extends JPanel {

	public JCheckBox searchId_CBox;
	public JCheckBox searchTitle_CBox;
	public JCheckBox searchAuthor_CBox;
	public JCheckBox searchTags_CBox;
	
	public JButton newEntry_bnt;
	public JButton settings_bnt;
	public JButton saveTable_btn;
	public JButton loadTable_btn;
	public JTextField search_TField;
	public JButton search_btn;
	public JButton clear_btn;
	public JCheckBox showMore_CBox;
	public ExportPopUpPanel exportPanel;
	
	public JPanel expandExport_pnl;
	
	/**
	 * Create the panel.
	 */
	public ControlPanel() {
		
		setBackground(new Color(0, 44, 88));
		setBounds(10, 11, 230, 632);
		setLayout(null);
		
		newEntry_bnt = new JButton("new Entry");
		newEntry_bnt.setSelectedIcon(new ImageIcon(nHentai.class.getResource("/grafics/Button.png")));
		newEntry_bnt.setForeground(Color.WHITE);
		newEntry_bnt.setFont(new Font("Tahoma", Font.PLAIN, 20));
		newEntry_bnt.setHorizontalTextPosition(SwingConstants.CENTER);
		newEntry_bnt.setIcon(new ImageIcon(nHentai.class.getResource("/grafics/Button.png")));
		newEntry_bnt.setBounds(10, 243, 210, 35);
		newEntry_bnt.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent evt) {
				newEntry_bnt.setIcon(new ImageIcon(nHentai.class.getResource("/grafics/ButtonHower.png")));
			}

			public void mouseExited(MouseEvent evt) {
				newEntry_bnt.setIcon(new ImageIcon(nHentai.class.getResource("/grafics/Button.png")));
			}

			public void mousePressed(MouseEvent evt) {
				newEntry_bnt.setIcon(new ImageIcon(nHentai.class.getResource("/grafics/ButtonSelected.png")));
			}

			public void mouseReleased(MouseEvent evt) {
				newEntry_bnt.setIcon(new ImageIcon(nHentai.class.getResource("/grafics/ButtonHower.png")));
			}
		});
		add(newEntry_bnt);

		settings_bnt = new JButton("");
		settings_bnt.setBounds(10, 586, 112, 35);
		settings_bnt.requestFocus();
		settings_bnt.setIcon(new ImageIcon(nHentai.class.getResource("/grafics/MainGUI/settings.png")));
		settings_bnt.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent evt) {
				settings_bnt.setIcon(new ImageIcon(nHentai.class.getResource("/grafics/MainGUI/settingsHover.png")));
			}

			public void mouseExited(MouseEvent evt) {
				settings_bnt.setIcon(new ImageIcon(nHentai.class.getResource("/grafics/MainGUI/settings.png")));
			}

			public void mousePressed(MouseEvent evt) {
				settings_bnt.setIcon(new ImageIcon(nHentai.class.getResource("/grafics/MainGUI/settingsSelected.png")));
			}

			public void mouseReleased(MouseEvent evt) {
				settings_bnt.setIcon(new ImageIcon(nHentai.class.getResource("/grafics/MainGUI/settingsHover.png")));
			}
		});
		add(settings_bnt);

		saveTable_btn = new JButton("");
		saveTable_btn.setFont(new Font("Tahoma", Font.PLAIN, 20));
		saveTable_btn.setForeground(Color.WHITE);
		saveTable_btn.setIcon(new ImageIcon(nHentai.class.getResource("/grafics/saveButton.png")));
		saveTable_btn.setBounds(10, 552, 80, 25);
		saveTable_btn.setHorizontalTextPosition(SwingConstants.CENTER);
		saveTable_btn.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent evt) {
				saveTable_btn.setIcon(new ImageIcon(nHentai.class.getResource("/grafics/saveButtonHover.png")));
			}

			public void mouseExited(MouseEvent evt) {
				saveTable_btn.setIcon(new ImageIcon(nHentai.class.getResource("/grafics/saveButton.png")));
			}

			public void mousePressed(MouseEvent evt) {
				saveTable_btn.setIcon(new ImageIcon(nHentai.class.getResource("/grafics/saveButtonSelected.png")));
			}

			public void mouseReleased(MouseEvent evt) {
				saveTable_btn.setIcon(new ImageIcon(nHentai.class.getResource("/grafics/saveButtonHover.png")));
			}
		});
		saveTable_btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				
				
				
				AnimationExpandExportPanel a = new AnimationExpandExportPanel(expandExport_pnl);
				a.execute();
				
			}
		});
		add(saveTable_btn);

		loadTable_btn = new JButton("");
		loadTable_btn.setIcon(new ImageIcon(nHentai.class.getResource("/grafics/loadButton.png")));
		loadTable_btn.setBounds(100, 552, 80, 25);
		loadTable_btn.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent evt) {
				loadTable_btn.setIcon(new ImageIcon(nHentai.class.getResource("/grafics/loadButtonHover.png")));
			}

			public void mouseExited(MouseEvent evt) {
				loadTable_btn.setIcon(new ImageIcon(nHentai.class.getResource("/grafics/loadButton.png")));
			}

			public void mousePressed(MouseEvent evt) {
				loadTable_btn.setIcon(new ImageIcon(nHentai.class.getResource("/grafics/loadButtonSelected.png")));
			}

			public void mouseReleased(MouseEvent evt) {
				loadTable_btn.setIcon(new ImageIcon(nHentai.class.getResource("/grafics/loadButtonHover.png")));
			}
		});
		add(loadTable_btn);
		
		search_TField = new JTextField();
		search_TField.setBackground(Color.WHITE);
		search_TField.setToolTipText("");
		search_TField.setBounds(10, 11, 157, 23);
		search_TField.setColumns(10);
		add(search_TField);
		
		search_btn = new JButton();
		search_btn.setHorizontalTextPosition(SwingConstants.CENTER);
		search_btn.setIcon(new ImageIcon(nHentai.class.getResource("/grafics/searchClear/search.png")));
		search_btn.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent evt) {
					search_btn.setIcon(new ImageIcon(nHentai.class.getResource("/grafics/searchClear/searchHover.png")));
			}

			public void mouseExited(MouseEvent evt) {
					search_btn.setIcon(new ImageIcon(nHentai.class.getResource("/grafics/searchClear/search.png")));
			}

			public void mousePressed(MouseEvent evt) {
					search_btn.setIcon(new ImageIcon(nHentai.class.getResource("/grafics/searchClear/searchSelected.png")));
			}

			public void mouseReleased(MouseEvent evt) {
					search_btn.setIcon(new ImageIcon(nHentai.class.getResource("/grafics/searchClear/searchHover.png")));
			}
		});
		search_btn.setBounds(197, 11, 22, 23);
		add(search_btn);
		
		
		clear_btn = new JButton();
		clear_btn.setHorizontalTextPosition(SwingConstants.CENTER);
		clear_btn.setIcon(new ImageIcon(nHentai.class.getResource("/grafics/searchClear/clear.png")));
		clear_btn.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent evt) {
				clear_btn.setIcon(new ImageIcon(nHentai.class.getResource("/grafics/searchClear/clearHover.png")));
			}

			public void mouseExited(MouseEvent evt) {
				clear_btn.setIcon(new ImageIcon(nHentai.class.getResource("/grafics/searchClear/clear.png")));
			}

			public void mousePressed(MouseEvent evt) {
				clear_btn.setIcon(new ImageIcon(nHentai.class.getResource("/grafics/searchClear/clearSelected.png")));
			}

			public void mouseReleased(MouseEvent evt) {
				clear_btn.setIcon(new ImageIcon(nHentai.class.getResource("/grafics/searchClear/clearHover.png")));
			}
		});
		clear_btn.setBounds(169, 11, 22, 23);
		add(clear_btn);
		
		searchId_CBox = new JCheckBox("id");
		searchId_CBox.setForeground(Color.WHITE);
		searchId_CBox.setBackground(new Color(0, 44, 88));
		searchId_CBox.setBounds(10, 64, 52, 23);
		searchId_CBox.setVisible(false);
		searchId_CBox.setSelected(true);
		add(searchId_CBox);
		
		searchTitle_CBox = new JCheckBox("title");
		searchTitle_CBox.setBackground(new Color(0, 44, 88));
		searchTitle_CBox.setForeground(Color.WHITE);
		searchTitle_CBox.setBounds(10, 90, 52, 23);
		searchTitle_CBox.setVisible(false);
		searchTitle_CBox.setSelected(true);
		add(searchTitle_CBox);
		
		searchAuthor_CBox = new JCheckBox("author");
		searchAuthor_CBox.setBackground(new Color(0, 44, 88));
		searchAuthor_CBox.setForeground(Color.WHITE);
		searchAuthor_CBox.setBounds(83, 64, 68, 23);
		searchAuthor_CBox.setVisible(false);
		searchAuthor_CBox.setSelected(true);
		add(searchAuthor_CBox);
		
		searchTags_CBox = new JCheckBox("tags");
		searchTags_CBox.setBackground(new Color(0, 44, 88));
		searchTags_CBox.setForeground(Color.WHITE);
		searchTags_CBox.setBounds(83, 90, 68, 23);
		searchTags_CBox.setVisible(false);
		searchTags_CBox.setSelected(true);
		add(searchTags_CBox);

		showMore_CBox = new JCheckBox("show more");
		showMore_CBox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED) {//checkbox has been selected
					searchId_CBox.setVisible(true);
					searchTitle_CBox.setVisible(true);
					searchAuthor_CBox.setVisible(true);
					searchTags_CBox.setVisible(true);
				} else {//checkbox has been deselected
					searchId_CBox.setVisible(false);
					searchTitle_CBox.setVisible(false);
					searchAuthor_CBox.setVisible(false);
					searchTags_CBox.setVisible(false);
				};
			}
		});
		showMore_CBox.setForeground(Color.WHITE);
		showMore_CBox.setBackground(new Color(0, 44, 88));
		showMore_CBox.setBounds(10, 38, 80, 23);
		add(showMore_CBox);
		
		
		exportPanel = new ExportPopUpPanel();
		expandExport_pnl = exportPanel.getPanel();
		add(expandExport_pnl);
	}
	
	public boolean[] getSearchConfig() {
		boolean[] config = new boolean[4];

			config[0] = searchId_CBox.isSelected();
			config[1] = searchTitle_CBox.isSelected();
			config[2] = searchAuthor_CBox.isSelected();
			config[3] = searchTags_CBox.isSelected();

		return config;
	}
}
