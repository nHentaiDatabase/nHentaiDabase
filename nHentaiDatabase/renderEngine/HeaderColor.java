package renderEngine;

import java.awt.Color;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * 
 * HeaderColor is for the Color of the table header
 *
 */
public class HeaderColor extends DefaultTableCellRenderer{
	public HeaderColor() {
		setOpaque(true);
	}
	public Component getTableCellRendererComponent(JTable myTable, Object value, boolean selected, boolean focused, int row, int column) {
		super.getTableCellRendererComponent(myTable, value, selected, focused, row, column);
		setBackground(new java.awt.Color(43, 43, 43));
		setBorder(BorderFactory.createLineBorder(Color.GRAY));
		return this;
	}
}