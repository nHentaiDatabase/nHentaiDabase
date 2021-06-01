package outsourcedClasses;

import java.awt.Component;

import javax.swing.JPanel;
import javax.swing.SwingWorker;

public class AnimationExpandExportPanel extends SwingWorker<Void, Void> {

	JPanel animatedPanel;
	Methods methods;
	
	public AnimationExpandExportPanel(JPanel animatedPanel) {
		this.animatedPanel = animatedPanel;
		methods = new Methods();
	}
	
	@Override
	protected Void doInBackground() throws Exception {
		long end = 0;
		long start = 0;
		int movement = 2;
		boolean contract = false;
		
		
		if(animatedPanel.isVisible()) {
			movement*= -1;
			contract = true;
		}else {
			animatedPanel.setSize(animatedPanel.getWidth(), 0);
			animatedPanel.setLocation(10, 555);
			animatedPanel.setVisible(true);
		}
		
		for(int i=0;i<49;i++) {
			start = System.nanoTime();
			
			methods.expandPanel(animatedPanel, movement);
			
			rerender(animatedPanel);
			
			end = System.nanoTime();
			long delta = ((int)end - (int)start) + 1;
			Thread.sleep(0, (int)(1000000000 / delta));
		}
		
		if(contract == true) {
			animatedPanel.setVisible(false);
		}
		
		for(int i=0;i<animatedPanel.getParent().getComponentCount();i++) {
			if(animatedPanel.getParent().getComponent(i) != animatedPanel) {
				animatedPanel.getParent().getComponent(i).revalidate();
				animatedPanel.getParent().getComponent(i).repaint();
			}
		}
		
		return null;
	}
	
	public void rerender(JPanel panel) {
		
		//Apply changes to Panel
		panel.revalidate();
		panel.repaint();
		
		for(int i=0;i<panel.getParent().getComponentCount();i++) {
			panel.getParent().getComponent(i).revalidate();
			panel.getParent().getComponent(i).repaint();
		}
	}
	
}