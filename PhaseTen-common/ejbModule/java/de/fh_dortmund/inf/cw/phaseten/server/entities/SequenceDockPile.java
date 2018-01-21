/**
 *
 */
package de.fh_dortmund.inf.cw.phaseten.server.entities;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
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
@DiscriminatorValue( value="SDP" )
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
		super();
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
 		boolean addedCard = true;

		if (minimum == null || card.getCardValue().getValue() < minimum.getValue()) {
			if (card.getCardValue() != CardValue.WILD) {
				addedCard = addFirst(card);
				minimum = card.getCardValue();
			} else {
				addedCard = addFirst(card);
				minimum = CardValue.SIX;
			}

			if (maximum == null) {
				maximum = minimum;
			}
		} else if (maximum == null || (card.getCardValue().getValue() > maximum.getValue() &&card.getCardValue() != CardValue.WILD)) {
			addedCard = super.addCard(card);
			maximum = card.getCardValue();
		} else if (minimum != CardValue.ONE && card.getCardValue() == CardValue.WILD) {
			//TODO ACHTUNG: Wildcards beachten. Bei einer 7er Sequence, könnten auch nur 3 und 4 gegeben sein und alles andere mit Wilds aufgefüllt sein. Das wird noch nicht wirklich abgebildet!

			addedCard = addFirst(card);
			minimum = CardValue.getCardValue(minimum.getValue() - 1);
		} else if (maximum != CardValue.TWELVE && card.getCardValue() == CardValue.WILD) {
			//TODO ACHTUNG: Wildcards beachten. Bei einer 7er Sequence, könnten auch nur 3 und 4 gegeben sein und alles andere mit Wilds aufgefüllt sein. Das wird noch nicht wirklich abgebildet!

			addedCard = super.addCard(card);
			maximum = CardValue.getCardValue(maximum.getValue() + 1);
		}

		return addedCard;
	}

	public CardValue getMinimum() {
		return minimum;
	}

	public CardValue getMaximum() {
		return maximum;
	}

	@Override
	public boolean canAddCard(Card card)
	{
		//TODO ACHTUNG: Wildcards beachten. Bei einer 7er Sequence, könnten auch nur 3 und 4 gegeben sein und alles andere mit Wilds aufgefüllt sein. Das wird noch nicht wirklich abgebildet!

		return (minimum == null || card.getCardValue().getValue() < minimum.getValue()
				|| maximum == null || card.getCardValue().getValue() > maximum.getValue()
//				|| (minimum != CardValue.ONE && card.getCardValue() == CardValue.WILD)
//				|| (maximum != CardValue.TWELVE && card.getCardValue() == CardValue.WILD)
				);
	}

}
