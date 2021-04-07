package moreInformation;

import javax.swing.JPanel;
import javax.swing.JLabel;

public class confirmDeleteEntry extends JPanel {

	/**
	 * Create the panel.
	 */
	public confirmDeleteEntry() {
		
		JLabel info_lbl = new JLabel("<html><h1 style='font-family: Tahoma; font-size: 20pt; color: red'>Do you realy want to delete this entry?");
		add(info_lbl);

	}

}
