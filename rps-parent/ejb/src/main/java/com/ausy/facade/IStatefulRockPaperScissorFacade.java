package com.ausy.facade;

import java.util.List;

import javax.ejb.Local;

import com.ausy.dto.IPlayer;
import com.ausy.dto.RpsResultDto;

/**
 * Expose all methods that can be invoked.
 * 
 * Facade Design pattern applied
 * 
 * @author dmaldonado
 *
 */
@Local
public interface IStatefulRockPaperScissorFacade {

	/**
	 * Clean all players and the historical result
	 */
	void cleanGame(final IPlayer playerOne, final IPlayer playerTwo);

	/**
	 * Plays Rock Paper and Scissor
	 * 
	 * @param playerOne
	 *            the player One
	 * @param playerTwo
	 *            the Player Two
	 */
	void play(final IPlayer playerOne, final IPlayer playerTwo);

	/**
	 * @return all results saved until this moment
	 */
	List<RpsResultDto> getAllRpsResultsOfCurrentGame();

	/**
	 * Saves a result of the game
	 * 
	 * @param result
	 *            the result to save
	 */
	void saveResult(final RpsResultDto result);

	/**
	 * Plays Rock Paper and Scissor
	 * 
	 * @param playerOne
	 *            the player One
	 * @param playerTwo
	 *            the Player Two
	 * @return an instance of RpsResultDto
	 */
	RpsResultDto playRps(IPlayer playerOne, IPlayer playerTwo);

}
