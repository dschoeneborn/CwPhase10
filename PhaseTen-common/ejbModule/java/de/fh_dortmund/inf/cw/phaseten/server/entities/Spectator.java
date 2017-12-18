/**
 * 
 */
package de.fh_dortmund.inf.cw.phaseten.server.entities;

/**
 * @author Dennis Schöneborn
 *
 */
public class Spectator {
	private Game game;
	
	private Spectator()
	{
		
	}
	
	public Spectator(Game game)
	{
		this();
		this.game = game;
	}
}
