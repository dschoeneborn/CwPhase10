package de.fh_dortmund.inf.cw.phaseten.server.shared;

import javax.ejb.Local;

/**
 * @author Marc Mettke
 */
@Local
public interface GameManagmentLocal extends GameManagment {
	public void sendGameMessage();
	
	public void createGame();
}
