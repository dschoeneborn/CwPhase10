package de.fh_dortmund.inf.cw.phaseten.server.messages;

import java.io.Serializable;
import java.util.Collection;

/**
 * @author Marc Mettke
 */
public class Lobby implements Serializable {
	private static final long serialVersionUID = -8449426368470253288L;
	
	private Collection<Player> players;
	private Collection<Player> spectator;
	
	public Lobby(Collection<Player> players, Collection<Player> spectator) {
		super();
		this.players = players;
		this.spectator = spectator;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public Collection<Player> getPlayers() {
		return players;
	}
	
	public Collection<Player> getSpectator() {
		return spectator;
	}
	
	public static Lobby from(de.fh_dortmund.inf.cw.phaseten.server.entities.Lobby lobby,
					  Collection<Player> spectator,
					  int phase) {
		// TODO: Add Spectators from lobby Object
		return new Lobby(
			Player.from(lobby.getPlayers(), phase),
			spectator
		);
	}
}
