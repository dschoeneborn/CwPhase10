/**
 * 
 */
package de.fh_dortmund.inf.cw.phaseten.server.entities;

import java.io.Serializable;
import java.util.LinkedList;

/**
 * @author Dennis Schöneborn
 * @author Marc Mettke
 */
public abstract class Pile implements Serializable {
	private static final long serialVersionUID = 8249906555853503950L;
	
	protected LinkedList<Card> cards;
	
}
