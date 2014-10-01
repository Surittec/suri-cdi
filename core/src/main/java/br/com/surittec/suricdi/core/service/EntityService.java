package br.com.surittec.suricdi.core.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import br.com.surittec.suricdi.core.repository.GenericEntityRepository;

public abstract class EntityService extends Service{

	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// PROTECTED METHODS
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	protected abstract GenericEntityRepository getGenericEntityRepository();
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// PUBLIC METHODS
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	/**
     * Persist (new entity) or merge the given entity. The distinction on calling either
     * method is done based on the primary key field being null or not.
     * If this results in wrong behavior for a specific case, consider using the
     * {@link org.apache.deltaspike.data.api.EntityManagerDelegate} which offers both
     * {@code persist} and {@code merge}.
     * @param entity            Entity to save.
     * @return                  Returns the modified entity.
     */
	public <E> E save(E entity){
		return getGenericEntityRepository().save(entity);
    }
	
	/**
     * Persist (new entities) or merge the given entities. The distinction on calling either
     * method is done based on the primary key field being null or not.
     * If this results in wrong behavior for a specific case, consider using the
     * {@link org.apache.deltaspike.data.api.EntityManagerDelegate} which offers both
     * {@code persist} and {@code merge}.
     * @param entities            Entities to save.
     * @return                  Returns the modified entity.
     */
	public <E> void save(Collection<E> entities) {
		getGenericEntityRepository().save(entities);
	}
	
	/**
     * Convenience access to {@link javax.persistence.EntityManager#remove(Object)}.
     * @param entity            Entity to remove.
     */
	public <E> void remove(E entity){
		getGenericEntityRepository().remove(entity);
    }
	
	/**
     * Convenience access to {@link javax.persistence.EntityManager#remove(Object)}.
     * @param entities            Entities to remove.
     */
	public <E> void remove(Collection<E> entities) {
		getGenericEntityRepository().remove(entities);
	}
	
	/**
     * Convenience access to {@link javax.persistence.EntityManager#refresh(Object)}.
     * @param entity            Entity to refresh.
     */
	public <E> void refresh(E entity){
		getGenericEntityRepository().refresh(entity);
    }

    /**
     * Convenience access to {@link javax.persistence.EntityManager#refresh(Object)}.
     * @param entities            Entities to refresh.
     */
	public <E> void refresh(Collection<E> entities) {
		getGenericEntityRepository().refresh(entities);
	}

	/**
     * Remove the given entity from the persistence context, causing
     * a managed entity to become detached.  Unflushed changes made
     * to the entity if any (including removal of the entity),
     * will not be synchronized to the database.  Entities which
     * previously referenced the detached entity will continue to
     * reference it.
     * @param entity  entity instance
     * @throws IllegalArgumentException if the instance is not an entity
     */
	public <E> void detach(E entity) {
		getGenericEntityRepository().detach(entity);
	}

	/**
     * Remove the given entities from the persistence context, causing
     * a managed entities to become detached.  Unflushed changes made
     * to the entities if any (including removal of the entities),
     * will not be synchronized to the database.  Entities which
     * previously referenced the detached entity will continue to
     * reference it.
     * @param entities  entities instances
     * @throws IllegalArgumentException if the instance is not an entity
     */
	public <E> void detach(Collection<E> entities) {
		getGenericEntityRepository().detach(entities);
	}
    
    /**
     * Convenience access to {@link javax.persistence.EntityManager#flush()}.
     */
	public void flush(){
		getGenericEntityRepository().flush();
    }
	
	/**
     * Entity lookup by primary key. Convenicence method around
     * {@link javax.persistence.EntityManager#find(Class, Object)}.
     * @param entityClass		Entity class
     * @param primaryKey        DB primary key.
     * @return                  Entity identified by primary or null if it does not exist.
     */
	public <E, PK extends Serializable> E findBy(Class<E> entityClass, PK primaryKey){
		return getGenericEntityRepository().findBy(entityClass, primaryKey);
    }

    /**
     * Lookup all existing entities of entity class {@code <E>}.
     * @param entityClass		Entity class
     * @return                  List of entities, empty if none found.
     */
	public <E> List<E> findAll(Class<E> entityClass){
		return getGenericEntityRepository().findAll(entityClass);
    }

    /**
     * Lookup a range of existing entities of entity class {@code <E>} with support for pagination.
     * @param entityClass		Entity class
     * @param start             The starting position.
     * @param max               The maximum number of results to return
     * @return                  List of entities, empty if none found.
     */
	public <E> List<E> findAll(Class<E> entityClass, int start, int max){
		return getGenericEntityRepository().findAll(entityClass, start, max);
    }
	
	/**
     * Find entities by the given named query.
     * @param entityClass		Entity class
     * @param namedQuery        Named Query
     * @param params 	        Named Query parameters
     * @return                  List of entities, empty if none found.
     */
	public <E> List<E> findByNamedQuery(Class<E> entityClass, String namedQuery, Map<String, Object> params){
		return getGenericEntityRepository().findByNamedQuery(entityClass, namedQuery, params);
	}
	
	/**
     * Find any entity by the given named query.
     * @param entityClass		Entity class
     * @param namedQuery        Named Query
     * @param params 	        Named Query parameters
     * @return                  Entity
     */
	public <E> E findAnyByNamedQuery(Class<E> entityClass, String namedQuery, Map<String, Object> params){
		return getGenericEntityRepository().findAnyByNamedQuery(entityClass, namedQuery, params);
	}
	
	/**
     * Find single entity by the given named query.
     * @param entityClass		Entity class
     * @param namedQuery        Named Query
     * @param params 	        Named Query parameters
     * @return                  Entity
     */
	public <E> E findUniqueByNamedQuery(Class<E> entityClass, String namedQuery, Map<String, Object> params){
		return getGenericEntityRepository().findUniqueByNamedQuery(entityClass, namedQuery, params);
	}
	
}
