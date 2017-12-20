/**
 * 
 */
package de.fh_dortmund.inf.cw.phaseten.server.entities;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 * @author Dennis Schöneborn
 *
 */

/*@Entity
@NamedQueries(value = {
		@NamedQuery(name="User.findAll", query="select u from User u"),
		@NamedQuery(name="User.findByName", query="select u from User u where u.name = :name")
})*/
public class User {
	private String loginName;
	@SuppressWarnings("unused")
	private String password;
	private Long coins;
	@SuppressWarnings("unused")
	private Player player;
	@SuppressWarnings("unused")
	private Spectator spectator;
	
	private User()
	{
		
	}
	
	public User(String name, String password)
	{
		this();
		this.loginName = name;
		this.password = password;
	}

	/**
	 * @return the loginName
	 */
	public String getLoginName() {
		return loginName;
	}

	/**
	 * @return the coins
	 */
	public Long getCoins() {
		return coins;
	}
}
