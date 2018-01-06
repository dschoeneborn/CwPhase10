package de.fh_dortmund.inf.cw.phaseten.server.beans.ai.raiting.phase_element;

import java.util.Arrays;
import java.util.Collection;

import de.fh_dortmund.inf.cw.phaseten.server.beans.ai.SimPile;

/**
 * @author Robin Harbecke
 *
 */
public class FoundPhaseResult extends MissingResult{
	private Collection<SimPileAndPileElement> foundPiles;
	
	public FoundPhaseResult(SimPile remainingPile, Collection<SimPileAndPileElement> foundPiles) {
		super(0, remainingPile);
		this.foundPiles = foundPiles;
	}
	
	public FoundPhaseResult(SimPile remainingPile, SimPileAndPileElement foundPile) {
		this(remainingPile, Arrays.asList(foundPile));
	}
	
	public Collection<SimPileAndPileElement> getFoundPiles() {
		return foundPiles;
	}	
}
