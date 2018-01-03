package de.fh_dortmund.inf.cw.phaseten.server.shared;

import javax.ejb.Remote;

import de.fh_dortmund.inf.cw.phaseten.server.entities.Player;

/**
 * @author Marc Mettke
 */
@Remote
public interface GameManagmentRemote extends GameManagment {

	void requestGameMessage(Player p);
}
