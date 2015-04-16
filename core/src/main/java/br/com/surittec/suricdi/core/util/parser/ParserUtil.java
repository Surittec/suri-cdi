/*
 * SURITTEC
 * Copyright 2015, SURITTEC CONSULTORIA LTDA, 
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
package br.com.surittec.suricdi.core.util.parser;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.deltaspike.core.api.provider.BeanProvider;

public abstract class ParserUtil {

	private static Map<Class<?>, Map<Class<?>, Class<? extends Parser<?, ?>>>> parsersMap = new HashMap<Class<?>, Map<Class<?>, Class<? extends Parser<?, ?>>>>();
	
	/*
	 * Default Methods
	 */
	
	static void register(Class<? extends Parser<?,?>> parserClass){
		ParameterizedType parameterizedType = null;
		
		for(Type genericInterface : parserClass.getGenericInterfaces()){
			if(genericInterface instanceof ParameterizedType){
				ParameterizedType pt = (ParameterizedType)genericInterface;
				if(pt.getRawType() instanceof Class<?> && Parser.class.isAssignableFrom((Class<?>)pt.getRawType())){
					parameterizedType = pt;
					break;
				}
			}
		}
		
		if(parameterizedType.getActualTypeArguments().length != 2){
			throw new RuntimeException(String.format("Parser %s must specify from and to classes", parameterizedType.getRawType()));
		}
		
		Class<?> from = (Class<?>)parameterizedType.getActualTypeArguments()[0];
		Class<?> to = (Class<?>)parameterizedType.getActualTypeArguments()[1];
		
		Map<Class<?>, Class<? extends Parser<?,?>>> fromMap = parsersMap.get(from);
		if(fromMap == null){
			fromMap = new HashMap<Class<?>, Class<? extends Parser<?,?>>>();
			parsersMap.put(from, fromMap);
		}
		
		fromMap.put(to, parserClass);
	}
	
	/*
	 * Public Methods
	 */
	
	public static <F,T> T parse(F from, Class<T> toClass){
		if(from == null) return null;
		return getParser(from, toClass).parse(from);
	}
	
	public static <F,T> List<T> parseList(List<F> listFrom, Class<T> toClass){
		if(listFrom == null || listFrom.isEmpty()) return Collections.emptyList();
		Parser<F, T> parser = getParser(listFrom.get(0), toClass);
		List<T> listTo = new ArrayList<T>();
		for(F from : listFrom) listTo.add(parser.parse(from));
		return listTo;
	}
	
	@SuppressWarnings("unchecked")
	public static <F,T> Parser<F,T> getParser(F from, Class<T> toClass){ 
		Map<Class<?>, Class<? extends Parser<?,?>>> fromMap = parsersMap.get(from.getClass());
		if(fromMap != null){
			Class<? extends Parser<?,?>> parserClass = fromMap.get(toClass);
			if(parserClass != null){
				return (Parser<F,T>) BeanProvider.getDependent(parserClass).get();
			}
		}
		throw new RuntimeException(String.format("Parser from %s to %s not found", from.getClass().getName(), toClass.getName()));
	}
}
