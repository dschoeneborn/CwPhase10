package de.fh_dortmund.inf.cw.phaseten.gui.playground.user;

import java.awt.Color;

import javax.swing.JLabel;

import de.fh_dortmund.inf.cw.phaseten.server.messages.Player;

/**
 * @author Robin Harbecke
 * @author Marc Mettke
 */
public class PlayerListEntryGUI extends JLabel implements UserListEntry{
	private static final long serialVersionUID = 670713669164339375L;

	public PlayerListEntryGUI(Player player) {
		this.setForeground(Color.green);
		this.setText(player.getName() + "(Phase " + player.getPhase() + ")");
	}	
}
