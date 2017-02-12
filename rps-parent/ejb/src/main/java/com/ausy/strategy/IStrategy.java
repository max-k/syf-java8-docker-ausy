package com.ausy.strategy;

import com.ausy.dto.IPlayer;

/**
 * This interface represent all possible strategies to be applied for a
 * Rock-Paper-Scissor player.
 * 
 * The strategy design patter is applied.
 * 
 * @author dmaldonado
 *
 */
public interface IStrategy {

	final String RANDOM_STRATEGY = "Random";
	final String WINSTAY_LOSESHIFT_STRATEGY = "WinStayLoseShift";
	final String ERROR_MESSAGE = "It is not possible to apply a %s Strategy on a player without state.";

	/**
	 * @param player
	 */
	void applyStrategy(final IPlayer player);

}
