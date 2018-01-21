/**
 *
 */
package de.fh_dortmund.inf.cw.phaseten.server.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author Dennis Schöneborn
 * @author Marc Mettke
 * @author Daniela Kaiser
 * @author Sebastian Seitz
 * @author Björn Merschmeier
 */
@Entity
public class PlayerPile extends Pile {
	private static final long serialVersionUID = -3026757589448841719L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	public PlayerPile() {
		super();
	}

	/**
	 * @author Björn Merschmeier
	 */
	@Override
	public boolean canAddLastCard(Card card) {
		return true;
	}

	/**
	 * @author Björn Merschmeier
	 */
	@Override
	public boolean canAddFirstCard(Card card) {
		return true;
	}
}
