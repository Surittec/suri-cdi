package br.com.surittec.suricdi.example.controller.pessoa;

import java.util.List;

import javax.inject.Inject;

import org.ocpsoft.rewrite.annotation.Join;

import br.com.surittec.suricdi.example.domain.entity.Pessoa;
import br.com.surittec.suricdi.example.service.PessoaService;
import br.com.surittec.suricdi.faces.controller.Controller;
import br.com.surittec.suricdi.faces.controller.stereotype.ViewController;

@ViewController
@Join(path="/cadastro/pessoa", to="/view/cadastro/pessoa/listar.xhtml")
public class ListarPessoasController extends Controller{

	private static final long serialVersionUID = 1L;

	@Inject
	private PessoaService pessoaService;
	
	private String nome;
	
	private List<Pessoa> pessoas;
	
	/*
	 * Init
	 */
	
	@Override
	public String requestAction() {
		pessoas = pessoaService.findAll(Pessoa.class);
		return null;
	}
	
	/*
	 * Public Methods
	 */
	
	public void pesquisar(){
		pessoas = pessoaService.findByNome(nome);
	}

	/*
	 * Gets & Sets
	 */
	
	public List<Pessoa> getPessoas() {
		return pessoas;
	}

	public void setPessoas(List<Pessoa> pessoas) {
		this.pessoas = pessoas;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
}
