package de.fh_dortmund.inf.cw.phaseten.server.shared;

import javax.ejb.Local;

import de.fh_dortmund.inf.cw.phaseten.server.entities.Game;
import de.fh_dortmund.inf.cw.phaseten.server.entities.Player;

/**
 * @author Marc Mettke
 * @author Tim Prange
 */
@Local
public interface GameManagementLocal extends GameManagement {
	public void sendGameMessage(Player p);

	/**
	 * Starts the Game. This Methods needs to be called before all other Methods
	 * <br>
	 * It persists the game so afterwords the persistet game is used
	 *
	 * @author Tim Prange
	 * @param game the game to put into that bean
	 */
	void startGame(Game game);

	void sendGameMessage(Game game);
}
