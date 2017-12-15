package de.fh_dortmund.inf.cw.phaseten.server.beans;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.Message;
import javax.jms.Topic;

import de.fh_dortmund.inf.cw.phaseten.server.entities.PlayerPile;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.UserDoesNotExistException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.UsernameAlreadyTakenException;
import de.fh_dortmund.inf.cw.phaseten.server.messages.CurrentPlayer;
import de.fh_dortmund.inf.cw.phaseten.server.shared.LobbyManagmentLocal;
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

	@EJB 
	LobbyManagmentLocal lobbyManagment;
	
	@Override
	public void requestPlayerMessage() {
		sendPlayerMessage();
	}

	@Override
	public void sendPlayerMessage() {
		sendPlayerMessage(new CurrentPlayer(
			"testPlayer",
			0,
			new PlayerPile(),
			"Waiting",
			500
		));
	}

	@Override
	public void register(String username, String password) throws UsernameAlreadyTakenException {
		// TODO: Register and directly login if username is available
		sendPlayerMessage();
		this.lobbyManagment.sendLobbyMessage();
	}

	@Override
	public void login(String username, String password) throws UserDoesNotExistException {
		// TODO: login if username exists
		sendPlayerMessage();
		this.lobbyManagment.sendLobbyMessage();
	}
	
	private void sendPlayerMessage(CurrentPlayer player) {
		Message message = jmsContext.createObjectMessage(player);
		jmsContext.createProducer().send(
			playerMessageTopic,
			message
		);
	}
}
