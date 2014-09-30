package br.com.surittec.suricdi.example.util.i18n;

import org.apache.deltaspike.core.api.message.MessageBundle;
import org.apache.deltaspike.core.api.message.MessageTemplate;

@MessageBundle
public interface Messages {

	@MessageTemplate("{global.dados.incluidos.sucesso}")
	String globalDadosIncluidosSucesso();
	
}
