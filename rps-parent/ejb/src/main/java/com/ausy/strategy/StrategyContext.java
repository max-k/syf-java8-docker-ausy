package com.ausy.strategy;

import com.ausy.dto.IPlayer;

/**
 * @author dmaldonado
 *
 */
public class StrategyContext {

	private IStrategy strategy;

	/**
	 * Constructor by default
	 */
	public StrategyContext() {
		super();
	}
	
	/**
	 * @param strategy
	 */
	public StrategyContext(IStrategy strategy) {
		this();
		this.strategy = strategy;
	}

	public void executeStrategy(IPlayer player) {

		strategy.applyStrategy(player);
	}

	/**
	 * @return the strategy
	 */
	public IStrategy getStrategy() {
		return strategy;
	}

	/**
	 * @param strategy
	 *            the strategy to set
	 */
	public void setStrategy(IStrategy strategy) {
		this.strategy = strategy;
	}

}
