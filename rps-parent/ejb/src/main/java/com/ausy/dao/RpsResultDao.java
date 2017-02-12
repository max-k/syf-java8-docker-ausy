package com.ausy.dao;

import com.ausy.dao.manager.AbstractJpaManager;
import com.ausy.entity.RpsResultEntity;

/**
 * @author dmaldonado
 *
 */
public class RpsResultDao extends GenericDao<RpsResultEntity, Long> {

	/**
	 * @param manager
	 */
	public RpsResultDao(AbstractJpaManager manager) {
		super(manager);
	}

}
