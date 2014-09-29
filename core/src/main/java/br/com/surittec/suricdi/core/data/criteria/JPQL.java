/*
 * SURITTEC
 * Copyright 2014, SURITTEC CONSULTORIA LTDA, 
 * and individual contributors as indicated by the @authors tag
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package br.com.surittec.suricdi.core.data.criteria;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * JPQL Support.
 *
 */
public class JPQL {

	private EntityManager entityManager;
	
	private List<String> select;
	private List<String> from;
	private List<String> where;
	private Map<String, Object> params;
	private List<String> group;
	private List<String> order;
	
	private Integer firstResult;
	private Integer maxResults;
	
	// ----------------------------------------------------------------------------
    // CONSTRUCTOR
    // ----------------------------------------------------------------------------
	
	public JPQL(EntityManager entityManager){
		this.entityManager = entityManager;
		this.select = new ArrayList<String>();
		this.from = new ArrayList<String>();
		this.where = new ArrayList<String>();
		this.params = new HashMap<String, Object>();
		this.group = new ArrayList<String>();
		this.order = new ArrayList<String>();
	}
	
	// ----------------------------------------------------------------------------
    // PUBLIC
    // ----------------------------------------------------------------------------
	
	public JPQL select(String select){
		this.select.add(select);
		return this;
	}
	
	public JPQL from(String from){
		this.from.add(from);
		return this;
	}
	
	public JPQL where(String where){
		this.where.add(where);
		return this;
	}
	
	public JPQL withParam(String paramName, Object paramValue){
		this.params.put(paramName, paramValue);
		return this;
	}
	
	public JPQL groupBy(String group){
		this.group.add(group);
		return this;
	}
	
	public JPQL orderBy(String order){
		this.order.add(order);
		return this;
	}
	
	public JPQL firstResult(Integer firstResult){
		this.firstResult = firstResult;
		return this;
	}
	
	public JPQL maxResults(Integer maxResults){
		this.maxResults = maxResults;
		return this;
	}
	
	@SuppressWarnings("unchecked")
	public <T> List<T> getResultList(Class<T> resultType){
		return (List<T>) getQuery().getResultList();
	}
	
	public List<?> getResultList(){
		return getQuery().getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getSingleResult(Class<T> resultType){
		return (T) getSingleResult();
	}
	
	public Object getSingleResult(){
		return getQuery().getSingleResult();
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getAnyResult(Class<T> resultType){
		return (T) getAnyResult();
	}
	
	public Object getAnyResult(){
		List<?> result = getResultList();
		if(result != null && !result.isEmpty()) return result.get(0);
		return null;
	}
	
	// ----------------------------------------------------------------------------
    // PRIVATE
    // ----------------------------------------------------------------------------
	
	private Query getQuery(){

		StringBuilder queryString = new StringBuilder();
		
		if(!select.isEmpty()) append(queryString, "select", select, ",");
		append(queryString, "from", from);
		if(!where.isEmpty()) append(queryString, "where", where, "and");
		if(!group.isEmpty()) append(queryString, "group by", group, ",");
		if(!order.isEmpty()) append(queryString, "order by", order, ",");
		
		Query query = entityManager.createQuery(queryString.toString());
		
		for(String paramName : params.keySet()){
			query.setParameter(paramName, params.get(paramName));
		}
		
		if(firstResult != null) query.setFirstResult(firstResult);
		if(maxResults != null) query.setMaxResults(maxResults);
		
		return query;
	}
	
	private void append(StringBuilder sb, String part, List<?> values, String separator){
		append(sb, part);
		for(int i = 0; i < values.size(); i++){
			append(sb, values.get(i));
			if(i != (values.size() - 1)) append(sb, separator);
			sb.append(" ");
		}
	}
	
	private void append(StringBuilder sb, String part, List<?> values){
		append(sb, part);
		for(Object value : values) append(sb, value);
	}
	
	private void append(StringBuilder sb, Object value){
		sb.append(value);
		sb.append(" ");
	}
	
}
