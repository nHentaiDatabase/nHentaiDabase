package search;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.Color;

public class searchPanel extends JPanel {
	private JTable table;

	/**
	 * Create the panel.
	 */
	public searchPanel() {
		setLayout(null);
		
		JScrollPane scrollPane_panel3 = new JScrollPane();
		scrollPane_panel3.setBounds(224, 5, 666, 632);
		add(scrollPane_panel3);
		
		table = new JTable() {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		table.setRowSelectionAllowed(false);
		table.setRowHeight(71);
		table.setForeground(Color.WHITE);
		table.setBackground(new Color(54, 57, 63));
		scrollPane_panel3.add(table);

	}

}
