/**
 *
 */
package de.fh_dortmund.inf.cw.phaseten.server.entities;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import de.fh_dortmund.inf.cw.phaseten.server.enumerations.CardValue;

/**
 * SetDockPile Entity.
 * 
 * @author Dennis Schöneborn
 * @author Marc Mettke
 * @author Daniela Kaiser
 * @author Sebastian Seitz
 * @author Björn Merschmeier
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorValue(value = "SETDP")
public class SetDockPile extends DockPile {
	private static final long serialVersionUID = -5890944285337742574L;

	@Column(nullable = false)
	@Enumerated(EnumType.ORDINAL)
	private CardValue cardValue;

	/**
	 * Konstruktor.
	 */
	public SetDockPile() {
		super();
	}

	/**
	 * Konstruktor.
	 * 
	 * @param cardValue
	 */
	public SetDockPile(CardValue cardValue) {
		this();
		this.cardValue = cardValue;
	}

	/**
	 * Liefert Card Value.
	 * 
	 * @return
	 */
	public CardValue getCardValue() {
		return cardValue;
	}

	/**
	 * fügt erste Karte hinzu.
	 * 
	 * @author Björn Merschmeier
	 * @param card
	 * @return konnte hinzugefügt werden
	 */
	@Override
	public boolean addFirst(Card c) {
		boolean addedCard = super.addFirst(c);

		if (addedCard && this.cardValue == null && c.getCardValue() != CardValue.WILD) {
			this.cardValue = c.getCardValue();
		}

		return addedCard;
	}

	/**
	 * fügt erste Karte hinzu.
	 * 
	 * @param card
	 * @return konnte hinzugefügt werden
	 * @author Björn Merschmeier
	 */
	@Override
	public boolean addLast(Card c) {
		boolean addedCard = super.addLast(c);

		if (addedCard && this.cardValue == null && c.getCardValue() != CardValue.WILD) {
			this.cardValue = c.getCardValue();
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
		return canAddCard(card);
	}

	/**
	 * 
	 * Prüft, ob erste Karte hinzugefügt werden kann.
	 * 
	 * @author Björn Merschmeier
	 */
	@Override
	public boolean canAddFirstCard(Card card) {
		return canAddCard(card);
	}

	/**
	 * Prüft Karte.
	 * 
	 * @author Björn Merschmeier
	 */
	private boolean canAddCard(Card card) {
		return card.getCardValue() != CardValue.SKIP
				&& ((this.cardValue != null && card.getCardValue() == this.cardValue)
						|| card.getCardValue() == CardValue.WILD || this.cardValue == null);
	}

}
