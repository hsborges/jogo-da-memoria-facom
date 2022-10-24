package br.ufms.facom.jogo.controllers;

import java.io.IOException;
import java.util.Base64;

import javax.ejb.EJB;
import javax.persistence.NoResultException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.commons.io.IOUtils;
import org.springframework.security.crypto.bcrypt.BCrypt;

import br.ufms.facom.jogo.entities.Jogador;
import br.ufms.facom.jogo.entities.Partida;
import br.ufms.facom.jogo.repositories.JogadorRepository;
import br.ufms.facom.jogo.repositories.PartidasRepository;

/**
 * Servlet para tratar dos dados das partidas
 * Tem atividade assíncronas servindo como registro para requisições assíncronas
 */
@WebServlet("/usuario")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
        maxFileSize = 1024 * 1024 * 10, // 10 MB
        maxRequestSize = 1024 * 1024 * 100 // 100 MB
)
public class JogadorController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @EJB
    private JogadorRepository jogadorRepository;

    @EJB
    private PartidasRepository partidaRepository;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public JogadorController() {
        super();
    }

    /**
     * O GET é usado para logout de usuário ou para exibir a página de login
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

        if (request.getSession().getAttribute("jogador") != null && (action == null || !action.equals("info")))
            response.sendRedirect(request.getContextPath() + "/usuario?action=info");
        else 
            request.getRequestDispatcher("/WEB-INF/usuario.jsp").forward(request, response);    
    }

    /**
     * O POST pode realizar o cadastro do usuário (se action=cadastro) ou fazer
     * login (caso contrário)
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        String uuid = request.getParameter("uuid");

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if (action != null && (action.equals("cadastro") || action.equals("info"))) {
            String name = request.getParameter("name");

            Part avtarPart = request.getPart("avatar");
            String fileName = avtarPart.getSubmittedFileName();
            String encodedString = null;

            if (!fileName.isEmpty()) {
                encodedString = Base64.getEncoder().encodeToString(IOUtils.toByteArray(avtarPart.getInputStream()));
            }

            try {
                Jogador previousInfo = this.jogadorRepository.findById(email);

                if (action.equals("cadastro") && previousInfo != null) {
                    throw new Exception("Email já cadastrado!");
                } else if (action.equals("info") && encodedString == null) {
                    encodedString = previousInfo.getAvatar();
                }

                String encriptedPassword = BCrypt.hashpw(request.getParameter("password"), BCrypt.gensalt(12));
                Jogador jogador = new Jogador(name, email, encriptedPassword, encodedString);

                this.jogadorRepository.save(jogador);

                request.getSession().setAttribute("jogador", jogador);

                if (uuid != null) {
                    Partida partida = partidaRepository.findById(uuid);

                    if (partida.getJogador() == null) {
                        partida.setJogador(jogador);
                        this.partidaRepository.save(partida);
                    }
                }

                if (uuid != null) {
                    request.setAttribute("partida", request.getSession().getAttribute(uuid));
                    request.getRequestDispatcher("/partidas").forward(request, response);
                    return;
                }
            } catch (Exception e) {
                request.setAttribute("message", e.getMessage());
                this.doGet(request, response);
                return;
            }
        }

        try {
            Jogador jogador = this.jogadorRepository.findById(email);

            if (!BCrypt.checkpw(password, jogador.getPassword()))
                throw new NoResultException("Email ou password não conferem");

            request.getSession().setAttribute("jogador", jogador);

            if (uuid != null) {
                Partida partida = partidaRepository.findById(uuid);

                if (partida.getJogador() == null)
                    partida.setJogador(jogador);

                this.partidaRepository.save(partida);
            }

            response.sendRedirect(request.getContextPath() + "/home" + (uuid != null ? ("?uuid=" + uuid) : ""));
        } catch (NoResultException nre) {
            request.setAttribute("message", "Usuário não encontrado ou senha incorreta!");
            this.doGet(request, response);
        }

    }

}
