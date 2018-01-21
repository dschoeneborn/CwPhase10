/**
 *
 */
package de.fh_dortmund.inf.cw.phaseten.server.entities;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 * @author Dennis Schöneborn
 * @author Marc Mettke
 * @author Daniela Kaiser
 * @author Sebastian Seitz
 * @author Björn Merschmeier
 */

@Entity
@DiscriminatorValue( value="SDP" )
public class SequenceDockPile extends DockPile {

	/**
	 *
	 */
	private static final long serialVersionUID = 8478624573278422943L;

	@Enumerated(EnumType.ORDINAL)
	@Column
	private CardValue minimum;

	@Enumerated(EnumType.ORDINAL)
	@Column
	private CardValue maximum;

	/**
	 *
	 */
	public SequenceDockPile() {
		super();
	}

	/**
	 * @author Björn Merschmeier
	 */
	@Override
	public boolean addLast(Card card) {
		boolean addedCard = super.addLast(card);

		if(addedCard && maximum != null)
		{
			maximum = CardValue.getCardValue(maximum.getValue() + 1);
		}
		else if(addedCard && maximum == null)
		{
			maximum = card.getCardValue();
			minimum = maximum;
		}

		return addedCard;
	}

	@Override
	public boolean addFirst(Card card)
	{
		boolean addedCard = super.addFirst(card);

		if(addedCard && minimum != null)
		{
			minimum = CardValue.getCardValue(minimum.getValue() - 1);
		}
		else if(addedCard && minimum == null)
		{
			minimum = card.getCardValue();
			maximum = minimum;
		}

		return addedCard;
	}

	/**
	 * @author Björn Merschmeier
	 */
	@Override
	public boolean canAddLastCard(Card card) {
		return card.getCardValue() != CardValue.SKIP
				&&((maximum == null && card.getCardValue() != CardValue.WILD)
						|| (maximum != null && card.getCardValue().getValue() > maximum.getValue() && maximum != CardValue.TWELVE)
						|| (maximum != CardValue.TWELVE && card.getCardValue() == CardValue.WILD));
	}


	/**
	 * @author Björn Merschmeier
	 */
	@Override
	public boolean canAddFirstCard(Card card) {
		return card.getCardValue() != CardValue.SKIP
				&& ((minimum == null && card.getCardValue() != CardValue.WILD)
						|| (card.getCardValue().getValue() < minimum.getValue() && minimum != CardValue.ONE)
						|| (card.getCardValue() == CardValue.WILD && minimum != CardValue.ONE));
	}
}
