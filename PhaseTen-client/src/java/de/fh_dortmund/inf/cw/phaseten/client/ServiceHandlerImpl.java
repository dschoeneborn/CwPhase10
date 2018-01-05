package de.fh_dortmund.inf.cw.phaseten.client;

import java.util.Collection;
import java.util.Observable;

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
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.GameNotInitializedException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.MoveNotValidException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.NoFreeSlotException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.NotEnoughPlayerException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.NotLoggedInException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.PlayerDoesNotExistsException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.UserDoesNotExistException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.UsernameAlreadyTakenException;
import de.fh_dortmund.inf.cw.phaseten.server.messages.GameGuiData;
import de.fh_dortmund.inf.cw.phaseten.server.messages.PlayerGuiData;
import de.fh_dortmund.inf.cw.phaseten.server.shared.GameManagementRemote;
import de.fh_dortmund.inf.cw.phaseten.server.shared.LobbyManagementRemote;
import de.fh_dortmund.inf.cw.phaseten.server.shared.UserManagementRemote;
import de.fh_dortmund.inf.cw.phaseten.server.shared.UserSessionRemote;

/**
 * @author Marc Mettke
 * @author Tim Prange
 */
public class ServiceHandlerImpl extends Observable implements ServiceHandler {
	private static ServiceHandlerImpl instance;

	private Context context;
	private UserManagementRemote playerManagementRemote;
	private LobbyManagementRemote lobbyManagementRemote;
	private GameManagementRemote gameManagementRemote;
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
			playerManagementRemote = (UserManagementRemote) context.lookup(
					"java:global/PhaseTen-ear/PhaseTen-ejb/UserManagementBean!de.fh_dortmund.inf.cw.phaseten.server.shared.UserManagementRemote");
			lobbyManagementRemote = (LobbyManagementRemote) context.lookup(
					"java:global/PhaseTen-ear/PhaseTen-ejb/LobbyManagementBean!de.fh_dortmund.inf.cw.phaseten.server.shared.LobbyManagementRemote");
			gameManagementRemote = (GameManagementRemote) context.lookup(
					"java:global/PhaseTen-ear/PhaseTen-ejb/GameManagementBean!de.fh_dortmund.inf.cw.phaseten.server.shared.GameManagementRemote");
			userSessionRemote = (UserSessionRemote) context.lookup(
					"java:global/PhaseTen-ear/PhaseTen-ejb/UserSessionBean!de.fh_dortmund.inf.cw.phaseten.server.shared.UserSessionRemote");
					
			
			ConnectionFactory connectionFactory = (ConnectionFactory) context
					.lookup("java:comp/DefaultJMSConnectionFactory");
			jmsContext = connectionFactory.createContext();

			playerMessageTopic = (Topic) context.lookup("java:global/jms/User");
			lobbyMessageTopic = (Topic) context.lookup("java:global/jms/Lobby");
			gameMessageTopic = (Topic) context.lookup("java:global/jms/Game");

			this.playerConsumer = jmsContext.createConsumer(playerMessageTopic);
			this.playerConsumer.setMessageListener(this);
			this.lobbyConsumer = jmsContext.createConsumer(lobbyMessageTopic);
			this.lobbyConsumer.setMessageListener(this);
			this.gameConsumer = jmsContext.createConsumer(gameMessageTopic);
			this.gameConsumer.setMessageListener(this);
		}
		catch (NamingException ex)
		{
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
	public void requestPlayerMessage() throws PlayerDoesNotExistsException, NotLoggedInException {		
		this.playerManagementRemote.requestPlayerMessage(getOrCreateCurrentPlayer());
	}

	@Override
	public void requestLobbyMessage() {
		this.lobbyManagementRemote.requestLobbyMessage();
	}

	@Override
	public void requestGameMessage() throws PlayerDoesNotExistsException, NotLoggedInException, GameNotInitializedException {
		this.gameManagementRemote.requestGameMessage(getOrCreateCurrentPlayer());
	}

	@Override
	public void register(String username, String password) throws UsernameAlreadyTakenException {
		userSessionRemote.register(username, password);
	}

	@Override
	public void login(String username, String password) throws UserDoesNotExistException {
		userSessionRemote.login(username, password);
	}

	@Override
	public void logout() throws NotLoggedInException
	{
		userSessionRemote.logout();
	}

	@Override
	public void enterLobbyAsPlayer() throws NoFreeSlotException, PlayerDoesNotExistsException, NotLoggedInException
	{
		this.lobbyManagementRemote.enterLobby(getOrCreateCurrentPlayer());
	}
	
	@Override
	public void enterLobbyAsSpectator() throws NotLoggedInException
	{
		this.lobbyManagementRemote.enterLobby(getOrCreateCurrentSpectator());
	}

	@Override
	public void startGame() throws NotEnoughPlayerException, PlayerDoesNotExistsException, NotLoggedInException
	{
		this.lobbyManagementRemote.startGame(getOrCreateCurrentPlayer());
	}

	@Override
	public void onMessage(Message message) {
		try
		{
			if (message.getJMSDestination().equals(playerMessageTopic) && message instanceof ObjectMessage)
			{
				setChanged();
				notifyObservers();
			}
			else if (message.getJMSDestination().equals(lobbyMessageTopic) && message instanceof ObjectMessage)
			{
				setChanged();
				notifyObservers();
			}
			else if (message.getJMSDestination().equals(gameMessageTopic) && message instanceof ObjectMessage)
			{
				GameGuiData game = (GameGuiData) ((ObjectMessage) message).getObject();
				setChanged();
				notifyObservers(game);
			}
		}
		catch (JMSException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void takeCardFromPullstack() throws MoveNotValidException, NotLoggedInException, GameNotInitializedException {
		gameManagementRemote.takeCardFromPullstack(getOrCreateCurrentPlayer());
	}

	@Override
	public void takeCardFromLiFoStack() throws MoveNotValidException, NotLoggedInException, GameNotInitializedException {
		gameManagementRemote.takeCardFromLiFoStack(getOrCreateCurrentPlayer());
	}

	@Override
	public void addToPileOnTable(Card card, DockPile dockPile) throws MoveNotValidException, NotLoggedInException, GameNotInitializedException {
		gameManagementRemote.addToPileOnTable(getOrCreateCurrentPlayer(), card, dockPile);
	}

	@Override
	public void layPhaseToTable(Collection<DockPile> cards) throws MoveNotValidException, NotLoggedInException, GameNotInitializedException {
		gameManagementRemote.layPhaseToTable(getOrCreateCurrentPlayer(), cards);
	}

	@Override
	public void layCardToLiFoStack(Card card) throws MoveNotValidException, NotLoggedInException, GameNotInitializedException {
		gameManagementRemote.layCardToLiFoStack(getOrCreateCurrentPlayer(), card);
	}

	@Override
	public void laySkipCardForPlayer(long destinationPlayerId, Card card) throws MoveNotValidException, NotLoggedInException, PlayerDoesNotExistsException, GameNotInitializedException {
		gameManagementRemote.laySkipCardForPlayerById(getOrCreateCurrentPlayer(), destinationPlayerId, card);
	}

	@Override
	public void exitLobby() throws NotLoggedInException
	{
		lobbyManagementRemote.leaveLobby(getOrCreateCurrentPlayer());
		lobbyManagementRemote.leaveLobby(getOrCreateCurrentSpectator());
	}

	@Override
	public boolean playerIsInGame() throws NotLoggedInException
	{
		return gameManagementRemote.isInGame(getOrCreateCurrentPlayer());
	}

	@Override
	public Collection<PlayerGuiData> getLobbyPlayers()
	{
		return lobbyManagementRemote.getPlayersForGui();
	}

	@Override
	public Collection<String> getLobbySpectators()
	{
		return lobbyManagementRemote.getSpectatorNamesForGui();
	}

	@Override
	public User getUser() throws NotLoggedInException
	{
		return userSessionRemote.getUser();
	}
	
	@Override
	public Collection<Card> getCards() throws NotLoggedInException
	{
		Player currentPlayer = getOrCreateCurrentPlayer();
		return currentPlayer.getPlayerPile().getCards();
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
	
	private Spectator getOrCreateCurrentSpectator() throws NotLoggedInException
	{
		return userSessionRemote.getOrCreateSpectator();
	}

	private Player getOrCreateCurrentPlayer() throws NotLoggedInException
	{
		return userSessionRemote.getOrCreatePlayer();
	}
}