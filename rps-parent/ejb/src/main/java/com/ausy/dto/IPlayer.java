package com.ausy.dto;

import java.io.Serializable;

import com.ausy.option.RpsMoveEnum;

/**
 * All methods that must have a player.
 * 
 * @author dmaldonado
 *
 */
public interface IPlayer extends Serializable {

	void clean();

	IRpsState getRpsState();

	void setRpsState(IRpsState rpsState);

	PlayerTypeEnum getPlayerType();

	void setPlayerType(PlayerTypeEnum playerType);

	RpsMoveEnum getMove();

	void setMove(RpsMoveEnum move);
	
	String getName();
	
	void setName(final String name);
	
	boolean isComputer();

}
