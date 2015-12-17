/*
 * SURITTEC
 * Copyright 2015, TTUS TECNOLOGIA DA INFORMACAO LTDA, 
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
package br.com.surittec.suricdi.sqs.jms;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Stereotype;
import javax.jms.Session;

import br.com.surittec.suricdi.sqs.context.SQSContextControl;

@SQSContextControl
@Dependent
@Stereotype
@Documented
@Target({TYPE})
@Retention(RUNTIME)
public @interface SQSListener {

	String pooledConnectionFactoryName() default "";

	String queue();

	int concurrentConsumers() default 1;
	
	int acknowledgeMode() default Session.AUTO_ACKNOWLEDGE;
	
	/**
	 * Create queue if not exists
	 * @return
	 */
	boolean createQueue() default false;
	
	/**
	 * DelaySeconds attribute used for create queue.
	 * Default: 0;
	 * @return
	 */
	int delaySeconds() default 0;
	
	/**
	 * MaximumMessageSize attribute used for create queue.
	 * Default: 262144 (256 KiB)
	 * @return
	 */
	int maximumMessageSize() default 262144;
	
	/**
	 * MessageRetentionPeriod attribute used for create queue.
	 * Default: 345600 (4 days).
	 * @return
	 */
	int messageRetentionPeriod() default 345600;
	
	/**
	 * ReceiveMessageWaitTimeSeconds attribute used for create queue.
	 * Default: 0
	 * @return
	 */
	int receiveMessageWaitTimeSeconds() default 0;
	
	/**
	 * VisibilityTimeout attribute used for create queue.
	 * Default: 30
	 * @return
	 */
	int visibilityTimeout() default 30;
}
