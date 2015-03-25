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
package br.com.surittec.suricdi.faces.converter;

import java.util.HashMap;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 * Conversor generico de objetos
 * 
 * @author Lucas Lins
 *
 */
@SuppressWarnings("unchecked")
@FacesConverter("br.com.surittec.suricdi.faces.converter.SimpleObjectConverter")
public class SimpleObjectConverter implements Converter{
	
	private static final String OBJECT_VALUES_ATTR = "br.com.surittec.suricdi.faces.converter.SimpleObjectConverter.OBJECT_VALUES_ATTR";
	
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		if(value == null || value.trim().length() == 0) return null;
		
		Map<Integer, Object> objectValues = (Map<Integer, Object>) component.getAttributes().get(OBJECT_VALUES_ATTR);
		if(objectValues == null) return null;
		
		return objectValues.get(Integer.valueOf(value));
	}
	
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		if(value == null) return "";
		
		Map<Integer, Object> objectValues = (Map<Integer, Object>) component.getAttributes().get(OBJECT_VALUES_ATTR);
		if(objectValues == null){
			objectValues = new HashMap<Integer, Object>();
			component.getAttributes().put(OBJECT_VALUES_ATTR, objectValues);
		}
		objectValues.put(value.hashCode(), value);
		
		return String.valueOf(value.hashCode());
	}

}
