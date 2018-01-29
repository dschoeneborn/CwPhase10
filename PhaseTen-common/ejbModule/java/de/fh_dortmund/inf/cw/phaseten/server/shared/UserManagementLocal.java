package de.fh_dortmund.inf.cw.phaseten.server.shared;

import javax.ejb.Local;

import de.fh_dortmund.inf.cw.phaseten.server.entities.Player;
import de.fh_dortmund.inf.cw.phaseten.server.entities.Spectator;
import de.fh_dortmund.inf.cw.phaseten.server.entities.User;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.NotLoggedInException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.PlayerDoesNotExistsException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.UserDoesNotExistException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.UserIsPlayerException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.UserIsSpectatorException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.UsernameAlreadyTakenException;

/**
 * @author Marc Mettke
 * @author Björn Merschmeier
 */
@Local
public interface UserManagementLocal extends UserManagement {

	/**
	 * Returns the existing player for a user or create a new one
	 * @param user
	 * @return
	 * @throws UserIsSpectatorException
	 */
	Player getOrCreatePlayer(User user) throws UserIsSpectatorException;

	/**
	 * Retunrs the existing spectator for a user or create a new one
	 * @param currentUser
	 * @return
	 * @throws UserIsPlayerException
	 */
	Spectator getOrCreateSpectator(User currentUser) throws UserIsPlayerException;

	/**
	 * Register a new user with given username and password
	 * @param username
	 * @param password
	 * @return
	 * @throws UsernameAlreadyTakenException
	 */
	User register(String username, String password) throws UsernameAlreadyTakenException;

	/**
	 * Login a user with a username and a password
	 * @param username
	 * @param password
	 * @return
	 * @throws UserDoesNotExistException
	 */
	User login(String username, String password) throws UserDoesNotExistException;

	/**
	 * Logs a user out
	 * @param currentUser
	 */
	public void logout(User currentUser);

	/**
	 * Request the sending of a user message
	 */
	void sendUserMessage();

	/**
	 * Request the sending of a user message
	 * @param p
	 * @throws PlayerDoesNotExistsException
	 * @throws NotLoggedInException
	 */
	void requestPlayerMessage(Player p) throws PlayerDoesNotExistsException, NotLoggedInException;

	/**
	 * Delete user from database and all games
	 * @param user
	 * @param password
	 * @throws PlayerDoesNotExistsException
	 */
	void unregister(User user, String password) throws PlayerDoesNotExistsException;
}
