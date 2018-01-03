package de.fh_dortmund.inf.cw.phaseten.server.beans;

import java.util.Collection;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.JMSContext;
import javax.jms.Message;
import javax.jms.Topic;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import de.fh_dortmund.inf.cw.phaseten.server.entities.Card;
import de.fh_dortmund.inf.cw.phaseten.server.entities.CardValue;
import de.fh_dortmund.inf.cw.phaseten.server.entities.DockPile;
import de.fh_dortmund.inf.cw.phaseten.server.entities.Game;
import de.fh_dortmund.inf.cw.phaseten.server.entities.Player;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.MoveNotValidException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.PlayerDoesNotExistsException;
import de.fh_dortmund.inf.cw.phaseten.server.shared.GameManagmentLocal;
import de.fh_dortmund.inf.cw.phaseten.server.shared.GameManagmentRemote;
import de.fh_dortmund.inf.cw.phaseten.server.shared.GameValidationLocal;
import de.fh_dortmund.inf.cw.phaseten.server.shared.UserManagementLocal;

/**
 * @author Marc Mettke
 * @author Björn Merschmeier
 * @author Tim Prange
 */
@Stateless
public class GameManagementBean implements GameManagmentRemote, GameManagmentLocal {
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
	public void requestGameMessage(Player p) {
		sendGameMessage(p);
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
	public void takeCardFromPullstack(Player player) throws MoveNotValidException {
		// TODO - BM - 31.12.2017 - Player vielleicht aus einer Playerbean auslesen und
		// nicht als Parameter übergeben lassen?
		Game game = getActualPlayedGame(player);
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
	 */
	@Override
	public void takeCardFromLiFoStack(Player player) throws MoveNotValidException {
		// TODO - BM - 31.12.2017 - Player vielleicht aus einer Playerbean auslesen und
		// nicht als Parameter übergeben lassen?
		Game game = getActualPlayedGame(player);
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
	 */
	@Override
	public void addToPileOnTable(Player player, Card card, DockPile dockPile) throws MoveNotValidException {
		// TODO - BM - 31.12.2017 - Player vielleicht aus einer Playerbean auslesen und
		// nicht als Parameter übergeben lassen?
		Game game = getActualPlayedGame(player);
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
	 */
	@Override
	public void layPhaseToTable(Player player, Collection<DockPile> piles) throws MoveNotValidException {
		// TODO - BM - 31.12.2017 - Player vielleicht aus einer Playerbean auslesen und
		// nicht als Parameter übergeben lassen?
		Game game = getActualPlayedGame(player);
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
	 */
	@Override
	public void layCardToLiFoStack(Player player, Card card) throws MoveNotValidException {
		// TODO - BM - 31.12.2017 - Player vielleicht aus einer Playerbean auslesen und
		// nicht als Parameter übergeben lassen?
		Game game = getActualPlayedGame(player);
		if (gameValidation.isValidPushCardToLiFoStack(game, player, card)) {
			game.getLiFoStack().addCard(card);
			player.resetRoundStage();
			game.nextCurrentPlayer();
		}
		else {
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
			throws MoveNotValidException, PlayerDoesNotExistsException {
		laySkipCardForPlayerById(currentPlayer, destinationPlayer.getId(), card);
	}

	@Override
	public void laySkipCardForPlayerById(Player currentPlayer, long destinationPlayerId, Card card)
			throws MoveNotValidException, PlayerDoesNotExistsException {
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
				&& gameValidation.isValidLaySkipCard(currentPlayer, destinationPlayer, game)) {
			currentPlayer.removeCardFromPlayerPile(card);
			destinationPlayer.givePlayerSkipCard();
			currentPlayer.resetRoundStage();
			game.nextCurrentPlayer();
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
	public void startGame(Game game) {
		entityManager.persist(game);
		entityManager.flush();
		sendGameMessage(game);
	}

	private void updateClient(Player p) {
		// Send updated Game to Client
		sendGameMessage(p);
		// Send updated Player Cards to Client
		playerManagment.sendPlayerMessage(p);
	}

	@SuppressWarnings("unused")
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
	private Game getActualPlayedGame(Player p) {
		Query query = entityManager.createNamedQuery("selectByUserId");
		query.setParameter("playerId", p.getId());
		
		return (Game)query.getSingleResult();
	}
}
