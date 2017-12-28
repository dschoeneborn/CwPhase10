package de.fh_dortmund.inf.cw.phaseten.gui;

import javax.swing.JFrame;

import de.fh_dortmund.inf.cw.phaseten.client.ServiceHandler;

/**
 * @author Robin Harbecke
 *
 */
public abstract class GuiFrame extends JFrame implements GuiElement{
	public abstract void updateData();
	protected ServiceHandler serviceHandler;
	
	public GuiFrame(String name,ServiceHandler serviceHandler) {
		super(name);
		this.serviceHandler = serviceHandler;
	}
}
