/**
 *
 */
package de.fh_dortmund.inf.cw.phaseten.server.entities;

import java.io.Serializable;
import java.util.NoSuchElementException;

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

import de.fh_dortmund.inf.cw.phaseten.server.enumerations.RoundStage;
import de.fh_dortmund.inf.cw.phaseten.server.enumerations.Stage;

/**
 * Player Entity.
 * 
 * @author Dennis Schöneborn
 * @author Marc Mettke
 * @author Daniela Kaiser
 * @author Björn Merschmeier
 */
@Entity
public class Player implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(nullable = false, unique = true)
	private PlayerPile playerPile;

	@Column(nullable = false, unique = true)
	private String name;

	@JoinColumn
	@ManyToOne()
	private Game game;

	@JoinColumn
	@ManyToOne()
	private Lobby lobby;

	@OneToOne(mappedBy = "player")
	private User user;

	@Enumerated(EnumType.ORDINAL)
	@Column(nullable = false)
	private Stage phase;

	@Enumerated(EnumType.ORDINAL)
	@Column(nullable = false)
	private RoundStage roundStage;

	@Column(nullable = false)
	private boolean playerLaidStage;

	@Column(nullable = false)
	private boolean playerHasSkipCard;

	@Column(nullable = false)
	private int negativePoints;

	@Column(nullable = false)
	private boolean isAI;

	/**
	 * Konstruktor.
	 */
	private Player() {
		this.playerPile = new PlayerPile();
		this.phase = Stage.TWO_TRIPLES;
		playerLaidStage = false;
		playerHasSkipCard = false;
		isAI = false;
		roundStage = RoundStage.PULL;
	}

	/**
	 * Konstruktor.
	 * 
	 * @param name
	 */
	public Player(String name) {
		this();
		this.name = name;
	}

	/**
	 * Liefert Game.
	 * 
	 * @return
	 */
	public Game getGame() {
		return game;
	}

	/**
	 * Liefert Lobby.
	 * 
	 * @return
	 */
	public Lobby getLobby() {
		return lobby;
	}

	/**
	 * Liefert User.
	 * 
	 * @return
	 */
	public User getUser() {
		return user;
	}

	/**
	 * Setzt User.
	 * 
	 * @param user
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * Liefert PlayerPile
	 * 
	 * @return playerPile
	 */
	public PlayerPile getPlayerPile() {
		return playerPile;
	}

	/**
	 * Setzt PlayerPile.
	 * 
	 * @param playerPile
	 */
	public void setPlayerPile(PlayerPile playerPile) {
		this.playerPile = playerPile;
	}

	/**
	 * Fügt Karte hinzu.
	 * 
	 * @param c
	 */
	public void addCardToPlayerPile(Card c) {
		playerPile.addLast(c);
	}

	/**
	 * Entfernt Karte.
	 * 
	 * @param c
	 * @throws NoSuchElementException
	 */
	public void removeCardFromPlayerPile(Card c) throws NoSuchElementException {
		playerPile.removeCard(c);
	}

	/**
	 * Liefert Namen.
	 * 
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Liefert Id.
	 * 
	 * @return id
	 */
	public long getId() {
		return id;
	}

	/**
	 * Liefert Round Stage.
	 * 
	 * @return roundStage
	 */
	public RoundStage getRoundStage() {
		return roundStage;
	}

	/**
	 * Fügt Round Stage hinzu.
	 * 
	 * @author Björn Merschmeier
	 */
	public void addRoundStage() {
		this.roundStage = RoundStage.getRoundStageValue(this.roundStage.getValue() + 1);
	}

	/**
	 * Liefert Phase.
	 * 
	 * @return
	 */
	public Stage getPhase() {
		return phase;
	}

	/**
	 * Fügt Phase hinzu
	 * 
	 * @author Björn Merschmeier
	 */
	public void addPhase() {
		this.phase = Stage.getStage(this.phase.getValue() + 1);
	}

	/**
	 * playerLaidStage?
	 * 
	 * @return playerLaidStage
	 */
	public boolean playerLaidStage() {
		return playerLaidStage;
	}

	/**
	 * Setzt playerLaidStage
	 * 
	 * @param playerLaidStage
	 */
	public void setPlayerLaidStage(boolean playerLaidStage) {
		this.playerLaidStage = playerLaidStage;
	}

	/**
	 * hat skip Karte?
	 * 
	 * @return playerHasSkipCard
	 */
	public boolean hasSkipCard() {
		return playerHasSkipCard;
	}

	/**
	 * Bekomme Skip Karte.
	 */
	public void givePlayerSkipCard() {
		this.playerHasSkipCard = true;
	}

	/**
	 * Entferne Skip Karte
	 */
	public void removeSkipCard() {
		this.playerHasSkipCard = false;
	}

	/**
	 * RoundStage = Pull
	 */
	public void resetRoundStage() {
		this.roundStage = RoundStage.PULL;
	}

	/**
	 * hat keine Karten?
	 * 
	 * @returnhasNoCards
	 */
	public boolean hasNoCards() {
		return this.playerPile.getCopyOfCardsList().size() == 0;
	}

	/**
	 * Liefert Abzugpunte.
	 * 
	 * @return negativePoints
	 */
	public int getNegativePoints() {
		return negativePoints;
	}

	/**
	 * Fügt Abzugpunkte hinzu.
	 * 
	 * @param i
	 */
	public void addNegativePoints(int i) {
		negativePoints += i;
	}

	/**
	 * Ist AI?
	 * 
	 * @return isAI
	 */
	public boolean getIsAI() {
		return isAI;
	}

	/**
	 * Setzt isAI
	 * 
	 * @param isAI
	 */
	public void setIsAI(boolean isAI) {
		this.isAI = isAI;
	}

	/**
	 * Setzt Game
	 * 
	 * @param game
	 */
	protected void setGame(Game game) {
		this.game = game;
	}

	/**
	 * Setzt Game zurück.
	 */
	protected void removeGame() {
		this.game = null;
	}

	/**
	 * Setzt Lobby.
	 * 
	 * @param lobby
	 */
	protected void setLobby(Lobby lobby) {
		this.lobby = lobby;
	}

	/**
	 * Delete Lobby from Player
	 */
	protected void removeLobby() {
		this.lobby = null;
	}

}
