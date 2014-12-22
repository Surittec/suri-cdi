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

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Classe utilitaria para manipulacao de excecoes.
 * 
 * @author lucas lins
 *
 */
public abstract class ExceptionUtil {

	/**
	 * Obtem a real causa do da excecao informada.
	 * 
	 * @param exception
	 * @return
	 */
	public static Throwable getRootCause(Throwable exception){
		while(exception.getCause() != null) exception = exception.getCause();
		return exception;
	}
	
	/**
	 * Obtem a root stack trace em formato texto 
	 * 
	 * @param exception
	 * @return
	 */
	public static String getRootStackTrace(Throwable exception){
		return getStackTrace(getRootCause(exception));
	}
	
	/**
	 * Obtem a stack trace em formato texto
	 * 
	 * @param exception
	 * @return
	 */
	public static String getStackTrace(Throwable exception){
		StringWriter sw = new StringWriter();
		exception.printStackTrace(new PrintWriter(sw));
		return sw.toString();
	}
}
