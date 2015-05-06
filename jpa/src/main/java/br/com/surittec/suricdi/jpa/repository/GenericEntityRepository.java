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
package br.com.surittec.suricdi.jpa.repository;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.surittec.surijpa.repository.GenericEntityRepositorySupport;

/**
 * Suporte para classes de persist�ncia, com encapsulamento do uso do
 * {@link javax.persistence.EntityManager} e provendo algumas opera��es
 * necess�rias manter ou pesquisar entidades.
 */
public abstract class GenericEntityRepository extends GenericEntityRepositorySupport {

	@Inject
	protected EntityManager entityManager;

	@Override
	protected EntityManager getEntityManager() {
		return entityManager;
	}

}
