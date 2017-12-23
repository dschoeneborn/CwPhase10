/**
 * 
 */
package de.fh_dortmund.inf.cw.phaseten.server.entities;

import java.util.LinkedList;

/**
 * @author Dennis Schöneborn
 * @author Marc Mettke
 * @author Björn Merschmeier
 */
public class SequenceDockPile extends DockPile {
	private static final long serialVersionUID = -2641836374110431370L;
	
	private CardValue minimum;
	private CardValue maximum;

	/**
	 * 
	 */
	public SequenceDockPile() {
		this.cards = new LinkedList<>();
	}

	/* (non-Javadoc)
	 * @see de.fh_dortmund.inf.cw.phaseten.server.entities.DockPile#dock(de.fh_dortmund.inf.cw.phaseten.server.entities.Card)
	 */
	/**
	 * @author Björn Merschmeier
	 */
	@Override
	public boolean addCard(Card card)
	{
		boolean addedCard = false;

		if(minimum == null || card.getCardValue().getValue() < minimum.getValue())
		{
			if(card.getCardValue() != CardValue.JOKER)
			{
				minimum = card.getCardValue();
			}
			else
			{
				minimum = CardValue.SIX;
			}
			
			if(maximum == null)
			{
				maximum = minimum;
			}
			
			this.cards.addFirst(card);
			
			addedCard = true;
		}
		else if(maximum == null || card.getCardValue().getValue() > maximum.getValue())
		{
			maximum = card.getCardValue();
			
			this.cards.addLast(card);
			
			addedCard = true;
		}
		else if(minimum != CardValue.ONE && card.getCardValue() == CardValue.JOKER)
		{
			minimum = CardValue.getCardValue(minimum.getValue() - 1);
			
			this.cards.addFirst(card);
			
			addedCard = true;
		}
		else if(maximum != CardValue.TWELVE && card.getCardValue() == CardValue.JOKER)
		{
			maximum = CardValue.getCardValue(maximum.getValue() + 1);
			
			this.cards.addLast(card);
			
			addedCard = true;
		}
		
		return addedCard;
	}

	public CardValue getMinimum() {
		return minimum;
	}

	public CardValue getMaximum() {
		return maximum;
	}

}
