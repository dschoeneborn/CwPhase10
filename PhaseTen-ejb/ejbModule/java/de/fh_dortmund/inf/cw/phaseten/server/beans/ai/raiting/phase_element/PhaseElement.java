package de.fh_dortmund.inf.cw.phaseten.server.beans.ai.raiting.phase_element;

import de.fh_dortmund.inf.cw.phaseten.server.beans.ai.SimPile;

/**
 * @author Robin Harbecke
 *
 */
public abstract class PhaseElement {
	public abstract MissingResult getMissingCards(SimPile pile);
}
