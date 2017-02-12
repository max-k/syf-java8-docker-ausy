package com.ausy.controller;

import org.junit.Assert;
import org.junit.Test;

import com.ausy.controller.RpsGameHelper;
import com.ausy.dto.Player;
import com.ausy.dto.PlayerTypeEnum;
import com.ausy.option.RpsMoveEnum;
import com.ausy.states.StateEnum;

/**
 * @author dmaldonado
 *
 */
public class TestRpsGameHelper {

	@Test
	public void testPlayRockPaperScissor() {

		Player playerOne = new Player();
		playerOne.setPlayerType(PlayerTypeEnum.COMPUTER);

		Player playerTwo = new Player();
		playerTwo.setPlayerType(PlayerTypeEnum.COMPUTER);

		// First time the players do not have moves
		Assert.assertNull(playerOne.getMove());
		Assert.assertNull(playerTwo.getMove());

		Assert.assertNull(playerOne.getRpsState().getMove());
		Assert.assertNull(playerTwo.getRpsState().getMove());
		// If any move the state by default is LOSE
		Assert.assertTrue(playerOne.getRpsState().getState() == StateEnum.LOSES);
		Assert.assertTrue(playerTwo.getRpsState().getState() == StateEnum.LOSES);

		RpsGameHelper.playRockPaperScissor(playerOne, playerTwo);

		// Once the user plays they will have moves and states
		Assert.assertNotNull(playerOne.getMove());
		Assert.assertNotNull(playerTwo.getMove());

		Assert.assertNotNull(playerOne.getRpsState().getMove());
		Assert.assertNotNull(playerTwo.getRpsState().getMove());
		Assert.assertNotNull(playerOne.getRpsState().getState());
		Assert.assertNotNull(playerTwo.getRpsState().getState());

		// If a player lose its move will change
		playerOne.getRpsState().setState(StateEnum.WINS);
		playerOne.getRpsState().setMove(RpsMoveEnum.PAPER);
		playerTwo.getRpsState().setState(StateEnum.LOSES);
		playerTwo.getRpsState().setMove(RpsMoveEnum.ROCK);

		RpsGameHelper.playRockPaperScissor(playerOne, playerTwo);

		// Player one stays with the same move
		Assert.assertTrue(playerOne.getMove() == RpsMoveEnum.PAPER);
		Assert.assertTrue(playerOne.getMove() == playerOne.getRpsState().getMove());
		// Player two change the move because he just lost
		Assert.assertTrue(RpsMoveEnum.ROCK != playerTwo.getRpsState().getMove());

		playerOne.getRpsState().setState(StateEnum.WINS);
		playerOne.getRpsState().setMove(RpsMoveEnum.PAPER);
		playerOne.setPlayerType(PlayerTypeEnum.HUMAN);
		playerOne.setMove(null);
		playerTwo.getRpsState().setState(StateEnum.LOSES);
		playerTwo.getRpsState().setMove(RpsMoveEnum.ROCK);

		// Expected exception because of human user does not have a move
		try {
			RpsGameHelper.playRockPaperScissor(playerOne, playerTwo);
			Assert.fail("An exception should be raised because the human user does not have any move");
		} catch (Exception e) {
		}

		// If a player lose its move will change
		playerOne.setMove(RpsMoveEnum.PAPER);
		RpsGameHelper.playRockPaperScissor(playerOne, playerTwo);

		Assert.assertTrue(playerOne.getRpsState().getMove() == playerOne.getMove());

		// If a player lose its move will change
		playerOne.setPlayerType(PlayerTypeEnum.HUMAN);
		playerOne.setMove(RpsMoveEnum.PAPER);
		playerTwo.setPlayerType(PlayerTypeEnum.HUMAN);
		playerTwo.setMove(RpsMoveEnum.ROCK);

		RpsGameHelper.playRockPaperScissor(playerOne, playerTwo);

		// The new moves become the move of the states of the players
		Assert.assertTrue(playerOne.getRpsState().getState() == StateEnum.WINS);
		Assert.assertTrue(playerTwo.getRpsState().getState() == StateEnum.LOSES);
		Assert.assertTrue(playerOne.getRpsState().getMove() == RpsMoveEnum.PAPER);
		Assert.assertTrue(playerTwo.getRpsState().getMove() == RpsMoveEnum.ROCK);

	}

}
