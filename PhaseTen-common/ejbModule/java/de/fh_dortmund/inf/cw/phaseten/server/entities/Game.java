/**
 *
 */
package de.fh_dortmund.inf.cw.phaseten.server.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 * 
 * Game Entity.
 * 
 * @author Dennis Schöneborn
 * @author Marc Mettke
 * @author Daniela Kaiser
 * @author Sebastian Seitz
 * @author Björn Merschmeier
 * @author Tim Prange
 */
@NamedQueries({ @NamedQuery(name = Game.GAME_SELECT_BY_USER_ID, query = "SELECT g FROM Game g " + "JOIN Player p "
		+ "WHERE p.id = :playerId") })
@Entity
public class Game implements Serializable {
	public static final String GAME_SELECT_BY_USER_ID = "selectByUserId";

	private static final long serialVersionUID = 1L;

	public static final int MAX_PLAYER = 6;
	public static final int MIN_PLAYER = 2;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@OneToMany(mappedBy = "game")
	private List<Player> players;

	@Column
	private int currentPlayer;

	@Column
	private int lastRoundBeginner = -1;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(nullable = false, unique = true)
	private PullStack pullStack;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(nullable = false, unique = true)
	private LiFoStack liFoStack;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "GAME_ID")
	private List<DockPile> openPiles;

	@OneToMany(mappedBy = "game")
	private List<Spectator> spectators;

	@Column
	private boolean gameInitialized = false;

	@Column
	private boolean gameFinished = false;

	private Game() {
		openPiles = new ArrayList<>();
		spectators = new ArrayList<>();
	}

	/**
	 * Konstruktor.
	 * 
	 * @param players
	 * @param spectators
	 * @param pullStack
	 * @param discardPile
	 */
	public Game(Set<Player> players, Set<Spectator> spectators, PullStack pullStack, LiFoStack discardPile) {
		this(players, spectators);

		this.setPullstack(pullStack);
		this.setLiFoStack(discardPile);
	}

	/**
	 * Konstruktor.
	 * 
	 * @param players
	 * @param spectators
	 */
	public Game(Set<Player> players, Set<Spectator> spectators) {
		this();

		for (Player player : players) {
			this.addPlayer(player);
		}

		for (Spectator spectator : spectators) {
			this.addSpectator(spectator);
		}
	}

	/**
	 * Fügt Spectator hinzu.
	 * 
	 * @param s
	 */
	public void addSpectator(Spectator s) {
		this.spectators.add(s);
		s.setGame(this);
	}

	/**
	 * Liefert openPiles.
	 * 
	 * @return the openPiles
	 */
	public List<DockPile> getOpenPiles() {
		return openPiles;
	}

	/**
	 * 
	 * Fügt openPile hinzu.
	 * 
	 * @param pile
	 */
	public void addOpenPile(DockPile pile) {
		this.openPiles.add(pile);
	}

	/**
	 * Liefert player
	 * 
	 * @return player
	 */
	public Collection<Player> getPlayers() {
		return players;
	}

	/**
	 * Liefert spectators.
	 * 
	 * @return spectators
	 */
	public Collection<Spectator> getSpectators() {
		return spectators;
	}

	/**
	 * Liefert pullStack.
	 * 
	 * @return pullStack
	 */
	public PullStack getPullStack() {
		return pullStack;
	}

	/**
	 * Liefert Lifostack.
	 * 
	 * @return
	 */
	public LiFoStack getLiFoStack() {
		return liFoStack;
	}

	/**
	 * Liefert aktuellen Spieler.
	 * 
	 * @return currentPlayer
	 */
	public Player getCurrentPlayer() {
		if (currentPlayer == -1) {
			return null;
		}
		return players.get(currentPlayer);
	}

	/**
	 * setzt aktuellen Spieler.
	 * 
	 * @param player
	 */
	public void setCurrentPlayer(Player player) {
		currentPlayer = players.indexOf(player);
	}

	/**
	 * Liefert nächsten Spieler.
	 * 
	 * @return nextPlayer
	 */
	public Player getNextPlayer() {
		return players.get((currentPlayer + 1) % players.size());
	}

	/**
	 * Liefert Id.
	 * 
	 * @return id
	 */
	public long getId() {
		return id;
	}

	/**
	 * Setzt PullStack.
	 * 
	 * @param pullStack
	 */
	public void setPullstack(PullStack pullStack) {
		this.pullStack = pullStack;
	}

	/**
	 * Setzt LiFoStack.
	 * 
	 * @param lifoStack
	 */
	public void setLiFoStack(LiFoStack lifoStack) {
		this.liFoStack = lifoStack;
	}

	/**
	 * Setzt letzten Runden Beginner.
	 * 
	 * @param roundBeginner
	 */
	public void setLastRoundBeginner(Player roundBeginner) {
		lastRoundBeginner = players.indexOf(roundBeginner);
	}

	/**
	 * Liefert letzten RundenBeginner.
	 * 
	 * @return lastRoundBeginner
	 */
	public Player getLastRoundBeginner() {
		Player result = null;

		if (lastRoundBeginner != -1) {
			result = players.get(lastRoundBeginner);
		}

		return result;
	}

	/**
	 * initialisiert?
	 * 
	 * @return gameInitialized
	 */
	public boolean isInitialized() {
		return gameInitialized;
	}

	/**
	 * Setzt initialisiert true.
	 */
	public void setInitialized() {
		gameInitialized = true;
	}

	/**
	 * beendet?
	 * 
	 * @return gameFinished
	 */
	public boolean isFinished() {
		return gameFinished;
	}

	/**
	 * Setzt beendet true.
	 */
	public void setFinished() {
		gameFinished = true;
	}

	/**
	 * Entfernt Spectator.
	 * 
	 * @param spectator
	 */
	public void removeSpectator(Spectator spectator) {
		spectator.removeGame();
		this.spectators.remove(spectator);
	}

	/**
	 * Entfernt Spieler.
	 * 
	 * @param player
	 */
	public void removePlayer(Player player) {
		player.removeGame();
		this.players.remove(player);
	}

	/**
	 * Fügt Spieler hinzu.
	 * 
	 * @param player
	 */
	private void addPlayer(Player player) {
		if (this.players == null) {
			this.players = new ArrayList<>();
		}
		this.players.add(player);
		player.setGame(this);
	}
}
