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
import javax.persistence.Transient;

/**
 * @author Dennis Schöneborn
 * @author Daniela Kaiser
 *
 */
@Entity
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
	private long coins;

	@SuppressWarnings("unused")
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "PLAYER_ID", unique = true)
	private Player player;
	@SuppressWarnings("unused")
	// TODO
	@Transient
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
	public Long getCoins() {
		return coins;
	}

	/**
	 * Add Coins.
	 * 
	 * @param coinsToAdd
	 */
	public void addCoins(long coinsToAdd) {
		coins += coinsToAdd;
	}
}
