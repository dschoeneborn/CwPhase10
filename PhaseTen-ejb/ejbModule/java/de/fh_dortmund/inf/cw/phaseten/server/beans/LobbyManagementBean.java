package de.fh_dortmund.inf.cw.phaseten.server.beans;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.Message;
import javax.jms.Topic;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import de.fh_dortmund.inf.cw.phaseten.server.entities.Game;
import de.fh_dortmund.inf.cw.phaseten.server.entities.Lobby;
import de.fh_dortmund.inf.cw.phaseten.server.entities.Player;
import de.fh_dortmund.inf.cw.phaseten.server.entities.Spectator;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.NoFreeSlotException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.NotEnoughPlayerException;
import de.fh_dortmund.inf.cw.phaseten.server.shared.GameManagmentLocal;
import de.fh_dortmund.inf.cw.phaseten.server.shared.GameValidationLocal;
import de.fh_dortmund.inf.cw.phaseten.server.shared.LobbyManagmentLocal;
import de.fh_dortmund.inf.cw.phaseten.server.shared.LobbyManagmentRemote;

/**
 * @author Marc Mettke
 * @author Tim Prange
 * @author Björn Merschmeier
 */
@Stateless
public class LobbyManagementBean implements LobbyManagmentRemote, LobbyManagmentLocal {
	@Inject
	private JMSContext jmsContext;
	@Resource(lookup = "java:global/jms/Lobby")
	private Topic lobbyMessageTopic;
	
	@PersistenceContext
	private EntityManager entityManager;

	@EJB
	GameManagmentLocal gameManagment;
	@EJB
	GameValidationLocal gameValidation;

	@Override
	public void requestLobbyMessage() {
		sendLobbyMessage();
	}

	/**
	 *TODO - BM - 03.01.2018 - mir ist nicht ganz klar, was diese Methode machen soll!
	 */
	@Override
	public void sendLobbyMessage() {
		de.fh_dortmund.inf.cw.phaseten.server.messages.Lobby messageLobby = 
				de.fh_dortmund.inf.cw.phaseten.server.messages.Lobby.from(getLatestLobby());
		
		sendLobbyMessage(messageLobby);
	}
	
	/*
	 *TODO - BM - 03.01.2018 - mir ist nicht ganz klar, was diese Methode machen soll!
	 */
	/**
	 * @author Björn Merschmeier
	 */
	@Override
	public void sendLobbyMessage(Lobby lobby) {
		de.fh_dortmund.inf.cw.phaseten.server.messages.Lobby messageLobby = 
				de.fh_dortmund.inf.cw.phaseten.server.messages.Lobby.from(getManagedLobby(lobby));
		
		sendLobbyMessage(messageLobby);
	}

	@Override
	public void enterLobby(Lobby lobby, Player player) throws NoFreeSlotException {
		if (gameValidation.isLobbyFull(lobby)) {
			throw new NoFreeSlotException();
		}
		else
		{
			lobby.addPlayer(player);
			sendLobbyMessage();
		}
	}
	
	@Override
	public void enterOrCreateNewLobby(Player player) {
		Lobby lobby = getLatestLobby();
		
		if(lobby == null || gameValidation.isLobbyFull(lobby))
		{
			lobby = startNewLobby(player);
			sendLobbyMessage(lobby);
		}
		else
		{
			lobby.addPlayer(player);
		}
		
		sendLobbyMessage(lobby);
	}

	@Override
	public void enterLobby(Lobby lobby, Spectator spectator) {
		getManagedLobby(lobby).addSpectator(spectator);
		sendLobbyMessage();
	}
	
	@Override
	public void leaveLobby(Player player)
	{
		Lobby lobby = getLobbyByPlayer(player);
		
		lobby.removePlayer(player);
		
		if(lobby.getNumberOfPlayers() <= 0)
		{
			entityManager.remove(lobby);
			entityManager.flush();
		}
	}
	
	/**
	 * The spectator leaves his lobby (not checked if spectator is in lobby)
	 * @author Björn Merschmeier
	 * @param Spectator spectator is the spectator to delete
	 */
	@Override
	public void leaveLobby(Spectator spectator)
	{
		Lobby lobby = getLobbyBySpectator(spectator);
		
		lobby.removeSpectator(spectator);
	}

	/**
	 * @author Björn Merschmeier
	 */
	@Override
	public void startGame(Player player) throws NotEnoughPlayerException {
		Lobby lobby = getLobbyByPlayer(player);
		
		if (!gameValidation.hasEnoughPlayers(lobby)) {
			throw new NotEnoughPlayerException();
		}
		
		Game game = new Game(lobby.getPlayers(), lobby.getSpectators());
		
		entityManager.persist(game);
		gameManagment.startGame(game);
		entityManager.flush();
		
		entityManager.remove(lobby);
	}

	/**
	 * Deletes empty lobbies which are left or no longer used
	 * @author Björn Merschmeier
	 */
	@Override
	public void deleteEmptyLobbies()
	{
		//TODO - BM - 03.01.2018 - Hier müssen noch die "waisen" entfernt werden, d.h. die Lobbies, die verlassen wurden,
		//aber aus irgendwelchen Gründen noch nicht gelöscht wurden
	}

	private void sendLobbyMessage(de.fh_dortmund.inf.cw.phaseten.server.messages.Lobby lobby) {
		Message message = jmsContext.createObjectMessage(lobby);
		jmsContext.createProducer().send(lobbyMessageTopic, message);
	}

	/**
	 * Gets the latest Lobby from DB in which the player is present
	 *
	 * @author Tim Prange
	 * @author Björn Merschmeier
	 * @param Player player
	 * @return Lobby
	 */
	private Lobby getLobbyByPlayer(Player player) {
		Query namedQuery = entityManager.createNamedQuery("lobby.selectLobbyByUserId");
		namedQuery.setParameter("playerId", player.getId());
		return (Lobby)namedQuery.getSingleResult();
	}

	/**
	 * Returns the lobby in which the spectator is present
	 * @author Björn Merschmeier
	 * @param Spectator spectator
	 */
	private Lobby getLobbyBySpectator(Spectator spectator) {
		Query namedQuery = entityManager.createNamedQuery("lobby.selectLobbyBySpectatorId");
		namedQuery.setParameter("spectatorId", spectator.getId());
		return (Lobby)namedQuery.getSingleResult();
	}

	/**
	 * Returns the latest lobby from the database
	 * @author Björn Merschmeier
	 */
	private Lobby getLatestLobby()
	{
		Query namedQuery = entityManager.createNamedQuery("lobby.selectLatest");
		
		try
		{
			return (Lobby)namedQuery.getSingleResult();
		}
		catch(NoResultException e)
		{
			return null;
		}
	}

	/**
	 * Returns the Managed Entity to the given Lobby
	 * TODO - NOT SURE IF NEEDED
	 * @author Björn Merschmeier
	 */
	private Lobby getManagedLobby(Lobby lobby)
	{
		Lobby managedLobby = entityManager.find(Lobby.class, lobby.getId());
		
		return managedLobby;
	}

	/**
	 * Starts a new Lobby
	 * @author Björn Merschmeier
	 */
	private Lobby startNewLobby(Player player) {
		Lobby l = new Lobby();
		
		l.addPlayer(player);
		
		entityManager.persist(l);
		entityManager.flush();
		
		return l;
	}
}
