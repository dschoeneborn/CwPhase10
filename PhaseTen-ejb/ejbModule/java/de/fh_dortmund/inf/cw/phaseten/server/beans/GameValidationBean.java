package de.fh_dortmund.inf.cw.phaseten.server.beans;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.ejb.Stateless;

import de.fh_dortmund.inf.cw.phaseten.server.entities.Card;
import de.fh_dortmund.inf.cw.phaseten.server.entities.DockPile;
import de.fh_dortmund.inf.cw.phaseten.server.entities.Game;
import de.fh_dortmund.inf.cw.phaseten.server.entities.Lobby;
import de.fh_dortmund.inf.cw.phaseten.server.entities.Pile;
import de.fh_dortmund.inf.cw.phaseten.server.entities.Player;
import de.fh_dortmund.inf.cw.phaseten.server.entities.SequenceDockPile;
import de.fh_dortmund.inf.cw.phaseten.server.entities.SetDockPile;
import de.fh_dortmund.inf.cw.phaseten.server.enumerations.CardValue;
import de.fh_dortmund.inf.cw.phaseten.server.enumerations.Color;
import de.fh_dortmund.inf.cw.phaseten.server.enumerations.RoundStage;
import de.fh_dortmund.inf.cw.phaseten.server.shared.GameValidationLocal;
import de.fh_dortmund.inf.cw.phaseten.server.shared.GameValidationRemote;

/**
 * Bean that validates if a given turn is allowed in the given game condition
 *
 * @author Björn Merschmeier
 * @author Tim Prange
 * @author Dennis Schöneborn
 */
@Stateless
public class GameValidationBean implements GameValidationLocal, GameValidationRemote {
	/*
	 * (non-Javadoc)
	 * 
	 * @see de.fh_dortmund.inf.cw.phaseten.server.shared.GameValidation#
	 * isValidDrawCardFromLiFoStack(de.fh_dortmund.inf.cw.phaseten.server.entities.
	 * Game, de.fh_dortmund.inf.cw.phaseten.server.entities.Player)
	 */
	/**
	 * @author Björn Merschmeier
	 */
	@Override
	public boolean isValidDrawCardFromLiFoStack(Game g, Player player) {
		boolean pullCardAllowed = true;

		if (!g.isInitialized() || g.getLiFoStack().showCard().getCardValue() == CardValue.SKIP
				|| !playerIsCurrentPlayer(g, player) || player.getRoundStage() != RoundStage.PULL) {
			pullCardAllowed = false;
		}

		return pullCardAllowed;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.fh_dortmund.inf.cw.phaseten.server.shared.GameValidation#
	 * isValidPushCardToLiFoStack(de.fh_dortmund.inf.cw.phaseten.server.entities.
	 * Game, de.fh_dortmund.inf.cw.phaseten.server.entities.Player,
	 * de.fh_dortmund.inf.cw.phaseten.server.entities.Card)
	 */
	/**
	 * @author Björn Merschmeier
	 */
	@Override
	public boolean isValidPushCardToLiFoStack(Game g, Player player, Card c) {
		boolean pushCardToLiFoStackAllowed = false;

		if (g.isInitialized() && playerIsCurrentPlayer(g, player)
				&& (player.getRoundStage() == RoundStage.PUT_AND_PUSH
						|| (player.getRoundStage() == RoundStage.PULL && c.getCardValue() == CardValue.SKIP))
				&& playerHasCard(c, player)
				&& (!player.hasSkipCard() || (player.hasSkipCard() && c.getCardValue() == CardValue.SKIP))) {
			pushCardToLiFoStackAllowed = true;
		}

		return pushCardToLiFoStackAllowed;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.fh_dortmund.inf.cw.phaseten.server.shared.GameValidation#
	 * isValidDrawCardFromPullStack(de.fh_dortmund.inf.cw.phaseten.server.entities.
	 * Game, de.fh_dortmund.inf.cw.phaseten.server.entities.Player)
	 */
	/**
	 * @author Björn Merschmeier
	 */
	@Override
	public boolean isValidDrawCardFromPullStack(Game g, Player player) {
		boolean pullCardAllowed = false;

		Player currentPlayer = g.getCurrentPlayer();

		if (g.isInitialized() && playerIsCurrentPlayer(g, player) && currentPlayer.getRoundStage() == RoundStage.PULL
				&& !currentPlayer.hasSkipCard()) {
			pullCardAllowed = true;
		}

		return pullCardAllowed;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.fh_dortmund.inf.cw.phaseten.server.shared.GameValidation#
	 * isValidLayStageToTable(de.fh_dortmund.inf.cw.phaseten.server.entities.Game,
	 * de.fh_dortmund.inf.cw.phaseten.server.entities.Player, java.util.Collection)
	 */
	@Override
	public boolean isValidLayStageToTable(Game g, Player p, Collection<DockPile> pilesInCollection) {
		boolean layStageDownAllowed = false;

		ArrayList<DockPile> piles = new ArrayList<>(pilesInCollection);

		ArrayList<Card> cardsInPiles = new ArrayList<>();

		for (DockPile pile : piles) {
			cardsInPiles.addAll(pile.getCopyOfCardsList());
		}

		if (g.isInitialized() && playerIsCurrentPlayer(g, p) && !p.hasSkipCard()
				&& p.getRoundStage() == RoundStage.PUT_AND_PUSH && !p.playerLaidStage()
				&& playerHasCards(cardsInPiles, p)) {
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.fh_dortmund.inf.cw.phaseten.server.shared.GameValidation#isValidToAddCard(
	 * de.fh_dortmund.inf.cw.phaseten.server.entities.Game,
	 * de.fh_dortmund.inf.cw.phaseten.server.entities.Player,
	 * de.fh_dortmund.inf.cw.phaseten.server.entities.Pile,
	 * de.fh_dortmund.inf.cw.phaseten.server.entities.Card)
	 */
	/**
	 * @author Björn Merschmeier
	 */
	@Override
	public boolean isValidToAddCardFirst(Game g, Player p, Pile pile, Card c) {
		boolean addCardAllowed = false;

		if (g.isInitialized() && playerIsCurrentPlayer(g, p) && !p.hasSkipCard()
				&& p.getRoundStage() == RoundStage.PUT_AND_PUSH && p.getPlayerPile().getCopyOfCardsList().size() > 1
				&& p.playerLaidStage() && playerHasCard(c, p)) {
			if(pile instanceof DockPile)
			{
				DockPile dockPile = (DockPile) pile;
				addCardAllowed = dockPile.canAddFirstCard(c);
			}
		}

		return addCardAllowed;
	}

	/**
	 * @author Björn Merschmeier
	 */
	@Override
	public boolean isValidToAddCardLast(Game g, Player p, Pile pile, Card c) {
		boolean addCardAllowed = false;

		if (g.isInitialized()
				&& playerIsCurrentPlayer(g, p)
				&& !p.hasSkipCard()
				&& p.getRoundStage() == RoundStage.PUT_AND_PUSH
				&& p.getPlayerPile().getCopyOfCardsList().size() > 1
				&& p.playerLaidStage() && playerHasCard(c, p))
		{
			if(pile instanceof DockPile)
			{
				DockPile dockPile = (DockPile) pile;
				addCardAllowed = dockPile.canAddLastCard(c);
			}
		}

		return addCardAllowed;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.fh_dortmund.inf.cw.phaseten.server.shared.GameValidation#
	 * isValidLaySkipCard(de.fh_dortmund.inf.cw.phaseten.server.entities.Player,
	 * de.fh_dortmund.inf.cw.phaseten.server.entities.Player,
	 * de.fh_dortmund.inf.cw.phaseten.server.entities.Game)
	 */
	/**
	 * @author Tim Prange
	 * @author Björn Merschmeier
	 */
	@Override
	public boolean isValidLaySkipCard(Player currentPlayer, Player destinationPlayer, Game g) {
		boolean canLaySkipCard = false;

		Card skipCard = new Card(Color.NONE, CardValue.SKIP);

		if (g.isInitialized() && playerHasCard(skipCard, currentPlayer)
				&& currentPlayer.getRoundStage() == RoundStage.PUT_AND_PUSH && playerIsCurrentPlayer(g, currentPlayer)
				&& !currentPlayer.hasSkipCard() && !destinationPlayer.hasSkipCard() && !destinationPlayer.hasNoCards()
				&& currentPlayer.getPlayerPile().getCopyOfCardsList().size() > 1) {
			canLaySkipCard = true;
		}

		return canLaySkipCard;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.fh_dortmund.inf.cw.phaseten.server.shared.GameValidation#hasEnoughPlayers(
	 * java.util.List)
	 */
	/**
	 * @author Tim Prange
	 * @author Björn Merschmeier
	 */
	@Override
	public boolean hasEnoughPlayers(Lobby lobby) {
		return lobby.getPlayers().size() >= Game.MIN_PLAYER;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.fh_dortmund.inf.cw.phaseten.server.shared.GameValidation#
	 * cantHaveMorePlayers(java.util.List)
	 */
	/**
	 * @author Tim Prange
	 * @author Björn Merschmeier
	 */
	@Override
	public boolean isLobbyFull(Lobby lobby) {
		return lobby.getPlayers().size() >= Game.MAX_PLAYER;
	}

	/**
	 * Validates if the given user has more cards
	 *
	 * @author Tim Prange
	 * @author Björn Merschmeier
	 * @param cards
	 *            The cards the player should have
	 * @param p
	 *            the player
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
	 * @author Björn Merschmeier
	 * @param card
	 *            the player should have
	 * @param p
	 *            the player that should have the card
	 * @return playerHasCard
	 */
	private boolean playerHasCard(Card card, Player p) {
		boolean playerHasCard = false;

		for (Card c : p.getPlayerPile().getCopyOfCardsList()) {
			if (c.getCardValue() == card.getCardValue() && c.getColor() == card.getColor()) {
				playerHasCard = true;
				break;
			}
		}

		return playerHasCard;
	}

	/**
	 * Validated if the card are ready for phase 1
	 *
	 * @author Björn Merschmeier
	 * @param piles
	 *            that should represent phase 1
	 * @return cardReadyForPhase1
	 */
	private boolean areCardsReadyForPhase1(List<DockPile> piles) {
		return (piles.size() == 2 && pileIsSet(piles.get(0), 3) && pileIsSet(piles.get(1), 3));
	}

	/**
	 * Validated if the card are ready for phase 2
	 *
	 * @author Björn Merschmeier
	 * @param piles
	 *            that should represent phase 2
	 * @return cardReadyForPhase2
	 */
	private boolean areCardsReadyForPhase2(List<DockPile> piles) {
		return (piles.size() == 2 && ((pileIsSet(piles.get(0), 3) && pileIsSequence(piles.get(1), 4))
				|| (pileIsSet(piles.get(1), 3) && pileIsSequence(piles.get(0), 4))));
	}

	/**
	 * Validated if the card are ready for phase 3
	 *
	 * @author Björn Merschmeier
	 * @param piles
	 *            that should represent phase 3
	 * @return cardReadyForPhase3
	 */
	private boolean areCardsReadyForPhase3(List<DockPile> piles) {
		return (piles.size() == 2 && ((pileIsSet(piles.get(0), 4) && pileIsSequence(piles.get(1), 4))
				|| (pileIsSet(piles.get(1), 4) && pileIsSequence(piles.get(0), 4))));
	}

	/**
	 * Validated if the card are ready for phase 4
	 *
	 * @author Björn Merschmeier
	 * @param piles
	 *            that should represent phase 4
	 * @return cardReadyForPhase4
	 */
	private boolean areCardsReadyForPhase4(List<DockPile> piles) {
		return (piles.size() == 1 && pileIsSequence(piles.get(0), 7));
	}

	/**
	 * Validated if the card are ready for phase 5
	 *
	 * @author Björn Merschmeier
	 * @param piles
	 *            that should represent phase 5
	 * @return cardReadyForPhase5
	 */
	private boolean areCardsReadyForPhase5(List<DockPile> piles) {
		return (piles.size() == 1 && pileIsSequence(piles.get(0), 8));
	}

	/**
	 * Validated if the card are ready for phase 6
	 *
	 * @author Björn Merschmeier
	 * @param piles
	 *            that should represent phase 6
	 * @return cardReadyForPhase6
	 */
	private boolean areCardsReadyForPhase6(List<DockPile> piles) {
		return (piles.size() == 1 && pileIsSequence(piles.get(0), 9));
	}

	/**
	 * Validated if the card are ready for phase 7
	 *
	 * @author Björn Merschmeier
	 * @param piles
	 *            that should represent phase 7
	 * @return cardReadyForPhase7
	 */
	private boolean areCardsReadyForPhase7(List<DockPile> piles) {
		return (piles.size() == 2 && pileIsSet(piles.get(0), 4) && pileIsSet(piles.get(1), 4));
	}

	/**
	 * Validated if the card are ready for phase 8
	 *
	 * @author Björn Merschmeier
	 * @param piles
	 *            that should represent phase 8
	 * @return cardReadyorPhase8
	 */
	private boolean areCardsReadyForPhase8(List<DockPile> piles) {
		return (piles.size() == 1 && piles.get(0) instanceof SetDockPile
				&& piles.get(0).getCopyOfCardsList().size() == 7);
	}

	/**
	 * Validated if the card are ready for phase 9
	 *
	 * @author Björn Merschmeier
	 * @param piles
	 *            that should represent phase 9
	 * @return cardReadyForPhase9
	 */
	private boolean areCardsReadyForPhase9(List<DockPile> piles) {
		return (piles.size() == 2 && ((pileIsSet(piles.get(0), 2) && pileIsSet(piles.get(1), 5))
				|| (pileIsSet(piles.get(1), 2) && pileIsSet(piles.get(0), 5))));
	}

	/**
	 * Validated if the card are ready for phase 10
	 *
	 * @author Björn Merschmeier
	 * @param piles
	 *            that should represent phase 10
	 * @return cardReadyForPhase10
	 */
	private boolean areCardsReadyForPhase10(List<DockPile> piles) {
		return (piles.size() == 2 && ((pileIsSet(piles.get(0), 3) && pileIsSet(piles.get(1), 5))
				|| (pileIsSet(piles.get(1), 3) && pileIsSet(piles.get(0), 5))));
	}

	/**
	 * Validates if the given pile represents a set of a given size
	 *
	 * @author Björn Merschmeier
	 * @param pile
	 *            the pile to check
	 * @param i
	 *            the size of the pile
	 * @return pileIsSet
	 */
	private boolean pileIsSet(DockPile pile, int i) {
		return (pile instanceof SetDockPile && pile.getCopyOfCardsList().size() == i);
	}

	/**
	 * validates if the given pile represents a sequence of a given size
	 *
	 * @author Björn Merschmeier
	 * @param pile
	 *            the pile to check
	 * @param i
	 *            the size of the pile
	 * @return pileIsSequence
	 */
	private boolean pileIsSequence(DockPile pile, int i) {
		return (pile instanceof SequenceDockPile && pile.getCopyOfCardsList().size() == i);
	}

	/**
	 * Validates if the given player is the current player in a given game
	 *
	 * @author Björn Merschmeier
	 * @param g
	 *            The game to check
	 * @param player
	 *            The player who wants to draw a card
	 * @return playerIsCurrentPlayer
	 */
	private boolean playerIsCurrentPlayer(Game g, Player p) {
		Player currentPlayer = g.getCurrentPlayer();

		return (currentPlayer.getId() == p.getId());
	}
}
