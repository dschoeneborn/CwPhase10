package de.fh_dortmund.inf.cw.phaseten.gui;

import de.fh_dortmund.inf.cw.phaseten.server.messages.CurrentPlayer;
import de.fh_dortmund.inf.cw.phaseten.server.messages.Game;
import de.fh_dortmund.inf.cw.phaseten.server.messages.Lobby;

/**
 * @author Robin Harbecke
 * @author Marc Mettke
 * @author Björn Merschmeier
 */
public interface GuiObserver {
	void gameDataUpdated(Game game);
	void currentPlayerDataUpdated(CurrentPlayer currentPlayer);
	void lobbyDataUpdated(Lobby lobby);
}
