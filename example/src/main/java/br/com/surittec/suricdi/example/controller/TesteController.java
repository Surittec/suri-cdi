package br.com.surittec.suricdi.example.controller;

import java.io.Serializable;

import org.ocpsoft.rewrite.annotation.Join;

import br.com.surittec.suricdi.faces.controller.Controller;
import br.com.surittec.suricdi.faces.controller.stereotype.ViewController;

@ViewController
@Join(path="/teste", to="/view/teste.xhtml")
public class TesteController extends Controller implements Serializable{

	private static final long serialVersionUID = 1L;

	private Integer count = 1;
	
	public void teste(){
		count++;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}
	
}
