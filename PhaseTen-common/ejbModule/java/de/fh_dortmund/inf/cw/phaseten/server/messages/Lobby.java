package de.fh_dortmund.inf.cw.phaseten.server.messages;

import java.io.Serializable;
import java.util.Collection;

import de.fh_dortmund.inf.cw.phaseten.server.entities.Spectator;

/**
 * @author Marc Mettke
 */
public class Lobby implements Serializable {
	private static final long serialVersionUID = -8449426368470253288L;
	
	private Collection<Player> players;
	private Collection<Spectator> spectators;
	
	public Lobby(Collection<Player> players, Collection<Spectator> spectators) {
		super();
		this.players = players;
		this.spectators = spectators;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public Collection<Player> getPlayers() {
		return players;
	}
	
	public Collection<Spectator> getSpectators() {
		return spectators;
	}
	
	public static Lobby from(de.fh_dortmund.inf.cw.phaseten.server.entities.Lobby lobby) {
		// TODO: Add Spectators from lobby Object
		return new Lobby(
			Player.from(lobby.getPlayers()),
			lobby.getSpectators()
		);
	}
}
