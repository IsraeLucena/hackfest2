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

@Entity(name="ParticipantesPorExperiencia")
public class ParticipantesPorExperiencia implements Participantes {
	
	@Id
	@SequenceGenerator(allocationSize = 1, initialValue = 0, name = "POR_EXPERIENCIA_SEQUENCE")
	@GeneratedValue(strategy = GenerationType.TABLE)
	private Long id;
	
	@JoinColumn
	@OneToMany(cascade=CascadeType.ALL)
	private List<Usuario> inscritosConfirmados;
	
	@JoinColumn
	@OneToMany(cascade=CascadeType.ALL)
	private List<Usuario> inscritosNaoConfirmados;

	@Override
	public void addParticipante(Usuario usuario, Evento evento) {
		if (inscritosConfirmados.size() == evento.getCapacidade()) {
			int indiceDoMenosExperiente = getUsuarioMenosExperiente(usuario);
			
			if (usuario.compareTo(inscritosConfirmados.get(indiceDoMenosExperiente)) > 0){
				inscritosNaoConfirmados.add(inscritosConfirmados.remove(indiceDoMenosExperiente));
				inscritosConfirmados.add(usuario);
			} else {
				inscritosNaoConfirmados.add(usuario);
			}
		} else {
			inscritosConfirmados.add(usuario);
		}
	}

	private int getUsuarioMenosExperiente(Usuario usuario) {
		int indiceDoMenosExperiente = -1;
		for (int i = 0; i < inscritosConfirmados.size(); i++) {
			if (usuario.compareTo(inscritosConfirmados.get(i)) > 0){
				indiceDoMenosExperiente = i;
			}
		}
		return indiceDoMenosExperiente;
	}

	@Override
	public List<Usuario> getParticipantes() {
		List<Usuario> todos = inscritosConfirmados;
		todos.addAll(inscritosNaoConfirmados);
		return todos;
	}
}
