package de.fh_dortmund.inf.cw.phaseten.server.beans;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import de.fh_dortmund.inf.cw.phaseten.server.entities.User;
import de.fh_dortmund.inf.cw.phaseten.server.shared.GameManagementLocal;

/**
 * Message-Driven Bean implementation class for: GameMessageBean
 * 
 * @author Sven Krefeld
 */
@MessageDriven(mappedName = "java:global/jms/GameQueue", activationConfig = {
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue") })
public class GameMessageBean implements MessageListener {

	@EJB
	private GameManagementLocal gameManagement;	

	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * @see MessageListener#onMessage(Message)
	 */
	public void onMessage(Message message) {
		try {
			long userId = message.getLongProperty("USER");
			User u = entityManager.find(User.class, userId);

			if (u.getPlayer() == null) {
			        Thread.sleep(3000);
			}

			if (u.getPlayer() != null) {
			        this.gameManagement.requestGameMessage(u.getPlayer());
			}
		} catch (Exception e) {
			System.err.println("Error while game message request: " + e.getMessage());
		}
	}

}
