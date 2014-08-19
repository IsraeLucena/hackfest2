package models.usuario;

import models.validations.LoginValidation;

public class Registro {
	private String nome;
	
	private String email;
	
	private String senha;
	
	private String outraSenha;
	
	public Registro(String nome, String email, String senha, String outraSenha) {
		this.nome = nome;
		this.email = email;
		this.senha = senha;
		this.outraSenha = outraSenha;
	}

	public String getNome() {
		return nome;
	}

	public String getEmail() {
		return email;
	}

	public String getSenha() {
		return senha;
	}

	public String getOutraSenha() {
		return outraSenha;
	}

	public boolean validate(){
		return LoginValidation.emailValido(email) && LoginValidation.senhaValida(senha, outraSenha);
	}
}
