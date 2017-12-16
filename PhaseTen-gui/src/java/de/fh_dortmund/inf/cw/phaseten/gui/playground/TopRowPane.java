package de.fh_dortmund.inf.cw.phaseten.gui.playground;

import java.awt.Component;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

/**
 * @author Robin Harbecke
 *
 */
public class TopRowPane extends JPanel{
	protected Card takeCardStack = new Card();
	protected Card throwCardStack = new Card();
	
	protected PhaseCard phaseCard = new PhaseCard();
	protected UserList userList = new UserList();
	
	public TopRowPane() {
		this.setLayout(new BoxLayout(this,BoxLayout.X_AXIS));
		this.add(topJustify(this.takeCardStack));	
		this.add(Box.createHorizontalStrut(10));
		this.add(topJustify(this.throwCardStack));
		this.add(Box.createHorizontalGlue());
		this.add(topJustify(this.phaseCard));		
		this.add(this.userList);
	}
	
	public void updateData() {
		this.throwCardStack = new Card();
		this.userList.updateData();
	}
	
	private static Component topJustify( JPanel panel )  {
	    Box  b = Box.createVerticalBox();
	    b.add( panel );
	    b.add( Box.createVerticalGlue() );
	    b.setPreferredSize(panel.getPreferredSize());
	    return b;
	}
}
