package de.fh_dortmund.inf.cw.phaseten.gui;

import javax.swing.JFrame;

import de.fh_dortmund.inf.cw.phaseten.client.ServiceHandler;

/**
 * @author Robin Harbecke
 * @author Marc Mettke
 */
public abstract class GuiFrame extends JFrame implements GuiElement{
	private static final long serialVersionUID = 1668198512083687454L;
	
	protected ServiceHandler serviceHandler;
	
	public GuiFrame(String name,ServiceHandler serviceHandler) {
		super(name);
		this.serviceHandler = serviceHandler;
	}
}
