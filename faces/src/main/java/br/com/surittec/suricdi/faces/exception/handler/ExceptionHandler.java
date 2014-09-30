/*
 * SURITTEC
 * Copyright 2014, SURITTEC CONSULTORIA LTDA, 
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
package br.com.surittec.suricdi.faces.exception.handler;

import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.el.ELException;
import javax.faces.FacesException;
import javax.faces.application.ViewExpiredException;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.FacesContext;
import javax.faces.event.ExceptionQueuedEvent;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.persistence.OptimisticLockException;
import javax.persistence.RollbackException;

import org.ocpsoft.rewrite.exception.RewriteException;

import br.com.surittec.suricdi.faces.util.FacesUtils;

import com.sun.faces.context.FacesFileNotFoundException;

/**
 * Exception Handler
 * 
 * @author Lucas Lins
 *
 */
public class ExceptionHandler extends ExceptionHandlerWrapper {
	
	private final javax.faces.context.ExceptionHandler wrapped;
	
	private static final Logger logger = Logger.getLogger(ExceptionHandler.class.getName());

	private boolean catched;
	
	public ExceptionHandler(final javax.faces.context.ExceptionHandler wrapped) {
		this.wrapped = wrapped;
	}
	
	@Override
	public javax.faces.context.ExceptionHandler getWrapped() {
		return this.wrapped;
	}
	
	@Override
	public void handle() throws FacesException {
		catched = false;
		
		for (final Iterator<ExceptionQueuedEvent> it = getUnhandledExceptionQueuedEvents().iterator(); it.hasNext();) {
			Throwable t = it.next().getContext().getException();
			while ((t instanceof FacesException || t instanceof ELException || t instanceof RollbackException) && t.getCause() != null) {
				t = t.getCause();
			}
			
			if(handleException(it, t, FacesFileNotFoundException.class, "com.sun.faces.context.FacesFileNotFoundException.message")) continue;
			if(handleException(it, t, RewriteException.class, "com.ocpsoft.pretty.PrettyException.message")) continue;
			if(handleException(it, t, EntityNotFoundException.class, "javax.persistence.EntityNotFoundException.message")) continue;
			if(handleException(it, t, EntityExistsException.class, "javax.persistence.EntityExistsException.message")) continue;
			if(handleException(it, t, OptimisticLockException.class, "javax.persistence.OptimisticLockException.message")) continue;
			if(handleException(it, t, ViewExpiredException.class, "javax.faces.application.ViewExpiredException.message")) continue;
			if(handleException(it, t, Exception.class, "java.lang.Exception.message")) continue;
			
		}
		getWrapped().handle();
	}
	
	protected boolean handleException(Iterator<ExceptionQueuedEvent> it, Throwable t, Class<? extends Throwable> type, String message){
		if(type.isAssignableFrom(t.getClass())){
			try{
				if(!catched){
					FacesContext facesContext = FacesUtils.getContext();
					
					FacesUtils.addMsgErro(message);
					
					facesContext.getApplication().getNavigationHandler().handleNavigation(facesContext, null, "pretty:error");
					facesContext.renderResponse();
					
					return true;
				}
			}finally{
				catched = true;
				logger.log(Level.SEVERE, t.getLocalizedMessage(), t);
				it.remove();
			}
		}
		return false;
	}
}
