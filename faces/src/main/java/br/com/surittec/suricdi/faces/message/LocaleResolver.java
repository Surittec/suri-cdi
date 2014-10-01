package br.com.surittec.suricdi.faces.message;

import java.util.Locale;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Specializes;

import org.apache.deltaspike.core.impl.message.DefaultLocaleResolver;

import br.com.surittec.suricdi.faces.util.FacesUtils;

@Specializes
@ApplicationScoped
public class LocaleResolver extends DefaultLocaleResolver{

	private static final long serialVersionUID = 1L;

	@Override
	public Locale getLocale() {
		return FacesUtils.getContext() != null ? FacesUtils.getLocale() : super.getLocale();
	}
	
}
