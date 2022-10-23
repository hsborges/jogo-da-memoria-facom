package br.ufms.facom.jogo.controllers;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.ufms.facom.jogo.repositories.RankingRepository;

/**
 * Servlet implementation class HomeController
 */
@WebServlet(value = "/home")
public class HomeController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @EJB
    private RankingRepository rankingRepository;

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("ranking", rankingRepository.getTopPartidas(10));
        request.getRequestDispatcher("/WEB-INF/home.jsp").forward(request, response);
    }
}
