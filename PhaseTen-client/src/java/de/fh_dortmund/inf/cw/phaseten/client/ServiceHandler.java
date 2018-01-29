package de.fh_dortmund.inf.cw.phaseten.client;

import java.util.Collection;
import java.util.Observer;

import javax.jms.MessageListener;

import de.fh_dortmund.inf.cw.phaseten.server.entities.Card;
import de.fh_dortmund.inf.cw.phaseten.server.entities.DockPile;
import de.fh_dortmund.inf.cw.phaseten.server.entities.User;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.GameNotInitializedException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.InsufficientCoinSupplyException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.MoveNotValidException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.NoFreeSlotException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.NotEnoughPlayersException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.NotLoggedInException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.PlayerDoesNotExistsException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.UserDoesNotExistException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.UserIsPlayerException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.UserIsSpectatorException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.UsernameAlreadyTakenException;
import de.fh_dortmund.inf.cw.phaseten.server.messages.PlayerGuiData;

/**
 * @author Marc Mettke
 * @author Björn Merschmeier
 */
public interface ServiceHandler extends MessageListener {

	void requestPlayerMessage() throws PlayerDoesNotExistsException, NotLoggedInException, UserIsSpectatorException;

	void requestLobbyMessage();

	void enterLobbyAsPlayer() throws NoFreeSlotException, PlayerDoesNotExistsException, NotLoggedInException, InsufficientCoinSupplyException, UserIsSpectatorException;

	void enterLobbyAsSpectator() throws NotLoggedInException, UserIsPlayerException;

	void addAI() throws NoFreeSlotException;

	void startGame() throws NotEnoughPlayersException, PlayerDoesNotExistsException, NotLoggedInException, UserIsSpectatorException;

	void requestGameMessage() throws PlayerDoesNotExistsException, NotLoggedInException, GameNotInitializedException;

	void takeCardFromPullstack() throws MoveNotValidException, NotLoggedInException, GameNotInitializedException, UserIsSpectatorException;

	void takeCardFromLiFoStack() throws MoveNotValidException, NotLoggedInException, GameNotInitializedException, UserIsSpectatorException;

	void layPhaseToTable(Collection<DockPile> cards)
			throws MoveNotValidException, NotLoggedInException, GameNotInitializedException, UserIsSpectatorException;

	void laySkipCardForPlayer(long destinationPlayerId, long cardId) throws MoveNotValidException, NotLoggedInException,
	PlayerDoesNotExistsException, GameNotInitializedException, UserIsSpectatorException;

	void register(String username, String password) throws UsernameAlreadyTakenException;

	void login(String username, String password) throws UserDoesNotExistException;

	void addObserver(Observer observer);

	void deleteObserver(Observer observer);

	void logout() throws NotLoggedInException;

	void exitLobby() throws NotLoggedInException;

	boolean playerIsInGame() throws NotLoggedInException, UserIsSpectatorException;

	Collection<PlayerGuiData> getLobbyPlayers();

	Collection<String> getLobbySpectators();

	User getUser() throws NotLoggedInException;

	Collection<Card> getCards() throws NotLoggedInException, UserIsSpectatorException;

	void addToPileOnTable(long cardId, long dockPileId, boolean tryToAttachFront)
			throws MoveNotValidException, NotLoggedInException, GameNotInitializedException, UserIsSpectatorException;

	void layCardToLiFoStack(long cardId)
			throws MoveNotValidException, NotLoggedInException, GameNotInitializedException, UserIsSpectatorException;

	void unregister(String password) throws NotLoggedInException, PlayerDoesNotExistsException;

	Collection<Class<? extends DockPile>> getDockPileTypesForPlayer() throws NotLoggedInException, UserIsSpectatorException;

	boolean userIsInGame() throws NotLoggedInException;
}
