package de.fh_dortmund.inf.cw.phaseten.server.messages;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import de.fh_dortmund.inf.cw.phaseten.server.entities.Player;

/**
 * @author Marc Mettke
 * @author Robin Harbecke
 * @author Tim Prange
 * @author Björn Merschmeier
 * @author Sven Krefeld
 */
public class PlayerGuiData implements Serializable {
	private static final long serialVersionUID = -2947823309554723821L;

	private int phase;
	private String name;
	private long id;
	private int negativePoints;
	private int roundStage;
	private int remainingCards;

	/**
	 * Initializes a new player-object with all information the gui needs
	 * @param playerName
	 * @param phase
	 * @param id
	 * @param negativePoints
	 * @param roundStage
	 */
	public PlayerGuiData(String playerName, int phase, long id, int negativePoints, int roundStage, int remainingCards) {
		this.phase = phase;
		this.name = playerName;
		this.negativePoints = negativePoints;
		this.id = id;
		this.roundStage = roundStage;
		this.remainingCards = remainingCards;
	}

	/**
	 * Returns the id
	 * @return id of the player
	 */
	public long getId()
	{
		return id;
	}

	/**
	 * Returns the Phase in which the player is currently
	 * @return the phase of the player in the current game
	 */
	public int getPhase() {
		return phase;
	}

	/**
	 * Returns the display-name of the player
	 * @return display-name of the player
	 */
	public String getName() {
		return name;
	}

	/**
	 * Returns negative points of the player in the current game
	 * @return the sum of all negative points of every round
	 */
	public int getNegativePoints()
	{
		return negativePoints;
	}

	/**
	 * Describes what the player is allowed to do in his current situation
	 * @return roundstage
	 */
	public int getRoundStage() {
		return roundStage;
	}

	public int getRemainingCards()
	{
		return remainingCards;
	}

	/**
	 * Generates a playerguidata-object from the real player object
	 * @param player
	 * @return
	 */
	public static PlayerGuiData from(Player player) {
		return new PlayerGuiData(player.getName(), player.getPhase().getValue(), player.getId(), player.getNegativePoints(), player.getRoundStage().getValue(), player.getPlayerPile().getSize());
	}

	/**
	 * Does the same like the other from method, but with a collection of players
	 * @param players
	 * @return
	 */
	public static Collection<PlayerGuiData> from(Collection<Player> players) {
		Collection<PlayerGuiData> _players = new ArrayList<>();

		for (Player player : players) {
			_players.add(PlayerGuiData.from(player));
		}
		return _players;
	}
}
