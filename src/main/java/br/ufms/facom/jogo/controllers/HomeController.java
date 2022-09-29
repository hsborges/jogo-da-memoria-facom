package br.ufms.facom.jogo.controllers;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.ufms.facom.jogo.entities.Jogador;

/**
 * Servlet implementation class HomeController
 */
public class HomeController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@PersistenceContext(name = "pu-sqlite")
	private EntityManager em;


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		List<Jogador> ranking = this.em.createQuery("SELECT j FROM Jogador j").getResultList();
		request.setAttribute("ranking", ranking);
		request.getRequestDispatcher("/WEB-INF/home.jsp").forward(request, response);
	}
}
