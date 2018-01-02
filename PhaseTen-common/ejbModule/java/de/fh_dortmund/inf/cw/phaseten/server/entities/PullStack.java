/**
 *
 */
package de.fh_dortmund.inf.cw.phaseten.server.entities;

import java.util.concurrent.ThreadLocalRandom;

import javax.persistence.Entity;

/**
 * @author Dennis Schöneborn
 * @author Marc Mettke
 * @author Sebastian Seitz
 */
@Entity
public class PullStack extends Stack {
	private static final long serialVersionUID = 2117218073864785792L;

	public PullStack() {
		for (Color color : Color.values()) {
			for (CardValue value : CardValue.values()) {
				addCard(new Card(color, value));
				if (!value.equals(CardValue.SKIP)) {
					addCard(new Card(color, value));
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * de.fh_dortmund.inf.cw.phaseten.server.entities.Pile#addCard(de.fh_dortmund.
	 * inf.cw.phaseten.server.entities.Card)
	 */
	/**
	 * This Method automatically randomizes the cards when adding
	 *
	 * @author Tim Prange
	 */
	@Override
	public boolean addCard(Card card) {

		// TODO: so machen oder lieber anders?
		int index = ThreadLocalRandom.current().nextInt(0, cards.size() + 1);
		this.cards.add(index, card);
		return false;
	}
}
