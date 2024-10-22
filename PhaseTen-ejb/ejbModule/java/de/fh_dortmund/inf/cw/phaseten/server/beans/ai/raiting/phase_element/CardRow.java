package de.fh_dortmund.inf.cw.phaseten.server.beans.ai.raiting.phase_element;

import de.fh_dortmund.inf.cw.phaseten.server.beans.ai.SimPile;
import de.fh_dortmund.inf.cw.phaseten.server.entities.Card;
import de.fh_dortmund.inf.cw.phaseten.server.entities.SequenceDockPile;
import de.fh_dortmund.inf.cw.phaseten.server.enumerations.CardValue;

/**
 * @author Robin Harbecke
 *
 */
public class CardRow extends PhaseElement{
	private int rowLength;
	
	public CardRow(int rowLength) {
		super();
		this.rowLength = rowLength;
	}

	@Override
	public MissingResult getMissingCards(SimPile pile) {
		MissingResult bestResult = getMissingCards(pile, 1);
		for(int i=2;i<=12-(this.rowLength-1);i++) {
			MissingResult currentResult = getMissingCards(pile, i);
			if(currentResult.missingCards < bestResult.missingCards) {
				bestResult = currentResult;
			}
		}
		return bestResult;
	}
	
	private MissingResult getMissingCards(SimPile pile,int startValue) {
		SimPile foundPile = SimPile.empty;
		SimPile remainingCards = pile;
		int missingCardCount = this.rowLength;
		for(int cardValue = startValue ; cardValue < startValue + this.rowLength;cardValue++) {
			boolean isFound = false;
			for (Card card : pile.getCards()) {
				if(!isFound && card.getCardValue().getValue() == cardValue) {
					foundPile = foundPile.addCard(card);
					remainingCards = remainingCards.removeCard(card);
					missingCardCount--;
					isFound = true;
				}
			}
			for (Card card : pile.getCards()) {
				if(!isFound && card.getCardValue() == CardValue.WILD) {
					foundPile = foundPile.addCard(card);
					remainingCards = remainingCards.removeCard(card);
					missingCardCount--;
					isFound = true;
				}
			}
		}		
		
		if(missingCardCount > 0) {
			return new MissingResult(missingCardCount, remainingCards);
		}else {
			return new FoundPhaseResult(remainingCards,new SimPileAndPileElement(foundPile, new SequenceDockPile()));
		}		
	}
}
