package de.fh_dortmund.inf.cw.phaseten.server.shared;

import javax.ejb.Remote;

import de.fh_dortmund.inf.cw.phaseten.server.entities.Player;

/**
 * @author Marc Mettke
 */
@Remote
public interface GameManagementRemote extends GameManagement {

	void requestGameMessage(Player p);
}
