package de.fh_dortmund.inf.cw.phaseten.server.beans.ai.raiting.phase_element;

import de.fh_dortmund.inf.cw.phaseten.server.beans.ai.SimPile;

/**
 * @author Robin Harbecke
 *
 */
public class MissingResult {
	protected int missingCards;
	protected SimPile remainingPile;	
	
	public MissingResult(int missingCards, SimPile remainingPile) {
		super();
		this.missingCards = missingCards;
		this.remainingPile = remainingPile;
	}
	
	public int getMissingCards() {
		return missingCards;
	}
	public SimPile getRemainingPile() {
		return remainingPile;
	}	
}
