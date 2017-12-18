/**
 * 
 */
package de.fh_dortmund.inf.cw.phaseten.server.entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author Dennis Schöneborn
 * @author Marc Mettke
 * @author Daniela Kaiser
 */
public abstract class DockPile extends Pile {
	private static final long serialVersionUID = -4661422639855266071L;

	protected DockPile() {

	}

	public List<Card> getCards() {
		return this.cards;
	}

	public abstract boolean dock(Card card);
}
