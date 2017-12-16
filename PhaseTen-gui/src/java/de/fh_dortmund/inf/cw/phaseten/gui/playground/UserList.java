package de.fh_dortmund.inf.cw.phaseten.gui.playground;

import java.awt.Dimension;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import de.fh_dortmund.inf.cw.phaseten.gui.playground.user.Player;
import de.fh_dortmund.inf.cw.phaseten.gui.playground.user.Spectator;
import de.fh_dortmund.inf.cw.phaseten.gui.playground.user.User;
import de.fh_dortmund.inf.cw.phaseten.gui.playground.user.UserListRenderer;

/**
 * @author Robin Harbecke
 *
 */
public class UserList extends JPanel{	
	protected DefaultListModel<User> listModel = new DefaultListModel<User>();
	protected JList<User> list;
	protected JScrollPane listScroller;
	
	public UserList() {
		super();		
		this.list = new JList<User>(this.listModel);
		this.list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.list.setCellRenderer(new UserListRenderer());
		this.listScroller = new JScrollPane(this.list);
		this.listScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		this.listScroller.setPreferredSize(new Dimension(250,200));
		this.setPreferredSize(this.listScroller.getPreferredSize());		
		this.add(this.listScroller);		
	}
	
	public void updateData() {
		this.listModel.removeAllElements();
		this.listModel.addElement(new Player());
		this.listModel.addElement(new Player());
		this.listModel.addElement(new Player());
		this.listModel.addElement(new Spectator());
	}
}
