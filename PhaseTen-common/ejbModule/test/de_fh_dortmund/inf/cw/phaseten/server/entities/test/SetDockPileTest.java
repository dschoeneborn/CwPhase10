package de_fh_dortmund.inf.cw.phaseten.server.entities.test;

import org.junit.Assert;
import org.junit.Test;

import de.fh_dortmund.inf.cw.phaseten.server.entities.Card;
import de.fh_dortmund.inf.cw.phaseten.server.entities.SetDockPile;
import de.fh_dortmund.inf.cw.phaseten.server.enumerations.CardValue;
import de.fh_dortmund.inf.cw.phaseten.server.enumerations.Color;

/**
 * @author Björn Merschmeier
 */
public class SetDockPileTest
{

	/**
	 * @author Björn Merschmeier
	 */
	@Test
	public void testCreateSetDockPile_specialOnlyWilds()
	{
		SetDockPile pile = new SetDockPile();

		Assert.assertNotNull(pile);

		Card c = new Card(Color.NONE, CardValue.WILD, 1);
		Assert.assertTrue(pile.addFirst(c));
		Assert.assertNull(pile.getCardValue());

		c = new Card(Color.NONE, CardValue.WILD, 2);
		Assert.assertTrue(pile.addFirst(c));
		Assert.assertNull(pile.getCardValue());

		c = new Card(Color.NONE, CardValue.WILD, 3);
		Assert.assertTrue(pile.addLast(c));
		Assert.assertNull(pile.getCardValue());

		c = new Card(Color.NONE, CardValue.WILD, 4);
		Assert.assertTrue(pile.addLast(c));
		Assert.assertNull(pile.getCardValue());
	}

	/**
	 * @author Björn Merschmeier
	 */
	@Test
	public void testCreateSetDockPile_firstWildsThanValue()
	{
		SetDockPile pile = new SetDockPile();

		Assert.assertNotNull(pile);

		Card c = new Card(Color.NONE, CardValue.WILD, 1);
		Assert.assertTrue(pile.addFirst(c));
		Assert.assertNull(pile.getCardValue());

		c = new Card(Color.NONE, CardValue.WILD, 2);
		Assert.assertTrue(pile.addFirst(c));
		Assert.assertNull(pile.getCardValue());

		c = new Card(Color.BLUE, CardValue.ELEVEN, 3);
		Assert.assertTrue(pile.addLast(c));
		Assert.assertEquals(CardValue.ELEVEN, pile.getCardValue());

		c = new Card(Color.GREEN, CardValue.ELEVEN, 4);
		Assert.assertTrue(pile.addFirst(c));
		Assert.assertEquals(CardValue.ELEVEN, pile.getCardValue());

		c = new Card(Color.GREEN, CardValue.ONE, 5);
		Assert.assertFalse(pile.addLast(c));
		Assert.assertFalse(pile.addFirst(c));
		Assert.assertEquals(CardValue.ELEVEN, pile.getCardValue());
	}

	/**
	 * @author Björn Merschmeier
	 */
	@Test
	public void testCreateSetDockPile_firstNumbersThanWilds()
	{
		SetDockPile pile = new SetDockPile();

		Assert.assertNotNull(pile);

		Card c = new Card(Color.BLUE, CardValue.ELEVEN, 3);
		Assert.assertTrue(pile.addLast(c));
		Assert.assertEquals(CardValue.ELEVEN, pile.getCardValue());

		c = new Card(Color.GREEN, CardValue.ELEVEN, 4);
		Assert.assertTrue(pile.addFirst(c));
		Assert.assertEquals(CardValue.ELEVEN, pile.getCardValue());

		c = new Card(Color.NONE, CardValue.WILD, 1);
		Assert.assertTrue(pile.addFirst(c));
		Assert.assertEquals(CardValue.ELEVEN, pile.getCardValue());

		c = new Card(Color.NONE, CardValue.WILD, 2);
		Assert.assertTrue(pile.addFirst(c));
		Assert.assertEquals(CardValue.ELEVEN, pile.getCardValue());

		c = new Card(Color.GREEN, CardValue.NINE, 5);
		Assert.assertFalse(pile.addLast(c));
		Assert.assertFalse(pile.addFirst(c));
		Assert.assertEquals(CardValue.ELEVEN, pile.getCardValue());
	}

	/**
	 * @author Björn Merschmeier
	 */
	@Test
	public void testCreateSetDockPile_TryAddSkipCard()
	{
		SetDockPile pile = new SetDockPile();

		Assert.assertNotNull(pile);

		Card c = new Card(Color.BLUE, CardValue.ELEVEN, 3);
		Assert.assertTrue(pile.addLast(c));
		Assert.assertEquals(CardValue.ELEVEN, pile.getCardValue());

		c = new Card(Color.GREEN, CardValue.ELEVEN, 4);
		Assert.assertTrue(pile.addFirst(c));
		Assert.assertEquals(CardValue.ELEVEN, pile.getCardValue());

		c = new Card(Color.NONE, CardValue.SKIP, 1);
		Assert.assertFalse(pile.addFirst(c));
		Assert.assertEquals(CardValue.ELEVEN, pile.getCardValue());
	}

	/**
	 * @author Björn Merschmeier
	 */
	@Test
	public void testCreateSetDockPile_TryAddSkipCardAsFirst()
	{
		SetDockPile pile = new SetDockPile();

		Assert.assertNotNull(pile);

		Card c = new Card(Color.NONE, CardValue.SKIP, 1);
		Assert.assertFalse(pile.addFirst(c));
		Assert.assertNull(pile.getCardValue());
	}
}
