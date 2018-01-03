package de.fh_dortmund.inf.cw.phaseten.server.shared;

import de.fh_dortmund.inf.cw.phaseten.server.entities.Player;
import de.fh_dortmund.inf.cw.phaseten.server.entities.Spectator;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.NotEnoughPlayerException;

/**
 * @author Marc Mettke
 * @author Björn Merschmeier
 */
public interface LobbyManagment {
	void leaveLobby(Player player);
	void leaveLobby(Spectator spectator);
	void startGame(Player player) throws NotEnoughPlayerException;
}
