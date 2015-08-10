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

import javax.jms.BytesMessage;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueBrowser;
import javax.jms.QueueReceiver;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.StreamMessage;
import javax.jms.TemporaryQueue;
import javax.jms.TemporaryTopic;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicSubscriber;


public class SQSSession implements Session, QueueSession {

	protected com.amazon.sqs.javamessaging.SQSSession delegate;
	
	public SQSSession(com.amazon.sqs.javamessaging.SQSSession session){
		this.delegate = session;
	}
	
	@Override
	public QueueReceiver createReceiver(Queue queue) throws JMSException {
		return delegate.createReceiver(queue);
	}

	@Override
	public QueueReceiver createReceiver(Queue queue, String messageSelector) throws JMSException {
		return delegate.createReceiver(queue, messageSelector);
	}

	@Override
	public QueueSender createSender(Queue queue) throws JMSException {
		return delegate.createSender(queue);
	}

	@Override
	public BytesMessage createBytesMessage() throws JMSException {
		return delegate.createBytesMessage();
	}

	@Override
	public MapMessage createMapMessage() throws JMSException {
		return delegate.createMapMessage();
	}

	@Override
	public Message createMessage() throws JMSException {
		return delegate.createMessage();
	}

	@Override
	public ObjectMessage createObjectMessage() throws JMSException {
		return delegate.createObjectMessage();
	}

	@Override
	public ObjectMessage createObjectMessage(Serializable object) throws JMSException {
		return delegate.createObjectMessage(object);
	}

	@Override
	public StreamMessage createStreamMessage() throws JMSException {
		return delegate.createStreamMessage();
	}

	@Override
	public TextMessage createTextMessage() throws JMSException {
		return delegate.createTextMessage();
	}

	@Override
	public TextMessage createTextMessage(String text) throws JMSException {
		return delegate.createTextMessage(text);
	}

	@Override
	public boolean getTransacted() throws JMSException {
		return delegate.getTransacted();
	}

	@Override
	public int getAcknowledgeMode() throws JMSException {
		return delegate.getAcknowledgeMode();
	}

	@Override
	public void commit() throws JMSException {
		delegate.commit();
	}

	@Override
	public void rollback() throws JMSException {
		delegate.rollback();
	}

	@Override
	public void close() throws JMSException {
		delegate.close();
	}

	@Override
	public void recover() throws JMSException {
		delegate.recover();
	}

	@Override
	public MessageListener getMessageListener() throws JMSException {
		return delegate.getMessageListener();
	}

	@Override
	public void setMessageListener(MessageListener listener) throws JMSException {
		// JUST IGNORE...
	}

	@Override
	public void run() {
		delegate.run();
	}

	@Override
	public MessageProducer createProducer(Destination destination) throws JMSException {
		return delegate.createProducer(destination);
	}

	@Override
	public MessageConsumer createConsumer(Destination destination) throws JMSException {
		return delegate.createConsumer(destination);
	}

	@Override
	public MessageConsumer createConsumer(Destination destination, String messageSelector) throws JMSException {
		return delegate.createConsumer(destination, messageSelector);
	}

	@Override
	public MessageConsumer createConsumer(Destination destination, String messageSelector, boolean NoLocal) throws JMSException {
		return delegate.createConsumer(destination, messageSelector, NoLocal);
	}

	@Override
	public Queue createQueue(String queueName) throws JMSException {
		return delegate.createQueue(queueName);
	}

	@Override
	public Topic createTopic(String topicName) throws JMSException {
		return delegate.createTopic(topicName);
	}

	@Override
	public TopicSubscriber createDurableSubscriber(Topic topic, String name) throws JMSException {
		return delegate.createDurableSubscriber(topic, name);
	}

	@Override
	public TopicSubscriber createDurableSubscriber(Topic topic, String name, String messageSelector, boolean noLocal) throws JMSException {
		return delegate.createDurableSubscriber(topic, name, messageSelector, noLocal);
	}

	@Override
	public QueueBrowser createBrowser(Queue queue) throws JMSException {
		return delegate.createBrowser(queue);
	}

	@Override
	public QueueBrowser createBrowser(Queue queue, String messageSelector) throws JMSException {
		return delegate.createBrowser(queue, messageSelector);
	}

	@Override
	public TemporaryQueue createTemporaryQueue() throws JMSException {
		return delegate.createTemporaryQueue();
	}

	@Override
	public TemporaryTopic createTemporaryTopic() throws JMSException {
		return delegate.createTemporaryTopic();
	}

	@Override
	public void unsubscribe(String name) throws JMSException {
		delegate.unsubscribe(name);
	}

}
