package controllers;

import static play.data.Form.form;

import java.util.List;

import models.usuario.Login;
import models.usuario.Usuario;
import play.data.Form;
import play.data.validation.ValidationError;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;

public class UsuarioController extends Controller {
	
	private static Form<Login> loginForm = form(Login.class);
	
	public static Result novo() {
		return ok();
	}
	
	public static Result entrar() {
		return ok();
	}
	
	public static boolean logado() {
		return true;
	}
	
	@Transactional
	public static Result autenticar() {
		System.out.println("Okkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk");
		Login login = loginForm.bindFromRequest().get();
		/*
		if (loginForm.hasErrors()) {
            String errorMsg = "";
            java.util.Map<String, List<play.data.validation.ValidationError>> errorsAll = loginForm.errors();
            for (String field : errorsAll.keySet()) {
                errorMsg += field + " ";
                for (ValidationError error : errorsAll.get(field)) {
                    errorMsg += error.message() + ", ";
                }
            }
            return badRequest(errorMsg);
        }

        if (loginForm.hasErrors() || !login.ehValido()) {
        	//flash("fail", "Email ou Senha Inválidos");
        	return badRequest();
        } else {
        	System.out.println("lllllllllllllllll");
        	for (Usuario usuario : Usuario.todos()) {
        		if (usuario.loginValido(login)){
        			session().clear();
                    session("usuario", usuario.getId().toString());
                    return ok();
        		}
			}
        }*/
        return badRequest();
    }
}
