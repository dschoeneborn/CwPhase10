/**
 *
 */
package de.fh_dortmund.inf.cw.phaseten.server.entities;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import de.fh_dortmund.inf.cw.phaseten.server.enumerations.CardValue;
import de.fh_dortmund.inf.cw.phaseten.server.enumerations.Color;

/**
 * ColorDockPile Entity.
 * 
 * @author Dennis Schöneborn
 * @author Marc Mettke
 * @author Daniela Kaiser
 * @author Sebastian Seitz
 * @author Björn Merschmeier
 * @author Robin Harbecke
 */
@Entity
@DiscriminatorValue(value = "CDP")
public class ColorDockPile extends DockPile {
	private static final long serialVersionUID = -8155115717024180700L;

	@Enumerated(EnumType.ORDINAL)
	@Column(nullable = false)
	@Basic(optional = false)
	private Color color;

	/**
	 * Default Konstruktor.
	 */
	public ColorDockPile() {
		super();
	}

	/**
	 * Konstruktor.
	 * 
	 * @param color
	 */
	public ColorDockPile(Color color) {
		this();
		this.color = color;
	}

	/**
	 * Liefert color.
	 * 
	 * @return color
	 */
	public Color getColor() {
		return this.color;
	}

	/**
	 * fügt erste Karte hinzu.
	 * 
	 * @author Björn Merschmeier
	 */
	@Override
	public boolean addFirst(Card c) {
		boolean addedCard = super.addFirst(c);

		setCardColorIfNotSet(c, addedCard);

		return addedCard;
	}

	/**
	 * fügt letzte Karte hinzu.
	 * 
	 * @author Björn Merschmeier
	 */
	@Override
	public boolean addLast(Card c) {
		boolean addedCard = super.addLast(c);

		setCardColorIfNotSet(c, addedCard);

		return addedCard;
	}

	/**
	 * Prüft, ob letzte Karte hinzugefügt werden kann.
	 * 
	 * @author Björn Merschmeier
	 */
	@Override
	public boolean canAddLastCard(Card card) {
		return canAddCard(card);
	}

	/**
	 * Prüft, ob erste Karte hinzugefügt werden kann.
	 * 
	 * @author Björn Merschmeier
	 */
	@Override
	public boolean canAddFirstCard(Card card) {
		return canAddCard(card);
	}

	/**
	 * Setzt Farbe nach Prüfung.
	 * 
	 * @author Björn Merschmeier
	 * @param c
	 * @param addedCard
	 */
	private void setCardColorIfNotSet(Card c, boolean addedCard) {
		if (addedCard && this.color == null && c.getColor() != Color.NONE) {
			this.color = c.getColor();
		}
	}

	/**
	 * prüft Karte.
	 * 
	 * @author Björn Merschmeier
	 * @author Robin Harbecke
	 */
	private boolean canAddCard(Card card) {
		return (this.color == null || card.getColor().equals(this.color) || card.getCardValue() == CardValue.WILD)
				&& card.getCardValue() != CardValue.SKIP;
	}

}
