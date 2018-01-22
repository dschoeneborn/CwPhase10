/**
 *
 */
package de.fh_dortmund.inf.cw.phaseten.server.entities;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import de.fh_dortmund.inf.cw.phaseten.server.enumerations.CardValue;

/**
 * SequenceDockPile Entity.
 * 
 * @author Dennis Schöneborn
 * @author Marc Mettke
 * @author Daniela Kaiser
 * @author Sebastian Seitz
 * @author Björn Merschmeier
 */
@Entity
@DiscriminatorValue(value = "SDP")
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
	 * Konstruktor
	 */
	public SequenceDockPile() {
		super();
	}

	/**
	 * fügt letzte Karte hinzu.
	 * 
	 * @param card
	 * @return konnte hinzugefügt werden
	 */
	@Override
	public boolean addLast(Card card) {
		boolean addedCard = super.addLast(card);

		if (addedCard && maximum != null) {
			maximum = CardValue.getCardValue(maximum.getValue() + 1);
		} else if (addedCard && maximum == null && card.getCardValue() != CardValue.WILD) {
			maximum = card.getCardValue();
			minimum = CardValue.getCardValue(card.getCardValue().getValue() - this.getCopyOfCardsList().size() + 1);
		}

		return addedCard;
	}

	/**
	 * fügt erste Karte hinzu.
	 * 
	 * @param card
	 * @return konnte hinzugefügt werden
	 */
	@Override
	public boolean addFirst(Card card) {
		boolean addedCard = super.addFirst(card);

		if (addedCard && minimum != null) {
			minimum = CardValue.getCardValue(minimum.getValue() - 1);
		} else if (addedCard && minimum == null && card.getCardValue() != CardValue.WILD) {
			minimum = card.getCardValue();
			maximum = CardValue.getCardValue(card.getCardValue().getValue() + this.getCopyOfCardsList().size() - 1);
		}

		return addedCard;
	}

	/**
	 * 
	 * Prüft, ob letzte Karte hinzugefügt werden kann.
	 * 
	 * @author Björn Merschmeier
	 */
	@Override
	public boolean canAddLastCard(Card card) {
		return card.getCardValue() != CardValue.SKIP
				&& ((maximum == null && card.getCardValue() != CardValue.WILD && this.getCopyOfCardsList().size() < 12
						&& card.getCardValue().getValue() - this.getCopyOfCardsList().size() > 0)
						|| (maximum != null && card.getCardValue().getValue() == (maximum.getValue() + 1)
								&& maximum != CardValue.TWELVE && this.getCopyOfCardsList().size() < 12)
						|| (maximum != CardValue.TWELVE && card.getCardValue() == CardValue.WILD
								&& this.getCopyOfCardsList().size() < 12));
	}

	/**
	 * 
	 * Prüft, ob erste Karte hinzugefügt werden kann.
	 * 
	 * @author Björn Merschmeier
	 */
	@Override
	public boolean canAddFirstCard(Card card) {
		return card.getCardValue() != CardValue.SKIP
				&& ((minimum == null && card.getCardValue() != CardValue.WILD && this.getCopyOfCardsList().size() < 12
						&& card.getCardValue().getValue() + this.getCopyOfCardsList().size() <= 12)
						|| (minimum != null && card.getCardValue().getValue() == (minimum.getValue() - 1)
								&& minimum != CardValue.ONE && this.getCopyOfCardsList().size() < 12)
						|| (minimum != CardValue.ONE && card.getCardValue() == CardValue.WILD
								&& this.getCopyOfCardsList().size() < 12));
	}
}
