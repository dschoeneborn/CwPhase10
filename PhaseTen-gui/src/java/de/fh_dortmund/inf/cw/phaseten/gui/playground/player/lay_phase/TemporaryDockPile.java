package de.fh_dortmund.inf.cw.phaseten.gui.playground.player.lay_phase;

import de.fh_dortmund.inf.cw.phaseten.server.entities.Card;
import de.fh_dortmund.inf.cw.phaseten.server.entities.DockPile;

/**
 * @author Robin Harbecke
 *
 */
public class TemporaryDockPile extends DockPile{	
	private static final long serialVersionUID = -164211083057026174L;

	@Override
	public boolean addCard(Card card) {
		this.cards.add(card);
		return true;
	}
	
	public void clear() {
		this.cards.clear();
	}
	
	public boolean containsCard(Card card) {
		return this.cards.contains(card);
	}
	
	public boolean removeCard(Card card) {
		return this.cards.remove(card);
	}

}
