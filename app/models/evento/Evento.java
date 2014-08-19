package models.evento;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import models.usuario.Usuario;
import play.data.validation.Constraints.Required;

import com.fasterxml.jackson.annotation.JsonBackReference;

import controllers.Application;

@Entity(name = "Evento")
public class Evento {

	@Id
	@SequenceGenerator(name = "EVENTO_SEQUENCE", sequenceName = "EVENTO_SEQUENCE", allocationSize = 1, initialValue = 0)
	@GeneratedValue(strategy = GenerationType.TABLE)
	private Long id;
	
	@NotNull
	@Column(nullable = false)
	private String titulo;

	@NotNull
	@Column(nullable = false)
	private String descricao;

	@Column
	@NotNull
	@Temporal(TemporalType.DATE)
	private Date data;

	private Participantes participantes;

	@NotNull
	@JoinColumn
	@OneToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JsonBackReference
	private Usuario administrador;

	@Required
	@ManyToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinTable(joinColumns={@JoinColumn(name="evento_id", nullable = false)}, inverseJoinColumns={@JoinColumn(name="tema_id", nullable = false)})
	private List<Tema> temas;

	@NotNull
	@JoinColumn
	@ManyToOne(cascade = CascadeType.ALL)
	private Local local;
	
	public Evento(){ }

	public Evento(Usuario usuario, String titulo, String descricao,
			Date data, List<Tema> temas, int capacidade, String comoChegar, Participantes participantes)
			throws Exception {
		this.administrador = usuario;
		this.titulo = titulo;
		this.descricao = descricao;
		this.data = data;
		this.temas = temas;
		this.participantes = participantes;
		this.local = new Local(capacidade, comoChegar);
	}

	public String getTitulo() {
		return titulo;
	}

	public String getDescricao() {
		return descricao;
	}

	public Date getData() {
		return data;
	}

	public Long getId() {
		return id;
	}

	public List<Usuario> getParticipantes() {
		return participantes.getParticipantes();
	}

	public void addParticipante(Usuario usuario) {
		participantes.addParticipante(usuario, this);
	}

	public Usuario getAdministrador() {
		return administrador;
	}

	public List<Tema> getTemas() {
		return temas;
	}

	public Local getLocal() {
		return this.local;
	}

	public void salvar(){
		Application.getDao().persist(this);
	}
	
	public static Evento buscar(Long id) {
		return Application.getDao().findByEntityId(Evento.class, id);
	}

	public static List<Evento> todos() {
		return Application.getDao().findAllByClassName("Evento");
	}

	public int getCapacidade() {
		return this.local.getCapacidade();
	}
}
