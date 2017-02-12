package com.ausy.dao.manager;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;

import com.ausy.dao.PlayerDao;
import com.ausy.dao.RpsResultDao;

@Stateless
public class RpsDaoManager extends AbstractJpaManager implements IRpsDaoManager {

	@PersistenceContext(unitName = "rpsPu")
	private EntityManager entityManager;

	private PlayerDao playerDao;

	private RpsResultDao rpsResultDao;

	/**
	 * @see com.ausy.dao.manager.AbstractJpaManager#doInitialize()
	 */
	@Override
	protected void doInitialize() {
		super.doInitialize();

		this.playerDao = addDao(new PlayerDao(this));
		this.rpsResultDao = addDao(new RpsResultDao(this));
	}

	@Override
	public EntityManager getEntityManager() {
		return this.entityManager;
	}

	@Override
	public EntityManagerFactory getEntityManagerFactory() {
		return this.entityManager.getEntityManagerFactory();
	}

	@Override
	public PlayerDao getPlayerDao() {
		return this.playerDao;
	}

	@Override
	public RpsResultDao getRpsResultDao() {
		return this.rpsResultDao;
	}

}
