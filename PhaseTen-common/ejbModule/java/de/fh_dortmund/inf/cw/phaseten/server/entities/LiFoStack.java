/**
 * 
 */
package de.fh_dortmund.inf.cw.phaseten.server.entities;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * @author Dennis Schöneborn
 * @author Marc Mettke
 * @author Daniela Kaiser
 */
public class LiFoStack extends Stack {
	private static final long serialVersionUID = -1006154816006779657L;

	public LiFoStack() {
		this.cards = new ArrayList<Card>();
	}

	public void pushCard(Card card) {
		ArrayList<Card> temp = new ArrayList<>();
		temp.addAll(cards);
		for (int i = 0; i < temp.size(); i++) {
			cards.set(i + 1, temp.get(i));
		}
		this.cards.set(0, card);
	}

	public Card showCard() {
		return this.cards.get(0);
	}
}
