package com.ausy.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.ausy.dto.PlayerTypeEnum;
import com.ausy.states.StateEnum;

/**
 * Represents a player.
 * 
 * @author dmaldonado
 *
 */
@Entity
@Table(name = "players", schema = "rps")
public class PlayerEntity implements IEntity<Long> {

	private static final long serialVersionUID = -4368831346909245256L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "name", length = 30, nullable = false, unique = true)
	@NotNull
	@Size(min = 1, max = 30)
	private String name;

	/**
	 * Player Type
	 */
	@Column(name = "playertype", nullable = false, length = 20)
	@NotNull
	@Enumerated(EnumType.STRING)
	private PlayerTypeEnum playerType;

	@Override
	public Long getId() {
		return this.id;
	}

	@Override
	public void setId(Long id) {

		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the playerType
	 */
	public PlayerTypeEnum getPlayerType() {
		return playerType;
	}

	/**
	 * @param playerType
	 *            the playerType to set
	 */
	public void setPlayerType(PlayerTypeEnum playerType) {
		this.playerType = playerType;
	}

}
