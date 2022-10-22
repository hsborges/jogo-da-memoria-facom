package br.ufms.facom.jogo.controllers;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.ufms.facom.jogo.entities.Ranking;

/**
 * Servlet implementation class HomeController
 */
@WebServlet(value = "/home")
public class HomeController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private EntityManager em = Persistence.createEntityManagerFactory("pu-sqlite").createEntityManager();

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @Override
    @SuppressWarnings("unchecked")
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Ranking> ranking = this.em.createQuery("SELECT r FROM Ranking r ORDER BY r.posicao ASC ").setMaxResults(10).getResultList();
        request.setAttribute("ranking", ranking);

        request.getRequestDispatcher("/WEB-INF/home.jsp").forward(request, response);
    }
}
