package de.fh_dortmund.inf.cw.phaseten.gui.playground.user;

import java.awt.Color;

import javax.swing.JLabel;

/**
 * @author Robin Harbecke
 * @author Marc Mettke
 */
public class Spectator extends JLabel implements User {
	private static final long serialVersionUID = -8061718977257724045L;

	public Spectator() {
		this.setForeground(Color.LIGHT_GRAY);
		this.update();
	}
	
	public void update() {
		this.setText("Spectator");
	}
}
