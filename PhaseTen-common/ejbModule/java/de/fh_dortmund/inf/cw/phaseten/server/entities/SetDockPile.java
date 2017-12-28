/**
 * 
 */
package de.fh_dortmund.inf.cw.phaseten.server.entities;

import java.util.ArrayList;
import java.util.LinkedList;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 * @author Dennis Schöneborn
 * @author Marc Mettke
 * @author Daniela Kaiser
 * @author Sebastian Seitz
 */
public class SetDockPile extends DockPile {
	private static final long serialVersionUID = -5890944285337742574L;
	
	@Column(nullable = false)
	@Basic(optional = false)
	@Enumerated(EnumType.ORDINAL)
	private CardValue cardValue;

	/**
	 * 
	 */
	private SetDockPile() {
		this.cards = new ArrayList<>();
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
