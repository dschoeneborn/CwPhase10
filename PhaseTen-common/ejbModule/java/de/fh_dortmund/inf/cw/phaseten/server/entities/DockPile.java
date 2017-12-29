/**
 * 
 */
package de.fh_dortmund.inf.cw.phaseten.server.entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

/**
 * @author Dennis Schöneborn
 * @author Marc Mettke
 * @author Daniela Kaiser
 * @author Sebastian Seitz
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class DockPile extends Pile {
	private static final long serialVersionUID = -4661422639855266071L;

	protected DockPile() {

	}

	public List<Card> getCards() {
		return this.cards;
	}

	public abstract boolean dock(Card card);
}
