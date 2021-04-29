package moreInformation;

import javax.swing.JPanel;
import javax.swing.JScrollBar;

import java.awt.Desktop;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.ListCellRenderer;

import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.PopupMenuEvent;
import javax.swing.plaf.basic.BasicArrowButton;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.plaf.basic.BasicScrollBarUI;

import nHentaiMainGUI.nHentai;
import outsourcedClasses.Methods;
import renderEngine.renderMethods;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import javax.swing.event.PopupMenuListener;
import javax.swing.border.MatteBorder;

public class moreInformationPanel extends JPanel {
	private JTextField id_TField;
	private JTextField title_TField;
	private JTextField author_TField;
	private JTextField pages_TField;
	private JTextField timesRead_TField;

	private JComboBox status_CBox;
	private JComboBox rating_CBox;
	
	private Methods methods = new Methods();
	private renderMethods RenderMethods = new renderMethods();
	
	private Point mouseCoord;
	
	boolean deleteEntry = false;
	/**
	 * Create the panel.
	 */
	public moreInformationPanel(String id, String title, String author, String pages, String rating, String timesRead, String status, String tags, String pictureLocation, boolean SFW, boolean scroll) {
		setBackground(new Color(35, 35, 35));
		
		methods.splitTagsUp(tags);
		
		setLayout(null);	
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(0, 0, 466, 759);
		panel_2.setPreferredSize(new Dimension(466, 759));
		panel_2.setBackground(new Color(35, 35, 35));
		panel_2.setLayout(null);
		panel_2.setBorder(null);
		panel_2.setVisible(true);
		add(panel_2);

        
		JLabel Id_lbl = new JLabel("id");
		Id_lbl.setForeground(Color.WHITE);
		Id_lbl.setBounds(22, 151, 46, 14);
			panel_2.add(Id_lbl);
		
		JLabel title_lbl = new JLabel("title");
		title_lbl.setForeground(Color.WHITE);
		title_lbl.setBounds(22, 221, 46, 14);
			panel_2.add(title_lbl);
		
		JLabel author_lbl = new JLabel("author");
		author_lbl.setForeground(Color.WHITE);
		author_lbl.setBounds(22, 291, 46, 14);
			panel_2.add(author_lbl);
		
		JLabel pages_lbl = new JLabel("pages");
		pages_lbl.setForeground(Color.WHITE);
		pages_lbl.setBounds(22, 361, 46, 14);
			panel_2.add(pages_lbl);
		
		JLabel rating_lbl = new JLabel("rating");
		rating_lbl.setForeground(Color.WHITE);
		rating_lbl.setBounds(22, 431, 46, 14);
			panel_2.add(rating_lbl);
		
		JLabel timesRead_lbl = new JLabel("times read");
		timesRead_lbl.setForeground(Color.WHITE);
		timesRead_lbl.setBounds(22, 501, 82, 14);
			panel_2.add(timesRead_lbl);
		
		JLabel status_lbl = new JLabel("status");
		status_lbl.setForeground(Color.WHITE);
		status_lbl.setBounds(22, 571, 46, 14);
			panel_2.add(status_lbl);
		
		JLabel titlePicture_lbl = new JLabel("title picture");
		titlePicture_lbl.setForeground(Color.WHITE);
		titlePicture_lbl.setBounds(216, 11, 74, 14);
			panel_2.add(titlePicture_lbl);
		
		JPanel panel = new JPanel();
		panel.setBounds(300, 11, 150, 212);
			panel_2.add(panel);
		
		JLabel image_lbl = new JLabel("");
		image_lbl.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				openWebsite open = new openWebsite();
				
				
				final JButton openButton = new JButton();
				openButton.setPreferredSize(new Dimension(57,23));
				openButton.setIcon(new ImageIcon(moreInformationPanel.class.getResource("/grafics/openURLButtons/openButton.png")));
				openButton.setHorizontalTextPosition(SwingConstants.CENTER);
				openButton.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						JOptionPane paneAP = methods.getOptionPane((JComponent)e.getSource());
						paneAP.setValue("open");
						Window w = SwingUtilities.getWindowAncestor(openButton);

					    if (w != null) {
					      w.setVisible(false);
					    }
					}
					
				});
				openButton.addMouseListener(new MouseAdapter() {
					public void mouseEntered(MouseEvent evt) {
						openButton.setIcon(new ImageIcon(moreInformationPanel.class.getResource("/grafics/openURLButtons/openButtonHover.png")));
					}

					public void mouseExited(MouseEvent evt) {
						openButton.setIcon(new ImageIcon(moreInformationPanel.class.getResource("/grafics/openURLButtons/openButton.png")));
					}

					public void mousePressed(MouseEvent evt) {
						openButton.setIcon(new ImageIcon(moreInformationPanel.class.getResource("/grafics/openURLButtons/openButtonSelected.png")));
					}

					public void mouseReleased(MouseEvent evt) {
						openButton.setIcon(new ImageIcon(moreInformationPanel.class.getResource("/grafics/openURLButtons/openButtonHover.png")));
					}
				});
				JButton copyButton = new JButton();
				copyButton.setPreferredSize(new Dimension(57,23));
				copyButton.setIcon(new ImageIcon(moreInformationPanel.class.getResource("/grafics/openURLButtons/copyButton.png")));
				copyButton.setHorizontalTextPosition(SwingConstants.CENTER);
				copyButton.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						JOptionPane paneAP = methods.getOptionPane((JComponent)e.getSource());
						paneAP.setValue("copy");
						Window w = SwingUtilities.getWindowAncestor(openButton);

					    if (w != null) {
					      w.setVisible(false);
					    }
					}
					
				});
				copyButton.addMouseListener(new MouseAdapter() {
					public void mouseEntered(MouseEvent evt) {
						copyButton.setIcon(new ImageIcon(moreInformationPanel.class.getResource("/grafics/openURLButtons/copyButtonHover.png")));
					}

					public void mouseExited(MouseEvent evt) {
						copyButton.setIcon(new ImageIcon(moreInformationPanel.class.getResource("/grafics/openURLButtons/copyButton.png")));
					}

					public void mousePressed(MouseEvent evt) {
						copyButton.setIcon(new ImageIcon(moreInformationPanel.class.getResource("/grafics/openURLButtons/copyButtonSelected.png")));
					}

					public void mouseReleased(MouseEvent evt) {
						copyButton.setIcon(new ImageIcon(moreInformationPanel.class.getResource("/grafics/openURLButtons/copyButtonHover.png")));
					}
				});
				UIManager.put("OptionPane.minimumSize", new Dimension(400, 110));
				Component[] buttonText = new Component[]{	openButton, 
															copyButton};
				JOptionPane pane = new JOptionPane(open, JOptionPane.PLAIN_MESSAGE,
						JOptionPane.YES_NO_OPTION, null, buttonText, null);
				final JDialog dialog = new JDialog((Frame)null, "Boo");
				
		        pane.addPropertyChangeListener(new PropertyChangeListener() {
		            @Override
		            public void propertyChange(PropertyChangeEvent evt) {
		                String name = evt.getPropertyName();
		                if ("value".equals(name)) {
		                	dialog.dispose();
		                    final Object value = pane.getValue();
		                    System.out.println(value);
		                    if(value.equals("open")) {
		    					try {
		    						Desktop.getDesktop().browse(new URI("https://nhentai.net/g/"+ id+ "/"));
		    					} catch (IOException | URISyntaxException e1) {
		    						// TODO Auto-generated catch block
		    						e1.printStackTrace();
		    					}
		    				}else if(value.equals("copy")) {
		    					String copy = "https://nhentai.net/g/"+ id+ "/";
		    					StringSelection stringSelection = new StringSelection(copy);
		    					Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		    					clipboard.setContents(stringSelection, null);
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
		image_lbl.setIcon(new ImageIcon(pictureLocation));
		panel.add(image_lbl);
		
		
		id_TField = new JTextField();
		id_TField.setEditable(false);
		id_TField.setForeground(Color.WHITE);
		id_TField.setBackground(new Color(59, 59, 59));
		id_TField.setBounds(22, 167, 86, 20);
		id_TField.setBorder(null);
			panel_2.add(id_TField);
		id_TField.setColumns(10);
		
		title_TField = new JTextField();
		title_TField.setEditable(false);
		title_TField.setBackground(new Color(59, 59, 59));
		title_TField.setForeground(Color.WHITE);
		title_TField.setBounds(22, 237, 268, 20);
		title_TField.setBorder(null);
			panel_2.add(title_TField);
		title_TField.setColumns(10);
		
		author_TField = new JTextField();
		author_TField.setEditable(false);
		author_TField.setBackground(new Color(59, 59, 59));
		author_TField.setForeground(Color.WHITE);
		author_TField.setBounds(22, 307, 140, 20);
		author_TField.setBorder(null);
			panel_2.add(author_TField);
		author_TField.setColumns(10);
		
		pages_TField = new JTextField();
		pages_TField.setEditable(false);
		pages_TField.setBackground(new Color(59, 59, 59));
		pages_TField.setForeground(Color.WHITE);
		pages_TField.setBounds(22, 377, 74, 20);
		pages_TField.setBorder(null);
			panel_2.add(pages_TField);
		pages_TField.setColumns(10);
		
		timesRead_TField = new JTextField();
		timesRead_TField.setBackground(new Color(59, 59, 59));
		timesRead_TField.setForeground(Color.WHITE);
		timesRead_TField.setBounds(22, 517, 46, 20);
		timesRead_TField.setBorder(null);
			panel_2.add(timesRead_TField);
		timesRead_TField.setColumns(10);

		id_TField.setText(id);
		
		title_TField.setText(title);
		title_TField.setCaretPosition(0);
		
		author_TField.setText(author);
		author_TField.setCaretPosition(0);
		
		pages_TField.setText(pages);
		
		timesRead_TField.setText(timesRead);
		timesRead_TField.setCaretPosition(0);
		
		JLabel tags_lbl = new JLabel("tags");
		tags_lbl.setForeground(Color.WHITE);
		tags_lbl.setBounds(22, 641, 46, 14);
			panel_2.add(tags_lbl);
		
		String[] tagsArr = methods.splitTagsUp(tags);
		JButton[] tagButtonArr = new JButton[tagsArr.length];
		
		JPanel panel_1 = new JPanel();
		panel_1.setLayout(new GridLayout((tagsArr.length/3) + 1, 3));
		
		for(int i=0;i<tagsArr.length;i++) {
			tagButtonArr[i] = new JButton(tagsArr[i], new ImageIcon(moreInformationPanel.class.getResource("/grafics/tagButton.png")));
			tagButtonArr[i].setForeground(Color.WHITE);
			tagButtonArr[i].setFont(new Font("Tahoma", Font.PLAIN, 15));
			tagButtonArr[i].setHorizontalTextPosition(SwingConstants.CENTER);
			tagButtonArr[i].setPreferredSize(new Dimension(135, 25));
			 
			panel_1.add(tagButtonArr[i]);
		}
		JPanel container = new JPanel(new FlowLayout(FlowLayout.CENTER, 0,0));
		container.setForeground(Color.WHITE);
		container.setBackground(new Color(35, 35, 35));
		container.setBorder(new MatteBorder(1, 1, 1, 1, new Color(35, 35, 35)));
        container.add(panel_1);
        
        JScrollPane tagsBody_SPane = new JScrollPane(container, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        if(SFW == true) {
        	tagsBody_SPane.setVisible(false);
        }
        
        JScrollBar scrollBar = tagsBody_SPane.getVerticalScrollBar();
        Dimension scrollBarDim = new Dimension(15, scrollBar
              .getPreferredSize().height);
        scrollBar.setPreferredSize(scrollBarDim);
        scrollBar.setUI(new BasicScrollBarUI() {
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
             return methods.createZeroButton();
         }

         @Override
         protected JButton createIncreaseButton(int orientation) {
             return methods.createZeroButton();
         }
        });
        tagsBody_SPane.setBounds(22, 667, 428, 80);
        	panel_2.add(tagsBody_SPane);
        
        
        rating_CBox = new JComboBox();
        rating_CBox.addPopupMenuListener(new PopupMenuListener() {
        	public void popupMenuCanceled(PopupMenuEvent e) {
        	}
        	public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
        	}
        	public void popupMenuWillBecomeVisible(PopupMenuEvent e)
     	   {
        		RenderMethods.handlePopupMenuWillBecomeVisible(e);
     	   }
        });
        rating_CBox.setUI(new BasicComboBoxUI(){
        	   @Override
        	   protected JButton createArrowButton() {
        	       JButton arrowButton = new BasicArrowButton( 
        	        BasicArrowButton.SOUTH,
        	        new Color(40, 40, 40), 
        	        new Color(59, 59, 59),
        	        new Color(114, 118, 125),
        	        new Color(59, 59, 59));
        	       arrowButton.setBorder(BorderFactory.createLineBorder(new Color(59, 59, 59)));
        	       return arrowButton;
        	    }});
        rating_CBox.setRenderer(new renderEngine.colorComboBoxPopUp());
        rating_CBox.setBackground(new Color(59, 59, 59));
        rating_CBox.setForeground(Color.WHITE);
        rating_CBox.setModel(new DefaultComboBoxModel(new String[] {"N/A", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"}));
        rating_CBox.setSelectedItem(rating);
        rating_CBox.setBounds(20, 447, 59, 24);
        	panel_2.add(rating_CBox);
        
        status_CBox = new JComboBox();
        status_CBox.addPopupMenuListener(new PopupMenuListener() {
        	public void popupMenuCanceled(PopupMenuEvent e) {
        	}
        	public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
        	}
        	public void popupMenuWillBecomeVisible(PopupMenuEvent e)
     	   {
        		RenderMethods.handlePopupMenuWillBecomeVisible(e);
     	   }
        });
        status_CBox.setUI(new BasicComboBoxUI(){
        	   @Override
        	   protected JButton createArrowButton() {
        	       JButton arrowButton = new BasicArrowButton( 
        	        BasicArrowButton.SOUTH,
        	        new Color(40, 40, 40), 
        	        new Color(59, 59, 59),
        	        new Color(114, 118, 125),
        	        new Color(59, 59, 59));
        	       arrowButton.setBorder(BorderFactory.createLineBorder(new Color(59, 59, 59)));
        	       return arrowButton;
        	    }});
        status_CBox.setRenderer(new renderEngine.colorComboBoxPopUp());
        status_CBox.setBackground(new Color(59, 59, 59));
        status_CBox.setForeground(Color.WHITE);
        status_CBox.setModel(new DefaultComboBoxModel(new String[] {"plan to read", "reading", "completed"}));
        status_CBox.setSelectedItem(status);
        status_CBox.setBounds(22, 587, 97, 24);
        	panel_2.add(status_CBox);
        
        JButton deleteEntry_btn = new JButton();
        deleteEntry_btn.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		UIManager.put("OptionPane.minimumSize", new Dimension(200, 100));
        		confirmDeleteEntry confirm = new confirmDeleteEntry();
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
		    					System.out.println("Entry gets deleted");
		    					deleteEntry = true;
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
        deleteEntry_btn.setIcon(new ImageIcon(moreInformationPanel.class.getResource("/grafics/inspect/delete.png")));
        deleteEntry_btn.addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent evt) {
				deleteEntry_btn.setIcon(new ImageIcon(moreInformationPanel.class.getResource("/grafics/inspect/deleteHover.png")));
			}

			public void mouseExited(MouseEvent evt) {
				deleteEntry_btn.setIcon(new ImageIcon(moreInformationPanel.class.getResource("/grafics/inspect/delete.png")));
			}

			public void mousePressed(MouseEvent evt) {
				deleteEntry_btn.setIcon(new ImageIcon(moreInformationPanel.class.getResource("/grafics/inspect/deleteSelected.png")));
			}

			public void mouseReleased(MouseEvent evt) {
				deleteEntry_btn.setIcon(new ImageIcon(moreInformationPanel.class.getResource("/grafics/inspect/deleteHover.png")));
			}
		});
        deleteEntry_btn.setBounds(353, 587, 97, 23);
        	panel_2.add(deleteEntry_btn);
        

        if(scroll == true) {
	        JScrollPane scrollPane = new JScrollPane(panel_2, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			scrollPane.setBounds(0, 0, 466, 500);
			scrollPane.setBackground(new Color(35, 35, 35));
			scrollPane.setVisible(true);
			add(scrollPane);
			
			JScrollBar scrollBar2 = scrollPane.getVerticalScrollBar();
	        Dimension scrollBarDim2 = new Dimension(15, scrollBar2
	              .getPreferredSize().height);
	        scrollBar2.setPreferredSize(scrollBarDim2);
	        scrollBar2.setUI(new BasicScrollBarUI() {
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
	             return methods.createZeroButton();
	         }

	         @Override
	         protected JButton createIncreaseButton(int orientation) {
	             return methods.createZeroButton();
	         }
	        });
        }else
        	add(panel_2);
	}
	
	/**
	 * Get the Rating
	 * 
	 * @return String
	 */
	public String getRating() {
		return (String) rating_CBox.getSelectedItem();
	}

	/**
	 * Get the Status
	 * 
	 * @return String
	 */
	public String getStatus() {
		return (String) status_CBox.getSelectedItem();
	}

	/**
	 * Get the times read
	 * 
	 * @return String
	 */
	public String getTimesRead() {
		return timesRead_TField.getText();
	}

	/**
	 * Get if the Entry gets deleted
	 * 
	 * @return boolean
	 */
	public boolean getDeleteEntry() {
		return deleteEntry;
	}   
}
