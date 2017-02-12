package com.ausy.strategy;

import java.util.Optional;

import com.ausy.dto.IPlayer;
import com.ausy.option.RpsMoveEnum;

/**
 * Strategy Random provides any {@link RpsMoveEnum}. </br>
 * 
 * Strategy design pattern is applied </br>
 * 
 * @author dmaldonado
 *
 */
public class RandomStrategy implements IStrategy {

	/**
	 * @see com.ausy.strategy.IStrategy#applyStrategy(com.ausy.dto.Player)
	 */
	@Override
	public void applyStrategy(IPlayer player) {

		Optional<IPlayer> optional = Optional.ofNullable(player);

		if (optional.isPresent()) {

			player.setMove(getRandomRpsMove());
		} else {

			throw new IllegalArgumentException(String.format(ERROR_MESSAGE, "Random"));
		}
	}

	public static final RpsMoveEnum getRandomRpsMove() {
		
		return RpsMoveEnum.values()[(int) (Math.random() * RpsMoveEnum.values().length)];
	}

}
