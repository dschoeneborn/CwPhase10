/**
 * 
 */
package de.fh_dortmund.inf.cw.phaseten.server.entities;

import java.util.LinkedList;

/**
 * @author Dennis Schöneborn
 *
 */
public class SequenceDockPile extends DockPile {

	/**
	 * 
	 */
	public SequenceDockPile() {
		this.cards = new LinkedList<>();
	}

	/* (non-Javadoc)
	 * @see de.fh_dortmund.inf.cw.phaseten.server.entities.DockPile#dock(de.fh_dortmund.inf.cw.phaseten.server.entities.Card)
	 */
	@Override
	public boolean dock(Card card) {
		if(this.cards.contains(card))
		{
			return false;
		}
		
		this.cards.add(card);
		return true;
	}

}
