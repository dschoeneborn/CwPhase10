package de.fh_dortmund.inf.cw.phaseten.server.beans;

import java.util.ArrayList;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.Message;
import javax.jms.Topic;

import de.fh_dortmund.inf.cw.phaseten.server.messages.Lobby;
import de.fh_dortmund.inf.cw.phaseten.server.shared.LobbyManagmentLocal;
import de.fh_dortmund.inf.cw.phaseten.server.shared.LobbyManagmentRemote;

/**
 * @author Marc Mettke
 */
@Stateless
public class LobbyManagment implements LobbyManagmentRemote, LobbyManagmentLocal {
	@Inject
	private JMSContext jmsContext;
	@Resource(lookup = "java:global/jms/Lobby")
	private Topic lobbyMessageTopic;
	
	@Override
	public void requestLobbyMessage() {
		sendLobbyMessage(new Lobby(
			new ArrayList<>(),
			new ArrayList<>()
		));
	}

	private void sendLobbyMessage(Lobby lobby) {
		Message message = jmsContext.createObjectMessage(lobby);
		jmsContext.createProducer().send(
			lobbyMessageTopic,
			message
		);
	}
}
