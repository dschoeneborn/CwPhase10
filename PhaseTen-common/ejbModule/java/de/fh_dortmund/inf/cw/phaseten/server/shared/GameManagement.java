package de.fh_dortmund.inf.cw.phaseten.server.shared;

import java.util.Collection;

import de.fh_dortmund.inf.cw.phaseten.server.entities.DockPile;
import de.fh_dortmund.inf.cw.phaseten.server.entities.Player;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.GameNotInitializedException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.MoveNotValidException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.PlayerDoesNotExistsException;

/**
 * @author Marc Mettke
 * @author Björn Merschmeier
 * @author Tim Prange
 */
public interface GameManagement {
	/**
	 * Draw a card from the lifo-stack
	 * @param player
	 * @throws MoveNotValidException
	 * @throws GameNotInitializedException
	 */
	void takeCardFromLiFoStack(Player player) throws MoveNotValidException, GameNotInitializedException;

	/**
	 * Add a card to a pile which was layed out already "on the table"
	 * @param player
	 * @param cardId
	 * @param dockPileId
	 * @throws MoveNotValidException
	 * @throws GameNotInitializedException
	 */
	void addToPileOnTable(Player player, long cardId, long dockPileId)
			throws MoveNotValidException, GameNotInitializedException;

	/**
	 * Lay a card from the players inventory on the lifo-stack to end his turn
	 * @param player
	 * @param cardId
	 * @throws MoveNotValidException
	 * @throws GameNotInitializedException
	 */
	void layCardToLiFoStack(Player player, long cardId) throws MoveNotValidException, GameNotInitializedException;

	/**
	 * Draw a card from the pullstack
	 * @param player
	 * @throws MoveNotValidException
	 * @throws GameNotInitializedException
	 */
	void takeCardFromPullstack(Player player) throws MoveNotValidException, GameNotInitializedException;

	/**
	 * Lay a phase to the table - checks if this is valid
	 * @param player
	 * @param piles
	 * @throws MoveNotValidException
	 * @throws GameNotInitializedException
	 */
	void layPhaseToTable(Player player, Collection<DockPile> piles)
			throws MoveNotValidException, GameNotInitializedException;

	/**
	 * Lay skip card in front of another player
	 * @param currentPlayer
	 * @param destinationPlayerId
	 * @param cardId
	 * @throws MoveNotValidException
	 * @throws PlayerDoesNotExistsException
	 * @throws GameNotInitializedException
	 */
	void laySkipCardForPlayerById(Player currentPlayer, long destinationPlayerId, long cardId)
			throws MoveNotValidException, PlayerDoesNotExistsException, GameNotInitializedException;

	/**
	 * Lay a card to an already layed down pile, but try to lay it in the front of the pile
	 * @param player
	 * @param cardId
	 * @param dockPileId
	 * @param layAtFirstPosition
	 * @throws MoveNotValidException
	 * @throws GameNotInitializedException
	 */
	void addToPileOnTable(Player player, long cardId, long dockPileId, boolean layAtFirstPosition) throws MoveNotValidException, GameNotInitializedException;

}
