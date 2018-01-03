/**
 * 
 */
package de.fh_dortmund.inf.cw.phaseten.server.beans;

import javax.ejb.EJB;
import javax.ejb.Stateful;

import de.fh_dortmund.inf.cw.phaseten.server.entities.Player;
import de.fh_dortmund.inf.cw.phaseten.server.entities.Spectator;
import de.fh_dortmund.inf.cw.phaseten.server.entities.User;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.NotLoggedInException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.PlayerAlreadyExistentException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.UserDoesNotExistException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.UsernameAlreadyTakenException;
import de.fh_dortmund.inf.cw.phaseten.server.shared.UserManagementLocal;
import de.fh_dortmund.inf.cw.phaseten.server.shared.UserSessionLocal;
import de.fh_dortmund.inf.cw.phaseten.server.shared.UserSessionRemote;

/**
 * @author Dennis Schöneborn
 * @author Björn Merschmeier
 */

@Stateful
public class UserSessionBean implements UserSessionRemote, UserSessionLocal {
	private User currentUser = null;
	
	@EJB
	private UserManagementLocal userManagement;
	
	@Override
	public void login(String userName, String password) throws UserDoesNotExistException {
		currentUser = userManagement.login(userName, password);
	}

	@Override
	public void register(String userName, String password) throws UsernameAlreadyTakenException {
		currentUser = userManagement.register(userName, password);
	}
	
	/**
	 * @author Björn Merschmeier
	 */
	@Override
	public Player getOrCreatePlayer(String name) throws NotLoggedInException, PlayerAlreadyExistentException
	{
		Player foundPlayer = null;
		
		if(currentUser != null)
		{
			if(currentUser.getPlayer() != null)
			{
				foundPlayer = currentUser.getPlayer();
				
				if(!foundPlayer.getName().equals(name))
				{
					throw new PlayerAlreadyExistentException();
				}
			}
			else
			{
				foundPlayer = new Player(name);
				currentUser.setPlayer(foundPlayer);
			}
		}
		else
		{
			throw new NotLoggedInException();
		}
		
		return foundPlayer;
	}

	/**
	 * @author Björn Merschmeier
	 */
	@Override
	public Spectator getOrCreateSpectator() throws NotLoggedInException
	{
		Spectator foundSpectator = null;
		
		if(currentUser != null)
		{
			if(currentUser.getSpectator() != null)
			{
				foundSpectator = currentUser.getSpectator();
			}
			else
			{
				foundSpectator = new Spectator();
			}
		}
		else
		{
			throw new NotLoggedInException();
		}
		
		return foundSpectator;
	}

	/**
	 * @author Björn Merschmeier
	 */
	@Override
	public void logout() {
		userManagement.logout(currentUser);
	}

	/**
	 * @author Björn Merschmeier
	 */
	@Override
	public User getUser() {
		return currentUser;
	}
	
}
