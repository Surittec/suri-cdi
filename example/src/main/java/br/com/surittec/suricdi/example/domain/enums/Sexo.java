package br.com.surittec.suricdi.example.domain.enums;

public enum Sexo {

	M("Masculino"),
	F("Feminino");
	
	private String descricao;
	
	private Sexo(String descricao){
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
	
}
