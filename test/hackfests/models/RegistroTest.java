package hackfests.models;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import models.usuario.Registro;

import org.junit.Test;

public class RegistroTest {

	@Test
	public void deveValidarUmRegistro(){
		assertThat(new Registro("jose", "jose@mail.com", "Senha#123", "Senha#123").validate(), is(true));
		assertThat(new Registro("jose", "jose@mail.com", "Senha#123", "senha#123").validate(), is(false));
	}
}
