package de.fh_dortmund.inf.cw.phaseten.server.beans;

import java.util.ArrayList;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.Message;
import javax.jms.Topic;

import de.fh_dortmund.inf.cw.phaseten.server.exceptions.NoFreeSlotException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.NotEnoughPlayerException;
import de.fh_dortmund.inf.cw.phaseten.server.messages.Lobby;
import de.fh_dortmund.inf.cw.phaseten.server.shared.GameManagmentLocal;
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
	
	@EJB
	GameManagmentLocal gameManagment;
	
	@Override
	public void requestLobbyMessage() {
		sendLobbyMessage();
	}

	@Override
	public void sendLobbyMessage() {
		sendLobbyMessage(new Lobby(
			new ArrayList<>(),
			new ArrayList<>()
		));
	}

	@Override
	public void enterAsPlayer() throws NoFreeSlotException {
		// TODO: Check if Player is allowed to enter and send updated lobby object
		sendLobbyMessage();
	}

	@Override
	public void enterAsSpectator() {
		// TODO: Add Player as Spectator and send updated lobby object
		sendLobbyMessage();
	}

	@Override
	public void startGame() throws NotEnoughPlayerException {
		// TODO: Check if game can be started.
		// The following must be substituted with a sendGameMessage call
		// inside of GameManagment after the Game has been created
		this.gameManagment.sendGameMessage();		
	}

	private void sendLobbyMessage(Lobby lobby) {
		Message message = jmsContext.createObjectMessage(lobby);
		jmsContext.createProducer().send(
			lobbyMessageTopic,
			message
		);
	}
}
