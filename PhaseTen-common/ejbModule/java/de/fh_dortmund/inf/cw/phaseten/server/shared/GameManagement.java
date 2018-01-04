package de.fh_dortmund.inf.cw.phaseten.server.shared;

import java.util.Collection;

import de.fh_dortmund.inf.cw.phaseten.server.entities.Card;
import de.fh_dortmund.inf.cw.phaseten.server.entities.DockPile;
import de.fh_dortmund.inf.cw.phaseten.server.entities.Player;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.MoveNotValidException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.PlayerDoesNotExistsException;

/**
 * @author Marc Mettke
 * @author Björn Merschmeier
 * @author Tim Prange
 */
public interface GameManagement {
	void takeCardFromLiFoStack(Player player) throws MoveNotValidException;

	void addToPileOnTable(Player player, Card card, DockPile dockPile) throws MoveNotValidException;

	void layCardToLiFoStack(Player player, Card card) throws MoveNotValidException;

	void takeCardFromPullstack(Player player) throws MoveNotValidException;

	void layPhaseToTable(Player player, Collection<DockPile> piles) throws MoveNotValidException;

	void laySkipCardForPlayer(Player currentPlayer, Player destinationPlayer, Card card) throws MoveNotValidException, PlayerDoesNotExistsException;

	void laySkipCardForPlayerById(Player currentPlayer, long destinationPlayerId, Card card) throws MoveNotValidException, PlayerDoesNotExistsException;

}
