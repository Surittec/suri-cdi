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
package br.com.surittec.suricdi.core.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import br.com.surittec.suricdi.core.validation.constraint.Cpf;
import br.com.surittec.util.data.CpfUtil;

/**
 * 
 * @author Lucas Lins
 * 
 */
public class CpfValidator implements ConstraintValidator<Cpf, String>{

	private boolean nullable;
	
	public void initialize(Cpf config) {
		this.nullable = config.nullable();
	}

	public boolean isValid(String cpf, ConstraintValidatorContext constraintContext) {
		return CpfUtil.isValid(cpf, nullable);
	}
}
