package com.ausy.strategy;

import org.junit.Assert;
import org.junit.Test;

import com.ausy.strategy.FactoryStrategy;
import com.ausy.strategy.RandomStrategy;
import com.ausy.strategy.WinStayLoseShiftStrategy;

/**
 * @author dmaldonado
 *
 */
public class TestFactoryStrategy {

	@Test
	public void testFactoryStrategy() {

		try {

			FactoryStrategy.getStrategy(null);
			Assert.fail("An exception should be raised");
		} catch (Exception e) {
		}

		Assert.assertTrue(FactoryStrategy.getStrategy("") instanceof WinStayLoseShiftStrategy);
		Assert.assertTrue(FactoryStrategy.getStrategy("Random") instanceof RandomStrategy);
		Assert.assertTrue(FactoryStrategy.getStrategy("WinStayLoseShift") instanceof WinStayLoseShiftStrategy);

	}

}
