package br.com.surittec.suricdi.example.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.com.surittec.suricdi.core.exception.BusinessException;
import br.com.surittec.suricdi.core.service.EntityService;
import br.com.surittec.suricdi.example.domain.entity.Pessoa;
import br.com.surittec.suricdi.example.domain.repository.GenericEntityRepository;
import br.com.surittec.suricdi.example.domain.repository.PessoaRepository;
import br.com.surittec.suricdi.example.util.i18n.Messages;

@Stateless
public class PessoaService extends EntityService{
	
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
	protected br.com.surittec.suricdi.core.repository.GenericEntityRepository getGenericEntityRepository() {
		return genericEntityRepository;
	}
	
	/*
	 * Public Methods
	 */
	
	public List<Pessoa> findByNome(String nome){
		return pessoaRepository.findByNome(nome);
	}
	
	public void save(Pessoa pessoa){
		if(!pessoaRepository.isUnique(pessoa)){
			throw new BusinessException(messages.pessoaCadastroFalhaUnicidade());
		}
		super.save(pessoa);
	}
}
