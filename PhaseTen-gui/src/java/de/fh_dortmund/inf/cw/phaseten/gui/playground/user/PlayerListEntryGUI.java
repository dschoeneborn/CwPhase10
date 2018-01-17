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
public class PlayerListEntryGUI extends JPanel implements UserListEntry{	
	private static final long serialVersionUID = 1356010565441425778L;
	
	protected PlayerGuiData player;
	
	protected JLabel textLabel;
	
	public PlayerListEntryGUI(PlayerGuiData player) {		
		this.player = player;
		this.setLayout(new FlowLayout());
		this.setForeground(new Color(0, 153, 0));
		this.setBackground(new Color(255, 0, 0));
		this.textLabel = new JLabel(this.player.getName() + "(Phase " + this.player.getPhase() + ")");
		this.add(this.textLabel);		
		this.revalidate();
		this.repaint();
	}
}
