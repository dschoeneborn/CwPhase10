/**
 *
 */
package de.fh_dortmund.inf.cw.phaseten.server.beans;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Remove;
import javax.ejb.Stateful;

import de.fh_dortmund.inf.cw.phaseten.server.entities.Card;
import de.fh_dortmund.inf.cw.phaseten.server.entities.ColorDockPile;
import de.fh_dortmund.inf.cw.phaseten.server.entities.DockPile;
import de.fh_dortmund.inf.cw.phaseten.server.entities.Player;
import de.fh_dortmund.inf.cw.phaseten.server.entities.SequenceDockPile;
import de.fh_dortmund.inf.cw.phaseten.server.entities.SetDockPile;
import de.fh_dortmund.inf.cw.phaseten.server.entities.Spectator;
import de.fh_dortmund.inf.cw.phaseten.server.entities.User;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.GameNotInitializedException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.InsufficientCoinSupplyException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.MoveNotValidException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.NoFreeSlotException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.NotEnoughPlayersException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.NotLoggedInException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.PlayerDoesNotExistsException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.UserDoesNotExistException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.UserIsPlayerException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.UserIsSpectatorException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.UsernameAlreadyTakenException;
import de.fh_dortmund.inf.cw.phaseten.server.messages.PlayerGuiData;
import de.fh_dortmund.inf.cw.phaseten.server.shared.CoinManagementLocal;
import de.fh_dortmund.inf.cw.phaseten.server.shared.GameManagementLocal;
import de.fh_dortmund.inf.cw.phaseten.server.shared.LobbyManagementLocal;
import de.fh_dortmund.inf.cw.phaseten.server.shared.UserManagementLocal;
import de.fh_dortmund.inf.cw.phaseten.server.shared.UserSessionLocal;
import de.fh_dortmund.inf.cw.phaseten.server.shared.UserSessionRemote;

/**
 * @author Dennis Schöneborn
 * @author Marc Mettke
 * @author Björn Merschmeier
 * @author Sebastian Seitz
 */
@Stateful
public class UserSessionBean implements UserSessionRemote, UserSessionLocal {
	private User currentUser = null;

	@EJB
	private UserManagementLocal userManagement;

	@EJB
	private LobbyManagementLocal lobbyManagement;

	@EJB
	private GameManagementLocal gameManagement;

	@EJB
	private CoinManagementLocal coinManagment;

	/* (non-Javadoc)
	 * @see de.fh_dortmund.inf.cw.phaseten.server.shared.UserSession#login(java.lang.String, java.lang.String)
	 */
	@Override
	public void login(String userName, String password) throws UserDoesNotExistException {
		currentUser = userManagement.login(userName, password);
	}

	/* (non-Javadoc)
	 * @see de.fh_dortmund.inf.cw.phaseten.server.shared.UserSession#register(java.lang.String, java.lang.String)
	 */
	@Override
	public void register(String userName, String password) throws UsernameAlreadyTakenException {
		currentUser = userManagement.register(userName, password);
	}

	/**
	 * @author Björn Merschmeier
	 * @throws UserIsSpectatorException
	 */
	/* (non-Javadoc)
	 * @see de.fh_dortmund.inf.cw.phaseten.server.shared.UserSession#getOrCreatePlayer()
	 */
	@Override
	public Player getOrCreatePlayer() throws NotLoggedInException, UserIsSpectatorException {
		return getOrCreateCurrentPlayer();
	}

	/**
	 * @author Björn Merschmeier
	 * @throws UserIsPlayerException
	 */
	/* (non-Javadoc)
	 * @see de.fh_dortmund.inf.cw.phaseten.server.shared.UserSession#getOrCreateSpectator()
	 */
	@Override
	public Spectator getOrCreateSpectator() throws NotLoggedInException, UserIsPlayerException {
		return userManagement.getOrCreateSpectator(currentUser);
	}

	/**
	 * @author Björn Merschmeier
	 */
	/* (non-Javadoc)
	 * @see de.fh_dortmund.inf.cw.phaseten.server.shared.UserSession#logout()
	 */
	@Override
	public void logout() {
		userManagement.logout(currentUser);
	}

	/**
	 * @author Björn Merschmeier
	 */
	/* (non-Javadoc)
	 * @see de.fh_dortmund.inf.cw.phaseten.server.shared.UserSession#getUser()
	 */
	@Override
	public User getUser() {
		return currentUser;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.fh_dortmund.inf.cw.phaseten.server.shared.UserSessionRemote#
	 * enterLobbyAsPlayer()
	 */
	/**
	 * @author Tim Prange
	 * @author Marc Mettke
	 * @throws UserIsSpectatorException
	 */
	@Override
	public void enterLobbyAsPlayer() throws NoFreeSlotException, PlayerDoesNotExistsException, NotLoggedInException,
	InsufficientCoinSupplyException, UserIsSpectatorException {
		if (currentUser == null) {
			throw new NotLoggedInException();
		}
		this.coinManagment.decreaseCoins(currentUser, 50);
		this.lobbyManagement.enterLobby(getOrCreateCurrentPlayer());

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.fh_dortmund.inf.cw.phaseten.server.shared.UserSessionRemote#
	 * enterLobbyAsSpectator()
	 */
	/**
	 * @author Tim Prange
	 * @throws UserIsPlayerException
	 */
	@Override
	public void enterLobbyAsSpectator() throws NotLoggedInException, UserIsPlayerException {
		this.lobbyManagement.enterLobby(getOrCreateCurrentSpectator());

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * de.fh_dortmund.inf.cw.phaseten.server.shared.UserSessionRemote#startGame()
	 */
	/**
	 * @author Tim Prange
	 * @throws UserIsSpectatorException
	 */
	@Override
	public void startGame() throws NotEnoughPlayersException, PlayerDoesNotExistsException, NotLoggedInException, UserIsSpectatorException {
		this.lobbyManagement.startGame(getOrCreateCurrentPlayer());
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.fh_dortmund.inf.cw.phaseten.server.shared.UserSessionRemote#
	 * takeCardFromPullstack()
	 */
	/**
	 * @author Tim Prange
	 * @throws UserIsSpectatorException
	 */
	@Override
	public void takeCardFromPullstack()
			throws MoveNotValidException, NotLoggedInException, GameNotInitializedException, UserIsSpectatorException {
		gameManagement.takeCardFromPullstack(getOrCreateCurrentPlayer());

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.fh_dortmund.inf.cw.phaseten.server.shared.UserSessionRemote#
	 * takeCardFromLiFoStack()
	 */
	/**
	 * @author Tim Prange
	 * @throws UserIsSpectatorException
	 */
	@Override
	public void takeCardFromLiFoStack()
			throws MoveNotValidException, NotLoggedInException, GameNotInitializedException, UserIsSpectatorException {
		gameManagement.takeCardFromLiFoStack(getOrCreateCurrentPlayer());

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.fh_dortmund.inf.cw.phaseten.server.shared.UserSessionRemote#
	 * addToPileOnTable(de.fh_dortmund.inf.cw.phaseten.server.entities.Card,
	 * de.fh_dortmund.inf.cw.phaseten.server.entities.DockPile)
	 */
	/**
	 * @author Tim Prange
	 * @author Björn Merschmeier
	 * @throws UserIsSpectatorException
	 */
	@Override
	public void addToPileOnTable(long cardId, long dockPileId, boolean tryToAttachToFront)
			throws MoveNotValidException, NotLoggedInException, GameNotInitializedException, UserIsSpectatorException {
		gameManagement.addToPileOnTable(getOrCreateCurrentPlayer(), cardId, dockPileId, tryToAttachToFront);

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.fh_dortmund.inf.cw.phaseten.server.shared.UserSessionRemote#
	 * layPhaseToTable(java.util.Collection)
	 */
	/**
	 * @author Tim Prange
	 * @throws UserIsSpectatorException
	 */
	@Override
	public void layPhaseToTable(Collection<DockPile> cards)
			throws MoveNotValidException, NotLoggedInException, GameNotInitializedException, UserIsSpectatorException {
		gameManagement.layPhaseToTable(getOrCreateCurrentPlayer(), cards);

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.fh_dortmund.inf.cw.phaseten.server.shared.UserSessionRemote#
	 * layCardToLiFoStack(de.fh_dortmund.inf.cw.phaseten.server.entities.Card)
	 */
	/**
	 * @author Tim Prange
	 * @author Björn Merschmeier
	 * @throws UserIsSpectatorException
	 */
	@Override
	public void layCardToLiFoStack(long cardId)
			throws MoveNotValidException, NotLoggedInException, GameNotInitializedException, UserIsSpectatorException {
		gameManagement.layCardToLiFoStack(getOrCreateCurrentPlayer(), cardId);

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.fh_dortmund.inf.cw.phaseten.server.shared.UserSessionRemote#
	 * laySkipCardForPlayer(long,
	 * de.fh_dortmund.inf.cw.phaseten.server.entities.Card)
	 */
	/**
	 * @author Tim Prange
	 * @author Björn Merschmeier
	 * @throws UserIsSpectatorException
	 */
	@Override
	public void laySkipCardForPlayer(long destinationPlayerId, long cardId) throws MoveNotValidException,
	NotLoggedInException, PlayerDoesNotExistsException, GameNotInitializedException, UserIsSpectatorException {
		gameManagement.laySkipCardForPlayerById(getOrCreateCurrentPlayer(), destinationPlayerId, cardId);

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * de.fh_dortmund.inf.cw.phaseten.server.shared.UserSessionRemote#exitLobby()
	 */
	/**
	 * @author Tim Prange
	 * @throws UserIsSpectatorException
	 */
	@Override
	public void exitLobby() throws NotLoggedInException
	{
		try
		{
			lobbyManagement.leaveLobby(getOrCreateCurrentPlayer());
		}
		catch(UserIsSpectatorException e)
		{

		}
		try
		{
			lobbyManagement.leaveLobby(getOrCreateCurrentSpectator());
		}
		catch(UserIsPlayerException e)
		{

		}

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * de.fh_dortmund.inf.cw.phaseten.server.shared.UserSessionRemote#playerIsInGame
	 * ()
	 */
	/**
	 * @author Tim Prange
	 * @throws UserIsSpectatorException
	 */
	@Override
	public boolean playerIsInGame() throws NotLoggedInException, UserIsSpectatorException {
		return gameManagement.isInGame(getOrCreateCurrentPlayer());
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.fh_dortmund.inf.cw.phaseten.server.shared.UserSessionRemote#
	 * getLobbyPlayers()
	 */
	/**
	 * @author Tim Prange
	 */
	@Override
	public Collection<PlayerGuiData> getLobbyPlayers() {
		return lobbyManagement.getPlayersForGui();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.fh_dortmund.inf.cw.phaseten.server.shared.UserSessionRemote#
	 * getLobbySpectators()
	 */
	/**
	 * @author Tim Prange
	 */
	@Override
	public Collection<String> getLobbySpectators() {
		return lobbyManagement.getSpectatorNamesForGui();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.fh_dortmund.inf.cw.phaseten.server.shared.UserSessionRemote#
	 * requestPlayerMessage()
	 */
	/**
	 * @author Tim Prange
	 * @throws UserIsSpectatorException
	 */
	@Override
	public void requestPlayerMessage() throws PlayerDoesNotExistsException, NotLoggedInException, UserIsSpectatorException {
		userManagement.requestPlayerMessage(getOrCreateCurrentPlayer());

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see de.fh_dortmund.inf.cw.phaseten.server.shared.UserSessionRemote#
	 * requestLobbyMessage()
	 */
	/**
	 * @author Tim Prange
	 */
	@Override
	public void requestLobbyMessage() {
		this.lobbyManagement.requestLobbyMessage();

	}

	@Override
	public Collection<Card> getCards() throws NotLoggedInException, UserIsSpectatorException {
		return getOrCreateCurrentPlayer().getPlayerPile().getCopyOfCardsList();
	}

	@Override
	@Remove
	public void unregister(String password) throws NotLoggedInException, PlayerDoesNotExistsException {
		if (currentUser != null) {
			userManagement.unregister(currentUser, password);
		} else {
			throw new NotLoggedInException();
		}
	}

	/**
	 * @author Björn Merschmeier
	 * @throws NotLoggedInException
	 * @throws UserIsSpectatorException
	 */
	/* (non-Javadoc)
	 * @see de.fh_dortmund.inf.cw.phaseten.server.shared.UserSessionRemote#getDockPileTypesForPlayer()
	 */
	@Override
	public Collection<Class<? extends DockPile>> getDockPileTypesForPlayer() throws NotLoggedInException, UserIsSpectatorException {
		List<Class<? extends DockPile>> result = new ArrayList<>();

		Player p = getOrCreateCurrentPlayer();

		switch(p.getPhase())
		{
		case TWO_TRIPLES: result.add(SetDockPile.class); result.add(SetDockPile.class); break;
		case TRIPLE_AND_SEQUENCE_OF_FOUR: result.add(SetDockPile.class); result.add(SequenceDockPile.class); break;
		case QUADRUPLE_AND_SEQUENCE_OF_FOUR: result.add(SetDockPile.class); result.add(SequenceDockPile.class); break;
		case SEQUENCE_OF_SEVEN: result.add(SequenceDockPile.class); break;
		case SEQUENCE_OF_EIGHT: result.add(SequenceDockPile.class); break;
		case SEQUENCE_OF_NINE: result.add(SequenceDockPile.class); break;
		case TWO_QUADRUPLES: result.add(SetDockPile.class); result.add(SetDockPile.class); break;
		case SEVEN_OF_ONE_COLOR: result.add(ColorDockPile.class); break;
		case QUINTUPLE_AND_TWIN: result.add(SetDockPile.class); result.add(SetDockPile.class); break;
		case QUINTUPLE_AND_TRIPLE: result.add(SetDockPile.class); result.add(SetDockPile.class); break;
		default:
			break;
		}

		return result;
	}

	/* (non-Javadoc)
	 * @see de.fh_dortmund.inf.cw.phaseten.server.shared.UserSessionRemote#addAI()
	 */
	@Override
	public void addAI() throws NoFreeSlotException {
		this.lobbyManagement.addAI();
	}

	/**
	 * @author Björn Merschmeier
	 */
	@Override
	public boolean isUserInGame() throws NotLoggedInException {
		return currentUser != null && ((currentUser.getPlayer() != null && currentUser.getPlayer().getGame() != null)
				|| (currentUser.getSpectator() != null && currentUser.getSpectator().getGame() != null));
	}

	/**
	 * Returns the current Player. If a current Player does not exist, a new one will be created and returned.
	 *
	 * @author Tim Prange
	 * @return current or new Player
	 * @throws UserIsSpectatorException
	 */
	private Player getOrCreateCurrentPlayer() throws NotLoggedInException, UserIsSpectatorException {
		if (currentUser == null) {
			throw new NotLoggedInException();
		} else {
			return userManagement.getOrCreatePlayer(currentUser);
		}
	}

	/**
	 * Returns the current Spectator. If a current Spectator does not exist, a new
	 * one will be created and returned.
	 *
	 * @author Tim Prange
	 * @return current or new Spectator
	 * @throws UserIsPlayerException
	 */
	private Spectator getOrCreateCurrentSpectator() throws UserIsPlayerException {
		return userManagement.getOrCreateSpectator(currentUser);
	}

}
