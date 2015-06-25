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
package br.com.surittec.suricdi.faces.interceptor.impl;

import java.io.Serializable;

import javax.ejb.EJBException;
import javax.faces.application.FacesMessage;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import br.com.surittec.suricdi.faces.interceptor.BusinessMessages;
import br.com.surittec.surifaces.util.FacesUtils;
import br.com.surittec.util.exception.BusinessException;
import br.com.surittec.util.exception.ExceptionUtil;
import br.com.surittec.util.message.Message;

/**
 * Interceptor que captura excecoes de negocio e adiciona as mensagens no
 * FacesMessages
 * 
 * @author Lucas Lins
 *
 */
@Interceptor
@BusinessMessages
public class BusinessExceptionInterceptor implements Serializable {

	private static final long serialVersionUID = 1L;

	@AroundInvoke
	public Object appendMessages(InvocationContext ctx) throws Exception {
		try {
			return ctx.proceed();
		} catch (BusinessException be) {
			return catchBusinessException(be);
		} catch (EJBException e) {
			Throwable t = ExceptionUtil.getRootCause(e);
			if (t instanceof BusinessException) {
				return catchBusinessException((BusinessException) t);
			} else {
				throw e;
			}
		}
	}

	private Object catchBusinessException(BusinessException be) {
		for (Message error : be.getErrors()) {
			if (error.getComponent() != null && FacesUtils.getContext().getViewRoot().findComponent(error.getComponent()) != null) {
				FacesUtils.addMsgToComponent(error.getComponent(), FacesMessage.SEVERITY_ERROR, error.getMessage(), error.getMessageParams());
			} else {
				FacesUtils.addMsg(FacesMessage.SEVERITY_ERROR, error.getMessage(), error.getMessageParams());
			}
		}
		return null;
	}

}
