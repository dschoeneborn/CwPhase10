package de.fh_dortmund.inf.cw.phaseten.client;

import java.util.Collection;
import java.util.Observer;

import javax.jms.MessageListener;

import de.fh_dortmund.inf.cw.phaseten.server.entities.Card;
import de.fh_dortmund.inf.cw.phaseten.server.entities.DockPile;
import de.fh_dortmund.inf.cw.phaseten.server.entities.User;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.GameNotInitializedException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.MoveNotValidException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.NoFreeSlotException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.NotEnoughPlayerException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.NotLoggedInException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.PlayerDoesNotExistsException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.UserDoesNotExistException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.UsernameAlreadyTakenException;
import de.fh_dortmund.inf.cw.phaseten.server.messages.PlayerGuiData;

/**
 * @author Marc Mettke
 * @author Björn Merschmeier
 */
public interface ServiceHandler extends MessageListener {

	void requestPlayerMessage() throws PlayerDoesNotExistsException, NotLoggedInException;

	void requestLobbyMessage();

	void enterLobbyAsPlayer() throws NoFreeSlotException, PlayerDoesNotExistsException, NotLoggedInException;

	void enterLobbyAsSpectator() throws NotLoggedInException;

	void startGame() throws NotEnoughPlayerException, PlayerDoesNotExistsException, NotLoggedInException;

	void requestGameMessage() throws PlayerDoesNotExistsException, NotLoggedInException, GameNotInitializedException;

	void takeCardFromPullstack() throws MoveNotValidException, NotLoggedInException, GameNotInitializedException;

	void takeCardFromLiFoStack() throws MoveNotValidException, NotLoggedInException, GameNotInitializedException;

	void layPhaseToTable(Collection<DockPile> cards)
			throws MoveNotValidException, NotLoggedInException, GameNotInitializedException;

	void laySkipCardForPlayer(long destinationPlayerId, long cardId) throws MoveNotValidException, NotLoggedInException,
			PlayerDoesNotExistsException, GameNotInitializedException;

	void register(String username, String password) throws UsernameAlreadyTakenException;

	void login(String username, String password) throws UserDoesNotExistException;

	void addObserver(Observer observer);

	void deleteObserver(Observer observer);

	void logout() throws NotLoggedInException;

	void exitLobby() throws NotLoggedInException;

	boolean playerIsInGame() throws NotLoggedInException;

	Collection<PlayerGuiData> getLobbyPlayers();

	Collection<String> getLobbySpectators();

	User getUser() throws NotLoggedInException;

	Collection<Card> getCards() throws NotLoggedInException;

	void addToPileOnTable(long cardId, long dockPileId)
			throws MoveNotValidException, NotLoggedInException, GameNotInitializedException;

	void layCardToLiFoStack(long cardId)
			throws MoveNotValidException, NotLoggedInException, GameNotInitializedException;

	void unregister(String password) throws NotLoggedInException, PlayerDoesNotExistsException;
}
