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
package br.com.surittec.suricdi.faces.primefaces.component.captcha;

import java.io.IOException;
import java.util.Map;

import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.primefaces.renderkit.CoreRenderer;

/**
 * 
 * @author Lucas Lins
 *
 */
public class CaptchaRenderer extends CoreRenderer{

	 private final static String CHALLENGE_FIELD = "recaptcha_challenge_field";
	 private final static String RESPONSE_FIELD = "recaptcha_response_field";

	 @Override
	 public void decode(FacesContext context, UIComponent component) {
		 Captcha captcha = (Captcha) component;
		 Map<String,String> params = context.getExternalContext().getRequestParameterMap();

		 String challenge = params.get(CHALLENGE_FIELD);
		 String answer = params.get(RESPONSE_FIELD);

		 if(answer != null) {
			 if(answer.equals(""))
				 captcha.setSubmittedValue(answer);
			 else
				 captcha.setSubmittedValue(new Verification(challenge, answer));
		 } else {
			 captcha.setSubmittedValue("");
		 }
	 }

	 @Override
	 public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
		 ResponseWriter writer = context.getResponseWriter();
		 Captcha captcha = (Captcha) component;
		 captcha.setRequired(true);

		 String publicKey = getPublicKey(context, captcha);

		 if(publicKey == null) {
			 throw new FacesException("Cannot find public key for catpcha, use primefaces.PUBLIC_CAPTCHA_KEY context-param to define one");
		 }

		 writer.startElement("script", null);
		 writer.writeAttribute("type", "text/javascript", null);

		 writer.write("var RecaptchaOptions = {");
		 writer.write("theme:\"" + captcha.getTheme() + "\"");
		 writer.write(",lang:\"" + captcha.getLanguage() + "\"");
		 if(captcha.getTabindex() != 0) {
			 writer.write(",tabIndex:" + captcha.getTabindex());
		 }
		 writer.write("};");
		 writer.endElement("script");

		 writer.startElement("script", null);
		 writer.writeAttribute("type", "text/javascript", null);
		 writer.writeAttribute("src", "https://www.google.com/recaptcha/api/challenge?k=" + publicKey, null);
		 writer.endElement("script");

		 writer.startElement("noscript", null);
		 writer.startElement("iframe", null);
		 writer.writeAttribute("src", "https://www.google.com/recaptcha/api/noscript?k=" + publicKey, null);
		 writer.endElement("iframe");

		 writer.startElement("textarea", null);
		 writer.writeAttribute("id", CHALLENGE_FIELD, null);
		 writer.writeAttribute("name", CHALLENGE_FIELD, null);
		 writer.writeAttribute("rows", "3", null);
		 writer.writeAttribute("columns", "40", null);
		 writer.endElement("textarea");

		 writer.startElement("input", null);
		 writer.writeAttribute("id", RESPONSE_FIELD, null);
		 writer.writeAttribute("name", RESPONSE_FIELD, null);
		 writer.writeAttribute("type", "hidden", null);
		 writer.writeAttribute("value", "manual_challenge", null);
		 writer.endElement("input");

		 writer.endElement("noscript");
	 }

	 protected String getPublicKey(FacesContext context, Captcha captcha) {
		 return context.getExternalContext().getInitParameter(Captcha.PUBLIC_KEY);
	 }

}
