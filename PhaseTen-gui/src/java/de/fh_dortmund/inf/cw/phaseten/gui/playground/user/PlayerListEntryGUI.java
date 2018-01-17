package de.fh_dortmund.inf.cw.phaseten.gui.playground.user;

import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import de.fh_dortmund.inf.cw.phaseten.server.messages.PlayerGuiData;

/**
 * @author Robin Harbecke
 * @author Marc Mettke
 */
public class PlayerListEntryGUI extends JLabel implements UserListEntry{	
	private static final long serialVersionUID = 1356010565441425778L;
	
	protected PlayerGuiData player;
	
	public PlayerListEntryGUI(PlayerGuiData player) {		
		this.player = player;
		this.setLayout(new FlowLayout());
		this.setForeground(new Color(0, 153, 0));		
		this.setText(this.player.getName() + "(Phase " + this.player.getPhase() + ")");		
		this.revalidate();
		this.repaint();
	}
}
