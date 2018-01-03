package de.fh_dortmund.inf.cw.phaseten.server.beans;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.Message;
import javax.jms.Topic;

import de.fh_dortmund.inf.cw.phaseten.server.entities.Card;
import de.fh_dortmund.inf.cw.phaseten.server.entities.CardValue;
import de.fh_dortmund.inf.cw.phaseten.server.entities.Color;
import de.fh_dortmund.inf.cw.phaseten.server.entities.ColorDockPile;
import de.fh_dortmund.inf.cw.phaseten.server.entities.DockPile;
import de.fh_dortmund.inf.cw.phaseten.server.entities.LiFoStack;
import de.fh_dortmund.inf.cw.phaseten.server.entities.PullStack;
import de.fh_dortmund.inf.cw.phaseten.server.entities.SequenceDockPile;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.CardAlreadyTakenInThisTurnException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.CardCannotBeAddedException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.InvalidCardCompilationException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.NotYourTurnException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.PhaseNotCompletedException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.TakeCardBeforeDiscardingException;
import de.fh_dortmund.inf.cw.phaseten.server.messages.Game;
import de.fh_dortmund.inf.cw.phaseten.server.messages.Player;
import de.fh_dortmund.inf.cw.phaseten.server.messages.Spectator;
import de.fh_dortmund.inf.cw.phaseten.server.shared.GameManagmentLocal;
import de.fh_dortmund.inf.cw.phaseten.server.shared.GameManagmentRemote;
import de.fh_dortmund.inf.cw.phaseten.server.shared.PlayerManagmentLocal;

/**
 * @author Marc Mettke
 */
@Stateless
public class GameManagment implements GameManagmentRemote, GameManagmentLocal {
	@Inject
	private JMSContext jmsContext;
	@Resource(lookup = "java:global/jms/Game")
	private Topic gameMessageTopic;
	
	@EJB 
	PlayerManagmentLocal playerManagment;
	
	@Override
	public void requestGameMessage() {
		sendGameMessage();
	}

	@Override
	public void createGame() {
		updateClient();		
	}

	@Override
	public void sendGameMessage() {
		LiFoStack drawStack = new LiFoStack();
		drawStack.pushCard(new Card(Color.BLUE, CardValue.EIGHT));
		
		ColorDockPile colorDockPile = new ColorDockPile(Color.RED);
		colorDockPile.dock(new Card(Color.RED, CardValue.SEVEN));
		colorDockPile.dock(new Card(Color.RED, CardValue.SIX));
		SequenceDockPile sequenceDockPile = new SequenceDockPile(); 
		sequenceDockPile.dock(new Card(Color.GREEN, CardValue.ONE));
		sequenceDockPile.dock(new Card(Color.GREEN, CardValue.TWO));
		sequenceDockPile.dock(new Card(Color.GREEN, CardValue.THREE));
		
		sendGameMessage(new Game(
			Arrays.asList(new Player("gamePlayerTest1", 1), new Player("gamePlayerTest2", 2)),
			Arrays.asList(new Spectator("gameSpectatorTest1"), new Spectator("gameSpectatorTest2")),
			new PullStack(), 
			drawStack, 
			Arrays.asList(colorDockPile, sequenceDockPile)
		));
	}

	@Override
	public void takeCardFromDrawPile() throws NotYourTurnException, CardAlreadyTakenInThisTurnException {
		// TODO: Check if move is valid, proceed move
		updateClient();
	}

	@Override
	public void takeCardFromDiscardPile() throws NotYourTurnException, CardAlreadyTakenInThisTurnException {
		// TODO: Check if move is valid, proceed move
		updateClient();		
	}

	@Override
	public void addToOpenPile(Card card, DockPile dockPile)
			throws NotYourTurnException, CardCannotBeAddedException, PhaseNotCompletedException {
		// TODO: Check if move is valid, proceed move
		updateClient();			
	}

	@Override
	public void goOut(Collection<Card> cards) throws NotYourTurnException, InvalidCardCompilationException {
		// TODO: Check if move is valid, proceed move
		updateClient();				
	}

	@Override
	public void discardCardToDiscardPile(Card card) throws NotYourTurnException, TakeCardBeforeDiscardingException {
		// TODO: Check if move is valid, proceed move
		updateClient();				
	}
	
	private void updateClient() {
		// Send updated Game to Client
		this.sendGameMessage();
		// Send updated Player Cards to Client
		this.playerManagment.sendPlayerMessage();
	}

	private void sendGameMessage(Game game) {
		Message message = jmsContext.createObjectMessage(game);
		jmsContext.createProducer().send(
			gameMessageTopic,
			message
		);
	}
}
