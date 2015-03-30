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
package br.com.surittec.suricdi.core.validation;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang.StringUtils;

import br.com.surittec.suricdi.core.validation.constraint.MinDate;

public class MinDateValidator implements ConstraintValidator<MinDate, Date> {

	private boolean nullable;
	private Date minDate;
	private SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

	@Override
	public boolean isValid(Date value, ConstraintValidatorContext context) {
		if (nullable && value == null)
			return true;
		if (minDate == null || !minDate.after(value))
			return true;
		return false;
	}

	@Override
	public void initialize(MinDate constraintAnnotation) {
		nullable = constraintAnnotation.nullable();
		try {
			if (StringUtils.isNotBlank(constraintAnnotation.minDate()))
				minDate = formatter.parse(constraintAnnotation.minDate());
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
}
