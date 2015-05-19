package br.com.surittec.suricdi.scheduler.annotation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.enterprise.util.Nonbinding;
import javax.interceptor.InterceptorBinding;

@InterceptorBinding
@Inherited
@Target({ TYPE })
@Retention(RUNTIME)
@Documented
public @interface JobVetoable {

	@Nonbinding
	String[] runnableProjectStages() default {"Production","Staging"};

}
