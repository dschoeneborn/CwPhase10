package de.fh_dortmund.inf.cw.phaseten.server.beans;

import static java.util.Collections.synchronizedList;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.Message;
import javax.jms.Topic;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.xml.bind.DatatypeConverter;

import de.fh_dortmund.inf.cw.phaseten.server.entities.Player;
import de.fh_dortmund.inf.cw.phaseten.server.entities.User;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.UserDoesNotExistException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.UsernameAlreadyTakenException;
import de.fh_dortmund.inf.cw.phaseten.server.shared.LobbyManagementLocal;
import de.fh_dortmund.inf.cw.phaseten.server.shared.UserManagementLocal;
import de.fh_dortmund.inf.cw.phaseten.server.shared.UserManagementRemote;

/**
 * @author Marc Mettke
 * @author Dennis Schöneborn
 * @author Björn Merschmeier
 */
@Stateless
public class UserManagementBean implements UserManagementRemote, UserManagementLocal {
	@Inject
	private JMSContext jmsContext;
	
	@Resource(lookup = "java:global/jms/CurrentPlayer")
	private Topic playerMessageTopic;

	@EJB
	LobbyManagementLocal lobbyManagment;

	@PersistenceContext
	private EntityManager em;
	
	private List<String> onlineUsers = synchronizedList(new LinkedList<>());

	@Override
	public void requestPlayerMessage(Player p) {
		sendPlayerMessage(p);
	}

	@Override
	public User register(String username, String password) throws UsernameAlreadyTakenException {
		User foundUser = null;
		User user = new User(username, this.computeHash(password));
		
		try 
		{
			try
			{
				foundUser = em.createNamedQuery("User.findByName", User.class).setParameter("name", username).getSingleResult();
				throw new EntityExistsException();
			}
			catch(NoResultException e)
			{
				em.persist(user);
			}
		}
		catch (EntityExistsException e)
		{
			throw new UsernameAlreadyTakenException();
		}

		try
		{
			foundUser = this.login(username, password);
		}
		catch (UserDoesNotExistException e)
		{
			e.printStackTrace();
		}

		this.lobbyManagment.sendLobbyMessage();
		
		return foundUser;
	}

	@Override
	public User login(String username, String password) throws UserDoesNotExistException
	{
		password = computeHash(password);
		
		User user = em.createNamedQuery("User.findByName", User.class).setParameter("name", username).getSingleResult();
		
		if(user == null || !user.getPassword().equals(password))
		{
			throw new UserDoesNotExistException();
		}
		
		onlineUsers.add(user.getLoginName());
		
		this.lobbyManagment.sendLobbyMessage();
		
		return user;
	}

	@Override
	public void sendPlayerMessage(Player p) {
		Message message = jmsContext.createObjectMessage(p);
		jmsContext.createProducer().send(playerMessageTopic, message);
	}

	@Override
	public void logout(User currentUser) {
		if(currentUser.getPlayer() != null)
		{
			lobbyManagment.leaveLobby(currentUser.getPlayer());
		}
		
		if(currentUser.getSpectator() != null)
		{
			lobbyManagment.leaveLobby(currentUser.getSpectator());
		}
		
		onlineUsers.remove(currentUser.getLoginName());
	}

	private String computeHash(String pw) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest(pw.getBytes());
			return DatatypeConverter.printHexBinary(hash);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}
}
