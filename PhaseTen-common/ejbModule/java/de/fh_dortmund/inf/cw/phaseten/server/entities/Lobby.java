/**
 * 
 */
package de.fh_dortmund.inf.cw.phaseten.server.entities;

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

/**
 * @author Dennis Schöneborn
 * @author Marc Mettke
 * @author Daniela Kaiser
 * @author Sebastian Seitz
 */
@Entity
public class Lobby {

	private static final int MAX_PLAYER = 6;

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

	@SuppressWarnings("unused")
	private Lobby() {

	}

	public Lobby(Player host) {
		this.players = new HashSet<>();
		this.players.add(host);
		this.spectators = new HashSet<>();
		host.setLobby(this);
	}

	public boolean isFull() {
		if (this.players.size() < MAX_PLAYER)
			return false;

		return true;

	}

	public int getNumberOfUsers() {
		return this.players.size();
	}

	public void addPlayer(Player player) {
		if (this.players.size() < MAX_PLAYER) {
			this.players.add(player);
			player.setLobby(this);
		}

	}

	public void addSpectator(Spectator spectator) {
		this.spectators.add(spectator);
	}

	public void removeSpectator(Spectator spectator) {
		this.spectators.remove(spectator);
	}

	public Set<Player> getPlayers() {
		return players;
	}

	public Game startGame() {
		// TODO Game erstellen

		return null;
	}
}
