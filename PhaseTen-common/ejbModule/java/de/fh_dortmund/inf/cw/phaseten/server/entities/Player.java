/**
 * 
 */
package de.fh_dortmund.inf.cw.phaseten.server.entities;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 * @author Dennis Schöneborn
 * @author Marc Mettke
 * @author Daniela Kaiser
 */
public class Player {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(nullable = false)
	@OneToOne(cascade = CascadeType.ALL)
	@Basic(optional = false)
	@JoinColumn(name="PLAYERPILE_ID", unique=true)
	private PlayerPile playerPile;

	@Column(nullable = false, unique = true)
	@Basic(optional = false)
	private String name;

	@Column
	@ManyToMany(cascade = CascadeType.PERSIST, mappedBy = "players")
	private Game game;

	@Column
	@ManyToOne(cascade = CascadeType.PERSIST)
	private Lobby lobby;

	private Player() {
		this.playerPile = new PlayerPile();
	}

	/**
	 * 
	 * @param name
	 */
	public Player(String name) {
		this();
		this.name = name;
	}

	/**
	 * 
	 * @param game
	 */
	public void setGame(Game game) {
		this.game = game;
	}

	public void removeGame() {
		this.game = null;
	}

	public Game getGame() {
		return game;
	}

	public Lobby getLobby() {
		return lobby;
	}

	/**
	 * 
	 * @param lobby
	 */
	public void setLobby(Lobby lobby) {
		this.lobby = lobby;
	}

	public void removeLobby() {
		this.lobby = null;
	}

	/**
	 * 
	 * @return playerPile
	 */
	public PlayerPile getPlayerPile() {
		return playerPile;
	}

	/**
	 * 
	 * @return name
	 */
	public String getName() {
		return name;
	}

}
