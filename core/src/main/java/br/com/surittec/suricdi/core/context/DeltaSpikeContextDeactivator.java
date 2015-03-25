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
package br.com.surittec.suricdi.core.context;

import org.apache.deltaspike.core.impl.scope.DeltaSpikeContextExtension;
import org.apache.deltaspike.core.spi.activation.ClassDeactivator;
import org.apache.deltaspike.core.spi.activation.Deactivatable;

/**
 * Desabilita os escopos do detla spike.
 * JSF 2.1 não possui uma integração harmônica com o deltaspike [WFK2-674].
 *
 */
public class DeltaSpikeContextDeactivator implements ClassDeactivator{
	
	private static final long serialVersionUID = 1L;
	
	@Override
	public Boolean isActivated(Class<? extends Deactivatable> targetClass) {
		if(targetClass.equals(DeltaSpikeContextExtension.class)){
			return Boolean.FALSE;
		}
		return null;
	}
	
}
