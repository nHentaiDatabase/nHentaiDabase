package settings;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.filechooser.FileSystemView;

import moreInformation.confirmDeleteEntry;
import moreInformation.moreInformationPanel;
import stats.statsPanel;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JCheckBox;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

public class settingsPanel extends JPanel {
	private JFileChooser myJFileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
	private String fileLocation;
	private boolean SFW;
	private boolean delete = false;
	/**
	 * Create the panel.
	 */
	public settingsPanel(String[][] planToRead, String[][] reading, String [][] completed, boolean sfw) {
		SFW = sfw;
		setForeground(Color.WHITE);
		setBackground(new Color(34, 34, 34));
		setLayout(null);
		
		JButton stats_btn = new JButton();
		stats_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UIManager.put("OptionPane.minimumSize", new Dimension(200, 100));
				statsPanel stats = new statsPanel(planToRead, reading, completed);
				UIManager.put("OptionPane.minimumSize", new Dimension(500, 900));
				JOptionPane inspectPane = new JOptionPane(stats, JOptionPane.PLAIN_MESSAGE,
						JOptionPane.OK_OPTION);
				int result = inspectPane.showOptionDialog(null, stats, "stats", 0,
						JOptionPane.PLAIN_MESSAGE, null, null, null);
			}
		});
		stats_btn.setIcon(new ImageIcon(moreInformationPanel.class.getResource("/grafics/settings/stats.png")));
		stats_btn.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent evt) {
				stats_btn.setIcon(new ImageIcon(moreInformationPanel.class.getResource("/grafics/settings/statsHover.png")));
			}

			public void mouseExited(MouseEvent evt) {
				stats_btn.setIcon(new ImageIcon(moreInformationPanel.class.getResource("/grafics/settings/stats.png")));
			}

			public void mousePressed(MouseEvent evt) {
				stats_btn.setIcon(new ImageIcon(moreInformationPanel.class.getResource("/grafics/settings/statsSelected.png")));
			}

			public void mouseReleased(MouseEvent evt) {
				stats_btn.setIcon(new ImageIcon(moreInformationPanel.class.getResource("/grafics/settings/statsHover.png")));
			}
		});
		stats_btn.setBounds(10, 11, 89, 23);
		add(stats_btn);
		
		JCheckBox SFWMode_ChBox = new JCheckBox("SFW Mode");
		SFWMode_ChBox.setForeground(Color.WHITE);
		SFWMode_ChBox.setBackground(new Color(34, 34, 34));
		SFWMode_ChBox.setSelected(sfw);
		SFWMode_ChBox.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				SFW = !SFW;
			}
		});
		SFWMode_ChBox.setBounds(10, 77, 97, 23);
		add(SFWMode_ChBox);
		
		JButton deleteAllData_btn = new JButton("");
		deleteAllData_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UIManager.put("OptionPane.minimumSize", new Dimension(200, 100));
				confirmDeleteEverything confirm = new confirmDeleteEverything();
        		JOptionPane pane = new JOptionPane(confirm, JOptionPane.PLAIN_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
				int result = pane.showOptionDialog(null, confirm, "confirm", 0, JOptionPane.PLAIN_MESSAGE, null, null, null);
				if(result == JOptionPane.OK_OPTION) {
					System.out.println("everything gets deleted");
					delete = true;
				}
			}
		});
		deleteAllData_btn.setIcon(new ImageIcon(moreInformationPanel.class.getResource("/grafics/settings/deleteAllData.png")));
		deleteAllData_btn.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent evt) {
				deleteAllData_btn.setIcon(new ImageIcon(moreInformationPanel.class.getResource("/grafics/settings/deleteAllDataHover.png")));
			}

			public void mouseExited(MouseEvent evt) {
				deleteAllData_btn.setIcon(new ImageIcon(moreInformationPanel.class.getResource("/grafics/settings/deleteAllData.png")));
			}

			public void mousePressed(MouseEvent evt) {
				deleteAllData_btn.setIcon(new ImageIcon(moreInformationPanel.class.getResource("/grafics/settings/deleteAllDataSelected.png")));
			}

			public void mouseReleased(MouseEvent evt) {
				deleteAllData_btn.setIcon(new ImageIcon(moreInformationPanel.class.getResource("/grafics/settings/deleteAllDataHover.png")));
			}
		});
		deleteAllData_btn.setBounds(10, 147, 150, 23);
		add(deleteAllData_btn);
		
		JLabel copyright_lbl = new JLabel("<html>Copyright: Philipp Bleimund");
		copyright_lbl.setForeground(Color.LIGHT_GRAY);
		copyright_lbl.setFont(new Font("Tahoma", Font.PLAIN, 15));
		copyright_lbl.setBounds(10, 181, 201, 68);
		add(copyright_lbl);

	}
	
	public String getFileLocation() {
		return fileLocation;
	}
	
	public boolean getSFW() {
		return SFW;
	}
	
	public boolean getDelete() {
		return delete;
	}
}
