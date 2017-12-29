package de.fh_dortmund.inf.cw.phaseten.gui.login;

import de.fh_dortmund.inf.cw.phaseten.client.ServiceHandler;
import de.fh_dortmund.inf.cw.phaseten.gui.GuiFrame;
import de.fh_dortmund.inf.cw.phaseten.server.messages.CurrentPlayer;
import de.fh_dortmund.inf.cw.phaseten.server.messages.Game;
import de.fh_dortmund.inf.cw.phaseten.server.messages.Lobby;

/**
 * @author Marc Mettke
 */
public class LoginWindow extends GuiFrame {
	private static final long serialVersionUID = -3411026015858719190L;

	public LoginWindow(ServiceHandler serviceHandler) {
		super("Phaseten | Login", serviceHandler);
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
