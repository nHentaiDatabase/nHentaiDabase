package settings;

import javax.swing.JPanel;
import javax.swing.JLabel;

public class confirmDeleteEverything extends JPanel {

	/**
	 * Create the panel.
	 */
	public confirmDeleteEverything() {
		
		JLabel info_lbl = new JLabel("<html><h1 style='font-family: Tahoma; font-size: 20pt; color: red'>Do you realy want to delete everything?");
		add(info_lbl);

	}

}
