/**
 *
 */
package de.fh_dortmund.inf.cw.phaseten.server.entities;

import java.io.Serializable;

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
 * @author Björn Merschmeier
 */
@Entity
public class Spectator implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = -3498449127463809540L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@ManyToOne()
	@JoinColumn(name = "GAME_ID")
	private Game game;

	private String name;

	@SuppressWarnings("unused")
	private Spectator() {

	}

	public Spectator(String name) {
		this.name = name;
	}

	public Spectator(String name, Game game) {
		this(name);
		this.game = game;
	}

	/**
	 * @return the game
	 */
	public Game getGame() {
		return game;
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	protected void removeGame()
	{
		this.game = null;
	}

	protected void setGame(Game game) {
		this.game = game;
	}
}
