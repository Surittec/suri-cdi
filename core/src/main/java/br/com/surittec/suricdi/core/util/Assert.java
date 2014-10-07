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
package br.com.surittec.suricdi.core.util;

import java.util.Collection;
import java.util.Map;

import br.com.surittec.suricdi.core.exception.BusinessException;
import br.com.surittec.util.message.Message;

/**
 * Classe utilitária que realiza várias validações de parâmetros e lança
 * {@link br.com.surittec.suricdi.core.exception.BusinessException}
 * quando necessário.
 * 
 * @author Lucas Lins
 * 
 */
public abstract class Assert {
	
	/**
	 * Valida se <code>value</code> é nulo e, se não for, lança uma 
	 * {@link br.com.surittec.suricdi.core.exception.BusinessException}.
	 * 
	 * @param value
	 * @param message
	 * @param messageParams
	 */
	public static void isNull(Object value, String message, Object ... messageParams){
		if(value != null) throw new BusinessException(message, messageParams);
	}

	/**
	 * Valida se <code>value</code> é nulo e, se não for, lança uma 
	 * {@link br.com.surittec.suricdi.core.exception.BusinessException}.
	 * 
	 * @param value
	 * @param message
	 * @param messageParams
	 */
	public static void isNull(Object value, Message message){
		if(value != null) throw new BusinessException(message);
	}

	/**
	 * Valida se <code>value</code> é nulo e, se não for, lança uma 
	 * {@link br.com.surittec.suricdi.core.exception.BusinessException}.
	 * 
	 * @param value
	 * @param message
	 * @param messageParams
	 */
	public static void isNullWithComponent(Object value, String component, String message, Object ... messageParams){
		if(value != null) BusinessException.throwMessageWithComponent(component, message, messageParams);
	}

	/**
	 * Valida se <code>value</code> não é nulo e, se for, lança uma 
	 * {@link br.com.surittec.suricdi.core.exception.BusinessException}.
	 * 
	 * @param value
	 * @param message
	 * @param messageParams
	 */
	public static void notNull(Object value, String message, Object ... messageParams){
		if (value == null) throw new BusinessException(message, messageParams);
	}

	/**
	 * Valida se <code>value</code> não é nulo e, se for, lança uma 
	 * {@link br.com.surittec.suricdi.core.exception.BusinessException}.
	 * 
	 * @param value
	 * @param message
	 * @param messageParams
	 */
	public static void notNull(Object value, Message message){
		if (value == null) throw new BusinessException(message);
	}

	/**
	 * Valida se <code>value</code> não é nulo e, se for, lança uma 
	 * {@link br.com.surittec.suricdi.core.exception.BusinessException}.
	 * 
	 * @param value
	 * @param message
	 * @param messageParams
	 */
	public static void notNullWithComponent(Object value, String component, String message, Object ... messageParams){
		if (value == null) BusinessException.throwMessageWithComponent(component, message, messageParams);
	}

	/**
	 * Valida se <code>value</code> possui conteúdo textual e, se não tiver, lança uma 
	 * {@link br.com.surittec.suricdi.core.exception.BusinessException}.
	 * 
	 * @param value
	 * @param message
	 * @param messageParams
	 */
	public static void notEmpty(String value, String message, Object ... messageParams){
		if (value == null || value.trim().length() == 0) throw new BusinessException(message, messageParams);
	}

	/**
	 * Valida se <code>value</code> possui conteúdo textual e, se não tiver, lança uma 
	 * {@link br.com.surittec.suricdi.core.exception.BusinessException}.
	 * 
	 * @param value
	 * @param message
	 * @param messageParams
	 */
	public static void notEmpty(String value, Message message){
		if (value == null || value.trim().length() == 0) throw new BusinessException(message);
	}

	/**
	 * Valida se <code>value</code> possui conteúdo textual e, se não tiver, lança uma 
	 * {@link br.com.surittec.suricdi.core.exception.BusinessException}.
	 * 
	 * @param value
	 * @param message
	 * @param messageParams
	 */
	public static void notEmptyWithComponent(String value, String component, String message, Object ... messageParams){
		if (value == null || value.trim().length() == 0) BusinessException.throwMessageWithComponent(component, message, messageParams);
	}

	/**
	 * Valida se <code>value</code> possui elementos inseridos e, se não tiver, lança uma 
	 * {@link br.com.surittec.suricdi.core.exception.BusinessException}.
	 * 
	 * @param value
	 * @param message
	 * @param messageParams
	 */
	public static void notEmpty(Collection<?> value, String message, Object ... messageParams){
		notNull(value, message, messageParams);
		if (value.isEmpty()) throw new BusinessException(message, messageParams);
	}

	/**
	 * Valida se <code>value</code> possui elementos inseridos e, se não tiver, lança uma 
	 * {@link br.com.surittec.suricdi.core.exception.BusinessException}.
	 * 
	 * @param value
	 * @param message
	 * @param messageParams
	 */
	public static void notEmpty(Collection<?> value, Message message){
		notNull(value, message);
		if (value.isEmpty()) throw new BusinessException(message);
	}

	/**
	 * Valida se <code>value</code> possui elementos inseridos e, se não tiver, lança uma 
	 * {@link br.com.surittec.suricdi.core.exception.BusinessException}.
	 * 
	 * @param value
	 * @param message
	 * @param messageParams
	 */
	public static void notEmptyWithComponent(Collection<?> value, String component, String message, Object ... messageParams){
		notNullWithComponent(value, component, message, messageParams);
		if (value.isEmpty()) BusinessException.throwMessageWithComponent(component, message, messageParams);
	}

	/**
	 * Valida se <code>value</code> possui elementos inseridos e, se não tiver, lança uma 
	 * {@link br.com.surittec.suricdi.core.exception.BusinessException}.
	 * 
	 * @param value
	 * @param message
	 * @param messageParams
	 */
	public static void notEmpty(Map<?,?> value, String message, Object ... messageParams){
		notNull(value, message, messageParams);
		if (value.isEmpty()) throw new BusinessException(message, messageParams);
	}

	/**
	 * Valida se <code>value</code> possui elementos inseridos e, se não tiver, lança uma 
	 * {@link br.com.surittec.suricdi.core.exception.BusinessException}.
	 * 
	 * @param value
	 * @param message
	 * @param messageParams
	 */
	public static void notEmpty(Map<?,?> value, Message message){
		notNull(value, message);
		if (value.isEmpty()) throw new BusinessException(message);
	}

	/**
	 * Valida se <code>value</code> possui elementos inseridos e, se não tiver, lança uma 
	 * {@link br.com.surittec.suricdi.core.exception.BusinessException}.
	 * 
	 * @param value
	 * @param message
	 * @param messageParams
	 */
	public static void notEmptyWithComponent(Map<?,?> value, String component, String message, Object ... messageParams){
		notNullWithComponent(value, component, message, messageParams);
		if (value.isEmpty()) BusinessException.throwMessageWithComponent(component, message, messageParams);
	}

	/**
	 * Valida se <code>value</code> possui elementos inseridos e, se não tiver, lança uma {@link br.com.surittec.suricdi.core.exception.BusinessException}.
	 * 
	 * @param value
	 * @param message
	 * @param messageParams
	 */
	public static <T> void notEmpty(T[] value, String message, Object ... messageParams){
		notNull(value, message, messageParams);
		if(value.length == 0) throw new BusinessException(message, messageParams);
	}

	/**
	 * Valida se <code>value</code> possui elementos inseridos e, se não tiver, lança uma {@link br.com.surittec.suricdi.core.exception.BusinessException}.
	 * 
	 * @param value
	 * @param message
	 * @param messageParams
	 */
	public static <T> void notEmpty(T[] value, Message message){
		notNull(value, message);
		if(value.length == 0) throw new BusinessException(message);
	}

	/**
	 * Valida se <code>value</code> possui elementos inseridos e, se não tiver, lança uma {@link br.com.surittec.suricdi.core.exception.BusinessException}.
	 * 
	 * @param value
	 * @param message
	 * @param messageParams
	 */
	public static <T> void notEmptyWithComponent(T[] value, String component, String message, Object ... messageParams){
		notNullWithComponent(value, component, message,  messageParams);
		if(value.length == 0) BusinessException.throwMessageWithComponent(component, message, messageParams);
	}

	/**
	 * Valida se <code>value</code> é nulo ou não possui conteúdo textual e, se tiver,
	 * lança uma {@link br.com.surittec.suricdi.core.exception.BusinessException}.
	 * 
	 * @param value
	 * @param message
	 * @param messageParams
	 */
	public static void isEmpty(String value, String message, Object ... messageParams){
		if (value != null && value.trim().length() > 0) throw new BusinessException(message, messageParams);
	}

	/**
	 * Valida se <code>value</code> é nulo ou não possui conteúdo textual e, se tiver,
	 * lança uma {@link br.com.surittec.suricdi.core.exception.BusinessException}.
	 * 
	 * @param value
	 * @param message
	 * @param messageParams
	 */
	public static void isEmpty(String value, Message message){
		if (value != null && value.trim().length() > 0) throw new BusinessException(message);
	}

	/**
	 * Valida se <code>value</code> é nulo ou não possui conteúdo textual e, se tiver,
	 * lança uma {@link br.com.surittec.suricdi.core.exception.BusinessException}.
	 * 
	 * @param value
	 * @param message
	 * @param messageParams
	 */
	public static void isEmptyWithComponent(String value, String component, String message, Object ... messageParams){
		if (value != null && value.trim().length() > 0) BusinessException.throwMessageWithComponent(component, message, messageParams);
	}

	/**
	 * Valida se <code>value</code> é nulo ou não possui elementos inseridos e, se tiver,
	 * lança uma {@link br.com.surittec.suricdi.core.exception.BusinessException}.
	 * 
	 * @param value
	 * @param message
	 * @param messageParams
	 */
	public static void isEmpty(Collection<?> value, String message, Object ... messageParams){
		if (value != null && !value.isEmpty()) throw new BusinessException(message, messageParams);
	}

	/**
	 * Valida se <code>value</code> é nulo ou não possui elementos inseridos e, se tiver,
	 * lança uma {@link br.com.surittec.suricdi.core.exception.BusinessException}.
	 * 
	 * @param value
	 * @param message
	 * @param messageParams
	 */
	public static void isEmpty(Collection<?> value, Message message){
		if (value != null && !value.isEmpty()) throw new BusinessException(message);
	}

	/**
	 * Valida se <code>value</code> é nulo ou não possui elementos inseridos e, se tiver,
	 * lança uma {@link br.com.surittec.suricdi.core.exception.BusinessException}.
	 * 
	 * @param value
	 * @param message
	 * @param messageParams
	 */
	public static void isEmptyWithComponent(Collection<?> value, String component, String message, Object ... messageParams){
		if (value != null && !value.isEmpty()) BusinessException.throwMessageWithComponent(component, message, messageParams);
	}

	/**
	 * Valida se <code>value</code> é nulo ou não possui elementos inseridos e, se tiver,
	 * lança uma {@link br.com.surittec.suricdi.core.exception.BusinessException}.
	 * 
	 * @param value
	 * @param message
	 * @param messageParams
	 */
	public static <T> void isEmpty(T[] value, String message, Object ... messageParams){
		if (value != null && value.length > 0) throw new BusinessException(message, messageParams);
	}

	/**
	 * Valida se <code>value</code> é nulo ou não possui elementos inseridos e, se tiver,
	 * lança uma {@link br.com.surittec.suricdi.core.exception.BusinessException}.
	 * 
	 * @param value
	 * @param message
	 * @param messageParams
	 */
	public static <T> void isEmpty(T[] value, Message message){
		if (value != null && value.length > 0) throw new BusinessException(message);
	}

	/**
	 * Valida se <code>value</code> é nulo ou não possui elementos inseridos e, se tiver,
	 * lança uma {@link br.com.surittec.suricdi.core.exception.BusinessException}.
	 * 
	 * @param value
	 * @param message
	 * @param messageParams
	 */
	public static <T> void isEmptyWithComponent(T[] value, String component, String message, Object ... messageParams){
		if (value != null && value.length > 0) BusinessException.throwMessageWithComponent(component, message, messageParams);
	}

	/**
	 * Valida se <code>value</code> é nulo ou não possui elementos inseridos e, se tiver,
	 * lança uma {@link br.com.surittec.suricdi.core.exception.BusinessException}.
	 * 
	 * @param value
	 * @param message
	 * @param messageParams
	 */
	public static void notNullElements(Collection<?> value, String message, Object ... messageParams){
		notNull(value, message, messageParams);
		for (Object element : value) notNull(element, message, messageParams);
	}

	/**
	 * Valida se <code>value</code> é nulo ou não possui elementos inseridos e, se tiver,
	 * lança uma {@link br.com.surittec.suricdi.core.exception.BusinessException}.
	 * 
	 * @param value
	 * @param message
	 * @param messageParams
	 */
	public static void notNullElements(Collection<?> value, Message message){
		notNull(value, message);
		for (Object element : value) notNull(element, message);
	}

	/**
	 * Valida se <code>value</code> é nulo ou não possui elementos inseridos e, se tiver,
	 * lança uma {@link br.com.surittec.suricdi.core.exception.BusinessException}.
	 * 
	 * @param value
	 * @param message
	 * @param messageParams
	 */
	public static void notNullElementsWithComponent(Collection<?> value, String component, String message, Object ... messageParams){
		notNullWithComponent(value, component, message, messageParams);
		for (Object element : value) notNullWithComponent(element, component, message, messageParams);
	}

	/**
	 * Valida se <code>value</code> é nulo ou não possui elementos inseridos e, se tiver,
	 * lança uma {@link br.com.surittec.suricdi.core.exception.BusinessException}.
	 * 
	 * @param value
	 * @param message
	 * @param messageParams
	 */
	public static <T> void notNullElements(T[] value, String message, Object ... messageParams){
		notNull(value, message, messageParams);
		for (T element : value) notNull(element, message, messageParams);
	}

	/**
	 * Valida se <code>value</code> é nulo ou não possui elementos inseridos e, se tiver,
	 * lança uma {@link br.com.surittec.suricdi.core.exception.BusinessException}.
	 * 
	 * @param value
	 * @param message
	 * @param messageParams
	 */
	public static <T> void notNullElements(T[] value, Message message){
		notNull(value, message);
		for (T element : value) notNull(element, message);
	}

	/**
	 * Valida se <code>value</code> é nulo ou não possui elementos inseridos e, se tiver,
	 * lança uma {@link br.com.surittec.suricdi.core.exception.BusinessException}.
	 * 
	 * @param value
	 * @param message
	 * @param messageParams
	 */
	public static <T> void notNullElementsWithComponent(T[] value, String component, String message, Object ... messageParams){
		notNullWithComponent(value, component, message, messageParams);
		for (T element : value) notNullWithComponent(element, component, message, messageParams);
	}

	/**
	 * Valida se <code>condition</code> é válida, se não, lança uma {@link br.com.surittec.suricdi.core.exception.BusinessException}.
	 * 
	 * @param value
	 * @param message
	 * @param messageParams
	 */
	public static void isTrue(boolean condition, String message, Object ... messageParams){
		if(!condition) throw new BusinessException(message, messageParams);
	}

	/**
	 * Valida se <code>condition</code> é válida, se não, lança uma {@link br.com.surittec.suricdi.core.exception.BusinessException}.
	 * 
	 * @param value
	 * @param message
	 * @param messageParams
	 */
	public static void isTrue(boolean condition, Message message){
		if(!condition) throw new BusinessException(message);
	}

	/**
	 * Valida se <code>condition</code> é válida, se não, lança uma {@link br.com.surittec.suricdi.core.exception.BusinessException}.
	 * 
	 * @param value
	 * @param message
	 * @param messageParams
	 */
	public static void isTrueWithComponent(boolean condition, String component, String message, Object ... messageParams){
		if(!condition) BusinessException.throwMessageWithComponent(component, message, messageParams);
	}

	/**
	 * Valida se <code>condition</code> não é válida, se for, lança uma {@link br.com.surittec.suricdi.core.exception.BusinessException}.
	 * 
	 * @param value
	 * @param message
	 * @param messageParams
	 */
	public static void isFalse(boolean condition, String message, Object ... messageParams){
		if(condition) throw new BusinessException(message, messageParams);
	}

	/**
	 * Valida se <code>condition</code> não é válida, se for, lança uma {@link br.com.surittec.suricdi.core.exception.BusinessException}.
	 * 
	 * @param value
	 * @param message
	 * @param messageParams
	 */
	public static void isFalse(boolean condition, Message message){
		if(condition) throw new BusinessException(message);
	}

	/**
	 * Valida se <code>condition</code> não é válida, se for, lança uma {@link br.com.surittec.suricdi.core.exception.BusinessException}.
	 * 
	 * @param value
	 * @param message
	 * @param messageParams
	 */
	public static void isFalseWithComponent(boolean condition, String component, String message, Object ... messageParams){
		if(condition) BusinessException.throwMessageWithComponent(component, message, messageParams);
	}
}
