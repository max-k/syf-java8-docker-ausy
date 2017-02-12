package com.ausy.strategy;

import org.junit.Assert;
import org.junit.Test;

import com.ausy.dto.Player;
import com.ausy.dto.PlayerTypeEnum;
import com.ausy.strategy.RandomStrategy;

/**
 * @author dmaldonado
 *
 */
public class TestRandomStrategy {

	@Test
	public void testApplyStrategy() {

		Player player = null;
		RandomStrategy randomStrategy = new RandomStrategy();

		try {
			randomStrategy.applyStrategy(player);
			Assert.fail("An exception should be raised");
		} catch (IllegalArgumentException e) {
		}

		Assert.assertNull(player);

		player = new Player(PlayerTypeEnum.COMPUTER);
		Assert.assertNull(player.getMove());
		randomStrategy.applyStrategy(player);
		Assert.assertNotNull(player.getMove());
	}

}
