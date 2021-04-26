package renderEngine;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**
 * 
 * colorComboBoxPopUp is for the color of the JComboBox
 *
 */
public class colorComboBoxPopUp extends JLabel implements ListCellRenderer {

    public colorComboBoxPopUp() {
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
