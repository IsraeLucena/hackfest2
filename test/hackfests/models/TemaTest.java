package hackfests.models;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.fail;
import models.evento.Tema;

import org.junit.Test;

import base.AbstractTest;

public class TemaTest extends AbstractTest {

	@Test
	public void deveSalvarTemaSemRepeticao() {
		try {
			assertThat(Tema.todos().size(), is(0));
			
			Tema tema = new Tema("Engenharia de Software I");
			tema.salvar();
			assertThat(Tema.todos().size(), is(1));
			
			assertThat(tema.getId(), is(1L));
			assertThat(tema.getTitulo(), is("Engenharia de Software I"));
			
			tema = new Tema("Engenharia de Software II");
			tema.salvar();
			assertThat(Tema.todos().size(), is(2));

			assertThat(tema.getId(), is(2L));
		} catch (Exception e) {
		}
	}
}
