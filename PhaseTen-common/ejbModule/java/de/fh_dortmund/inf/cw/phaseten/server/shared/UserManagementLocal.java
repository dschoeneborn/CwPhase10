package de.fh_dortmund.inf.cw.phaseten.server.shared;

import javax.ejb.Local;

import de.fh_dortmund.inf.cw.phaseten.server.entities.User;

/**
 * @author Marc Mettke
 */
@Local
public interface UserManagementLocal extends UserManagement {
	public void sendPlayerMessage();
}
