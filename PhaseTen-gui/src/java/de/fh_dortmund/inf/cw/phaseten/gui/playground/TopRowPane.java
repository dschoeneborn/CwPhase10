package de.fh_dortmund.inf.cw.phaseten.gui.playground;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

import de.fh_dortmund.inf.cw.phaseten.client.ServiceHandler;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.CardAlreadyTakenInThisTurnException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.NotYourTurnException;

/**
 * @author Robin Harbecke
 *
 */
public class TopRowPane extends JPanel{
	protected CardPile drawPile = new CardPile();
	protected CardPile discardPile = new CardPile();
	
	protected PhaseCard phaseCard = new PhaseCard();
	protected UserList userList = new UserList();
	
	protected ServiceHandler serviceHandler;
	
	public TopRowPane(ServiceHandler serviceHandler) {
		this.serviceHandler = serviceHandler;
		this.setLayout(new BoxLayout(this,BoxLayout.X_AXIS));
		this.add(topJustify(this.drawPile));	
		this.add(Box.createHorizontalStrut(10));
		this.add(topJustify(this.discardPile));
		this.add(Box.createHorizontalGlue());
		this.add(topJustify(this.phaseCard));		
		this.add(this.userList);
		this.setupListeners();
	}
	
	private void setupListeners() {
		this.drawPile.addMouseListener(new MouseAdapter() {			
			public void mouseClicked(MouseEvent e) {
				try {
					TopRowPane.this.serviceHandler.takeCardFromDrawPile();
				} catch (NotYourTurnException e1) {
				} catch (CardAlreadyTakenInThisTurnException e1) {
				}
			} 
		});
		this.discardPile.addMouseListener(new MouseAdapter() {			
			public void mouseClicked(MouseEvent e) {
				try {
					TopRowPane.this.serviceHandler.takeCardFromDiscardPile();
				} catch (NotYourTurnException e1) {
				} catch (CardAlreadyTakenInThisTurnException e1) {
				}
			} 
		});
	}
	
	public void updateData() {		
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
