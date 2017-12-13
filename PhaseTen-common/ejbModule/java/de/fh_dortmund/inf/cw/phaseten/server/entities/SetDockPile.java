/**
 * 
 */
package de.fh_dortmund.inf.cw.phaseten.server.entities;

import java.util.LinkedList;

/**
 * @author Dennis Schöneborn
 *
 */
public class SetDockPile extends DockPile {
	private CardValue cardValue;

	/**
	 * 
	 */
	private SetDockPile() {
		this.cards = new LinkedList<>();
	}

	/**
	 * @param cardValue
	 */
	public SetDockPile(CardValue cardValue) {
		this();
		this.cardValue = cardValue;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.fh_dortmund.inf.cw.phaseten.server.entities.DockPile#dock(de.fh_dortmund.
	 * inf.cw.phaseten.server.entities.Card)
	 */
	@Override
	public boolean dock(Card card) {
		if (card.getCardValue().equals(this.cardValue)) {
			this.cards.add(card);
			return true;
		}

		return false;
	}

}
