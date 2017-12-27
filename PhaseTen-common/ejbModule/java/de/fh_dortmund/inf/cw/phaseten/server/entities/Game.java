/**
 * 
 */
package de.fh_dortmund.inf.cw.phaseten.server.entities;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 * @author Dennis Schöneborn
 * @author Marc Mettke
 * @author Daniela Kaiser
 * @author Sebastian Seitz
 * @author Björn Merschmeier
 */
@Entity
public class Game implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@OneToMany(cascade = CascadeType.PERSIST, mappedBy = "game")
	@Basic(optional = false)
	private List<Player> players;

	@Column
	private int currentPlayer;

	// TODO - DK an BM - 27.12.2017 - Bitte keine mehrfachen Verweise auf Player
	// (bei Verwendung bitte äquivalent zum currentPlayer nur den Index speichern)
	// TODO - BM - 20.12.2017 - Diese Variable soll verwendet werden oder es müssen
	// getter und setter erstellt werden
	// private Player lastFirstPlayer;

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
	private List<Spectator> spectators;

	/**
	 * 
	 */
	private Game() {
		this.players = new LinkedList<>();
		this.spectators = new LinkedList<>();
		this.pullStack = new PullStack();
		this.liFoStack = new LiFoStack();
		this.openPiles = new LinkedList<>();
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

	private void addPlayer(Player p) {
		this.players.add(p);
		p.setGame(this);
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

	public List<Spectator> getSpectators() {
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
		this.currentPlayer = players.indexOf(player);
	}

	// TODO Fassadenmethoden für die eintelnen Stacks

}
