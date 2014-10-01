package br.com.surittec.suricdi.example.util.i18n;

import org.apache.deltaspike.core.api.message.MessageBundle;
import org.apache.deltaspike.core.api.message.MessageTemplate;

@MessageBundle
public interface Messages {

	/*
	 * Pessoa
	 */
	
	@MessageTemplate("{pessoa.cadastro.falha.unicidade}")
	String pessoaCadastroFalhaUnicidade();
	
	/*
	 * Global
	 */
	
	@MessageTemplate("{global.registro.nao.encontrado}")
	String globalRegistroNaoEncontrado();
	
	@MessageTemplate("{global.dados.incluidos.sucesso}")
	String globalDadosIncluidosSucesso();
	
	@MessageTemplate("{global.dados.alterados.sucesso}")
	String globalDadosAlteradosSucesso();
	
	@MessageTemplate("{global.registro.removido.sucesso}")
	String globalRegistroRemovidoSucesso();
	
}
