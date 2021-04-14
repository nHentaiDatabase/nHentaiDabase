package nHentaiWebScaper;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class Error extends JPanel {

	/**
	 * Create the panel.
	 */
	public Error(String id) {

		JLabel info_lbl = new JLabel("<html><h1 style='font-family: Tahoma; font-size: 15pt; color: white'>some error occurred ( id: " +id+ ") please try again.<br>-check your internet conection<br>-maybe it is nHentai's problem");
		add(info_lbl);
		
	}

}
