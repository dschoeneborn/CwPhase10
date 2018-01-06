package de.fh_dortmund.inf.cw.phaseten.server.messages;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import de.fh_dortmund.inf.cw.phaseten.server.entities.Card;
import de.fh_dortmund.inf.cw.phaseten.server.entities.DockPile;
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
	private Collection<DockPile> openPiles;

	/**
	 * Constructor for GameGuiData.java
	 *
	 * @param players
	 * @param spectators
	 * @param liFoStackTop
	 * @param openPiles
	 */
	public GameGuiData(Collection<PlayerGuiData> players, Collection<String> spectators, Card liFoStackTop,
			Collection<DockPile> openPiles) {
		super();
		this.players = players;
		this.spectators = spectators;
		this.liFoStackTop = liFoStackTop;
		this.openPiles = openPiles;
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

	public Collection<DockPile> getOpenPiles() {
		return openPiles;
	}

	public static GameGuiData from(Game game) {
		ArrayList<String> spectatorNames = new ArrayList<>();

		for (Spectator spectator : game.getSpectators()) {
			spectatorNames.add(spectator.getName());
		}

		return new GameGuiData(PlayerGuiData.from(game.getPlayers()), spectatorNames, game.getLiFoStack().showCard(),
				game.getOpenPiles());
	}
}
