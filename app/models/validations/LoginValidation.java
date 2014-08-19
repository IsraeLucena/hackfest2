package models.validations;

public class LoginValidation {

	public static boolean senhaValida(String senha, String outraSenha) {
		return senha != null && outraSenha != null &&
			!senha.isEmpty() && !outraSenha.isEmpty() && senha.equals(outraSenha);
	}
	
	public static boolean emailValido(String email) {
		return email != null && !email.isEmpty();
	}
}
