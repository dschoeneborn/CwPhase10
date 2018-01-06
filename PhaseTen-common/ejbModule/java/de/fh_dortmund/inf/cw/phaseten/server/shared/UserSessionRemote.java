/**
 *
 */
package de.fh_dortmund.inf.cw.phaseten.server.shared;

import java.util.Collection;

import javax.ejb.Remote;

import de.fh_dortmund.inf.cw.phaseten.server.entities.Card;
import de.fh_dortmund.inf.cw.phaseten.server.entities.DockPile;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.GameNotInitializedException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.MoveNotValidException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.NoFreeSlotException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.NotEnoughPlayerException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.NotLoggedInException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.PlayerDoesNotExistsException;
import de.fh_dortmund.inf.cw.phaseten.server.messages.PlayerGuiData;

/**
 * @author Dennis Schöneborn
 */
@Remote
public interface UserSessionRemote extends UserSession {

	/**
	 * TODO Add JavaDoc
	 *
	 * @author Tim Prange
	 */
	void enterLobbyAsPlayer() throws NoFreeSlotException, PlayerDoesNotExistsException, NotLoggedInException;

	/**
	 * TODO Add JavaDoc
	 *
	 * @author Tim Prange
	 */
	void enterLobbyAsSpectator() throws NotLoggedInException;

	/**
	 * TODO Add JavaDoc
	 *
	 * @author Tim Prange
	 */
	void startGame() throws NotEnoughPlayerException, PlayerDoesNotExistsException, NotLoggedInException;

	/**
	 * TODO Add JavaDoc
	 *
	 * @author Tim Prange
	 */
	void takeCardFromPullstack() throws MoveNotValidException, NotLoggedInException, GameNotInitializedException;

	/**
	 * TODO Add JavaDoc
	 *
	 * @author Tim Prange
	 */
	void takeCardFromLiFoStack() throws MoveNotValidException, NotLoggedInException, GameNotInitializedException;

	/**
	 * TODO Add JavaDoc
	 *
	 * @author Tim Prange
	 * @param card
	 * @param dockPile
	 */
	void addToPileOnTable(Card card, long dockPileId)
			throws MoveNotValidException, NotLoggedInException, GameNotInitializedException;

	/**
	 * TODO Add JavaDoc
	 *
	 * @author Tim Prange
	 * @param cards
	 */
	void layPhaseToTable(Collection<DockPile> cards)
			throws MoveNotValidException, NotLoggedInException, GameNotInitializedException;

	/**
	 * TODO Add JavaDoc
	 *
	 * @author Tim Prange
	 * @param card
	 */
	void layCardToLiFoStack(Card card) throws MoveNotValidException, NotLoggedInException, GameNotInitializedException;

	/**
	 * TODO Add JavaDoc
	 *
	 * @author Tim Prange
	 * @param destinationPlayerId
	 * @param card
	 */
	void laySkipCardForPlayer(long destinationPlayerId, Card card) throws MoveNotValidException, NotLoggedInException,
			PlayerDoesNotExistsException, GameNotInitializedException;

	/**
	 * TODO Add JavaDoc
	 *
	 * @author Tim Prange
	 */
	void exitLobby() throws NotLoggedInException;

	/**
	 * TODO Add JavaDoc
	 *
	 * @author Tim Prange
	 */
	boolean playerIsInGame() throws NotLoggedInException;

	/**
	 * TODO Add JavaDoc
	 *
	 * @author Tim Prange
	 * @throws GameNotInitializedException
	 * @throws NotLoggedInException
	 * @throws PlayerDoesNotExistsException
	 */
	void requestGameMessage() throws PlayerDoesNotExistsException, NotLoggedInException, GameNotInitializedException;

	/**
	 * TODO Add JavaDoc
	 *
	 * @author Tim Prange
	 * @return
	 */
	Collection<PlayerGuiData> getLobbyPlayers();

	/**
	 * TODO Add JavaDoc
	 *
	 * @author Tim Prange
	 * @return
	 */
	Collection<String> getLobbySpectators();

	/**
	 * TODO Add JavaDoc
	 *
	 * @author Tim Prange
	 */
	void requestPlayerMessage() throws PlayerDoesNotExistsException, NotLoggedInException;

	/**
	 * TODO Add JavaDoc
	 *
	 * @author Tim Prange
	 */
	void requestLobbyMessage();

}
