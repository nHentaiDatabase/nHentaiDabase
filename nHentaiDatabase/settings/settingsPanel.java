package settings;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.filechooser.FileSystemView;

import moreInformation.confirmDeleteEntry;
import moreInformation.moreInformationPanel;
import nHentaiMainGUI.nHentai;
import outsourcedClasses.Methods;
import stats.statsPanel;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.Font;
import java.awt.Frame;
import java.awt.Point;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

public class settingsPanel extends JPanel {
	private boolean SFW;
	private boolean delete = false;
	private Point mouseCoord;
	
	private Methods methods = new Methods();
	/**
	 * Create the panel.
	 */
	public settingsPanel(String[][] planToRead, String[][] reading, String [][] completed, boolean sfw, boolean resScroll) {
		SFW = sfw;
		setForeground(Color.WHITE);
		setBackground(new Color(34, 34, 34));
		setLayout(null);
		
		JButton stats_btn = new JButton();
		stats_btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Component[] tmp = methods.OKCancelButtonCreate();
				Component[] buttonText = new Component[1];
				buttonText[0] = tmp[0];
				
				UIManager.put("OptionPane.minimumSize", new Dimension(600, 350));
				statsPanel stats = new statsPanel(planToRead, reading, completed, resScroll);
				JOptionPane inspectPane = new JOptionPane(stats, JOptionPane.PLAIN_MESSAGE,
						JOptionPane.OK_OPTION, null, buttonText, null);
				
				final JDialog dialog = new JDialog();
				
				inspectPane.addPropertyChangeListener(new PropertyChangeListener() {
		            @Override
		            public void propertyChange(PropertyChangeEvent evt) {
		                String name = evt.getPropertyName();
		                if ("value".equals(name)) {
		                	dialog.dispose();
		                    final Object value = inspectPane.getValue();
		                    System.out.println(value);
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
				dialogcloseWindow_btn.setIcon(new ImageIcon(settingsPanel.class.getResource("/grafics/Close.png")));
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
				
				Component[] buttonText = methods.OKCancelButtonCreate();
				
        		JOptionPane pane = new JOptionPane(confirm, JOptionPane.PLAIN_MESSAGE, JOptionPane.OK_CANCEL_OPTION, null, buttonText, null);
				
				final JDialog dialog = new JDialog((Frame)null, "Boo");
				
		        pane.addPropertyChangeListener(new PropertyChangeListener() {
		            @Override
		            public void propertyChange(PropertyChangeEvent evt) {
		                String name = evt.getPropertyName();
		                if ("value".equals(name)) {
		                	dialog.dispose();
		                    final Object value = pane.getValue();
		                    System.out.println(value);
		                    if(value.equals("OK")) {
		                    	System.out.println("everything gets deleted");
		    					delete = true;
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
	
	/**
	 * get the SFW (save for work)
	 * 
	 * @return boolean
	 */
	public boolean getSFW() {
		return SFW;
	}
	
	/**
	 * get if all Data gets deleted
	 * 
	 * @return boolean
	 */
	public boolean getDelete() {
		return delete;
	}
}
