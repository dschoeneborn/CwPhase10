/**
 * 
 */
package de.fh_dortmund.inf.cw.phaseten.server.entities;

import java.util.LinkedList;

/**
 * @author Dennis Schöneborn
 * @author Marc Mettke
 * @author Björn Merschmeier
 */
public class LiFoStack extends Stack {
	private static final long serialVersionUID = -1006154816006779657L;

	public LiFoStack()
	{
		this.cards = new LinkedList<Card>();
	}
	
	public Card showCard()
	{
		return this.cards.getFirst();
	}

	@Override
	public boolean addCard(Card card) {
		this.cards.add(card);
		
		return true;
	}
}
