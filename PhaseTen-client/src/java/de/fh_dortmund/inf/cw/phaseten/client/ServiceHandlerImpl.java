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
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.CardAlreadyTakenInThisTurnException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.CardCannotBeAddedException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.InvalidCardCompilationException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.NoFreeSlotException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.NotEnoughPlayerException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.NotYourTurnException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.PasswordIncorrectException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.PhaseNotCompletedException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.TakeCardBeforeDiscardingException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.UserDoesNotExistException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.UsernameAlreadyTakenException;
import de.fh_dortmund.inf.cw.phaseten.server.messages.CurrentPlayer;
import de.fh_dortmund.inf.cw.phaseten.server.messages.Game;
import de.fh_dortmund.inf.cw.phaseten.server.messages.Lobby;
import de.fh_dortmund.inf.cw.phaseten.server.shared.GameManagmentRemote;
import de.fh_dortmund.inf.cw.phaseten.server.shared.LobbyManagmentRemote;
import de.fh_dortmund.inf.cw.phaseten.server.shared.UserSessionRemote;

/**
 * @author Marc Mettke
 */
public class ServiceHandlerImpl extends ServiceHandler {
	private static ServiceHandlerImpl instance;
	
	private Context context;
	private UserSessionRemote userSessionRemote;
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
			userSessionRemote = (UserSessionRemote) context.lookup(
				"java:global/PhaseTen-ear/PhaseTen-ejb/UserSessionBean!de.fh_dortmund.inf.cw.phaseten.server.shared.UserSessionRemote"
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

	public void requestPlayerMessage() {
		this.userSessionRemote.requestPlayerMessage();
	}

	public void requestLobbyMessage() {
		this.lobbyManagmentRemote.requestLobbyMessage();		
	}

	public void requestGameMessage() {
		this.gameManagmentRemote.requestGameMessage();	
	}

	public void register(String username, String password) throws UsernameAlreadyTakenException {
		this.userSessionRemote.register(username, password);
	}

	public void login(String username, String password) throws UserDoesNotExistException, PasswordIncorrectException {
		this.userSessionRemote.login(username, password);
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

	public void addToOpenPile(Card card, DockPile dockPile)
			throws NotYourTurnException, CardCannotBeAddedException, PhaseNotCompletedException {
		this.gameManagmentRemote.addToOpenPile(card, dockPile);
	}

	public void goOut(Collection<Card> cards) throws NotYourTurnException, InvalidCardCompilationException {
		this.gameManagmentRemote.goOut(cards);
	}

	public void discardCardToDiscardPile(Card card) throws NotYourTurnException, TakeCardBeforeDiscardingException {
		this.gameManagmentRemote.discardCardToDiscardPile(card);
	}

	public void onMessage(Message message) {
		try {
			if(message.getJMSDestination().equals(playerMessageTopic) && message instanceof ObjectMessage) {
				CurrentPlayer currentPlayer = (CurrentPlayer) ((ObjectMessage) message).getObject();
				notify(currentPlayer);
			} else if(message.getJMSDestination().equals(lobbyMessageTopic) && message instanceof ObjectMessage) {
				Lobby lobby = (Lobby) ((ObjectMessage) message).getObject();
				notify(lobby);
			} else if(message.getJMSDestination().equals(gameMessageTopic) && message instanceof ObjectMessage) {
				Game game = (Game) ((ObjectMessage) message).getObject();
				notify(game);
			}
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
	
	private void notify(Object object) {
		setChanged();
		notifyObservers(object);
	}

	public void logout() {
		this.userSessionRemote.logout();
	}
}
