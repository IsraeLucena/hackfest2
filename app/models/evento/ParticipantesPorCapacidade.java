package models.evento;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import models.usuario.Usuario;

@Entity(name="ParticipantesPorCapacidade")
public class ParticipantesPorCapacidade implements Participantes {

	@Id
	@SequenceGenerator(allocationSize = 1, initialValue = 0, name = "POR_EXPERIENCIA_SEQUENCE")
	@GeneratedValue(strategy = GenerationType.TABLE)
	private Long id;

	@JoinColumn
	@OneToMany(cascade=CascadeType.ALL)
	private List<Usuario> inscritos;
	
	public void addParticipante(Usuario usuario, AdicionaParticipante metodoParaAdicionar){
		metodoParaAdicionar.addParticipante(usuario);
	}

	@Override
	public void addParticipante(Usuario usuario, Evento evento) {
		if (inscritos.size() < evento.getCapacidade()) {
			inscritos.add(usuario);
		}
	}

	@Override
	public List<Usuario> getParticipantes() {
		return inscritos;
	}

}
