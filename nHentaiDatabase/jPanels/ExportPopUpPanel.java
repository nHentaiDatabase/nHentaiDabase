package jPanels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import nHentaiMainGUI.nHentai;
import renderEngine.dropShadowPane;

public class ExportPopUpPanel extends JPanel{

	public JPanel expandExport_internalPanel;
	public JButton expand_button1_btn;
	public JButton expand_button2_btn;
	public JButton expand_button3_btn;
	public dropShadowPane expandExport_pnl;
	
	/**
	 * Create the panel.
	 */
	public ExportPopUpPanel() {
		
		expandExport_internalPanel = new JPanel();
		expandExport_internalPanel.setVisible(true);
		expandExport_internalPanel.setBackground(new Color(0, 44, 88));
		expandExport_internalPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
				

		expand_button1_btn = new JButton("Save Tab      ");
		expand_button1_btn.setIcon(new ImageIcon(nHentai.class.getResource("/grafics/expandExort/Background.png")));
		expand_button1_btn.setForeground(Color.WHITE);
		expand_button1_btn.setPreferredSize(new Dimension(135, 30));
		expand_button1_btn.setFont(new Font("Tahoma", Font.PLAIN, 17));
		expand_button1_btn.setHorizontalTextPosition(SwingConstants.CENTER);
		expand_button1_btn.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent evt) {
				expand_button1_btn.setIcon(new ImageIcon(nHentai.class.getResource("/grafics/expandExort/BackgroundHover.png")));
			}

			public void mouseExited(MouseEvent evt) {
				expand_button1_btn.setIcon(new ImageIcon(nHentai.class.getResource("/grafics/expandExort/Background.png")));
			}

			public void mousePressed(MouseEvent evt) {
				expand_button1_btn.setIcon(new ImageIcon(nHentai.class.getResource("/grafics/expandExort/Background.png")));
			}

			public void mouseReleased(MouseEvent evt) {
				expand_button1_btn.setIcon(new ImageIcon(nHentai.class.getResource("/grafics/expandExort/BackgroundHover.png")));
			}
		});
		expandExport_internalPanel.add(expand_button1_btn);
		
		expand_button2_btn = new JButton("Export Tab    ");
		expand_button2_btn.setIcon(new ImageIcon(nHentai.class.getResource("/grafics/expandExort/Background.png")));
		expand_button2_btn.setForeground(Color.WHITE);
		expand_button2_btn.setPreferredSize(new Dimension(135, 30));
		expand_button2_btn.setFont(new Font("Tahoma", Font.PLAIN, 17));
		expand_button2_btn.setHorizontalTextPosition(SwingConstants.CENTER);
		expand_button2_btn.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent evt) {
				expand_button2_btn.setIcon(new ImageIcon(nHentai.class.getResource("/grafics/expandExort/BackgroundHover.png")));
			}

			public void mouseExited(MouseEvent evt) {
				expand_button2_btn.setIcon(new ImageIcon(nHentai.class.getResource("/grafics/expandExort/Background.png")));
			}

			public void mousePressed(MouseEvent evt) {
				expand_button2_btn.setIcon(new ImageIcon(nHentai.class.getResource("/grafics/expandExort/Background.png")));
			}

			public void mouseReleased(MouseEvent evt) {
				expand_button2_btn.setIcon(new ImageIcon(nHentai.class.getResource("/grafics/expandExort/BackgroundHover.png")));
			}
		});
		expandExport_internalPanel.add(expand_button2_btn);
		
		expand_button3_btn = new JButton("Export id-List");
		expand_button3_btn.setIcon(new ImageIcon(nHentai.class.getResource("/grafics/expandExort/Background.png")));
		expand_button3_btn.setForeground(Color.WHITE);
		expand_button3_btn.setPreferredSize(new Dimension(135, 30));
		expand_button3_btn.setFont(new Font("Tahoma", Font.PLAIN, 17));
		expand_button3_btn.setHorizontalTextPosition(SwingConstants.CENTER);
		expand_button3_btn.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent evt) {
				expand_button3_btn.setIcon(new ImageIcon(nHentai.class.getResource("/grafics/expandExort/BackgroundHover.png")));
			}

			public void mouseExited(MouseEvent evt) {
				expand_button3_btn.setIcon(new ImageIcon(nHentai.class.getResource("/grafics/expandExort/Background.png")));
			}

			public void mousePressed(MouseEvent evt) {
				expand_button3_btn.setIcon(new ImageIcon(nHentai.class.getResource("/grafics/expandExort/Background.png")));
			}

			public void mouseReleased(MouseEvent evt) {
				expand_button3_btn.setIcon(new ImageIcon(nHentai.class.getResource("/grafics/expandExort/BackgroundHover.png")));
			}
		});
		expandExport_internalPanel.add(expand_button3_btn);
		
		expandExport_pnl = new dropShadowPane(expandExport_internalPanel, 0, 0, 0, 0);
		expandExport_pnl.setBounds(10, 455, 143, 98);
		expandExport_pnl.setSize(143, 98);
		expandExport_pnl.setBackground(new Color(0, 44, 88));
		expandExport_pnl.setForeground(Color.WHITE);
	}

	public dropShadowPane getPanel() {
		expandExport_pnl = new dropShadowPane(expandExport_internalPanel, 0, 0, 0, 0);
		expandExport_pnl.setBounds(10, 455, 143, 98);
		expandExport_pnl.setSize(143, 98);
		expandExport_pnl.setBackground(new Color(0, 44, 88));
		expandExport_pnl.setForeground(Color.WHITE);
		return expandExport_pnl;
	}
	
}
