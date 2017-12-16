package de.fh_dortmund.inf.cw.phaseten.gui.playground.user;

import java.awt.Color;

import javax.swing.JLabel;

/**
 * @author Robin Harbecke
 *
 */
public class Player extends JLabel implements User {
	public Player() {
		this.setForeground(Color.green);
		this.update();
	}
	
	public void update() {
		this.setText("Player" + " ("+1+")");
	}
}
