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
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;

/**
 * @author Dennis Schöneborn
 * @author Marc Mettke
 * @author Daniela Kaiser
 * @author Sebastian Seitz
 * @author Björn Merschmeier
 * @author Robin Harbecke
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "PILE_TYPE")
public abstract class Pile implements Serializable {
	private static final long serialVersionUID = 8249906555853503950L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
	@JoinColumn(name="PILE_ID")
	@OrderColumn
	private List<Card> cards;

	public Pile() {
		cards = new ArrayList<>();
	}

	public Collection<Card> getCopyOfCardsList() {
		return new ArrayList<>(cards);
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

	public void clearCards() {
		this.cards.clear();
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

	public boolean removeCard(Card c)
	{
		return cards.remove(c);
	}

	public boolean containsCard(Card card)
	{
		return this.cards.contains(card);
	}

	/**
	 * Returns the id of the pile.
	 *
	 * @author Tim Prange
	 * @return id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @author Robin Harbecke
	 * @param card
	 */
	public abstract boolean canAddCard(Card card);

	protected Card getLast()
	{
		LinkedList<Card> newCards = new LinkedList<>(cards);
		return newCards.getLast();
	}
}
