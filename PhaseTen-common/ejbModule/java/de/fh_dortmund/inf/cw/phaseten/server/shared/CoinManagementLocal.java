/**
 * 
 */
package de.fh_dortmund.inf.cw.phaseten.server.shared;

import javax.ejb.Local;

import de.fh_dortmund.inf.cw.phaseten.server.entities.User;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.InsufficientCoinSupplyException;

/**
 * @author Dennis Schöneborn
 *
 */

@Local
public interface CoinManagementLocal extends CoinManagement {
	public void increaseCoins(User user, int coins);
	public void decreaseCoins(User user, int coins) throws InsufficientCoinSupplyException;
	public void setCoins(User user, int coins);
}
