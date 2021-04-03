package moreInformation;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;

public class openWebsite extends JPanel {

	/**
	 * Create the panel.
	 */
	public openWebsite() {
		setLayout(null);
		JLabel info_lbl = new JLabel("<html><body>do you want to open the link in Browser (not inconito),<br>or copy the link?</body></html>");
		info_lbl.setFont(new Font("Tahoma", Font.PLAIN, 15));
		info_lbl.setBounds(10, 11, 430, 35);
		add(info_lbl);

	}
}
