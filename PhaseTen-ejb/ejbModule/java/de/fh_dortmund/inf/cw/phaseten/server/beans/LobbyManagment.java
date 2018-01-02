package de.fh_dortmund.inf.cw.phaseten.server.beans;

import java.util.Collection;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.Message;
import javax.jms.Topic;

import de.fh_dortmund.inf.cw.phaseten.server.entities.Game;
import de.fh_dortmund.inf.cw.phaseten.server.entities.Player;
import de.fh_dortmund.inf.cw.phaseten.server.entities.Spectator;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.NoFreeSlotException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.NotEnoughPlayerException;
import de.fh_dortmund.inf.cw.phaseten.server.messages.Lobby;
import de.fh_dortmund.inf.cw.phaseten.server.shared.GameManagmentLocal;
import de.fh_dortmund.inf.cw.phaseten.server.shared.GameValidationLocal;
import de.fh_dortmund.inf.cw.phaseten.server.shared.LobbyManagmentLocal;
import de.fh_dortmund.inf.cw.phaseten.server.shared.LobbyManagmentRemote;

/**
 * @author Marc Mettke
 * @author Tim Prange
 */
@Stateless
public class LobbyManagment implements LobbyManagmentRemote, LobbyManagmentLocal {
	@Inject
	private JMSContext jmsContext;
	@Resource(lookup = "java:global/jms/Lobby")
	private Topic lobbyMessageTopic;

	@EJB
	GameManagmentLocal gameManagment;
	@EJB
	GameValidationLocal gameValidation;

	@Override
	public void requestLobbyMessage() {
		sendLobbyMessage();
	}

	@Override
	public void sendLobbyMessage() {

		// TODO Entfernen der Phase, wenn Player-Message entsprechend angepasst
		sendLobbyMessage(Lobby.from(getLatestLobby(), de.fh_dortmund.inf.cw.phaseten.server.messages.Player
				.from((Collection<Player>) (Collection<?>) getLatestLobby().getSpectators(), 1), 0));
	}

	@Override
	public void enterAsPlayer() throws NoFreeSlotException {
		de.fh_dortmund.inf.cw.phaseten.server.entities.Lobby lobby = getLatestLobby();
		if (gameValidation.cantHaveMorePlayers(lobby.getPlayers())) {
			throw new NoFreeSlotException();
		}
		// TODO: Get the actual player who wants to enter
		lobby.addPlayer(new Player("TEST"));
		sendLobbyMessage();
	}

	@Override
	public void enterAsSpectator() {
		// TODO: Get the acutal spectator who wants to enter
		getLatestLobby().addSpectator(new Spectator(null));
		sendLobbyMessage();
	}

	@Override
	public void startGame() throws NotEnoughPlayerException {
		if (!gameValidation.hasEnoughPlayers(getLatestLobby().getPlayers())) {
			throw new NotEnoughPlayerException();
		}
		try {
			this.gameManagment.startGame(new Game(getLatestLobby().getPlayers(), getLatestLobby().getSpectators()));
		}
		catch (NoFreeSlotException e) {
			// TODO Wirklich notwendig? Durch Prüfung bei Hinzufügen der Spieler kann hier
			// nichts passieren
			e.printStackTrace();
		}
	}

	private void sendLobbyMessage(Lobby lobby) {
		Message message = jmsContext.createObjectMessage(lobby);
		jmsContext.createProducer().send(lobbyMessageTopic, message);
	}

	/**
	 * gets the latest Lobby from DB
	 *
	 * @author Tim Prange
	 * @return Lobby
	 */
	private de.fh_dortmund.inf.cw.phaseten.server.entities.Lobby getLatestLobby() {
		// TODO get the latest Lobby from DB

		return new de.fh_dortmund.inf.cw.phaseten.server.entities.Lobby();
	}
}
