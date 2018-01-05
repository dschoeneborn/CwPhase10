package de.fh_dortmund.inf.cw.phaseten.server.beans;

import java.util.Collection;
import java.util.List;
import java.util.Random;

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

import de.fh_dortmund.inf.cw.phaseten.server.entities.Card;
import de.fh_dortmund.inf.cw.phaseten.server.entities.CardValue;
import de.fh_dortmund.inf.cw.phaseten.server.entities.Color;
import de.fh_dortmund.inf.cw.phaseten.server.entities.DockPile;
import de.fh_dortmund.inf.cw.phaseten.server.entities.Game;
import de.fh_dortmund.inf.cw.phaseten.server.entities.LiFoStack;
import de.fh_dortmund.inf.cw.phaseten.server.entities.Player;
import de.fh_dortmund.inf.cw.phaseten.server.entities.PullStack;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.GameNotInitializedException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.MoveNotValidException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.PlayerDoesNotExistsException;
import de.fh_dortmund.inf.cw.phaseten.server.messages.CurrentPlayer;
import de.fh_dortmund.inf.cw.phaseten.server.shared.GameManagementLocal;
import de.fh_dortmund.inf.cw.phaseten.server.shared.GameManagementRemote;
import de.fh_dortmund.inf.cw.phaseten.server.shared.GameValidationLocal;
import de.fh_dortmund.inf.cw.phaseten.server.shared.UserManagementLocal;

/**
 * @author Marc Mettke
 * @author Björn Merschmeier
 * @author Tim Prange
 */
@Stateless
public class GameManagementBean implements GameManagementRemote, GameManagementLocal {
	@Inject
	private JMSContext jmsContext;
	@Resource(lookup = "java:global/jms/Game")
	private Topic gameMessageTopic;
	
	@PersistenceContext
	private EntityManager entityManager;

	@EJB
	UserManagementLocal playerManagment;

	@EJB
	GameValidationLocal gameValidation;

	@Override
	public void requestGameMessage(Player p) throws GameNotInitializedException
	{
		if(!getActualPlayedGame(p).isInitialized())
		{
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		if(getActualPlayedGame(p).isInitialized())
		{
			sendGameMessage(p);
		}
		else
		{
			throw new GameNotInitializedException();
		}
	}

	@Override
	public void sendGameMessage(Player p) {
		sendGameMessage(
				de.fh_dortmund.inf.cw.phaseten.server.messages.Game
						.from(getActualPlayedGame(p)));
	}

	@Override
	public void sendGameMessage(Game game) {
		for(Player p: game.getPlayers())
		{
			sendGameMessage(p);
		}
	}

	/**
	 * @author Björn Merschmeier
	 */
	@Override
	public void takeCardFromPullstack(Player player) throws MoveNotValidException
	{
		Game game = getActualPlayedGame(player);
		if (gameValidation.isValidDrawCardFromPullStack(game, player))
		{
			Card drawnCard = game.getPullStack().pullTopCard();
			player.addCardToPlayerPile(drawnCard);
			player.addRoundStage();
		}
		else
		{
			throw new MoveNotValidException();
		}
		updateClient(player);
	}

	/**
	 * @author Björn Merschmeier
	 */
	@Override
	public void takeCardFromLiFoStack(Player player) throws MoveNotValidException
	{
		Game game = getActualPlayedGame(player);
		if (gameValidation.isValidDrawCardFromLiFoStack(game, player))
		{
			Card drawnCard = game.getLiFoStack().pullTopCard();
			player.addCardToPlayerPile(drawnCard);
			player.addRoundStage();
		}
		else
		{
			throw new MoveNotValidException();
		}
		updateClient(player);
	}

	/**
	 * @author Björn Merschmeier
	 */
	@Override
	public void addToPileOnTable(Player player, Card card, DockPile dockPile) throws MoveNotValidException
	{
		Game game = getActualPlayedGame(player);
		if (gameValidation.isValidToAddCard(game, player, dockPile, card))
		{
			dockPile.addCard(card);
			player.removeCardFromPlayerPile(card);
		}
		else
		{
			throw new MoveNotValidException();
		}
		updateClient(player);
	}

	/**
	 * @author Björn Merschmeier
	 */
	@Override
	public void layPhaseToTable(Player player, Collection<DockPile> piles) throws MoveNotValidException
	{
		Game game = getActualPlayedGame(player);
		if (gameValidation.isValidLayStageToTable(game, player, piles))
		{
			for (DockPile pile : piles)
			{
				game.addOpenPile(pile);

				for (Card card : pile.getCards())
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

		updateClient(player);
	}

	/**
	 * @author Björn Merschmeier
	 */
	@Override
	public void layCardToLiFoStack(Player player, Card card) throws MoveNotValidException
	{
		Game game = getActualPlayedGame(player);
		if (gameValidation.isValidPushCardToLiFoStack(game, player, card))
		{
			game.getLiFoStack().addCard(card);
			player.resetRoundStage();
			setNextPlayer(game);
		}
		else
		{
			throw new MoveNotValidException();
		}

		updateClient(player);
	}

	/**
	 * @author Björn Merschmeier
	 * @throws PlayerDoesNotExistsException 
	 */
	@Override
	public void laySkipCardForPlayer(Player currentPlayer, Player destinationPlayer, Card card)
			throws MoveNotValidException, PlayerDoesNotExistsException
	{
		laySkipCardForPlayerById(currentPlayer, destinationPlayer.getId(), card);
	}

	@Override
	public void laySkipCardForPlayerById(Player currentPlayer, long destinationPlayerId, Card card)
			throws MoveNotValidException, PlayerDoesNotExistsException
	{
		Game game = getActualPlayedGame(currentPlayer);
		Player destinationPlayer = null;
		
		for(Player p : game.getPlayers())
		{
			if(p.getId() == destinationPlayerId)
			{
				destinationPlayer = p;
				break;
			}
		}
		
		if(destinationPlayer == null)
		{
			throw new PlayerDoesNotExistsException();
		}
		
		if (card.getCardValue() == CardValue.SKIP
				&& gameValidation.isValidLaySkipCard(currentPlayer, destinationPlayer, game))
		{
			currentPlayer.removeCardFromPlayerPile(card);
			destinationPlayer.givePlayerSkipCard();
			currentPlayer.resetRoundStage();
			setNextPlayer(game);
		}
		else {
			throw new MoveNotValidException();
		}

		updateClient(currentPlayer);
	}

	/*
	 * (non-Javadoc)
	 * @see de.fh_dortmund.inf.cw.phaseten.server.shared.GameManagment#initGame(de.
	 * fh_dortmund.inf.cw.phaseten.server.entities.Game)
	 */
	/**
	 * @author Tim Prange
	 * @author Björn Merschmeier
	 */
	@Override
	public void startGame(Game game)
	{
		entityManager.persist(game);
		
		initNextRound(game);
		game.setInitialized();
		sendGameMessage(game);
		
		entityManager.flush();
	}


	@Override
	public boolean isInGame(Player p)
	{
		return (getActualPlayedGame(p) != null);
	}
	
	/**
	 * @author Björn Merschmeier
	 * @param game
	 */
	private void initNextRound(Game game)
	{
		initializePullstack(game);
		initializeLiFoStack(game);
		initializeFirstPlayer(game);
		giveCardsToPlayers(game);
		
		if(game.getLiFoStack().showCard().getCardValue() == CardValue.SKIP)
		{
			setNextPlayer(game);
		}
		
	}
	
	/**
	 * @author Björn Merschmeier
	 * @param game
	 */
	private void giveCardsToPlayers(Game game)
	{
		List<Player> players = game.getPlayers();
		
		for(int i = 0; i < 10; i++)
		{
			for(Player player : players)
			{
				player.addCardToPlayerPile(game.getPullStack().pullTopCard());
			}
		}
	}

	/**
	 * @author Björn Merschmeier
	 * @param game
	 */
	private void initializeFirstPlayer(Game game)
	{
		List<Player> players = game.getPlayers();
		
		Random r = new Random();
		int randomPlayer = r.nextInt(players.size());
		
		game.setCurrentPlayer(players.get(randomPlayer));
	}

	/**
	 * @author Björn Merschmeier
	 * @param game
	 */
	private void initializeLiFoStack(Game game)
	{
		Card topCard = game.getPullStack().pullTopCard();
		
		LiFoStack lifoStack = new LiFoStack();
		lifoStack.addCard(topCard);
		
		game.setLiFoStack(lifoStack);
	}

	/**
	 * @author Björn Merschmeier
	 * @param game
	 */
	private void initializePullstack(Game game)
	{
		PullStack pullStack = new PullStack();
		pullStack.initializeCards();
		pullStack.shuffle();
		
		game.setPullstack(pullStack);
	}

	/**
	 * @author Björn Merschmeier
	 * @param game
	 * @throws MoveNotValidException 
	 */
	private void setNextPlayer(Game game)
	{
		Player nextPlayer = game.getNextPlayer();
		game.setCurrentPlayer(nextPlayer);
		
		if(nextPlayer.hasSkipCard())
		{
			try
			{
				layCardToLiFoStack(nextPlayer, new Card(Color.NONE, CardValue.SKIP));
			}
			catch(MoveNotValidException e)
			{
				throw new RuntimeException("There happend something awkward in the gamelogic. You cannot continue your game, please restart or contact your administrator. #1");
			}
			setNextPlayer(game);
		}
		else if(nextPlayer.hasNoCards())
		{
			countPoints(game);
			initNextRound(game);
		}
	}

	/**
	 * @author Björn Merschmeier
	 * @param game
	 */
	private void countPoints(Game game)
	{
		List<Player> players = game.getPlayers();
		
		for(Player player : players)
		{
			List<Card> remainingCards = player.getPlayerPile().getCards();
			
			for(Card remainingCard : remainingCards)
			{
				if(remainingCard.getCardValue().getValue() >= 5)
				{
					player.addNegativePoints(5);
				}
				else if(remainingCard.getCardValue().getValue() >= 12)
				{
					player.addNegativePoints(10);
				}
				else if(remainingCard.getCardValue() == CardValue.SKIP)
				{
					player.addNegativePoints(15);
				}
				else if(remainingCard.getCardValue() == CardValue.WILD)
				{
					player.addNegativePoints(20);
				}
			}
		}
	}

	private void updateClient(Player p) {
		// Send updated Game to Client
		sendGameMessage(p);
		// Send updated Player Cards to Client
		playerManagment.sendPlayerMessage(CurrentPlayer.from(p));
	}

	private void sendGameMessage(de.fh_dortmund.inf.cw.phaseten.server.messages.Game game) {
		Message message = jmsContext.createObjectMessage(game);
		jmsContext.createProducer().send(gameMessageTopic, message);
	}

	/**
	 * Gets the latest game persist
	 *
	 * @author Tim Prange
	 * @author Björn Merschmeier
	 * @param Player p
	 * @return game
	 */
	private Game getActualPlayedGame(Player p)
	{
		Query query = entityManager.createNamedQuery("selectByUserId");
		query.setParameter("playerId", p.getId());
		
		try
		{
			return (Game)query.getSingleResult();
		}
		catch(NoResultException e)
		{
			return null;
		}
	}
}
