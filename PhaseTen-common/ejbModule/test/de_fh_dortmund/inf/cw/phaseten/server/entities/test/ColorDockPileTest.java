package de_fh_dortmund.inf.cw.phaseten.server.entities.test;

import org.junit.Assert;
import org.junit.Test;

import de.fh_dortmund.inf.cw.phaseten.server.entities.Card;
import de.fh_dortmund.inf.cw.phaseten.server.entities.CardValue;
import de.fh_dortmund.inf.cw.phaseten.server.entities.Color;
import de.fh_dortmund.inf.cw.phaseten.server.entities.ColorDockPile;

/**
 * @author Björn Merschmeier
 */
public class ColorDockPileTest {
	/**
	 * @author Björn Merschmeier
	 */
	@Test
	public void testCreateColorDockPile_specialOnlyWilds()
	{
		ColorDockPile pile = new ColorDockPile();

		Assert.assertNotNull(pile);

		Card c = new Card(Color.NONE, CardValue.WILD, 1);
		Assert.assertTrue(pile.addFirst(c));
		Assert.assertNull(pile.getColor());

		c = new Card(Color.NONE, CardValue.WILD, 2);
		Assert.assertTrue(pile.addFirst(c));
		Assert.assertNull(pile.getColor());

		c = new Card(Color.NONE, CardValue.WILD, 3);
		Assert.assertTrue(pile.addLast(c));
		Assert.assertNull(pile.getColor());

		c = new Card(Color.NONE, CardValue.WILD, 4);
		Assert.assertTrue(pile.addLast(c));
		Assert.assertNull(pile.getColor());
	}

	/**
	 * @author Björn Merschmeier
	 */
	@Test
	public void testCreateColorDockPile_firstWildsThanValue()
	{
		ColorDockPile pile = new ColorDockPile();

		Assert.assertNotNull(pile);

		Card c = new Card(Color.NONE, CardValue.WILD, 1);
		Assert.assertTrue(pile.addFirst(c));
		Assert.assertNull(pile.getColor());

		c = new Card(Color.NONE, CardValue.WILD, 2);
		Assert.assertTrue(pile.addFirst(c));
		Assert.assertNull(pile.getColor());

		c = new Card(Color.BLUE, CardValue.ELEVEN, 3);
		Assert.assertTrue(pile.addLast(c));
		Assert.assertEquals(Color.BLUE, pile.getColor());

		c = new Card(Color.BLUE, CardValue.TEN, 4);
		Assert.assertTrue(pile.addFirst(c));
		Assert.assertEquals(Color.BLUE, pile.getColor());

		c = new Card(Color.GREEN, CardValue.ELEVEN, 5);
		Assert.assertFalse(pile.addLast(c));
		Assert.assertFalse(pile.addFirst(c));
		Assert.assertEquals(Color.BLUE, pile.getColor());
	}

	/**
	 * @author Björn Merschmeier
	 */
	@Test
	public void testCreateColorDockPile_firstNumbersThanWilds()
	{
		ColorDockPile pile = new ColorDockPile();

		Assert.assertNotNull(pile);

		Card c = new Card(Color.BLUE, CardValue.ELEVEN, 3);
		Assert.assertTrue(pile.addLast(c));
		Assert.assertEquals(Color.BLUE, pile.getColor());

		c = new Card(Color.BLUE, CardValue.ELEVEN, 4);
		Assert.assertTrue(pile.addFirst(c));
		Assert.assertEquals(Color.BLUE, pile.getColor());

		c = new Card(Color.NONE, CardValue.WILD, 1);
		Assert.assertTrue(pile.addFirst(c));
		Assert.assertEquals(Color.BLUE, pile.getColor());

		c = new Card(Color.NONE, CardValue.WILD, 2);
		Assert.assertTrue(pile.addFirst(c));
		Assert.assertEquals(Color.BLUE, pile.getColor());

		c = new Card(Color.GREEN, CardValue.NINE, 5);
		Assert.assertFalse(pile.addLast(c));
		Assert.assertFalse(pile.addFirst(c));
		Assert.assertEquals(Color.BLUE, pile.getColor());
	}

	/**
	 * @author Björn Merschmeier
	 */
	@Test
	public void testCreateColorDockPile_TryAddSkipCard()
	{
		ColorDockPile pile = new ColorDockPile();

		Assert.assertNotNull(pile);

		Card c = new Card(Color.BLUE, CardValue.ELEVEN, 3);
		Assert.assertTrue(pile.addLast(c));
		Assert.assertEquals(Color.BLUE, pile.getColor());

		c = new Card(Color.BLUE, CardValue.ELEVEN, 4);
		Assert.assertTrue(pile.addFirst(c));
		Assert.assertEquals(Color.BLUE, pile.getColor());

		c = new Card(Color.NONE, CardValue.SKIP, 1);
		Assert.assertFalse(pile.addFirst(c));
		Assert.assertEquals(Color.BLUE, pile.getColor());
	}

	/**
	 * @author Björn Merschmeier
	 */
	@Test
	public void testCreateColorDockPile_tryAddSkipCardAsFirst()
	{
		ColorDockPile pile = new ColorDockPile();

		Assert.assertNotNull(pile);

		Card c = new Card(Color.NONE, CardValue.SKIP, 1);
		Assert.assertFalse(pile.addFirst(c));
		Assert.assertNull(pile.getColor());
	}
}
