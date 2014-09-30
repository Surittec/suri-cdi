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
package br.com.surittec.suricdi.faces.primefaces.component.select;

import java.io.IOException;

import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.convert.Converter;
import javax.faces.model.SelectItem;

import org.primefaces.component.selectonemenu.SelectOneMenu;

/**
 * 
 * @author Lucas Lins
 *
 */
public class SelectOneMenuRenderer extends org.primefaces.component.selectonemenu.SelectOneMenuRenderer{

	@Override
	protected void encodeOption(FacesContext context, SelectOneMenu menu, SelectItem option, Object values, Object submittedValues, Converter converter) throws IOException {
		 ResponseWriter writer = context.getResponseWriter();
		 String itemValueAsString = getOptionAsString(context, menu, converter, option.getValue());
		 boolean disabled = option.isDisabled();

		 Object valuesArray;
		 Object itemValue;
		 if(submittedValues != null) {
			 valuesArray = submittedValues;
			 itemValue = itemValueAsString;
		 } else {
			 valuesArray = values;
			 itemValue = option.getValue();
		 }

		 boolean selected = isSelected(context, menu, itemValue, valuesArray, converter);

		 writer.startElement("option", null);
		 writer.writeAttribute("value", itemValueAsString, null);
		 if(disabled) writer.writeAttribute("disabled", "disabled", null);
		 if(selected) writer.writeAttribute("selected", "selected", null);

		 if(option.isEscape())
			 writer.writeText(option.getLabel(), "value");
		 else
			 writer.write(option.getLabel());

		 writer.endElement("option");
	 }
	
}
