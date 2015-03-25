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
package br.com.surittec.suricdi.core.exception;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.ApplicationException;

import br.com.surittec.util.message.Message;

/**
 * Excecao de negocio. Atraves desta classe e possivel passar mensagens que serao exibidas 
 * diretamente na tela do usuario.
 * 
 * @author Lucas Lins
 *
 */
@ApplicationException(rollback = true)
public class BusinessException extends RuntimeException{
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// ATTRIBUTES
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	private static final long serialVersionUID = 1L;
	
	private List<Message> errors = new ArrayList<Message>();
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// CONSTRUCTORS
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	/**
	 * Instancia uma nova BusinessException sem nenhum código de mensagem lançado.
	 */
	public BusinessException() {
		super();
	}

	/**
	 * Instancia uma nova BusinessException com uma mensagem de valor <code>message</code>
	 * e com possíveis parâmetros em <code>params</code>.
	 */
	public BusinessException(String message, Object ... params){
		addMessage(message, params);
	}

	/**
	 * Instancia uma nova BusinessException contendo a <code>Message</code> passada por
	 * parâmetro.
	 */
	public BusinessException(Message message){
		addMessage(message);
	}
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// THROWS METHODS
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

	/**
	 * Instancia e lança uma nova BusinessException com uma mensagem de valor
	 * <code>message</code> e com possíveis parâmetros em <code>params</code>.
	 */
	public static void throwMessage(String message, Object ... messageParams){
		throw new BusinessException(message, messageParams);
	}

	/**
	 * Instancia e lança uma nova BusinessException contendo a
	 * <code>Message</code> passada por parâmetro.
	 */
	public static void throwMessage(Message message){
		throw new BusinessException(message);
	}

	/**
	 * Instancia e lança uma nova BusinessException com uma mensagem de código
	 * <code>code</code>, uma mensagem <code>message</code> e com possíveis parâmetros
	 * em <code>messageParams</code>.
	 */
	public static void throwMessageWithCode(String code, String message, Object ... messageParams){
		BusinessException be = new BusinessException();
		be.addMessageWithCode(code, message, messageParams);
		throw be;
	}

	/**
	 * Instancia e lança uma nova BusinessException com uma mensagem específica de um campo
	 * <code>component</code>, uma mensagem <code>message</code> e com possíveis parâmetros
	 * em <code>messageParams</code>.
	 */
	public static void throwMessageWithComponent(String component, String message, Object ... messageParams){
		BusinessException be = new BusinessException();
		be.addMessageWithComponent(component, message, messageParams);
		throw be;
	}

	/**
	 * Instancia e lança uma nova BusinessException com uma mensagem de código
	 * <code>code</code>, específica de um campo <code>component</code>, uma mensagem
	 * <code>message</code> e com possíveis parâmetros em <code>messageParams</code>.
	 */
	public static void throwMessageWithCodeAndComponent(String code, String component, String message, Object ... messageParams){
		BusinessException be = new BusinessException();
		be.addMessageWithCodeAndComponent(code, component, message, messageParams);
		throw be;
	}
	
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	// PUBLIC METHODS
	// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	
	/**
	 * Inclui uma nova mensagem na lista de erros da exceção.
	 * @param message
	 */
	public void addMessage(Message message){
		errors.add(message);
	}

	/**
	 * Inclui uma nova mensagem de valor <code>message</code> e com possíveis
	 * parâmetros em <code>params</code> na lista de erros da exceção.
	 * @param message
	 */
	public void addMessage(String message, Object ... messageParams){
		addMessage(new Message(null, null, message, messageParams));
	}

	/**
	 * Inclui uma nova mensagem de código <code>code</code>, valor <code>message</code>
	 * e com possíveis parâmetros em <code>params</code> na lista de erros da exceção.
	 * @param message
	 */
	public void addMessageWithCode(String code, String message, Object ... messageParams){
		addMessage(new Message(code, null, message, messageParams));
	}

	/**
	 * Inclui uma nova mensagem para o campo <code>component</code>, valor <code>message</code>
	 * e com possíveis parâmetros em <code>params</code> na lista de erros da exceção.
	 * @param message
	 */
	public void addMessageWithComponent(String component, String message, Object ... messageParams){
		addMessage(new Message(null, component, message, messageParams));
	}

	/**
	 * Inclui uma nova mensagem de código <code>code</code>, para o campo <code>component</code>,
	 * valor <code>message</code> e com possíveis parâmetros em <code>params</code> na lista
	 * de erros da exceção.
	 * @param message
	 */
	public void addMessageWithCodeAndComponent(String code, String component, String message, Object ... messageParams){
		addMessage(new Message(code, component, message, messageParams));
	}

	/**
	 * Retorna a lista de erros da exceção.
	 * @param message
	 */
	public List<Message> getErrors(){ 
		return errors;
	}
	
}
