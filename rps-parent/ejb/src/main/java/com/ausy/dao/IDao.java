package com.ausy.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityNotFoundException;

import com.ausy.entity.IEntity;
import com.ausy.exception.BusinessException;
import com.ausy.exception.PersistenceException;

/**
 * Interface represents implementation of generic DAO.
 * 
 * @param <T>
 *            object's type, it must implements at least <code>IEntity</code>
 * @param <I>
 *            primary key's type
 * 
 * @author dmaldonado
 */
public interface IDao<T extends IEntity<I>, I extends Serializable> {

    /**
     * Retrieve an persisted object using the given id as primary key.
     * 
     * @param id
     *            object's primary key
     * @return object
     * @throws EntityNotFoundException
     *             -
     *             if not found
     */
    T load(I id) throws PersistenceException;

    /**
     * Retrieve an persisted object using the given id as primary key.
     * 
     * Returns null if not found.
     * 
     * @param id
     *            object's primary key
     * @return object
     * @throws BusinessException
     */
    T get(I id) throws PersistenceException;

    /**
     * Retrieve an persisted objects using the given ids as primary keys.
     * 
     * @param ids
     *            objects's ids
     * @return list of objects
     */
    List<T> get(I... ids) throws PersistenceException;

    /**
     * Retrieve all persisted objects.
     * 
     * @return list of objects
     */
    List<T> getAll() throws PersistenceException;

    /**
     * Save all changes made to an object.
     * 
     * @param object
     *            object
     */
    T save(T object) throws PersistenceException;

    /**
     * Save all changes made to objects.
     * 
     * @param objects
     *            objects
     */
    void save(T... objects) throws PersistenceException;

    /**
     * Save all changes made to objects.
     * 
     * @param objects
     *            objects
     */
    void save(Iterable<T> objects) throws PersistenceException;

    /**
     * Remove an object by given id. Check if object is not default one.
     * 
     * If entity implements <code>IHiddenable</code> then it is hidden instead of deleted.
     * 
     * @param id
     *            object's pk
     */
    void delete(I id) throws PersistenceException;

    /**
     * Remove objects by given ids. Check if all objects are not mark as default one.
     * 
     * If objects implement <code>IHiddenable</code> then ther are hidden instead of deleted.
     * 
     * @param ids
     *            objects's pk
     * @throws UnsupportedOperationException
     *             - if one of entities is default one
     */
    void delete(I... ids) throws PersistenceException;

    /**
     * Remove an object. Check if object is not default one.
     * 
     * If object implements <code>IHiddenable</code> then it is hidden instead of deleted.
     * 
     * @param object
     *            object
     */
    void delete(T object) throws PersistenceException;

    /**
     * Remove objects. Check if all objects are not mark as default one.
     * 
     * If objects implement <code>IHiddenable</code> then they are hidden instead of deleted.
     * 
     * @param objects
     *            objects
     */
    void delete(T... objects) throws PersistenceException;

    /**
     * Remove objects. Check if all objects are not mark as default one.
     * 
     * If objects implement <code>IHiddenable</code> then they are hidden instead of deleted.
     * 
     * @param objects
     *            objects
     */
    void delete(Iterable<T> objects) throws PersistenceException;

    /**
     * Delete all objects, including default one.
     * 
     * If objects implement <code>IHiddenable</code> then they are hidden instead of deleted.
     * 
     */
    void deleteAll() throws PersistenceException;

    /**
     * Remove the given entity from the persistence context, causing
     * a managed entity to become detached. Unflushed changes made
     * to the entity if any (including removal of the entity),
     * will not be synchronized to the database. Entities which
     * previously referenced the detached entity will continue to
     * reference it.
     * 
     * @param object
     * @throws IllegalArgumentException
     *             if the instance is not an
     *             entity
     * @since Java Persistence 2.0
     */
    void detach(T object);

    /**
     * Refresh a persistent object that may have changed in another thread/transaction.
     * 
     * @param entity
     *            transient entity
     */
    void refresh(T entity) throws PersistenceException;

    /**
     * Refresh the given objects in parameters
     * 
     * @param objects
     *            the objects
     * @throws PersistenceException  a persistence exception
     */
    void refreshAll(final Collection<T> objects) throws PersistenceException;

    /**
     * Refresh objects.
     * 
     * @param objects
     *            object s
     * @throws PersistenceException  a persistence exception
     */
    void refresh(T... objects) throws PersistenceException;

    /**
     * Load the given objects
     * 
     * @param objects
     *            the objects to load
     * @return a List
     * @throws PersistenceException  a persistence exception
     */
    List<T> loadAll(final Collection<T> objects) throws PersistenceException;

    /**
     * Load objects
     * 
     * @param objects
     * @return a list
     * @throws PersistenceException  a persistence exception
     */
    List<T> load(T... objects) throws PersistenceException;

    /**
     * Find an object list corresponding to the given parameters
     * 
     * @param name
     *            the property name
     * @param value
     *            the value
     * @return a list of T
     */
    List<T> findBy(String name, Object value) throws PersistenceException;

    /**
     * Find an object list corresponding to the given map of attributes
     *
     * @param attributes
     *            the map of attribute name and value
     *
     * @return a list of T
     */
    List<T> findBy(Map<String, Object> attributes) throws PersistenceException;

    /**
     * Find an object where the 'name' attribute = value
     * 
     * @param name
     *            the attribute name
     * @param value
     *            the attribute value
     * @return T
     * @throws PersistenceException
     *             a persistence exception
     */
    T findUniqueBy(String name, Object value) throws PersistenceException;

    /**
     * Find the object list that corresponding to the given filter in parameter
     * 
     * @param object
     *            the object filtering the result
     * @return a list
     * @throws PersistenceException
     *             a persistence exception
     */
    List<T> findBy(T object) throws PersistenceException;

    /**
     * Find the object list that corresponding to the given filter in parameter, where the result
     * begin by the start parameter and the number of object is defined by the count parameter
     * 
     * @param object
     *            the object filtering the result
     * @param start
     *            the start register number
     * @param count
     *            the register number to find
     * @param order
     *            the attribute name for which the search will be sorted
     * @param asc
     *            a flag indicating if the search is asc or desc
     * @return a list
     * @throws PersistenceException
     */
    List<T> findBy(final T object, final int start, final int count, final String order, final boolean asc) throws PersistenceException;


}