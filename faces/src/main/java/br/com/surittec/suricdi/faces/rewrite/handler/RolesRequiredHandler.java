/*
 * SURITTEC
 * Copyright 2015, SURITTEC CONSULTORIA LTDA, 
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
package br.com.surittec.suricdi.faces.rewrite.handler;

import org.ocpsoft.rewrite.annotation.Join;
import org.ocpsoft.rewrite.annotation.api.ClassContext;
import org.ocpsoft.rewrite.annotation.api.HandlerChain;
import org.ocpsoft.rewrite.annotation.handler.HandlerWeights;
import org.ocpsoft.rewrite.annotation.handler.JoinHandler;
import org.ocpsoft.rewrite.annotation.spi.AnnotationHandler;
import org.ocpsoft.rewrite.servlet.config.SendStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.surittec.suricdi.faces.rewrite.annotation.RolesRequired;
import br.com.surittec.suricdi.faces.rewrite.condition.JAASRoles;

/**
 * Handler para insersao de regra de seguranca
 * 
 * @author Lucas Lins
 *
 */
public class RolesRequiredHandler implements AnnotationHandler<RolesRequired> {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Override
	public Class<RolesRequired> handles() {
		return RolesRequired.class;
	}

	/**
	 * Needs high priority than {@link JoinHandler}
	 */
	@Override
	public int priority() {
		return HandlerWeights.WEIGHT_TYPE_STRUCTURAL - 1;
	}

	@Override
	public void process(ClassContext context, RolesRequired annotation, HandlerChain chain) {
		
		Join join = context.getJavaClass().getAnnotation(Join.class);
		
		if(join != null){
			context.setBaseRule(org.ocpsoft.rewrite.servlet.config.rule.Join.path(join.path()).to(join.to()).withChaining());
			context.getRuleBuilder().withPriority(annotation.priority());
			context.getRuleBuilder().when(JAASRoles.hasntRoles(annotation.value()));
			context.getRuleBuilder().perform(SendStatus.error(403));
		}else{
			logger.warn(String.format("SECURITY VULNERABILITY: The class %s must have org.ocpsoft.rewrite.annotation.Join annotation"));
		}
		
		chain.proceed();
	}
}
