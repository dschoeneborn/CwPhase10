package de.fh_dortmund.inf.cw.phaseten.server.beans.ai.raiting;

import de.fh_dortmund.inf.cw.phaseten.server.beans.ai.SimPile;
import de.fh_dortmund.inf.cw.phaseten.server.entities.Game;
import de.fh_dortmund.inf.cw.phaseten.server.enumerations.Stage;

/**
 * @author Robin Harbecke
 *
 */
public class RateCardsUtil {
	public static double rateCards(boolean hasLayedPhase,Stage phase,SimPile playerPile,Game game){
		if(hasLayedPhase) {
			return RateCardsRemainingValueUtil.rateCards(playerPile, game);			
		}else {
			return rateCardsForPhase(playerPile, phase);			
		}		
	}
	
	private static double rateCardsForPhase(SimPile playerPile,Stage phase) {
		return  RateCardsForPhaseUtil.rateCards(playerPile, phase);
	}
}
