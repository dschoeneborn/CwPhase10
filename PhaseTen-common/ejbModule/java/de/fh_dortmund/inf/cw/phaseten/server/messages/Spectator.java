package de.fh_dortmund.inf.cw.phaseten.server.messages;

import java.io.Serializable;

/**
 * @author Robin Harbecke
 *
 */
public class Spectator extends User implements Serializable {
	private static final long serialVersionUID = -5990383948259655714L;
	
	private String name;
	
	public Spectator(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}
}
