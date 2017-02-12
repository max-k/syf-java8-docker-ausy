package com.ausy.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.ausy.option.RpsMoveEnum;
import com.ausy.states.StateEnum;

/**
 * This is result of a tour in the game
 * 
 * @author dmaldonado
 *
 */
@Entity
@Table(name = "results", schema = "rps")
public class RpsResultEntity implements IEntity<Long> {

	private static final long serialVersionUID = -7220675220480389902L;

	/**
	 * Result number
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	/**
	 * Player one
	 */
	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST,
			CascadeType.REFRESH }, fetch = FetchType.EAGER, targetEntity = PlayerEntity.class)
	@JoinColumn(name = "playeroneid")
	private PlayerEntity playerOne;

	/**
	 * Move of player one
	 */
	@Column(name = "playeronemove", nullable = false, length = 20)
	@NotNull
	@Enumerated(EnumType.STRING)
	private RpsMoveEnum playerOneMove;

	/**
	 * Result of player one
	 */
	@Column(name = "playeroneresult", nullable = false, length = 20)
	@NotNull
	@Enumerated(EnumType.STRING)
	private StateEnum playerOneResult;

	/**
	 * Player two
	 */
	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST,
			CascadeType.REFRESH }, fetch = FetchType.EAGER, targetEntity = PlayerEntity.class)
	@JoinColumn(name = "playertwoid")
	private PlayerEntity playerTwo;

	/**
	 * Move of player two
	 */
	@Column(name = "playertwomove", nullable = false, length = 20)
	@NotNull
	@Enumerated(EnumType.STRING)
	private RpsMoveEnum playerTwoMove;

	/**
	 * Result of player two
	 */
	@Column(name = "playertworesult", nullable = false, length = 20)
	@NotNull
	@Enumerated(EnumType.STRING)
	private StateEnum playerTwoResult;

	/**
	 * @return the playerOne
	 */
	public PlayerEntity getPlayerOne() {
		return playerOne;
	}

	/**
	 * @param playerOne
	 *            the playerOne to set
	 */
	public void setPlayerOne(PlayerEntity playerOne) {
		this.playerOne = playerOne;
	}

	/**
	 * @return the playerOneMove
	 */
	public RpsMoveEnum getPlayerOneMove() {
		return playerOneMove;
	}

	/**
	 * @param playerOneMove
	 *            the playerOneMove to set
	 */
	public void setPlayerOneMove(RpsMoveEnum playerOneMove) {
		this.playerOneMove = playerOneMove;
	}

	/**
	 * @return the playerOneResult
	 */
	public StateEnum getPlayerOneResult() {
		return playerOneResult;
	}

	/**
	 * @param playerOneResult
	 *            the playerOneResult to set
	 */
	public void setPlayerOneResult(StateEnum playerOneResult) {
		this.playerOneResult = playerOneResult;
	}

	/**
	 * @return the playerTwo
	 */
	public PlayerEntity getPlayerTwo() {
		return playerTwo;
	}

	/**
	 * @param playerTwo
	 *            the playerTwo to set
	 */
	public void setPlayerTwo(PlayerEntity playerTwo) {
		this.playerTwo = playerTwo;
	}

	/**
	 * @return the playerTwoMove
	 */
	public RpsMoveEnum getPlayerTwoMove() {
		return playerTwoMove;
	}

	/**
	 * @param playerTwoMove
	 *            the playerTwoMove to set
	 */
	public void setPlayerTwoMove(RpsMoveEnum playerTwoMove) {
		this.playerTwoMove = playerTwoMove;
	}

	/**
	 * @return the playerTwoResult
	 */
	public StateEnum getPlayerTwoResult() {
		return playerTwoResult;
	}

	/**
	 * @param playerTwoResult
	 *            the playerTwoResult to set
	 */
	public void setPlayerTwoResult(StateEnum playerTwoResult) {
		this.playerTwoResult = playerTwoResult;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

}
