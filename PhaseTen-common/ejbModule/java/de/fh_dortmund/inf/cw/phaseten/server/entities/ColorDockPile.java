/**
 * 
 */
package de.fh_dortmund.inf.cw.phaseten.server.entities;

import java.util.ArrayList;
import java.util.LinkedList;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 * @author Dennis Schöneborn
 * @author Marc Mettke
 * @author Daniela Kaiser
 * @author Sebastian Seitz
 */
@Entity
public class ColorDockPile extends DockPile {
	private static final long serialVersionUID = -8155115717024180700L;
	
	
	@Enumerated(EnumType.ORDINAL)
	@Column(nullable = false)
	@Basic(optional = false)
	private Color color;

	private ColorDockPile() {
		this.cards = new ArrayList<>();
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
