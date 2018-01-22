package de.fh_dortmund.inf.cw.phaseten.server.entities;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import de.fh_dortmund.inf.cw.phaseten.server.enumerations.CardValue;
import de.fh_dortmund.inf.cw.phaseten.server.enumerations.Color;

/**
 * Card Entity.
 *
 * @author Dennis Schöneborn
 * @author Daniela
 * @author Marc Mettke
 */
@Entity
public class Card implements Serializable {
	private static final long serialVersionUID = -1900999599773985719L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public int id;

	@Enumerated(EnumType.ORDINAL)
	@Column(nullable = false)
	@Basic(optional = false)
	private Color color;

	@Column(nullable = false)
	@Basic(optional = false)
	@Enumerated(EnumType.ORDINAL)
	private CardValue cardValue;

	/**
	 * Default Konstruktor.
	 */
	@SuppressWarnings("unused")
	private Card() {

	}

	/**
	 * Konstruktor.
	 * 
	 * @param color
	 * @param cardValue
	 */
	public Card(Color color, CardValue cardValue) {
		super();
		this.color = color;
		this.cardValue = cardValue;
	}

	/**
	 * Konstruktor.
	 * 
	 * @param color
	 * @param cardValue
	 * @param id
	 */
	public Card(Color color, CardValue cardValue, int id) {
		this(color, cardValue);

		this.id = id;
	}

	/**
	 * Liefert id.
	 * 
	 * @return id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Liefert color.
	 * 
	 * @return the color
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * Liefert cardValue.
	 * 
	 * @return the cardValue
	 */
	public CardValue getCardValue() {
		return cardValue;
	}

	/**
	 * Liefert Punktabzug für jeweilige Karte.
	 * 
	 * @return punktabzug
	 */
	public int getRoundEndValue() {
		switch (cardValue) {
		case ONE:
		case TWO:
		case THREE:
		case FOUR:
		case FIVE:
		case SIX:
		case SEVEN:
		case EIGHT:
		case NINE:
			return 5;
		case TEN:
		case ELEVEN:
		case TWELVE:
			return 10;
		case SKIP:
			return 15;
		case WILD:
			return 25;
		default:
			return 5;
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Card other = (Card) obj;
		if (cardValue != other.cardValue)
			return false;
		if (color != other.color)
			return false;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Card [id=" + id + ", color=" + color.name() + ", cardValue=" + cardValue.getWrittenValue() + "]";
	}
}
