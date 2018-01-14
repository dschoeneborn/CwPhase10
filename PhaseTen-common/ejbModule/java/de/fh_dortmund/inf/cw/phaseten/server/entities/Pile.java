/**
 *
 */
package de.fh_dortmund.inf.cw.phaseten.server.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
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

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
	@JoinTable
	private List<Card> cards;

	public Pile() {
		cards = new ArrayList<>();
	}

	public Collection<Card> getCards() {
		return new ArrayList<Card>(cards);
	}

	public boolean addCard(Card card)
	{
		if(canAddCard(card))
		{
			cards.add(card);
			return true;
		}
		else
		{
			return false;
		}
	}

	public boolean addAll(Collection<Card> notVisibleCards)
	{
		boolean canAddCards = true;
		
		for(Card c : notVisibleCards)
		{
			if(!this.canAddCard(c))
			{
				canAddCards = false;
			}
		}
		
		if(canAddCards)
		{
			cards.addAll(notVisibleCards);
		}
		
		return canAddCards;
	}

	public boolean addFirst(Card card)
	{
		if(canAddCard(card))
		{
			LinkedList<Card> newCards = new LinkedList<>(cards);
			newCards.addFirst(card);
			cards = newCards;
			
			return true;
		}
		else
		{
			return false;
		}
	}

	public boolean isEmpty()
	{
		return cards.isEmpty();
	}

	public Card getCard(int i)
	{
		return cards.get(i);
	}
	
	public void setCard(int i, Card c)
	{
		cards.set(i, c);
	}

	public void removeCard(int i)
	{
		cards.remove(i);
	}
	
	public void removeCard(Card c)
	{
		cards.remove(c);
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

	/**
	 * @author Robin Harbecke
	 * @param card
	 */
	public abstract boolean canAddCard(Card card);
}
