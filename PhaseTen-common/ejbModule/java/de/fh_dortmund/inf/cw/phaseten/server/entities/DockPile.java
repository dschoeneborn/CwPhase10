/**
 * 
 */
package de.fh_dortmund.inf.cw.phaseten.server.entities;

import java.util.List;

/**
 * @author Dennis Schöneborn
 * @author Marc Mettke
 */
public abstract class DockPile extends Pile {
	private static final long serialVersionUID = -4661422639855266071L;

	public List<Card> getCards()
	{
		return this.cards;
	}
}
