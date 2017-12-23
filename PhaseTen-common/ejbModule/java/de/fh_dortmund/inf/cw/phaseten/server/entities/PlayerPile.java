/**
 * 
 */
package de.fh_dortmund.inf.cw.phaseten.server.entities;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Dennis Schöneborn
 * @author Marc Mettke
 */
public class PlayerPile extends Pile {
	private static final long serialVersionUID = -3026757589448841719L;

	public PlayerPile() {
		this.cards = new LinkedList<Card>();
	}

	public List<Card> getCards() {
		return this.cards;
	}

	public boolean addCard(Card card) {
		this.cards.addFirst(card);
		
		return true;
	}

	public void deleteCard(Card card) {
		this.cards.remove(card);
	}
}
