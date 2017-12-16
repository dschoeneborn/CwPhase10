package de.fh_dortmund.inf.cw.phaseten.server.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries({
		@NamedQuery(name="TestTypeQueryAll", query="SELECT t from TestType as t")
})
public class TestType implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id @GeneratedValue
    private long id;
}
