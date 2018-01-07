package de.fh_dortmund.inf.cw.phaseten.server.beans.ai.raiting;

import de.fh_dortmund.inf.cw.phaseten.server.beans.ai.SimPile;
import de.fh_dortmund.inf.cw.phaseten.server.entities.Card;
import de.fh_dortmund.inf.cw.phaseten.server.entities.Game;
import de.fh_dortmund.inf.cw.phaseten.server.entities.Pile;

/**
 * @author Robin Harbecke
 *
 */
public class RateCardsRemainingValueUtil {
	public static double rateCards(SimPile playerPile,Game game){
		int sum = 0;
		for (Card card : playerPile.getCards()) {
			for (Pile pile: game.getOpenPiles()) {
				if(!pile.canAddCard(card)) {
					sum += card.getRoundEndValue();
				}
			}
		}
		return -sum;
	}
}
