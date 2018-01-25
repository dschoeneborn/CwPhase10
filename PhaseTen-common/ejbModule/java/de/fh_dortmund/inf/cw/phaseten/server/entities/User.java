/**
 *
 */
package de.fh_dortmund.inf.cw.phaseten.server.entities;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;

import de.fh_dortmund.inf.cw.phaseten.server.entities.listener.EntityListener;

/**
 * User Entity.
 *
 * @author Dennis Schöneborn
 * @author Daniela Kaiser
 * @author Björn Merschmeier
 */

@Entity
@NamedQueries(value = { @NamedQuery(name = "User.findAll", query = "select u from User u"),
		@NamedQuery(name = "User.findByName", query = "select u from User u where u.loginName = :name"),
		@NamedQuery(name = User.FIND_BY_PLAYER, query = "SELECT u FROM User u WHERE u.player = :"
				+ User.PARAM_PLAYER) })
@EntityListeners({ EntityListener.class })
public class User implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public static final String FIND_BY_PLAYER = "User.FIND_BY_PLAYER";
	public static final String PARAM_PLAYER = "UserParamPlayer";

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(unique = true, nullable = false)
	@Basic(optional = false)
	private String loginName;

	@Column(nullable = false)
	@Basic(optional = false)
	private String password;

	@Column
	private int coins;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "PLAYER_ID", unique = true)
	private Player player;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn
	private Spectator spectator;

	/**
	 * Konstruktor
	 */
	private User() {

	}

	/**
	 * Konstruktor.
	 *
	 * @param name
	 * @param password
	 */
	public User(String name, String password) {
		this();
		this.loginName = name;
		this.password = password;
		this.coins = 0;
	}

	/**
	 * Liefert Login Namen
	 *
	 * @return the loginName
	 */
	public String getLoginName() {
		return loginName;
	}

	/**
	 * Liefert Coins
	 *
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

	/**
	 * Verringert Coins.
	 *
	 * @param coins
	 */
	public void decreaseCoins(int coins) {
		this.coins -= coins;
	}

	/**
	 * Set Coins.
	 *
	 * @param coinsToSet
	 */
	public void setCoins(int coinsToSet) {
		coins = coinsToSet;
	}

	/**
	 * Liefert Player
	 *
	 * @return Player
	 */
	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	/**
	 * Liefert Spectator.
	 *
	 * @return Spectator
	 */
	public Spectator getSpectator() {
		return spectator;
	}

	/**
	 * Liefert Passwort.
	 *
	 * @return password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Setzt spectator
	 *
	 * @param spectator
	 */
	public void setSpectator(Spectator spectator) {
		this.spectator = spectator;
	}

	/**
	 * Liefert id
	 *
	 * @return id
	 */
	public long getId() {
		return id;
	}
}
