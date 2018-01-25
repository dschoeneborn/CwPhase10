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
 * Lobby Entity.
 * 
 * @author Dennis Schöneborn
 * @author Marc Mettke
 * @author Daniela Kaiser
 * @author Sebastian Seitz
 * @author Björn Merschmeier
 */
@NamedQueries({
		@NamedQuery(name = Lobby.LOBBY_SELECT_LOBBY_BY_USER_ID, query = "SELECT l FROM Lobby l " + "JOIN Player p "
				+ "WHERE p.id = :playerId"),
		@NamedQuery(name = Lobby.LOBBY_SELECT_LATEST, query = "SELECT l FROM Lobby l"),
		@NamedQuery(name = Lobby.SELECT_LOBBY_BY_SPECTATOR_ID, query = "SELECT l FROM Lobby l " + "JOIN Spectator s "
				+ "WHERE s.id = :spectatorId"),
		@NamedQuery(name = Lobby.LOBBY_FIND_BY_ID, query = "SELECT l FROM Lobby l WHERE l.id = :lobbyId") })
@Entity
public class Lobby implements Serializable {

	protected static final String LOBBY_FIND_BY_ID = "lobby.findById";

	protected static final String SELECT_LOBBY_BY_SPECTATOR_ID = "selectLobbyBySpectatorId";

	protected static final String LOBBY_SELECT_LATEST = "lobby.selectLatest";

	protected static final String LOBBY_SELECT_LOBBY_BY_USER_ID = "lobby.selectLobbyByUserId";

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

	/**
	 * Konstruktor.
	 */
	public Lobby() {
		this.players = new HashSet<>();
		this.spectators = new HashSet<>();
	}

	/**
	 * max. Anzahl Spieler erreicht
	 * 
	 * @return isFull
	 */
	public boolean isFull() {
		if (this.players.size() < Game.MAX_PLAYER) {
			return false;
		}
		return true;
	}

	/**
	 * Liefert Anazhl Spieler.
	 * 
	 * @return size
	 */
	public int getNumberOfPlayers() {
		return this.players.size();
	}

	/**
	 * Liefert Anzahl Spectator.
	 * 
	 * @return size
	 */
	public int getNumberOfSpectators() {
		return this.spectators.size();
	}

	/**
	 * Fügt Spieler hinzu, wenn möglich.
	 * 
	 * @param player
	 */
	public void addPlayer(Player player) {
		if (this.players.size() < Game.MAX_PLAYER) {
			this.players.add(player);
			player.setLobby(this);
		}
	}

	/**
	 * Fügt Spectator hinzu.
	 * 
	 * @param spectator
	 */
	public void addSpectator(Spectator spectator) {
		this.spectators.add(spectator);
	}

	/**
	 * entfernt Spieler.
	 * 
	 * @param player
	 */
	public void removePlayer(Player player) {
		this.players.remove(player);
		player.removeLobby();
	}

	/**
	 * entfernt Spectator.
	 * 
	 * @param spectator
	 */
	public void removeSpectator(Spectator spectator) {
		this.spectators.remove(spectator);
	}

	/**
	 * Liefert spieler
	 * 
	 * @return players
	 */
	public Set<Player> getPlayers() {
		return players;
	}

	/**
	 * Liefert Spectator.
	 * 
	 * @return
	 */
	public Set<Spectator> getSpectators() {
		return spectators;
	}

	/**
	 * Vorbereiten auf Schließen
	 */
	public void preRemove() {

		removeSpectators();
		removePlayers();
	}

	/**
	 * Entfernt Spieler.
	 */
	private void removePlayers() {
		for (Player p : new ArrayList<>(players)) {
			removePlayer(p);
		}
	}

	/**
	 * Entfernt Spectator.
	 */
	public void removeSpectators() {
		for (Spectator s : new ArrayList<>(spectators)) {
			removeSpectator(s);
		}
	}

	/**
	 * Liefert id
	 * 
	 * @return id
	 */
	public long getId() {
		return id;
	}
}
