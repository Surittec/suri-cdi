package br.com.surittec.suricdi.sqs.context;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.interceptor.InterceptorBinding;

@Documented
@Inherited
@InterceptorBinding
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface SQSContextControl {}
