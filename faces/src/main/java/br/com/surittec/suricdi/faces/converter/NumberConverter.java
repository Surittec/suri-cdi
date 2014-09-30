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
package br.com.surittec.suricdi.faces.converter;

import java.text.NumberFormat;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import br.com.surittec.suricdi.faces.facelets.util.input.InputNumberTag;
import br.com.surittec.suricdi.faces.util.FacesUtils;

/**
 * Conversor de numeros
 * 
 * @author Lucas Lins
 *
 */
@FacesConverter("br.com.surittec.suricdi.faces.converter.NumberConverter")
public class NumberConverter implements Converter{
	
	// PUBLIC METHODS ******************************
	
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		
		if(value == null || value.trim().length() < 1){
			return null;
		}
		
		try {
			NumberFormat nf = getNumberFormat(component);
			
			Class<?> convertionClass = InputNumberTag.getConvertionType(component);
			if(convertionClass != null){
				return convertionClass.getConstructor(String.class).newInstance(String.valueOf(nf.parse(value)));
			}
			return nf.parse(value);
		} catch (Exception e) {
			throw new ConverterException(FacesUtils.createMessage(FacesMessage.SEVERITY_ERROR, "javax.faces.component.UIInput.CONVERSION"));
		}
	}
	
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if(value == null){
			return null;
		}
		return getNumberFormat(component).format(value);
	}
	
	// PRIVATE METHODS ***************************
	
	private NumberFormat getNumberFormat(UIComponent component){
		
		NumberFormat nf = NumberFormat.getNumberInstance(InputNumberTag.getLocale(component));
		
		int integerDigits = InputNumberTag.getIntegerDigits(component);
		int fractionDigits = InputNumberTag.getFractionDigits(component);
		
		nf.setMaximumIntegerDigits(integerDigits);
		nf.setMinimumFractionDigits(fractionDigits);
		nf.setMaximumFractionDigits(fractionDigits);
		
		return nf;
	}
}
