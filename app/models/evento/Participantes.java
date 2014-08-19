package models.evento;

import java.util.List;

import models.usuario.Usuario;

public interface Participantes {
	void addParticipante(Usuario usuario, Evento evento);	
	List<Usuario> getParticipantes();
}
