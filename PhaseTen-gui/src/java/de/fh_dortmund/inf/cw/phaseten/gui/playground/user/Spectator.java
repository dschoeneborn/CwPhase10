package de.fh_dortmund.inf.cw.phaseten.gui.playground.user;

import java.awt.Color;

import javax.swing.JLabel;

/**
 * @author Robin Harbecke
 *
 */
public class Spectator extends JLabel implements User {
	public Spectator() {
		this.setForeground(Color.LIGHT_GRAY);
		this.update();
	}
	
	public void update() {
		this.setText("Spectator");
	}
}
