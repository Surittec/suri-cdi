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
package br.com.surittec.suricdi.sqs.extension;

import java.util.HashSet;
import java.util.Set;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.AfterDeploymentValidation;
import javax.enterprise.inject.spi.BeforeBeanDiscovery;
import javax.enterprise.inject.spi.BeforeShutdown;
import javax.enterprise.inject.spi.Extension;
import javax.enterprise.inject.spi.ProcessAnnotatedType;
import javax.jms.MessageListener;

import org.apache.deltaspike.core.spi.activation.Deactivatable;
import org.apache.deltaspike.core.util.ClassDeactivationUtils;

import br.com.surittec.suricdi.sqs.jms.SQSListener;
import br.com.surittec.suricdi.sqs.jms.SQSListenerHolder;

public class SQSListenerExtension implements Extension, Deactivatable{

	private boolean isActivated = true;

	private Set<SQSListenerHolder> listeners;
	
	/*
	 * Protected Methods
	 */
	
	protected void init(@Observes BeforeBeanDiscovery beforeBeanDiscovery){
		isActivated = ClassDeactivationUtils.isActivated(getClass());
		if(!isActivated) return;
		listeners = new HashSet<SQSListenerHolder>();
    }
	
	protected <T> void discoverListeners(@Observes ProcessAnnotatedType<MessageListener> pat) {
		if(!isActivated) return;
		
		if(pat.getAnnotatedType().isAnnotationPresent(SQSListener.class)){
			Class<? extends MessageListener> messageListenerClass = pat.getAnnotatedType().getJavaClass();
			SQSListener sqsMessageDriven = pat.getAnnotatedType().getAnnotation(SQSListener.class);
			for(int i = 0; i < sqsMessageDriven.concurrentConsumers(); i++){
				listeners.add(new SQSListenerHolder(sqsMessageDriven,  messageListenerClass));
			}
		}
	}
	
	protected void startListeners(@Observes AfterDeploymentValidation event) {
		if(!isActivated) return;
		
		for(SQSListenerHolder listener : listeners){
			listener.start();
		}
	}
	
	protected void destroyListeners(@Observes BeforeShutdown event) {
		if(!isActivated) return;
		
		for(SQSListenerHolder listener: listeners){
			listener.destroy();
		}
	}
	
}
