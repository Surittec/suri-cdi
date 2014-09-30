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
package br.com.surittec.suricdi.faces.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import br.com.surittec.suricdi.faces.util.FacesUtils;
import br.com.surittec.util.data.EmailUtil;

/**
 * Validador de E-mail
 * 
 * @author Lucas Lins
 *
 */
@FacesValidator("br.com.surittec.suricdi.faces.validator.EmailValidator")
public class EmailValidator implements Validator{

	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		if(!EmailUtil.isValid(value, true)){
			String label = (String)component.getAttributes().get("label");
			if(label != null && !label.trim().equals("")){
				throw new ValidatorException(FacesUtils.createMessage(FacesMessage.SEVERITY_ERROR, "javax.faces.validator.Email.detail", label));
			}else{
				throw new ValidatorException(FacesUtils.createMessage(FacesMessage.SEVERITY_ERROR, "javax.faces.validator.Email"));
			}
		}
	}
	
}
