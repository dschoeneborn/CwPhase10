package de.fh_dortmund.inf.cw.phaseten.server.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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

	private Color color;
	private CardValue cardValue;

	/**
	 * @param color
	 * @param cardValue
	 */
	public Card(Color color, CardValue cardValue) {
		super();
		this.color = color;
		this.cardValue = cardValue;
	}

	public Card() {

        }

	public int getId() {
		return id;
	}

	/**
	 * @return the color
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * @return the cardValue
	 */
	public CardValue getCardValue() {
		return cardValue;
	}

}
