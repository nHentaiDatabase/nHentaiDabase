package nHentaiWebScaper;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class Error extends JPanel {

	/**
	 * Create the panel.
	 */
	public Error() {

		JLabel info_lbl = new JLabel("<html><h1 style='font-family: Tahoma; font-size: 15pt; color: white'>some error occurred please try again.<br>-check your internet conection<br>-maybe it is nHentai's problem");
		add(info_lbl);
		
	}

}
