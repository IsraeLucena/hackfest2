package controllers;

import models.dao.GenericDAO;
import models.dao.GenericDAOImpl;
import play.mvc.Controller;

public class Application extends Controller {
	
	private static GenericDAO dao;

	public static GenericDAO getDao() {
		if (dao == null) {
			dao = new GenericDAOImpl();
		}
		return dao;
	}
}
