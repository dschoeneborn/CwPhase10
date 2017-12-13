/**
 * 
 */
package de.fh_dortmund.inf.cw.phaseten.server.entities;

/**
 * @author Dennis Schöneborn
 *
 */
public class Player {
	private PlayerPile playerPile;
	private String name;
	private Game game;
	private Lobby lobby;
	
	private Player()
	{
		this.playerPile = new PlayerPile();
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
	
}
