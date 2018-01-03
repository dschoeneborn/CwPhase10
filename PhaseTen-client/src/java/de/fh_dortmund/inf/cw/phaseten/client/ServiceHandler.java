package de.fh_dortmund.inf.cw.phaseten.client;

import java.util.Collection;

import javax.jms.MessageListener;

import de.fh_dortmund.inf.cw.phaseten.server.entities.Card;
import de.fh_dortmund.inf.cw.phaseten.server.entities.DockPile;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.MoveNotValidException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.NoFreeSlotException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.NotEnoughPlayerException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.NotLoggedInException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.PlayerAlreadyExistentException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.PlayerDoesNotExistsException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.UserDoesNotExistException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.UsernameAlreadyTakenException;
import de.fh_dortmund.inf.cw.phaseten.server.shared.StubRemote;

/**
 * @author Marc Mettke
 * @author Björn Merschmeier
 */
public interface ServiceHandler extends StubRemote,
										MessageListener {

	void requestPlayerMessage() throws PlayerDoesNotExistsException;

	void requestLobbyMessage();

	void enterAnyLobbyAsPlayer() throws NoFreeSlotException, PlayerDoesNotExistsException;
	
	void enterLobbyAsPlayer(long lobbyId) throws NoFreeSlotException, PlayerDoesNotExistsException;

	void startGame() throws NotEnoughPlayerException, PlayerDoesNotExistsException;

	void requestGameMessage() throws PlayerDoesNotExistsException;

	void enterAnyLobbyAsNewPlayer(String playername) throws NoFreeSlotException, PlayerDoesNotExistsException, NotLoggedInException, PlayerAlreadyExistentException;

	void enterLobbyAsSpectator(long lobbyId) throws NotLoggedInException, NoFreeSlotException;

	void enterLobbyAsNewPlayer(long lobbyId, String playerName) throws NoFreeSlotException, NotLoggedInException, PlayerAlreadyExistentException;

	void takeCardFromPullstack() throws MoveNotValidException, PlayerDoesNotExistsException;

	void takeCardFromLiFoStack() throws MoveNotValidException, PlayerDoesNotExistsException;

	void addToPileOnTable(Card card, DockPile dockPile) throws MoveNotValidException, PlayerDoesNotExistsException;

	void layPhaseToTable(Collection<DockPile> cards) throws MoveNotValidException, PlayerDoesNotExistsException;

	void layCardToLiFoStack(Card card) throws MoveNotValidException, PlayerDoesNotExistsException;

	void laySkipCardForPlayer(long destinationPlayerId, Card card) throws MoveNotValidException, PlayerDoesNotExistsException;

	void register(String username, String password) throws UsernameAlreadyTakenException;

	void login(String username, String password) throws UserDoesNotExistException;
}
