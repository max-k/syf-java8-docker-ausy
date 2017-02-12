package com.ausy.dto;

import com.ausy.option.RpsMoveEnum;

/**
 * Represents a player in Rock Paper and Scissor Game
 * 
 * @author dmaldonado
 *
 */
public class Player implements IPlayer {

	private static final long serialVersionUID = 1800686635379289441L;

	/**
	 * Last State in the game
	 */
	private IRpsState rpsState;

	/**
	 * User Type [Human/Computer]
	 */
	private PlayerTypeEnum playerType;
	
	/**
	 * Name of the player
	 */
	private String name;

	/**
	 * Current move
	 */
	private RpsMoveEnum move;

	/**
	 * Default constructor
	 */
	public Player() {
		super();
		rpsState = new RpsState();
	}

	/**
	 * 
	 * @param playerType
	 */
	public Player(PlayerTypeEnum playerType) {

		this();
		this.playerType = playerType;
	}

	/**
	 * Clean the state of the player
	 */
	@Override
	public void clean() {

		this.setRpsState(new RpsState());
		this.setMove(null);
	}

	/**
	 * @return the rpsState
	 */
	@Override
	public IRpsState getRpsState() {
		return rpsState;
	}

	/**
	 * @param rpsState
	 *            the rpsState to set
	 */
	@Override
	public void setRpsState(IRpsState rpsState) {
		this.rpsState = rpsState;
	}

	/**
	 * @return the playerType
	 */
	@Override
	public PlayerTypeEnum getPlayerType() {
		return playerType;
	}

	/**
	 * @param playerType
	 *            the playerType to set
	 */
	@Override
	public void setPlayerType(PlayerTypeEnum playerType) {
		this.playerType = playerType;
	}

	/**
	 * @return the move
	 */
	@Override
	public RpsMoveEnum getMove() {
		return move;
	}

	/**
	 * @param move
	 *            the move to set
	 */
	@Override
	public void setMove(RpsMoveEnum move) {
		this.move = move;
	}

	@Override
	public String toString() {
		return "Player [name =" + name + " rpsState=" + rpsState.toString() + ", playerType=" + playerType + ", move=" + move + "]";
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public void setName(String name) {
		
		this.name = name;
	}

	@Override
	public boolean isComputer() {

		return PlayerTypeEnum.COMPUTER == this.playerType;
	}

}
