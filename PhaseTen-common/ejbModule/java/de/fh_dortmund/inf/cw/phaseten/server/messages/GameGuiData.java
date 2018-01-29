package de.fh_dortmund.inf.cw.phaseten.server.messages;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import de.fh_dortmund.inf.cw.phaseten.server.entities.Card;
import de.fh_dortmund.inf.cw.phaseten.server.entities.Game;
import de.fh_dortmund.inf.cw.phaseten.server.entities.Spectator;

/**
 * @author Marc Mettke
 * @author Robin Harbecke
 * @author Björn Merschmeier
 */
public class GameGuiData implements Serializable {
	private static final long serialVersionUID = -8803043695255479666L;

	private Collection<PlayerGuiData> players;
	private Collection<String> spectators;
	private Card liFoStackTop;
	private Collection<OpenPileGuiData> openPiles;
	private PlayerGuiData currentPlayer;
	private boolean isFinished;

	/**
	 * Constructor for GameGuiData.java
	 *
	 * @param players
	 * @param spectators
	 * @param liFoStackTop
	 * @param openPiles
	 */
	public GameGuiData(Collection<PlayerGuiData> players, Collection<String> spectators, Card liFoStackTop,
			Collection<OpenPileGuiData> openPiles, PlayerGuiData currentPlayer, boolean isFinished) {
		super();
		this.players = players;
		this.spectators = spectators;
		this.liFoStackTop = liFoStackTop;
		this.openPiles = openPiles;
		this.currentPlayer = currentPlayer;
	}

	public PlayerGuiData getCurrentPlayer()
	{
		return currentPlayer;
	}

	public Collection<PlayerGuiData> getPlayers() {
		return players;
	}

	public Collection<String> getSpectators() {
		return spectators;
	}

	/**
	 * Returns liFoStackTop
	 *
	 * @return the liFoStackTop
	 */
	public Card getLiFoStackTop() {
		return liFoStackTop;
	}

	/**
	 * Returns all open piles "on the table"
	 * @return
	 */
	public Collection<OpenPileGuiData> getOpenPiles() {
		return openPiles;
	}

	/**
	 * Checks if game is finished
	 * @return
	 */
	public boolean isFinished() {
		return isFinished;
	}

	/**
	 * Generates a GameGuiData-Object from the real game-object which is persisted in the server
	 * @param game
	 * @return
	 */
	public static GameGuiData from(Game game) {
		ArrayList<String> spectatorNames = new ArrayList<>();

		for (Spectator spectator : game.getSpectators()) {
			spectatorNames.add(spectator.getName());
		}

		return new GameGuiData(PlayerGuiData.from(game.getPlayers()), spectatorNames, game.getLiFoStack().showCard(),
				OpenPileGuiData.from(game.getOpenPiles()), PlayerGuiData.from(game.getCurrentPlayer()), game.isFinished());
	}
}
