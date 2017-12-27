/**
 * 
 */
package de.fh_dortmund.inf.cw.phaseten.server.entities;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

/**
 * @author Dennis Schöneborn
 * @author Marc Mettke
 * @author Daniela Kaiser
 * @author Björn Merschmeier
 */
@Entity
public class Player {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@OneToOne(cascade = CascadeType.ALL)
	@Basic(optional = false)
	@JoinColumn(nullable = false, unique=true)
	private PlayerPile playerPile;

	@Column(nullable = false, unique = true)
	@Basic(optional = false)
	private String name;

	@JoinColumn
	@ManyToOne(cascade = CascadeType.PERSIST)
	private Game game;

	@JoinColumn
	@ManyToOne(cascade = CascadeType.PERSIST)
	private Lobby lobby;
	
	@Enumerated(EnumType.ORDINAL)
	@Column(nullable = false)
	@Basic(optional = false)
	private Stage phase;

	@Column(nullable = false)
	@Basic(optional = false)
	private RoundStage roundStage;
	
	@Column(nullable = false)
	@Basic(optional = false)
	private boolean playerLaidStage;

	@Column(nullable = false)
	@Basic(optional = false)
	private boolean playerHasSkipCard;
	
	private Player()
	{
		this.playerPile = new PlayerPile();
		this.phase = Stage.TWO_TRIPLES;
		playerLaidStage = false;
		playerHasSkipCard = false;
		roundStage = RoundStage.PULL;
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

	public long getId() {
		return id;
	}

	public RoundStage getRoundStage() {
		return roundStage;
	}

	/***
	 * @author Björn Merschmeier
	 */
	public void addRoundStage() {
		this.roundStage = RoundStage.getRoundStageValue(this.roundStage.getValue()+1);
	}

	public Stage getPhase() {
		return phase;
	}

	/***
	 * @author Björn Merschmeier
	 */
	public void addPhase() {
		this.phase = Stage.getStage(this.phase.getValue()+1);
	}

	public boolean playerLaidStage() {
		return playerLaidStage;
	}

	public void setPlayerLaidStage(boolean playerLaidStage) {
		this.playerLaidStage = playerLaidStage;
	}

	public boolean hasSkipCard() {
		return playerHasSkipCard;
	}

	public void givePlayerSkipCard() {
		this.playerHasSkipCard = true;
	}
	
}
