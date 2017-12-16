package de.fh_dortmund.inf.cw.phaseten.server.messages;

import de.fh_dortmund.inf.cw.phaseten.server.entities.PlayerPile;

/**
 * @author Marc Mettke
 */
public class CurrentPlayer extends Player {
	private static final long serialVersionUID = -8171079521185318013L;
	
	private PlayerPile playerPile;
	private String status;
	private int coins;

	public CurrentPlayer(String playerName, 
						 int phase, 
						 PlayerPile playerPile, 
						 String status, 
						 int coins) {
		super(playerName, phase);
		this.playerPile = playerPile;
		this.status = status;
		this.coins = coins;
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
		// TODO: Add phase, status, coins from player Object
		return new CurrentPlayer(
			player.getName(),
			phase, 
			player.getPlayerPile(), 
			status, 
			coins
		);
	}
}
