package de.fh_dortmund.inf.cw.phaseten.server.shared;

import de.fh_dortmund.inf.cw.phaseten.server.exceptions.UserDoesNotExistException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.UsernameAlreadyTakenException;

/**
 * @author Marc Mettke
 */
public interface PlayerManagment {
	void register(String username, String password) throws UsernameAlreadyTakenException;
	
	void login(String username, String password) throws UserDoesNotExistException;
}
