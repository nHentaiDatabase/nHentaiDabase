package moreInformation;

import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollBar;

import java.awt.CardLayout;
import java.awt.Desktop;
import java.awt.Dimension;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListCellRenderer;

import net.miginfocom.swing.MigLayout;
import newEntry.newEntryGeneral;
import newEntry.newEntryPanelRead;

import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JTextPane;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.event.PopupMenuEvent;
import javax.swing.plaf.basic.BasicArrowButton;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.plaf.metal.MetalScrollBarUI;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
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

	JComboBox status_CBox;
	JComboBox rating_CBox;
	/**
	 * Create the panel.
	 */
	public moreInformationPanel(String id, String title, String author, String pages, String rating, String timesRead, String status, String tags, String pictureLocation) {
		setBackground(new Color(35, 35, 35));
		
		splitTagsUp(tags);
		
		setLayout(null);
		
		JLabel Id_lbl = new JLabel("id");
		Id_lbl.setForeground(Color.WHITE);
		Id_lbl.setBounds(22, 151, 46, 14);
		add(Id_lbl);
		
		JLabel title_lbl = new JLabel("title");
		title_lbl.setForeground(Color.WHITE);
		title_lbl.setBounds(22, 221, 46, 14);
		add(title_lbl);
		
		JLabel author_lbl = new JLabel("author");
		author_lbl.setForeground(Color.WHITE);
		author_lbl.setBounds(22, 291, 46, 14);
		add(author_lbl);
		
		JLabel pages_lbl = new JLabel("pages");
		pages_lbl.setForeground(Color.WHITE);
		pages_lbl.setBounds(22, 361, 46, 14);
		add(pages_lbl);
		
		JLabel rating_lbl = new JLabel("rating");
		rating_lbl.setForeground(Color.WHITE);
		rating_lbl.setBounds(22, 431, 46, 14);
		add(rating_lbl);
		
		JLabel timesRead_lbl = new JLabel("times read");
		timesRead_lbl.setForeground(Color.WHITE);
		timesRead_lbl.setBounds(22, 501, 82, 14);
		add(timesRead_lbl);
		
		JLabel status_lbl = new JLabel("status");
		status_lbl.setForeground(Color.WHITE);
		status_lbl.setBounds(22, 571, 46, 14);
		add(status_lbl);
		
		JLabel titlePicture_lbl = new JLabel("title picture");
		titlePicture_lbl.setForeground(Color.WHITE);
		titlePicture_lbl.setBounds(216, 11, 74, 14);
		add(titlePicture_lbl);
		
		JPanel panel = new JPanel();
		panel.setBounds(300, 11, 150, 212);
		add(panel);
		
		JLabel image_lbl = new JLabel("");
		image_lbl.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				openWebsite open = new openWebsite();
				JOptionPane pane = new JOptionPane(open, JOptionPane.PLAIN_MESSAGE,
						JOptionPane.YES_NO_OPTION);
				
				final JButton openButton = new JButton();
				openButton.setPreferredSize(new Dimension(57,23));
				openButton.setIcon(new ImageIcon(moreInformationPanel.class.getResource("/grafics/openURLButtons/openButton.png")));
				openButton.setHorizontalTextPosition(SwingConstants.CENTER);
				openButton.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						JOptionPane paneAP = getOptionPane((JComponent)e.getSource());
						paneAP.setValue(openButton);
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
						JOptionPane paneAP = getOptionPane((JComponent)e.getSource());
						paneAP.setValue(copyButton);
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
				int result = pane.showOptionDialog(null, open, "settings", 0, JOptionPane.PLAIN_MESSAGE, null,
						buttonText, null);
				if(result == JOptionPane.OK_OPTION) {
					try {
						Desktop.getDesktop().browse(new URI("https://nhentai.net/g/"+ id+ "/"));
					} catch (IOException | URISyntaxException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}else if(result == JOptionPane.NO_OPTION) {
					String copy = "https://nhentai.net/g/"+ id+ "/";
					StringSelection stringSelection = new StringSelection(copy);
					Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
					clipboard.setContents(stringSelection, null);
				}
			}
		});
		image_lbl.setIcon(new ImageIcon(pictureLocation));
		panel.add(image_lbl);
		
		id_TField = new JTextField();
		id_TField.setForeground(Color.WHITE);
		id_TField.setBackground(new Color(59, 59, 59));
		id_TField.setBounds(22, 167, 86, 20);
		id_TField.setBorder(null);
		add(id_TField);
		id_TField.setColumns(10);
		
		title_TField = new JTextField();
		title_TField.setBackground(new Color(59, 59, 59));
		title_TField.setForeground(Color.WHITE);
		title_TField.setBounds(22, 237, 268, 20);
		title_TField.setBorder(null);
		add(title_TField);
		title_TField.setColumns(10);
		
		author_TField = new JTextField();
		author_TField.setBackground(new Color(59, 59, 59));
		author_TField.setForeground(Color.WHITE);
		author_TField.setBounds(22, 307, 140, 20);
		author_TField.setBorder(null);
		add(author_TField);
		author_TField.setColumns(10);
		
		pages_TField = new JTextField();
		pages_TField.setBackground(new Color(59, 59, 59));
		pages_TField.setForeground(Color.WHITE);
		pages_TField.setBounds(22, 377, 74, 20);
		pages_TField.setBorder(null);
		add(pages_TField);
		pages_TField.setColumns(10);
		
		timesRead_TField = new JTextField();
		timesRead_TField.setBackground(new Color(59, 59, 59));
		timesRead_TField.setForeground(Color.WHITE);
		timesRead_TField.setBounds(22, 517, 46, 20);
		timesRead_TField.setBorder(null);
		add(timesRead_TField);
		timesRead_TField.setColumns(10);

		id_TField.setText(id);
		
		title_TField.setText(title);
		
		author_TField.setText(author);
		
		pages_TField.setText(pages);
		
		timesRead_TField.setText(timesRead);
		
		JLabel tags_lbl = new JLabel("tags");
		tags_lbl.setForeground(Color.WHITE);
		tags_lbl.setBounds(22, 641, 46, 14);
		add(tags_lbl);
		
		String[] tagsArr = splitTagsUp(tags);
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
        
        JScrollBar scrollBar = tagsBody_SPane.getVerticalScrollBar();;
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
             return createZeroButton();
         }

         @Override
         protected JButton createIncreaseButton(int orientation) {
             return createZeroButton();
         }
       });
        tagsBody_SPane.setBounds(22, 667, 428, 80);
        add(tagsBody_SPane);
        
        
        rating_CBox = new JComboBox();
        rating_CBox.addPopupMenuListener(new PopupMenuListener() {
        	public void popupMenuCanceled(PopupMenuEvent e) {
        	}
        	public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
        	}
        	public void popupMenuWillBecomeVisible(PopupMenuEvent e)
     	   {
        		handlePopupMenuWillBecomeVisible(e);
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
        rating_CBox.setRenderer(new ColorBox());
        rating_CBox.setBackground(new Color(59, 59, 59));
        rating_CBox.setForeground(Color.WHITE);
        rating_CBox.setModel(new DefaultComboBoxModel(new String[] {"N/A", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"}));
        rating_CBox.setSelectedItem(rating);
        rating_CBox.setBounds(20, 447, 59, 24);
        add(rating_CBox);
        
        status_CBox = new JComboBox();
        status_CBox.addPopupMenuListener(new PopupMenuListener() {
        	public void popupMenuCanceled(PopupMenuEvent e) {
        	}
        	public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
        	}
        	public void popupMenuWillBecomeVisible(PopupMenuEvent e)
     	   {
        		handlePopupMenuWillBecomeVisible(e);
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
        status_CBox.setRenderer(new ColorBox());
        status_CBox.setBackground(new Color(59, 59, 59));
        status_CBox.setForeground(Color.WHITE);
        status_CBox.setModel(new DefaultComboBoxModel(new String[] {"plan to read", "reading", "completed"}));
        status_CBox.setSelectedItem(status);
        status_CBox.setBounds(22, 587, 97, 24);
        add(status_CBox);
	}
	
	public String[] splitTagsUp(String tags) {
		char[] tagsChar = tags.toCharArray();
		int tagNumber = 0;
		for(int i=0;i<tagsChar.length;i++) {
			if(tagsChar[i] == ',') {
				tagNumber++;
			}
		}
		String[] tagsArr = new String[tagNumber];
		int currTag = 0;
		for(int i=0;i<tagsChar.length;i++) {
			if(tagsChar[i] == ',' && currTag+1<tagsArr.length)
				currTag++;
			else {
				if(tagsArr[currTag] == null)
					tagsArr[currTag] = "" + tagsChar[i];
				else
					tagsArr[currTag] = tagsArr[currTag] + tagsChar[i];				
			}
		}
		for(int i=1;i<tagsArr.length;i++) {
			String tmp = tagsArr[i];
			StringBuilder sb = new StringBuilder(tmp);
			sb.deleteCharAt(0);
			tmp = sb.toString();
			tagsArr[i] = tmp;
		}
		return tagsArr;
	}
	
	public String getRating() {
		return (String) rating_CBox.getSelectedItem();
	}
	
	public String getStatus() {
		return (String) status_CBox.getSelectedItem();
	}
	
	public String getTimesRead() {
		return timesRead_TField.getText();
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
	
	class ColorBox extends JLabel implements ListCellRenderer {

        public ColorBox() {
            setOpaque(true);
        }

        @Override
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
			setText(value.toString());
			
			Color background;
			Color foreground;
			
			// check if this cell is selected
			if (isSelected) {
				background = new Color(0, 34, 58);
				foreground = Color.WHITE;
			
			// unselected, and not the DnD drop location
			} else {
				background = new Color(15, 15, 15);
				foreground = Color.WHITE;
			};
			
			setBackground(background);
			setForeground(foreground);
			list.setSelectionBackground(new Color(59, 59, 59));
			
			return this;
		}
    }   

	protected JButton createZeroButton() {
	    JButton button = new JButton("zero button");
	    Dimension zeroDim = new Dimension(0,0);
	    button.setPreferredSize(zeroDim);
	    button.setMinimumSize(zeroDim);
	    button.setMaximumSize(zeroDim);
	    return button;
	}
	
	public void handlePopupMenuWillBecomeVisible(PopupMenuEvent e) {
		JComboBox comboBox = (JComboBox) e.getSource();
	      Object popup = comboBox.getUI().getAccessibleChild(comboBox, 0);
	      Component c = ((Container) popup).getComponent(0);
	      if (c instanceof JScrollPane)
	      {
	         JScrollPane scrollpane = (JScrollPane) c;
	         JScrollBar scrollBar = scrollpane.getVerticalScrollBar();
	         Dimension scrollBarDim = new Dimension(15, scrollBar
	               .getPreferredSize().height);
	         scrollBar.setPreferredSize(scrollBarDim);
	         scrollBar.setUI(new BasicScrollBarUI() {
	            @Override 
	            protected void configureScrollBarColors(){
	                this.thumbColor = new Color(32, 34, 37);
	                this.thumbDarkShadowColor = new Color(32, 34, 37);
	                this.thumbLightShadowColor = new Color(32, 34, 37);
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
	      }
	}
}
