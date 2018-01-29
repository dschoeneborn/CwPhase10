/**
 *
 */
package de.fh_dortmund.inf.cw.phaseten.server.shared;

import java.util.Collection;

import javax.ejb.Remote;

import de.fh_dortmund.inf.cw.phaseten.server.entities.Card;
import de.fh_dortmund.inf.cw.phaseten.server.entities.DockPile;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.GameNotInitializedException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.InsufficientCoinSupplyException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.MoveNotValidException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.NoFreeSlotException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.NotEnoughPlayersException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.NotLoggedInException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.PlayerDoesNotExistsException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.UserIsPlayerException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.UserIsSpectatorException;
import de.fh_dortmund.inf.cw.phaseten.server.messages.PlayerGuiData;

/**
 * @author Dennis Schöneborn
 * @author Tim Prange
 * @author Björn Merschmeier
 * @author Sebastian Seitz
 */
@Remote
public interface UserSessionRemote extends UserSession {

	/**
	 * Enters lobby as a player
	 *
	 * @throws NoFreeSlotException  If the lobby is full
	 * @throws PlayerDoesNotExistsException  If the player does not exist.
	 * @throws NotLoggedInException  If the player is not logged in.
	 * @throws InsufficientCoinSupplyException  If the player does not own enough coins.
	 *
	 * @author Tim Prange
	 * @throws UserIsSpectatorException
	 */
	void enterLobbyAsPlayer() throws NoFreeSlotException, PlayerDoesNotExistsException, NotLoggedInException, InsufficientCoinSupplyException, UserIsSpectatorException;

	/**
	 * Enters lobby as a spectator
	 *
	 * @throws NotLoggedInException  If the player is not logged in.
	 *
	 * @author Tim Prange
	 * @throws UserIsPlayerException
	 */
	void enterLobbyAsSpectator() throws NotLoggedInException, UserIsPlayerException;

	void addAI() throws NoFreeSlotException;

	/**
	 * Starts the game.
	 *
	 * @throws PlayerDoesNotExistsException  If the player does not exist.
	 * @throws NotLoggedInException  If the player is not logged in.
	 * @throws NotEnoughPlayersException  If there are not enough players in the lobby.
	 *
	 * @author Tim Prange
	 * @throws UserIsSpectatorException
	 */
	void startGame() throws NotEnoughPlayersException, PlayerDoesNotExistsException, NotLoggedInException, UserIsSpectatorException;

	/**
	 * Takes a card from the pull stack.
	 *
	 * @throws MoveNotValidException  If the move is against the rules.
	 * @throws NotLoggedInException  If the player is not logged in.
	 * @throws GameNotInitializedException  If the game is not initialized yet.
	 *
	 *
	 * @author Tim Prange
	 * @throws UserIsSpectatorException
	 */
	void takeCardFromPullstack() throws MoveNotValidException, NotLoggedInException, GameNotInitializedException, UserIsSpectatorException;

	/**
	 * Takes a card from the lifo stack.
	 *
	 * @throws MoveNotValidException  If the move is against the rules.
	 * @throws NotLoggedInException  If the player is not logged in.
	 * @throws GameNotInitializedException  If the game is not initialized yet.
	 *
	 * @author Tim Prange
	 * @throws UserIsSpectatorException
	 */
	void takeCardFromLiFoStack() throws MoveNotValidException, NotLoggedInException, GameNotInitializedException, UserIsSpectatorException;

	/**
	 * Adds the card to a pile.
	 *
	 * @author Tim Prange
	 * @param cardId
	 * @param dockPileId
	 *
	 * @throws MoveNotValidException  If the move is against the rules.
	 * @throws NotLoggedInException  If the player is not logged in.
	 * @throws GameNotInitializedException  If the game is not initialized yet.
	 * @throws UserIsSpectatorException
	 *
	 */
	void addToPileOnTable(long cardId, long dockPileId, boolean tryToAttachToFront)
			throws MoveNotValidException, NotLoggedInException, GameNotInitializedException, UserIsSpectatorException;

	/**
	 * Lays a collection of cards as a Phase.
	 *
	 * @author Tim Prange
	 * @param cards
	 *
	 * @throws MoveNotValidException  If the move is against the rules.
	 * @throws NotLoggedInException  If the player is not logged in.
	 * @throws GameNotInitializedException  If the game is not initialized yet.
	 * @throws UserIsSpectatorException
	 *
	 */
	void layPhaseToTable(Collection<DockPile> cards)
			throws MoveNotValidException, NotLoggedInException, GameNotInitializedException, UserIsSpectatorException;

	/**
	 * Lays a card on the lifo stack.
	 *
	 * @author Tim Prange
	 * @param cardId
	 *
	 * @throws MoveNotValidException  If the move is against the rules.
	 * @throws NotLoggedInException  If the player is not logged in.
	 * @throws GameNotInitializedException  If the game is not initialized yet.
	 * @throws UserIsSpectatorException
	 */
	void layCardToLiFoStack(long cardId) throws MoveNotValidException, NotLoggedInException, GameNotInitializedException, UserIsSpectatorException;

	/**
	 * Lays a skip card for a player.
	 *
	 * @author Tim Prange
	 * @param destinationPlayerId
	 * @param cardId
	 *
	 * @throws MoveNotValidException  If the move is against the rules.
	 * @throws NotLoggedInException  If the player is not logged in.
	 * @throws GameNotInitializedException  If the game is not initialized yet.
	 * @throws PlayerDoesNotExistsException  If the player does not exist.
	 * @throws UserIsSpectatorException
	 */
	void laySkipCardForPlayer(long destinationPlayerId, long cardId) throws MoveNotValidException, NotLoggedInException,
	PlayerDoesNotExistsException, GameNotInitializedException, UserIsSpectatorException;

	/**
	 * Player exits the lobby.
	 *
	 * @author Tim Prange
	 *
	 * @throws NotLoggedInException  If the player is not logged in.
	 */
	void exitLobby() throws NotLoggedInException;

	/**
	 * Checks if a player is in a game.
	 *
	 * @author Tim Prange
	 *
	 *
	 * @throws NotLoggedInException  If the player is not logged in.
	 * @throws UserIsSpectatorException
	 */
	boolean playerIsInGame() throws NotLoggedInException, UserIsSpectatorException;

	/**
	 * Returns players in the lobby.
	 *
	 * @author Tim Prange
	 * @return players in the lobby
	 */
	Collection<PlayerGuiData> getLobbyPlayers();

	/**
	 * Returns spectators in the lobby.
	 *
	 * @author Tim Prange
	 * @return spectators in the lobby
	 */
	Collection<String> getLobbySpectators();

	/**
	 * Requests a players message.
	 *
	 * @author Tim Prange
	 *
	 * @throws NotLoggedInException  If the player is not logged in.
	 * @throws PlayerDoesNotExistsException  If the player does not exist.
	 * @throws UserIsSpectatorException
	 */
	void requestPlayerMessage() throws PlayerDoesNotExistsException, NotLoggedInException, UserIsSpectatorException;

	/**
	 * Requests a lobby message.
	 *
	 * @author Tim Prange
	 */
	void requestLobbyMessage();

	/**
	 * Returns cards.
	 *
	 * @author Björn Merschmeier
	 * @return cards
	 * @throws NotLoggedInException  If the player is not logged in.
	 * @throws UserIsSpectatorException
	 */
	Collection<Card> getCards() throws NotLoggedInException, UserIsSpectatorException;

	/**
	 * Delete the saved user from the database. Proxy-Method
	 * @param password
	 * @throws NotLoggedInException
	 * @throws PlayerDoesNotExistsException
	 */
	void unregister(String password) throws NotLoggedInException, PlayerDoesNotExistsException;

	/**
	 * Return the dockpile-types the player currently needs in his phase
	 * @return
	 * @throws NotLoggedInException
	 * @throws UserIsSpectatorException
	 */
	Collection<Class<? extends DockPile>> getDockPileTypesForPlayer() throws NotLoggedInException, UserIsSpectatorException;

	boolean isUserInGame() throws NotLoggedInException;

}
