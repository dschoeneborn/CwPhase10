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

	public void login(String userName, String password) throws UserDoesNotExistException;
	public void register(String userName, String password) throws UsernameAlreadyTakenException;
	public void logout();
	User getUser();
	Player getOrCreatePlayer() throws NotLoggedInException;
	Spectator getOrCreateSpectator() throws NotLoggedInException;
}
