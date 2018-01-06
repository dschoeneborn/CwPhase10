package de.fh_dortmund.inf.cw.phaseten.server.shared;

import java.util.Collection;

import javax.ejb.Local;

import de.fh_dortmund.inf.cw.phaseten.server.entities.Player;
import de.fh_dortmund.inf.cw.phaseten.server.entities.Spectator;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.GameNotInitializedException;

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
	void startGame(Collection<Player> players, Collection<Spectator> spectators);

	void requestGameMessage(Player player) throws GameNotInitializedException;

	boolean isInGame(Player player);
}
