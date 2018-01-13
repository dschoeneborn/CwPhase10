/**
 * 
 */
package de.fh_dortmund.inf.cw.phaseten.server.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

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

	public boolean addCard(Card card) {
		this.cards.add(0, card);
		return true;
	}

	public void removeCard(Card card) throws NoSuchElementException {
		if(!this.cards.remove(card))
		{
			throw new NoSuchElementException();
		}
	}
}
