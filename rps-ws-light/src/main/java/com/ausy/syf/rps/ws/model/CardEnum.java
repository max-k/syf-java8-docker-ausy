package com.ausy.syf.rps.ws.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by Nelson Levert on 2/20/17.
 */
public enum CardEnum implements Comparable<CardEnum> {
	ROCK,
	PAPER,
	SCISSORS;

	private static final List<CardEnum> VALUES = Collections.unmodifiableList(Arrays.asList(values()));
	private static final int SIZE = VALUES.size();
	private static final Random RANDOM = new Random();

	public static CardEnum getRandomCard()  {
		return VALUES.get(RANDOM.nextInt(SIZE));
	}

	public boolean isWin(CardEnum opponentCardEnum) {
		if (this.equals(ROCK) && opponentCardEnum.equals(SCISSORS)) {
			return true;
		} else if (this.equals(PAPER) && opponentCardEnum.equals(ROCK)) {
			return true;
		} else if (this.equals(SCISSORS) && opponentCardEnum.equals(PAPER)) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isLost(CardEnum opponentCardEnum) {
		if (this.equals(ROCK) && opponentCardEnum.equals(PAPER)) {
			return true;
		} else if (this.equals(PAPER) && opponentCardEnum.equals(SCISSORS)) {
			return true;
		} else if (this.equals(SCISSORS) && opponentCardEnum.equals(ROCK)) {
			return true;
		} else {
			return false;
		}
	}
}
