package com.ausy.dto;

import java.io.Serializable;

import com.ausy.option.RpsMoveEnum;
import com.ausy.states.StateEnum;

/**
 * Represents states in the game Rock Paper and Scissor. <br/>
 * 
 * The last State [Wins/Loses], the last move.
 * 
 * @author dmaldonado
 *
 */
public interface IRpsState extends Serializable {

	/**
	 * @return a String which represents the state name
	 */
	StateEnum getState();

	/**
	 * 
	 * @param state
	 */
	void setState(StateEnum state);

	/**
	 * 
	 * @param move
	 */
	void setMove(final RpsMoveEnum move);

	/**
	 * 
	 * @return
	 */
	RpsMoveEnum getMove();

}
