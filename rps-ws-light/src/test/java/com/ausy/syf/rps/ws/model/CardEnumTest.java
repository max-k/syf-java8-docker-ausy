package com.ausy.syf.rps.ws.model;

import junit.framework.TestCase;
import org.junit.Test;

/**
 * Created by Nelson Levert on 2/20/17.
 */
public class CardEnumTest extends TestCase {
	@Test
	public void test() {
		// rock
		assertFalse(CardEnum.ROCK.isWin(CardEnum.ROCK));
		assertFalse(CardEnum.ROCK.isWin(CardEnum.PAPER));
		assertTrue(CardEnum.ROCK.isWin(CardEnum.SCISSORS));

		assertFalse(CardEnum.ROCK.isLost(CardEnum.ROCK));
		assertTrue(CardEnum.ROCK.isLost(CardEnum.PAPER));
		assertFalse(CardEnum.ROCK.isLost(CardEnum.SCISSORS));

		// paper
		assertTrue(CardEnum.PAPER.isWin(CardEnum.ROCK));
		assertFalse(CardEnum.PAPER.isWin(CardEnum.PAPER));
		assertFalse(CardEnum.PAPER.isWin(CardEnum.SCISSORS));

		assertFalse(CardEnum.PAPER.isLost(CardEnum.ROCK));
		assertFalse(CardEnum.PAPER.isLost(CardEnum.PAPER));
		assertTrue(CardEnum.PAPER.isLost(CardEnum.SCISSORS));

		// scissors
		assertFalse(CardEnum.SCISSORS.isWin(CardEnum.ROCK));
		assertTrue(CardEnum.SCISSORS.isWin(CardEnum.PAPER));
		assertFalse(CardEnum.SCISSORS.isWin(CardEnum.SCISSORS));

		assertTrue(CardEnum.SCISSORS.isLost(CardEnum.ROCK));
		assertFalse(CardEnum.SCISSORS.isLost(CardEnum.PAPER));
		assertFalse(CardEnum.SCISSORS.isLost(CardEnum.SCISSORS));
	}

}