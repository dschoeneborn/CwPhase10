package de.fh_dortmund.inf.cw.phaseten.server.beans;

import javax.ejb.EJB;

import org.apache.openejb.jee.Empty;
import org.apache.openejb.jee.StatelessBean;
import org.apache.openejb.jee.jpa.unit.PersistenceUnit;
import org.apache.openejb.junit.ApplicationComposer;
import org.apache.openejb.testing.Module;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.fh_dortmund.inf.cw.phaseten.server.entities.TestType;

/**
 * @author Marc Mettke
 */
@RunWith(ApplicationComposer.class)
public class GameManagementTest {
	@EJB
	GameManagementBean gameManagment;
	
	@EJB
	UserSessionBean userSession1;
	
	@EJB
	UserSessionBean userSession2;

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
		final StatelessBean bean = new StatelessBean(GameManagementBean.class);
		bean.setLocalBean(new Empty());
		return bean;
	}

	@Test
	public void testSendGameMessage() throws Exception
	{
		
	}

	/*Player t = new Player("Tim");
	Card tCardTwo = new Card(Color.BLUE, CardValue.TWO);
	Card tCardFour1 = new Card(Color.BLUE, CardValue.FOUR);
	Card tCardFour2 = new Card(Color.BLUE, CardValue.FOUR);
	Card tCardFive1 = new Card(Color.BLUE, CardValue.FIVE);
	Card tCardFive2 = new Card(Color.BLUE, CardValue.FIVE);
	Card tCardSix = new Card(Color.BLUE, CardValue.SIX);
	Card tCardSeven = new Card(Color.BLUE, CardValue.SEVEN);
	Card tCardNine = new Card(Color.BLUE, CardValue.NINE);
	Card tCardTwelve = new Card(Color.BLUE, CardValue.TWELVE);
	Card tCardSkip = new Card(Color.NONE, CardValue.SKIP);
	t.addCardToPlayerPile(tCardTwo);
	t.addCardToPlayerPile(tCardFour1);
	t.addCardToPlayerPile(tCardFour2);
	t.addCardToPlayerPile(tCardFive1);
	t.addCardToPlayerPile(tCardFive2);
	t.addCardToPlayerPile(tCardSix);
	t.addCardToPlayerPile(tCardSeven);
	t.addCardToPlayerPile(tCardNine);
	t.addCardToPlayerPile(tCardTwelve);
	t.addCardToPlayerPile(tCardSkip);

	Player b = new Player("Björn");
	Card bCardOne = new Card(Color.BLUE, CardValue.ONE);
	Card bCardFour = new Card(Color.BLUE, CardValue.FOUR);
	Card bCardSix = new Card(Color.BLUE, CardValue.SIX);
	Card bCardSeven = new Card(Color.BLUE, CardValue.SEVEN);
	Card bCardNine1 = new Card(Color.BLUE, CardValue.NINE);
	Card bCardNine2 = new Card(Color.BLUE, CardValue.NINE);
	Card bCardEleven1 = new Card(Color.BLUE, CardValue.ELEVEN);
	Card bCardEleven2 = new Card(Color.BLUE, CardValue.ELEVEN);
	Card bCardTwelve = new Card(Color.BLUE, CardValue.TWELVE);
	Card bCardWild = new Card(Color.NONE, CardValue.WILD);
	b.addCardToPlayerPile(bCardOne);
	b.addCardToPlayerPile(bCardFour);
	b.addCardToPlayerPile(bCardSix);
	b.addCardToPlayerPile(bCardSeven);
	b.addCardToPlayerPile(bCardNine1);
	b.addCardToPlayerPile(bCardNine2);
	b.addCardToPlayerPile(bCardEleven1);
	b.addCardToPlayerPile(bCardEleven2);
	b.addCardToPlayerPile(bCardTwelve);
	b.addCardToPlayerPile(bCardWild);

	Set<Player> players = new HashSet<>();
	players.add(t);
	players.add(b);

	Game g = new Game(players, new HashSet<Spectator>());

	PullStack pullStack = new PullStack();
	Card pullStackSeven1 = new Card(Color.BLUE, CardValue.SEVEN);
	pullStack.addCard(pullStackSeven1);
	Card pullStackEleven1 = new Card(Color.BLUE, CardValue.ELEVEN);
	pullStack.addCard(pullStackEleven1);
	Card pullStackFour1 = new Card(Color.BLUE, CardValue.FOUR);
	pullStack.addCard(pullStackFour1);
	Card pullStackNine1 = new Card(Color.BLUE, CardValue.NINE);
	pullStack.addCard(pullStackNine1);
	Card pullStackTwelve1 = new Card(Color.BLUE, CardValue.TWELVE);
	pullStack.addCard(pullStackTwelve1);
	Card pullStackTen1 = new Card(Color.BLUE, CardValue.TEN);
	pullStack.addCard(pullStackTen1);
	Card pullStackWild1 = new Card(Color.NONE, CardValue.WILD);
	pullStack.addCard(pullStackWild1);
	Card pullStackTwo1 = new Card(Color.BLUE, CardValue.TWO);
	pullStack.addCard(pullStackTwo1);
	Card pullStackFour2 = new Card(Color.BLUE, CardValue.FOUR);
	pullStack.addCard(pullStackFour2);
	Card pullStackThree1 = new Card(Color.BLUE, CardValue.THREE);
	pullStack.addCard(pullStackThree1);
	Card pullStackFive1 = new Card(Color.BLUE, CardValue.FIVE);
	pullStack.addCard(pullStackFive1);
	Card pullStackSix1 = new Card(Color.BLUE, CardValue.SIX);
	pullStack.addCard(pullStackSix1);
	Card pullStackOne1 = new Card(Color.BLUE, CardValue.ONE);
	pullStack.addCard(pullStackOne1);
	Card pullStackThree2 = new Card(Color.BLUE, CardValue.THREE);
	pullStack.addCard(pullStackThree2);
	Card pullStackWild2 = new Card(Color.NONE, CardValue.WILD);
	pullStack.addCard(pullStackWild2);
	Card pullStackSix2 = new Card(Color.BLUE, CardValue.SIX);
	pullStack.addCard(pullStackSix2);
	Card pullStackTwo2 = new Card(Color.BLUE, CardValue.TWO);
	pullStack.addCard(pullStackTwo2);
	Card pullStackSkip1 = new Card(Color.NONE, CardValue.SKIP);
	pullStack.addCard(pullStackSkip1);
	Card pullStackSeven2 = new Card(Color.BLUE, CardValue.SEVEN);
	pullStack.addCard(pullStackSeven2);
	Card pullStackTwo3 = new Card(Color.BLUE, CardValue.TWO);
	pullStack.addCard(pullStackTwo3);
	Card pullStackTen2 = new Card(Color.BLUE, CardValue.TEN);
	pullStack.addCard(pullStackTen2);
	Card pullStackSeven3 = new Card(Color.BLUE, CardValue.SEVEN);
	pullStack.addCard(pullStackSeven3);
	Card pullStackSkip2 = new Card(Color.NONE, CardValue.SKIP);
	pullStack.addCard(pullStackSkip2);
	g.setPullstack(pullStack);

	LiFoStack lifoStack = new LiFoStack();
	Card lifoStackTwelve = new Card(Color.BLUE, CardValue.TWELVE);
	lifoStack.addCard(lifoStackTwelve);
	g.setLiFoStack(lifoStack);
	g.setCurrentPlayer(t);
	g.setInitialized();

	pullFromLiFo(t, g, lifoStack);
	//T: 12 von LIFO

	laySkipCard(t, tCardSkip, b, g, lifoStack);
	//T: Skip legen und B auswählen

	Assert.assertFalse(gameValidation.isValidDrawCardFromLiFoStack(g, b));

	pullFromPullStack(t, g, pullStack);
	//T: SKIP von PULL

	Assert.assertFalse(gameValidation.isValidDrawCardFromPullStack(g, t));

	laySkipCard(t, tCardSkip, b, g, lifoStack);
	//T: Skip legen und B auswählen

	Assert.assertFalse(gameValidation.isValidDrawCardFromLiFoStack(g, b));

	Assert.assertFalse(gameValidation.isValidDrawCardFromLiFoStack(g, t));

	pullFromPullStack(t, g, pullStack);
	//T: 7 von PULL

	layCardToLiFoStack(t, tCardTwo, g, lifoStack);
	//T: 2 auf LIFO

	pullFromPullStack(b, g, pullStack);
	//B: 10 von PULL

	layCardToLiFoStack(b, pullStackTen2, g, lifoStack);
	//B: 10 auf LIFO

	pullFromPullStack(t, g, pullStack);
	//T: 2 von PULL

	layCardToLiFoStack(t, tCardNine, g, lifoStack);
	//T: 9 auf LIFO

	pullFromLiFo(b, g, lifoStack);
	//B: 9 von LIFO

	Assert.assertTrue(b.getPlayerPile().getCards().contains(tCardNine));

	Collection<DockPile> pilesInCollection = new ArrayList<>();
	DockPile phase1b1 = new SetDockPile(CardValue.NINE);
	Assert.assertTrue(phase1b1.addCard(bCardNine1));
	Assert.assertTrue(phase1b1.addCard(bCardNine2));
	Assert.assertTrue(phase1b1.addCard(tCardNine));
	Assert.assertFalse(phase1b1.addCard(bCardSeven));

	DockPile phase1b2 = new SetDockPile(CardValue.ELEVEN);
	Assert.assertTrue(phase1b2.addCard(bCardEleven1));
	Assert.assertTrue(phase1b2.addCard(bCardEleven2));
	Assert.assertTrue(phase1b2.addCard(bCardWild));

	pilesInCollection.add(phase1b1);
	pilesInCollection.add(phase1b2);

	Assert.assertTrue(gameValidation.isValidLayStageToTable(g, b, pilesInCollection));

	b.setPlayerLaidStage(true);

	Assert.assertFalse(gameValidation.isValidLayStageToTable(g, b, pilesInCollection));

	b.removeCardFromPlayerPile(bCardNine1);
	b.removeCardFromPlayerPile(bCardNine2);
	b.removeCardFromPlayerPile(bCardEleven2);
	b.removeCardFromPlayerPile(bCardEleven1);
	b.removeCardFromPlayerPile(bCardWild);
	b.removeCardFromPlayerPile(tCardNine);

	g.addOpenPile(phase1b1);
	g.addOpenPile(phase1b2);
	//B: 9 9 9 und 11 11 W auslegen

	layCardToLiFoStack(b, bCardTwelve, g, lifoStack);
	//B: 12 auf LIFO

	Assert.assertFalse(gameValidation.isValidToAddCard(g, t, phase1b1, tCardTwo));
	
	pullFromLiFo(t, g, lifoStack);
	Assert.assertTrue(t.getPlayerPile().getCards().contains(bCardTwelve));
	// T: 12 von LIFO
	
	layCardToLiFoStack(t, tCardSix, g, lifoStack);
	// T: 6 auf LIFO
	
	pullFromLiFo(b, g, lifoStack);
	// B: 6 von LIFO
	
	layCardToLiFoStack(b, bCardSeven, g, lifoStack);
	// B: 7 auf LIFO
	
	pullFromLiFo(t, g, lifoStack);
	// T: 7 von LIFO
	
	Collection<DockPile> pilesInCollectionT = new ArrayList<>();
	DockPile phase1t1 = new SetDockPile(CardValue.SEVEN);
	Assert.assertTrue(phase1t1.addCard(bCardSeven));
	Assert.assertTrue(phase1t1.addCard(tCardSeven));
	Assert.assertTrue(phase1t1.addCard(pullStackSeven3));

	DockPile phase1t2 = new SetDockPile(CardValue.TWELVE);
	Assert.assertTrue(phase1t2.addCard(tCardTwelve));
	Assert.assertTrue(phase1t2.addCard(bCardTwelve));
	Assert.assertTrue(phase1t2.addCard(lifoStackTwelve));

	pilesInCollectionT.add(phase1t1);
	pilesInCollectionT.add(phase1t2);

	Assert.assertTrue(gameValidation.isValidLayStageToTable(g, t, pilesInCollectionT));

	t.setPlayerLaidStage(true);

	Assert.assertFalse(gameValidation.isValidLayStageToTable(g, t, pilesInCollectionT));

	t.removeCardFromPlayerPile(bCardSeven);
	t.removeCardFromPlayerPile(tCardSeven);
	t.removeCardFromPlayerPile(pullStackSeven3);
	t.removeCardFromPlayerPile(tCardTwelve);
	t.removeCardFromPlayerPile(bCardTwelve);
	t.removeCardFromPlayerPile(lifoStackTwelve);

	g.addOpenPile(phase1t1);
	g.addOpenPile(phase1t2);
	// T: PHASE 7,7,7 12,12,12
	
	layCardToLiFoStack(t, tCardTwo, g, lifoStack);
	// T: 2 auf LIFO
	
	pullFromPullStack(b, g, pullStack);
	// B: 7 von Pull
	
	Assert.assertTrue(gameValidation.isValidToAddCard(g, b, phase1t1, pullStackSeven2));
	phase1t1.addCard(pullStackSeven2);
	b.removeCardFromPlayerPile(pullStackSeven2);
	// B: 7 Anlegen
	
	layCardToLiFoStack(b, bCardOne, g, lifoStack);
	// B: 1 auf LIFO
	
	pullFromPullStack(t, g, pullStack);
	// T: s von Pull
	
	laySkipCard(t, pullStackSkip1, b, g, lifoStack);
	// T: s auf LIFO B auswählen

	pullFromPullStack(t, g, pullStack);
	// T: 2 von PULL
	
	layCardToLiFoStack(t, pullStackTwo2, g, lifoStack);
	// T: 2 auf LIFO
	
	pullFromPullStack(b, g, pullStack);
	// B: 6 vom PULL
	
	layCardToLiFoStack(b, bCardFour, g, lifoStack);
	// B: 4 auf LIFO
	
	pullFromPullStack(t, g, pullStack);
	// T: W von PULL
	
	Assert.assertTrue(gameValidation.isValidToAddCard(g, t, phase1b2, pullStackWild2));
	phase1b2.addCard(pullStackWild2);
	t.removeCardFromPlayerPile(pullStackWild2);
	// T: W an 11 anlegen
	
	layCardToLiFoStack(t, tCardFive1, g, lifoStack);
	// T: 5 auf LIFO
	
	pullFromPullStack(b, g, pullStack);
	// B: 3 von PULL
	
	layCardToLiFoStack(b, pullStackThree2, g, lifoStack);
	// B: 3 auf LIFO

	pullFromPullStack(t, g, pullStack);
	// T: 1 von PULL
	
	layCardToLiFoStack(t, pullStackOne1, g, lifoStack);
	// T: 1 auf LIFO
	
	pullFromPullStack(b, g, pullStack);
	// B: 6 von PULL
	
	layCardToLiFoStack(b, pullStackSix1, g, lifoStack);
	// B: 6 auf LIFO
	
	pullFromPullStack(t, g, pullStack);
	// T: 5 von PULL
	
	layCardToLiFoStack(t, pullStackFive1, g, lifoStack);
	// T: 5 auf LIFO
	
	pullFromPullStack(b, g, pullStack);
	// B: 3 von PULL
	
	layCardToLiFoStack(b, pullStackThree1, g, lifoStack);
	// B: 3 auf LIFO
	
	pullFromPullStack(t, g, pullStack);
	// T: 4 von PULL
	
	layCardToLiFoStack(t, tCardFive2, g, lifoStack);
	// T: 5 auf LIFO
	
	pullFromPullStack(b, g, pullStack);
	// B: 2 von PULL
	
	layCardToLiFoStack(b, pullStackTwo1, g, lifoStack);
	// B: 2 auf LIFO
	
	pullFromPullStack(t, g, pullStack);
	// T: W von PULL
	
	Assert.assertTrue(gameValidation.isValidToAddCard(g, t, phase1b1, pullStackWild1));
	phase1b1.addCard(pullStackWild1);
	t.removeCardFromPlayerPile(pullStackWild1);
	// T: W an 9
	
	layCardToLiFoStack(t, tCardFour1, g, lifoStack);
	// T: 4 auf LIFO
	
	pullFromPullStack(b, g, pullStack);
	// B: 10 von PULL
	
	layCardToLiFoStack(b, pullStackTen1, g, lifoStack);
	// B: 10 auf LIFO
	
	pullFromPullStack(t, g, pullStack);
	// T: 12 von PULL
	
	Assert.assertTrue(gameValidation.isValidToAddCard(g, t, phase1t2, pullStackTwelve1));
	phase1t2.addCard(pullStackTwelve1);
	t.removeCardFromPlayerPile(pullStackTwelve1);
	// T: 12 an 12 anlegen
	
	layCardToLiFoStack(t, tCardFour2, g, lifoStack);
	// T: 4 auf LIFO
	
	pullFromPullStack(b, g, pullStack);
	// B: 9 von PULL
	
	Assert.assertTrue(gameValidation.isValidToAddCard(g, b, phase1b1, pullStackNine1));
	phase1b1.addCard(pullStackNine1);
	b.removeCardFromPlayerPile(pullStackNine1);
	// B: 9 anlegen an 9en
	
	layCardToLiFoStack(b, bCardSix, g, lifoStack);
	// B: 6 auf LIFO
	
	pullFromPullStack(t, g, pullStack);
	// T: 4 von PULL
	
	layCardToLiFoStack(t, pullStackFour1, g, lifoStack);
	// T: 4 auf LIFO
	
	pullFromPullStack(b, g, pullStack);
	// B: 11 von PULL
	
	Assert.assertTrue(gameValidation.isValidToAddCard(g, b, phase1b2, pullStackEleven1));
	phase1b2.addCard(pullStackEleven1);
	b.removeCardFromPlayerPile(pullStackEleven1);
	// B: 11 an 11en anlegen
	
	layCardToLiFoStack(b, pullStackSix2, g, lifoStack);
	// B: 6 auf LIFO
	
	pullFromPullStack(t, g, pullStack);
	// T: 7 von PULL
	
	Assert.assertTrue(gameValidation.isValidToAddCard(g, t, phase1t1, pullStackSeven1));
	phase1t1.addCard(pullStackSeven1);
	t.removeCardFromPlayerPile(pullStackSeven1);
	// T: 7 an 7en anlegen
	
	layCardToLiFoStack(t, pullStackFour2, g, lifoStack);
	// T: 4 auf LIFO
*/
}
