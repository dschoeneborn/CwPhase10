package de.fh_dortmund.inf.cw.phaseten.server.beans;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.Message;
import javax.jms.Topic;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import de.fh_dortmund.inf.cw.phaseten.server.entities.User;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.UserDoesNotExistException;
import de.fh_dortmund.inf.cw.phaseten.server.shared.CoinManagementLocal;
import de.fh_dortmund.inf.cw.phaseten.server.shared.CoinManagementRemote;

/**
 * @author Robin Harbecke
 * @author Björn Merschmeier
 *
 */
@Stateless
public class CoinManagementBean implements CoinManagementRemote, CoinManagementLocal {

	@Inject
	private JMSContext jmsContext;

	@Resource(lookup = "java:global/jms/CurrentPlayer")
	private Topic playerMessageTopic;

	@PersistenceContext
	private EntityManager em;

	@Override
	public void increaseCoins(String userName, int coins) throws UserDoesNotExistException {
		if (userName != null) {
			User user = getUser(userName);

			user.increaseCoins(coins);
			em.merge(user);
			em.flush();

			sendPlayerMessage();
		}

	}

	@Override
	public void decreaseCoins(String userName, int coins) throws UserDoesNotExistException {
		if (userName != null) {
			User user = getUser(userName);

			user.decreaseCoins(coins);
			em.merge(user);
			em.flush();

			sendPlayerMessage();
		}
	}

	@Override
	public void setCoins(String userName, int coins) throws UserDoesNotExistException {
		if (userName != null) {
			User user = getUser(userName);

			user.setCoins(coins);
			em.merge(user);
			em.flush();

			sendPlayerMessage();
		}
	}

	private User getUser(String userName) throws UserDoesNotExistException {
		User user = em.createNamedQuery("User.findByName", User.class).setParameter("name", userName).getSingleResult();

		if (user == null) {
			throw new UserDoesNotExistException();
		}
		return user;
	}

	private void sendPlayerMessage()
	{
		Message message = jmsContext.createObjectMessage();
		jmsContext.createProducer().send(playerMessageTopic, message);
	}
}
