package newEntry;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.JButton;

public class newEntry {

	private JFrame frame;
	private JTextField codeInput_TField;
	private JTextField URLInput_TField;

	private boolean URL = false;
	private String URLString;
	private String code;
	private boolean submit = false;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					newEntry window = new newEntry();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public newEntry() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel info = new JLabel("<html><body>insert new entry by entering the  code (ex. 177013),<br>or entring the full URL.</body></html>");
		info.setFont(new Font("Tahoma", Font.PLAIN, 15));
		info.setBounds(10, 11, 414, 36);
		frame.getContentPane().add(info);
		
		codeInput_TField = new JTextField();
		codeInput_TField.setBounds(10, 76, 117, 20);
		frame.getContentPane().add(codeInput_TField);
		codeInput_TField.setColumns(10);
		codeInput_TField.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if(c == KeyEvent.VK_ENTER) {
					URL = false;
					code = codeInput_TField.getText();
				}
			}

			@Override
			public void keyPressed(KeyEvent e) {}
			@Override
			public void keyReleased(KeyEvent e) {}
		});
		
		JLabel code_lbl = new JLabel("code:");
		code_lbl.setFont(new Font("Tahoma", Font.PLAIN, 13));
		code_lbl.setBounds(10, 58, 46, 14);
		frame.getContentPane().add(code_lbl);
		
		URLInput_TField = new JTextField();
		URLInput_TField.setBounds(10, 142, 293, 20);
		frame.getContentPane().add(URLInput_TField);
		URLInput_TField.setColumns(10);
		URLInput_TField.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if(c == KeyEvent.VK_ENTER) {
					URL = true;
					URLString = URLInput_TField.getText();
				}
			}

			@Override
			public void keyPressed(KeyEvent e) {}
			@Override
			public void keyReleased(KeyEvent e) {}
		});
		
		JLabel URL_lbl = new JLabel("URL:");
		URL_lbl.setFont(new Font("Tahoma", Font.PLAIN, 13));
		URL_lbl.setBounds(10, 122, 46, 14);
		frame.getContentPane().add(URL_lbl);
		
		JButton submit_btn = new JButton("submit");
		submit_btn.setBounds(335, 227, 89, 23);
		submit_btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setUnvisible();
			}
		});
		frame.getContentPane().add(submit_btn);
		
		JButton exit_btn = new JButton("exit");
		exit_btn.setBounds(10, 227, 89, 23);
		exit_btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setUnvisible();
				URLInput_TField.setText("");
				codeInput_TField.setText("");
			}
		});
		frame.getContentPane().add(exit_btn);
	}
	
	public void setVisible() {
		frame.setVisible(true);
	}
	
	public void setUnvisible() {
		frame.setVisible(false);
	}
	
	public void clear() {
		URLInput_TField.setText("");
		codeInput_TField.setText("");
	}
	
	public boolean getVisible() {
		return frame.isVisible();
	}
}
