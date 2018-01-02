/**
 * 
 */
package de.fh_dortmund.inf.cw.phaseten.server.entities;

import java.util.ArrayList;

import javax.persistence.Entity;

/**
 * @author Dennis Schöneborn
 * @author Marc Mettke
 * @author Daniela Kaiser
 * @author Sebastian Seitz
 */
@Entity
public class LiFoStack extends Stack {
	private static final long serialVersionUID = -1006154816006779657L;

	public LiFoStack() {
		this.cards = new ArrayList<Card>();
	}

	public void pushCard(Card card) {
		this.cards.add(0, card);
	}

	public Card showCard() {
		return this.cards.get(0);
	}
}
