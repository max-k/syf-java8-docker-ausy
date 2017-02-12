package com.ausy.strategy;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.ausy.dto.IPlayer;
import com.ausy.option.RpsMoveEnum;
import com.ausy.states.StateEnum;

/**
 * This is a strategy determined by a Chinese study which determined that when
 * players win, they stay with the same move and if they lose they change their
 * moves. </br>
 * 
 * Strategy design pattern is applied.</br>
 * 
 * @author dmaldonado
 *
 */
public class WinStayLoseShiftStrategy implements IStrategy {

	private static final Map<StateEnum, IDetermineOptionRPS> behaviors;

	static {
		
		// Java 8

		// Parameterization of behavior
		behaviors = new HashMap<>();
		// WinStay
		behaviors.put(StateEnum.WINS, (IPlayer player) -> player.getRpsState().getMove());
		// LoseShift
		behaviors.put(StateEnum.LOSES, (IPlayer player) -> {

			return getNewMoveWhenUserLose(player);
		});
	}

	/**
	 * Determine the new move for the player.
	 */
	private static RpsMoveEnum getNewMoveWhenUserLose(IPlayer player) {

		RpsMoveEnum move = RandomStrategy.getRandomRpsMove();
		RpsMoveEnum oldMove = player.getRpsState().getMove();

		while (oldMove != null && move == player.getRpsState().getMove()) {

			move = RandomStrategy.getRandomRpsMove();
		}

		return move;
	}

	/**
	 * @see com.ausy.strategy.IStrategy#applyStrategy(com.ausy.dto.
	 *      Player)
	 */
	@Override
	public void applyStrategy(IPlayer player) {

		Optional<IPlayer> optional = Optional.ofNullable(player);

		if (optional.isPresent()) {

			player.setMove(behaviors.get(player.getRpsState().getState()).getMove(player));
		} else {

			throw new IllegalArgumentException(String.format(ERROR_MESSAGE, "WinStayLoseShift"));
		}

	}

	public interface IDetermineOptionRPS {

		RpsMoveEnum getMove(final IPlayer player);

	}

}
