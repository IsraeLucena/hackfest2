package models.evento;

import java.util.List;

import javax.annotation.Nonnegative;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import models.usuario.Usuario;

import org.hibernate.validator.constraints.NotEmpty;

import play.db.jpa.Transactional;
import controllers.Application;

@Entity
public class Local {

	@Id
	@SequenceGenerator(name = "EVENTO_SEQUENCE", sequenceName = "EVENTO_SEQUENCE", allocationSize = 1, initialValue = 0)
	@GeneratedValue(strategy = GenerationType.TABLE)
	private Long id;
	
	@Column(nullable = false)
	@Nonnegative
	private int capacidade;

	@NotEmpty
	@Column(nullable = false)
	private String comoChegar;
	
	public Local() { }
	
	public Local(int capacidade, String comoChegar) {
		this.capacidade = capacidade;
		this.comoChegar = comoChegar;
	}

	public Long getId() {
		return id;
	}

	public int getCapacidade() {
		return capacidade;
	}

	public String getComoChegar() {
		return comoChegar;
	}

	public void salvar(){
		Application.getDao().persist(this);
		Application.getDao().flush();
	}

	public static Local buscar(Long id) {
		return Application.getDao().findByEntityId(Local.class, id);
	}

	@Transactional
	public static List<Local> todos() {
		return Application.getDao().findAllByClassName("Local");
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj != null && obj instanceof Local) {
			Local local = (Local) obj;
			return this.capacidade == local.getCapacidade()
					&& this.comoChegar == local.getComoChegar();
		}
		return false;
	}
}
