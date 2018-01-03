/**
 * 
 */
package de.fh_dortmund.inf.cw.phaseten.server.entities;

import java.util.ArrayList;
import java.util.LinkedList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 * @author Dennis Schöneborn
 * @author Marc Mettke
 * @author Daniela Kaiser
 * @author Sebastian Seitz
 * @author Björn Merschmeier
 */

@Entity
public class SequenceDockPile extends DockPile {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8478624573278422943L;

	@Enumerated(EnumType.ORDINAL)
	@Column
	private CardValue minimum;

	@Enumerated(EnumType.ORDINAL)
	@Column
	private CardValue maximum;

	/**
	 * 
	 */
	public SequenceDockPile() {
		this.cards = new ArrayList<>();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.fh_dortmund.inf.cw.phaseten.server.entities.DockPile#dock(de.fh_dortmund.
	 * inf.cw.phaseten.server.entities.Card)
	 */
	/**
	 * @author Björn Merschmeier
	 */
	@Override
	public boolean addCard(Card card) {
		boolean addedCard = false;

		if (minimum == null || card.getCardValue().getValue() < minimum.getValue()) {
			if (card.getCardValue() != CardValue.JOKER) {
				minimum = card.getCardValue();
			} else {
				minimum = CardValue.SIX;
			}

			if (maximum == null) {
				maximum = minimum;
			}

			LinkedList<Card> c = new LinkedList<Card>(this.cards);
			c.addFirst(card);
			this.cards = c;

			addedCard = true;
		} else if (maximum == null || card.getCardValue().getValue() > maximum.getValue()) {
			maximum = card.getCardValue();

			LinkedList<Card> c = new LinkedList<>(this.cards);
			c.addLast(card);
			this.cards = c;

			addedCard = true;
		} else if (minimum != CardValue.ONE && card.getCardValue() == CardValue.JOKER) {
			minimum = CardValue.getCardValue(minimum.getValue() - 1);

			LinkedList<Card> c = new LinkedList<>(this.cards);
			c.addFirst(card);
			this.cards = c;

			addedCard = true;
		} else if (maximum != CardValue.TWELVE && card.getCardValue() == CardValue.JOKER) {
			maximum = CardValue.getCardValue(maximum.getValue() + 1);

			LinkedList<Card> c = new LinkedList<>(this.cards);
			c.addLast(card);
			this.cards = c;

			addedCard = true;
		}

		return addedCard;
	}

	public CardValue getMinimum() {
		return minimum;
	}

	public CardValue getMaximum() {
		return maximum;
	}

}
