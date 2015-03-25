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
package br.com.surittec.suricdi.faces.facelets.util;

import java.util.Locale;

import javax.faces.context.FacesContext;

/**
 * Classe utilitaria para utilizacao dos compoenetes.
 * 
 * @author lucas lins
 *
 */
public final class ComponentSupport {
	
	/**
	 * Retorna o locale de acordo com o parametro informado ou o locale 
	 * utilizado pela aplicacao, caso o parametro informado seja nulo ou vazio.
	 * 
	 * @param localeObj
	 * @return locale
	 */
	public static Locale getLocale(Object localeObj){
		
		String locale = String.valueOf(localeObj);
		
		if(locale.equals("null") || locale.trim().length() < 1){
			return FacesContext.getCurrentInstance().getViewRoot().getLocale();
		}
		
		if (locale.length() == 2) {
            return new Locale(locale);
        }
        if (locale.length() == 5) {
            return new Locale(locale.substring(0, 2), locale.substring(3, 5).toUpperCase());
        }
        if (locale.length() >= 7) {
            return new Locale(locale.substring(0, 2), locale.substring(3, 5).toUpperCase(), locale.substring(6, locale.length()));
        }
		
        throw new RuntimeException("INVALID LOCALE: " + locale);
	}
	
}
