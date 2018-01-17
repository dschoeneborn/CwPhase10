/**
 *
 */
package de.fh_dortmund.inf.cw.phaseten.server.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Entity;

/**
 * @author Dennis Schöneborn
 * @author Marc Mettke
 * @author Daniela Kaiser
 * @author Sebastian Seitz
 * @author Björn Merschmeier
 */
@Entity
public class LiFoStack extends Stack {
	private static final long serialVersionUID = -1006154816006779657L;

	public LiFoStack()
	{
		super();
	}

	public Card showCard() {
		if (this.getCopyOfCardsList().isEmpty()) {
			return null;
		}
		return new LinkedList<>(this.getCopyOfCardsList()).getLast();
	}

	public Collection<Card> getNotVisibleCards() {
		List<Card> result = new ArrayList<>();

		while(this.getCopyOfCardsList().size() > 2)
		{
			Card currentCard = new LinkedList<>(this.getCopyOfCardsList()).getFirst();
			this.removeCard(currentCard);
			result.add(currentCard);
		}

		return result;
	}

	@Override
	public boolean canAddCard(Card card)
	{
		return true;
	}
}
