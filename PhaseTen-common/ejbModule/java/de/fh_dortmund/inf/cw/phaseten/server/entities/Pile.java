/**
 * 
 */
package de.fh_dortmund.inf.cw.phaseten.server.entities;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Dennis Schöneborn
 * @author Marc Mettke
 * @author Björn Merschmeier
 */
public abstract class Pile implements Serializable {
	private static final long serialVersionUID = 8249906555853503950L;
	
	protected LinkedList<Card> cards;
	
	public Pile()
	{
		cards = new LinkedList<Card>();
	}
	
	/**
	 * @author Björn Merschmeier
	 * @param card
	 */
	public abstract boolean addCard(Card card);
	
	/**
	 * @author Björn Merschmeier
	 * @return
	 */
	public int getSize()
	{
		return cards.size();
	}
	
	public List<Card> getCards()
	{
		return cards;
	}
}
