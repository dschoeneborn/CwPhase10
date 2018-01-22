package de.fh_dortmund.inf.cw.phaseten.server.beans.ai.raiting.phase_element;

import java.util.Arrays;

import de.fh_dortmund.inf.cw.phaseten.server.beans.ai.SimPile;
import de.fh_dortmund.inf.cw.phaseten.server.entities.Card;
import de.fh_dortmund.inf.cw.phaseten.server.entities.ColorDockPile;
import de.fh_dortmund.inf.cw.phaseten.server.enumerations.CardValue;
import de.fh_dortmund.inf.cw.phaseten.server.enumerations.Color;

/**
 * @author Robin Harbecke
 *
 */
public class CardColorCount extends PhaseElement{
	private int cardCount;
	
	public CardColorCount(int cardCount) {
		super();
		this.cardCount = cardCount;
	}

	@Override
	public MissingResult getMissingCards(SimPile pile) {
		MissingResult bestResult = getMissingCards(pile,Color.RED);
		for (Color color : Arrays.asList(Color.GREEN,Color.BLUE,Color.YELLOW)) {
			MissingResult currentResult = getMissingCards(pile, color);
			if(currentResult.missingCards < bestResult.missingCards) {
				bestResult = currentResult;
			}
		}
		return bestResult;
	}
	
	private MissingResult getMissingCards(SimPile pile,Color cardColor) {
		SimPile foundPile = SimPile.empty;
		SimPile remainingCards = pile;
		int missingCardCount = this.cardCount;		
		for (Card card : pile.getCards()) {				
			if(missingCardCount > 0 && card.getColor() == cardColor) {
				foundPile = foundPile.addCard(card);
				remainingCards = remainingCards.removeCard(card);
				missingCardCount--;				
			}
		}		
		for (Card card : pile.getCards()) {
			if(missingCardCount > 0 && card.getCardValue() == CardValue.WILD) {
				foundPile = foundPile.addCard(card);
				remainingCards = remainingCards.removeCard(card);
				missingCardCount--;
			}
		}
		
		if(missingCardCount > 0) {
			return new MissingResult(missingCardCount, remainingCards);
		}else {
			return new FoundPhaseResult(remainingCards,new SimPileAndPileElement(foundPile, new ColorDockPile(cardColor)));
		}		
	}	
}
