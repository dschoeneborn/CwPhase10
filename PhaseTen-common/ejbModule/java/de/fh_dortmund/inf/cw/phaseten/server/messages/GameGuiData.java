package de.fh_dortmund.inf.cw.phaseten.server.messages;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import de.fh_dortmund.inf.cw.phaseten.server.entities.DockPile;
import de.fh_dortmund.inf.cw.phaseten.server.entities.LiFoStack;
import de.fh_dortmund.inf.cw.phaseten.server.entities.PullStack;
import de.fh_dortmund.inf.cw.phaseten.server.entities.Spectator;

/**
 * @author Marc Mettke
 * @author Robin Harbecke
 * @author Björn Merschmeier
 */
public class GameGuiData implements Serializable {
	private static final long serialVersionUID = -8803043695255479666L;

	private Collection<PlayerGuiData> players;
	private Collection<String> spectators;
	private PullStack pullStack;
	private LiFoStack liFoStack;
	private Collection<DockPile> openPiles;
	
	public GameGuiData(Collection<PlayerGuiData> players, 
			    Collection<String> spectators, 
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
	
	public Collection<PlayerGuiData> getPlayers() {
		return players;
	}
	
	public Collection<String> getSpectators() {
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
	
	public static GameGuiData from(de.fh_dortmund.inf.cw.phaseten.server.entities.Game game)
	{
		ArrayList<String> spectatorNames = new ArrayList<String>();
		
		for(Spectator spectator : game.getSpectators())
		{
			spectatorNames.add(spectator.getName());
		}
		
		return new GameGuiData(
			PlayerGuiData.from(game.getPlayers()),  
			spectatorNames, 
			game.getPullStack(),
			game.getLiFoStack(),
			game.getOpenPiles()
		);	
	}	
}
