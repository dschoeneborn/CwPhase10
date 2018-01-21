package de.fh_dortmund.inf.cw.phaseten.server.shared;

import java.util.Collection;

import javax.ejb.Local;

import de.fh_dortmund.inf.cw.phaseten.server.entities.Player;
import de.fh_dortmund.inf.cw.phaseten.server.entities.Spectator;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.NoFreeSlotException;
import de.fh_dortmund.inf.cw.phaseten.server.messages.PlayerGuiData;

/**
 * @author Marc Mettke
 * @author Tim Prange
 * @author Sebastian Seitz
 */
@Local
public interface LobbyManagementLocal extends LobbyManagement {
	public void sendLobbyMessage();

	void enterLobby(Player player) throws NoFreeSlotException;

	void enterLobby(Spectator spectator);

	/**
	 * Returns players.
	 *
	 * @author Tim Prange
	 * @return players
	 */
	public Collection<PlayerGuiData> getPlayersForGui();
	
	void addAI() throws NoFreeSlotException;

	Collection<String> getSpectatorNamesForGui();

	void requestLobbyMessage();
}
