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
	void takeCardFromLiFoStack(Player player) throws MoveNotValidException, GameNotInitializedException;

	void addToPileOnTable(Player player, long cardId, long dockPileId)
			throws MoveNotValidException, GameNotInitializedException;

	void layCardToLiFoStack(Player player, long cardId) throws MoveNotValidException, GameNotInitializedException;

	void takeCardFromPullstack(Player player) throws MoveNotValidException, GameNotInitializedException;

	void layPhaseToTable(Player player, Collection<DockPile> piles)
			throws MoveNotValidException, GameNotInitializedException;

	void laySkipCardForPlayerById(Player currentPlayer, long destinationPlayerId, long cardId)
			throws MoveNotValidException, PlayerDoesNotExistsException, GameNotInitializedException;

}
