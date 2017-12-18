/**
 * 
 */
package de.fh_dortmund.inf.cw.phaseten.server.entities;

/**
 * @author Dennis Schöneborn
 *
 */
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
