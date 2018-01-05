package de.fh_dortmund.inf.cw.phaseten.server.messages;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Consumer;

/**
 * @author Marc Mettke
 * @author Robin Harbecke
 */
public class PlayerGuiData implements Serializable {
	private static final long serialVersionUID = -2947823309554723821L;
	
	private int phase;
	private String name;
	
	public PlayerGuiData(String playerName, int phase) {
		this.phase = phase;
		this.name = playerName;
	}
	
	public int getPhase() {
		return phase;
	}
	
	public String getName()
	{
		return name;
	}
	
	public static PlayerGuiData from(de.fh_dortmund.inf.cw.phaseten.server.entities.Player player) {
		// TODO: Add phase from player Object
		return new PlayerGuiData(
			player.getName(),
			player.getPhase().getValue()
		);
	}
	
	public static Collection<PlayerGuiData> from(Collection<de.fh_dortmund.inf.cw.phaseten.server.entities.Player> players) {
		Collection<PlayerGuiData> _players = new ArrayList<>();
		players.forEach(new Consumer<de.fh_dortmund.inf.cw.phaseten.server.entities.Player>() {
			@Override
			public void accept(de.fh_dortmund.inf.cw.phaseten.server.entities.Player player) {
				_players.add(from(player));
			}
		});
		return _players;
	}
}
