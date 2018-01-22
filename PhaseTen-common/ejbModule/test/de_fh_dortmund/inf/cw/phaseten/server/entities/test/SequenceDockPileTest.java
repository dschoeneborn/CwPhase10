package de_fh_dortmund.inf.cw.phaseten.server.entities.test;

import org.junit.Assert;
import org.junit.Test;

import de.fh_dortmund.inf.cw.phaseten.server.entities.Card;
import de.fh_dortmund.inf.cw.phaseten.server.entities.SequenceDockPile;
import de.fh_dortmund.inf.cw.phaseten.server.enumerations.CardValue;
import de.fh_dortmund.inf.cw.phaseten.server.enumerations.Color;

/**
 * @author Björn Merschmeier
 */
public class SequenceDockPileTest
{
	/**
	 * @author Björn Merschmeier
	 */
	@Test
	public void testSequenceDockPile_addNumbersOneToFour_differentColors()
	{
		SequenceDockPile pile = new SequenceDockPile();

		Assert.assertTrue(pile.addLast(new Card(Color.BLUE, CardValue.ONE, 1)));
		Assert.assertTrue(pile.addLast(new Card(Color.GREEN, CardValue.TWO, 2)));
		Assert.assertTrue(pile.addLast(new Card(Color.RED, CardValue.THREE, 3)));
		Assert.assertTrue(pile.addLast(new Card(Color.YELLOW, CardValue.FOUR, 4)));

		Assert.assertFalse(pile.addFirst(new Card(Color.BLUE, CardValue.ONE, 5)));
		Assert.assertFalse(pile.addFirst(new Card(Color.GREEN, CardValue.SIX, 7)));
		Assert.assertFalse(pile.addLast(new Card(Color.RED, CardValue.ONE, 6)));
		Assert.assertFalse(pile.addLast(new Card(Color.GREEN, CardValue.SIX, 8)));
	}

	/**
	 * @author Björn Merschmeier
	 */
	@Test
	public void testSequenceDockPile_addNumbersOneToFour_differentColors_reverseOrder()
	{
		SequenceDockPile pile = new SequenceDockPile();

		Assert.assertTrue(pile.addFirst(new Card(Color.YELLOW, CardValue.FOUR, 4)));
		Assert.assertTrue(pile.addFirst(new Card(Color.RED, CardValue.THREE, 3)));
		Assert.assertTrue(pile.addFirst(new Card(Color.GREEN, CardValue.TWO, 2)));
		Assert.assertTrue(pile.addFirst(new Card(Color.BLUE, CardValue.ONE, 1)));

		Assert.assertFalse(pile.addFirst(new Card(Color.BLUE, CardValue.ONE, 5)));
		Assert.assertFalse(pile.addFirst(new Card(Color.GREEN, CardValue.SIX, 7)));
		Assert.assertFalse(pile.addLast(new Card(Color.RED, CardValue.ONE, 6)));
		Assert.assertFalse(pile.addLast(new Card(Color.GREEN, CardValue.SIX, 8)));
	}

	/**
	 * @author Björn Merschmeier
	 */
	@Test
	public void testSequenceDockPile_addNumbersFiveToEight_differentColors()
	{
		SequenceDockPile pile = new SequenceDockPile();

		Assert.assertTrue(pile.addLast(new Card(Color.BLUE, CardValue.FIVE, 1)));
		Assert.assertTrue(pile.addLast(new Card(Color.GREEN, CardValue.SIX, 2)));
		Assert.assertTrue(pile.addLast(new Card(Color.RED, CardValue.SEVEN, 3)));
		Assert.assertTrue(pile.addLast(new Card(Color.YELLOW, CardValue.EIGHT, 4)));

		Assert.assertFalse(pile.addFirst(new Card(Color.BLUE, CardValue.ONE, 5)));
		Assert.assertFalse(pile.addFirst(new Card(Color.BLUE, CardValue.THREE, 9)));
		Assert.assertFalse(pile.addFirst(new Card(Color.GREEN, CardValue.SIX, 7)));
		Assert.assertFalse(pile.addLast(new Card(Color.GREEN, CardValue.SIX, 8)));
		Assert.assertFalse(pile.addLast(new Card(Color.RED, CardValue.ONE, 6)));
	}

	@Test
	public void testSequenceDockPile_addNumbersFiveToEight_differentColors_reverseOrder()
	{
		SequenceDockPile pile = new SequenceDockPile();

		Assert.assertTrue(pile.addFirst(new Card(Color.YELLOW, CardValue.EIGHT, 4)));
		Assert.assertTrue(pile.addFirst(new Card(Color.RED, CardValue.SEVEN, 3)));
		Assert.assertTrue(pile.addFirst(new Card(Color.GREEN, CardValue.SIX, 2)));
		Assert.assertTrue(pile.addFirst(new Card(Color.BLUE, CardValue.FIVE, 1)));

		Assert.assertFalse(pile.addFirst(new Card(Color.BLUE, CardValue.ONE, 5)));
		Assert.assertFalse(pile.addFirst(new Card(Color.BLUE, CardValue.THREE, 9)));
		Assert.assertFalse(pile.addFirst(new Card(Color.GREEN, CardValue.SIX, 7)));
		Assert.assertFalse(pile.addLast(new Card(Color.GREEN, CardValue.SIX, 8)));
		Assert.assertFalse(pile.addLast(new Card(Color.RED, CardValue.ONE, 6)));
	}

	/**
	 * @author Björn Merschmeier
	 */
	@Test
	public void testSequenceDockPile_addNumbersNineToTwelve_differentColors()
	{
		SequenceDockPile pile = new SequenceDockPile();

		Assert.assertTrue(pile.addLast(new Card(Color.BLUE, CardValue.NINE, 1)));
		Assert.assertTrue(pile.addLast(new Card(Color.GREEN, CardValue.TEN, 2)));
		Assert.assertTrue(pile.addLast(new Card(Color.RED, CardValue.ELEVEN, 3)));
		Assert.assertTrue(pile.addLast(new Card(Color.YELLOW, CardValue.TWELVE, 4)));

		Assert.assertFalse(pile.addFirst(new Card(Color.BLUE, CardValue.ONE, 5)));
		Assert.assertFalse(pile.addFirst(new Card(Color.BLUE, CardValue.SEVEN, 9)));
		Assert.assertFalse(pile.addFirst(new Card(Color.GREEN, CardValue.TWELVE, 7)));
		Assert.assertFalse(pile.addLast(new Card(Color.GREEN, CardValue.TWELVE, 8)));
		Assert.assertFalse(pile.addLast(new Card(Color.RED, CardValue.ONE, 6)));

		Assert.assertTrue(pile.addFirst(new Card(Color.BLUE, CardValue.EIGHT, 10)));
		Assert.assertTrue(pile.addFirst(new Card(Color.BLUE, CardValue.SEVEN, 9)));
	}

	/**
	 * @author Björn Merschmeier
	 */
	@Test
	public void testSequenceDockPile_addNumbersNineToTwelve_differentColors_reverseOrder()
	{
		SequenceDockPile pile = new SequenceDockPile();

		Assert.assertTrue(pile.addFirst(new Card(Color.YELLOW, CardValue.TWELVE, 4)));
		Assert.assertTrue(pile.addFirst(new Card(Color.RED, CardValue.ELEVEN, 3)));
		Assert.assertTrue(pile.addFirst(new Card(Color.GREEN, CardValue.TEN, 2)));
		Assert.assertTrue(pile.addFirst(new Card(Color.BLUE, CardValue.NINE, 1)));

		Assert.assertFalse(pile.addFirst(new Card(Color.BLUE, CardValue.ONE, 5)));
		Assert.assertFalse(pile.addFirst(new Card(Color.BLUE, CardValue.SEVEN, 9)));
		Assert.assertFalse(pile.addFirst(new Card(Color.GREEN, CardValue.TWELVE, 7)));
		Assert.assertFalse(pile.addLast(new Card(Color.GREEN, CardValue.TWELVE, 8)));
		Assert.assertFalse(pile.addLast(new Card(Color.RED, CardValue.ONE, 6)));
	}

	/**
	 * @author Björn Merschmeier
	 */
	@Test
	public void testSequenceDockPile_addOnlyWilds()
	{
		SequenceDockPile pile = new SequenceDockPile();

		Assert.assertTrue(pile.addLast(new Card(Color.NONE, CardValue.WILD, 1)));
		Assert.assertTrue(pile.addLast(new Card(Color.NONE, CardValue.WILD, 2)));
		Assert.assertTrue(pile.addLast(new Card(Color.NONE, CardValue.WILD, 3)));
		Assert.assertTrue(pile.addLast(new Card(Color.NONE, CardValue.WILD, 4)));
		Assert.assertTrue(pile.addLast(new Card(Color.NONE, CardValue.WILD, 5)));
		Assert.assertTrue(pile.addLast(new Card(Color.NONE, CardValue.WILD, 6)));
		Assert.assertTrue(pile.addLast(new Card(Color.NONE, CardValue.WILD, 7)));
		Assert.assertTrue(pile.addLast(new Card(Color.NONE, CardValue.WILD, 8)));
		Assert.assertTrue(pile.addLast(new Card(Color.NONE, CardValue.WILD, 9)));
		Assert.assertTrue(pile.addLast(new Card(Color.NONE, CardValue.WILD, 10)));
		Assert.assertTrue(pile.addLast(new Card(Color.NONE, CardValue.WILD, 11)));
		Assert.assertTrue(pile.addLast(new Card(Color.NONE, CardValue.WILD, 12)));

		Assert.assertFalse(pile.addLast(new Card(Color.NONE, CardValue.WILD, 13)));
	}

	/**
	 * @author Björn Merschmeier
	 */
	@Test
	public void testSequenceDockPile_addOnlyWilds_reverse()
	{
		SequenceDockPile pile = new SequenceDockPile();

		Assert.assertTrue(pile.addFirst(new Card(Color.NONE, CardValue.WILD, 1)));
		Assert.assertTrue(pile.addFirst(new Card(Color.NONE, CardValue.WILD, 2)));
		Assert.assertTrue(pile.addFirst(new Card(Color.NONE, CardValue.WILD, 3)));
		Assert.assertTrue(pile.addFirst(new Card(Color.NONE, CardValue.WILD, 4)));
		Assert.assertTrue(pile.addFirst(new Card(Color.NONE, CardValue.WILD, 5)));
		Assert.assertTrue(pile.addFirst(new Card(Color.NONE, CardValue.WILD, 6)));
		Assert.assertTrue(pile.addFirst(new Card(Color.NONE, CardValue.WILD, 7)));
		Assert.assertTrue(pile.addFirst(new Card(Color.NONE, CardValue.WILD, 8)));
		Assert.assertTrue(pile.addFirst(new Card(Color.NONE, CardValue.WILD, 9)));
		Assert.assertTrue(pile.addFirst(new Card(Color.NONE, CardValue.WILD, 10)));
		Assert.assertTrue(pile.addFirst(new Card(Color.NONE, CardValue.WILD, 11)));
		Assert.assertTrue(pile.addFirst(new Card(Color.NONE, CardValue.WILD, 12)));

		Assert.assertFalse(pile.addFirst(new Card(Color.NONE, CardValue.WILD, 13)));
	}

	/**
	 * @author Björn Merschmeier
	 */
	@Test
	public void testSequenceDockPile_addOnlyWilds_mixed()
	{
		SequenceDockPile pile = new SequenceDockPile();

		Assert.assertTrue(pile.addFirst(new Card(Color.NONE, CardValue.WILD, 1)));
		Assert.assertTrue(pile.addLast(new Card(Color.NONE, CardValue.WILD, 2)));
		Assert.assertTrue(pile.addFirst(new Card(Color.NONE, CardValue.WILD, 3)));
		Assert.assertTrue(pile.addLast(new Card(Color.NONE, CardValue.WILD, 4)));
		Assert.assertTrue(pile.addFirst(new Card(Color.NONE, CardValue.WILD, 5)));
		Assert.assertTrue(pile.addLast(new Card(Color.NONE, CardValue.WILD, 6)));
		Assert.assertTrue(pile.addFirst(new Card(Color.NONE, CardValue.WILD, 7)));
		Assert.assertTrue(pile.addLast(new Card(Color.NONE, CardValue.WILD, 8)));
		Assert.assertTrue(pile.addFirst(new Card(Color.NONE, CardValue.WILD, 9)));
		Assert.assertTrue(pile.addLast(new Card(Color.NONE, CardValue.WILD, 10)));
		Assert.assertTrue(pile.addFirst(new Card(Color.NONE, CardValue.WILD, 11)));
		Assert.assertTrue(pile.addLast(new Card(Color.NONE, CardValue.WILD, 12)));

		Assert.assertFalse(pile.addFirst(new Card(Color.NONE, CardValue.WILD, 13)));
		Assert.assertFalse(pile.addLast(new Card(Color.NONE, CardValue.WILD, 13)));
	}

	/**
	 * @author Björn Merschmeier
	 */
	@Test
	public void testSequenceDockPile_addNumbersOneToFourThreeWilds()
	{
		SequenceDockPile pile = new SequenceDockPile();

		Assert.assertTrue(pile.addLast(new Card(Color.NONE, CardValue.WILD, 1)));
		Assert.assertTrue(pile.addLast(new Card(Color.NONE, CardValue.WILD, 2)));
		Assert.assertTrue(pile.addLast(new Card(Color.NONE, CardValue.WILD, 3)));
		Assert.assertTrue(pile.addLast(new Card(Color.BLUE, CardValue.FOUR, 4)));

		Assert.assertFalse(pile.addFirst(new Card(Color.NONE, CardValue.WILD, 5)));
		Assert.assertTrue(pile.addLast(new Card(Color.NONE, CardValue.WILD, 5)));
	}

	/**
	 * @author Björn Merschmeier
	 */
	@Test
	public void testSequenceDockPile_addNumbersOneToFourThreeWilds_reverse()
	{
		SequenceDockPile pile = new SequenceDockPile();

		Assert.assertTrue(pile.addFirst(new Card(Color.BLUE, CardValue.FOUR, 4)));
		Assert.assertTrue(pile.addFirst(new Card(Color.NONE, CardValue.WILD, 3)));
		Assert.assertTrue(pile.addFirst(new Card(Color.NONE, CardValue.WILD, 2)));
		Assert.assertTrue(pile.addFirst(new Card(Color.NONE, CardValue.WILD, 1)));

		Assert.assertFalse(pile.addFirst(new Card(Color.NONE, CardValue.WILD, 5)));
		Assert.assertTrue(pile.addLast(new Card(Color.NONE, CardValue.WILD, 5)));
	}

	/**
	 * @author Björn Merschmeier
	 */
	@Test
	public void testSequenceDockPile_addNumbersOneToFourThreeWilds_addCardsTillTwelve()
	{
		SequenceDockPile pile = new SequenceDockPile();

		Assert.assertTrue(pile.addLast(new Card(Color.NONE, CardValue.WILD, 1)));
		Assert.assertTrue(pile.addLast(new Card(Color.NONE, CardValue.WILD, 2)));
		Assert.assertTrue(pile.addLast(new Card(Color.NONE, CardValue.WILD, 3)));
		Assert.assertTrue(pile.addLast(new Card(Color.BLUE, CardValue.FOUR, 4)));

		Assert.assertFalse(pile.addFirst(new Card(Color.NONE, CardValue.WILD, 5)));
		Assert.assertTrue(pile.addLast(new Card(Color.NONE, CardValue.WILD, 5)));
		Assert.assertFalse(pile.addLast(new Card(Color.BLUE, CardValue.SEVEN, 6)));
		Assert.assertTrue(pile.addLast(new Card(Color.GREEN, CardValue.SIX, 6)));
		Assert.assertTrue(pile.addLast(new Card(Color.NONE, CardValue.WILD, 7)));
		Assert.assertTrue(pile.addLast(new Card(Color.NONE, CardValue.WILD, 8)));
		Assert.assertTrue(pile.addLast(new Card(Color.NONE, CardValue.WILD, 9)));
		Assert.assertFalse(pile.addLast(new Card(Color.GREEN, CardValue.NINE, 10)));
		Assert.assertFalse(pile.addLast(new Card(Color.RED, CardValue.ELEVEN, 10)));
		Assert.assertTrue(pile.addLast(new Card(Color.YELLOW, CardValue.TEN, 10)));
		Assert.assertTrue(pile.addLast(new Card(Color.NONE, CardValue.WILD, 11)));
		Assert.assertTrue(pile.addLast(new Card(Color.NONE, CardValue.WILD, 12)));
	}

	/**
	 * @author Björn Merschmeier
	 */
	@Test
	public void testSequenceDockPile_addNumbersOneToFourTwoWildsInMiddle()
	{
		SequenceDockPile pile = new SequenceDockPile();

		Assert.assertTrue(pile.addLast(new Card(Color.RED, CardValue.ONE, 1)));
		Assert.assertTrue(pile.addLast(new Card(Color.NONE, CardValue.WILD, 2)));
		Assert.assertTrue(pile.addLast(new Card(Color.NONE, CardValue.WILD, 3)));
		Assert.assertTrue(pile.addLast(new Card(Color.BLUE, CardValue.FOUR, 4)));
	}

	/**
	 * @author Björn Merschmeier
	 */
	@Test
	public void testSequenceDockPile_addNumbersOneToFourTwoWildsInMiddle_reverse()
	{
		SequenceDockPile pile = new SequenceDockPile();

		Assert.assertTrue(pile.addFirst(new Card(Color.BLUE, CardValue.FOUR, 4)));
		Assert.assertTrue(pile.addFirst(new Card(Color.NONE, CardValue.WILD, 3)));
		Assert.assertTrue(pile.addFirst(new Card(Color.NONE, CardValue.WILD, 2)));
		Assert.assertTrue(pile.addFirst(new Card(Color.RED, CardValue.ONE, 1)));

		Assert.assertFalse(pile.addFirst(new Card(Color.NONE, CardValue.WILD, 5)));
		Assert.assertTrue(pile.addLast(new Card(Color.NONE, CardValue.WILD, 5)));
	}

	/**
	 * @author Björn Merschmeier
	 */
	@Test
	public void testSequenceDockPile_notValid1()
	{
		SequenceDockPile pile = new SequenceDockPile();

		Assert.assertTrue(pile.addLast(new Card(Color.NONE, CardValue.WILD, 1)));
		Assert.assertTrue(pile.addLast(new Card(Color.NONE, CardValue.WILD, 2)));
		Assert.assertFalse(pile.addLast(new Card(Color.YELLOW, CardValue.TWO, 3)));
		Assert.assertTrue(pile.addLast(new Card(Color.YELLOW, CardValue.THREE, 3)));
		Assert.assertTrue(pile.addLast(new Card(Color.NONE, CardValue.WILD, 4)));
		Assert.assertTrue(pile.addLast(new Card(Color.NONE, CardValue.WILD, 5)));
	}

	/**
	 * @author Björn Merschmeier
	 */
	@Test
	public void testSequenceDockPile_notValid2()
	{
		SequenceDockPile pile = new SequenceDockPile();

		Assert.assertTrue(pile.addFirst(new Card(Color.NONE, CardValue.WILD, 1)));
		Assert.assertTrue(pile.addFirst(new Card(Color.NONE, CardValue.WILD, 2)));
		Assert.assertFalse(pile.addFirst(new Card(Color.YELLOW, CardValue.ELEVEN, 3)));
		Assert.assertTrue(pile.addFirst(new Card(Color.YELLOW, CardValue.TEN, 3)));
		Assert.assertFalse(pile.addLast(new Card(Color.NONE, CardValue.WILD, 4)));
		Assert.assertTrue(pile.addFirst(new Card(Color.NONE, CardValue.WILD, 4)));
		Assert.assertFalse(pile.addLast(new Card(Color.NONE, CardValue.WILD, 5)));
		Assert.assertTrue(pile.addFirst(new Card(Color.NONE, CardValue.WILD, 5)));
	}
}
