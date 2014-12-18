package br.com.surittec.suricdi.core.validation.util;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.enterprise.context.Dependent;
import javax.validation.ConstraintViolation;
import javax.validation.Path;
import javax.validation.Path.Node;
import javax.validation.Validation;
import javax.validation.Validator;

import org.hibernate.validator.HibernateValidator;
import org.hibernate.validator.HibernateValidatorConfiguration;
import org.hibernate.validator.internal.engine.MethodParameterNodeImpl;
import org.hibernate.validator.messageinterpolation.ResourceBundleMessageInterpolator;
import org.hibernate.validator.method.MethodConstraintViolation;
import org.hibernate.validator.method.MethodValidator;
import org.hibernate.validator.resourceloading.AggregateResourceBundleLocator;

import br.com.surittec.suricdi.core.exception.BusinessException;
import br.com.surittec.suricdi.core.util.Constants;

/**
 * Classe utilitaria para validacao com Bean Validation.
 * 
 * @author Lucas Lins
 *
 */
@Dependent
public class ValidationUtil implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	
	/*
	 * Bean Validation
	 */
	
	public <T> void validate(T param){
		validate(param, null, new Class<?>[]{}, null, new String[]{});
	}
	
	public <T> void validate(T param, String paramName){
		validate(param, paramName, new Class<?>[]{}, null, new String[]{});
	}
	
	public <T> void validate(T param, Class<?>[] groups){
		validate(param, null, groups, null, new String[]{});
	}
	
	public <T> void validate(T param, String[] bundleNames){
		validate(param, null, new Class<?>[]{}, null, bundleNames);
	}
	
	public <T> void validate(T param, String paramName, Class<?>[] groups){
		validate(param, null, groups, null, new String[]{});
	}
	
	public <T> void validate(T param, String paramName, String validationErrorCode){
		validate(param, paramName, new Class<?>[]{}, validationErrorCode, new String[]{});
	}
	
	public <T> void validate(T param, String paramName, String[] bundleNames){
		validate(param, paramName, new Class<?>[]{}, null, bundleNames);
	}
	
	public <T> void validate(T param, String paramName, Class<?>[] groups, String[] bundleNames){
		validate(param, paramName, groups, null, bundleNames);
	}
	
	public <T> void validate(T param, String paramName, String validationErrorCode, String[] bundleNames){
		validate(param, paramName, new Class<?>[]{}, validationErrorCode, bundleNames);
	}
	
	public <T> void validate(T param, String paramName, Class<?>[] groups, String validationErrorCode, String[] bundleNames){
		if(param == null) return;
		
		Set<ConstraintViolation<T>> constraintViolations = getValidator(bundleNames).validate(param, groups);
		if(!constraintViolations.isEmpty()){
			
			BusinessException be = new BusinessException();
			
			for(ConstraintViolation<T> cv : constraintViolations){
				be.addMessageWithCodeAndComponent(
						validationErrorCode,
						getPropertyPath(paramName, cv.getPropertyPath()), 
						cv.getMessage());
			}
			
			throw be;
		}
	}
	
	/*
	 * Method validation
	 */
	
	public <T> void validateMethod(T object, Method method, String[] parameterNames, Object[] parameterValues){
		validateMethod(object, method, parameterNames, parameterValues, new Class<?>[]{} , null, new String[]{});
	}
	
	public <T> void validateMethod(T object, Method method, String[] parameterNames, Object[] parameterValues, String validationErrorCode){
		validateMethod(object, method, parameterNames, parameterValues, new Class<?>[]{} , validationErrorCode, new String[]{});
	}
	
	public <T> void validateMethod(T object, Method method, String[] parameterNames, Object[] parameterValues, Class<?>[] groups){
		validateMethod(object, method, parameterNames, parameterValues, groups, null, new String[]{});
	}
	
	public <T> void validateMethod(T object, Method method, String[] parameterNames, Object[] parameterValues, String[] bundleNames){
		validateMethod(object, method, parameterNames, parameterValues, new Class<?>[]{} , null, bundleNames);
	}
	
	public <T> void validateMethod(T object, Method method, String[] parameterNames, Object[] parameterValues, Class<?>[] groups, String validationErrorCode){
		validateMethod(object, method, parameterNames, parameterValues, groups, validationErrorCode, new String[]{});
	}
	
	public <T> void validateMethod(T object, Method method, String[] parameterNames, Object[] parameterValues, Class<?>[] groups, String[] bundleNames){
		validateMethod(object, method, parameterNames, parameterValues, groups, null, bundleNames);
	}
	
	public <T> void validateMethod(T object, Method method, String[] parameterNames, Object[] parameterValues, String validationErrorCode, String[] bundlesName){
		validateMethod(object, method, parameterNames, parameterValues, new Class<?>[]{} , validationErrorCode, bundlesName);
	}
	
	public <T> void validateMethod(T object, Method method, String[] parameterNames, Object[] parameterValues, Class<?>[] groups, String validationErrorCode, String[] bundlesName){
		MethodValidator mv = getValidator(bundlesName).unwrap(MethodValidator.class);
		
		Set<MethodConstraintViolation<T>> constraintViolations = mv.validateAllParameters(object, method, parameterValues, groups);
		
		if(!constraintViolations.isEmpty()){
			BusinessException be = new BusinessException();
			
			for(MethodConstraintViolation<T> cv : constraintViolations){
				be.addMessageWithCodeAndComponent(
						validationErrorCode,
						getPropertyPath(parameterNames[cv.getParameterIndex()], cv.getPropertyPath()), 
						cv.getMessage());
			}
			
			throw be;
		}
	}
	
	/*
	 * Protected Methods
	 */
	
	@SuppressWarnings("deprecation")
	protected Validator getValidator(String[] bundleName){
		
		List<String> resourceBundles = new ArrayList<String>(Arrays.asList(bundleName));
		resourceBundles.add(Constants.SURITTEC_CORE_BUNDLE_BASENAME);
		
		HibernateValidatorConfiguration configuration = Validation.byProvider(HibernateValidator.class).configure();
		configuration.messageInterpolator(
				new ResourceBundleMessageInterpolator(
						new AggregateResourceBundleLocator(
							resourceBundles,
							configuration.getDefaultResourceBundleLocator()
						)
				)
		);
		
		return configuration.buildValidatorFactory().getValidator();
	}
	
	protected String getPropertyPath(String paramName, Path path){
		StringBuilder pp = new StringBuilder(paramName != null ? paramName : "");
		Iterator<Node> it = path.iterator();
		while(it.hasNext()){
			Node node = it.next();
			if(!(node instanceof MethodParameterNodeImpl)){
				pp.append(".");
				pp.append(node.getName());
			}
		}
		return pp.toString();
	}
	
}
