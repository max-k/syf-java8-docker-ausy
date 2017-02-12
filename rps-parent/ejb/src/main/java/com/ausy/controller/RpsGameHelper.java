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
import com.ausy.facade.StatefulRockPaperFacadeBean;
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

	public static void setLastHistoricalMoveFor(IPlayer playerTwo, IRpsDaoManager rpsManager) {
		List<RpsResultEntity> results;
		try {
			results = rpsManager.getRpsResultDao().getAll();

			// Inverse sort
			results.sort((resultOne, resultTwo) -> resultTwo.getId().compareTo(resultOne.getId()));

			// Change to Java 7
			Optional<RpsResultEntity> optionalForlastResultOfPlayerTwo = results.stream()
					.filter(result -> result.getPlayerOne().getName().equals(playerTwo.getName())
							|| result.getPlayerTwo().getName().equals(playerTwo.getName()))
					.findFirst();

			if (optionalForlastResultOfPlayerTwo.isPresent()) {

				RpsResultEntity lastResultOfPlayerTwo = optionalForlastResultOfPlayerTwo.get();

				RpsMoveEnum lastMoveOfPlayerTwo = null;
				StateEnum lastStateOfPlayerTwo = null;
				
				if (lastResultOfPlayerTwo.getPlayerOne().getName().equals(playerTwo.getName())) {

					lastMoveOfPlayerTwo = lastResultOfPlayerTwo.getPlayerOneMove();
					lastStateOfPlayerTwo = lastResultOfPlayerTwo.getPlayerOneResult(); 
				} else {
					
					lastMoveOfPlayerTwo = lastResultOfPlayerTwo.getPlayerTwoMove();
					lastStateOfPlayerTwo = lastResultOfPlayerTwo.getPlayerTwoResult();
				}


				playerTwo.getRpsState().setMove(lastMoveOfPlayerTwo);
				playerTwo.getRpsState().setState(lastStateOfPlayerTwo);
			}

		} catch (PersistenceException e) {
			LOG.info("Error during the computing of Number of Wins for a player");
		}
	}

}
