/**
 *
 */
package de.fh_dortmund.inf.cw.phaseten.server.entities;

import javax.persistence.MappedSuperclass;

/**
 * Stack Entity.
 * 
 * @author Dennis Schöneborn
 * @author Marc Mettke
 * @author Daniela Kaiser
 * @author Sebastian Seitz
 */

@MappedSuperclass
public abstract class Stack extends Pile {
	private static final long serialVersionUID = -7799217959895975619L;

	/**
	 * Zieht oberste Karte.
	 * 
	 * @return card
	 */
	public Card pullTopCard() {
		if (this.isEmpty()) {
			return null;
		}
		Card temp = getLast();
		removeCard(temp);
		return temp;
	}
}
