/**
 * 
 */
package de.fh_dortmund.inf.cw.phaseten.server.entities;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 * @author Dennis Schöneborn
 * @author Daniela Kaiser
 *
 */

@Entity
@NamedQueries(value = {
		@NamedQuery(name="User.findAll", query="select u from User u"),
		@NamedQuery(name="User.findByName", query="select u from User u where u.loginName = :name")
})
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(unique = true, nullable = false)
	@Basic(optional = false)
	private String loginName;

	@SuppressWarnings("unused")
	@Column(nullable = false)
	@Basic(optional = false)
	private String password;

	@Column
	private int coins;

	@SuppressWarnings("unused")
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "PLAYER_ID", unique = true)
	private Player player;

	@SuppressWarnings("unused")
	@OneToOne(cascade=CascadeType.PERSIST)
	@JoinColumn
	private Spectator spectator;

	private User() {

	}

	public User(String name, String password) {
		this();
		this.loginName = name;
		this.password = password;
		this.coins = 0;
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
	public int getCoins() {
		return coins;
	}

	/**
	 * Increase Coins.
	 * 
	 * @param coinsToAdd
	 */
	public void increaseCoins(int coins) {
		this.coins += coins;
	}
	
	public void decreaseCoins(int coins) {
		this.coins -= coins;
	}
	
	/**
	 * Set Coins.
	 * 
	 * @param coinsToSet
	 */
	public void setCoins(int coinsToSet)
	{
		coins = coinsToSet;
	}
	
}
