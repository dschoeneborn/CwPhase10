package de.fh_dortmund.inf.cw.phaseten.server.shared;

import javax.ejb.Remote;

import de.fh_dortmund.inf.cw.phaseten.server.entities.Player;

/**
 * @author Marc Mettke
 * @author Björn Merschmeier
 */
@Remote
public interface UserManagementRemote extends UserManagement {


	void requestPlayerMessage(Player p);
}
