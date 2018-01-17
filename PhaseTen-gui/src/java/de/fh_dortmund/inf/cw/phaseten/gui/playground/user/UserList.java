package de.fh_dortmund.inf.cw.phaseten.gui.playground.user;

import java.awt.Dimension;
import java.util.Collection;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import de.fh_dortmund.inf.cw.phaseten.client.ServiceHandler;
import de.fh_dortmund.inf.cw.phaseten.server.messages.PlayerGuiData;

/**
 * @author Robin Harbecke
 */
public class UserList extends JPanel{
	private static final long serialVersionUID = -1790443575912655929L;

	protected JScrollPane listScroller;
	protected ServiceHandler serviceHandler;
	protected JPanel contentPanel = new JPanel();

	public UserList(ServiceHandler serviceHandler) {
		super();
		this.serviceHandler = serviceHandler;
		this.listScroller = new JScrollPane(this.contentPanel);
		this.listScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		this.listScroller.setPreferredSize(new Dimension(250,200));
		this.setPreferredSize(this.listScroller.getPreferredSize());
		this.add(this.listScroller);
		this.contentPanel.setLayout(new BoxLayout(this.contentPanel, BoxLayout.Y_AXIS));
	}


	public void updateData(Collection<PlayerGuiData> players, Collection<String> spectators, PlayerGuiData currentPlayer) {
		this.contentPanel.removeAll();
		for (PlayerGuiData player : players) {
			this.contentPanel.add(new CardDropPlayerListEntryGUI(player, currentPlayer.getId() == player.getId(), this.serviceHandler));
		}
		for (String spectator : spectators) {
			this.contentPanel.add(new SpectatorListEntryGui(spectator));
		}
		this.revalidate();
		this.repaint();
	}
}
