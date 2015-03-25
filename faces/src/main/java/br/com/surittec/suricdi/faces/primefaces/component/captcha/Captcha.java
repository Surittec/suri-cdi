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
package br.com.surittec.suricdi.faces.primefaces.component.captcha;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.el.ValueExpression;
import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.application.ResourceDependencies;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.primefaces.util.MessageFactory;


/**
 * 
 * @author lucas lins
 *
 */
@ResourceDependencies({

})
public class Captcha extends UIInput {


	public static final String COMPONENT_TYPE = "org.primefaces.component.Captcha";
	public static final String COMPONENT_FAMILY = "org.primefaces.component";
	private static final String DEFAULT_RENDERER = "org.primefaces.component.CaptchaRenderer";
	private static final String OPTIMIZED_PACKAGE = "org.primefaces.component.";

	protected enum PropertyKeys {

		theme
		,language
		,tabindex
		,label
		,secure;

		String toString;

		PropertyKeys(String toString) {
			this.toString = toString;
		}

		PropertyKeys() {}

		public String toString() {
			return ((this.toString != null) ? this.toString : super.toString());
		}
	}

	public Captcha() {
		setRendererType(DEFAULT_RENDERER);
	}

	public String getFamily() {
		return COMPONENT_FAMILY;
	}

	public java.lang.String getTheme() {
		return (java.lang.String) getStateHelper().eval(PropertyKeys.theme, "red");
	}
	public void setTheme(java.lang.String _theme) {
		getStateHelper().put(PropertyKeys.theme, _theme);
		handleAttribute("theme", _theme);
	}

	public java.lang.String getLanguage() {
		return (java.lang.String) getStateHelper().eval(PropertyKeys.language, "en");
	}
	public void setLanguage(java.lang.String _language) {
		getStateHelper().put(PropertyKeys.language, _language);
		handleAttribute("language", _language);
	}

	public int getTabindex() {
		return (java.lang.Integer) getStateHelper().eval(PropertyKeys.tabindex, 0);
	}
	public void setTabindex(int _tabindex) {
		getStateHelper().put(PropertyKeys.tabindex, _tabindex);
		handleAttribute("tabindex", _tabindex);
	}

	public java.lang.String getLabel() {
		return (java.lang.String) getStateHelper().eval(PropertyKeys.label, null);
	}
	public void setLabel(java.lang.String _label) {
		getStateHelper().put(PropertyKeys.label, _label);
		handleAttribute("label", _label);
	}

	public boolean isSecure() {
		return (java.lang.Boolean) getStateHelper().eval(PropertyKeys.secure, false);
	}
	public void setSecure(boolean _secure) {
		getStateHelper().put(PropertyKeys.secure, _secure);
		handleAttribute("secure", _secure);
	}


	public final static String PUBLIC_KEY = "primefaces.PUBLIC_CAPTCHA_KEY";
	public final static String PRIVATE_KEY = "primefaces.PRIVATE_CAPTCHA_KEY";
	public final static String INVALID_MESSAGE_ID = "primefaces.captcha.INVALID";

	public final static String OLD_PRIVATE_KEY = "org.primefaces.component.captcha.PRIVATE_KEY";

	private static final Logger logger = Logger.getLogger(Captcha.class.getName());

	@Override
	protected void validateValue(FacesContext context, Object value) {
		super.validateValue(context, value);

		if(isValid()) {

			String result = null;
			Verification verification = (Verification) value;

			try {
				URL url = new URL("https://www.google.com/recaptcha/api/verify");
				URLConnection conn = url.openConnection();
				conn.setDoInput(true);
				conn.setDoOutput(true);
				conn.setUseCaches(false);
				conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
				String postBody = createPostParameters(context, verification);

				OutputStream out = conn.getOutputStream();
				out.write(postBody.getBytes());
				out.flush();
				out.close();

				BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				result = rd.readLine();
				rd.close();
			}catch(Exception exception) {
				throw new FacesException(exception);
			}

			boolean isValid = Boolean.valueOf(result);

			if(!isValid) {
				setValid(false);

				String validatorMessage = getValidatorMessage();
				FacesMessage msg = null;

				if(validatorMessage != null) {
					msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, validatorMessage, validatorMessage);
				}
				else {
					Object[] params = new Object[2];
					params[0] = MessageFactory.getLabel(context, this);
					params[1] = verification.getAnswer();

					msg = MessageFactory.getMessage(Captcha.INVALID_MESSAGE_ID, FacesMessage.SEVERITY_ERROR, params);
				}

				context.addMessage(getClientId(context), msg);
			}
		}
	}

	private String createPostParameters(FacesContext facesContext, Verification verification) throws UnsupportedEncodingException {
		String challenge = verification.getChallenge();
		String answer = verification.getAnswer();
		String remoteAddress = ((HttpServletRequest) facesContext.getExternalContext().getRequest()).getRemoteAddr();
		String privateKey = null;
		String oldPrivateKey = facesContext.getExternalContext().getInitParameter(Captcha.OLD_PRIVATE_KEY);
		String newPrivateKey = facesContext.getExternalContext().getInitParameter(Captcha.PRIVATE_KEY);

		//Backward compatibility
		if(oldPrivateKey != null) {
			logger.warning("PrivateKey definition on captcha is deprecated, use primefaces.PRIVATE_CAPTCHA_KEY context-param instead");

			privateKey = oldPrivateKey;
		}
		else {
			privateKey = newPrivateKey;
		}

		if(privateKey == null) {
			throw new FacesException("Cannot find private key for catpcha, use primefaces.PRIVATE_CAPTCHA_KEY context-param to define one");
		}

		StringBuilder postParams = new StringBuilder();
		postParams.append("privatekey=").append(URLEncoder.encode(privateKey, "UTF-8"));
		postParams.append("&remoteip=").append(URLEncoder.encode(remoteAddress, "UTF-8"));
		postParams.append("&challenge=").append(URLEncoder.encode(challenge, "UTF-8"));
		postParams.append("&response=").append(URLEncoder.encode(answer, "UTF-8"));

		return postParams.toString();
	}

	protected FacesContext getFacesContext() {
		return FacesContext.getCurrentInstance();
	}

	public void handleAttribute(String name, Object value) {
		@SuppressWarnings("unchecked")
        List<String> setAttributes = (List<String>) this.getAttributes().get("javax.faces.component.UIComponentBase.attributesThatAreSet");
		if(setAttributes == null) {
			String cname = this.getClass().getName();
			if(cname != null && cname.startsWith(OPTIMIZED_PACKAGE)) {
				setAttributes = new ArrayList<String>(6);
				this.getAttributes().put("javax.faces.component.UIComponentBase.attributesThatAreSet", setAttributes);
			}
		}
		if(setAttributes != null) {
			if(value == null) {
				ValueExpression ve = getValueExpression(name);
				if(ve == null) {
					setAttributes.remove(name);
				} else if(!setAttributes.contains(name)) {
					setAttributes.add(name);
				}
			}
		}
	}

}
