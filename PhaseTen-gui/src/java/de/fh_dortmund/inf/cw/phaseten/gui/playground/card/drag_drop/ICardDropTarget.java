package de.fh_dortmund.inf.cw.phaseten.gui.playground.card.drag_drop;

import de.fh_dortmund.inf.cw.phaseten.server.entities.Card;

/**
 * @author Robin Harbecke
 *
 */
public interface ICardDropTarget{
	void handleCardDrop(Card card);	
}
