package com.ausy.dao.manager;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.persistence.Cache;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import com.ausy.dao.GenericDao;
import com.ausy.entity.IEntity;


/**
 * @author dmaldonado
 * 
 */
public abstract class AbstractJpaManager {

    private Map<Class<? extends IEntity<?>>, GenericDao<? extends IEntity<?>, ? extends Serializable>> daos = new HashMap<Class<? extends IEntity<?>>, GenericDao<? extends IEntity<?>, ? extends Serializable>>();

    /**
     * Interface used to interact with the persistence context.
     * 
     * @return a {@link EntityManager} instance
     */
    public abstract EntityManager getEntityManager();

    /**
     * Interface used to interact with the entity manager factory
     * for the persistence unit.
     * 
     * @return a {@link EntityManagerFactory} instance
     */
    public abstract EntityManagerFactory getEntityManagerFactory();

    /**
     * Interface used to interact with the second-level cache.
     * 
     * @return a {@link Cache} instance
     */
    public Cache getCacheManager() {

        return getEntityManagerFactory().getCache();
    }

    /**
     * Flushes the underlying EntityManager
     */
    public void flush() {

        getEntityManager().flush();
    }

    public void refresh(Object entity) {

        getEntityManager().refresh(entity);
    }

    protected <T extends GenericDao<? extends IEntity<?>, ? extends Serializable>> T addDao(T dao) {

        if (dao != null) {
            daos.put(dao.getEntityClass(), dao);
        }
        return dao;
    }

    /**
     * @return
     */
    private Map<Class<? extends IEntity<?>>, GenericDao<? extends IEntity<?>, ? extends Serializable>> getDaos() {

        return daos;
    }

    public GenericDao<? extends IEntity<?>, ? extends Serializable> getDao(Class<? extends IEntity<?>> clazz) {

        return daos.get(clazz);
    }

    @PostConstruct
    public final void initialize() {

        this.doInitialize();
        for (GenericDao<? extends IEntity<? extends Serializable>, ? extends Serializable> dao : getDaos().values()) {
            dao.init();
        }

    }

    protected void doInitialize() {
    }
}