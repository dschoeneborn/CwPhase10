package de.fh_dortmund.inf.cw.phaseten.server.entities.listener;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;

import de.fh_dortmund.inf.cw.phaseten.server.entities.Player;
import de.fh_dortmund.inf.cw.phaseten.server.entities.Spectator;
import de.fh_dortmund.inf.cw.phaseten.server.entities.User;

/***
 * EntityListener für das UserManagement.**
 *
 * @author Daniela Kaiser
 */
public class EntityListener {

	/**
	 * Bei neuem User werden initial 500 Coins hinzugefügt.
	 *
	 * @param entity
	 */
	@PrePersist
	public void newEntity(Object entity) {

		if(entity instanceof User)
		{
			Class<? extends Object> clazz = entity.getClass();

			try {
				Method increaseCoins = clazz.getMethod("increaseCoins", Integer.class);

				int initialCoins = 500;

				increaseCoins.invoke(entity, initialCoins);

			}
			catch (NoSuchMethodException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Meldung nach persistieren wird ausgegeben.
	 *
	 * @param entity
	 */
	@PostPersist
	public void persistEntity(Object entity) {

		Class<? extends Object> clazz = entity.getClass();

		try {
			Method getLoginName = clazz.getMethod("getLoginName");
			String loginName = (String) getLoginName.invoke(entity);
			System.out.println("User persistiert: " + loginName);
		}
		catch (NoSuchMethodException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Meldung nach Update wird ausgegeben.
	 *
	 * @param entity
	 */
	@PostUpdate
	public void updateEntity(Object entity) {

		Class<? extends Object> clazz = entity.getClass();

		try {
			Method getLoginName = clazz.getMethod("getLoginName");
			String loginName = (String) getLoginName.invoke(entity);

			Method getCoins = clazz.getMethod("getCoins");
			int coins = (int) getCoins.invoke(entity);

			Method getPlayer = clazz.getMethod("getPlayer");
			Player player = (Player) getPlayer.invoke(entity);

			Method getSpectator = clazz.getMethod("getSpectator");
			Spectator spectator = (Spectator) getSpectator.invoke(entity);

			System.out.println("User persistiert: loginName=" + loginName + ", coins=" + coins + ", player="
					+ (player == null ? "-" : player.getName()) + ", spectator="
					+ (spectator == null ? "-" : spectator.getName()) + "");
		}
		catch (NoSuchMethodException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
		}
	}

}
