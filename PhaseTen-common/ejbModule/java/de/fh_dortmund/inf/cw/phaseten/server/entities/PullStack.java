/**
 * 
 */
package de.fh_dortmund.inf.cw.phaseten.server.entities;

import javax.persistence.Entity;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * @author Dennis Schöneborn
 * @author Marc Mettke
 * @author Sebastian Seitz
 */
@Entity
public class PullStack extends Stack {
	private static final long serialVersionUID = 2117218073864785792L;

	public PullStack()
	{
		throw new NotImplementedException();
		//TODO Einen vollständigen Kartenstapel initialisieren
	}

	@Override
	public boolean addCard(Card card)
	{
		return false;
	}
}
