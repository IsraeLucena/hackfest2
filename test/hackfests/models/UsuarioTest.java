package hackfests.models;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.fail;

import java.util.Date;

import models.evento.Evento;
import models.evento.Local;
import models.evento.Tema;
import models.exceptions.Strings;
import models.usuario.Usuario;

import org.junit.Before;
import org.junit.Test;

import base.AbstractTest;

public class UsuarioTest extends AbstractTest {

	private Local localPadrao;
	private Usuario usuario1;
	private Usuario usuario2;
	private Usuario usuario3;

	@Before
	public void setup() {
		localPadrao = new Local(15, "Local 1");
		localPadrao.salvar();

		usuario1 = new Usuario("jose", "jose@mail.com", "Senha#123");
		usuario2 = new Usuario("maria", "maria@mail.com", "Senha#123");
		usuario3 = new Usuario("joana", "joana@mail.com", "Senha#123");

		try {
			new Tema("Tema 1").salvar();
			new Tema("Tema 2").salvar();
		} catch (Exception e) {
			fail();
		}
	}

	@Test
	public void deveCadastrarUmUsuarioSemRepeticao() {
		try {
			assertThat(Usuario.todos().size(), is(0));

			Usuario usuario = new Usuario("jose", "jose@mail.com", "Senha#123");
			usuario.salvar();
			assertThat(Usuario.todos().size(), is(1));

			assertThat(usuario.getId(), is(1L));
			assertThat(usuario.getNome(), is("jose"));

			usuario = new Usuario("jose", "jose@mail.com", "Senha#123");
			usuario.salvar();
			assertThat(Usuario.todos().size(), is(1));

			usuario = new Usuario("jose", "jose.luis@mail.com", "Senha#123");
			usuario.salvar();
			assertThat(Usuario.todos().size(), is(2));
		} catch (Exception e) {
			fail();
		}
	}

	@Test
	public void deveVerificarSeLoginEhValido() {
		try {
			assertThat(Usuario.loginValido("jose@mail.com", "Senha#123"),
					is(false));

			Usuario usuario = new Usuario("jose", "jose@mail.com", "Senha#123");
			usuario.salvar();

			assertThat(Usuario.loginValido("jose.luis@mail.com", "Senha#123"),
					is(false));
			assertThat(Usuario.loginValido("jose@mail.com", "senha#123"),
					is(false));

			assertThat(Usuario.loginValido("jose@mail.com", "Senha#123"),
					is(true));
		} catch (Exception e) {
			fail();
		}
	}

	/*@Test
	public void deveCriarUmEvento() {
		try {
			new Evento(usuario1, "Evento 1", "Descricao 1", new Date(),
					Tema.todos(), localPadrao.getId(), false).salvar();
		} catch (Exception e) {
			System.err.println(e.getMessage());
			fail();
		}

		assertThat(Usuario.buscar(1L).getEventosAdministrados().size(), is(1));

		try {
			new Evento(usuario1, "Evento 2", "Descricao 2", new Date(),
					Tema.todos(), localPadrao.getId(), false).salvar();
		} catch (Exception e) {
			fail();
		}

		assertThat(Usuario.buscar(1L).getEventosAdministrados().size(), is(2));
	}

	@Test
	public void deveCriarEventoComLocalJaCadastradoOuNao() {
		try {
			try {
				new Evento(usuario1, "Evento 1", "Descricao 1", new Date(),
						Tema.todos(), 20, "No LCC2", false).salvar();
			} catch (Exception e) {
				fail();
			}

			assertThat(Usuario.buscar(1L).getEventosAdministrados().size(),
					is(1));

			try {
				new Evento(usuario1, "Evento 1", "Descricao 1", new Date(),
						Tema.todos(), 1L, false).salvar();
			} catch (Exception e) {
				fail();
			}

			assertThat(Usuario.buscar(1L).getEventosAdministrados().size(),
					is(2));
		} catch (Exception e) {
			fail();
		}
	}

	@Test
	public void naoDeveParticiparDeEventosComCapacidadeTotal() {
		Evento evento = null;

		try {
			evento = new Evento(usuario1, "Evento 1", "Descricao 1",
					new Date(), Tema.todos(), 1, "No LCC2", false);
		} catch (Exception e) {
			fail();
		}

		try {
			evento.addParticipante(usuario2);
		} catch (Exception e) {
			fail();
		}

		try {
			evento.addParticipante(usuario3);
			fail();
		} catch (Exception e) {
			assertThat(e.getMessage(),
					is(Strings.EVENTO_COM_CAPACIDADE_MAXIMA_EXCEPTION));
		}
	}

	@Test
	public void devePriorizarParticipantesExperientes() {
		try {
			Evento evento1 = null;
			Evento evento2 = null;
			Evento evento3 = null;
			Evento evento4 = null;

			try {
				evento1 = new Evento(usuario1, "Evento 1", "Descricao 1",
						new Date(), Tema.todos(), 1, "No LCC2", true);
			} catch (Exception e) {
				fail();
			}

			evento1.addParticipante(usuario2);
			evento1.salvar();

			assertThat(usuario2.getEventosQueVaiParticipar().size(), is(1));

			try {
				evento1.addParticipante(usuario3);
				fail();
			} catch (Exception e) {
				assertThat(e.getMessage(),
						is(Strings.EVENTO_COM_CAPACIDADE_MAXIMA_EXCEPTION));
			}

			assertThat(usuario2.getEventosAdministrados().size(), is(0));
			assertThat(usuario2.getEventosQueVaiParticipar().size(), is(1));

			assertThat(usuario3.getEventosAdministrados().size(), is(0));
			assertThat(usuario3.getEventosQueVaiParticipar().size(), is(0));

			evento2 = new Evento(usuario3, "Evento 2", "Descricao 2",
					new Date(), Tema.todos(), localPadrao.getId(), false);
			evento2.salvar();

			assertThat(usuario2.getEventosAdministrados().size(), is(0));
			assertThat(usuario2.getEventosQueVaiParticipar().size(), is(1));

			assertThat(usuario3.getEventosAdministrados().size(), is(1));
			assertThat(usuario3.getEventosQueVaiParticipar().size(), is(0));

			try {
				evento1.addParticipante(usuario3);
				fail();
			} catch (Exception e) {
				assertThat(e.getMessage(),
						is(Strings.EVENTO_COM_CAPACIDADE_MAXIMA_EXCEPTION));
			}

			evento3 = new Evento(usuario1, "Evento 3", "Descricao 3",
					new Date(), Tema.todos(), localPadrao.getId(), false);

			evento3.addParticipante(usuario3);
			evento3.salvar();

			assertThat(usuario2.getEventosAdministrados().size(), is(0));
			assertThat(usuario2.getEventosQueVaiParticipar().size(), is(1));

			assertThat(usuario3.getEventosAdministrados().size(), is(1));
			assertThat(usuario3.getEventosQueVaiParticipar().size(), is(1));

			try {
				evento1.addParticipante(usuario3);
				fail();
			} catch (Exception e) {
				assertThat(e.getMessage(),
						is(Strings.EVENTO_COM_CAPACIDADE_MAXIMA_EXCEPTION));
			}

			evento4 = new Evento(usuario1, "Evento 4", "Descricao 4",
					new Date(), Tema.todos(), localPadrao.getId(), false);

			evento4.addParticipante(usuario3);
			evento4.salvar();

			assertThat(usuario2.getEventosAdministrados().size(), is(0));
			assertThat(usuario2.getEventosQueVaiParticipar().size(), is(1));

			assertThat(usuario3.getEventosAdministrados().size(), is(1));
			assertThat(usuario3.getEventosQueVaiParticipar().size(), is(2));

			try {
				evento1.addParticipante(usuario3);
			} catch (Exception e) {
				fail();
			}

			assertThat(usuario2.getEventosAdministrados().size(), is(0));
			assertThat(usuario2.getEventosQueVaiParticipar().size(), is(0));

			assertThat(usuario3.getEventosAdministrados().size(), is(1));
			assertThat(usuario3.getEventosQueVaiParticipar().size(), is(3));
		} catch (Exception e) {
			fail();
		}
	}

	@Test
	public void deveListarEventosParticipandoEAdministrados() {
		try {
			Evento evento1 = null;
			Evento evento2 = null;

			assertThat(usuario1.getEventosAdministrados().size(), is(0));

			evento1 = new Evento(usuario1, "Evento 1", "Descricao 1",
					new Date(), Tema.todos(), localPadrao.getId(), false);
			evento1.salvar();

			assertThat(usuario1.getEventosAdministrados().size(), is(1));

			evento2 = new Evento(usuario1, "Evento 2", "Descricao 2",
					new Date(), Tema.todos(), localPadrao.getId(), false);
			evento2.salvar();

			assertThat(usuario1.getEventosAdministrados().size(), is(2));

			assertThat(usuario2.getEventosAdministrados().size(), is(0));

			new Evento(usuario2, "Evento 3", "Descricao 3", new Date(),
					Tema.todos(), localPadrao.getId(), false).salvar();

			assertThat(usuario1.getEventosAdministrados().size(), is(2));
			assertThat(usuario2.getEventosAdministrados().size(), is(1));

			assertThat(usuario1.getEventosQueVaiParticipar().size(), is(0));
			assertThat(usuario2.getEventosQueVaiParticipar().size(), is(0));

			evento1.addParticipante(usuario2);

			try {
				evento1.addParticipante(usuario1);
			} catch (Exception e) {
				assertThat(e.getMessage(),
						is(Strings.EVENTO_EH_ADMINSTRADOR_EXCEPTION));
			}
		} catch (Exception e) {
			fail();
		}
	}*/
}
