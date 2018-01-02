package de.fh_dortmund.inf.cw.phaseten.gui.playground.user;

import java.awt.Color;

import javax.swing.JLabel;

import de.fh_dortmund.inf.cw.phaseten.server.messages.Spectator;

/**
 * @author Robin Harbecke
 * @author Marc Mettke
 */
public class SpectatorListEntryGui extends JLabel implements UserListEntry{
	private static final long serialVersionUID = -8061718977257724045L;

	public SpectatorListEntryGui(Spectator spectator) {
		this.setForeground(Color.LIGHT_GRAY);
		this.setText(spectator.getName());		
	}	
}
