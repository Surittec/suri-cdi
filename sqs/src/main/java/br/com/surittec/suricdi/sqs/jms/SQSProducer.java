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
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.RequestScoped;
import javax.jms.BytesMessage;
import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.TextMessage;

import br.com.surittec.suricdi.sqs.pool.PooledConnectionFactory;

@RequestScoped
public abstract class SQSProducer implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Connection connection;
	
	private Session session;
	
	private MessageProducer messageProducer;
	
	private Map<String, Destination> queues;
	
	/*
	 * Lifecycle Methods
	 */
	
	@PostConstruct
	void postConstruct(){
		try{
			connection = getConnectionFactory().createConnection();
			session = connection.createSession(false, getAcknowledgeMode());
			queues = new HashMap<String, Destination>();
			messageProducer = session.createProducer(getDefaultQueue());
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}
	
	@PreDestroy
	void preDestroy(){
		if(connection != null) try{connection.close();}catch(Exception e){}
		connection = null;
		session = null;
		messageProducer = null;
		queues = null;
	}
	
	protected abstract PooledConnectionFactory getConnectionFactory();
	
	protected int getAcknowledgeMode(){
		return Session.AUTO_ACKNOWLEDGE;
	}
	
	/*
	 * Queue Methods
	 */
	
	protected Destination getDefaultQueue(){
		return getQueue(null);
	}
	
	private Destination getQueue(String queueName){
		if(queueName == null) return null;
		
		Destination queue = queues.get(queueName);
		if(queue != null) return queue;
		
		try{
			queue = session.createQueue(queueName);
			queues.put(queueName, queue);
			return queue;
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}
	
	/*
	 * Send Methods
	 */
	
	protected void send(Message message) throws JMSException {
		messageProducer.send(message);
	}
	
	protected void send(String queueName, Message message) throws JMSException {
		messageProducer.send(getQueue(queueName), message);
	}
	
	protected void send(Message message, int deliveryMode, int priority, long timeToLive) throws JMSException{
		messageProducer.send(message, deliveryMode, priority, timeToLive);
	}
	
	protected void send(String queueName, Message message, int deliveryMode, int priority, long timeToLive) throws JMSException {
		messageProducer.send(getQueue(queueName), message, deliveryMode, priority, timeToLive);
	}
	
	/*
	 * Message Creation Methods
	 */
	
	/**
     * Creates a <code>BytesMessage</code>.
     * 
     * @return new <code>BytesMessage</code>
     * @throws JMSException
     *             If session is closed or internal error
     */
    public BytesMessage createBytesMessage() throws JMSException {
        return session.createBytesMessage();
    }
    
    /**
     * Creates a <code>ObjectMessage</code>.
     * 
     * @return new <code>ObjectMessage</code>
     * @throws JMSException
     *             If session is closed or internal error
     */
    public ObjectMessage createObjectMessage() throws JMSException {
        return session.createObjectMessage();
    }
    
    /**
     * Creates an initialized <code>ObjectMessage</code>.
     * 
     * @param object
     *            The initialized <code>ObjectMessage</code>
     * @return new <code>ObjectMessage</code>
     * @throws JMSException
     *             If session is closed or internal error
     */
    public ObjectMessage createObjectMessage(Serializable object) throws JMSException {
        return session.createObjectMessage(object);
    }
    
    /**
     * Creates a <code>TextMessage</code>.
     * 
     * @return new <code>TextMessage</code>
     * @throws JMSException
     *             If session is closed or internal error
     */
    public TextMessage createTextMessage() throws JMSException {
        return session.createTextMessage();
    }
    
    /**
     * Creates an initialized <code>TextMessage</code>.
     * 
     * @param text
     *            The initialized <code>TextMessage</code>
     * @return new <code>TextMessage</code>
     * @throws JMSException
     *             If session is closed or internal error
     */
    public TextMessage createTextMessage(String text) throws JMSException {
        return session.createTextMessage(text);
    }
}
