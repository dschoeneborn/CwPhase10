/**
 * 
 */
package de.fh_dortmund.inf.cw.phaseten.server.beans;

import javax.ejb.EJB;
import javax.ejb.Stateful;

import de.fh_dortmund.inf.cw.phaseten.server.exceptions.PasswordIncorrectException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.UserDoesNotExistException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.UsernameAlreadyTakenException;
import de.fh_dortmund.inf.cw.phaseten.server.shared.PlayerManagmentLocal;
import de.fh_dortmund.inf.cw.phaseten.server.shared.UserSessionLocal;
import de.fh_dortmund.inf.cw.phaseten.server.shared.UserSessionRemote;

/**
 * @author Dennis Schöneborn
 * @author Marc Mettke
 */
@Stateful
public class UserSessionBean implements UserSessionRemote, UserSessionLocal {
	private String currentUserName = null;
	@EJB
	private PlayerManagmentLocal userManagement;
	
	@Override
	public void login(String userName, String password) throws UserDoesNotExistException, PasswordIncorrectException {
		userManagement.login(userName, password);
		currentUserName = userName;
	}

	@Override
	public void register(String userName, String password) throws UsernameAlreadyTakenException {
		userManagement.register(userName, password);
	}

	@Override
	public void logout() {
		currentUserName = null;
	}

	@Override
	public void requestPlayerMessage() {
		userManagement.requestPlayerMessage();
	}
	
}
