package de.fh_dortmund.inf.cw.phaseten.server.beans;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.Message;
import javax.jms.Topic;

import de.fh_dortmund.inf.cw.phaseten.server.entities.PlayerPile;
import de.fh_dortmund.inf.cw.phaseten.server.messages.CurrentPlayer;
import de.fh_dortmund.inf.cw.phaseten.server.shared.PlayerManagmentLocal;
import de.fh_dortmund.inf.cw.phaseten.server.shared.PlayerManagmentRemote;

/**
 * @author Marc Mettke
 */
@Stateless
public class PlayerManagment implements PlayerManagmentRemote, PlayerManagmentLocal {
	@Inject
	private JMSContext jmsContext;
	@Resource(lookup = "java:global/jms/CurrentPlayer")
	private Topic playerMessageTopic;
	
	@Override
	public void requestPlayerMessage() {
		sendPlayerMessage(new CurrentPlayer(
			"testPlayer",
			0,
			new PlayerPile(),
			"Waiting",
			500
		));
	}

	private void sendPlayerMessage(CurrentPlayer player) {
		Message message = jmsContext.createObjectMessage(player);
		jmsContext.createProducer().send(
			playerMessageTopic,
			message
		);
	}
}
