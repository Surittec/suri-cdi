package br.com.surittec.suricdi.example.controller.pessoa;

import javax.inject.Inject;

import org.ocpsoft.rewrite.annotation.Join;
import org.ocpsoft.rewrite.annotation.Parameter;
import org.ocpsoft.rewrite.faces.annotation.Deferred;
import org.ocpsoft.rewrite.faces.navigate.Navigate;

import br.com.surittec.suricdi.example.domain.entity.Pessoa;
import br.com.surittec.suricdi.example.service.PessoaService;
import br.com.surittec.suricdi.example.util.i18n.Messages;
import br.com.surittec.suricdi.faces.controller.Controller;
import br.com.surittec.suricdi.faces.controller.stereotype.ViewController;

@ViewController
@Join(path = "/cadastro/pessoa/{idPessoa}", to = "/view/cadastro/pessoa/manter.xhtml")
public class ManterPessoaController extends Controller{

	private static final long serialVersionUID = 1L;

	@Inject
	private PessoaService pessoaService;
	
	@Inject
	private Messages messages;
	
	@Parameter
	@Deferred
	private String idPessoa;
	
	private Pessoa pessoa;
	
	/*
	 * Init
	 */
	
	@Override
	public String requestAction() {
		try{
			if("nova".equals(idPessoa)){
				pessoa = new Pessoa();
				return null;
			}
			
			Long id = Long.parseLong(idPessoa);
			pessoa = pessoaService.findBy(Pessoa.class, id);
			if(pessoa == null) throw new NumberFormatException();
			return null;
		}catch(NumberFormatException nfe){
			addMsgErro(messages.globalRegistroNaoEncontrado());
			return Navigate.to(ListarPessoasController.class).withoutRedirect().build();
		}
	}

	/*
	 * Public Methods
	 */
	
	public Navigate salvar(){
		boolean nova = pessoa.getId() == null;
		pessoaService.save(pessoa);
		addMsg(nova ? messages.globalDadosIncluidosSucesso() : messages.globalDadosAlteradosSucesso());
		return Navigate.to(ListarPessoasController.class);
	}
	
	public Navigate excluir(){
		pessoaService.remove(pessoa);
		addMsg(messages.globalRegistroRemovidoSucesso());
		return Navigate.to(ListarPessoasController.class);
	}
	
	/*
	 * Gets & Sets
	 */
	
	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public String getIdPessoa() {
		return idPessoa;
	}

	public void setIdPessoa(String idPessoa) {
		this.idPessoa = idPessoa;
	}
	
}
