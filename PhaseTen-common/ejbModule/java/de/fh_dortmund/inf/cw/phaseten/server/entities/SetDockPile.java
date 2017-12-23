/**
 * 
 */
package de.fh_dortmund.inf.cw.phaseten.server.entities;

import java.util.LinkedList;

/**
 * @author Dennis Schöneborn
 * @author Marc Mettke
 * @author Björn Merschmeier
 */
public class SetDockPile extends DockPile {
	private static final long serialVersionUID = -5890944285337742574L;
	
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
	public boolean addCard(Card card) {
		if (card.getCardValue().equals(this.cardValue)) {
			this.cards.add(card);
			return true;
		}

		return false;
	}

	public CardValue getCardValue()
	{
		return cardValue;
	}
}
