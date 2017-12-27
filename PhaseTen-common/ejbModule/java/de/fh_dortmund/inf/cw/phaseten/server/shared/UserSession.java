/**
 * 
 */
package de.fh_dortmund.inf.cw.phaseten.server.shared;

import de.fh_dortmund.inf.cw.phaseten.server.exceptions.UserDoesNotExistException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.UsernameAlreadyTakenException;

/**
 * @author Dennis Schöneborn
 *
 */
public interface UserSession {

	public void login(String userName, String password) throws UserDoesNotExistException;
	public void register(String userName, String password) throws UsernameAlreadyTakenException;
	public void logout();
}
