package br.com.surittec.suricdi.faces.interceptor.util;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;

import br.com.surittec.surifaces.util.FacesUtils;
import br.com.surittec.util.exception.BusinessException;

@RequestScoped
public abstract class BusinessExceptionRedirectResolver {
	
	public void handleNavigation(BusinessException be){
		String redirect = resolveRedirect(be);
		if(redirect != null){
			FacesContext context = FacesUtils.getContext();
			context.getApplication().getNavigationHandler().handleNavigation(context, null, redirect);
		}
	}
	
	protected abstract String resolveRedirect(BusinessException be);
	
}
