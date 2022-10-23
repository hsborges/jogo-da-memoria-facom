package br.ufms.facom.jogo.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import br.ufms.facom.jogo.entities.Jogador;
import br.ufms.facom.jogo.entities.Partida;
import br.ufms.facom.jogo.entities.Ranking;
import br.ufms.facom.jogo.repositories.PartidasRepository;
import br.ufms.facom.jogo.repositories.RankingRepository;

/**
 * Servlet para tratar dos dados das partidas
 * Tem atividade assíncronas servindo como registro para requisições assíncronas
 */
@WebServlet("/partidas")
public class PartidaController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @EJB
    private PartidasRepository partidasRepo;

    @EJB
    private RankingRepository rankingRepo;

    private ObjectWriter jsonWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();

    /**
     * @see HttpServlet#HttpServlet()
     */
    public PartidaController() {
        super();
    }

    /**
     * O GET pode retornar um uuid de partida novo ou dados existentes se um for
     * fornecido.
     * Ele responde com o formato JSON (pois são chamadas assincronas)
     */

    @Override
    @SuppressWarnings("unchecked")
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        if (req.getParameter("uuid") == null) {
            String uuid = UUID.randomUUID().toString();
            resp.getWriter().write("{ \"uuid\": \"" + UUID.randomUUID() + "\" }");

            if (req.getSession().getAttribute("uuids") == null)
                req.getSession().setAttribute("uuids", new ArrayList<String>());

            List<String> uuids = (List<String>) req.getSession().getAttribute("uuids");
            uuids.add(uuid);
        } else {
            Partida partida = (Partida) req.getSession().getAttribute(req.getParameter("uuid"));
            if (partida != null)
                resp.getWriter().write(jsonWriter.writeValueAsString(partida));
            else
                resp.getWriter().write("{ \"uuid\": \"" + req.getParameter("uuid") + "\" }");
        }
    }

    /**
     * O POST recebe os dados de uma partida e salva no banco
     * Se a partida for finalizada salva ela no ranking
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Partida partida = null;

        if (request.getAttribute("partida") != null) {
            partida = (Partida) request.getAttribute("partida");
        } else {
            if (request.getParameter("uuid") == null)
                throw new ServletException("UUID ou partida é obrigatório!");

            String uuid = request.getParameter("uuid");
            Long tempo = Long.parseLong(request.getParameter("tempo"));
            Long jogadas = Long.parseLong(request.getParameter("jogadas"));
            Long acertos = Long.parseLong(request.getParameter("acertos"));
            String ordemAcertos = request.getParameter("ordemAcertos");
            Long pontuacao = Long.parseLong(request.getParameter("pontuacao"));

            Jogador jogador = (Jogador) request.getSession().getAttribute("jogador");
            partida = this.partidasRepo
                    .save(new Partida(uuid, jogador, tempo, acertos, ordemAcertos, jogadas, pontuacao));
        }

        if (partida.isFinalizada()) {
            System.out.println(partida); 
            try {
                this.rankingRepo.save(new Ranking(partida.getJogador(), partida));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        request.getSession().setAttribute(partida.getUuid(), partida);
        response.sendRedirect(request.getContextPath() + "/home?uuid=" + partida.getUuid());
    }

}
