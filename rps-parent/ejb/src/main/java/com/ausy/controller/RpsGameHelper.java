package com.ausy.controller;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import com.ausy.dao.manager.IRpsDaoManager;
import com.ausy.dto.IPlayer;
import com.ausy.dto.IRpsState;
import com.ausy.dto.RpsState;
import com.ausy.entity.RpsResultEntity;
import com.ausy.exception.PersistenceException;
import com.ausy.option.RpsMoveEnum;
import com.ausy.states.StateEnum;
import com.ausy.strategy.FactoryStrategy;
import com.ausy.strategy.IStrategy;
import com.ausy.strategy.StrategyContext;

/**
 * This is a controller which is responsible for implementing the business logic
 * for the Rps Game.
 * 
 * @author dmaldonado
 *
 */
public final class RpsGameHelper {

	private static final Logger LOG = Logger.getLogger(RpsGameHelper.class.getName());

	private RpsGameHelper() {
		super();
	}

	/**
	 * This method applies the methods to determine the move of the computer and
	 * also determine who wins.
	 * 
	 * @param playerOne
	 *            human player
	 * @param playerTwo
	 *            computer player
	 */
	public static void playRockPaperScissor(final IPlayer playerOne, final IPlayer playerTwo) {

		setRpsMove(playerOne);
		setRpsMove(playerTwo);

		IRpsState playerOneState = new RpsState();
		IRpsState playerTwoState = new RpsState();

		playerOneState.setMove(playerOne.getMove());
		playerTwoState.setMove(playerTwo.getMove());

		playerOne.setRpsState(playerOneState);
		playerTwo.setRpsState(playerTwoState);

		// Set who wins and who loses
		if (playerOneState.getMove().breakBy(playerTwoState.getMove())) {

			playerOne.getRpsState().setState(StateEnum.LOSES);
			playerTwo.getRpsState().setState(StateEnum.WINS);

		} else if (playerTwoState.getMove().breakBy(playerOneState.getMove())) {

			playerOne.getRpsState().setState(StateEnum.WINS);
			playerTwo.getRpsState().setState(StateEnum.LOSES);

		} else {

			playerOne.getRpsState().setState(StateEnum.LOSES);
			playerTwo.getRpsState().setState(StateEnum.LOSES);
		}
	}

	/**
	 * Set the move for players. The human user already constains its move, but
	 * if player is computer a strategy is applied to generate its move.
	 * 
	 * The strategy by default is WinStayLoseShift. Another strategy could be
	 * applied.
	 * 
	 * @param player
	 *            the player to eval
	 * @return
	 */
	private static final void setRpsMove(IPlayer player) {

		switch (player.getPlayerType()) {
		// The move has been selected by a human user
		case HUMAN:

			Optional<RpsMoveEnum> optional = Optional.ofNullable(player.getMove());

			if (!optional.isPresent()) {
				throw new IllegalArgumentException("A human user should already have a move");
			}
			break;

		case COMPUTER:

			StrategyContext context = new StrategyContext();
			IStrategy strategy = FactoryStrategy.getStrategy(player.getPlayerType().getStrategyForPlayer());
			context.setStrategy(strategy);
			context.executeStrategy(player);
			break;
		}
	}

	public static void setLastHistoricalMoveFor(IPlayer player, IRpsDaoManager rpsManager) {

		List<RpsResultEntity> results = null;

		try {
			results = rpsManager.getRpsResultDao().getAll();

			// Inverse sort
			// Start : Change to java 7
			results.sort((resultOne, resultTwo) -> resultTwo.getId().compareTo(resultOne.getId()));
			// End : Change to java 7
			
			// Start : Change to Java 8
			RpsResultEntity lastResultOfPlayer = null;

			for (RpsResultEntity rpsResult : results) {
				if (rpsResult.getPlayerOne().getName().equals(player.getName())
						|| rpsResult.getPlayerTwo().getName().equals(player.getName())) {
					lastResultOfPlayer = rpsResult;
				}
			}

			if (lastResultOfPlayer != null) {

				RpsMoveEnum lastMoveOfPlayer = null;
				StateEnum lastStateOfPlayer = null;

				if (lastResultOfPlayer.getPlayerOne().getName().equals(player.getName())) {

					lastMoveOfPlayer = lastResultOfPlayer.getPlayerOneMove();
					lastStateOfPlayer = lastResultOfPlayer.getPlayerOneResult();
				} else {

					lastMoveOfPlayer = lastResultOfPlayer.getPlayerTwoMove();
					lastStateOfPlayer = lastResultOfPlayer.getPlayerTwoResult();
				}

				player.getRpsState().setMove(lastMoveOfPlayer);
				player.getRpsState().setState(lastStateOfPlayer);
			}

		} catch (PersistenceException e) {
			LOG.info("Error during the computing of Number of Wins for a player");
		}
	}

}

// End : Change to Java 8

// Optional<RpsResultEntity> optionalForlastResultOfPlayer =
// results.stream()
// .filter(result ->
// result.getPlayerOne().getName().equals(player.getName())
// || result.getPlayerTwo().getName().equals(player.getName()))
// .findFirst();
//
// if (optionalForlastResultOfPlayer.isPresent()) {
//
// RpsResultEntity lastResultOfPlayerChanged =
// optionalForlastResultOfPlayer.get();
//
// RpsMoveEnum lastMoveOfPlayerTwo = null;
// StateEnum lastStateOfPlayerTwo = null;
//
// if
// (lastResultOfPlayerChanged.getPlayerOne().getName().equals(player.getName()))
// {
//
// lastMoveOfPlayerTwo =
// lastResultOfPlayerChanged.getPlayerOneMove();
// lastStateOfPlayerTwo =
// lastResultOfPlayerChanged.getPlayerOneResult();
// } else {
//
// lastMoveOfPlayerTwo =
// lastResultOfPlayerChanged.getPlayerTwoMove();
// lastStateOfPlayerTwo =
// lastResultOfPlayerChanged.getPlayerTwoResult();
// }
//
// player.getRpsState().setMove(lastMoveOfPlayerTwo);
// player.getRpsState().setState(lastStateOfPlayerTwo);
// }
