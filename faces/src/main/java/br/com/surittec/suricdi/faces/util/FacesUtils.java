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
package br.com.surittec.suricdi.faces.util;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Classe utilitaria para o JSF
 * 
 * @author Lucas Lins
 *
 */
public abstract class FacesUtils {

	/**
     * Retorna a referencia do FacesContext.
     * @return FacesContext
     */
    public static FacesContext getContext(){
        return FacesContext.getCurrentInstance();
    }
	
    /**
     * Retorna a referencia do ExternalContext.
     * @return ExternalContext
     */
    public static ExternalContext getExternalContext(){
    	return getContext().getExternalContext();
    }
    
    /**
     * Retorna a referencia para o HttpServletResponse atual.
     * @return HttpServletResponse
     */
    public static HttpServletResponse getResponse(){
        return (HttpServletResponse)getExternalContext().getResponse();
    }

    /**
     * Retorna a referencia para o HttpServletRequest atual.
     * @return HttpServletRequest
     */
    public static HttpServletRequest getRequest(){
        return (HttpServletRequest)getExternalContext().getRequest();
    }
    
    /**
     * Retorna a referencia para o Map de request.
     * @return Map<String, Object>
     */
    public static Map<String, Object> getRequestMap(){
        return getExternalContext().getRequestMap();
    }
    
    /**
     * Retorna a instancia atual do contexto ServletContext.
     * @return ServletContext
     */
    public static ServletContext getServletContext(){
        return (ServletContext)getExternalContext().getContext();
    }
    
    /**
     * Retorna um parametro do Request HTTP (HttpServletRequest) sob a chave 'name'.
     * @param name - chave do parametro no Request HTTP
     * @return String - parametro do Request HTTP sob a chave 'name'
     */
    public static String getParameter(String name){
        return getRequest().getParameter(name);
    }

    /**
     * Retorna a referencia do Map de parametros da Request
     * @return Map<String, String>
     */
    public static Map<String, String> getRequestParameterMap(){
        return getExternalContext().getRequestParameterMap();
    }
    
    /**
     * Retorna a referencia para o HttpSession atual.
     * @return HttpSession
     */
    public static HttpSession getSession(){
        return getRequest().getSession();
    }
    
    /**
     * Retorna a referencia do Map da HttpSession.
     * @return Map<String, Object>
     */
    public static Map<String, Object> getSessionMap(){
        return getExternalContext().getSessionMap();
    }
    
    /**
     * Retorna o contexto de Flash
     * @return Flash
     */
    public static Flash getFlash(){
    	return getExternalContext().getFlash();
    }
	
	/**
     * Adiciona uma mensagem no facesMessages com severidade info.
     * @param msg
     * @param params
     */
    public static void addMsg(String msg, Object ... params) {
		addMsg(FacesMessage.SEVERITY_INFO, msg, params);
	}

	/**
     * Adiciona uma mensagem no facesMessages com severidade INFO, para o componente 
     * especificado
     * @param compoenentId
     * @param msg
     * @param params
     */
    public static void addMsgToComponent(String componenteId, String msg, Object... params) {
		addMsgToComponent(componenteId, FacesMessage.SEVERITY_INFO, msg, params);
	}
	
	/**
     * Adiciona uma mensagem no facesMessages com severidade warn.
     * @param msg
     * @param params
     */
    public static void addMsgWarn(String msg, Object... params) {
		addMsg(FacesMessage.SEVERITY_WARN, msg, params);
	}

	/**
     * Adiciona uma mensagem no facesMessages com severidade WARN, para o componente 
     * especificado.
     * @param compoenentId
     * @param msg
     * @param params
     */
    public static void addMsgWarnToComponent(String componenteId, String msg, Object... params) {
		addMsgToComponent(componenteId, FacesMessage.SEVERITY_WARN, msg, params);
	}
	
	/**
     * Adiciona uma mensagem no facesMessages com severidade error.
     * @param msg
     * @param params
     */
    public static void addMsgErro(String msg, Object... params) {
		addMsg(FacesMessage.SEVERITY_ERROR, msg, params);
	}

	/**
     * Adiciona uma mensagem do bundle no facesMessages com severidade error, para o componente 
     * especificado.
     * @param compoenentId
     * @param msg
     * @param params
     */
    public static void addMsgErroToComponent(String componenteId, String msg, Object... params) {
		addMsgToComponent(componenteId, FacesMessage.SEVERITY_ERROR, msg, params);
	}
	
    /**
     * Adiciona uma mensagem do bundle no facesMessages
     * @param severity
     * @param msg
     * @param params
     */
	public static void addMsg(Severity severity, String msg, Object... params){
		addMsgToComponent(null, severity, msg, params);
	}
	
	/**
	 * Adiciona uma mensagem do bundle no facesMessages, para o componente especificado
	 * @param severity
	 * @param componentId
	 * @param msg
	 * @param params
	 */
	public static void addMsgToComponent(String componentId, Severity severity, String msg, Object... params){
		FacesContext.getCurrentInstance().addMessage(componentId, createMessage(severity, msg, params));
	}
	
	/**
	 * Cria uma mensagem com os parametros informados
	 * @param severity
	 * @param msg
	 * @param params
	 * @return
	 */
	public static FacesMessage createMessage(Severity severity, String msg, Object... params){
		return new FacesMessage(severity, getMessageFromDefaultBundle(msg, params), null);
	}
	
	/**
	 * Retorna a mensagem do arquivo properties default ("messages") formatada com os parametros passados.
	 * 
	 * @param key
	 * @param params
	 * @return mensagem formatada
	 */
    public static String getMessageFromDefaultBundle(String key, Object ... params){
        return getMessageFromBundle(null, key, params);
	}
	
	/**
	 * Retorna a mensagem do arquivo properties, que possui o nome passado como argumento, formatada com os parametros passados.
	 * 
	 * @param bundleName
	 * @param key
	 * @param params
	 * @return mensagem formatada
	 */
    public static String getMessageFromBundle(String bundleName, String key, Object ... params){
    	FacesContext context = FacesContext.getCurrentInstance();
    	Locale locale = context.getViewRoot() != null ? context.getViewRoot().getLocale() : context.getExternalContext().getRequestLocale();
    	
		if(bundleName == null) bundleName = context.getApplication().getMessageBundle();
		
		return getMessageFromBundle(bundleName, locale, key, params);
	}
    
    
    /**
	 * Retorna a mensagem do bundle informado e de acordo com os parametros informados.
	 * 
	 * @param bundleName
	 * @param locale
	 * @param key
	 * @param params
	 * @return mensagem formatada
	 */
    public static String getMessageFromBundle(String bundleName, Locale locale, String key, Object ... params){
		ResourceBundle bundle = ResourceBundle.getBundle(bundleName, locale);
		
		if(bundle.containsKey(key)) return formatMsg(bundle.getString(key), params);
		
		bundle = ResourceBundle.getBundle(Constants.SURITTEC_FACES_BUNDLE_BASENAME, locale);
		if(bundle.containsKey(key)) return formatMsg(bundle.getString(key), params);
		
		bundle = ResourceBundle.getBundle(Constants.SURITTEC_CORE_BUNDLE_BASENAME, locale);
		if(bundle.containsKey(key)) return formatMsg(bundle.getString(key), params);
		
		bundle = ResourceBundle.getBundle(Constants.PRIMEFACES_BUNDLE_BASENAME, locale);
		if(bundle.containsKey(key)) return formatMsg(bundle.getString(key), params);
		
		bundle = ResourceBundle.getBundle(Constants.DEFAULT_BUNDLE_BASENAME, locale);
		if(bundle.containsKey(key)) return formatMsg(bundle.getString(key), params);
		
        return formatMsg(key, params);
	}
    
    private static String formatMsg(String msg, Object ... params){
    	if(params == null || params.length == 0) return msg;
		MessageFormat form = new MessageFormat(msg);
        return form.format(params);
    }
	
}
