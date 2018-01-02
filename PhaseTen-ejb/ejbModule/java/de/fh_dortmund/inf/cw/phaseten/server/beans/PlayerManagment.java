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
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.xml.bind.DatatypeConverter;

import de.fh_dortmund.inf.cw.phaseten.server.entities.Card;
import de.fh_dortmund.inf.cw.phaseten.server.entities.CardValue;
import de.fh_dortmund.inf.cw.phaseten.server.entities.Color;
import de.fh_dortmund.inf.cw.phaseten.server.entities.PlayerPile;
import de.fh_dortmund.inf.cw.phaseten.server.entities.User;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.PasswordIncorrectException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.UserDoesNotExistException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.UsernameAlreadyTakenException;
import de.fh_dortmund.inf.cw.phaseten.server.messages.CurrentPlayer;
import de.fh_dortmund.inf.cw.phaseten.server.shared.LobbyManagmentLocal;
import de.fh_dortmund.inf.cw.phaseten.server.shared.PlayerManagmentLocal;

/**
 * @author Marc Mettke
 * @author Dennis Schöneborn
 */
@Stateless
public class PlayerManagment implements PlayerManagmentLocal {
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
	public void requestPlayerMessage() {
		sendPlayerMessage();
	}

	@Override
	public void sendPlayerMessage() {
		PlayerPile playerPile = new PlayerPile();
		playerPile.addCard(new Card(Color.GREEN, CardValue.SEVEN));
		playerPile.addCard(new Card(Color.RED, CardValue.FIVE));
		playerPile.addCard(new Card(Color.BLUE, CardValue.ONE));
		sendPlayerMessage(new CurrentPlayer("testCurrentPlayer", 0, playerPile, "Waiting", 500));
	}

	@Override
	public void register(String username, String password) throws UsernameAlreadyTakenException {
		User user = new User(username, this.computeHash(password));
		//TODO toggle comment till entities are availible
		/*try {
			em.persist(user);
		} catch (EntityExistsException e) {
			throw new UsernameAlreadyTakenException();
		}

		try {
			this.login(username, password);
		} catch (UserDoesNotExistException e) {
			e.printStackTrace();
		}*/

		sendPlayerMessage();
		this.lobbyManagment.sendLobbyMessage();
	}

	@Override
	public void login(String username, String password) throws UserDoesNotExistException, PasswordIncorrectException {
		// TODO: WIP: login if username exists - toggle comment till entities are availible
		/*User user = em.createNamedQuery("User.findByName", User.class).setParameter("name", username).getSingleResult();
		
		if(user == null)
		{
			throw new UserDoesNotExistException();
		}
		
		onlineUsers.add(user.getLoginName());*/
		
		sendPlayerMessage();
		this.lobbyManagment.sendLobbyMessage();
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
