package com.ausy.dto;

/**
 * @author dmaldonado
 *
 */
public enum PlayerTypeEnum {

	HUMAN("noStrategy"),

	/**
	 * I assume that for the game it is the strategy by default.
	 */
	COMPUTER("WinStayLoseShift");

	/**
	 * Represents the strategy by default according to the user type
	 */
	private String strategyForPlayer;

	/**
	 * @param strategyForPlayer
	 */
	private PlayerTypeEnum(String strategyForPlayer) {
		this.strategyForPlayer = strategyForPlayer;
	}

	/**
	 * @return the strategy to apply for the current player type.
	 */
	public String getStrategyForPlayer() {

		return strategyForPlayer;
	}
}
