/**
 * 
 */
package de.fh_dortmund.inf.cw.phaseten.server.shared;

import javax.ejb.Local;

import de.fh_dortmund.inf.cw.phaseten.server.exceptions.UserDoesNotExistException;

/**
 * @author Dennis Schöneborn
 *
 */

@Local
public interface CoinManagementLocal extends CoinManagement {
	public void increaseCoins(String userName, int coins) throws UserDoesNotExistException;
	public void decreaseCoins(String userName, int coins) throws UserDoesNotExistException;
	public void setCoins(String userName, int coins) throws UserDoesNotExistException;
}
