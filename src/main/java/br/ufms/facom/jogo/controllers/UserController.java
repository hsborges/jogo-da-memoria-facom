package br.ufms.facom.jogo.controllers;

import java.io.IOException;
import java.util.Base64;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.apache.commons.io.IOUtils;

import br.ufms.facom.jogo.entities.Jogador;

/**
 * Servlet implementation class UserController
 */
@WebServlet("/usuario")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
        maxFileSize = 1024 * 1024 * 10, // 10 MB
        maxRequestSize = 1024 * 1024 * 100 // 100 MB
)
public class UserController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private EntityManager em = Persistence.createEntityManagerFactory("pu-sqlite").createEntityManager();

    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserController() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if (action != null && action.equals("sair")) {
            request.getSession().removeAttribute("jogador");
            request.getSession().invalidate();
            response.sendRedirect(request.getContextPath() + "/");
            return;
        }

        request.getRequestDispatcher("/WEB-INF/usuario.jsp").forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        String uuid = request.getParameter("uuid");

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if (action != null && action.equals("cadastro")) {
            String name = request.getParameter("name");

            Part avtarPart = request.getPart("avatar");
            String fileName = avtarPart.getSubmittedFileName();
            String encodedString = null;

            if (!fileName.isEmpty()) {
                encodedString = Base64.getEncoder().encodeToString(IOUtils.toByteArray(avtarPart.getInputStream()));
            }

            EntityTransaction trx = this.em.getTransaction();

            try {
                trx.begin();
                Long res = (Long) this.em.createQuery("SELECT COUNT(j) FROM Jogador j WHERE j.email = :email")
                        .setParameter("email", email).getSingleResult();
                if (res > 0)
                    throw new Exception("Email já cadastrado!");

                Jogador jogador = new Jogador(name, email, password, encodedString);
                this.em.persist(jogador);

                if (uuid != null) {
                    this.em.createQuery("UPDATE Partida p SET p.jogador = :jogador WHERE p.uuid = :uuid")
                        .setParameter("uuid", uuid)
                        .setParameter("jogador", jogador)
                        .executeUpdate();
                    
                    request.setAttribute("partida", request.getSession().getAttribute(uuid));
                    request.getRequestDispatcher("/partida").forward(request, response);
                }
                
                

                this.em.flush();
                trx.commit();
            } catch (Exception e) {
                trx.rollback();
                request.setAttribute("message", e.getMessage());
                this.doGet(request, response);
                return;
            }
        }

        try {
            Jogador jogador = (Jogador) this.em
                    .createQuery("SELECT j FROM Jogador j WHERE j.email = :email AND j.password = :password")
                    .setParameter("email", email)
                    .setParameter("password", password)
                    .getSingleResult();

            HttpSession session = request.getSession();
            session.setAttribute("jogador", jogador);
            
            if (uuid != null) {
                this.em.getTransaction().begin();
                this.em.createQuery("UPDATE Partida p SET p.jogador = :jogador WHERE p.uuid = :uuid")
                    .setParameter("uuid", uuid)
                    .setParameter("jogador", jogador)
                    .executeUpdate();
                this.em.getTransaction().commit();
            }

            response.sendRedirect(request.getContextPath() + "/home?uuid=" + uuid);
        } catch (NoResultException nre) {
            request.setAttribute("message", "Usuário não encontrado ou senha incorreta!");
            this.doGet(request, response);
        }

    }

}
