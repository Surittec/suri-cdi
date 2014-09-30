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

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import br.com.surittec.suricdi.faces.util.FacesUtils;

/**
 * Converter de data
 * 
 * @author Lucas Lins
 *
 */
@FacesConverter("br.com.surittec.suricdi.faces.converter.DateConverter")
public class DateConverter implements Converter{

	private static final String PATTERN_ATTR = "DateConverter.PATTERN_ATTR";
	
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if(value == null || value.trim().length() < 1) return null;
		
		try{
			
			String pattern = (String) component.getAttributes().get(PATTERN_ATTR);
			if(value.length() != pattern.length()){
				throw new Exception();
			}
			
			SimpleDateFormat df = new SimpleDateFormat(pattern);
			df.setLenient(false);
			
			return df.parse(value);
			
		}catch (Exception e) {
			String label = (String)component.getAttributes().get("label");
			if(label != null && !label.trim().equals("")){
				throw new ConverterException(FacesUtils.createMessage(FacesMessage.SEVERITY_ERROR, "javax.faces.validator.Date.detail", label));
			}else{
				throw new ConverterException(FacesUtils.createMessage(FacesMessage.SEVERITY_ERROR, "javax.faces.validator.Date"));
			}
		}
	}

	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if(value == null) return null;
		return new SimpleDateFormat((String) component.getAttributes().get(PATTERN_ATTR)).format((Date) value);
	}
	
	
}
