package controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import models.evento.Evento;
import models.evento.Tema;
import play.Logger;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;

import com.fasterxml.jackson.databind.ObjectMapper;

public class EventoController extends Controller{
	
	@Transactional
	public static Result novo() {
		return ok();
	}
	
	@Transactional
	public static Result porTema(int temaId) {
	
		Tema tema = Tema.buscar(temaId);
		
		if (tema == null){
			return badRequest();
		}

		List<Evento> eventos = new ArrayList<>();
		
		
		for (Evento evento : Evento.todos()) {
			if (evento.getTemas().contains(tema)){
				eventos.add(evento);
			}
		}

		Collections.sort(eventos, new Comparator<Evento>() {
			@Override
			public int compare(Evento e1, Evento e2) {
				return Integer.compare(e2.getParticipantes().size(), e1.getParticipantes().size());
			};
		});
		
		ObjectMapper mapper = new ObjectMapper();
		String json = "";

		try {
			json = mapper.writeValueAsString(eventos);
		} catch (Exception e) {
			Logger.error(e.getMessage());
			return badRequest();
		}

		return ok(json);
	}
	
	@Transactional
	public static Result participar() {
		return ok();
	}
}
