package com.ausy.service;

import java.util.logging.Logger;

import javax.annotation.security.PermitAll;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.ausy.dto.IPlayer;
import com.ausy.dto.Player;
import com.ausy.dto.PlayerTypeEnum;
import com.ausy.dto.RpsResultDto;
import com.ausy.dto.RpsState;
import com.ausy.facade.IRockPaperScissorFacade;
import com.ausy.facade.IStatefulRockPaperScissorFacade;
import com.ausy.facade.RockPaperScissorFacade;
import com.ausy.option.RpsMoveEnum;
import com.ausy.response.Result;
import com.ausy.response.ResultCounter;

/**
 * 
 * @author dmaldonado
 *
 */
@Stateless
@Path("/rps")
public class RpsService {

	private static final Logger LOG = Logger.getLogger(RpsService.class.getName());

	@EJB
	private IRockPaperScissorFacade iRockPaperScissorFacade;

	@EJB
	private IStatefulRockPaperScissorFacade iStatefulRockPaperScissorFacade;

	/**
	 * Count the number of Wins for the player given
	 * 
	 * @param userName
	 *            the player name
	 * @return {@link ResultCounter}
	 */
	@GET
	@Path("/numberOfWins/{userName}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@PermitAll
	public ResultCounter getNumberWinsFor(@PathParam("userName") String userName) {

		Long numberOfWinOf = iRockPaperScissorFacade.getNumberOfWinOf(userName);

		ResultCounter result = new ResultCounter();
		result.setUserName(userName);
		result.setNumberOfWins(numberOfWinOf);

		return result;
	}

	/**
	 * Play Rock Paper Scissor with the move provided by the player
	 * 
	 * @return {@link Result}
	 */
	@GET
	@Path("/playRps/{playerName}/{playerMove}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@PermitAll
	public Result playRps(@PathParam("playerName") String playerName, @PathParam("playerMove") String playerMove) {

		Result result = new Result();
		
		IPlayer playerTwo = new Player();
		playerTwo.setName(PlayerTypeEnum.COMPUTER.name());
		playerTwo.setPlayerType(PlayerTypeEnum.COMPUTER);
		playerTwo.setRpsState(new RpsState());

		IPlayer playerOne = new Player();
		playerOne.setName(playerName);
		try {
			playerOne.setPlayerType(PlayerTypeEnum.HUMAN);
			playerOne.setRpsState(new RpsState());

			result.setOpponentName(playerTwo.getName());
			result.setPlayerMove(playerMove);

			playerOne.setMove(RpsMoveEnum.valueOf(playerMove));
			
			RpsResultDto resultRps = iStatefulRockPaperScissorFacade.playRps(playerOne, playerTwo);

			result.setMoveOpponent(playerTwo.getMove().name());
			result.setResult(resultRps.getPlayerOne().getRpsState().getState().name());
		} catch (IllegalArgumentException e) {
			result.setMoveOpponent("No determined Move for Opponent");
			result.setResult("Possible values for playing : PAPER, SCISSOR, ROCK");
		}

		return result;

	}

}
