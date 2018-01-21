package de.fh_dortmund.inf.cw.phaseten.gui.playground.player.lay_phase;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.util.Collection;

import javax.swing.BorderFactory;

import de.fh_dortmund.inf.cw.phaseten.client.ServiceHandler;
import de.fh_dortmund.inf.cw.phaseten.gui.playground.card.CardPane;
import de.fh_dortmund.inf.cw.phaseten.gui.playground.card.PilePane;
import de.fh_dortmund.inf.cw.phaseten.gui.playground.card.drag_drop.DragableCardPane;
import de.fh_dortmund.inf.cw.phaseten.gui.playground.player.PlayerCardsPane;
import de.fh_dortmund.inf.cw.phaseten.server.entities.Card;
import de.fh_dortmund.inf.cw.phaseten.server.entities.DockPile;

/**
 * @author Robin Harbecke
 *
 */
public class TemporaryDockPilePane extends PilePane{
	private static final long serialVersionUID = 1783687628149961426L;

	protected PlayerCardsPane playerCardsPane;
	protected DockPile dockPile;
	protected ServiceHandler serviceHandler;

	public TemporaryDockPilePane(ServiceHandler serviceHandler,PlayerCardsPane playerCardsPane, Class<? extends DockPile> dockPileType) {
		this.serviceHandler = serviceHandler;
		this.playerCardsPane = playerCardsPane;
		this.setPreferredSize(new Dimension(CardPane.cardSize.width*5+50, CardPane.cardSize.height+30));
		this.setMinimumSize(this.getPreferredSize());
		this.setMaximumSize(this.getPreferredSize());
		this.setBorder(BorderFactory.createLineBorder(Color.black, 2));

		try {
			this.dockPile = dockPileType.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			throw new RuntimeException("Error in programming, this should not happen");
		}
		this.updateData();
	}

	@Override
	public void handleCardDrop(Card card, Point point) {
		if(point.getX() < this.getWidth()/2)
		{
			this.dockPile.addFirst(card);
		}
		else
		{
			this.dockPile.addLast(card);
		}
		this.playerCardsPane.updateData();
	}

	public DockPile getDockPile() {
		return this.dockPile;
	}

	@Override
	protected Collection<Card> getCards() {
		return this.dockPile.getCopyOfCardsList();
	}

	@Override
	protected CardPane getCardPane(Card card) {
		return new DragableCardPane(card);
	}
}
