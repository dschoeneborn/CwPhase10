package de.fh_dortmund.inf.cw.phaseten.gui;

import de.fh_dortmund.inf.cw.phaseten.client.ServiceHandler;
import de.fh_dortmund.inf.cw.phaseten.gui.playground.PlaygroundWindow;

/**
 * @author Robin Harbecke
 *
 */
public class GuiManager {
	protected GuiFrame currentGuiFrame; 
	protected ServiceHandler serviceHandler;

	public GuiManager(ServiceHandler serviceHandler) {
		this.serviceHandler = serviceHandler;
		this.showPlaygoundGui();
		this.updateData();
	}
	
	protected void closeCurrentGui() {
		if(this.currentGuiFrame != null)currentGuiFrame.setVisible(false);
		this.currentGuiFrame = null;
	}
	
	public void showPlaygoundGui() {
		this.closeCurrentGui();
		this.currentGuiFrame = new PlaygroundWindow(this.serviceHandler);
	}
	
	
	protected void updateData() {
		if(this.currentGuiFrame != null) {
			this.currentGuiFrame.updateData();
		}
	}
	
	
	
}
