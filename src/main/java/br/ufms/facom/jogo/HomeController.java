package br.ufms.facom.jogo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class HomeController
 */
public class HomeController extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public HomeController() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		List<String> ranking = new ArrayList<String>();
		ranking.add("Hudson Borges");
		ranking.add("Maria Jose");
		ranking.add("Jose Maria");
		
		if (request.getParameter("nome") != null)
			ranking.add((String)request.getParameter("nome"));
		
		request.setAttribute("ranking", ranking);
		
		request.getRequestDispatcher("/WEB-INF/home.jsp").forward(request, response);
	}
}
