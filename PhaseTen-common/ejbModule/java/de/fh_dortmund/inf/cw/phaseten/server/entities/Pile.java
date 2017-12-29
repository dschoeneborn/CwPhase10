/**
 * 
 */
package de.fh_dortmund.inf.cw.phaseten.server.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;

/**
 * @author Dennis Schöneborn
 * @author Marc Mettke
 * @author Daniela Kaiser
 * @author Sebastian Seitz
 */
@MappedSuperclass
public abstract class Pile implements Serializable {
	private static final long serialVersionUID = 8249906555853503950L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	
	@OneToMany(cascade = CascadeType.PERSIST)
	@JoinColumn(name="PERSON_ID")
	protected List<Card> cards;

}
