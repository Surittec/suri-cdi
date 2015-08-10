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

import java.io.Serializable;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;

import org.apache.deltaspike.core.api.provider.BeanProvider;

import br.com.surittec.suricdi.sqs.pool.PooledConnectionFactory;

public class SQSListenerHolder implements Serializable {

	private static final long serialVersionUID = 1L;

	private SQSListener sqsListener;
	
	private Class<? extends MessageListener> messageListenerClass;
	
	private Connection connection;
	
	private Session session;
	
	private MessageConsumer messageConsumer;
	
	private MessageListener messageListener;

	/*
	 * Init
	 */
	
	public SQSListenerHolder(SQSListener sqsListener, Class<? extends MessageListener> messageListenerClass){
		this.sqsListener = sqsListener;
		this.messageListenerClass = messageListenerClass;
	}
	
	public void start(){
		try{
			connection = createConnection();
			session = connection.createSession(false, sqsListener.acknowledgeMode());
			messageListener = BeanProvider.getDependent(messageListenerClass).get();
			messageConsumer = session.createConsumer(session.createQueue(sqsListener.queue()));
			messageConsumer.setMessageListener(messageListener);
			connection.start();
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}
	
	public void destroy(){
		if(connection != null){
			try{connection.close();}catch(Exception e){}
		}
	}
	
	/*
	 * Private Methods
	 */
	
	private Connection createConnection() throws JMSException {
		PooledConnectionFactory connectionFactory = null;
		if(sqsListener.pooledConnectionFactoryName().isEmpty()){
			connectionFactory = BeanProvider.getContextualReference(PooledConnectionFactory.class);
		}else{
			connectionFactory = ((PooledConnectionFactory) BeanProvider.getContextualReference(sqsListener.pooledConnectionFactoryName()));
		}
		return connectionFactory.createConnection();
	}
	
	/*
	 * Getters
	 */
	
	public Connection getConnection() {
		return connection;
	}

	public Session getSession() {
		return session;
	}

	public MessageConsumer getMessageConsumer() {
		return messageConsumer;
	}

	public MessageListener getMessageListener() {
		return messageListener;
	}

	public SQSListener getSqsMessageDriven() {
		return sqsListener;
	}

	public Class<? extends MessageListener> getMessageListenerClass() {
		return messageListenerClass;
	}
	
}
