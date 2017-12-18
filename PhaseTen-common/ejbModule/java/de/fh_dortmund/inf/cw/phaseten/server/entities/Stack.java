/**
 * 
 */
package de.fh_dortmund.inf.cw.phaseten.server.entities;

/**
 * @author Dennis Schöneborn
 * @author Marc Mettke
 * @author Daniela Kaiser
 */
public abstract class Stack extends Pile {
	private static final long serialVersionUID = -7799217959895975619L;

	public Card pullTopCard() {
		if (this.cards.isEmpty()) {
			return null;
		}

		Card temp = this.cards.get(0);
		this.cards.remove(0);

		return temp;
	}
}
