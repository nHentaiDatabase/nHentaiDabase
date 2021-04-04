package renderEngine;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Rectangle;

import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicGraphicsUtils;
import javax.swing.plaf.basic.BasicTabbedPaneUI;

public class CustomTabbedPaneUI extends BasicTabbedPaneUI {

    private int inclTab = 4;
    private int anchoFocoV = inclTab;
    private int anchoFocoH = 4;
    private int anchoCarpetas = 18;
    private Polygon shape;

    public static ComponentUI createUI(JComponent c) {
        return new CustomTabbedPaneUI();
    }

    @Override
    protected void installDefaults() {
        super.installDefaults();
        tabAreaInsets.right = anchoCarpetas;
    }

    @Override
    protected void paintTabArea(Graphics g, int tabPlacement, int selectedIndex) {
        super.paintTabArea(g, tabPlacement, selectedIndex);
    }
    
    @Override
    protected void paintTabBackground(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected) {
        Graphics2D g2D = (Graphics2D) g;
        int xp[] = null;
        int yp[] = null;
        
        xp = new int[] {x, x, x + w, x + w, x};
        yp = new int[] {y, y + h - 1, y + h - 1, y, y};
        shape = new Polygon(xp, yp, xp.length);
        g2D.setColor(new java.awt.Color(32, 34, 37));
        g2D.setPaint(new java.awt.Color(32, 34, 37));
        g2D.fill(shape);
    }

    @Override
    protected void paintText(Graphics g, int tabPlacement, Font font, FontMetrics metrics, int tabIndex, String title, Rectangle textRect, boolean isSelected) {
        super.paintText(g, tabPlacement, font, metrics, tabIndex, title, textRect, isSelected);
        int mnemIndex = tabPane.getDisplayedMnemonicIndexAt(tabIndex);
        g.setColor(Color.WHITE);
        BasicGraphicsUtils.drawStringUnderlineCharAt(g, title, mnemIndex, textRect.x, textRect.y + metrics.getAscent());
    }

    @Override
    protected int calculateTabWidth(int tabPlacement, int tabIndex, FontMetrics metrics) {
        return 20 + inclTab + super.calculateTabWidth(tabPlacement, tabIndex, metrics);
    }

    @Override
    protected int calculateTabHeight(int tabPlacement, int tabIndex, int fontHeight) {
        if (tabPlacement == LEFT || tabPlacement == RIGHT) {
            return super.calculateTabHeight(tabPlacement, tabIndex, fontHeight);
        } else {
            return anchoFocoH + super.calculateTabHeight(tabPlacement, tabIndex, fontHeight);
        }
    }

    @Override
    protected void paintTabBorder(Graphics g, int tabPlacement, int tabIndex, int x, int y, int w, int h, boolean isSelected) {
    	
    }

    @Override
    protected void paintFocusIndicator(Graphics g, int tabPlacement, Rectangle[] rects, int tabIndex, Rectangle iconRect, Rectangle textRect, boolean isSelected) {
        if (tabPane.hasFocus() && isSelected) {
            g.setColor(Color.DARK_GRAY);
            g.drawPolygon(shape);
        }
    }
    
    protected void paintContendBorder(Graphics g, int tabPlacement, int selectedIndex) {
    	super.paintContentBorder(g, tabPlacement, selectedIndex);
    }
}