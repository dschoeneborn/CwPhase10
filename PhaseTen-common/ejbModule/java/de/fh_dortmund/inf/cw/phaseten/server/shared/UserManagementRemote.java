package de.fh_dortmund.inf.cw.phaseten.server.shared;

import javax.ejb.Remote;

/**
 * @author Marc Mettke
 */
@Remote
public interface UserManagementRemote extends UserManagement {
	void requestPlayerMessage();
}
