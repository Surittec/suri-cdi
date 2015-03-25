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
package br.com.surittec.suricdi.faces.rewrite.condition;

import java.util.Arrays;
import java.util.Collection;

import org.ocpsoft.rewrite.context.EvaluationContext;
import org.ocpsoft.rewrite.servlet.config.HttpCondition;
import org.ocpsoft.rewrite.servlet.http.event.HttpServletRewrite;

/**
 * Condicao para validacao se o usuario nao possui as regras informadas
 * 
 * @author Lucas Lins
 *
 */
public class JAASRoles extends HttpCondition {

	private final Collection<String> roles;

	/**
	 * Create a new {@link JAASRoles} condition specifying the roles of which
	 * the current user must be a member for evaluation to return
	 * <code>true</code>.
	 */
	public static JAASRoles hasntRoles(String... roles) {
		return new JAASRoles(roles);
	}

	private JAASRoles(String[] roles) {
		this.roles = Arrays.asList(roles);
	}

	@Override
	public boolean evaluateHttp(HttpServletRewrite event, EvaluationContext context) {
		HttpServletRewrite rewrite = event;

		boolean hasAllRoles = true;
		
		// check if user has all required roles
		for (String role : roles) {
			if (!rewrite.getRequest().isUserInRole(role)) {
				hasAllRoles = false;
			}
		}
		
		return !hasAllRoles;
	}

}
