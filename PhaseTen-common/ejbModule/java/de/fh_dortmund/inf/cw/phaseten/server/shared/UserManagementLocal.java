package de.fh_dortmund.inf.cw.phaseten.server.shared;

import javax.ejb.Local;

import de.fh_dortmund.inf.cw.phaseten.server.entities.Player;
import de.fh_dortmund.inf.cw.phaseten.server.entities.Spectator;
import de.fh_dortmund.inf.cw.phaseten.server.entities.User;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.UserDoesNotExistException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.UsernameAlreadyTakenException;

/**
 * @author Marc Mettke
 * @author Björn Merschmeier
 */
@Local
public interface UserManagementLocal extends UserManagement {

	Player getOrCreatePlayer(User user);

	Spectator getOrCreateSpectator(User currentUser);
	
	User register(String username, String password) throws UsernameAlreadyTakenException;
	
	User login(String username, String password) throws UserDoesNotExistException;

	public void logout(User currentUser);

	void sendUserMessage();
}
