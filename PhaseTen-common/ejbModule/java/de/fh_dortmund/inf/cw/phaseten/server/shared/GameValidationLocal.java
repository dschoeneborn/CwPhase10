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
	/**
	 * Checks if a lobby has enought players to start a game
	 * @param lobby
	 * @return
	 */
	boolean hasEnoughPlayers(Lobby lobby);

	/**
	 * checks if a lobby is full
	 * @param lobby
	 * @return
	 */
	boolean isLobbyFull(Lobby lobby);
}
