package de.fh_dortmund.inf.cw.phaseten.server.messages;

import java.io.Serializable;
import java.util.Collection;

import de.fh_dortmund.inf.cw.phaseten.server.entities.DockPile;
import de.fh_dortmund.inf.cw.phaseten.server.entities.LiFoStack;
import de.fh_dortmund.inf.cw.phaseten.server.entities.PullStack;

/**
 * @author Marc Mettke
 * @author Robin Harbecke
 * @author Björn Merschmeier
 */
public class Game implements Serializable {
	private static final long serialVersionUID = -8803043695255479666L;

	private Collection<Player> players;
	private Collection<Spectator> spectators;
	private PullStack pullStack;
	private LiFoStack liFoStack;
	private Collection<DockPile> openPiles;
	
	public Game(Collection<Player> players, 
			    Collection<Spectator> spectators, 
			    PullStack pullStack, 
			    LiFoStack liFoStack, 
			    Collection<DockPile> openPiles) {
		super();
		this.players = players;
		this.spectators = spectators;
		this.pullStack = pullStack;
		this.liFoStack = liFoStack;
		this.openPiles = openPiles;
	}
	
	public Collection<Player> getPlayers() {
		return players;
	}
	
	public Collection<Spectator> getSpectators() {
		return spectators;
	}	
	
	public PullStack getPullStack() {
		return pullStack;
	}
	
	public LiFoStack getLiFoStack() {
		return liFoStack;
	}
	
	public Collection<DockPile> getOpenPiles() {
		return openPiles;
	}
	
	public static Game from(de.fh_dortmund.inf.cw.phaseten.server.entities.Game game) {
		return new Game(
			Player.from(game.getPlayers()),  
			Spectator.from(game.getSpectators()), 
			game.getPullStack(),
			game.getLiFoStack(),
			game.getOpenPiles()
		);	
	}	
}
