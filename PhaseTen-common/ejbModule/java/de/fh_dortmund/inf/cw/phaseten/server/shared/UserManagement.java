package de.fh_dortmund.inf.cw.phaseten.server.shared;

import de.fh_dortmund.inf.cw.phaseten.server.entities.User;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.UserDoesNotExistException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.UsernameAlreadyTakenException;

/**
 * @author Marc Mettke
 * @author Björn Merschmeier
 */
public interface UserManagement {
	User register(String username, String password) throws UsernameAlreadyTakenException;
	
	User login(String username, String password) throws UserDoesNotExistException;

	public void logout(User currentUser);
}
