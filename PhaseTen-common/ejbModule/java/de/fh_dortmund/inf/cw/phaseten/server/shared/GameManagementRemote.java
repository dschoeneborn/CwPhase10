package de.fh_dortmund.inf.cw.phaseten.server.shared;

import javax.ejb.Remote;

import de.fh_dortmund.inf.cw.phaseten.server.entities.Game;
import de.fh_dortmund.inf.cw.phaseten.server.entities.Player;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.GameNotInitializedException;

/**
 * @author Marc Mettke
 * @author Björn Merschmeier
 */
@Remote
public interface GameManagementRemote extends GameManagement {

	void requestGameMessage(Player p) throws GameNotInitializedException;

	boolean isInGame(Player orCreateCurrentPlayer);
}
