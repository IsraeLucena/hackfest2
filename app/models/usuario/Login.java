package models.usuario;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import models.validations.LoginValidation;

import org.apache.xerces.impl.dv.util.Base64;
import org.hibernate.validator.constraints.NotEmpty;

import play.data.validation.Constraints.Email;
import controllers.Application;

@Entity
public class Login {

	public String getEmail() {
		return email;
	}

	private void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	private void setSenha(String senha) {
		this.senha = senha;
	}

	@Id
	@SequenceGenerator(name = "LOGUIN_SEQUENCE", sequenceName = "LOGUIN_SEQUENCE", allocationSize = 1, initialValue = 0)
	@GeneratedValue(strategy = GenerationType.TABLE)
	private Long id;

	@Column(name = "email", unique = true, nullable = false)
	@Email
	private String email;

	@NotEmpty
	@Column(name = "senha", nullable = false)
	private String senha;

	public Login() {
	}

	public Login(String email, String senha) {
		this.email = email;
		this.senha = criptografarSenha(senha);
	}

	public Long getId() {
		return id;
	}

	private String criptografarSenha(String senha) {
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA");
		} catch (NoSuchAlgorithmException e) {

		}
		try {
			md.update(senha.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {

		}

		byte raw[] = md.digest();
		return Base64.encode(raw);
	}

	public boolean ehValido() {
		return (LoginValidation.emailValido(this.email)
				&& LoginValidation.senhaValida(this.senha, this.senha)
				&& this.email.equals(this.email) && Application.getDao()
				.findByAttributeName("Login", "email", this.email).size() == 0);
	}

	public boolean senhaValida(String senha) {
		if (senha != null && !senha.isEmpty()) {
			return this.senha.equals(criptografarSenha(senha));
		}
		return false;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj != null && obj instanceof Login) {
			Login login = (Login) obj;
			return this.email.equals(login.email)
					&& (this.senha.equals(criptografarSenha(login.senha)));
		}
		return false;
	}
}
