package br.com.surittec.suricdi.core.repository.util;

import javax.persistence.EntityManager;

public abstract class EntityUtil {

	public static String getEntityName(EntityManager entityManager, Class<?> entityClass){
    	return entityManager.getMetamodel().entity(entityClass).getName();
    }
    
    public static boolean isNew(EntityManager entityManager, Object entity){
        try{
            return entityManager.getEntityManagerFactory().getPersistenceUnitUtil().getIdentifier(entity) == null;
        }catch (IllegalArgumentException e){
            // Not an entity
            return false;
        }
    }
	
}
