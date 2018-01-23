package de.fh_dortmund.inf.cw.phaseten.server.beans;

import java.util.ArrayList;
import java.util.HashSet;

import javax.ejb.EJB;

import org.apache.openejb.jee.Empty;
import org.apache.openejb.jee.StatelessBean;
import org.apache.openejb.jee.jpa.unit.PersistenceUnit;
import org.apache.openejb.junit.ApplicationComposer;
import org.apache.openejb.testing.Module;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.fh_dortmund.inf.cw.phaseten.server.entities.Card;
import de.fh_dortmund.inf.cw.phaseten.server.entities.Game;
import de.fh_dortmund.inf.cw.phaseten.server.entities.LiFoStack;
import de.fh_dortmund.inf.cw.phaseten.server.entities.Player;
import de.fh_dortmund.inf.cw.phaseten.server.entities.PlayerPile;
import de.fh_dortmund.inf.cw.phaseten.server.entities.PullStack;
import de.fh_dortmund.inf.cw.phaseten.server.entities.TestType;
import de.fh_dortmund.inf.cw.phaseten.server.entities.ai.TakeCardAction;
import de.fh_dortmund.inf.cw.phaseten.server.enumerations.CardValue;
import de.fh_dortmund.inf.cw.phaseten.server.enumerations.Color;

/**
 * @author Robin Harbecke
 */
@RunWith(ApplicationComposer.class)
public class AIManagmentTest {
	@EJB
	AIManagmentBean aiManagment;

	@Module
	public PersistenceUnit persistence() {
		PersistenceUnit unit = new PersistenceUnit("PhaseTenDB");
		unit.setJtaDataSource("testTypeDatabase");
		unit.setNonJtaDataSource("testTypeDatabaseUnmanaged");
		unit.getClazz().add(TestType.class.getName());
		unit.setProperty("openjpa.jdbc.SynchronizeMappings", "buildSchema(ForeignKeys=true)");
		return unit;
	}

	@Module
	public StatelessBean app() throws Exception {
		final StatelessBean bean = new StatelessBean(AIManagmentBean.class);
		bean.setLocalBean(new Empty());
		return bean;
	}
	
	@Test
	public void testTakeCard() throws Exception {	
		PlayerPile playerPile = new PlayerPile();
		playerPile.addLast(new Card(Color.GREEN, CardValue.SEVEN));
		Player player = new Player("testPlayer");
	    LiFoStack discardPile = new LiFoStack();
	    discardPile.addLast(new Card(Color.GREEN, CardValue.EIGHT));
		Game game = new Game(new HashSet<>(), new HashSet<>(), new PullStack(), discardPile);
		Assert.assertEquals(TakeCardAction.DISCARD_PILE, this.aiManagment.takeCard(player, game));
	}
	
	@Test
	public void testCardsToPile() throws Exception {	
		PlayerPile playerPile = new PlayerPile();
		playerPile.addLast(new Card(Color.GREEN, CardValue.SEVEN));
		Player player = new Player("testPlayer");
	    LiFoStack discardPile = new LiFoStack();
	    discardPile.addLast(new Card(Color.GREEN, CardValue.EIGHT));
		Game game = new Game(new HashSet<>(), new HashSet<>(), new PullStack(), discardPile);
		Assert.assertEquals(new ArrayList<>(), this.aiManagment.cardsToPile(player, game));
	}
	
	@Test
	public void testDiscardCard() throws Exception {	
		//TODO - Test auskommentiert, weil nicht mehr funktionsfähig - Test reparieren!
//		PlayerPile playerPile = new PlayerPile();
//		playerPile.addCard(new Card(Color.GREEN, CardValue.SEVEN));
//		Player player = new Player("testPlayer");
//	    LiFoStack discardPile = new LiFoStack();
//	    discardPile.addCard(new Card(Color.GREEN, CardValue.EIGHT));
//		Game game = new Game(new HashSet<>(), new HashSet<>(), new PullStack(), discardPile);
//		Assert.assertEquals(new Card(Color.GREEN, CardValue.SEVEN), this.aiManagment.discardCard(player, game));
	}

}
