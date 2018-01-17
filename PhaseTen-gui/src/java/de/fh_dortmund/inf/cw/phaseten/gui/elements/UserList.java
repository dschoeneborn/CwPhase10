package de.fh_dortmund.inf.cw.phaseten.gui.elements;

import java.awt.Dimension;
import java.util.Collection;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import de.fh_dortmund.inf.cw.phaseten.gui.playground.user.PlayerListEntryGUI;
import de.fh_dortmund.inf.cw.phaseten.gui.playground.user.SpectatorListEntryGui;
import de.fh_dortmund.inf.cw.phaseten.gui.playground.user.UserListEntry;
import de.fh_dortmund.inf.cw.phaseten.gui.playground.user.UserListRenderer;
import de.fh_dortmund.inf.cw.phaseten.server.messages.PlayerGuiData;

/**
 * @author Robin Harbecke
 * @author Marc Mettke
 */
public class UserList extends JPanel{	
	private static final long serialVersionUID = -7067128962658272765L;
	
	protected DefaultListModel<UserListEntry> listModel = new DefaultListModel<UserListEntry>();
	protected JList<UserListEntry> list;
	protected JScrollPane listScroller;	
	
	public UserList() {
		super();				
		this.list = new JList<UserListEntry>(this.listModel);
		this.list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.list.setCellRenderer(new UserListRenderer());
		this.listScroller = new JScrollPane(this.list);
		this.listScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		this.listScroller.setPreferredSize(new Dimension(250,200));
		this.setPreferredSize(this.listScroller.getPreferredSize());		
		this.add(this.listScroller);		
	}
		
	
	public void updateData(Collection<PlayerGuiData> collection, Collection<String> collection2) {
		this.listModel.removeAllElements();
		for (PlayerGuiData player : collection) {
			this.listModel.addElement(new PlayerListEntryGUI(player));
		}
		for (String spectator : collection2) {
			this.listModel.addElement(new SpectatorListEntryGui(spectator));
		}
		this.revalidate();
		this.repaint();
	}
}
