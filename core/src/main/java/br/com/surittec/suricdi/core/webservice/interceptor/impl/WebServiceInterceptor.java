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
package br.com.surittec.suricdi.core.webservice.interceptor.impl;

import java.io.Serializable;
import java.lang.annotation.Annotation;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.jws.WebParam;
import javax.xml.bind.annotation.XmlElement;

import org.slf4j.Logger;

import br.com.surittec.suricdi.core.exception.BusinessException;
import br.com.surittec.suricdi.core.validation.util.ValidationUtil;
import br.com.surittec.suricdi.core.webservice.exception.ServiceExceptionConverter;
import br.com.surittec.util.exception.ExceptionUtil;

/**
 * Interceptor que permite utilizar o bean validation em web services
 * e converter business exception em uma exception do web service.
 * 
 * @author Lucas Lins
 *
 */
@Interceptor
@br.com.surittec.suricdi.core.webservice.interceptor.WebServiceInterceptor
public class WebServiceInterceptor implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private Logger logger;
	
	@Inject
	private ValidationUtil validationUtil;
	
	@Inject
	private ServiceExceptionConverter exceptionConverter;
	
	@AroundInvoke
	public Object intercept(InvocationContext ctx) throws Exception {
		try{
			
			validationUtil.validateMethod(
					ctx.getTarget(), 
					ctx.getMethod(), 
					getParameterNames(ctx), 
					ctx.getParameters(), 
					exceptionConverter.getValidationErrorCode());
			
			return ctx.proceed();
			
		}catch (Exception e) {
			
			Throwable rootCause = ExceptionUtil.getRootCause(e);
			
			if(rootCause instanceof BusinessException){
				throw exceptionConverter.convert((BusinessException)rootCause);
			}else{
				logger.error("Service Error", rootCause);
				throw exceptionConverter.convert(rootCause);
			}
		}
	}
	
	/*
	 * Private Methods
	 */
	
	private String[] getParameterNames(InvocationContext ctx){
		String[] parameterNames = new String[ctx.getParameters().length];
		Annotation[][] paramsAnnotations = ctx.getMethod().getParameterAnnotations();
		for(int i = 0; i < paramsAnnotations.length; i++){
			parameterNames[i] = getParameterName(i, paramsAnnotations[i]);
		}
		return parameterNames;
	}
	
	private String getParameterName(int paramIndex, Annotation[] paramAnnotations){
		for(Annotation a : paramAnnotations){
			if(a instanceof XmlElement){
				return ((XmlElement) a).name();
			}
			
			if(a instanceof WebParam){
				return ((WebParam) a).name();
			}
		}
		
		return String.format("arg%d", paramIndex);
	}
	
}
