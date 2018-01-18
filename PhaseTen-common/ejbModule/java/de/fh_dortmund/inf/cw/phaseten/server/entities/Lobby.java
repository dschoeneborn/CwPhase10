/**
 *
 */
package de.fh_dortmund.inf.cw.phaseten.server.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

/**
 * @author Dennis Schöneborn
 * @author Marc Mettke
 * @author Daniela Kaiser
 * @author Sebastian Seitz
 * @author Björn Merschmeier
 */
@NamedQueries({
		@NamedQuery(name = "lobby.selectLobbyByUserId", query = "SELECT l FROM Lobby l "
				+ "JOIN Player p " + "WHERE p.id = :playerId"),
		@NamedQuery(name = "lobby.selectLatest", query = "SELECT l FROM Lobby l"),
		@NamedQuery(name = "selectLobbyBySpectatorId", query = "SELECT l FROM Lobby l " + "JOIN Spectator s "
				+ "WHERE s.id = :spectatorId"),
		@NamedQuery(name = "lobby.findById", query = "SELECT l FROM Lobby l WHERE l.id = :lobbyId") })
@Entity
public class Lobby implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 5336567507324327686L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@OneToMany(mappedBy = "lobby")
	@JoinColumn(nullable = false)
	private Set<Player> players;

	@OneToMany
	@JoinTable
	private Set<Spectator> spectators;

	public Lobby() {
		this.players = new HashSet<>();
		this.spectators = new HashSet<>();
	}

	public boolean isFull() {
		if (this.players.size() < Game.MAX_PLAYER) {
			return false;
		}

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
		player.removeLobby();
	}

	public void removeSpectator(Spectator spectator) {
		this.spectators.remove(spectator);
	}

	public Set<Player> getPlayers() {
		return players;
	}

	public Set<Spectator> getSpectators() {
		return spectators;
	}

	public void preRemove() {
		
		removeSpectators();
		removePlayers();
	}

	public long getId() {
		return id;
	}

	public void removeSpectators() {
		for(Spectator s : new ArrayList<>(spectators))
		{
			removeSpectator(s);
		}
	}
	
	private void removePlayers()
	{
		for(Player p: new ArrayList<>(players))
		{
			removePlayer(p);
		}
	}
}
