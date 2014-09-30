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
package br.com.surittec.suricdi.faces.facelets.util.input;

import java.util.Locale;

import javax.faces.component.UIComponent;

import br.com.surittec.suricdi.faces.facelets.util.ComponentSupport;

/**
 * Classe suporte a utilizacao da tag de numero.
 * 
 * @author lucas lins
 *
 */
public final class InputNumberTag {
	
	public static String INETEGER_DIGITS_ATTR = "integerDigits";
	public static String FRACTION_DIGITS_ATTR = "fractionDigits";
	public static String CONVERTION_TYPE_ATTR = "convertionType";
	
	public static int DEFAULT_INTEGER_DIGITS = 12;
	public static int DEFAULT_FRACTION_DIGITS = 2;
	
	
	// PUBLIC METHODS *********************
	
	public static int getIntegerDigits(UIComponent component){
		if(component != null && component.getAttributes() != null){
			if(component.getAttributes().get(INETEGER_DIGITS_ATTR) != null){
				return new Integer(component.getAttributes().get(INETEGER_DIGITS_ATTR).toString());
			}
		}
		return DEFAULT_INTEGER_DIGITS;
	}
	
	public static int getIntegerDigits(Object integerDigits){
		try{
			return Integer.valueOf(integerDigits.toString());
		}catch (Exception e) {
			return DEFAULT_INTEGER_DIGITS;
		}
	}
	
	public static int getFractionDigits(UIComponent component){
		if(component != null && component.getAttributes() != null){
			if(component.getAttributes().get(FRACTION_DIGITS_ATTR) != null){
				return new Integer(component.getAttributes().get(FRACTION_DIGITS_ATTR).toString());
			}
		}
		return DEFAULT_FRACTION_DIGITS;
	}
	
	public static int getFractionDigits(Object fractionDigits){
		try{
			return Integer.valueOf(fractionDigits.toString());
		}catch (Exception e) {
			return DEFAULT_FRACTION_DIGITS;
		}
	}
	
	public static Locale getLocale(UIComponent component){
		return ComponentSupport.getLocale(component.getAttributes().get("locale"));
	}
	
	public static Class<?> getConvertionType(UIComponent component) throws ClassNotFoundException{
		String className = (String)component.getAttributes().get(CONVERTION_TYPE_ATTR); 
		if(className != null){
			return Class.forName(className);
		}
		return null;
	}
}
