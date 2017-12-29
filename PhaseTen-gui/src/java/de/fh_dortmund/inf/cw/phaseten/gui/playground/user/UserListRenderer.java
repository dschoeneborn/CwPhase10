package de.fh_dortmund.inf.cw.phaseten.gui.playground.user;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**
 * @author Robin Harbecke
 * @author Marc Mettke
 */
public class UserListRenderer implements ListCellRenderer<User> {
	public Component getListCellRendererComponent(JList<? extends User> list, User value, int index, boolean isSelected,
			boolean cellHasFocus) {
		if (value instanceof Player) {
			return (Player) value;
		}
		if (value instanceof Spectator) {
			return (Spectator) value;
		}
		return new JLabel("Cant display value");
	}
}