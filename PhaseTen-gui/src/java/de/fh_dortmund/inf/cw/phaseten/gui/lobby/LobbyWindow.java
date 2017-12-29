package de.fh_dortmund.inf.cw.phaseten.gui.lobby;

import de.fh_dortmund.inf.cw.phaseten.client.ServiceHandler;
import de.fh_dortmund.inf.cw.phaseten.gui.GuiFrame;
import de.fh_dortmund.inf.cw.phaseten.server.messages.CurrentPlayer;
import de.fh_dortmund.inf.cw.phaseten.server.messages.Game;
import de.fh_dortmund.inf.cw.phaseten.server.messages.Lobby;

/**
 * @author Marc Mettke
 */
public class LobbyWindow extends GuiFrame {
	private static final long serialVersionUID = -3411026015858719190L;

	public LobbyWindow(ServiceHandler serviceHandler) {
		super("Phaseten | Lobby", serviceHandler);
	}

	@Override
	public void gameDataUpdated(Game game) {
		
	}

	@Override
	public void currentPlayerDataUpdated(CurrentPlayer currentPlayer) {
		
	}

	@Override
	public void lobbyDataUpdated(Lobby lobby) {
		
	}
}
