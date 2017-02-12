package com.ausy.facade;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import com.ausy.controller.RpsGameHelper;
import com.ausy.dao.manager.IRpsDaoManager;
import com.ausy.dto.IPlayer;
import com.ausy.dto.Player;
import com.ausy.dto.PlayerTypeEnum;
import com.ausy.dto.RpsState;
import com.ausy.dto.RpsResultDto;
import com.ausy.entity.PlayerEntity;
import com.ausy.entity.RpsResultEntity;
import com.ausy.exception.PersistenceException;
import com.ausy.option.RpsMoveEnum;
import com.ausy.states.StateEnum;

/**
 * This represents the business component available to play RPS.
 * 
 * @author dmaldonado
 *
 */
@Stateful
@TransactionManagement(TransactionManagementType.CONTAINER)
public class StatefulRockPaperFacadeBean implements IStatefulRockPaperScissorFacade {

	private static final Logger LOG = Logger.getLogger(StatefulRockPaperFacadeBean.class.getName());

	private List<RpsResultDto> results = new ArrayList<>();

	@EJB
	private IRpsDaoManager rpsManager;

	@EJB
	private IRockPaperScissorFacade rpsFacade;

	@Override
	public void cleanGame(IPlayer playerOne, IPlayer playerTwo) {

		playerOne.clean();
		playerTwo.clean();
		results.clear();
	}

	@Override
	public void play(IPlayer playerOne, IPlayer playerTwo) {

		RpsGameHelper.playRockPaperScissor(playerOne, playerTwo);
		RpsResultDto result = new RpsResultDto(copyPlayer(playerOne), copyPlayer(playerTwo), results.size() + 1);

		results.add(result);
		saveResult(result);
	}

	private static final IPlayer copyPlayer(IPlayer playerToCopy) {

		IPlayer player = new Player();

		player.setMove(playerToCopy.getMove());
		player.setName(playerToCopy.getName());
		player.setPlayerType(playerToCopy.getPlayerType());

		player.setRpsState(new RpsState(playerToCopy.getRpsState().getState(), playerToCopy.getMove()));

		return player;
	}

	@Override
	public List<RpsResultDto> getAllRpsResultsOfCurrentGame() {

		// Change to Java 7
		results.sort((resultOne, resultTwo) -> resultOne.getId().compareTo(resultTwo.getId()));

		return new ArrayList<>(results);
	}

	@Override
	public void saveResult(RpsResultDto result) {

		try {

			PlayerEntity playerOneEntity = rpsManager.getPlayerDao().persistPlayer(result.getPlayerOne());
			PlayerEntity playerTwoEntity = rpsManager.getPlayerDao().persistPlayer(result.getPlayerTwo());

			RpsResultEntity resultEntity = new RpsResultEntity();
			resultEntity.setPlayerOne(playerOneEntity);
			resultEntity.setPlayerOneMove(result.getPlayerOne().getMove());
			resultEntity.setPlayerOneResult(result.getPlayerOne().getRpsState().getState());
			resultEntity.setPlayerTwo(playerTwoEntity);
			resultEntity.setPlayerTwoMove(result.getPlayerTwo().getMove());
			resultEntity.setPlayerTwoResult(result.getPlayerTwo().getRpsState().getState());

			rpsManager.getRpsResultDao().save(resultEntity);

		} catch (PersistenceException p) {
			LOG.info("Error during rps result persistence." + p.getMessage());
		}

	}

	@Override
	public RpsResultDto playRps(IPlayer playerOne, IPlayer playerTwo) {

		RpsGameHelper.setLastHistoricalMoveFor(playerTwo, rpsManager);

		RpsGameHelper.playRockPaperScissor(playerOne, playerTwo);
		RpsResultDto result = new RpsResultDto(copyPlayer(playerOne), copyPlayer(playerTwo));

		saveResult(result);
		return result;
	}

}
