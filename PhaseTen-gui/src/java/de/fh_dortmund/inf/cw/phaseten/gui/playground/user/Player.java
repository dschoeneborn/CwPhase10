package de.fh_dortmund.inf.cw.phaseten.gui.playground.user;

import java.awt.Color;

import javax.swing.JLabel;

/**
 * @author Robin Harbecke
 * @author Marc Mettke
 */
public class Player extends JLabel implements User {
	private static final long serialVersionUID = 670713669164339375L;

	public Player() {
		this.setForeground(Color.green);
		this.update();
	}
	
	public void update() {
		this.setText("Player" + " ("+1+")");
	}
}
