package de.fh_dortmund.inf.cw.phaseten.server.shared;

import javax.ejb.Local;

/**
 * @author Marc Mettke
 */
@Local
public interface PlayerManagmentLocal extends PlayerManagment {
	public void sendPlayerMessage();
}
