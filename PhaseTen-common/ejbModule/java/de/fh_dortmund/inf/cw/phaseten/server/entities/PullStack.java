/**
 *
 */
package de.fh_dortmund.inf.cw.phaseten.server.entities;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import javax.persistence.Entity;

import de.fh_dortmund.inf.cw.phaseten.server.enumerations.CardValue;
import de.fh_dortmund.inf.cw.phaseten.server.enumerations.Color;

/**
 * PullStack Entity
 * 
 * @author Dennis Schöneborn
 * @author Marc Mettke
 * @author Sebastian Seitz
 * @author Björn Merschmeier
 * @author Tim Prange
 */
@Entity
public class PullStack extends Stack {
	private static final long serialVersionUID = 2117218073864785792L;

	/**
	 * Konstruktor
	 */
	public PullStack() {
		super();
	}

	/**
	 * Initialisierung.
	 * 
	 * @author Björn Merschmeier
	 * @author Tim Prange
	 */
	public void initializeCards() {
		for (Color color : Color.values()) {
			if (!color.equals(Color.NONE)) {
				for (CardValue value : CardValue.values()) {
					if (!value.equals(CardValue.SKIP) && !value.equals(CardValue.WILD)) {
						addLast(new Card(color, value));
						addLast(new Card(color, value));
					}
				}

			}
		}
		addLast(new Card(Color.NONE, CardValue.SKIP));
		addLast(new Card(Color.NONE, CardValue.SKIP));
		addLast(new Card(Color.NONE, CardValue.SKIP));
		addLast(new Card(Color.NONE, CardValue.SKIP));

		addLast(new Card(Color.NONE, CardValue.WILD));
		addLast(new Card(Color.NONE, CardValue.WILD));
		addLast(new Card(Color.NONE, CardValue.WILD));
		addLast(new Card(Color.NONE, CardValue.WILD));
		addLast(new Card(Color.NONE, CardValue.WILD));
		addLast(new Card(Color.NONE, CardValue.WILD));
		addLast(new Card(Color.NONE, CardValue.WILD));
		addLast(new Card(Color.NONE, CardValue.WILD));
	}

	/**
	 * mischt.
	 * 
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

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (Card c : this.getCopyOfCardsList()) {
			sb.append(c.toString());
			sb.append(System.lineSeparator());
		}
		return sb.toString();
	}

	/**
	 * 
	 * Prüft, ob letzte Karte hinzugefügt werden kann.
	 * 
	 * @author Björn Merschmeier
	 */
	@Override
	public boolean canAddLastCard(Card card) {
		return true;
	}

	/**
	 * Prüft, ob erste Karte hinzugefügt werden kann.
	 * 
	 * @author Björn Merschmeier
	 */
	@Override
	public boolean canAddFirstCard(Card card) {
		return true;
	}
}
