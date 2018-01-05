/**
 *
 */
package de.fh_dortmund.inf.cw.phaseten.server.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.persistence.Basic;
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

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * @author Dennis Schöneborn
 * @author Marc Mettke
 * @author Daniela Kaiser
 * @author Sebastian Seitz
 * @author Björn Merschmeier
 * @author Tim Prange
 */
@NamedQueries({
	@NamedQuery(name="selectByUserId", query="SELECT g FROM Game g "
											+ "JOIN Player p "
											+ "WHERE p.id = :playerId")
})
@Entity
public class Game implements Serializable
{
	private static final long serialVersionUID = 1L;

	public static final int MAX_PLAYER = 6;
	public static final int MIN_PLAYER = 3;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@OneToMany(cascade = CascadeType.PERSIST, mappedBy = "game")
	@Basic(optional = false)
	private List<Player> players;

	@Column
	private int currentPlayer;

	// TODO - BM - 20.12.2017 - Diese Variable soll verwendet werden oder es müssen
	// getter und setter erstellt werden
	@Column
	private int lastRoundBeginner = -1;

	@Basic(optional = false)
	@OneToOne
	@JoinColumn(nullable = false, unique = true)
	private PullStack pullStack;

	@Basic(optional = false)
	@OneToOne
	@JoinColumn(nullable = false, unique = true)
	private LiFoStack liFoStack;

	@OneToMany
	@JoinColumn(unique = true)
	private List<DockPile> openPiles;

	@OneToMany(mappedBy = "game")
	private Set<Spectator> spectators;
	
	@Transient
	private boolean gameInitialized = false;

	/**
	 *
	 */
	private Game() {
		this.players = new ArrayList<>();
		this.spectators = new HashSet<>();
		this.openPiles = new LinkedList<>();
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

	/**
	 * @param p1
	 * @param p2
	 */
	public Game(Player p1, Player p2) {
		this();
		this.addPlayer(p1);
		this.addPlayer(p2);
	}

	/**
	 * @param p1
	 * @param p2
	 * @param p3
	 */
	public Game(Player p1, Player p2, Player p3) {
		this(p1, p2);
		this.addPlayer(p3);
	}

	/**
	 * @param p1
	 * @param p2
	 * @param p3
	 * @param p4
	 */
	public Game(Player p1, Player p2, Player p3, Player p4) {
		this(p1, p2, p3);
		this.addPlayer(p4);
	}

	/**
	 * @param p1
	 * @param p2
	 * @param p3
	 * @param p4
	 * @param p5
	 */
	public Game(Player p1, Player p2, Player p3, Player p4, Player p5) {
		this(p1, p2, p3, p4);
		this.addPlayer(p5);
	}

	/**
	 * @param p1
	 * @param p2
	 * @param p3
	 * @param p4
	 * @param p5
	 * @param p6
	 */
	public Game(Player p1, Player p2, Player p3, Player p4, Player p5, Player p6) {
		this(p1, p2, p3, p4, p5);
		this.addPlayer(p6);
	}

	public Game(ArrayList<?> arrayList, ArrayList<?> arrayList2, PullStack pullStack2, LiFoStack liFoStack2,
			ArrayList<?> arrayList3) {
		// TODO Auto-generated constructor stub
		throw new NotImplementedException();
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

	public void setCurrentPlayer(Player player)
	{
		currentPlayer = players.indexOf(player);
	}
	
	public Player getNextPlayer()
	{
		return players.get((currentPlayer + 1) % players.size());
	}

	public long getId() {
		return id;
	}

	public void setPullstack(PullStack pullStack)
	{
		this.pullStack = pullStack;
	}

	public void setLiFoStack(LiFoStack lifoStack)
	{
		this.liFoStack = lifoStack;
	}
	
	public void setLastRoundBeginner(Player roundBeginner)
	{
		lastRoundBeginner = players.indexOf(roundBeginner);
	}
	
	public Player getLastRoundBeginner()
	{
		Player result = null;
		
		if(lastRoundBeginner != -1)
		{
			result = players.get(lastRoundBeginner);
		}
		
		return result;
	}
	
	public boolean isInitialized()
	{
		return gameInitialized;
	}
	
	public void setInitialized()
	{
		gameInitialized = true;
	}

	private void addPlayer(Player p) {
		this.players.add(p);
		p.setGame(this);
	}
}
