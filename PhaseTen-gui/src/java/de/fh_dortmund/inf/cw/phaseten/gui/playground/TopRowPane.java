package de.fh_dortmund.inf.cw.phaseten.gui.playground;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

import de.fh_dortmund.inf.cw.phaseten.client.ServiceHandler;
import de.fh_dortmund.inf.cw.phaseten.gui.elements.UserList;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.CardAlreadyTakenInThisTurnException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.NotYourTurnException;
import de.fh_dortmund.inf.cw.phaseten.server.messages.Game;

/**
 * @author Robin Harbecke
 * @author Marc Mettke
 */
public class TopRowPane extends JPanel{
	private static final long serialVersionUID = -6210706602718026386L;
	
	protected CardPilePane drawPile = new CardPilePane();
	protected CardPilePane discardPile = new CardPilePane();
	
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
	
	
	public void gameDataUpdated(Game game) {
		this.userList.updateData(game.getPlayers(),game.getSpectators());
	}	
	
	private static Component topJustify( JPanel panel )  {
	    Box  b = Box.createVerticalBox();
	    b.add( panel );
	    b.add( Box.createVerticalGlue() );
	    b.setPreferredSize(panel.getPreferredSize());
	    return b;
	}
}
