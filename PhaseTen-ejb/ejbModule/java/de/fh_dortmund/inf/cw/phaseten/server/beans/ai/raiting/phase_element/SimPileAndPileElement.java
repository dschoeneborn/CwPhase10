package de.fh_dortmund.inf.cw.phaseten.server.beans.ai.raiting.phase_element;

import de.fh_dortmund.inf.cw.phaseten.server.beans.ai.SimPile;
import de.fh_dortmund.inf.cw.phaseten.server.entities.DockPile;

/**
 * @author Robin Harbecke
 *
 */
public class SimPileAndPileElement {
	private SimPile simPile;
	private DockPile pile;
	
	public SimPileAndPileElement(SimPile simPile, DockPile pile) {
		super();
		this.simPile = simPile;
		this.pile = pile;
	}

	public SimPile getSimPile() {
		return this.simPile;
	}

	public DockPile getPile() {
		return this.pile;
	}
}
