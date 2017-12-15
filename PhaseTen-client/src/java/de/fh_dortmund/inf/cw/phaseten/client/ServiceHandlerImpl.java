package de.fh_dortmund.inf.cw.phaseten.client;

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

import de.fh_dortmund.inf.cw.phaseten.server.exceptions.CardAlreadyTakenInThisTurnException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.CardCannotBeAddedException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.InvalidCardCompilationException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.NoFreeSlotException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.NotEnoughPlayerException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.NotYourTurnException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.PhaseNotCompletedException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.TakeCardBeforeDiscardingException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.UserDoesNotExistException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.UsernameAlreadyTakenException;
import de.fh_dortmund.inf.cw.phaseten.server.messages.CurrentPlayer;
import de.fh_dortmund.inf.cw.phaseten.server.messages.Game;
import de.fh_dortmund.inf.cw.phaseten.server.messages.Lobby;
import de.fh_dortmund.inf.cw.phaseten.server.shared.GameManagmentRemote;
import de.fh_dortmund.inf.cw.phaseten.server.shared.LobbyManagmentRemote;
import de.fh_dortmund.inf.cw.phaseten.server.shared.PlayerManagmentRemote;
import de.fh_dortmund.inf.cw.phaseten.server.shared.StubRemote;

/**
 * @author Marc Mettke
 */
public class ServiceHandlerImpl implements ServiceHandler {
	private static ServiceHandlerImpl instance;
	
	private Context context;
	private StubRemote stubRemote;
	private PlayerManagmentRemote playerManagmentRemote;
	private LobbyManagmentRemote lobbyManagmentRemote;
	private GameManagmentRemote gameManagmentRemote;
	
	private JMSContext jmsContext;
	private Topic playerMessageTopic;
	private Topic lobbyMessageTopic;
	private Topic gameMessageTopic;
	JMSConsumer playerConsumer;
	JMSConsumer lobbyConsumer;
	JMSConsumer gameConsumer;
	
	private ServiceHandlerImpl() {
		try {
			context = new InitialContext();
			stubRemote = (StubRemote) context.lookup(
				"java:global/PhaseTen-ear/PhaseTen-ejb/ServerStub!de.fh_dortmund.inf.cw.phaseten.server.shared.StubRemote"
			);
			playerManagmentRemote = (PlayerManagmentRemote) context.lookup(
				"java:global/PhaseTen-ear/PhaseTen-ejb/PlayerManagment!de.fh_dortmund.inf.cw.phaseten.server.shared.PlayerManagmentRemote"
			);
			lobbyManagmentRemote = (LobbyManagmentRemote) context.lookup(
				"java:global/PhaseTen-ear/PhaseTen-ejb/LobbyManagment!de.fh_dortmund.inf.cw.phaseten.server.shared.LobbyManagmentRemote"
			);
			gameManagmentRemote = (GameManagmentRemote) context.lookup(
				"java:global/PhaseTen-ear/PhaseTen-ejb/GameManagment!de.fh_dortmund.inf.cw.phaseten.server.shared.GameManagmentRemote"
			);
				
			ConnectionFactory connectionFactory = (ConnectionFactory) context.lookup("java:comp/DefaultJMSConnectionFactory");
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
		} catch (NamingException ex) {
			throw new RuntimeException(ex);
		}
	}
	
	public static ServiceHandlerImpl getInstance() {
		if(instance == null) {
			instance = new ServiceHandlerImpl();
		}
		return instance;
	}

	public String helloWorld() {
		return stubRemote.helloWorld();
	}

	public void requestPlayerMessage() {
		this.playerManagmentRemote.requestPlayerMessage();
	}

	public void requestLobbyMessage() {
		this.lobbyManagmentRemote.requestLobbyMessage();		
	}

	public void requestGameMessage() {
		this.gameManagmentRemote.requestGameMessage();	
	}

	public void register(String username, String password) throws UsernameAlreadyTakenException {
		this.playerManagmentRemote.register(username, password);
	}

	public void login(String username, String password) throws UserDoesNotExistException {
		this.playerManagmentRemote.login(username, password);
	}

	public void enterAsPlayer() throws NoFreeSlotException {
		this.lobbyManagmentRemote.enterAsPlayer();
	}

	public void enterAsSpectator() {
		this.lobbyManagmentRemote.enterAsSpectator();
	}

	public void startGame() throws NotEnoughPlayerException {
		this.lobbyManagmentRemote.startGame();
	}

	public void takeCardFromDrawPile() throws NotYourTurnException, CardAlreadyTakenInThisTurnException {
		this.gameManagmentRemote.takeCardFromDrawPile();
	}

	public void takeCardFromDiscardPile() throws NotYourTurnException, CardAlreadyTakenInThisTurnException {
		this.gameManagmentRemote.takeCardFromDrawPile();
	}

	public void addToOpenPile() throws NotYourTurnException, CardCannotBeAddedException, PhaseNotCompletedException {
		this.gameManagmentRemote.addToOpenPile();
	}

	public void goOut() throws NotYourTurnException, InvalidCardCompilationException {
		this.gameManagmentRemote.goOut();
	}

	public void discardCardToDiscardPile() throws NotYourTurnException, TakeCardBeforeDiscardingException {
		this.gameManagmentRemote.discardCardToDiscardPile();
	}

	public void onMessage(Message message) {
		try {
			if(message.getJMSDestination().equals(playerMessageTopic) && message instanceof ObjectMessage) {
				@SuppressWarnings("unused")
				CurrentPlayer currentPlayer = (CurrentPlayer) ((ObjectMessage) message).getObject();
				System.out.println("Received CurrentPlayer Object: " + currentPlayer);
			} else if(message.getJMSDestination().equals(lobbyMessageTopic) && message instanceof ObjectMessage) {
				@SuppressWarnings("unused")
				Lobby lobby = (Lobby) ((ObjectMessage) message).getObject();
				System.out.println("Received Lobby Object: " + lobby);
			} else if(message.getJMSDestination().equals(gameMessageTopic) && message instanceof ObjectMessage) {
				@SuppressWarnings("unused")
				Game game = (Game) ((ObjectMessage) message).getObject();
				System.out.println("Received Game Object: " + game);
			}
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
}
