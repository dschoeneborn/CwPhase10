/**
 * 
 */
package de.fh_dortmund.inf.cw.phaseten.server.entities;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * @author Dennis Schöneborn
 * @author Marc Mettke
 */
public class Game {
	private Set<Player> player;
	private Set<Spectator> spectators;
	private PullStack pullStack;
	private LiFoStack liFoStack;
	private List<DockPile> openPiles;

	/**
	 * 
	 */
	private Game() {
		this.player = new HashSet<>();
		this.spectators = new HashSet<>();
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
	
	/**
	 * Help method to add a Player including association
	 * @param p
	 */
	private void addPlayer(Player p)
	{
		this.player.add(p);
		p.setGame(this);
	}
	
	/**
	 * @param spectator
	 */
	public void addSpectator(Spectator spectator)
	{
		this.spectators.add(spectator);
	}
	
	/**
	 * @param spectator
	 */
	public void removeSpectator(Spectator spectator)
	{
		this.spectators.remove(spectator);
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
	public void addOpenPile(DockPile pile)
	{
		this.openPiles.add(pile);
	}

	public Set<Player> getPlayer() {
		return player;
	}

	public PullStack getPullStack() {
		return pullStack;
	}

	public LiFoStack getLiFoStack() {
		return liFoStack;
	}
	
	
	
	
	//TODO Fassadenmethoden für die eintelnen Stacks
	
}
