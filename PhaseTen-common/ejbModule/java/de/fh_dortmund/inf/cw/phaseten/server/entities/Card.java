package de.fh_dortmund.inf.cw.phaseten.server.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Card Entity.
 * 
 * @author Daniela
 *
 */
@Entity
public class Card {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public int id;

	public int getId() {
		return id;
	}
}
