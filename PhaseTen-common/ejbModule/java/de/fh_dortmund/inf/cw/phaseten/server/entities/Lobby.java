/**
 * 
 */
package de.fh_dortmund.inf.cw.phaseten.server.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import de.fh_dortmund.inf.cw.phaseten.server.exceptions.NoFreeSlotException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.NotEnoughPlayerException;

/**
 * @author Dennis Schöneborn
 * @author Marc Mettke
 * @author Daniela Kaiser
 * @author Sebastian Seitz
 */
@Entity
public class Lobby implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@OneToMany(cascade = CascadeType.PERSIST, mappedBy = "lobby")
	@Basic(optional = false)
	@JoinColumn(nullable = false)
	private Set<Player> players;

	@OneToMany(cascade=CascadeType.PERSIST)
	@JoinColumn
	private Set<Spectator> spectators;

	public Lobby() {
		this.players = new HashSet<>();
		this.spectators = new HashSet<>();
	}

	public boolean isFull() {
		if (this.players.size() < Game.MAX_PLAYER)
			return false;

		return true;

	}

	public int getNumberOfPlayers() {
		return this.players.size();
	}
	
	public int getNumberOfSpectators() {
		return this.spectators.size();
	}
	
	public void addPlayer(Player player) {
		if (this.players.size() < Game.MAX_PLAYER) {
			this.players.add(player);
			player.setLobby(this);
		}
	}

	public void addSpectator(Spectator spectator) {
		this.spectators.add(spectator);
	}
	
	public void removePlayer(Player player) {
		this.players.remove(player);
	}

	public void removeSpectator(Spectator spectator) {
		this.spectators.remove(spectator);
	}

	public Set<Player> getPlayers() {
		return players;
	}
	
	public Set<Spectator> getSpectators()
	{
		return spectators;
	}

	public Game startGame() throws NotEnoughPlayerException, NoFreeSlotException{
		Game game = new Game(this.players, this.spectators);
		
		players.clear();
		spectators.clear();
		
		return game;
	}
}
