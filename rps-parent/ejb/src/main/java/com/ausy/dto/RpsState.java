package com.ausy.dto;

import com.ausy.option.RpsMoveEnum;
import com.ausy.states.StateEnum;

/**
 * @author dmaldonado
 *
 */
public class RpsState implements IRpsState {

	private static final long serialVersionUID = -5603721897003294028L;

	private StateEnum state;

	private RpsMoveEnum move;

	/**
	 */
	public RpsState() {
		super();
		state = StateEnum.LOSES;
	}

	/**
	 * @param state
	 * @param move
	 */
	public RpsState(StateEnum state, RpsMoveEnum move) {
		super();
		this.state = state;
		this.move = move;
	}

	/**
	 * @param state
	 *            the state to set
	 */
	public void setState(StateEnum state) {
		this.state = state;
	}

	@Override
	public StateEnum getState() {

		return state;
	}

	@Override
	public void setMove(RpsMoveEnum move) {

		this.move = move;
	}

	@Override
	public RpsMoveEnum getMove() {

		return move;
	}

	@Override
	public String toString() {
		return "RpcState [state=" + state + ", move=" + move + "]";
	}

}
