package com.ausy.facade;

import java.util.List;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

import com.ausy.dao.manager.IRpsDaoManager;
import com.ausy.dto.PlayerTypeEnum;
import com.ausy.dto.RpsState;
import com.ausy.entity.RpsResultEntity;
import com.ausy.exception.PersistenceException;
import com.ausy.states.StateEnum;

/**
 * @author dmaldonado
 *
 */
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class RockPaperScissorFacade implements IRockPaperScissorFacade {

	private static final Logger LOG = Logger.getLogger(RockPaperScissorFacade.class.getName());

	@EJB
	private IRpsDaoManager rpsManager;

	@Override
	public Long getNumberOfWinOf(final String userName) {

		List<RpsResultEntity> results;
		try {
			results = rpsManager.getRpsResultDao().getAll();
			
			// Start : Change To Java 8
			long count  = 0l;
			for (RpsResultEntity rpsResult : results) {
				if (isWinnerPlayer(userName, rpsResult)) {
					count++;
				}
			}
			
			return count;
			// End : Change to Java 8

		} catch (PersistenceException e) {
			LOG.info("Error during the computing of Number of Wins for a player");
		}

		return 0L;

	}

	/**
	 * Determines the whether the player given won or not.
	 * @param player the player to evaluate
	 * @param result the results of a tour
	 * @return boolean true is the player win 
	 */
	private boolean isWinnerPlayer(final String userName, RpsResultEntity result) {
		return (result.getPlayerOne().getName().equals(userName) && result.getPlayerOneResult().equals(StateEnum.WINS))
				|| (result.getPlayerTwo().getName().equals(userName)
						&& result.getPlayerTwoResult().equals(StateEnum.WINS));
	}

}

// return results.stream().filter(result -> isWinnerPlayer(userName, result)).count();