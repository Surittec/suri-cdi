package br.com.surittec.suricdi.core.validation.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import javax.enterprise.context.Dependent;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.hibernate.validator.HibernateValidator;
import org.hibernate.validator.HibernateValidatorConfiguration;
import org.hibernate.validator.messageinterpolation.ResourceBundleMessageInterpolator;
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
	 * Public Methods
	 */
	
	public <T> void validate(T param, String ... bundleName){
		validateWithNameAndCode(param, "", null, bundleName);
	}
	
	public <T> void validateWithName(T param, String paramName, String ... bundleName){
		validateWithNameAndCode(param, paramName, null, bundleName);
	}
	
	public <T> void validateWithCode(T param, String validationErrorCode, String ... bundleName){
		validateWithNameAndCode(param, null, validationErrorCode, bundleName);
	}
	
	public <T> void validateWithNameAndCode(T param, String paramName, String validationErrorCode, String ... bundleName){
		if(param == null) return;
		
		paramName = paramName != null ? paramName + "." : "";
		
		Set<ConstraintViolation<T>> constraintViolations = getValidator(bundleName).validate(param);
		if(!constraintViolations.isEmpty()){
			
			BusinessException be = new BusinessException();
			
			for(ConstraintViolation<T> cv : constraintViolations){
				be.addMessageWithCodeAndComponent(
						validationErrorCode,
						String.format("%s%s", paramName, cv.getPropertyPath()), 
						cv.getMessage());
			}
			
			throw be;
		}
	}
	
	/*
	 * Protected Methods
	 */
	
	@SuppressWarnings("deprecation")
	protected Validator getValidator(String ... bundleName){
		
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
	
}
