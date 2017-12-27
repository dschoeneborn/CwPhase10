/**
 * 
 */
package de.fh_dortmund.inf.cw.phaseten.server.entities;

/**
 * @author Dennis Schöneborn
 * @author Marc Mettke
 * @author Björn Merschmeier
 */
public class Player {
	private PlayerPile playerPile;
	private String name;
	private Game game;
	private Lobby lobby;
	private Stage phase;
	private long id;
	private RoundStage roundStage;
	private boolean playerLaidStage;
	private boolean playerHasSkipCard;
	
	private Player()
	{
		this.playerPile = new PlayerPile();
		this.phase = Stage.TWO_TRIPLES;
	}
	
	public Player(String name)
	{
		this();
		this.name = name;
	}
	
	public void setGame(Game game)
	{
		this.game = game;
	}
	
	public void removeGame()
	{
		this.game = null;
	}
	
	public void setLobby(Lobby lobby)
	{
		this.lobby = lobby;
	}
	
	public void removeLobby()
	{
		this.lobby = null;
	}

	public PlayerPile getPlayerPile() {
		return playerPile;
	}

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
