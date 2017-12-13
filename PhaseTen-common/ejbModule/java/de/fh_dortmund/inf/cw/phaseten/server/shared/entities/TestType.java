package de.fh_dortmund.inf.cw.phaseten.server.shared.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class TestType {
	@Id @GeneratedValue
    private long id;
}
