package outsourcedClasses;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.HeadlessException;

import javax.accessibility.AccessibleContext;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.plaf.FileChooserUI;

public class CustomFileChooser extends JFileChooser{
    public CustomFileChooser (){
        super();
        initComponent();
    }

    public CustomFileChooser (String currentDirPath){
        super(currentDirPath);
        initComponent();
    }

    public void initComponent(){
        Component c[] = getComponents();
        customizeUI(c);
    }


public void customizeUI(Component[] c){
    for(int i=0;i<c.length;i++){
        if(c[i] instanceof JPanel){
            ((JPanel)c[i]).setBackground(Color.black);
            if(((JPanel)c[i]).getComponentCount() !=0){
                customizeUI(((JPanel)c[i]).getComponents());
            }
        }
        if(c[i] instanceof JTextField){
            System.out.println("JTextField");
            ((JTextField)c[i]).setBackground(Color.RED);
        }
        if(c[i] instanceof JToggleButton){
            System.out.println("JToggleButton");
        }
        if(c[i] instanceof JScrollPane){
            System.out.println("JScrollPane");
        }

    }   
}
    @Override
    protected JDialog createDialog(Component parent) throws HeadlessException {
    FileChooserUI ui = getUI();
    String title = ui.getDialogTitle(this);
    putClientProperty(AccessibleContext.ACCESSIBLE_DESCRIPTION_PROPERTY,
                      title);

    JDialog dialog;

    if (parent instanceof Frame) {
        dialog = new JDialog((Frame)parent, title, true);
    } else {
        dialog = new JDialog((Dialog)parent, title, true);
    }
    dialog.setComponentOrientation(this.getComponentOrientation());

    Container contentPane = dialog.getContentPane();
    contentPane.setLayout(new BorderLayout());
    contentPane.add(this, BorderLayout.CENTER);
    contentPane.setBackground(Color.black);


    dialog.pack();
    dialog.setLocationRelativeTo(parent);

    return dialog;
}}