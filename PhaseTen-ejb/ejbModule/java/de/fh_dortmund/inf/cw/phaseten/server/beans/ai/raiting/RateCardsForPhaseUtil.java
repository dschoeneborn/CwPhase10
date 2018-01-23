package de.fh_dortmund.inf.cw.phaseten.server.beans.ai.raiting;

import de.fh_dortmund.inf.cw.phaseten.server.beans.ai.SimPile;
import de.fh_dortmund.inf.cw.phaseten.server.beans.ai.raiting.phase_element.CardColorCount;
import de.fh_dortmund.inf.cw.phaseten.server.beans.ai.raiting.phase_element.CardRow;
import de.fh_dortmund.inf.cw.phaseten.server.beans.ai.raiting.phase_element.CombinedPhaseElement;
import de.fh_dortmund.inf.cw.phaseten.server.beans.ai.raiting.phase_element.IdenticalCards;
import de.fh_dortmund.inf.cw.phaseten.server.beans.ai.raiting.phase_element.MissingResult;
import de.fh_dortmund.inf.cw.phaseten.server.enumerations.Stage;

/**
 * @author Robin Harbecke
 *
 */
public class RateCardsForPhaseUtil {
	public static double rateCards(SimPile pile,Stage phase){
		return getRemainingCards(pile, phase) * (-100);		
	}
	
	private static int getRemainingCards(SimPile pile,Stage phase) {		
		return getPhaseResult(pile, phase).getMissingCards();
	}
	
	public static MissingResult getPhaseResult(SimPile pile,Stage phase) {
		if(phase == Stage.TWO_TRIPLES) {
			return new CombinedPhaseElement(new IdenticalCards(3), new IdenticalCards(3)).getMissingCards(pile);
		}
		if(phase == Stage.TRIPLE_AND_SEQUENCE_OF_FOUR) {
			return new CombinedPhaseElement(new IdenticalCards(3), new CardRow(4)).getMissingCards(pile);
		}
		if(phase == Stage.QUADRUPLE_AND_SEQUENCE_OF_FOUR) {
			return new CombinedPhaseElement(new IdenticalCards(4), new CardRow(4)).getMissingCards(pile);
		}
		if(phase == Stage.SEQUENCE_OF_SEVEN) {
			return new CardRow(7).getMissingCards(pile);
		}
		if(phase == Stage.SEQUENCE_OF_EIGHT) {
			return new CardRow(8).getMissingCards(pile);
		}
		if(phase == Stage.SEQUENCE_OF_NINE) {
			return new CardRow(9).getMissingCards(pile);
		}
		if(phase == Stage.TWO_QUADRUPLES) {
			return new CombinedPhaseElement(new IdenticalCards(4), new IdenticalCards(4)).getMissingCards(pile);
		}
		if(phase == Stage.SEVEN_OF_ONE_COLOR) {
			return new CardColorCount(7).getMissingCards(pile);
		}
		if(phase == Stage.QUINTUPLE_AND_TWIN) {
			return new CombinedPhaseElement(new IdenticalCards(5), new IdenticalCards(2)).getMissingCards(pile);
		}
		if(phase == Stage.QUINTUPLE_AND_TRIPLE) {
			return new CombinedPhaseElement(new IdenticalCards(5), new IdenticalCards(3)).getMissingCards(pile);
		}
		return null;
	}
}
