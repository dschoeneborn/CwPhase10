/**
 * 
 */
package de.fh_dortmund.inf.cw.phaseten.server.entities;

import java.util.List;

/**
 * @author Dennis Schöneborn
 *
 */
public abstract class DockPile extends Pile {
	
	public List<Card> getCards()
	{
		return this.cards;
	}
	
	public abstract boolean dock(Card card);
}
