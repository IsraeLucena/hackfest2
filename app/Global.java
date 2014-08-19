import java.util.Date;
import java.util.List;
import java.util.Random;

import models.evento.Evento;
import models.evento.Local;
import models.evento.ParticipantesPorExperiencia;
import models.evento.Tema;
import models.usuario.Usuario;
import play.GlobalSettings;
import play.api.mvc.Handler;
import play.db.jpa.JPA;
import play.libs.F;
import play.mvc.Http.RequestHeader;

public class Global extends GlobalSettings {

	private boolean jaIniciou = false;
	
	@Override
	public Handler onRouteRequest(RequestHeader arg0) {
		/*if (!jaIniciou) {
			jaIniciou = true;
			JPA.withTransaction(new F.Callback0() {
		        @Override
		        public void invoke() throws Throwable {
		        	
		        	for (int i = 1; i <= 5; i++) {
		        		new Tema("Tema " + i).salvar();
		        	}
		        	
		        	for (int i = 1; i <= 100; i++) {
						new Usuario("Usuario "+i, "usuario"+i+"@mail.com", "Senha#123").salvar();
					}
		        	
		        	List<Usuario> usuarios = Usuario.todos();
		        	List<Tema> temas = Tema.todos();
		        	
		        	Random rnd = new Random();
		        	
		        	int i = 1;
		        	
		        	Local local1 = new Local(5, "Localidade 1");
		        	local1.salvar();
		        	
		        	Local local2 = new Local(50, "Localidade 2");
		        	local2.salvar();
		        	
		        	Local local3 = new Local(60, "Localidade 3");
		        	local3.salvar();
	/*
		        	Evento evento3 = new Evento(usuarios.get(0), "Evento " + 1, "Descrição " + 1, new Date(), temas, loc ,new ParticipantesPorExperiencia());
		        	evento3.salvar();
		        	
		        	Evento evento = new Evento(usuarios.get(1), "Evento " + 2, "Descrição " + 2, new Date(), temas, local2.getId(), false);
		        	evento.salvar();
		        	evento = new Evento(usuarios.get(2), "Evento " + 3, "Descrição " + 3, new Date(), temas, local3.getId(), false);
		        	evento.salvar();
		        	evento = new Evento(usuarios.get(3), "Evento " + 4, "Descrição " + 4, new Date(), temas, local2.getId(), false);
		        	evento.salvar();
		        	evento = new Evento(usuarios.get(4), "Evento " + 5, "Descrição " + 5, new Date(), temas, local3.getId(), false);
		        	/*
		        	List<Evento> eventos = Evento.todos();
		        	
		        	
		        	for (i = 5; i < 87; i++){
		        		Usuario usuario = usuarios.get(i);
		        		Evento ev = eventos.get(rnd.nextInt(eventos.size()-1));
		        		ev.addParticipante(usuario);
		        		ev.salvar();
		        	}
		        	
		        	usuarios = Usuario.todos();
	
		        	Evento evento1 = new Evento(usuarios.get(0), "Evento " + 6, "Descrição " + 6, new Date(), temas, local1.getId() ,true);
		        	evento1.salvar();
		        	
		        	for (int j = 1; j <= 5; j++) {
		        		evento1.addParticipante(usuarios.get(j));
					}
	        		evento1.salvar();
	        		
		        	Evento evento2 = new Evento(usuarios.get(1), "Evento " + 7, "Descrição " + 7, new Date(), temas, local2.getId() ,true);
	
		        	for (int j = 2; j <= 5; j++) {
		        		evento2.addParticipante(usuarios.get(j));
					}
	        		evento2.salvar();
		        }
			});
		}
		*/
		return null;
	}
}