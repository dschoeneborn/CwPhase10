/**
 * 
 */
package de.fh_dortmund.inf.cw.phaseten.server.entities;

/**
 * @author Dennis Schöneborn
 *
 */
public abstract class Stack extends Pile {
	public Card pullTopCard()
	{
		if(this.cards.isEmpty())
		{
			return null;
		}
		
		Card temp = this.cards.getFirst();
		this.cards.removeFirst();
		
		return temp;
	}
}
