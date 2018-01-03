package de.fh_dortmund.inf.cw.phaseten.client;

import java.util.Collection;

import javax.jms.ConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Topic;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import de.fh_dortmund.inf.cw.phaseten.server.entities.Card;
import de.fh_dortmund.inf.cw.phaseten.server.entities.DockPile;
import de.fh_dortmund.inf.cw.phaseten.server.entities.Player;
import de.fh_dortmund.inf.cw.phaseten.server.entities.Spectator;
import de.fh_dortmund.inf.cw.phaseten.server.entities.User;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.MoveNotValidException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.NoFreeSlotException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.NotEnoughPlayerException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.NotLoggedInException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.PlayerAlreadyExistentException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.PlayerDoesNotExistsException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.UserDoesNotExistException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.UsernameAlreadyTakenException;
import de.fh_dortmund.inf.cw.phaseten.server.messages.CurrentPlayer;
import de.fh_dortmund.inf.cw.phaseten.server.messages.Game;
import de.fh_dortmund.inf.cw.phaseten.server.messages.Lobby;
import de.fh_dortmund.inf.cw.phaseten.server.shared.GameManagementRemote;
import de.fh_dortmund.inf.cw.phaseten.server.shared.LobbyManagementRemote;
import de.fh_dortmund.inf.cw.phaseten.server.shared.UserManagementRemote;
import de.fh_dortmund.inf.cw.phaseten.server.shared.UserSessionRemote;
import de.fh_dortmund.inf.cw.phaseten.server.shared.StubRemote;

/**
 * @author Marc Mettke
 * @author Tim Prange
 */
public class ServiceHandlerImpl implements ServiceHandler {
	private static ServiceHandlerImpl instance;

	private Context context;
	private StubRemote stubRemote;
	private UserManagementRemote playerManagmentRemote;
	private LobbyManagementRemote lobbyManagmentRemote;
	private GameManagementRemote gameManagmentRemote;
	private UserSessionRemote userSessionRemote;

	private JMSContext jmsContext;
	private Topic playerMessageTopic;
	private Topic lobbyMessageTopic;
	private Topic gameMessageTopic;
	private JMSConsumer playerConsumer;
	private JMSConsumer lobbyConsumer;
	private JMSConsumer gameConsumer;

	private ServiceHandlerImpl() {
		try {
			context = new InitialContext();
			stubRemote = (StubRemote) context.lookup(
					"java:global/PhaseTen-ear/PhaseTen-ejb/ServerStub!de.fh_dortmund.inf.cw.phaseten.server.shared.StubRemote");
			playerManagmentRemote = (UserManagementRemote) context.lookup(
					"java:global/PhaseTen-ear/PhaseTen-ejb/UserManagementBean!de.fh_dortmund.inf.cw.phaseten.server.shared.UserManagementRemote");
			lobbyManagmentRemote = (LobbyManagementRemote) context.lookup(
					"java:global/PhaseTen-ear/PhaseTen-ejb/LobbyManagementBean!de.fh_dortmund.inf.cw.phaseten.server.shared.LobbyManagementRemote");
			gameManagmentRemote = (GameManagementRemote) context.lookup(
					"java:global/PhaseTen-ear/PhaseTen-ejb/GameManagementBean!de.fh_dortmund.inf.cw.phaseten.server.shared.GameManagementRemote");
			userSessionRemote = (UserSessionRemote) context.lookup(
					"java:global/PhaseTen-ear/PhaseTen-ejb/UserSessionBean!de.fh_dortmund.inf.cw.phaseten.server.shared.UserSessionRemote");
					
			
			ConnectionFactory connectionFactory = (ConnectionFactory) context
					.lookup("java:comp/DefaultJMSConnectionFactory");
			jmsContext = connectionFactory.createContext();

			playerMessageTopic = (Topic) context.lookup("java:global/jms/CurrentPlayer");
			lobbyMessageTopic = (Topic) context.lookup("java:global/jms/Lobby");
			gameMessageTopic = (Topic) context.lookup("java:global/jms/Game");

			this.playerConsumer = jmsContext.createConsumer(playerMessageTopic);
			this.playerConsumer.setMessageListener(this);
			this.lobbyConsumer = jmsContext.createConsumer(lobbyMessageTopic);
			this.lobbyConsumer.setMessageListener(this);
			this.gameConsumer = jmsContext.createConsumer(gameMessageTopic);
			this.gameConsumer.setMessageListener(this);
		}
		catch (NamingException ex) {
			throw new RuntimeException(ex);
		}
	}

	public static ServiceHandlerImpl getInstance() {
		if (instance == null) {
			instance = new ServiceHandlerImpl();
		}
		return instance;
	}

	@Override
	public String helloWorld() {
		return stubRemote.helloWorld();
	}

	@Override
	public void requestPlayerMessage() throws PlayerDoesNotExistsException {		
		this.playerManagmentRemote.requestPlayerMessage(getCurrentPlayer());
	}

	@Override
	public void requestLobbyMessage() {
		this.lobbyManagmentRemote.requestLobbyMessage();
	}

	@Override
	public void requestGameMessage() throws PlayerDoesNotExistsException {
		this.gameManagmentRemote.requestGameMessage(getCurrentPlayer());
	}

	@Override
	public void register(String username, String password) throws UsernameAlreadyTakenException {
		this.playerManagmentRemote.register(username, password);
	}

	@Override
	public void login(String username, String password) throws UserDoesNotExistException {
		this.playerManagmentRemote.login(username, password);
	}

	@Override
	public void enterAnyLobbyAsPlayer() throws NoFreeSlotException, PlayerDoesNotExistsException
	{
		this.lobbyManagmentRemote.enterOrCreateNewLobby(getCurrentPlayer());
	}
	
	@Override
	public void enterAnyLobbyAsNewPlayer(String playername) throws NoFreeSlotException, PlayerDoesNotExistsException, NotLoggedInException, PlayerAlreadyExistentException
	{
		createNewPlayer(playername);
		this.lobbyManagmentRemote.enterOrCreateNewLobby(getCurrentPlayer());
	}
	
	@Override
	public void enterLobbyAsSpectator(long lobbyId) throws NotLoggedInException
	{
		this.lobbyManagmentRemote.enterLobby(lobbyId, getOrCreateCurrentSpectator());
	}
	
	@Override
	public void enterLobbyAsPlayer(long lobbyId) throws NoFreeSlotException, PlayerDoesNotExistsException
	{
		this.lobbyManagmentRemote.enterLobby(lobbyId, getCurrentPlayer());
	}
	
	@Override
	public void enterLobbyAsNewPlayer(long lobbyId, String playerName) throws NoFreeSlotException, NotLoggedInException, PlayerAlreadyExistentException
	{
		this.lobbyManagmentRemote.enterLobby(lobbyId, createNewPlayer(playerName));
	}

	@Override
	public void startGame() throws NotEnoughPlayerException, PlayerDoesNotExistsException {
		this.lobbyManagmentRemote.startGame(getCurrentPlayer());
	}

	@Override
	public void onMessage(Message message) {
		try {
			if (message.getJMSDestination().equals(playerMessageTopic) && message instanceof ObjectMessage) {
				@SuppressWarnings("unused")
				CurrentPlayer currentPlayer = (CurrentPlayer) ((ObjectMessage) message).getObject();
				System.out.println("Received CurrentPlayer Object: " + currentPlayer);
			}
			else if (message.getJMSDestination().equals(lobbyMessageTopic) && message instanceof ObjectMessage) {
				@SuppressWarnings("unused")
				Lobby lobby = (Lobby) ((ObjectMessage) message).getObject();
				System.out.println("Received Lobby Object: " + lobby);
			}
			else if (message.getJMSDestination().equals(gameMessageTopic) && message instanceof ObjectMessage) {
				@SuppressWarnings("unused")
				Game game = (Game) ((ObjectMessage) message).getObject();
				System.out.println("Received Game Object: " + game);
			}
		}
		catch (JMSException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void takeCardFromPullstack() throws MoveNotValidException, PlayerDoesNotExistsException {
		gameManagmentRemote.takeCardFromPullstack(getCurrentPlayer());
	}

	@Override
	public void takeCardFromLiFoStack() throws MoveNotValidException, PlayerDoesNotExistsException {
		gameManagmentRemote.takeCardFromLiFoStack(getCurrentPlayer());
	}

	@Override
	public void addToPileOnTable(Card card, DockPile dockPile) throws MoveNotValidException, PlayerDoesNotExistsException {
		gameManagmentRemote.addToPileOnTable(getCurrentPlayer(), card, dockPile);
	}

	@Override
	public void layPhaseToTable(Collection<DockPile> cards) throws MoveNotValidException, PlayerDoesNotExistsException {
		gameManagmentRemote.layPhaseToTable(getCurrentPlayer(), cards);
	}

	@Override
	public void layCardToLiFoStack(Card card) throws MoveNotValidException, PlayerDoesNotExistsException {
		gameManagmentRemote.layCardToLiFoStack(getCurrentPlayer(), card);
	}

	@Override
	public void laySkipCardForPlayer(long destinationPlayerId, Card card) throws MoveNotValidException, PlayerDoesNotExistsException {
		gameManagmentRemote.laySkipCardForPlayerById(getCurrentPlayer(), destinationPlayerId, card);
	}
	
	public JMSConsumer getPlayerConsumer()
	{
		return playerConsumer;
	}
	
	public JMSConsumer getLobbyConsumer()
	{
		return lobbyConsumer;
	}
	
	public JMSConsumer getGameConsumer()
	{
		return gameConsumer;
	}
	
	private Player createNewPlayer(String playername) throws NotLoggedInException, PlayerAlreadyExistentException
	{
		return userSessionRemote.getOrCreatePlayer(playername);
	}
	
	private Spectator getOrCreateCurrentSpectator() throws NotLoggedInException
	{
		return userSessionRemote.getOrCreateSpectator();
	}

	private Player getCurrentPlayer() throws PlayerDoesNotExistsException
	{
		User u = userSessionRemote.getUser();
		
		if(u != null && u.getPlayer() != null)
		{
			return u.getPlayer();
		}
		else
		{
			throw new PlayerDoesNotExistsException();
		}
	}
}
