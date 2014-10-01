package br.com.surittec.suricdi.example.util;

import java.util.Arrays;
import java.util.List;

import br.com.surittec.suricdi.example.domain.enums.Sexo;
import br.com.surittec.suricdi.faces.controller.Controller;
import br.com.surittec.suricdi.faces.controller.stereotype.ViewController;

@ViewController
public class ListasUtil extends Controller{

	private static final long serialVersionUID = 1L;
	
	private List<Sexo> sexos;

	public List<Sexo> getSexos() {
		if(sexos == null){
			sexos = Arrays.asList(Sexo.values());
		}
		return sexos;
	}
	
}
