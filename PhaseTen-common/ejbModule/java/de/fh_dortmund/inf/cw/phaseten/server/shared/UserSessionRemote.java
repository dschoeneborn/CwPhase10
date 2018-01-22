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
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.NotEnoughPlayerException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.NotLoggedInException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.PlayerDoesNotExistsException;
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
	 */
	void enterLobbyAsPlayer() throws NoFreeSlotException, PlayerDoesNotExistsException, NotLoggedInException, InsufficientCoinSupplyException;

	/**
	 * Enters lobby as a spectator
	 * 
	 * @throws NotLoggedInException  If the player is not logged in.
	 *
	 * @author Tim Prange
	 */
	void enterLobbyAsSpectator() throws NotLoggedInException;

	void addAI() throws NoFreeSlotException;

	/**
	 * Starts the game.
	 * 
	 * @throws PlayerDoesNotExistsException  If the player does not exist.
	 * @throws NotLoggedInException  If the player is not logged in.
	 * @throws NotEnoughPlayerException  If there are not enough players in the lobby.
	 *
	 * @author Tim Prange
	 */
	void startGame() throws NotEnoughPlayerException, PlayerDoesNotExistsException, NotLoggedInException;

	/**
	 * Takes a card from the pull stack.
	 * 
	 * @throws MoveNotValidException  If the move is against the rules.
	 * @throws NotLoggedInException  If the player is not logged in.
	 * @throws GameNotInitializedException  If the game is not initialized yet.
	 *
	 *
	 * @author Tim Prange
	 */
	void takeCardFromPullstack() throws MoveNotValidException, NotLoggedInException, GameNotInitializedException;

	/**
	 * Takes a card from the lifo stack.
	 * 
	 * @throws MoveNotValidException  If the move is against the rules.
	 * @throws NotLoggedInException  If the player is not logged in.
	 * @throws GameNotInitializedException  If the game is not initialized yet.
	 *
	 * @author Tim Prange
	 */
	void takeCardFromLiFoStack() throws MoveNotValidException, NotLoggedInException, GameNotInitializedException;

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
	 * 
	 */
	void addToPileOnTable(long cardId, long dockPileId, boolean tryToAttachToFront)
			throws MoveNotValidException, NotLoggedInException, GameNotInitializedException;

	/**
	 * Lays a collection of cards as a Phase.
	 *
	 * @author Tim Prange
	 * @param cards
	 * 
	 * @throws MoveNotValidException  If the move is against the rules.
	 * @throws NotLoggedInException  If the player is not logged in.
	 * @throws GameNotInitializedException  If the game is not initialized yet.
	 * 
	 */
	void layPhaseToTable(Collection<DockPile> cards)
			throws MoveNotValidException, NotLoggedInException, GameNotInitializedException;

	/**
	 * Lays a card on the lifo stack.
	 *
	 * @author Tim Prange
	 * @param cardId
	 * 
	 * @throws MoveNotValidException  If the move is against the rules.
	 * @throws NotLoggedInException  If the player is not logged in.
	 * @throws GameNotInitializedException  If the game is not initialized yet.
	 */
	void layCardToLiFoStack(long cardId) throws MoveNotValidException, NotLoggedInException, GameNotInitializedException;

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
	 */
	void laySkipCardForPlayer(long destinationPlayerId, long cardId) throws MoveNotValidException, NotLoggedInException,
	PlayerDoesNotExistsException, GameNotInitializedException;

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
	 */
	boolean playerIsInGame() throws NotLoggedInException;

	/**
	 * Requests a game message.
	 *
	 * @author Tim Prange
	 * @throws NotLoggedInException  If the player is not logged in.
	 * @throws GameNotInitializedException  If the game is not initialized yet.
	 * @throws PlayerDoesNotExistsException  If the player does not exist.
	 */
	void requestGameMessage() throws PlayerDoesNotExistsException, NotLoggedInException, GameNotInitializedException;

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
	 */
	void requestPlayerMessage() throws PlayerDoesNotExistsException, NotLoggedInException;

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
	 */
	Collection<Card> getCards() throws NotLoggedInException;

	void unregister(String password) throws NotLoggedInException, PlayerDoesNotExistsException;

	Collection<Class<? extends DockPile>> getDockPileTypesForPlayer() throws NotLoggedInException;

}
