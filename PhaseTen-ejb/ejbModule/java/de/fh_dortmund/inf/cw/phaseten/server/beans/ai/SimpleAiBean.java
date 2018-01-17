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
import de.fh_dortmund.inf.cw.phaseten.server.entities.CardValue;
import de.fh_dortmund.inf.cw.phaseten.server.entities.Color;
import de.fh_dortmund.inf.cw.phaseten.server.entities.DockPile;
import de.fh_dortmund.inf.cw.phaseten.server.entities.Game;
import de.fh_dortmund.inf.cw.phaseten.server.entities.Player;
import de.fh_dortmund.inf.cw.phaseten.server.entities.ai.CardsToPileAction;
import de.fh_dortmund.inf.cw.phaseten.server.entities.ai.TakeCardAction;

/**
 * @author Robin Harbecke
 */
public class SimpleAiBean implements IAIPlayer {
	@Override
	public TakeCardAction takeCard(Player player, Game game) {
		Card discardCard = game.getLiFoStack().showCard();

		double ratingWithDiscardCard = getRatingWithExtraCard(player, game, discardCard);
		double maxRatingWithDrawerCard = Double.MIN_VALUE;
		double ratingWithDrawerCard;

		for(CardValue cardValue : CardValue.values() ) {
			for(Color cardColor : Color.values()) {
				Card card = new Card(cardColor, cardValue);
				if(!discardCard.equals(card)) {
					ratingWithDrawerCard = getRatingWithExtraCard(player, game, card);
					if(ratingWithDrawerCard > maxRatingWithDrawerCard) {
						maxRatingWithDrawerCard = ratingWithDrawerCard;
					}
				}
			}
		}

		if(ratingWithDiscardCard >= maxRatingWithDrawerCard) {
			return TakeCardAction.DISCARD_PILE;
		} else {
			return TakeCardAction.DRAWER_PILE;
		}
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
				if(pile.getCopyOfCardsList().size() < (player.getPlayerPile().getCopyOfCardsList().size()-1) && pile.canAddCard(card)){
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
