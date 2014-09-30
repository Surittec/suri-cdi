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
package br.com.surittec.suricdi.faces.facelets.function;

import java.text.DecimalFormatSymbols;

import br.com.surittec.suricdi.faces.facelets.util.ComponentSupport;
import br.com.surittec.suricdi.faces.facelets.util.input.InputNumberTag;
import br.com.surittec.suricdi.faces.util.FacesUtils;


/**
 * Classe utilitaria com funcoes a serem utilizadas nas paginas.
 * 
 * @author lucas lins
 *
 */
public class FaceletsFunctions {
	
	public static java.lang.Object getDefaultValue(java.lang.Object valueToTest, java.lang.Object defaultValue){
		Object retorno = (valueToTest == null) ? defaultValue : valueToTest;
	    return retorno;
	}
	
	public static java.lang.String getInputNumberMask(java.lang.String locale, java.lang.Object integerDigits, java.lang.Object fractionDigits){
		DecimalFormatSymbols symbols = new DecimalFormatSymbols(ComponentSupport.getLocale(locale));
		char groupingSeparator = symbols.getGroupingSeparator();
		groupingSeparator = (groupingSeparator != ',' && groupingSeparator != '.') ? ' ' : groupingSeparator;
		
		StringBuilder mask = new StringBuilder();
		
		for(int i = 0; i < InputNumberTag.getFractionDigits(fractionDigits); i++) mask.append("9");
		if(mask.length() > 0) mask.append(symbols.getDecimalSeparator());
		for(int i = 0; i < InputNumberTag.getIntegerDigits(integerDigits); i++){
			
			if(i != 0 && i % 3 == 0){
				mask.append(groupingSeparator);
			}
			mask.append("9");
		}
		
		return mask.toString();
	}
	
	public static java.lang.String getInputNumberValidValue(java.lang.Object fractionDigits){
		StringBuilder mask = new StringBuilder();
		for(int i = 0; i < InputNumberTag.getFractionDigits(fractionDigits) + 1; i++) mask.append("0");
		return mask.toString();
	}
	
	public static boolean hasGlobalMessages(){
		return !FacesUtils.getContext().getMessageList(null).isEmpty();
	}
}