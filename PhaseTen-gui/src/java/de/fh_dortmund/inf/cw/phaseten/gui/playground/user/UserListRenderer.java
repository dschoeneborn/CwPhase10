package de.fh_dortmund.inf.cw.phaseten.gui.playground.user;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**
 * @author Robin Harbecke
 * @author Marc Mettke
 * @author Björn Merschmeier
 */
public class UserListRenderer implements ListCellRenderer<UserListEntry> {
	@Override
	public Component getListCellRendererComponent(JList<? extends UserListEntry> list, UserListEntry value, int index, boolean isSelected,
			boolean cellHasFocus) {
		if (value instanceof CardDropPlayerListEntryGUI) {
			return (CardDropPlayerListEntryGUI) value;
		}
		if (value instanceof SpectatorListEntryGui) {
			return (SpectatorListEntryGui) value;
		}
		if (value instanceof PlayerListEntryGUI)
		{
			return (PlayerListEntryGUI) value;
		}
		return new JLabel("Cant display value");
	}
}