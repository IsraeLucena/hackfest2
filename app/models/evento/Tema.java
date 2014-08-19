package models.evento;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import org.hibernate.validator.constraints.NotEmpty;

import controllers.Application;

@Entity(name = "Tema")
public class Tema {
	
	@Id
	@SequenceGenerator(name = "TEMA_SEQUENCE", allocationSize = 1, initialValue = 0)
	@GeneratedValue(strategy = GenerationType.TABLE)
	private Long id;

	@NotEmpty
	@Column(unique = true)
	private String titulo;
	
	public Tema() { }
	
	public Tema(String titulo) {
		this.titulo = titulo;
	}

	public Long getId() {
		return id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void salvar(){
		if (Application.getDao().findByAttributeName("Tema", "titulo", this.titulo).size() == 0){
			Application.getDao().persist(this);
			Application.getDao().flush();
		}
	}

	public static Tema buscar(long id){
		return Application.getDao().findByEntityId(Tema.class, id);
	}

	public static List<Tema> todos(){
		return Application.getDao().findAllByClassName("Tema");
	}
	
	@Override
	public String toString() {
		return this.titulo;
	}
}
