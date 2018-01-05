package de.fh_dortmund.inf.cw.phaseten.server.shared;

import java.util.Collection;

import javax.ejb.Remote;

import de.fh_dortmund.inf.cw.phaseten.server.entities.Card;
import de.fh_dortmund.inf.cw.phaseten.server.entities.Player;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.GameNotInitializedException;

/**
 * @author Marc Mettke
 * @author Björn Merschmeier
 */
@Remote
public interface GameManagementRemote extends GameManagement {

	void requestGameMessage(Player player) throws GameNotInitializedException;

	boolean isInGame(Player player);
}
