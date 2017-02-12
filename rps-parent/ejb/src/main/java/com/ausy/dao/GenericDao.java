package com.ausy.dao;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.Transient;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Criteria;
import org.hibernate.Session;

import com.ausy.dao.manager.AbstractJpaManager;
import com.ausy.entity.IEntity;
import com.ausy.exception.PersistenceException;

/**
 * Abstract implementation of generic DAO.
 * 
 * @param <T>
 *            entity type, it must implements at least <code>IEntity</code>
 * @param <I>
 *            entity's primary key, it must be serializable
 * @see IEntity
 * 
 * @author dmaldonado
 */
public class GenericDao<T extends IEntity<I>, I extends Serializable> implements IDao<T, I> {

	/**
	 * GenericDao Logger
	 */
	private static final Logger LOG = Logger.getLogger(GenericDao.class.getName());

	private EntityManager entityManager;

	private AbstractJpaManager manager;

	private Class<T> clazz;

	/**
	 * Constructor with given {@link IEntity} implementation. Use for creating
	 * DAO without extending this class.
	 * 
	 * @param manager
	 */
	@SuppressWarnings("unchecked")
	public GenericDao(AbstractJpaManager manager) {

		this.manager = manager;
		this.entityManager = this.manager.getEntityManager();

		Type[] types = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments();

		if (types[0] instanceof ParameterizedType) {
			// If the class has parameterized types, it takes the raw type.
			ParameterizedType type = (ParameterizedType) types[0];
			clazz = (Class<T>) type.getRawType();
		} else if (types[0] instanceof TypeVariable<?>) {
			// If the class has type variable, it takes the bounds.
			TypeVariable<?> type = (TypeVariable<?>) types[0];
			clazz = (Class<T>) type.getBounds()[0];
		} else {
			clazz = (Class<T>) types[0];
		}

	}

	public Class<T> getEntityClass() {

		return clazz;
	}

	@Override
	public T load(final I id) throws PersistenceException {

		T entity = get(id);
		if (entity == null) {
			throw new PersistenceException("entity " + clazz + "#" + id + " was not found");
		}
		return entity;
	}

	@Override
	public T get(final I id) throws PersistenceException {

		try {
			return entityManager.find(clazz, id);
		} catch (IllegalArgumentException e) {
			throw new PersistenceException(e);
		}
	}

	@Override
	public List<T> get(final I... ids) throws PersistenceException {

		if (ids != null && ids.length > 0) {
			try {
				CriteriaQuery<T> criteriaQuery = getQuery();
				Root<T> from = criteriaQuery.from(clazz);
				Predicate predicate = from.get(IEntity.FIELD_ID).in((Object[]) ids);
				TypedQuery<T> typedQuery = getTypeQuery(criteriaQuery.select(from).where(predicate));
				return typedQuery.getResultList();
			} catch (RuntimeException e) {
				throw new PersistenceException(e);
			}
		}

		return Collections.emptyList();
	}

	@Override
	public List<T> getAll() throws PersistenceException {

		try {
			CriteriaQuery<T> criteriaQuery = getQuery();
			Root<T> from = criteriaQuery.from(clazz);
			TypedQuery<T> typedQuery = getTypeQuery(criteriaQuery.select(from));
			return typedQuery.getResultList();
		} catch (RuntimeException e) {
			throw new PersistenceException(e);
		}
	}

	@Override
	public T save(final T object) throws PersistenceException {

		T result = object;

		try {
			if (object.getId() != null) {
				result = entityManager.merge(object);
			} else {
				entityManager.persist(object);
			}

			entityManager.flush();
		} catch (RuntimeException e) {
			LOG.info(String.format("Error: Impossible to save the %s entity", object.toString()));
		}

		return result;
	}

	@Override
	public void save(final T... objects) throws PersistenceException {

		for (T object : objects) {
			save(object);
		}
	}

	@Override
	public void save(final Iterable<T> objects) throws PersistenceException {

		for (T object : objects) {
			save(object);
		}
	}

	@Override
	public void delete(final I id) throws PersistenceException {

		delete(load(id));
	}

	@Override
	public void delete(final I... ids) throws PersistenceException {

		deleteAll(get(ids));
	}

	/**
	 * 
	 * @param objects
	 *            Object list to delete
	 */
	private void deleteAll(final Iterable<T> objects) throws PersistenceException {

		try {
			for (T object : objects) {
				entityManager.remove(object);
			}

			entityManager.flush();
		} catch (RuntimeException e) {
			throw new PersistenceException(e);
		}
	}

	@Override
	public void delete(final T object) throws PersistenceException {

		try {
			entityManager.remove(object);
			entityManager.flush();
		} catch (RuntimeException e) {
			throw new PersistenceException(e);
		}
	}

	@Override
	public void delete(T... objects) throws PersistenceException {

		deleteAll(Arrays.asList(objects));
	}

	@Override
	public void delete(final Iterable<T> objects) throws PersistenceException {

		deleteAll(objects);
	}

	@Override
	public void deleteAll() throws PersistenceException {

		deleteAll(getAll());
	}

	@Override
	public void detach(final T object) {

		entityManager.detach(object);
	}

	@Override
	public void refresh(T entity) throws PersistenceException {

		try {

			entityManager.refresh(entity);
		} catch (RuntimeException e) {
			throw new PersistenceException(e);
		}
	}

	@Override
	public void refreshAll(final Collection<T> objects) throws PersistenceException {

		for (T object : objects) {
			refresh(object);
		}
	}

	@Override
	public void refresh(T... objects) throws PersistenceException {

		refreshAll(Arrays.asList(objects));
	}

	@Override
	public List<T> loadAll(final Collection<T> objects) throws PersistenceException {

		List<T> result = new ArrayList<T>();

		for (T object : objects) {
			result.add(load(object.getId()));
		}

		return result;
	}

	@Override
	public List<T> load(T... objects) throws PersistenceException {

		return loadAll(Arrays.asList(objects));
	}

	/**
	 * Return a CriteriaQuery for the Entity
	 * 
	 * @return
	 */
	protected CriteriaQuery<T> getQuery() {

		return entityManager.getCriteriaBuilder().createQuery(clazz);
	}

	@Override
	public List<T> findBy(String name, Object value) throws PersistenceException {

		try {
			CriteriaQuery<T> query = getFindByQuery(name, value);
			return getTypeQuery(query).getResultList();
		} catch (RuntimeException e) {
			throw new PersistenceException(e);
		}
	}

	@Override
	public List<T> findBy(Map<String, Object> attributes) throws PersistenceException {

		try {
			CriteriaQuery<T> query = getFindByQuery(attributes);
			return getTypeQuery(query).getResultList();
		} catch (RuntimeException e) {
			throw new PersistenceException(e);
		}
	}

	private CriteriaQuery<T> getFindByQuery(String name, Object value) throws PersistenceException {

		Map<String, Object> attributes = new HashMap<>();
		attributes.put(name, value);
		return getFindByQuery(attributes);
	}

	private CriteriaQuery<T> getFindByQuery(Map<String, Object> attributes) throws PersistenceException {

		try {
			CriteriaBuilder cb = getCriteriaBuilder();
			CriteriaQuery<T> criteriaQuery = cb.createQuery(clazz);
			Root<T> from = criteriaQuery.from(clazz);

			Predicate[] predicates = new Predicate[attributes.size()];
			int i = 0;

			for (Map.Entry<String, Object> entry : attributes.entrySet()) {
				predicates[i] = cb.equal(from.get(entry.getKey()), entry.getValue());
				i++;
			}
			return criteriaQuery.where(predicates);
		} catch (RuntimeException e) {
			throw new PersistenceException(e);
		}
	}

	@Override
	public T findUniqueBy(String name, Object value) throws PersistenceException {

		try {

			CriteriaQuery<T> query = getFindByQuery(name, value);
			return getFirstResult(query);
		} catch (RuntimeException e) {
			throw new PersistenceException(e);
		}
	}

	/**
	 * Return first entity
	 * 
	 * @param query
	 *            Interface of query execution
	 * @return object
	 */
	protected T getFirstResult(Query query) throws PersistenceException {

		List<T> runs = getTopResults(query, 0, 1);
		return runs.isEmpty() ? null : runs.get(0);
	}

	/**
	 * Return first entity
	 * 
	 * @param query
	 *            Interface of query execution
	 * @return an object
	 */
	protected T getFirstResult(CriteriaQuery<T> query) throws PersistenceException {

		List<T> runs = getTopResults(query, 0, 1);
		return runs.isEmpty() ? null : runs.get(0);
	}

	/**
	 * Return top n entities in result
	 * 
	 * @param query
	 *            Interface of query execution
	 * @param start
	 *            the record number to begin
	 * @param count
	 *            the record count to get
	 * @return an object list
	 */
	@SuppressWarnings("unchecked")
	protected List<T> getTopResults(Query query, int start, int count) throws PersistenceException {

		try {

			return query.setFirstResult(start).setMaxResults(start + count).getResultList();
		} catch (RuntimeException e) {
			throw new PersistenceException(e);
		}
	}

	/**
	 * Return top n entities in result
	 * 
	 * @param query
	 *            Interface of query execution
	 * @param start
	 *            the beginning record
	 * @param count
	 *            the record number to get
	 * @return an object list
	 */
	protected List<T> getTopResults(CriteriaQuery<T> query, int start, int count) throws PersistenceException {

		try {

			return entityManager.createQuery(query).setFirstResult(start).setMaxResults(count).getResultList();
		} catch (Exception e) {
			throw new PersistenceException(e);
		}
	}

	@Override
	public List<T> findBy(T object) throws PersistenceException {

		CriteriaQuery<T> criteriaQuery = getFindByQuery(object, null, false);
		TypedQuery<T> typedQuery = getTypeQuery(criteriaQuery);
		return typedQuery.getResultList();
	}

	@Override
	public List<T> findBy(T object, final int start, final int count, final String order, final boolean asc)
			throws PersistenceException {

		CriteriaQuery<T> criteriaQuery = getFindByQuery(object, order, asc);

		return getTopResults(criteriaQuery, start, count);
	}

	/**
	 * Generate a {@link CriteriaQuery} to find the entities that corresponding
	 * to the passed value
	 *
	 * @param object
	 *            the object containing the value to filter the objects
	 * @return a {@link CriteriaQuery} instance
	 * @throws PersistenceException
	 */
	public CriteriaQuery<T> getFindByQuery(final T object, final String order, final boolean asc)
			throws PersistenceException {

		try {

			CriteriaBuilder cb = getCriteriaBuilder();
			CriteriaQuery<T> criteriaQuery = cb.createQuery(clazz);
			Root<T> from = criteriaQuery.from(clazz);
			List<Predicate> restrictions = new ArrayList<Predicate>();
			Field[] fields = object.getClass().getDeclaredFields();

			for (Field field : fields) {

				if (!isFieldToCheck(field)) {
					continue;
				}

				Object value = null;

				try {
					field.setAccessible(true);
					value = field.get(object);
				} catch (IllegalArgumentException e) {
					continue;
				} catch (IllegalAccessException e) {
					continue;
				}

				if (value == null) {
					continue;
				}

				restrictions.add(cb.equal(from.get(field.getName()), value));

			}

			criteriaQuery = criteriaQuery.select(from).where(restrictions.toArray(new Predicate[0]));
			if (null != order) {
				if (asc) {
					criteriaQuery = criteriaQuery.orderBy(cb.asc(from.get(order)));
				} else {
					criteriaQuery = criteriaQuery.orderBy(cb.desc(from.get(order)));
				}
			}
			return criteriaQuery;
		} catch (RuntimeException e) {
			throw new PersistenceException(e);
		}

	}

	/**
	 * Create an instance of <code>TypedQuery</code> for executing a criteria
	 * query.
	 * 
	 * @param select
	 *            a criteria query object
	 * @return the new query instance
	 * @throws PersistenceException
	 */
	private TypedQuery<T> getTypeQuery(CriteriaQuery<T> select) throws PersistenceException {

		try {

			return entityManager.createQuery(select);
		} catch (RuntimeException e) {
			throw new PersistenceException(e);
		}
	}

	/**
	 * Determine if the given field must be checked for doing the entity request
	 * 
	 * @param field
	 *            the field to check
	 * @return a boolean
	 */
	private boolean isFieldToCheck(final Field field) {

		final boolean isIdField = field.getName().equals(IEntity.FIELD_ID);
		final boolean isFinal = (field.getModifiers() & Modifier.FINAL) == Modifier.FINAL;
		final boolean isTransient = (((field.getModifiers() & Modifier.TRANSIENT) == Modifier.TRANSIENT)
				|| (field.isAnnotationPresent(Transient.class)));
		final boolean isStatic = (field.getModifiers() & Modifier.STATIC) == Modifier.STATIC;

		if (isFinal || isTransient || isStatic || isIdField) {
			return false;
		}

		return true;
	}

	/**
	 * Return CriteriaBuilder
	 * 
	 * @return a {@link CriteriaBuilder} instance
	 */
	protected CriteriaBuilder getCriteriaBuilder() {

		return entityManager.getCriteriaBuilder();
	}

	/**
	 * @return the entityManager
	 */
	public EntityManager getEntityManager() {

		return entityManager;
	}

	/**
	 * @param entityManager
	 *            the entityManager to set
	 */
	public void setEntityManager(EntityManager entityManager) {

		this.entityManager = entityManager;
	}

	/**
	 * @return the manager
	 */
	public AbstractJpaManager getManager() {

		return manager;
	}

	/**
	 * @param manager
	 *            the manager to set
	 */
	public void setManager(AbstractJpaManager manager) {
	
		this.manager = manager;
	}

	@SuppressWarnings("unchecked")
	public <M extends AbstractJpaManager> M getTypedManager() {

		return (M) this.manager;
	}

	/**
	 * Initialize data from the DAO. This method is called automatically by the
	 * {@link AbstractJpaManager} when creating the DAO.
	 * 
	 */
	public void init() {

	}
	
	/**
     * @return The hibernate criteria from the entity class
     */
    protected Criteria getCriteria() {

        Session session = (Session)getEntityManager().getDelegate();

        return session.createCriteria(getEntityClass());
    }

}
