/*
 * SURITTEC
 * Copyright 2014, SURITTEC CONSULTORIA LTDA, 
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
