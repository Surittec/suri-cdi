package br.com.surittec.suricdi.faces.validator;

import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.component.EditableValueHolder;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import br.com.surittec.suricdi.faces.util.FacesUtils;

@FacesValidator("br.com.surittec.suricdi.faces.validator.MatchValidator")
public class MatchValidator implements Validator{

	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		if(value == null) return;
		
		if(component.getAttributes().get("validateEmailBefore") != null){
			EmailValidator validator = new EmailValidator();
			validator.validate(context, component, value);
		}
		
		String with = (String) component.getAttributes().get("with");
		EditableValueHolder matchWith = (EditableValueHolder)component.findComponent(with);
		if(matchWith == null){
			 throw new FacesException("Cannot find component " + with + " in view.");
		}
		
		if(!value.equals(matchWith.getSubmittedValue())){
			String message = (String)component.getAttributes().get("message");
			if(message != null && !message.trim().equals("")){
				throw new ValidatorException(FacesUtils.createMessage(FacesMessage.SEVERITY_ERROR, message));
			}
			
			String label = (String)component.getAttributes().get("label");
			if(label != null && !label.trim().equals("")){
				throw new ValidatorException(FacesUtils.createMessage(FacesMessage.SEVERITY_ERROR, "javax.faces.validator.Match.detail", label));
			}else{
				throw new ValidatorException(FacesUtils.createMessage(FacesMessage.SEVERITY_ERROR, "javax.faces.validator.Match"));
			}
		}
	}
	
}
