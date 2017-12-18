/**
 * 
 */
package de.fh_dortmund.inf.cw.phaseten.server.entities;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * @author Dennis Schöneborn
 * @author Marc Mettke
 * @author Daniela Kaiser
 */
public class SequenceDockPile extends DockPile {
	private static final long serialVersionUID = -2641836374110431370L;

	/**
	 * 
	 */
	public SequenceDockPile() {
		this.cards = new ArrayList<>();
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
