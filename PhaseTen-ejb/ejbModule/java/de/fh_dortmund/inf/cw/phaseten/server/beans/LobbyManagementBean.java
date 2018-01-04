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
import de.fh_dortmund.inf.cw.phaseten.server.shared.GameManagementLocal;
import de.fh_dortmund.inf.cw.phaseten.server.shared.GameValidationLocal;
import de.fh_dortmund.inf.cw.phaseten.server.shared.LobbyManagementLocal;
import de.fh_dortmund.inf.cw.phaseten.server.shared.LobbyManagementRemote;

/**
 * @author Marc Mettke
 * @author Tim Prange
 * @author Björn Merschmeier
 */
@Stateless
public class LobbyManagementBean implements LobbyManagementRemote, LobbyManagementLocal {
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
		de.fh_dortmund.inf.cw.phaseten.server.messages.Lobby messageLobby = 
				de.fh_dortmund.inf.cw.phaseten.server.messages.Lobby.from(getOrCreateLobby());
		
		sendLobbyMessage(messageLobby);
	}
	
	/**
	 * @author Björn Merschmeier
	 */
	@Override
	public void enterLobby(Player player) throws NoFreeSlotException
	{
		Lobby l = getOrCreateLobby();
		
		if(l.isFull())
		{
			throw new NoFreeSlotException();
		}
		
		l.addPlayer(player);
		
		entityManager.flush();
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
	 * @author Björn Merschmeier
	 */
	@Override
	public void enterLobby(Spectator spectator)
	{
		Lobby l = getOrCreateLobby();
		
		l.addSpectator(spectator);
		
		entityManager.flush();
		sendLobbyMessage();
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
	private Lobby getOrCreateLobby()
	{
		Query namedQuery = entityManager.createNamedQuery("lobby.selectLatest");
		
		try
		{
			return (Lobby)namedQuery.getSingleResult();
		}
		catch(NoResultException e)
		{
			Lobby l = new Lobby();
			entityManager.persist(l);
			entityManager.flush();
			
			return l;
		}
	}
}
