package de.fh_dortmund.inf.cw.phaseten.gui;

import de.fh_dortmund.inf.cw.phaseten.client.ServiceHandlerImpl;

public class Main {
	public static void main(String[] args) {	
		GuiManager guiManager = new GuiManager(ServiceHandlerImpl.getInstance());
		guiManager.dispose();
	}
}
