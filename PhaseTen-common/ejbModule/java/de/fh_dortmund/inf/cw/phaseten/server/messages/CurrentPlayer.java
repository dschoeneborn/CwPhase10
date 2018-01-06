package de.fh_dortmund.inf.cw.phaseten.server.messages;

import de.fh_dortmund.inf.cw.phaseten.server.entities.PlayerPile;
import de.fh_dortmund.inf.cw.phaseten.server.entities.Stage;

/**
 * @author Marc Mettke
 */
public class CurrentPlayer extends Player {
	private static final long serialVersionUID = -8171079521185318013L;

	private boolean playerLaidStage;
	private PlayerPile playerPile;
	private String status;
	private int coins;

	public CurrentPlayer(String playerName, 
						 Stage phase, 
						 PlayerPile playerPile, 
						 String status, 
						 int coins,
						 boolean playerLaidStage) {
		super(playerName, phase);
		this.playerPile = playerPile;
		this.status = status;
		this.coins = coins;
		this.playerLaidStage = playerLaidStage;
	}

	public boolean getPlayerLaidStage() {
		return playerLaidStage;
	}	

	public PlayerPile getPlayerPile() {
		return playerPile;
	}
	
	public String getStatus() {
		return status;
	}

	public int getCoins() {
		return coins;
	}
	
	public static CurrentPlayer from(de.fh_dortmund.inf.cw.phaseten.server.entities.Player player,
							  int phase, 
							  String status, 
							  int coins) {
		return new CurrentPlayer(
			player.getName(),
			player.getPhase(), 
			player.getPlayerPile(), 
			status, 
			coins,
			player.playerLaidStage()
		);
	}
}
