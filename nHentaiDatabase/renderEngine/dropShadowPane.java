package renderEngine;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class dropShadowPane extends JPanel {

	JPanel internalPane;
	
    public dropShadowPane(JPanel InternalPane, int top, int left, int bottom, int right) {
        setBorder(new EmptyBorder(top, left, bottom, right));

        setLayout(new BorderLayout());
        internalPane = InternalPane;
        internalPane.setOpaque(false);
        DropShadowBorder border = new DropShadowBorder();
        border.setFillContentArea(true);
        internalPane.setBorder(border);

        add(internalPane);
    }
    
    public dropShadowPane(JPanel InternalPane) {
        setBorder(new EmptyBorder(5, 5, 5, 5));

        setLayout(new BorderLayout());
        internalPane = InternalPane;
        internalPane.setOpaque(false);
        DropShadowBorder border = new DropShadowBorder();
        border.setFillContentArea(true);
        internalPane.setBorder(border);

        add(internalPane);
    }
}
