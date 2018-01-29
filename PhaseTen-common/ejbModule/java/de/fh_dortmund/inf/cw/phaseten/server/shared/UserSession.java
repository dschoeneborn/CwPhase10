/**
 *
 */
package de.fh_dortmund.inf.cw.phaseten.server.shared;

import de.fh_dortmund.inf.cw.phaseten.server.entities.Player;
import de.fh_dortmund.inf.cw.phaseten.server.entities.Spectator;
import de.fh_dortmund.inf.cw.phaseten.server.entities.User;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.NotLoggedInException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.UserDoesNotExistException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.UsernameAlreadyTakenException;

/**
 * @author Dennis Schöneborn
 * @author Björn Merschmeier
 */
public interface UserSession {

	/**
	 * Login a user
	 * @param userName
	 * @param password
	 * @throws UserDoesNotExistException
	 */
	public void login(String userName, String password) throws UserDoesNotExistException;

	/**
	 * Register a user
	 * @param userName
	 * @param password
	 * @throws UsernameAlreadyTakenException
	 */
	public void register(String userName, String password) throws UsernameAlreadyTakenException;

	/**
	 * log the saved user out
	 */
	public void logout();

	/**
	 * return the current saved user
	 * @return
	 */
	User getUser();

	/**
	 * Create a new player for the saved user
	 * @return
	 * @throws NotLoggedInException
	 */
	Player getOrCreatePlayer() throws NotLoggedInException;

	/**
	 * Create a spectator for the saved user
	 * @return
	 * @throws NotLoggedInException
	 */
	Spectator getOrCreateSpectator() throws NotLoggedInException;
}
