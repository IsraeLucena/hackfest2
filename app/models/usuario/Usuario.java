package models.usuario;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SequenceGenerator;

import models.evento.Evento;
import play.data.validation.Constraints.Required;
import controllers.Application;

@Entity
public class Usuario implements Comparable<Usuario> {
	
	@Id
	@SequenceGenerator(name = "USUARIO_SEQUENCE", sequenceName = "USUARIO_SEQUENCE", allocationSize = 1, initialValue = 0)
	@GeneratedValue(strategy = GenerationType.TABLE)
	private Long id;
	
	@Required
	@Column(nullable = false)
	private String nome;

	@Required
	@PrimaryKeyJoinColumn
	@OneToOne(cascade = CascadeType.ALL, optional = false, fetch = FetchType.EAGER, orphanRemoval = false)
	private Login login;

	public Usuario() { }

	public Usuario(String nome, String email, String senha) {
		this.nome = nome;
		this.login = new Login(email, senha);
	}

	public String getNome() {
		return nome;
	}

	public Long getId() {
		return id;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj != null && obj instanceof Usuario ){
			Usuario usuario = (Usuario) obj;
			return (this.login.equals(usuario.login));
		}
		return false;
	}

	public void salvar() {
		if (login.ehValido()){
			Application.getDao().persist(this);
			Application.getDao().flush();
		}
	}

	public static List<Usuario> todos() {
		return Application.getDao().findAllByClassName("Usuario");
	}

	public static Usuario buscar(Long id) {
		return Application.getDao().findByEntityId(Usuario.class, id);
	}

	public static boolean loginValido(String email, String senha) {
		List<Login> login = Application.getDao().findByAttributeName("Login", "email", email);
		return login.size() == 1 && login.get(0).senhaValida(senha);
	}

	public List<Evento> getEventosAdministrados() {
		List<Evento> eventosAdministrados = new ArrayList<>();
		for (Evento evento : Evento.todos()) {
			if (evento.getAdministrador().equals(this)) {
				eventosAdministrados.add(evento);
			}
		}
		return eventosAdministrados;
	}
	
	public List<Evento> getEventosQueVaiParticipar() {
		List<Evento> eventosQueVaiParticipar = new ArrayList<>();
		for (Evento evento : Evento.todos()) {
			if (evento.getParticipantes().contains(this)) {
				eventosQueVaiParticipar.add(evento);
			}
		}
		return eventosQueVaiParticipar;
	}

	public boolean loginValido(Login login) {
		return this.login.equals(login);
	}

	@Override
	public int compareTo(Usuario outroUsuario) {
		int compResult = Integer.compare(this.getEventosAdministrados().size(), outroUsuario.getEventosAdministrados().size());

		if (compResult == 0) {
			compResult = Integer.compare(this.getEventosQueVaiParticipar().size(), outroUsuario.getEventosQueVaiParticipar().size());
		}

		return compResult;
	}
}
