package com.ausy.dao;

import com.ausy.dto.IPlayer;
import com.ausy.entity.PlayerEntity;
import com.ausy.exception.PersistenceException;

/**
 * @author dmaldonado
 *
 */
public interface IPlayerDao {

	PlayerEntity persistPlayer(final IPlayer player) throws PersistenceException;
	
}
