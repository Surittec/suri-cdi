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
package br.com.surittec.suricdi.example.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.surittec.suricdi.example.domain.entity.Pessoa;
import br.com.surittec.suricdi.example.domain.repository.GenericEntityRepository;
import br.com.surittec.suricdi.example.domain.repository.PessoaRepository;
import br.com.surittec.suricdi.example.util.i18n.Messages;
import br.com.surittec.suricdi.jpa.service.EntityService;
import br.com.surittec.util.validation.Assert;

@Stateless
public class PessoaService extends EntityService {

	@Inject
	private GenericEntityRepository genericEntityRepository;

	@Inject
	private PessoaRepository pessoaRepository;

	@Inject
	private Messages messages;

	/*
	 * Protected Methods
	 */

	@Override
	protected GenericEntityRepository getGenericEntityRepository() {
		return genericEntityRepository;
	}

	/*
	 * Public Methods
	 */

	public List<Pessoa> findByNome(String nome) {
		return pessoaRepository.findByNome(nome);
	}

	public void save(Pessoa pessoa) {
		Assert.isTrue(pessoaRepository.isUnique(pessoa), messages.pessoaCadastroFalhaUnicidade());

		super.save(pessoa);
	}
}
