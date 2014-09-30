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

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import br.com.surittec.suricdi.faces.util.FacesUtils;

/**
 * Converter de Enum
 * 
 * @author Lucas Lins
 *
 */
@FacesConverter("br.com.surittec.suricdi.faces.converter.EnumConverter")
public class EnumConverter implements Converter{
	
	private static final String ATTRIBUTE_ENUM_TYPE = "br.com.surittec.suricdi.faces.converter.EnumConverter.enumType";
	
	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if(value == null) return "";
		if(!value.getClass().isEnum()) throwException(component);
		component.getAttributes().put(ATTRIBUTE_ENUM_TYPE, value.getClass());
		return ((Enum<?>) value).name();
	}

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Object getAsObject(FacesContext context, UIComponent component,String value) {
		try{
			return Enum.valueOf((Class<Enum>)component.getAttributes().get(ATTRIBUTE_ENUM_TYPE), value);
		}catch (Exception e) {
			throwException(component);
			return null;
		}
	}

	
	private void throwException(UIComponent component) {
		String label = (String)component.getAttributes().get("label");
		if(label != null && !label.trim().equals("")){
			throw new ConverterException(FacesUtils.createMessage(FacesMessage.SEVERITY_ERROR, "javax.faces.converter.EnumConverter.ENUM", label));
		}else{
			throw new ConverterException(FacesUtils.createMessage(FacesMessage.SEVERITY_ERROR, "javax.faces.converter.EnumConverter.ENUM", component.getId()));
		}
	}

}
