package de.fh_dortmund.inf.cw.phaseten.server.shared;

import javax.ejb.Local;

import de.fh_dortmund.inf.cw.phaseten.server.entities.Lobby;

/**
 * 
 * @author Björn Merschmeier
 *
 */
@Local
public interface GameValidationLocal extends GameValidation
{
	boolean hasEnoughPlayers(Lobby lobby);

	boolean isLobbyFull(Lobby lobby);
}
