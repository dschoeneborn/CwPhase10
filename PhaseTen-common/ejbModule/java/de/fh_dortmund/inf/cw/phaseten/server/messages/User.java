package de.fh_dortmund.inf.cw.phaseten.server.messages;

import java.io.Serializable;

/**
 * @author Robin Harbecke
 *
 */
public abstract class User implements Serializable {	
	private static final long serialVersionUID = 4043227135224483776L;

	public User()  {
		
	}

	public abstract String getName(); 
}
