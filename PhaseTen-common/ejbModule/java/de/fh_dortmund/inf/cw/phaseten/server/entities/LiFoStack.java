/**
 * 
 */
package de.fh_dortmund.inf.cw.phaseten.server.entities;

import java.util.ArrayList;
import java.util.LinkedList;

import javax.persistence.Entity;

/**
 * @author Dennis Schöneborn
 * @author Marc Mettke
 * @author Daniela Kaiser
 * @author Sebastian Seitz
 * @author Björn Merschmeier
 */
@Entity
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
	
	public Card showCard()
	{
		return new LinkedList<Card>(this.cards).getFirst();
	}

	@Override
	public boolean addCard(Card card) {
		this.cards.add(card);
		
		return true;
	}
}
