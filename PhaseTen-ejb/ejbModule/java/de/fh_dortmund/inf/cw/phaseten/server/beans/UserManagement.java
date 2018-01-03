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

import de.fh_dortmund.inf.cw.phaseten.server.entities.PlayerPile;
import de.fh_dortmund.inf.cw.phaseten.server.entities.User;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.UserDoesNotExistException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.UsernameAlreadyTakenException;
import de.fh_dortmund.inf.cw.phaseten.server.messages.CurrentPlayer;
import de.fh_dortmund.inf.cw.phaseten.server.shared.LobbyManagmentLocal;
import de.fh_dortmund.inf.cw.phaseten.server.shared.UserManagementLocal;
import de.fh_dortmund.inf.cw.phaseten.server.shared.UserManagementRemote;

/**
 * @author Marc Mettke
 * @author Dennis Schöneborn
 */
@Stateless
public class UserManagement implements UserManagementRemote, UserManagementLocal {
	@Inject
	private JMSContext jmsContext;
	
	@Resource(lookup = "java:global/jms/CurrentPlayer")
	private Topic playerMessageTopic;

	@EJB
	LobbyManagmentLocal lobbyManagment;

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

		sendPlayerMessage();
		this.lobbyManagment.sendLobbyMessage();
		
		return foundUser;
	}

	@Override
	public User login(String username, String password) throws UserDoesNotExistException
	{
		User user = em.createNamedQuery("User.findByName", User.class).setParameter("name", username).getSingleResult();
		
		if(user == null || !user.getPassword().equals(password))
		{
			throw new UserDoesNotExistException();
		}
		
		onlineUsers.add(user.getLoginName());
		
		sendPlayerMessage();
		this.lobbyManagment.sendLobbyMessage();
		
		return user;
	}

	private void sendPlayerMessage(CurrentPlayer player) {
		Message message = jmsContext.createObjectMessage(player);
		jmsContext.createProducer().send(playerMessageTopic, message);
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
