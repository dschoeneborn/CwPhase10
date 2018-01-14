package de.fh_dortmund.inf.cw.phaseten.server.beans;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Topic;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import de.fh_dortmund.inf.cw.phaseten.server.entities.Card;
import de.fh_dortmund.inf.cw.phaseten.server.entities.CardValue;
import de.fh_dortmund.inf.cw.phaseten.server.entities.DockPile;
import de.fh_dortmund.inf.cw.phaseten.server.entities.Game;
import de.fh_dortmund.inf.cw.phaseten.server.entities.LiFoStack;
import de.fh_dortmund.inf.cw.phaseten.server.entities.Player;
import de.fh_dortmund.inf.cw.phaseten.server.entities.PullStack;
import de.fh_dortmund.inf.cw.phaseten.server.entities.Spectator;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.GameNotInitializedException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.MoveNotValidException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.PlayerDoesNotExistsException;
import de.fh_dortmund.inf.cw.phaseten.server.messages.GameGuiData;
import de.fh_dortmund.inf.cw.phaseten.server.messages.PlayerGuiData;
import de.fh_dortmund.inf.cw.phaseten.server.shared.GameManagementLocal;
import de.fh_dortmund.inf.cw.phaseten.server.shared.GameValidationLocal;
import de.fh_dortmund.inf.cw.phaseten.server.shared.UserManagementLocal;

/**
 * @author Marc Mettke
 * @author Björn Merschmeier
 * @author Tim Prange
 */
@Stateless
public class GameManagementBean implements GameManagementLocal {
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
	public void requestGameMessage(Player p) throws GameNotInitializedException {
		if (!p.getGame().isInitialized()) {
			try {
				Thread.sleep(3000);
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		if (p.getGame().isInitialized()) {
			sendGameMessage(p);
		}
		else {
			throw new GameNotInitializedException();
		}
	}

	@Override
	public void sendGameMessage(Player p) {
		sendGameMessage(GameGuiData.from(p.getGame()));
	}

	/**
	 * @author Björn Merschmeier
	 * @throws GameNotInitializedException
	 */
	@Override
	public void takeCardFromPullstack(Player player) throws MoveNotValidException {
		Game game = player.getGame();
		if (gameValidation.isValidDrawCardFromPullStack(game, player)) {
			Card drawnCard = game.getPullStack().pullTopCard();
			player.addCardToPlayerPile(drawnCard);
			player.addRoundStage();
		}
		else {
			throw new MoveNotValidException();
		}
		updateClient(player);
	}

	/**
	 * @author Björn Merschmeier
	 * @throws GameNotInitializedException
	 */
	@Override
	public void takeCardFromLiFoStack(Player player) throws MoveNotValidException {
		Game game = player.getGame();
		if (gameValidation.isValidDrawCardFromLiFoStack(game, player)) {
			Card drawnCard = game.getLiFoStack().pullTopCard();
			player.addCardToPlayerPile(drawnCard);
			player.addRoundStage();
		}
		else {
			throw new MoveNotValidException();
		}
		updateClient(player);
	}

	/**
	 * @author Björn Merschmeier
	 * @throws GameNotInitializedException
	 */
	@Override
	public void addToPileOnTable(Player player, long cardId, long dockPileId)
			throws MoveNotValidException, GameNotInitializedException {
		Card card = findCard(player, cardId);
		Game game = player.getGame();
		DockPile dockPile = entityManager.find(DockPile.class, dockPileId);
		if (gameValidation.isValidToAddCard(game, player, dockPile, card)) {
			dockPile.addCard(card);
			player.removeCardFromPlayerPile(card);
		}
		else {
			throw new MoveNotValidException();
		}
		updateClient(player);
	}

	/**
	 * @author Björn Merschmeier
	 * @throws GameNotInitializedException
	 */
	@Override
	public void layPhaseToTable(Player player, Collection<DockPile> piles)
			throws MoveNotValidException, GameNotInitializedException {
		Game game = player.getGame();
		if (gameValidation.isValidLayStageToTable(game, player, piles)) {
			for (DockPile pile : piles) {
				game.addOpenPile(pile);

				for (Card card : pile.getCards()) {
					player.removeCardFromPlayerPile(card);
				}
			}

			player.setPlayerLaidStage(true);
		}
		else {
			throw new MoveNotValidException();
		}

		updateClient(player);
	}

	/**
	 * @author Björn Merschmeier
	 * @throws GameNotInitializedException
	 */
	@Override
	public void layCardToLiFoStack(Player player, long cardId)
			throws MoveNotValidException, GameNotInitializedException {
		Card card = findCard(player, cardId);
		Game game = player.getGame();
		if (gameValidation.isValidPushCardToLiFoStack(game, player, card)) {
			game.getLiFoStack().addCard(card);
			player.removeCardFromPlayerPile(card);
			
			putNotVisibleLiFoStackCardsShuffledUnderPullStack(game);
			
			player.resetRoundStage();
			setNextPlayer(game);
		}
		else {
			throw new MoveNotValidException();
		}

		updateClient(player);
	}

	@Override
	public void laySkipCardForPlayerById(Player currentPlayer, long destinationPlayerId, long cardId)
			throws MoveNotValidException, PlayerDoesNotExistsException, GameNotInitializedException {
		Card card = findCard(currentPlayer, cardId);
		Game game = currentPlayer.getGame();
		Player destinationPlayer = null;

		for (Player p : game.getPlayers()) {
			if (p.getId() == destinationPlayerId) {
				destinationPlayer = p;
				break;
			}
		}

		if (destinationPlayer == null) {
			throw new PlayerDoesNotExistsException();
		}

		if (card.getCardValue() == CardValue.SKIP
				&& gameValidation.isValidLaySkipCard(currentPlayer, destinationPlayer, game)) {
			destinationPlayer.givePlayerSkipCard();
			layCardToLiFoStack(currentPlayer, cardId);
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
	public void startGame(Collection<Player> playersCollection, Collection<Spectator> spectatorsCollection) {
		Set<Player> players = new HashSet<>(playersCollection);
		Set<Spectator> spectators = new HashSet<>(spectatorsCollection);

		Game game = new Game(players, spectators);

		initNextRound(game);
		game.setInitialized();

		entityManager.persist(game);

		sendGameMessage((Player) players.toArray()[0]);
	}

	@Override
	public boolean isInGame(Player p) {
		return (p.getGame() != null);
	}

	/**
	 * @author Björn Merschmeier
	 * @param game
	 */
	private void putNotVisibleLiFoStackCardsShuffledUnderPullStack(Game game) {
		game.getPullStack().addCards(game.getLiFoStack().getNotVisibleCards());
		game.getPullStack().shuffle();
	}

	/**
	 * @author Björn Merschmeier
	 * @param player
	 * @param cardId
	 * @return
	 */
	private Card findCard(Player player, long cardId) {
		Card foundCard = null;

		for (Card c : player.getPlayerPile().getCards()) {
			if (c.getId() == cardId) {
				foundCard = c;
				break;
			}
		}

		return foundCard;
	}

	/**
	 * @author Björn Merschmeier
	 * @param game
	 */
	private void initNextRound(Game game) {
		initializePullstack(game);
		initializeLiFoStack(game);
		initializeFirstPlayer(game);
		giveCardsToPlayers(game);

		if (game.getLiFoStack().showCard().getCardValue() == CardValue.SKIP) {
			setNextPlayer(game);
		}

	}

	/**
	 * @author Björn Merschmeier
	 * @param game
	 */
	private void giveCardsToPlayers(Game game) {
		Collection<Player> players = game.getPlayers();

		for (int i = 0; i < 10; i++) {
			for (Player player : players) {
				player.addCardToPlayerPile(game.getPullStack().pullTopCard());
			}
		}
	}

	/**
	 * @author Björn Merschmeier
	 * @param game
	 */
	private void initializeFirstPlayer(Game game) {
		List<Player> players = new ArrayList<>(game.getPlayers());

		Random r = new Random();
		int nextPlayer = 0;

		if (game.getLastRoundBeginner() == null) {
			nextPlayer = r.nextInt(players.size());
		}
		else {
			nextPlayer = (players.indexOf(game.getLastRoundBeginner()) + 1) % players.size();
		}

		game.setCurrentPlayer(players.get(nextPlayer));
		game.setLastRoundBeginner(players.get(nextPlayer));
	}

	/**
	 * @author Björn Merschmeier
	 * @param game
	 */
	private void initializeLiFoStack(Game game) {
		Card topCard = game.getPullStack().pullTopCard();

		LiFoStack lifoStack = new LiFoStack();
		lifoStack.addCard(topCard);

		game.setLiFoStack(lifoStack);
	}

	/**
	 * @author Björn Merschmeier
	 * @param game
	 */
	private void initializePullstack(Game game) {
		deleteOldCards(game);
		PullStack pullStack = new PullStack();
		pullStack.initializeCards();
		pullStack.shuffle();
		saveNewCards(pullStack.getCards());

		game.setPullstack(pullStack);
	}

	private void saveNewCards(Collection<Card> cards) {
		for (Card card : cards) {
			entityManager.persist(card);
		}
	}

	private void deleteOldCards(Game game) {
		if (game != null) {
			if (game.getPullStack() != null) {
				deleteCards(game.getPullStack().getCards());
			}

			if (game.getLiFoStack() != null) {
				deleteCards(game.getLiFoStack().getCards());
			}

			for (Player p : game.getPlayers()) {
				deleteCards(p.getPlayerPile().getCards());
			}

			for (DockPile pile : game.getOpenPiles()) {
				deleteCards(pile.getCards());
			}
		}
	}

	private void deleteCards(Collection<Card> cards) {
		for (Card card : cards) {
			entityManager.remove(card);
		}
	}

	/**
	 * @author Björn Merschmeier
	 * @param game
	 */
	private void setNextPlayer(Game game) {
		Player nextPlayer = game.getNextPlayer();
		game.setCurrentPlayer(nextPlayer);

		if (nextPlayer.hasSkipCard()) {
			nextPlayer.removeSkipCard();
			setNextPlayer(game);
		}
		else if (nextPlayer.hasNoCards()) {
			countPoints(game);
			initNextRound(game);
		}
	}

	/**
	 * @author Björn Merschmeier
	 * @param game
	 */
	private void countPoints(Game game) {
		List<Player> players = new ArrayList<>(game.getPlayers());

		for (Player player : players) {
			Collection<Card> remainingCards = player.getPlayerPile().getCards();

			for (Card remainingCard : remainingCards) {
				if (remainingCard.getCardValue().getValue() >= 5) {
					player.addNegativePoints(5);
				}
				else if (remainingCard.getCardValue().getValue() >= 12) {
					player.addNegativePoints(10);
				}
				else if (remainingCard.getCardValue() == CardValue.SKIP) {
					player.addNegativePoints(15);
				}
				else if (remainingCard.getCardValue() == CardValue.WILD) {
					player.addNegativePoints(20);
				}
			}
		}
	}

	private void updateClient(Player p) {
		// Send updated Game to Client
		sendGameMessage(p);

		playerManagment.sendUserMessage();
	}

	private void sendGameMessage(GameGuiData game) {
		Message message = jmsContext.createObjectMessage(game);

		String playersAndSpectators = ";-;-;";

		for (PlayerGuiData player : game.getPlayers()) {
			playersAndSpectators += player.getName();
			playersAndSpectators += ";-;-;";
		}

		for (String spectator : game.getSpectators()) {
			playersAndSpectators += spectator;
			playersAndSpectators += ";-;-;";
		}

		try {
			message.setStringProperty("userNames", playersAndSpectators);
		}
		catch (JMSException e) {
			e.printStackTrace();
		}

		jmsContext.createProducer().send(gameMessageTopic, message);
	}
}
