package de.fh_dortmund.inf.cw.phaseten.server.shared;

import java.util.Collection;

import de.fh_dortmund.inf.cw.phaseten.server.entities.Card;
import de.fh_dortmund.inf.cw.phaseten.server.entities.DockPile;
import de.fh_dortmund.inf.cw.phaseten.server.entities.Game;
import de.fh_dortmund.inf.cw.phaseten.server.entities.Pile;
import de.fh_dortmund.inf.cw.phaseten.server.entities.Player;

/**
 * @author Björn Merschmeier
 * @author Tim Prange
 */
public interface GameValidation {

	/**
	 * Validates if its allowed to draw a card from the LiFo stack
	 *
	 * @param g The game to check
	 * @param player The player who wants to draw a card
	 * @return pullCardAllowed
	 * @author Björn Merschmeier
	 */
	boolean isValidDrawCardFromLiFoStack(Game g, Player player);

	/**
	 * Validates if its allowed to push a card to the LiFo stack
	 *
	 * @author Björn Merschmeier
	 * @param g The game to check
	 * @param player The player who wants to push a card
	 * @param c the Card the player wants to push
	 * @return pushCardToLiFoStackAllowed
	 */
	boolean isValidPushCardToLiFoStack(Game g, Player player, Card c);

	/**
	 * Validates if its allowed to draw a card from the pull stack
	 *
	 * @param g The game to check
	 * @param player The player who wants to draw a card
	 * @return pullCardAllowed
	 * @author Björn Merschmeier
	 */
	boolean isValidDrawCardFromPullStack(Game game, Player player);

	/**
	 * Validates if the given player is allowed to lay the stage he wants to
	 *
	 * @author Björn Merschmeier
	 * @param g the game to check
	 * @param p the player who wants to lay the stage
	 * @param piles The card the player wants to lay its stage with
	 * @return layStageDownAllowed
	 */
	boolean isValidLayStageToTable(Game g, Player p, Collection<DockPile> piles);

	/**
	 * Validated if the given user is allowed to lay a skip card to a given user
	 *
	 * @author Tim Prange
	 * @author Björn Merschmeier
	 * @param currentPlayer the player that wants to lay a skip card
	 * @param destinationPlayer the player that gets the skip card
	 * @param g the game to check
	 * @return canLaySkipCard
	 */
	boolean isValidLaySkipCard(Player currentPlayer, Player destinationPlayer, Game g);


	/**
	 * Validates if the given Player is allowed to add the given card to the given
	 * pile
	 *
	 * @author Björn Merschmeier
	 * @param g the game to validated
	 * @param p the player who wants to add a card
	 * @param pile the pile the player wants to add the card
	 * @param c the card the player wants to add
	 * @return addCardAllowed
	 */
	boolean isValidToAddCardFirst(Game g, Player p, Pile pile, Card c);


	/**
	 * Validates if the given Player is allowed to add the given card to the given
	 * pile
	 *
	 * @author Björn Merschmeier
	 * @param g the game to validated
	 * @param p the player who wants to add a card
	 * @param pile the pile the player wants to add the card
	 * @param c the card the player wants to add
	 * @return addCardAllowed
	 */
	boolean isValidToAddCardLast(Game g, Player p, Pile pile, Card c);
}
