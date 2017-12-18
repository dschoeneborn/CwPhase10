/**
 * 
 */
package de.fh_dortmund.inf.cw.phaseten.server.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * @author Dennis Schöneborn
 * @author Sebastian Seitz
 *
 */
@Entity
public class Spectator {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@ManyToOne
	@JoinColumn(name="GAME_ID")
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
