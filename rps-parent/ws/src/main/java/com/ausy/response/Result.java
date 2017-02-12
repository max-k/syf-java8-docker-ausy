/**
 * 
 */
package com.ausy.response;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author dmaldonado
 *
 */
@XmlRootElement(namespace = "result")
public class Result implements Serializable {

	private static final long serialVersionUID = 1L;

	private String opponentName;

	private String moveOpponent;

	private String playerMove;

	private String result;

	/**
	 * @return the opponentName
	 */
	@XmlElement(required = true, nillable = false)
	public String getOpponentName() {
		return opponentName;
	}

	/**
	 * @param opponentName
	 *            the opponentName to set
	 */
	public void setOpponentName(String opponentName) {
		this.opponentName = opponentName;
	}

	/**
	 * @return the moveOpponent
	 */
	@XmlElement(required = true, nillable = false)
	public String getMoveOpponent() {
		return moveOpponent;
	}

	/**
	 * @param moveOpponent
	 *            the moveOpponent to set
	 */
	public void setMoveOpponent(String moveOpponent) {
		this.moveOpponent = moveOpponent;
	}

	/**
	 * @return the playerMove
	 */
	@XmlElement(required = true, nillable = false)
	public String getPlayerMove() {
		return playerMove;
	}

	/**
	 * @param playerMove
	 *            the playerMove to set
	 */
	public void setPlayerMove(String playerMove) {
		this.playerMove = playerMove;
	}

	/**
	 * @return the result
	 */
	@XmlElement(required = true, nillable = false)
	public String getResult() {
		return result;
	}

	/**
	 * @param result
	 *            the result to set
	 */
	public void setResult(String result) {
		this.result = result;
	}

}
