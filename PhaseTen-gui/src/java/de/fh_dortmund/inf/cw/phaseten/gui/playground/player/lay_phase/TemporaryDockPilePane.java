package de.fh_dortmund.inf.cw.phaseten.gui.playground.player.lay_phase;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.BorderFactory;

import de.fh_dortmund.inf.cw.phaseten.client.ServiceHandler;
import de.fh_dortmund.inf.cw.phaseten.gui.playground.card.CardPane;
import de.fh_dortmund.inf.cw.phaseten.gui.playground.card.PilePane;
import de.fh_dortmund.inf.cw.phaseten.gui.playground.card.drag_drop.DragableCardPane;
import de.fh_dortmund.inf.cw.phaseten.gui.playground.player.PlayerCardsPane;
import de.fh_dortmund.inf.cw.phaseten.server.entities.Card;

/**
 * @author Robin Harbecke
 *
 */
public class TemporaryDockPilePane extends PilePane{	
	private static final long serialVersionUID = 1783687628149961426L;	

	protected PlayerCardsPane playerCardsPane;
	protected TemporaryDockPile dockPile = new TemporaryDockPile();
	protected ServiceHandler serviceHandler;
	
	public TemporaryDockPilePane(ServiceHandler serviceHandler,PlayerCardsPane playerCardsPane) {
		this.serviceHandler = serviceHandler;
		this.playerCardsPane = playerCardsPane;
		this.setPreferredSize(new Dimension(CardPane.cardSize.width*5+50, CardPane.cardSize.height+30));		
		this.setMinimumSize(this.getPreferredSize());
		this.setMaximumSize(this.getPreferredSize());
		this.setBorder(BorderFactory.createLineBorder(Color.black, 2));
		this.updateData();
	}
	
	public void handleCardDrop(Card card) {
		this.dockPile.addCard(card);
		this.playerCardsPane.updateData();
	}
	
	public TemporaryDockPile getDockPile() {		
		return this.dockPile;
	}

	@Override
	protected Iterable<Card> getCards() {
		return this.dockPile.getCards();
	}
	
	@Override
	protected CardPane getCardPane(Card card) {
		return new DragableCardPane(card);
	}
}
