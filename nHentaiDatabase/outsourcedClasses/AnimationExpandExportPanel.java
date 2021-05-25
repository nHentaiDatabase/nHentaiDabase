package outsourcedClasses;

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
			animatedPanel.revalidate();
			animatedPanel.repaint();
			animatedPanel.getParent().revalidate();
			animatedPanel.getParent().repaint();
			end = System.nanoTime();
			long delta = ((int)end - (int)start) + 1;
			Thread.sleep(0, (int)(1000000000 / delta));
		}
		
		if(contract == true) {
			animatedPanel.setVisible(false);
		}
		return null;
	}
	
}