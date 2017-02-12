package com.ausy.strategy;

/**
 * Strategy Factory. This class determines which is the strategy to be applied
 * by a player. </br>
 * 
 * Here it is not necessary to implement a singleton design pattern because the
 * static method is enough. </br>
 * 
 * @see IStrategy.
 * 
 * @author dmaldonado
 *
 */
public final class FactoryStrategy {

	/**
	 * @return the strategy according to strategy passed as parameter
	 */
	public final static IStrategy getStrategy(final String strategy) {

		if (null == strategy) {
			throw new IllegalArgumentException("It is possible to determine the strategy with the value nulls");
		}

		IStrategy result = null;

		switch (strategy) {
		case IStrategy.RANDOM_STRATEGY:
			result = new RandomStrategy();
			break;
		case IStrategy.WINSTAY_LOSESHIFT_STRATEGY:
		default:
			result = new WinStayLoseShiftStrategy();
		}

		return result;
	}

}
