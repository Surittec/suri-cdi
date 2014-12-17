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
package br.com.surittec.suricdi.core.util;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Utilitario de mensagens
 * 
 * @author Lucas Lins
 *
 */
public abstract class MessageUtil {

	/**
	 * Retorna a mensagem de acordo com o bundle informado, ou de acordo
	 * com o bundle do core, caso o bundle informado nao possua a mensagem.
	 * 
	 * @param bundleName
	 * @param locale
	 * @param key
	 * @param params
	 * @return mensagem formatada
	 */
	public static String getMessageFromBundle(String bundleName, Locale locale, String key, Object... params) {
		List<String> bundlesName = new ArrayList<String>();
		bundlesName.add(bundleName);
		bundlesName.add(Constants.SURITTEC_CORE_BUNDLE_BASENAME);
		
		return getMessageFromBundle(bundlesName, locale, key, params);
	}
	
	/**
	 * Retorna a mensagem de acordo com a lista de bundles informados.
	 * 
	 * @param bundlesName
	 * @param locale
	 * @param key
	 * @param params
	 * @return
	 */
	public static String getMessageFromBundle(List<String> bundlesName, Locale locale, String key, Object ... params){
    	for(String bundleName : bundlesName){
    		ResourceBundle bundle = ResourceBundle.getBundle(bundleName, locale);
    		if(bundle.containsKey(key)) return formatMessage(bundle.getString(key), params);
    	}
    	return formatMessage(key, params);
    }
	
	/**
	 * Formata a mensagem de acordo com seus parametros
	 * 
	 * @param msg
	 * @param params
	 * @return
	 */
	public static String formatMessage(String msg, Object... params) {
		if (params == null || params.length == 0) return msg;
		return new MessageFormat(msg).format(params);
	}

}
