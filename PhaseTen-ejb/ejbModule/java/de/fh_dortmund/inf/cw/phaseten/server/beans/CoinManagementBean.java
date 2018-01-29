package de.fh_dortmund.inf.cw.phaseten.server.beans;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import de.fh_dortmund.inf.cw.phaseten.server.entities.User;
import de.fh_dortmund.inf.cw.phaseten.server.exceptions.InsufficientCoinSupplyException;
import de.fh_dortmund.inf.cw.phaseten.server.shared.CoinManagementLocal;
import de.fh_dortmund.inf.cw.phaseten.server.shared.CoinManagementRemote;

/**
 * @author Robin Harbecke
 * @author Björn Merschmeier
 * @author Marc Mettke
 */
@Stateless
public class CoinManagementBean implements CoinManagementRemote, CoinManagementLocal {

	@PersistenceContext
	private EntityManager em;

	@Override
	public void increaseCoins(User user, int coins) {
		user.increaseCoins(new Integer(coins));
		em.merge(user);
		em.flush();
	}

	@Override
	public void decreaseCoins(User user, int coins) throws InsufficientCoinSupplyException {
		if (user.getCoins() < coins) {
			throw new InsufficientCoinSupplyException();
		}
		user.decreaseCoins(new Integer(coins));
		em.merge(user);
		em.flush();
	}

	@Override
	public void setCoins(User user, int coins) {
		user.setCoins(new Integer(coins));
		em.merge(user);
		em.flush();
	}
}
