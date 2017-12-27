package de.fh_dortmund.inf.cw.phaseten.server.beans;

import java.util.ArrayList;
import java.util.List;

import de.fh_dortmund.inf.cw.phaseten.server.entities.Card;
import de.fh_dortmund.inf.cw.phaseten.server.entities.CardValue;
import de.fh_dortmund.inf.cw.phaseten.server.entities.Color;
import de.fh_dortmund.inf.cw.phaseten.server.entities.ColorDockPile;
import de.fh_dortmund.inf.cw.phaseten.server.entities.Game;
import de.fh_dortmund.inf.cw.phaseten.server.entities.Pile;
import de.fh_dortmund.inf.cw.phaseten.server.entities.Player;
import de.fh_dortmund.inf.cw.phaseten.server.entities.RoundStage;
import de.fh_dortmund.inf.cw.phaseten.server.entities.SequenceDockPile;
import de.fh_dortmund.inf.cw.phaseten.server.entities.SetDockPile;

/**
 * Bean that validates if a given turn is allowed in the given game condition
 *
 * @author Björn Merschmeier
 */
public class GameValidationBean {
	/**
	 * Validates if its allowed to draw a card from the LiFo stack
	 *
	 * @param g The game to check
	 * @param player The player who wants to draw a card
	 * @return pullCardAllowed
	 * @author Björn Merschmeier
	 */
	public boolean isValidDrawCardFromLiFoStack(Game g, Player player) {
		boolean pullCardAllowed = true;

		Card cardOnTop = g.getLiFoStack().showCard();

		if (cardOnTop.getCardValue() == CardValue.SKIP || !playerIsCurrentPlayer(g, player)
				|| player.getRoundStage() != RoundStage.PULL) {
			pullCardAllowed = false;
		}

		return pullCardAllowed;
	}

	/**
	 * Validates if its allowed to push a card to the LiFo stack
	 *
	 * @author Björn Merschmeier
	 * @param g The game to check
	 * @param player The player who wants to push a card
	 * @param c the Card the player wants to push
	 * @return pushCardToLiFoStackAllowed
	 */
	public boolean isValidPushCardToLiFoStack(Game g, Player player, Card c) {
		boolean pushCardToLiFoStackAllowed = false;

		if (playerIsCurrentPlayer(g, player) && player.getRoundStage() == RoundStage.PUSH && playerHasCard(c, player)
				&& (!player.hasSkipCard() || (player.hasSkipCard() && c.getCardValue() == CardValue.SKIP))) {
			pushCardToLiFoStackAllowed = true;
		}

		return pushCardToLiFoStackAllowed;
	}

	/**
	 * Validates if its allowed to draw a card from the pull stack
	 *
	 * @param g The game to check
	 * @param player The player who wants to draw a card
	 * @return pullCardAllowed
	 * @author Björn Merschmeier
	 */
	public boolean isValidDrawCardFromPullStack(Game g, Player player) {
		boolean pullCardAllowed = true;

		Player currentPlayer = g.getCurrentPlayer();

		if (!playerIsCurrentPlayer(g, player) || currentPlayer.getRoundStage() != RoundStage.PULL) {
			pullCardAllowed = false;
		}

		return pullCardAllowed;
	}

	/**
	 * Validates if the given player is allowed to lay the stage he wants to
	 *
	 * @author Björn Merschmeier
	 * @param g the game to check
	 * @param p the player who wants to lay the stage
	 * @param piles The card the player wants to lay its stage with
	 * @return layStageDownAllowed
	 */
	public boolean isValidLayStageToTable(Game g, Player p, ArrayList<Pile> piles) {
		boolean layStageDownAllowed = false;

		ArrayList<Card> cardsInPiles = new ArrayList<>();

		for (Pile pile : piles) {
			cardsInPiles.addAll(pile.getCards());
		}

		if (playerIsCurrentPlayer(g, p) && !p.hasSkipCard() && p.getRoundStage() == RoundStage.PUT
				&& !p.playerLaidStage() && playerHasCards(cardsInPiles, p)) {
			switch (p.getPhase()) {
			case TWO_TRIPLES:
				layStageDownAllowed = areCardsReadyForPhase1(piles);
				break;
			case TRIPLE_AND_SEQUENCE_OF_FOUR:
				layStageDownAllowed = areCardsReadyForPhase2(piles);
				break;
			case QUADRUPLE_AND_SEQUENCE_OF_FOUR:
				layStageDownAllowed = areCardsReadyForPhase3(piles);
				break;
			case SEQUENCE_OF_SEVEN:
				layStageDownAllowed = areCardsReadyForPhase4(piles);
				break;
			case SEQUENCE_OF_EIGHT:
				layStageDownAllowed = areCardsReadyForPhase5(piles);
				break;
			case SEQUENCE_OF_NINE:
				layStageDownAllowed = areCardsReadyForPhase6(piles);
				break;
			case TWO_QUADRUPLES:
				layStageDownAllowed = areCardsReadyForPhase7(piles);
				break;
			case SEVEN_OF_ONE_COLOR:
				layStageDownAllowed = areCardsReadyForPhase8(piles);
				break;
			case QUINTUPLE_AND_TWIN:
				layStageDownAllowed = areCardsReadyForPhase9(piles);
				break;
			case QUINTUPLE_AND_TRIPLE:
				layStageDownAllowed = areCardsReadyForPhase10(piles);
				break;
			default:
				break;
			}
		}

		return layStageDownAllowed;
	}

	/**
	 * Validates if the given Player is allowed to add the given card to the given
	 * pile
	 *
	 * @author Tim Prange
	 * @param g the game to validated
	 * @param p the player who wants to add a card
	 * @param pile the pile the player wants to add the card
	 * @param c the card the player wants to add
	 * @return addCardAllowed
	 */
	public boolean isValidToAddCard(Game g, Player p, Pile pile, Card c) {
		boolean addCardAllowed = false;

		if (playerIsCurrentPlayer(g, p) && !p.hasSkipCard() && p.getRoundStage() == RoundStage.PUT
				&& p.getPlayerPile().getSize() >= 1 && p.playerLaidStage() && playerHasCard(c, p)) {
			if (pile instanceof SetDockPile) {
				SetDockPile setDockPile = (SetDockPile) pile;

				if (setDockPile.getCardValue() == c.getCardValue()) {
					addCardAllowed = true;
				}
			}
			else if (pile instanceof SequenceDockPile) {
				SequenceDockPile sequenceDockPile = (SequenceDockPile) pile;

				if ((sequenceDockPile.getMinimum().getValue() - 1 == c.getCardValue().getValue()
						|| sequenceDockPile.getMaximum().getValue() + 1 == c.getCardValue().getValue()
						|| c.getCardValue() == CardValue.JOKER) && !pileIsFull(sequenceDockPile)) {
					addCardAllowed = true;
				}
			}
			else if (pile instanceof ColorDockPile) {
				ColorDockPile colorDockPile = (ColorDockPile) pile;

				if (colorDockPile.getColor() == c.getColor()) {
					addCardAllowed = true;
				}
			}
		}

		return addCardAllowed;
	}

	/**
	 * Validated if the given user is allowed to lay a skip card to a given user
	 *
	 * @author Tim Prange
	 * @param currentPlayer the player that wants to lay a skip card
	 * @param destinationPlayer the player that gets the skip card
	 * @param g the game to check
	 * @return canLaySkipCard
	 */
	public boolean isValidLaySkipCard(Player currentPlayer, Player destinationPlayer, Game g) {
		boolean canLaySkipCard = false;

		Card skipCard = new Card(Color.NONE, CardValue.SKIP);

		if (playerHasCard(skipCard, currentPlayer) && currentPlayer.getRoundStage() == RoundStage.PUSH
				&& playerIsCurrentPlayer(g, currentPlayer) && !currentPlayer.hasSkipCard()
				&& !destinationPlayer.hasSkipCard()) {
			canLaySkipCard = true;
		}

		return canLaySkipCard;
	}

	/**
	 * Validates if the given user has more cards
	 *
	 * @author Tim Prange
	 * @param cards The cards the player should have
	 * @param p the player
	 * @return userHasCards
	 */
	private boolean playerHasCards(List<Card> cards, Player p) {
		boolean userHasCards = true;

		for (Card card : cards) {
			if (!playerHasCard(card, p)) {
				userHasCards = false;
			}
		}

		return userHasCards;
	}

	/**
	 * Validated if a given user hat a given card
	 *
	 * @author Tim Prange
	 * @param card the player should have
	 * @param p the player that should have the card
	 * @return playerHasCard
	 */
	private boolean playerHasCard(Card card, Player p) {
		boolean playerHasCard = false;

		for (Card c : p.getPlayerPile().getCards()) {
			if (c.getCardValue() == card.getCardValue() && c.getColor() == card.getColor()) {
				playerHasCard = true;
				break;
			}
		}

		return playerHasCard;
	}

	/**
	 * Validates if a given pile is "full" which means all cards from one to twelve
	 * are in that given pile
	 *
	 * @author Tim Prange
	 * @param sequenceDockPile the pile to check
	 * @return pileIsFull
	 */
	private boolean pileIsFull(SequenceDockPile sequenceDockPile) {
		// TODO needs to include wildcards
		return sequenceDockPile.getMinimum() == CardValue.ONE && sequenceDockPile.getMaximum() == CardValue.TWELVE;
	}

	/**
	 * Validated if the card are ready for phase 1
	 *
	 * @author Björn Merschmeier
	 * @param piles that should represent phase 1
	 * @return cardReadeForPhase1
	 */
	private boolean areCardsReadyForPhase1(ArrayList<Pile> piles) {
		return (piles.size() == 2 && pileIsSet(piles.get(0), 3) && pileIsSet(piles.get(1), 3));
	}

	/**
	 * Validated if the card are ready for phase 2
	 *
	 * @author Björn Merschmeier
	 * @param piles that should represent phase 2
	 * @return cardReadeForPhase2
	 */
	private boolean areCardsReadyForPhase2(ArrayList<Pile> piles) {
		return (piles.size() == 2 && ((pileIsSet(piles.get(0), 3) && pileIsSequence(piles.get(1), 4))
				|| (pileIsSet(piles.get(1), 3) && pileIsSequence(piles.get(0), 4))));
	}

	/**
	 * Validated if the card are ready for phase 3
	 *
	 * @author Björn Merschmeier
	 * @param piles that should represent phase 3
	 * @return cardReadeForPhase3
	 */
	private boolean areCardsReadyForPhase3(ArrayList<Pile> piles) {
		return (piles.size() == 2 && ((pileIsSet(piles.get(0), 4) && pileIsSequence(piles.get(1), 4))
				|| (pileIsSet(piles.get(1), 4) && pileIsSequence(piles.get(0), 4))));
	}

	/**
	 * Validated if the card are ready for phase 4
	 *
	 * @author Björn Merschmeier
	 * @param piles that should represent phase 4
	 * @return cardReadeForPhase4
	 */
	private boolean areCardsReadyForPhase4(ArrayList<Pile> piles) {
		return (piles.size() == 1 && pileIsSequence(piles.get(0), 7));
	}

	/**
	 * Validated if the card are ready for phase 5
	 *
	 * @author Björn Merschmeier
	 * @param piles that should represent phase 5
	 * @return cardReadeForPhase5
	 */
	private boolean areCardsReadyForPhase5(ArrayList<Pile> piles) {
		return (piles.size() == 1 && pileIsSequence(piles.get(0), 8));
	}

	/**
	 * Validated if the card are ready for phase 6
	 *
	 * @author Björn Merschmeier
	 * @param piles that should represent phase 6
	 * @return cardReadeForPhase6
	 */
	private boolean areCardsReadyForPhase6(ArrayList<Pile> piles) {
		return (piles.size() == 1 && pileIsSequence(piles.get(0), 9));
	}

	/**
	 * Validated if the card are ready for phase 7
	 *
	 * @author Björn Merschmeier
	 * @param piles that should represent phase 7
	 * @return cardReadeForPhase7
	 */
	private boolean areCardsReadyForPhase7(ArrayList<Pile> piles) {
		return (piles.size() == 2 && pileIsSet(piles.get(0), 4) && pileIsSet(piles.get(1), 4));
	}

	/**
	 * Validated if the card are ready for phase 8
	 *
	 * @author Björn Merschmeier
	 * @param piles that should represent phase 8
	 * @return cardReadeForPhase8
	 */
	private boolean areCardsReadyForPhase8(ArrayList<Pile> piles) {
		return (piles.size() == 1 && piles.get(0) instanceof SetDockPile && piles.get(0).getSize() == 7);
	}

	/**
	 * Validated if the card are ready for phase 9
	 *
	 * @author Björn Merschmeier
	 * @param piles that should represent phase 9
	 * @return cardReadeForPhase9
	 */
	private boolean areCardsReadyForPhase9(ArrayList<Pile> piles) {
		return (piles.size() == 2 && ((pileIsSet(piles.get(0), 2) && pileIsSet(piles.get(1), 5))
				|| (pileIsSet(piles.get(1), 2) && pileIsSet(piles.get(0), 5))));
	}

	/**
	 * Validated if the card are ready for phase 10
	 *
	 * @author Björn Merschmeier
	 * @param piles that should represent phase 10
	 * @return cardReadeForPhase10
	 */
	private boolean areCardsReadyForPhase10(ArrayList<Pile> piles) {
		return (piles.size() == 2 && ((pileIsSet(piles.get(0), 3) && pileIsSet(piles.get(1), 5))
				|| (pileIsSet(piles.get(1), 3) && pileIsSet(piles.get(0), 5))));
	}

	/**
	 * Validates if the given pile represents a set of a given size
	 *
	 * @author Björn Merschmeier
	 * @param pile the pile to check
	 * @param i the size of the pile
	 * @return pileIsSet
	 */
	private boolean pileIsSet(Pile pile, int i) {
		return (pile instanceof SetDockPile && pile.getSize() == i);
	}

	/**
	 * validates if the given pile represents a sequence of a given size
	 *
	 * @author Björn Merschmeier
	 * @param pile the pile to check
	 * @param i the size of the pile
	 * @return pileIsSequence
	 */
	private boolean pileIsSequence(Pile pile, int i) {
		return (pile instanceof SequenceDockPile && pile.getSize() == i);
	}

	/**
	 * Validates if the given player is the current player in a given game
	 *
	 * @author Björn Merschmeier
	 * @param g The game to check
	 * @param player The player who wants to draw a card
	 * @return playerIsCurrentPlayer
	 */
	private boolean playerIsCurrentPlayer(Game g, Player p) {
		Player currentPlayer = g.getCurrentPlayer();

		// XXX Maybe check if current user is not skiped
		return (currentPlayer.getId() == p.getId());
	}
}
