package de.fh_dortmund.inf.cw.phaseten.server.beans;

import java.util.ArrayList;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.Message;
import javax.jms.Topic;

import de.fh_dortmund.inf.cw.phaseten.server.entities.LiFoStack;
import de.fh_dortmund.inf.cw.phaseten.server.entities.PullStack;
import de.fh_dortmund.inf.cw.phaseten.server.messages.Game;
import de.fh_dortmund.inf.cw.phaseten.server.shared.GameManagmentLocal;
import de.fh_dortmund.inf.cw.phaseten.server.shared.GameManagmentRemote;

/**
 * @author Marc Mettke
 */
@Stateless
public class GameManagment implements GameManagmentRemote, GameManagmentLocal {
	@Inject
	private JMSContext jmsContext;
	@Resource(lookup = "java:global/jms/Game")
	private Topic gameMessageTopic;
	
	@Override
	public void requestGameMessage() {
		sendGameMessage(new Game(
			new ArrayList<>(), 
			new ArrayList<>(), 
			new PullStack(), 
			new LiFoStack(), 
			new ArrayList<>()
		));
	}

	private void sendGameMessage(Game game) {
		Message message = jmsContext.createObjectMessage(game);
		jmsContext.createProducer().send(
			gameMessageTopic,
			message
		);
	}
}
