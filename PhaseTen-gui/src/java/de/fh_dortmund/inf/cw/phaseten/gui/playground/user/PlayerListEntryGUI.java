package de.fh_dortmund.inf.cw.phaseten.gui.playground.user;

import java.awt.Color;

import javax.swing.JLabel;

import de.fh_dortmund.inf.cw.phaseten.server.messages.PlayerGuiData;

/**
 * @author Robin Harbecke
 * @author Marc Mettke
 */
public class PlayerListEntryGUI extends JLabel implements UserListEntry{
	private static final long serialVersionUID = 670713669164339375L;

	public PlayerListEntryGUI(PlayerGuiData player) {
		this.setForeground(new Color(0, 153, 0));
		this.setText(player.getName() + "(Phase " + player.getPhase() + ")");
	}	
}
