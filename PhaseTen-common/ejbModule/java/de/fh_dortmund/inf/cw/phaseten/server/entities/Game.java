/**
 * 
 */
package de.fh_dortmund.inf.cw.phaseten.server.entities;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import de.fh_dortmund.inf.cw.phaseten.server.exceptions.NoFreeSlotException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.NotEnoughPlayerException;

/**
 * @author Dennis Schöneborn
 * @author Marc Mettke
 * @author Daniela Kaiser
 * @author Sebastian Seitz
 */
@Entity
public class Game {

	public static final int MAX_PLAYER = 6;
	public static final int MIN_PLAYER = 3;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@OneToMany(cascade = CascadeType.PERSIST, mappedBy = "game")
	@Basic(optional = false)
	private List<Player> players;

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

	public Game(Set<Player> players, Set<Spectator> spectators) throws NotEnoughPlayerException, NoFreeSlotException {
		this();
		if (players.size() < MIN_PLAYER)
			throw new NotEnoughPlayerException();
		if (players.size() > MAX_PLAYER)
			throw new NoFreeSlotException();

		players.forEach(player -> this.addPlayer(player));
		spectators.forEach(spectator -> this.addSpectator(spectator));
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

	public List<Spectator> getSpectators() {
		return spectators;
	}

	public PullStack getPullStack() {
		return pullStack;
	}

	public LiFoStack getLiFoStack() {
		return liFoStack;
	}

	// TODO Fassadenmethoden für die eintelnen Stacks

}
