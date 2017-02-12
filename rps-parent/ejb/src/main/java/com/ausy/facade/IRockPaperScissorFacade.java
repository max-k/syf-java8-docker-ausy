package com.ausy.facade;

import javax.ejb.Local;

/**
 * @author dmaldonado
 *
 */
@Local
public interface IRockPaperScissorFacade {

	/**
	 * Return the number of victory of the user.
	 * 
	 * @param userName
	 *            the user name
	 * @return number of wins
	 */
	Long getNumberOfWinOf(final String userName);

}
