package de.fh_dortmund.inf.cw.phaseten.server.shared;

import de.fh_dortmund.inf.cw.phaseten.server.exceptions.NoFreeSlotException;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.NotEnoughPlayerException;

/**
 * @author Marc Mettke
 */
public interface LobbyManagment {
	void enterAsPlayer() throws NoFreeSlotException;
	
	void enterAsSpectator();
	
	void startGame() throws NotEnoughPlayerException;
}
