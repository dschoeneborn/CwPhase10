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

import de.fh_dortmund.inf.cw.phaseten.server.entities.Card;
import de.fh_dortmund.inf.cw.phaseten.server.entities.CardValue;
import de.fh_dortmund.inf.cw.phaseten.server.entities.DockPile;
import de.fh_dortmund.inf.cw.phaseten.server.entities.Game;
import de.fh_dortmund.inf.cw.phaseten.server.entities.LiFoStack;
import de.fh_dortmund.inf.cw.phaseten.server.entities.Player;
import de.fh_dortmund.inf.cw.phaseten.server.entities.PullStack;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.MoveNotValidException;
import de.fh_dortmund.inf.cw.phaseten.server.shared.GameManagmentLocal;
import de.fh_dortmund.inf.cw.phaseten.server.shared.GameManagmentRemote;
import de.fh_dortmund.inf.cw.phaseten.server.shared.GameValidationLocal;
import de.fh_dortmund.inf.cw.phaseten.server.shared.PlayerManagmentLocal;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * @author Marc Mettke
 * @author Björn Merschmeier
 */
@Stateless
public class GameManagmentBean implements GameManagmentRemote, GameManagmentLocal {
	@Inject
	private JMSContext jmsContext;
	@Resource(lookup = "java:global/jms/Game")
	private Topic gameMessageTopic;
	
	//TODO - BM - 31.12.2017 - muss noch gefüllt werden!
	private Game game;
	
	@EJB 
	PlayerManagmentLocal playerManagment;
	
	@EJB
	GameValidationLocal gameValidation;
	
	@Override
	public void requestGameMessage() {
		sendGameMessage();
	}

	@Override
	public void sendGameMessage() {
		sendGameMessage(new de.fh_dortmund.inf.cw.phaseten.server.messages.Game(
			new ArrayList<>(), 
			new ArrayList<>(), 
			new PullStack(), 
			new LiFoStack(), 
			new ArrayList<>()
		));
		
		throw new NotImplementedException();
	}

	/**
	 * @author Björn Merschmeier
	 */
	@Override
	public void takeCardFromPullstack(Player p) throws MoveNotValidException {
		//TODO - BM - 31.12.2017 - Player vielleicht aus einer Playerbean auslesen und nicht als Parameter übergeben lassen?
		if(gameValidation.isValidDrawCardFromPullStack(game, p))
		{
			Card drawnCard = game.getPullStack().pullTopCard();
			p.addCardToPlayerPile(drawnCard);
			p.addRoundStage();
		}
		else
		{
			throw new MoveNotValidException();
		}
		updateClient();
	}

	/**
	 * @author Björn Merschmeier
	 */
	@Override
	public void takeCardFromLiFoStack(Player player) throws MoveNotValidException {
		//TODO - BM - 31.12.2017 - Player vielleicht aus einer Playerbean auslesen und nicht als Parameter übergeben lassen?
		if(gameValidation.isValidDrawCardFromLiFoStack(game, player))
		{
			Card drawnCard = game.getLiFoStack().pullTopCard();
			player.addCardToPlayerPile(drawnCard);
			player.addRoundStage();
		}
		else
		{
			throw new MoveNotValidException();
		}
		updateClient();		
	}

	/**
	 * @author Björn Merschmeier
	 */
	@Override
	public void addToPileOnTable(Player player, Card card, DockPile dockPile) throws MoveNotValidException {
		//TODO - BM - 31.12.2017 - Player vielleicht aus einer Playerbean auslesen und nicht als Parameter übergeben lassen?
		if(gameValidation.isValidToAddCard(game, player, dockPile, card))
		{
			dockPile.addCard(card);
			player.removeCardFromPlayerPile(card);
		}
		else
		{
			throw new MoveNotValidException();
		}
		updateClient();			
	}

	/**
	 * @author Björn Merschmeier
	 */
	@Override
	public void layPhaseToTable(Player player, Collection<DockPile> piles) throws MoveNotValidException
	{
		//TODO - BM - 31.12.2017 - Player vielleicht aus einer Playerbean auslesen und nicht als Parameter übergeben lassen?
		if(gameValidation.isValidLayStageToTable(game, player, piles))
		{
			for(DockPile pile : piles)
			{
				game.addOpenPile(pile);
				
				for(Card card : pile.getCards())
				{
					player.removeCardFromPlayerPile(card);
				}
			}
			
			player.setPlayerLaidStage(true);
		}
		else
		{
			throw new MoveNotValidException();
		}
		
		updateClient();
	}

	/**
	 * @author Björn Merschmeier
	 */
	@Override
	public void layCardToLiFoStack(Player player, Card card) throws MoveNotValidException {
		//TODO - BM - 31.12.2017 - Player vielleicht aus einer Playerbean auslesen und nicht als Parameter übergeben lassen?
		if(gameValidation.isValidPushCardToLiFoStack(game, player, card))
		{
			game.getLiFoStack().addCard(card);
			player.resetRoundStage();
			game.nextCurrentPlayer();
		}
		else
		{
			throw new MoveNotValidException();
		}
		
		updateClient();				
	}

	/**
	 * @author Björn Merschmeier
	 */
	@Override
	public void laySkipCardForPlayer(Player currentPlayer, Player destinationPlayer, Card card) throws MoveNotValidException
	{
		//TODO - BM - 31.12.2017 - Player vielleicht aus einer Playerbean auslesen und nicht als Parameter übergeben lassen?
		if(card.getCardValue() == CardValue.SKIP
			&& gameValidation.isValidLaySkipCard(currentPlayer, destinationPlayer, game))
		{
			currentPlayer.removeCardFromPlayerPile(card);
			destinationPlayer.givePlayerSkipCard();
			currentPlayer.resetRoundStage();
			game.nextCurrentPlayer();
		}
		else
		{
			throw new MoveNotValidException();
		}
		
		updateClient();
	}
	
	private void updateClient() {
		// Send updated Game to Client
		this.sendGameMessage();
		// Send updated Player Cards to Client
		this.playerManagment.sendPlayerMessage();
	}

	private void sendGameMessage(de.fh_dortmund.inf.cw.phaseten.server.messages.Game game) {
		Message message = jmsContext.createObjectMessage(game);
		jmsContext.createProducer().send(
			gameMessageTopic,
			message
		);
	}
}
