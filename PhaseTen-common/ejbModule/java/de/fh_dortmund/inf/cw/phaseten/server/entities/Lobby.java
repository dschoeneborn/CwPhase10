/**
 * 
 */
package de.fh_dortmund.inf.cw.phaseten.server.entities;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Dennis Schöneborn
 * @author Marc Mettke
 */
public class Lobby {
	private static final int MAX_PLAYER = 6;
	private Set<Player> player;
	private Set<Spectator> spectators;

	public Lobby(Player host) {
		this.player = new HashSet<>();
		this.spectators = new HashSet<>();
		
		this.player.add(host);
		host.setLobby(this);
	}

	public boolean isFull() {
		if (this.player.size() < MAX_PLAYER)
			return false;

		return true;

	}

	public int getNumberOfUsers() {
		return this.player.size();
	}

	public void addPlayer(Player player) {
		if (this.player.size() < MAX_PLAYER)
		{
			this.player.add(player);
			player.setLobby(this);
		}
			
	}
	
	public Set<Player> getPlayer() {
		return player;
	}
	
	public void addSpectator(Spectator spectator)
	{
		this.spectators.add(spectator);
	}
	
	public void removeSpectator(Spectator spectator)
	{
		this.spectators.remove(spectator);
	}

	public Game startGame()
	{
		//TODO Game erstellen
		
		return null;
	}
}
