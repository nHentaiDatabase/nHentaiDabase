package renderEngine;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.event.PopupMenuEvent;
import javax.swing.plaf.basic.BasicScrollBarUI;

import outsourcedClasses.Methods;

public class renderMethods {
	
	private Methods methods = new Methods();
	
	public renderMethods() {
		
	}
	
	/**
	 * The combo box popup handler.
	 * Colors every component right.
	 * 
	 * @param e The parent component
	 */
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
	              return methods.createZeroButton();
	          }

	          @Override
	          protected JButton createIncreaseButton(int orientation) {
	              return methods.createZeroButton();
	          }
	        });
	      }
	} 
}
