package br.com.surittec.suricdi.example.domain.repository;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import br.com.surittec.suricdi.core.repository.EntityRepository;
import br.com.surittec.suricdi.core.repository.criteria.JPQL;
import br.com.surittec.suricdi.example.domain.entity.Pessoa;

public class PessoaRepository extends EntityRepository<Pessoa, Long>{

	/**
	 * Exemplo de utilizacao do JPQL 
	 */
	public List<Pessoa> findByNome(String nome){
		
		JPQL jpql = jpql().from("Pessoa");
		
		if(StringUtils.isNotBlank(nome)){
			jpql.where("nome = :nome").withParam("nome", nome);
		}
		
		return jpql.getResultList(Pessoa.class);
	}
	
	public boolean isUnique(Pessoa pessoa){
		
		JPQL jpql = jpql().select("count(p)").from("Pessoa p");

		jpql.where("(p.cpf = :cpf or p.email = :email)");
		jpql.withParam("cpf", pessoa.getCpf());
		jpql.withParam("email", pessoa.getEmail());
		
		if(pessoa.getId() != null){
			jpql.where("p.id != :id").withParam("id", pessoa.getId());
		}
		
		return jpql.getSingleResult(Long.class) == 0;
	}
	
}
