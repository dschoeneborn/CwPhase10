package de.fh_dortmund.inf.cw.phaseten.server.beans;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
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
import javax.persistence.TypedQuery;

import de.fh_dortmund.inf.cw.phaseten.server.entities.Card;
import de.fh_dortmund.inf.cw.phaseten.server.entities.DockPile;
import de.fh_dortmund.inf.cw.phaseten.server.entities.Game;
import de.fh_dortmund.inf.cw.phaseten.server.entities.LiFoStack;
import de.fh_dortmund.inf.cw.phaseten.server.entities.Player;
import de.fh_dortmund.inf.cw.phaseten.server.entities.PlayerPile;
import de.fh_dortmund.inf.cw.phaseten.server.entities.PullStack;
import de.fh_dortmund.inf.cw.phaseten.server.entities.Spectator;
import de.fh_dortmund.inf.cw.phaseten.server.entities.User;
import de.fh_dortmund.inf.cw.phaseten.server.entities.ai.CardsToPileAction;
import de.fh_dortmund.inf.cw.phaseten.server.enumerations.CardValue;
import de.fh_dortmund.inf.cw.phaseten.server.enumerations.Stage;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.GameNotInitializedException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.MoveNotValidException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.PlayerDoesNotExistsException;
import de.fh_dortmund.inf.cw.phaseten.server.messages.GameGuiData;
import de.fh_dortmund.inf.cw.phaseten.server.messages.PlayerGuiData;
import de.fh_dortmund.inf.cw.phaseten.server.shared.AIManagementLocal;
import de.fh_dortmund.inf.cw.phaseten.server.shared.CoinManagementLocal;
import de.fh_dortmund.inf.cw.phaseten.server.shared.GameManagementLocal;
import de.fh_dortmund.inf.cw.phaseten.server.shared.GameValidationLocal;
import de.fh_dortmund.inf.cw.phaseten.server.shared.UserManagementLocal;;

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
	private UserManagementLocal playerManagment;

	@EJB
	GameValidationLocal gameValidation;

	@EJB
	private CoinManagementLocal coinManagment;

	@EJB
	AIManagementLocal aiManagment;

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
			takeCardFromPullstack(player, game);
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
			takeCardFromLiFoStack(player, game);
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
		addToPileOnTable(player, cardId, dockPileId, false);
	}

	/**
	 * @author Björn Merschmeier
	 * @throws GameNotInitializedException
	 */
	@Override
	public void addToPileOnTable(Player player, long cardId, long dockPileId, boolean layAtFirstPosition)
			throws MoveNotValidException, GameNotInitializedException {
		Card card = findCard(player, cardId);
		Game game = player.getGame();
		DockPile dockPile = entityManager.find(DockPile.class, dockPileId);

		if (layAtFirstPosition && gameValidation.isValidToAddCardFirst(game, player, dockPile, card)) {
			dockPile.addFirst(card);
		}
		else if (!layAtFirstPosition && gameValidation.isValidToAddCardLast(game, player, dockPile, card)) {
			dockPile.addLast(card);
		}
		else if (gameValidation.isValidToAddCardLast(game, player, dockPile, card)) {
			dockPile.addLast(card);
		}
		else if (gameValidation.isValidToAddCardFirst(game, player, dockPile, card)) {
			dockPile.addFirst(card);
		}
		else {
			throw new MoveNotValidException();
		}
		player.removeCardFromPlayerPile(card);

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
			layPhaseToTable(player, piles, game);
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
			layCardToLiFoStack(player, game, card);
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
			layCardToLiFoStack(currentPlayer, game, card);
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

	private void layCardToLiFoStack(Player player, Game game, Card card) {
		game.getLiFoStack().addLast(card);
		player.removeCardFromPlayerPile(card);

		game = player.getGame();
		putNotVisibleLiFoStackCardsShuffledUnderPullStack(game);

		player.resetRoundStage();
	}

	private void layPhaseToTable(Player player, Collection<DockPile> piles, Game game) {
		for (DockPile pile : piles) {
			entityManager.persist(pile);
			game.addOpenPile(pile);

			for (Card card : pile.getCopyOfCardsList()) {
				player.removeCardFromPlayerPile(card);
			}
		}

		player.setPlayerLaidStage(true);
		player.addPhase();
	}

	private void takeCardFromLiFoStack(Player player, Game game) {
		Card drawnCard = game.getLiFoStack().pullTopCard();
		player.addCardToPlayerPile(drawnCard);
		player.addRoundStage();
	}

	private void takeCardFromPullstack(Player player, Game game) {
		Card drawnCard = game.getPullStack().pullTopCard();
		player.addCardToPlayerPile(drawnCard);
		player.addRoundStage();
	}

	/**
	 * @author Björn Merschmeier
	 * @param game
	 */
	private void putNotVisibleLiFoStackCardsShuffledUnderPullStack(Game game) {
		game.getPullStack().addAllLast(game.getLiFoStack().getNotVisibleCards());
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

		for (Card c : player.getPlayerPile().getCopyOfCardsList()) {
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
		for (Player player : game.getPlayers()) {
			if (player.getPhase() == Stage.FINISHED) {
				game.setFinished();
				cashPlayerOut(game);
				return;
			}
			player.setPlayerLaidStage(false);
		}

		initializePullstack(game);
		initializeLiFoStack(game);
		initializeFirstPlayer(game);
		giveCardsToPlayers(game);

		for (Player player : game.getPlayers()) {
			player.setPlayerLaidStage(false);
		}

		if (game.getLiFoStack().showCard().getCardValue() == CardValue.SKIP) {
			setNextPlayer(game);
		}
		else if (game.getCurrentPlayer().getIsAI()) {
			aiTurn(game, game.getCurrentPlayer());
			setNextPlayer(game);
		}
	}

	private void cashPlayerOut(Game game) {
		List<Player> ranking = new LinkedList<>();
		int i = 0;
		for (Player player : game.getPlayers()) {
			i = 0;
			for (Player rankedPlayer : ranking) {
				if (rankedPlayer.getNegativePoints() > player.getNegativePoints()) {
					break;
				}
				i++;
			}

			ranking.add(i, player);
		}
		int coins = ranking.size() * 50;
		int divisor = 0;
		for (i = 1; i < ranking.size(); i++) {
			divisor += i;
		}
		i = 1;
		for (Player player : ranking) {
			if (!player.getIsAI()) {
				TypedQuery<User> query = entityManager.createNamedQuery(User.FIND_BY_PLAYER, User.class);
				query.setParameter(User.PARAM_PLAYER, player);
				User result = query.getSingleResult();
				coinManagment.increaseCoins(result, coins / divisor * (ranking.size() - i));
			}
			i++;
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
		lifoStack.addLast(topCard);

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
		saveNewCards(pullStack.getCopyOfCardsList());

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
				PullStack toDelete = game.getPullStack();
				game.setPullstack(null);
				entityManager.remove(toDelete);
			}

			if (game.getLiFoStack() != null) {
				LiFoStack toDelete = game.getLiFoStack();
				game.setLiFoStack(null);
				entityManager.remove(toDelete);
			}

			for (Player p : game.getPlayers()) {
				PlayerPile toDelete = p.getPlayerPile();
				PlayerPile newPile = new PlayerPile();
				entityManager.persist(newPile);
				p.setPlayerPile(newPile);
				entityManager.remove(toDelete);
			}

			List<DockPile> openPiles = new ArrayList<>(game.getOpenPiles());
			for (DockPile pile : openPiles) {
				game.getOpenPiles().remove(pile);
				entityManager.remove(pile);
			}
		}
	}

	/**
	 * @author Björn Merschmeier
	 * @author Marc Mettke
	 * @param game
	 */
	private void setNextPlayer(Game game) {
		// Removed recursion to prevent stack overflow when only ai player are playing
		while (true) {
			Player nextPlayer = game.getNextPlayer();
			game.setCurrentPlayer(nextPlayer);

			// end loop when the next player does not have any cards left
			if (nextPlayer.hasNoCards()) {
				countPoints(game);
				initNextRound(game);
				break;
			}
			else if (nextPlayer.hasSkipCard()) {
				nextPlayer.removeSkipCard();
			}
			// don't end loop as long as ai players are taking their turns
			// if there are only ai players than this loop will go on till one of them won
			else if (nextPlayer.getIsAI()) {
				aiTurn(game, nextPlayer);
			}
			// end loop if a valid next player is found (not ai and not skipped)
			else if (!nextPlayer.hasSkipCard()) {
				break;
			}
		}
	}

	private void aiTurn(Game game, Player player) {
		// take card
		switch (aiManagment.takeCard(player, game)) {
		case DISCARD_PILE: {
			takeCardFromLiFoStack(player, game);
		}
		case DRAWER_PILE: {
			takeCardFromPullstack(player, game);
		}
		}

		// do moves
		List<CardsToPileAction> actions;
		do {
			// get moves
			actions = aiManagment.cardsToPile(player, game);

			Collection<DockPile> piles = new ArrayList<>();
			for (CardsToPileAction action : actions) {
				// lay out phase
				if (!action.isDockPileAlreadyExisting()) {
					DockPile dockPile = action.getDockpile();
					for (Card card : action.getCards()) {
						dockPile.addLast(card);
					}
					piles.add(dockPile);
				}
				// add card to existing DockPile
				else {
					for (Card card : action.getCards()) {
						try {
							addToPileOnTable(player, card.getId(), action.getDockpile().getId());
						}
						catch (MoveNotValidException | GameNotInitializedException e) {
							e.printStackTrace();
						}
					}
				}
			}
			if (piles.size() > 0) {
				layPhaseToTable(player, piles, game);
			}
		}
		while (actions.size() > 0);

		// discard card
		Card card = aiManagment.discardCard(player, game);
		layCardToLiFoStack(player, game, card);
	}

	/**
	 * @author Björn Merschmeier
	 * @param game
	 */
	private void countPoints(Game game) {
		List<Player> players = new ArrayList<>(game.getPlayers());

		for (Player player : players) {
			Collection<Card> remainingCards = player.getPlayerPile().getCopyOfCardsList();

			for (Card remainingCard : remainingCards) {
				player.addNegativePoints(remainingCard.getRoundEndValue());
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
