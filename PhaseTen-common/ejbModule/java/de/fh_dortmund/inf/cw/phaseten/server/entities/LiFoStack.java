/**
 * 
 */
package de.fh_dortmund.inf.cw.phaseten.server.entities;

import java.util.LinkedList;

/**
 * @author Dennis Schöneborn
 *
 */
public class LiFoStack extends Stack {
	public LiFoStack()
	{
		this.cards = new LinkedList<Card>();
	}
	
	public void pushCard(Card card)
	{
		this.cards.addFirst(card);
	}
	
	public Card showCard()
	{
		return this.cards.getFirst();
	}
}
