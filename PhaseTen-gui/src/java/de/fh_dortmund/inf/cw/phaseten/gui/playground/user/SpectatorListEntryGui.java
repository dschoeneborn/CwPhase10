package de.fh_dortmund.inf.cw.phaseten.gui.playground.user;

import java.awt.Color;

import javax.swing.JLabel;

/**
 * @author Robin Harbecke
 * @author Marc Mettke
 */
public class SpectatorListEntryGui extends JLabel implements UserListEntry{
	private static final long serialVersionUID = -8061718977257724045L;

	public SpectatorListEntryGui(String spectator) {
		this.setForeground(new Color(89,89,89));
		this.setText(spectator);		
	}	
}
