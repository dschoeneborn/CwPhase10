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

	public PlayerGuiData(String playerName, int phase, long id, int negativePoints, int roundStage) {
		this.phase = phase;
		this.name = playerName;
		this.negativePoints = negativePoints;
		this.id = id;
		this.roundStage = roundStage;
	}

	public long getId()
	{
		return id;
	}

	public int getPhase() {
		return phase;
	}

	public String getName() {
		return name;
	}

	public int getNegativePoints()
	{
		return negativePoints;
	}

	public int getRoundStage() {
		return roundStage;
	}

	public static PlayerGuiData from(Player player) {
		return new PlayerGuiData(player.getName(), player.getPhase().getValue(), player.getId(), player.getNegativePoints(), player.getRoundStage().getValue());
	}

	public static Collection<PlayerGuiData> from(Collection<Player> players) {
		Collection<PlayerGuiData> _players = new ArrayList<>();

		for (Player player : players) {
			_players.add(PlayerGuiData.from(player));
		}
		return _players;
	}
}
