package br.com.surittec.suricdi.example.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "pessoa")
public class Pessoa {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, unique = true, nullable = false)
	private Long id;
	
	@Column(name = "nome", length = 100, nullable = false)
	private String nome;
	
	
}
