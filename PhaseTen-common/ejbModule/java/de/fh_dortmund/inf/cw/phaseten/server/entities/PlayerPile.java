/**
 * 
 */
package de.fh_dortmund.inf.cw.phaseten.server.entities;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Dennis Schöneborn
 *
 */
public class PlayerPile extends Pile {
	public PlayerPile() {
		this.cards = new LinkedList<Card>();
	}

	public List<Card> getCards() {
		return this.cards;
	}

	public void addCard(Card card) {
		this.cards.addFirst(card);
	}

	public void deleteCard(Card card) {
		this.cards.remove(card);
	}
}
