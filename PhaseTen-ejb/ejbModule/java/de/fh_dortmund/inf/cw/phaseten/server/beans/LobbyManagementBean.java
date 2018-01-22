package de.fh_dortmund.inf.cw.phaseten.server.beans;

import java.util.ArrayList;
import java.util.Collection;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.Message;
import javax.jms.Topic;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import de.fh_dortmund.inf.cw.phaseten.server.entities.Lobby;
import de.fh_dortmund.inf.cw.phaseten.server.entities.Player;
import de.fh_dortmund.inf.cw.phaseten.server.entities.Spectator;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.NoFreeSlotException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.NotEnoughPlayersException;
import de.fh_dortmund.inf.cw.phaseten.server.messages.PlayerGuiData;
import de.fh_dortmund.inf.cw.phaseten.server.shared.GameManagementLocal;
import de.fh_dortmund.inf.cw.phaseten.server.shared.GameValidationLocal;
import de.fh_dortmund.inf.cw.phaseten.server.shared.LobbyManagementLocal;

/**
 * @author Marc Mettke
 * @author Tim Prange
 * @author Björn Merschmeier
 */
@Stateless
public class LobbyManagementBean implements LobbyManagementLocal {
	@Inject
	private JMSContext jmsContext;
	@Resource(lookup = "java:global/jms/Lobby")
	private Topic lobbyMessageTopic;

	@PersistenceContext
	private EntityManager entityManager;

	@EJB
	GameManagementLocal gameManagment;
	@EJB
	GameValidationLocal gameValidation;

	@Override
	public void requestLobbyMessage() {
		sendLobbyMessage();
	}

	/**
	 * @author Björn Merschmeier
	 */
	@Override
	public void sendLobbyMessage() {
		Message message = jmsContext.createObjectMessage();
		jmsContext.createProducer().send(lobbyMessageTopic, message);
	}

	/**
	 * @author Björn Merschmeier
	 */
	@Override
	public void enterLobby(Player player) throws NoFreeSlotException {
		Lobby l = getOrCreateLobby();

		if (l.isFull()) {
			throw new NoFreeSlotException();
		}

		l.addPlayer(player);

		sendLobbyMessage();
	}

	@Override
	public void leaveLobby(Player player) {
		Lobby lobby = getOrCreateLobby();

		lobby.removePlayer(player);

		if (lobby.getNumberOfPlayers() <= 0) {
			entityManager.remove(lobby);
			entityManager.flush();
		}
	}

	/**
	 * @author Björn Merschmeier
	 */
	@Override
	public void enterLobby(Spectator spectator) {
		Lobby l = getOrCreateLobby();

		l.addSpectator(spectator);

		entityManager.merge(l);

		sendLobbyMessage();
	}

	/**
	 * The spectator leaves his lobby (not checked if spectator is in lobby)
	 *
	 * @author Björn Merschmeier
	 * @param Spectator spectator is the spectator to delete
	 */
	@Override
	public void leaveLobby(Spectator spectator) {
		Lobby lobby = getOrCreateLobby();

		lobby.removeSpectator(spectator);
	}

	/**
	 * @author Björn Merschmeier
	 */
	@Override
	public void startGame(Player player) throws NotEnoughPlayersException {
		Lobby lobby = getOrCreateLobby();

		if (!gameValidation.hasEnoughPlayers(lobby)) {
			throw new NotEnoughPlayersException();
		}

		gameManagment.startGame(lobby.getPlayers(), lobby.getSpectators());
		
		lobby.preRemove();

		entityManager.remove(lobby);
	}

	@Override
	public Collection<PlayerGuiData> getPlayersForGui() {
		return PlayerGuiData.from(getOrCreateLobby().getPlayers());
	}

	@Override
	public Collection<String> getSpectatorNamesForGui() {
		ArrayList<String> result = new ArrayList<>();

		for (Spectator s : getOrCreateLobby().getSpectators()) {
			result.add(s.getName());
		}

		return result;
	}

	/**
	 * Returns the latest lobby from the database
	 *
	 * @author Björn Merschmeier
	 */
	private Lobby getOrCreateLobby() {
		Query namedQuery = entityManager.createNamedQuery("lobby.selectLatest");

		try {
			namedQuery.setLockMode(LockModeType.PESSIMISTIC_READ);
			return (Lobby) namedQuery.getSingleResult();
		}
		catch (NoResultException e) {
			Lobby l = new Lobby();
			entityManager.persist(l);
			entityManager.lock(l, LockModeType.PESSIMISTIC_READ);
			entityManager.flush();

			return l;
		}
	}

	@Override
	public void addAI() throws NoFreeSlotException {
		Lobby l = getOrCreateLobby();

		if (l.isFull()) {
			throw new NoFreeSlotException();
		}
		Player player = new Player("AI-" + System.currentTimeMillis());
		player = entityManager.merge(player);
		player.setIsAI(true);
		l.addPlayer(player);

		sendLobbyMessage();
	}
}
