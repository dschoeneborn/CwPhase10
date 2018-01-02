package de.fh_dortmund.inf.cw.phaseten.server.messages;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Consumer;

/**
 * @author Marc Mettke
 * @author Robin Harbecke
 */
public class Player extends Spectator implements Serializable {
	private static final long serialVersionUID = -2947823309554723821L;
	
	private int phase;
	
	public Player(String playerName, int phase) {
		super(playerName);
		this.phase = phase;
	}
	
	public int getPhase() {
		return phase;
	}
	
	public static Player from(de.fh_dortmund.inf.cw.phaseten.server.entities.Player player,
							  int phase) {
		// TODO: Add phase from player Object
		return new Player(
			player.getName(),
			phase
		);
	}
	
	public static Collection<Player> from(Collection<de.fh_dortmund.inf.cw.phaseten.server.entities.Player> players,
							  int phase) {
		Collection<Player> _players = new ArrayList<>();
		players.forEach(new Consumer<de.fh_dortmund.inf.cw.phaseten.server.entities.Player>() {
			@Override
			public void accept(de.fh_dortmund.inf.cw.phaseten.server.entities.Player player) {
				_players.add(from(player, phase));
			}
		});
		return _players;
	}
}
