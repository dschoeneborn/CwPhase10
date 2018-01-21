/**
 *
 */
package de.fh_dortmund.inf.cw.phaseten.server.entities;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

/**
 * @author Dennis Schöneborn
 * @author Marc Mettke
 * @author Daniela Kaiser
 * @author Sebastian Seitz
 * @author Björn Merschmeier
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorValue( value="SETDP" )
public class SetDockPile extends DockPile {
	private static final long serialVersionUID = -5890944285337742574L;

	@Column(nullable = false)
	@Enumerated(EnumType.ORDINAL)
	private CardValue cardValue;

	public SetDockPile() {
		super();
	}

	/**
	 * @param cardValue
	 */
	public SetDockPile(CardValue cardValue) {
		this();
		this.cardValue = cardValue;
	}

	public CardValue getCardValue() {
		return cardValue;
	}


	/**
	 * @author Björn Merschmeier
	 */
	@Override
	public boolean addFirst(Card c)
	{
		boolean addedCard = super.addFirst(c);

		if(addedCard && this.cardValue == null)
		{
			this.cardValue = c.getCardValue();
		}

		return addedCard;
	}

	/**
	 * @author Björn Merschmeier
	 */
	@Override
	public boolean addLast(Card c)
	{
		boolean addedCard = super.addLast(c);

		if(addedCard && this.cardValue == null)
		{
			this.cardValue = c.getCardValue();
		}

		return addedCard;
	}


	/**
	 * @author Björn Merschmeier
	 */
	@Override
	public boolean canAddLastCard(Card card)
	{
		return canAddCard(card);
	}


	/**
	 * @author Björn Merschmeier
	 */
	@Override
	public boolean canAddFirstCard(Card card)
	{
		return canAddCard(card);
	}


	/**
	 * @author Björn Merschmeier
	 */
	private boolean canAddCard(Card card)
	{
		return card.getCardValue() != CardValue.SKIP
				&& ((this.cardValue != null && card.getCardValue() == this.cardValue)
						|| card.getCardValue() == CardValue.WILD
						|| this.cardValue == null);
	}

}
