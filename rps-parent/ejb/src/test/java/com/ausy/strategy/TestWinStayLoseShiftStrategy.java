package com.ausy.strategy;

import org.junit.Assert;
import org.junit.Test;

import com.ausy.dto.IRpsState;
import com.ausy.dto.Player;
import com.ausy.dto.PlayerTypeEnum;
import com.ausy.option.RpsMoveEnum;
import com.ausy.states.StateEnum;
import com.ausy.strategy.WinStayLoseShiftStrategy;

/**
 * @author dmaldonado
 *
 */
public class TestWinStayLoseShiftStrategy {

	@Test
	public void testApplyStrategy() {

		Player player = null;
		WinStayLoseShiftStrategy strategy = new WinStayLoseShiftStrategy();

		try {
			strategy.applyStrategy(player);
			Assert.fail("An exception should be raised");
		} catch (Exception e) {
		}

		player = new Player(PlayerTypeEnum.COMPUTER);
		strategy.applyStrategy(player);
		Assert.assertNotNull(player.getMove());

		// If user wins, he stay with the same move
		IRpsState rpsState = player.getRpsState();
		rpsState.setMove(RpsMoveEnum.PAPER);
		rpsState.setState(StateEnum.WINS);
		strategy.applyStrategy(player);
		Assert.assertNotNull(player.getMove());
		Assert.assertTrue(player.getMove() == player.getRpsState().getMove());

		// If user loses, he change its last move
		rpsState.setState(StateEnum.LOSES);
		strategy.applyStrategy(player);
		Assert.assertNotNull(player.getMove());
		Assert.assertFalse(player.getMove() == player.getRpsState().getMove());
	}

}
