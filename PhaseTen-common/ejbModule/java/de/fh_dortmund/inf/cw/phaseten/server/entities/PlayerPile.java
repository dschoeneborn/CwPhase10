/**
 * 
 */
package de.fh_dortmund.inf.cw.phaseten.server.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author Dennis Schöneborn
 * @author Marc Mettke
 * @author Daniela Kaiser
 * @author Sebastian Seitz
 */
@Entity
public class PlayerPile extends Pile {
	private static final long serialVersionUID = -3026757589448841719L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	public PlayerPile() {
		this.cards = new ArrayList<Card>();
	}

	public List<Card> getCards() {
		return this.cards;
	}

	public void addCard(Card card) {
		ArrayList<Card> temp = new ArrayList<>();
		temp.addAll(cards);
		for (int i = 0; i < temp.size(); i++) {
			cards.set(i + 1, temp.get(i));
		}
		this.cards.set(0, card);
	}

	public void deleteCard(Card card) {
		this.cards.remove(card);
	}
}
