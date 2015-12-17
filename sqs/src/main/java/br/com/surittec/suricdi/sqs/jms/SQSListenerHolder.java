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

import org.apache.activemq.jms.pool.PooledConnection;
import org.apache.deltaspike.core.api.provider.BeanProvider;

import br.com.surittec.suricdi.sqs.pool.PooledConnectionFactory;

import com.amazon.sqs.javamessaging.SQSConnection;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.CreateQueueRequest;
import com.amazonaws.services.sqs.model.QueueDoesNotExistException;

public class SQSListenerHolder implements Serializable {

	private static final long serialVersionUID = 1L;

	private SQSListener sqsListener;
	
	private Class<? extends MessageListener> messageListenerClass;
	
	private Connection connection;
	
	private Session session;
	
	private MessageConsumer messageConsumer;
	
	private String queue;
	
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
			
			queue = getQueueName();
			checkQueue();
			
			messageConsumer = session.createConsumer(session.createQueue(queue));
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
	
	private String getQueueName(){
		return BeanProvider.getDependent(SQSQueueNameResolver.class).get().resolve(sqsListener, messageListenerClass);
	}
	
	private void checkQueue() throws JMSException{
		if(sqsListener.createQueue()){
			AmazonSQS amazonSQS = ((SQSConnection)((PooledConnection)connection).getConnection()).getAmazonSQSClient();
			try{
				amazonSQS.getQueueUrl(queue);
			}catch(QueueDoesNotExistException e){
				CreateQueueRequest createQueueRequest = new CreateQueueRequest(queue);
				createQueueRequest.addAttributesEntry("DelaySeconds", String.valueOf(sqsListener.delaySeconds()));
				createQueueRequest.addAttributesEntry("MaximumMessageSize", String.valueOf(sqsListener.maximumMessageSize()));
				createQueueRequest.addAttributesEntry("MessageRetentionPeriod", String.valueOf(sqsListener.messageRetentionPeriod()));
				createQueueRequest.addAttributesEntry("ReceiveMessageWaitTimeSeconds", String.valueOf(sqsListener.receiveMessageWaitTimeSeconds()));
				createQueueRequest.addAttributesEntry("VisibilityTimeout", String.valueOf(sqsListener.visibilityTimeout()));
				amazonSQS.createQueue(createQueueRequest);
			}
		}
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

	public String getQueue() {
		return queue;
	}
	
}
