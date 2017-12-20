/**
 * 
 */
package de.fh_dortmund.inf.cw.phaseten.server.entities;

import java.util.LinkedList;

/**
 * @author Dennis Schöneborn
 * @author Marc Mettke
 */
public class LiFoStack extends Stack {
	private static final long serialVersionUID = -1006154816006779657L;

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
