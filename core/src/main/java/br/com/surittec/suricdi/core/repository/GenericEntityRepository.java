package br.com.surittec.suricdi.core.repository;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.com.surittec.suricdi.core.repository.criteria.JPQL;
import br.com.surittec.suricdi.core.repository.util.EntityUtil;

public abstract  class GenericEntityRepository {

	@Inject
	protected EntityManager entityManager;
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// PROTECTED METHODS
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	/**
	 * Create a JPQL support
	 * @return jpql
	 */
	protected JPQL jpql(){
		return new JPQL(entityManager);
	}
	
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
        if (EntityUtil.isNew(entityManager, entity)){
            entityManager.persist(entity);
            return entity;
        }
        return entityManager.merge(entity);
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
		for(E e : entities) save(e);
	}
	
	/**
     * Convenience access to {@link javax.persistence.EntityManager#remove(Object)}.
     * @param entity            Entity to remove.
     */
	public <E> void remove(E entity){
        entityManager.remove(entity);
    }
	
	/**
     * Convenience access to {@link javax.persistence.EntityManager#remove(Object)}.
     * @param entities            Entities to remove.
     */
	public <E> void remove(Collection<E> entities) {
		for(E e : entities) remove(e);
	}
	
	/**
     * Convenience access to {@link javax.persistence.EntityManager#refresh(Object)}.
     * @param entity            Entity to refresh.
     */
	public <E> void refresh(E entity){
        entityManager.refresh(entity);
    }

    /**
     * Convenience access to {@link javax.persistence.EntityManager#refresh(Object)}.
     * @param entities            Entities to refresh.
     */
	public <E> void refresh(Collection<E> entities) {
		for(E e : entities) refresh(e);
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
		entityManager.detach(entity);
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
		for(E e : entities) detach(e);
	}
    
    /**
     * Convenience access to {@link javax.persistence.EntityManager#flush()}.
     */
	public void flush(){
        entityManager.flush();
    }
	
	/**
     * Entity lookup by primary key. Convenicence method around
     * {@link javax.persistence.EntityManager#find(Class, Object)}.
     * @param entityClass		Entity class
     * @param primaryKey        DB primary key.
     * @return                  Entity identified by primary or null if it does not exist.
     */
	public <E, PK extends Serializable> E findBy(Class<E> entityClass, PK primaryKey){
        return entityManager.find(entityClass, primaryKey);
    }

    /**
     * Lookup all existing entities of entity class {@code <E>}.
     * @param entityClass		Entity class
     * @return                  List of entities, empty if none found.
     */
	public <E> List<E> findAll(Class<E> entityClass){
		return jpql().from(EntityUtil.getEntityName(entityManager, entityClass)).getResultList(entityClass);
    }

    /**
     * Lookup a range of existing entities of entity class {@code <E>} with support for pagination.
     * @param entityClass		Entity class
     * @param start             The starting position.
     * @param max               The maximum number of results to return
     * @return                  List of entities, empty if none found.
     */
	public <E> List<E> findAll(Class<E> entityClass, int start, int max){
		JPQL jpql = jpql().from(EntityUtil.getEntityName(entityManager, entityClass));
        if (start > 0) jpql.firstResult(start);
        if (max > 0) jpql.maxResults(max);
        return jpql.getResultList(entityClass);
    }
	
	/**
     * Find entities by the given named query.
     * @param entityClass		Entity class
     * @param namedQuery        Named Query
     * @param params 	        Named Query parameters
     * @return                  List of entities, empty if none found.
     */
	public <E> List<E> findByNamedQuery(Class<E> entityClass, String namedQuery, Map<String, Object> params){
		TypedQuery<E> query = entityManager.createNamedQuery(namedQuery, entityClass);
		if(params != null){
			for(String paramName : params.keySet()){
				query.setParameter(paramName, params.get(paramName));
			}
		}
		return query.getResultList();
	}
	
	/**
     * Find any entity by the given named query.
     * @param entityClass		Entity class
     * @param namedQuery        Named Query
     * @param params 	        Named Query parameters
     * @return                  Entity
     */
	public <E> E findAnyByNamedQuery(Class<E> entityClass, String namedQuery, Map<String, Object> params){
		TypedQuery<E> query = entityManager.createNamedQuery(namedQuery, entityClass);
		if(params != null){
			for(String paramName : params.keySet()){
				query.setParameter(paramName, params.get(paramName));
			}
		}
		
		List<E> result = query.getResultList();
		if(result != null && !result.isEmpty()){
			return result.get(0);
		}else{
			return null;
		}
	}
	
	/**
     * Find single entity by the given named query.
     * @param entityClass		Entity class
     * @param namedQuery        Named Query
     * @param params 	        Named Query parameters
     * @return                  Entity
     */
	public <E> E findUniqueByNamedQuery(Class<E> entityClass, String namedQuery, Map<String, Object> params){
		TypedQuery<E> query = entityManager.createNamedQuery(namedQuery, entityClass);
		if(params != null){
			for(String paramName : params.keySet()){
				query.setParameter(paramName, params.get(paramName));
			}
		}
		return query.getSingleResult();
	}
	
}
