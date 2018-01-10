/**
 *
 */
package de.fh_dortmund.inf.cw.phaseten.server.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;

/**
 * @author Dennis Schöneborn
 * @author Marc Mettke
 * @author Daniela Kaiser
 * @author Sebastian Seitz
 * @author Björn Merschmeier
 * @author Robin Harbecke
 */
@MappedSuperclass
public abstract class Pile implements Serializable {
	private static final long serialVersionUID = 8249906555853503950L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@OneToMany(fetch = FetchType.EAGER)
	@JoinTable
	protected List<Card> cards;

	public Pile() {
		cards = new ArrayList<>();
	}

	/**
	 * @author Björn Merschmeier
	 * @param card
	 */
	public abstract boolean addCard(Card card);

	/**
	 * @author Robin Harbecke
	 * @param card
	 */
	public boolean canAddCard(Card card) {//todo implement
		return false;
	}
	
	/**
	 * @author Björn Merschmeier
	 * @return
	 */
	public int getSize() {
		return cards.size();
	}

	public List<Card> getCards() {
		return cards;
	}

	/**
	 * TODO Add JavaDoc
	 *
	 * @author Tim Prange
	 * @return
	 */
	public long getId() {
		return id;
	}
}
