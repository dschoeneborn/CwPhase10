package de.fh_dortmund.inf.cw.phaseten.server.beans.ai.raiting.phase_element;

import java.util.HashMap;
import java.util.Map;

import de.fh_dortmund.inf.cw.phaseten.server.beans.ai.SimPile;
import de.fh_dortmund.inf.cw.phaseten.server.entities.Card;
import de.fh_dortmund.inf.cw.phaseten.server.entities.CardValue;
import de.fh_dortmund.inf.cw.phaseten.server.entities.SetDockPile;

/**
 * @author Robin Harbecke
 *
 */
public class IdenticalCards extends PhaseElement {
	private int totalCardCount;

	public IdenticalCards(int totalCardCount) {
		super();
		this.totalCardCount = totalCardCount;
	}

	@Override
	public MissingResult getMissingCards(SimPile pile) {
		Map<CardValue,Integer> cardCount = new HashMap<CardValue,Integer>();
		cardCount.put(CardValue.WILD, 0);
		for (Card card : pile.getCards()) {
			if(card.getCardValue() != CardValue.SKIP);
			if(!cardCount.containsKey(card.getCardValue())) {
				cardCount.put(card.getCardValue(), 0);
			}
			cardCount.put(card.getCardValue(), cardCount.get(card.getCardValue())+1);
		}
		CardValue bestCardValue = null;
		int bestCardCount = 0;
		for (CardValue cardValue : cardCount.keySet()) {
			if(cardValue != CardValue.WILD && cardValue !=  CardValue.SKIP) {
				if(cardCount.get(cardValue) > bestCardCount) {
					bestCardCount = cardCount.get(cardValue);
					bestCardValue = cardValue;
				}
			}
		}
		if(bestCardValue == null) return new MissingResult(this.totalCardCount,pile);
		SimPile remainingCards = pile;
		SimPile foundCards = SimPile.empty;
		int missingCardCount = this.totalCardCount;
		for (Card card: pile.getCards()) {
			if(missingCardCount >0 && card.getCardValue() == bestCardValue) {
				remainingCards = remainingCards.removeCard(card);
				foundCards = foundCards.addCard(card);
				missingCardCount--;
			}						
		}
		for (Card card: pile.getCards()) {
			if(missingCardCount >0 && card.getCardValue() == CardValue.WILD) {
				remainingCards = remainingCards.removeCard(card);
				foundCards = foundCards.addCard(card);
				missingCardCount--;
			}						
		}
		
		if(missingCardCount == 0) {
			return new FoundPhaseResult(remainingCards,new SimPileAndPileElement(foundCards, new SetDockPile(bestCardValue)));
		}else {
			return new MissingResult(missingCardCount, remainingCards);
		}
		
	}		 
}
