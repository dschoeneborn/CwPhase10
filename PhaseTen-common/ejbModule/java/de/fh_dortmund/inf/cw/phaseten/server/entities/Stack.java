/**
 *
 */
package de.fh_dortmund.inf.cw.phaseten.server.entities;

import javax.persistence.MappedSuperclass;

/**
 * @author Dennis Schöneborn
 * @author Marc Mettke
 * @author Daniela Kaiser
 * @author Sebastian Seitz
 */

@MappedSuperclass
public abstract class Stack extends Pile {
	private static final long serialVersionUID = -7799217959895975619L;

	public Card pullTopCard() {
		if (this.isEmpty()) {
			return null;
		}

		String before = this.toString();

		Card temp = getLast();
		removeCard(temp);
		String after = this.toString();
		return temp;
	}
}
