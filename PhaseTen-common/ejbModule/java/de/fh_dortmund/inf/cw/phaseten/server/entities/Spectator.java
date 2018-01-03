/**
 * 
 */
package de.fh_dortmund.inf.cw.phaseten.server.entities;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * @author Dennis Schöneborn
 * @author Sebastian Seitz
 * @author Daniela Kaiser
 *
 */
@Entity
public class Spectator implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3498449127463809540L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@ManyToOne(cascade=CascadeType.PERSIST)
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

	/**
	 * @return the game
	 */
	public Game getGame() {
		return game;
	}

	/**
	 * @param game the game to set
	 */
	public void setGame(Game game) {
		this.game = game;
	}
}
