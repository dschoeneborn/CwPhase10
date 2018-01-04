package de.fh_dortmund.inf.cw.phaseten.client;

import java.util.Collection;
import java.util.Observer;

import javax.jms.MessageListener;

import de.fh_dortmund.inf.cw.phaseten.server.entities.Card;
import de.fh_dortmund.inf.cw.phaseten.server.entities.DockPile;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.MoveNotValidException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.NoFreeSlotException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.NotEnoughPlayerException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.NotLoggedInException;
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

	void requestPlayerMessage() throws PlayerDoesNotExistsException, NotLoggedInException;

	void requestLobbyMessage();

	void enterLobbyAsPlayer() throws NoFreeSlotException, PlayerDoesNotExistsException, NotLoggedInException;
	
	void enterLobbyAsSpectator() throws NotLoggedInException;

	void startGame() throws NotEnoughPlayerException, PlayerDoesNotExistsException, NotLoggedInException;

	void requestGameMessage() throws PlayerDoesNotExistsException, NotLoggedInException;
	
	void takeCardFromPullstack() throws MoveNotValidException, NotLoggedInException;

	void takeCardFromLiFoStack() throws MoveNotValidException, NotLoggedInException;

	void addToPileOnTable(Card card, DockPile dockPile) throws MoveNotValidException, NotLoggedInException;

	void layPhaseToTable(Collection<DockPile> cards) throws MoveNotValidException, NotLoggedInException;

	void layCardToLiFoStack(Card card) throws MoveNotValidException, NotLoggedInException;

	void laySkipCardForPlayer(long destinationPlayerId, Card card) throws MoveNotValidException, NotLoggedInException, PlayerDoesNotExistsException;

	void register(String username, String password) throws UsernameAlreadyTakenException;

	void login(String username, String password) throws UserDoesNotExistException;
	
	void addObserver(Observer observer);
	
	void deleteObserver(Observer observer);
}
