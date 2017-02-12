package com.ausy.dto;

import java.io.Serializable;

/**
 * This is result of a tour in the game
 * 
 * @author dmaldonado
 *
 */
public class RpsResultDto implements Serializable {

	private static final long serialVersionUID = -7220675220480389902L;

	/**
	 * Result number
	 */
	private Integer id;

	/**
	 * Player one
	 */
	private IPlayer playerOne;

	/**
	 * Player two
	 */
	private IPlayer playerTwo;
	
	/**
	 * The players instance will fill the result of a tour
	 */
	public RpsResultDto(IPlayer playerOne, IPlayer playerTwo) {

		this(playerOne, playerTwo, 0);
	}

	/**
	 * The players instance will fill the result of a tour
	 */
	public RpsResultDto(IPlayer playerOne, IPlayer playerTwo, int id) {
		super();

		if (playerOne == null || playerTwo == null) {
			throw new IllegalArgumentException("Imposible to create an instance of RpsResultDto without players");
		}

		this.id = id;
		this.playerOne = playerOne;
		this.playerTwo = playerTwo;
	}

	/**
	 * @return the playerOne
	 */
	public IPlayer getPlayerOne() {
		return playerOne;
	}

	/**
	 * @param playerOne
	 *            the playerOne to set
	 */
	public void setPlayerOne(IPlayer playerOne) {
		this.playerOne = playerOne;
	}

	/**
	 * @return the playerTwo
	 */
	public IPlayer getPlayerTwo() {
		return playerTwo;
	}

	/**
	 * @param playerTwo
	 *            the playerTwo to set
	 */
	public void setPlayerTwo(IPlayer playerTwo) {
		this.playerTwo = playerTwo;
	}

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

}
