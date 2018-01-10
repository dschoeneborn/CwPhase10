/**
 *
 */
package de.fh_dortmund.inf.cw.phaseten.server.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
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
import javax.persistence.Transient;

/**
 * @author Dennis Schöneborn
 * @author Marc Mettke
 * @author Daniela Kaiser
 * @author Sebastian Seitz
 * @author Björn Merschmeier
 * @author Tim Prange
 */
@NamedQueries({ @NamedQuery(name = "selectByUserId", query = "SELECT g FROM Game g " + "JOIN Player p "
		+ "WHERE p.id = :playerId") })
@Entity
public class Game implements Serializable {
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

	@OneToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(nullable = false, unique = true)
	private PullStack pullStack;

	@OneToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(nullable = false, unique = true)
	private LiFoStack liFoStack;

	@OneToMany(cascade = CascadeType.PERSIST)
	@JoinColumn(unique = true)
	private List<DockPile> openPiles;

	@OneToMany(mappedBy = "game")
	private Set<Spectator> spectators;

	@Transient
	private boolean gameInitialized = false;
	
	private Game()
	{
		openPiles = new ArrayList<DockPile>();
		spectators = new HashSet<Spectator>();
	}

	/**
	 * @param arrayList2 
	 * @param discardPile 
	 * @param pullStack2 
	 * @param hashSet 
	 * @param arrayList 
	 *
	 */
	public Game(Set<Player> players, Set<Spectator> spectators, PullStack pullStack2, LiFoStack discardPile)
	{
		this(players, spectators);

		this.setPullstack(pullStack2);
		this.setLiFoStack(discardPile);
	}

	public Game(Set<Player> players, Set<Spectator> spectators) {
		this();

		for (Player player : players) {
			this.addPlayer(player);
		}

		for (Spectator spectator : spectators) {
			this.addSpectator(spectator);
		}
	}

	public void addSpectator(Spectator s) {
		this.spectators.add(s);
		s.setGame(this);
	}

	/**
	 * @return the openPiles
	 */
	public List<DockPile> getOpenPiles() {
		return openPiles;
	}

	/**
	 * @param pile
	 */
	public void addOpenPile(DockPile pile) {
		this.openPiles.add(pile);
	}

	public List<Player> getPlayers() {
		return players;
	}

	public Set<Spectator> getSpectators() {
		return spectators;
	}

	public PullStack getPullStack() {
		return pullStack;
	}

	public LiFoStack getLiFoStack() {
		return liFoStack;
	}

	public Player getCurrentPlayer() {
		if (currentPlayer == -1) {
			return null;
		}
		return players.get(currentPlayer);
	}

	public void setCurrentPlayer(Player player) {
		currentPlayer = players.indexOf(player);
	}

	public Player getNextPlayer() {
		return players.get((currentPlayer + 1) % players.size());
	}

	public long getId() {
		return id;
	}

	public void setPullstack(PullStack pullStack) {
		this.pullStack = pullStack;
	}

	public void setLiFoStack(LiFoStack lifoStack) {
		this.liFoStack = lifoStack;
	}

	public void setLastRoundBeginner(Player roundBeginner) {
		lastRoundBeginner = players.indexOf(roundBeginner);
	}

	public Player getLastRoundBeginner() {
		Player result = null;

		if (lastRoundBeginner != -1) {
			result = players.get(lastRoundBeginner);
		}

		return result;
	}

	public boolean isInitialized() {
		return gameInitialized;
	}

	public void setInitialized() {
		gameInitialized = true;
	}

	private void addPlayer(Player p) {
		if(this.players == null)
		{
			this.players = new ArrayList<>();
		}
		this.players.add(p);
		p.setGame(this);
	}
}
