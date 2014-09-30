package br.com.surittec.suricdi.core.repository;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;

import br.com.surittec.suricdi.core.repository.criteria.JPQL;
import br.com.surittec.suricdi.core.repository.util.EntityUtil;

@SuppressWarnings("unchecked")
public abstract class EntityRepository<E, PK extends Serializable> {
	
	protected Class<E> type;
	
	@Inject
	protected Logger logger;
	
	@Inject
	protected EntityManager entityManager;
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// CONSTRUCTORS
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	/**
	 * Construtor que ja obtem de forma automatica o tipo do Repository.
	 */
	public EntityRepository(){
		Type superclass = getClass().getGenericSuperclass();
		if(superclass instanceof ParameterizedType){
			ParameterizedType parameterizedType = (ParameterizedType)superclass;
			if(parameterizedType.getActualTypeArguments().length > 0){
				type = (Class<E>)parameterizedType.getActualTypeArguments()[0];
			}
		}
	}
	
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
	public E save(E entity){
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
	public void save(Collection<E> entities) {
		for(E e : entities) save(e);
	}
	
	/**
     * Convenience access to {@link javax.persistence.EntityManager#remove(Object)}.
     * @param entity            Entity to remove.
     */
	public void remove(E entity){
        entityManager.remove(entity);
    }
	
	/**
     * Convenience access to {@link javax.persistence.EntityManager#remove(Object)}.
     * @param entities            Entities to remove.
     */
	public void remove(Collection<E> entities) {
		for(E e : entities) remove(e);
	}
	
	/**
     * Convenience access to {@link javax.persistence.EntityManager#refresh(Object)}.
     * @param entity            Entity to refresh.
     */
	public void refresh(E entity){
        entityManager.refresh(entity);
    }

    /**
     * Convenience access to {@link javax.persistence.EntityManager#refresh(Object)}.
     * @param entities            Entities to refresh.
     */
	public void refresh(Collection<E> entities) {
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
	public void detach(E entity) {
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
	public void detach(Collection<E> entities) {
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
     * @param primaryKey        DB primary key.
     * @return                  Entity identified by primary or null if it does not exist.
     */
	public E findBy(PK primaryKey){
        return entityManager.find(type, primaryKey);
    }

    /**
     * Lookup all existing entities of entity class {@code <E>}.
     * @return                  List of entities, empty if none found.
     */
	public List<E> findAll(){
		return jpql().from(EntityUtil.getEntityName(entityManager, type)).getResultList(type);
    }

    /**
     * Lookup a range of existing entities of entity class {@code <E>} with support for pagination.
     * @param start             The starting position.
     * @param max               The maximum number of results to return
     * @return                  List of entities, empty if none found.
     */
	public List<E> findAll(int start, int max){
		JPQL jpql = jpql().from(EntityUtil.getEntityName(entityManager, type));
        if (start > 0) jpql.firstResult(start);
        if (max > 0) jpql.maxResults(max);
        return jpql.getResultList(type);
    }
	
	/**
     * Find entities by the given named query.
     * @param namedQuery        Named Query
     * @param params 	        Named Query parameters
     * @return                  List of entities, empty if none found.
     */
	public List<E> findByNamedQuery(String namedQuery, Map<String, Object> params){
		TypedQuery<E> query = entityManager.createNamedQuery(namedQuery, type);
		if(params != null){
			for(String paramName : params.keySet()){
				query.setParameter(paramName, params.get(paramName));
			}
		}
		return query.getResultList();
	}
	
	/**
     * Find any entity by the given named query.
     * @param namedQuery        Named Query
     * @param params 	        Named Query parameters
     * @return                  Entity
     */
	public E findAnyByNamedQuery(String namedQuery, Map<String, Object> params){
		TypedQuery<E> query = entityManager.createNamedQuery(namedQuery, type);
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
     * @param namedQuery        Named Query
     * @param params 	        Named Query parameters
     * @return                  Entity
     */
	public E findUniqueByNamedQuery(String namedQuery, Map<String, Object> params){
		TypedQuery<E> query = entityManager.createNamedQuery(namedQuery, type);
		if(params != null){
			for(String paramName : params.keySet()){
				query.setParameter(paramName, params.get(paramName));
			}
		}
		return query.getSingleResult();
	}
    
}
