package br.com.surittec.suricdi.core.validation.interceptor.impl;

import java.io.Serializable;
import java.lang.annotation.Annotation;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.jws.WebParam;
import javax.xml.bind.annotation.XmlElement;

import br.com.surittec.suricdi.core.validation.interceptor.Validate;
import br.com.surittec.suricdi.core.validation.util.Param;
import br.com.surittec.suricdi.core.webservice.exception.ServiceExceptionConverter;
import br.com.surittec.surivalidation.exception.ValidationException;
import br.com.surittec.surivalidation.util.ValidationUtil;
import br.com.surittec.util.exception.BusinessException;

/**
 * Interceptor que permite utilizar o bean validation em qualquer metodo.
 * 
 * @author Lucas Lins
 *
 */
@Interceptor
@Validate
public class ValidationInterceptor implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private ServiceExceptionConverter exceptionConverter;
	
	@AroundInvoke
	public Object intercept(InvocationContext ctx) throws Exception {
		try {

			ValidationUtil.validateMethod(
					ctx.getTarget(),
					ctx.getMethod(),
					getParameterNames(ctx),
					ctx.getParameters(),
					getGroups(ctx),
					exceptionConverter.getValidationErrorCode());

			return ctx.proceed();

		} catch (ValidationException ve) {
			BusinessException be = new BusinessException();
			be.getErrors().addAll(ve.getErrors());
			throw be;
		}
	}

	/*
	 * Private Methods
	 */

	private String[] getParameterNames(InvocationContext ctx) {
		String[] parameterNames = new String[ctx.getParameters().length];
		Annotation[][] paramsAnnotations = ctx.getMethod().getParameterAnnotations();
		for (int i = 0; i < paramsAnnotations.length; i++) {
			parameterNames[i] = getParameterName(i, paramsAnnotations[i]);
		}
		return parameterNames;
	}

	private String getParameterName(int paramIndex, Annotation[] paramAnnotations) {
		for (Annotation a : paramAnnotations) {
			if (a instanceof Param) 	 return ((Param)a).name();
			if (a instanceof XmlElement) return ((XmlElement) a).name();
			if (a instanceof WebParam) 	 return ((WebParam) a).name();
		}
		return String.format("arg%d", paramIndex);
	}
	
	private Class<?>[] getGroups(InvocationContext ctx){
		Validate validate = ctx.getMethod().getAnnotation(Validate.class);
		return validate != null ? validate.groups() : new Class<?>[]{};
	}
}
