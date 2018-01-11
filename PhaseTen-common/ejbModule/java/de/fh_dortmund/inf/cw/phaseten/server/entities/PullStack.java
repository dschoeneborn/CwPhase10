/**
 *
 */
package de.fh_dortmund.inf.cw.phaseten.server.entities;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import javax.persistence.Entity;

/**
 * @author Dennis Schöneborn
 * @author Marc Mettke
 * @author Sebastian Seitz
 * @author Björn Merschmeier
 * @author Tim Prange
 */
@Entity
public class PullStack extends Stack {
	private static final long serialVersionUID = 2117218073864785792L;

	public PullStack() {
	}

	/**
	 * @author Björn Merschmeier
	 */
	public void initializeCards() {
		for (Color color : Color.values()) {
			for (CardValue value : CardValue.values()) {
				if (!value.equals(CardValue.SKIP) && !value.equals(CardValue.WILD)) {
					addCard(new Card(color, value));
					addCard(new Card(color, value));
				}
			}

			addCard(new Card(Color.NONE, CardValue.SKIP));
			addCard(new Card(Color.NONE, CardValue.SKIP));
			addCard(new Card(Color.NONE, CardValue.SKIP));
			addCard(new Card(Color.NONE, CardValue.SKIP));

			addCard(new Card(Color.NONE, CardValue.WILD));
			addCard(new Card(Color.NONE, CardValue.WILD));
			addCard(new Card(Color.NONE, CardValue.WILD));
			addCard(new Card(Color.NONE, CardValue.WILD));
			addCard(new Card(Color.NONE, CardValue.WILD));
			addCard(new Card(Color.NONE, CardValue.WILD));
			addCard(new Card(Color.NONE, CardValue.WILD));
			addCard(new Card(Color.NONE, CardValue.WILD));
		}
	}

	/**
	 * @author Björn Merschmeier
	 */
	public void shuffle() {
		Random random = ThreadLocalRandom.current();

		for (int i = cards.size() - 1; i > 0; i--) {
			int index = random.nextInt(i + 1);

			Card a = cards.get(index);
			cards.set(index, cards.get(i));
			cards.set(i, a);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * de.fh_dortmund.inf.cw.phaseten.server.entities.Pile#addCard(de.fh_dortmund.
	 * inf.cw.phaseten.server.entities.Card)
	 */
	/**
	 * @author Tim Prange
	 */
	@Override
	public boolean addCard(Card card) {
		this.cards.add(card);
		return false;
	}
}
