/**
 * 
 */
package de.fh_dortmund.inf.cw.phaseten.server.entities;

import java.util.LinkedList;

/**
 * @author Dennis Schöneborn
 * @author Marc Mettke
 */
public class ColorDockPile extends DockPile {
	private static final long serialVersionUID = -8155115717024180700L;
	
	private Color color;

	private ColorDockPile() {
		this.cards = new LinkedList<>();
	}

	public ColorDockPile(Color color) {
		this();
		this.color = color;
	}

	public Color getColor() {
		return this.color;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.fh_dortmund.inf.cw.phaseten.server.entities.DockPile#dock(de.fh_dortmund.
	 * inf.cw.phaseten.server.entities.Card)
	 */
	@Override
	public boolean dock(Card card) {
		if (card.getColor().equals(this.color)) {
			this.cards.add(card);
			return true;
		}

		return false;
	}

}
