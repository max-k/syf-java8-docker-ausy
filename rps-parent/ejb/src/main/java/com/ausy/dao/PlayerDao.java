package com.ausy.dao;

import java.util.Optional;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import com.ausy.dao.manager.AbstractJpaManager;
import com.ausy.dto.IPlayer;
import com.ausy.entity.PlayerEntity;
import com.ausy.exception.PersistenceException;

/**
 * @author dmaldonado
 *
 */
public class PlayerDao extends GenericDao<PlayerEntity, Long> implements IPlayerDao {

	public PlayerDao(AbstractJpaManager manager) {
		super(manager);
	}

	@Override
	public PlayerEntity persistPlayer(IPlayer player) throws PersistenceException {

		Criteria criteria = getCriteria();
		criteria.add(Restrictions.eq("name", player.getName()));
		criteria.add(Restrictions.eq("playerType", player.getPlayerType()));

		// Java 8
		Optional<PlayerEntity> playerOptional = Optional.ofNullable((PlayerEntity) criteria.uniqueResult());

		if (!playerOptional.isPresent()) {

			PlayerEntity playerEntity = new PlayerEntity();
			playerEntity.setName(player.getName());
			playerEntity.setPlayerType(player.getPlayerType());
			playerEntity = save(playerEntity);
			playerOptional = Optional.of(playerEntity);
		}

		return playerOptional.get();
	}

}
