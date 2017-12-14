package de.fh_dortmund.inf.cw.phaseten.server.messages;

import java.io.Serializable;
import java.util.Collection;

import de.fh_dortmund.inf.cw.phaseten.server.entities.DockPile;
import de.fh_dortmund.inf.cw.phaseten.server.entities.LiFoStack;
import de.fh_dortmund.inf.cw.phaseten.server.entities.PullStack;

/**
 * @author Marc Mettke
 */
public class Game implements Serializable {
	private static final long serialVersionUID = -8803043695255479666L;

	private Collection<Player> players;
	private Collection<Player> spectator;
	private PullStack pullStack;
	private LiFoStack liFoStack;
	private Collection<DockPile> openPiles;
	
	public Game(Collection<Player> players, 
			    Collection<Player> spectator, 
			    PullStack pullStack, 
			    LiFoStack liFoStack, 
			    Collection<DockPile> openPiles) {
		super();
		this.players = players;
		this.spectator = spectator;
		this.pullStack = pullStack;
		this.liFoStack = liFoStack;
		this.openPiles = openPiles;
	}
	
	public Collection<Player> getPlayers() {
		return players;
	}
	
	public Collection<Player> getSpectator() {
		return spectator;
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
	
	public static Game from(de.fh_dortmund.inf.cw.phaseten.server.entities.Game game,
						    Collection<Player> spectator,
						    int phase) {
		// TODO: Get Spectators from Game
		return new Game(
			Player.from(game.getPlayer(), phase),  
			spectator, 
			game.getPullStack(),
			game.getLiFoStack(),
			game.getOpenPiles()
		);	
	}
}
