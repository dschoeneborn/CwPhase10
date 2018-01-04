package de.fh_dortmund.inf.cw.phaseten.server.messages;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Robin Harbecke
 * @author Björn Merschmeier
 */
public class Spectator implements Serializable {
	private static final long serialVersionUID = -5990383948259655714L;
	
	private String name;
	
	public Spectator(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	/**
	 * @author Björn Merschmeier
	 * @param spectators
	 * @return
	 */
	public static Collection<Spectator> from(Collection<de.fh_dortmund.inf.cw.phaseten.server.entities.Spectator> spectators)
	{
		Collection<Spectator> newSpectators = new ArrayList<Spectator>();
		
		for(de.fh_dortmund.inf.cw.phaseten.server.entities.Spectator s : spectators)
		{
			newSpectators.add(Spectator.from(s));
		}
		
		return newSpectators;
	}
	
	/**
	 * @author Björn Merschmeier
	 * @param spectator
	 * @return
	 */
	public static Spectator from(de.fh_dortmund.inf.cw.phaseten.server.entities.Spectator spectator)
	{
		Spectator result = new Spectator(spectator.getName());
		
		return result;
	}
}
