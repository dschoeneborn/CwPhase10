package de.fh_dortmund.inf.cw.phaseten.server.beans.ai.raiting.phase_element;

import java.util.ArrayList;
import java.util.Collection;

import de.fh_dortmund.inf.cw.phaseten.server.beans.ai.SimPile;

/**
 * @author Robin Harbecke
 *
 */
public class CombinedPhaseElement extends PhaseElement{
	private PhaseElement phaseElement1;
	private PhaseElement phaseElement2;
	
	public CombinedPhaseElement(PhaseElement phaseElement1,PhaseElement phaseElement2) {
		super();
		this.phaseElement1 = phaseElement1;
		this.phaseElement2 = phaseElement2;
	}
	
	
	@Override
	public MissingResult getMissingCards(SimPile pile) {
		MissingResult resultA = getMissingResult(pile, this.phaseElement1,this.phaseElement2);
		MissingResult resultB = getMissingResult(pile, this.phaseElement2,this.phaseElement1);
		if(resultA.missingCards < resultB.missingCards) {
			return resultA;
		}else {
			return resultB;
		}
	}
	
	private static MissingResult getMissingResult(SimPile pile,PhaseElement phaseElementA,PhaseElement phaseElementB) {
		MissingResult missingResultA = phaseElementA.getMissingCards(pile); 
		MissingResult missingResultB = phaseElementA.getMissingCards(missingResultA.getRemainingPile());
		if(missingResultA instanceof FoundPhaseResult && missingResultB instanceof FoundPhaseResult) {
			Collection<SimPileAndPileElement> resultCollection = new ArrayList<>();
			resultCollection.addAll(((FoundPhaseResult)missingResultA).getFoundPiles());
			resultCollection.addAll(((FoundPhaseResult)missingResultA).getFoundPiles());
			return new FoundPhaseResult(missingResultB.remainingPile,resultCollection);
		}
		return new MissingResult(missingResultA.missingCards+missingResultB.missingCards, missingResultB.remainingPile);
	}		
}
