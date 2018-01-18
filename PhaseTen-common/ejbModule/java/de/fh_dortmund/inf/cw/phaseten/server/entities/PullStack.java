/**
 *
 */
package de.fh_dortmund.inf.cw.phaseten.server.entities;

import java.util.Collection;
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
		super();
	}

	/**
	 * @author Björn Merschmeier
	 * @author Tim Prange
	 */
	public void initializeCards() {
		for (Color color : Color.values()) {
			if (!color.equals(Color.NONE)) {
				for (CardValue value : CardValue.values()) {
					if (!value.equals(CardValue.SKIP) && !value.equals(CardValue.WILD)) {
						addCard(new Card(color, value));
						addCard(new Card(color, value));
					}
				}

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

	/**
	 * @author Björn Merschmeier
	 */
	public void shuffle() {
		Random random = ThreadLocalRandom.current();

		for (int i = getCopyOfCardsList().size() - 1; i > 0; i--) {
			int index = random.nextInt(i + 1);

			Card a = getCard(index);
			setCard(index, getCard(i));
			setCard(i, a);
		}
	}

	public void addCards(Collection<Card> notVisibleCards) {
		this.addAll(notVisibleCards);
	}

	@Override
	public boolean canAddCard(Card card) {
		return true;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (Card c : this.getCopyOfCardsList()) {
			sb.append(c.toString());
			sb.append(System.lineSeparator());
		}
		return sb.toString();
	}
}
