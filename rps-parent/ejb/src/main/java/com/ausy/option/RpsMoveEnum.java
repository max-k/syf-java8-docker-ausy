package com.ausy.option;

/**
 * All possible moves in the Game
 * 
 * @author dmaldonado
 *
 */
public enum RpsMoveEnum {

	PAPER,

	SCISSOR(),

	ROCK;

	/**
	 * 
	 * @param option
	 * @return
	 */
	private RpsMoveEnum whatWin(final RpsMoveEnum option) {

		switch (option) {
		case PAPER:
			return SCISSOR;

		case SCISSOR:
			return ROCK;

		case ROCK:
			return PAPER;

		default:
			return option;
		}
	}

	/**
	 * Determines if the option given beats the current option.
	 */
	public boolean breakBy(final RpsMoveEnum option) {

		RpsMoveEnum winner = whatWin(this);

		if (option == winner) {
			return true;
		}

		return false;
	}

	/**
	 * Get the constant corresponding to the given rps Move
	 *
	 * @param rpsMove
	 *            the value containing the enumeration value
	 * @return a {@link RpsMoveEnum}
	 */
	public static RpsMoveEnum getEnum(final String rpsMove) {

		for (RpsMoveEnum currentRpsMove : RpsMoveEnum.values()) {
			if (currentRpsMove.name().equals(rpsMove)) {
				return currentRpsMove;
			}
		}

		return null;
	}

}
