package de.fh_dortmund.inf.cw.phaseten.server.beans.ai;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.fh_dortmund.inf.cw.phaseten.server.beans.ai.raiting.RateCardsForPhaseUtil;
import de.fh_dortmund.inf.cw.phaseten.server.beans.ai.raiting.RateCardsUtil;
import de.fh_dortmund.inf.cw.phaseten.server.beans.ai.raiting.phase_element.FoundPhaseResult;
import de.fh_dortmund.inf.cw.phaseten.server.beans.ai.raiting.phase_element.MissingResult;
import de.fh_dortmund.inf.cw.phaseten.server.beans.ai.raiting.phase_element.SimPileAndPileElement;
import de.fh_dortmund.inf.cw.phaseten.server.entities.Card;
import de.fh_dortmund.inf.cw.phaseten.server.entities.DockPile;
import de.fh_dortmund.inf.cw.phaseten.server.entities.Game;
import de.fh_dortmund.inf.cw.phaseten.server.entities.Player;
import de.fh_dortmund.inf.cw.phaseten.server.entities.ai.CardsToPileAction;
import de.fh_dortmund.inf.cw.phaseten.server.entities.ai.TakeCardAction;
import de.fh_dortmund.inf.cw.phaseten.server.enumerations.CardValue;
import de.fh_dortmund.inf.cw.phaseten.server.enumerations.Color;

/**
 * @author Robin Harbecke
 */
public class SimpleAiBean implements IAIPlayer {
	@Override
	public TakeCardAction takeCard(Player player, Game game) {
		Card discardCard = game.getLiFoStack().showCard();

		double ratingWithDiscardCard = this.getRatingWithExtraCard(player, game, discardCard);
		double ratingWithDrawerCard = this.getRatingWithRandomCard(player, game);

		if(ratingWithDiscardCard >= ratingWithDrawerCard){
			return TakeCardAction.DISCARD_PILE;
		} else {
			return TakeCardAction.DRAWER_PILE;
		}
	}

	private double getRatingWithRandomCard(Player player, Game game) {
		double sumRating = 0;
		int countRating = 0;

		for(CardValue cardValue : CardValue.values() ) {
			for(Color cardColor : Color.values()) {
				if(cardColor == Color.NONE && cardValue != CardValue.WILD && cardValue != CardValue.SKIP) {
					continue;
				}
				if(cardColor != Color.NONE && cardValue == CardValue.WILD) {
					continue;
				}
				if(cardColor != Color.NONE && cardValue == CardValue.SKIP) {
					continue;
				}

				Card card = new Card(cardColor, cardValue);
				sumRating += getRatingWithExtraCard(player, game, card);
				countRating++;
			}
		}
		return sumRating / (countRating);
	}

	private double getRatingWithExtraCard(Player player, Game game, Card card) {
		return RateCardsUtil.rateCards(
				player.playerLaidStage(),
				player.getPhase(),
				SimPile.from(
						player.getPlayerPile(),
						card
						),
				game
				);
	}

	@Override
	public List<CardsToPileAction> cardsToPile(Player player, Game game) {
		if(!player.playerLaidStage()) {
			return this.layPhase(player, game);
		}else
		{
			return this.putCardsToExistingPile(player, game);
		}

	}

	private List<CardsToPileAction> layPhase(Player player, Game game) {
		List<CardsToPileAction> actions = new ArrayList<>();
		MissingResult rating = RateCardsForPhaseUtil.getPhaseResult(SimPile.from(player.getPlayerPile()), player.getPhase());
		if(rating instanceof FoundPhaseResult) {
			FoundPhaseResult foundPhase = (FoundPhaseResult) rating;
			for (SimPileAndPileElement pileElement: foundPhase.getFoundPiles()) {
				actions.add(new CardsToPileAction(pileElement.getPile(), pileElement.getSimPile().getCards(), false));
			}
		}
		return actions;
	}

	private List<CardsToPileAction> putCardsToExistingPile(Player player, Game game) {
		List<CardsToPileAction> actions = new ArrayList<>();
		for (Card card : player.getPlayerPile().getCopyOfCardsList()) {
			for (DockPile pile: game.getOpenPiles()) {
				if(actions.size() < (player.getPlayerPile().getCopyOfCardsList().size()-1) && (pile.canAddLastCard(card) || pile.canAddFirstCard(card))){
					actions.add(new CardsToPileAction(pile, Arrays.asList(card), true));
				}
			}
		}
		return actions;
	}

	@Override
	public Card discardCard(Player player, Game game) {
		Card minCard = null;
		double minCardRating = Double.MAX_VALUE;
		double tmpRating = -1;

		for(Card card : player.getPlayerPile().getCopyOfCardsList()) {
			tmpRating = getRatingWithoutCard(player, game, card);
			if( tmpRating < minCardRating ) {
				minCardRating = tmpRating;
				minCard = card;
			}
		}

		return minCard;
	}

	private double getRatingWithoutCard(Player player, Game game, Card card) {
		return RateCardsUtil.rateCards(
				player.playerLaidStage(),
				player.getPhase(),
				SimPile.from(
						player.getPlayerPile()
						).removeCard(card),
				game
				);
	}
}
