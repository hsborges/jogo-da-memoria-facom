package br.ufms.facom.jogo.repositories;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.ufms.facom.jogo.entities.Partida;
import br.ufms.facom.jogo.entities.Ranking;

@Stateless
public class RankingRepository {

    @PersistenceContext(unitName = "pu-sqlite")
    private EntityManager em;

    public Ranking findById(String uuid) {
        return this.em.find(Ranking.class, uuid);
    }

    public Ranking findByPartida(Partida partida) {
        return this.findById(partida.getUuid());
    }

    public List<Ranking> getTopPartidas() {
        return this.getTopPartidas(10);
    }

    public List<Ranking> getTopPartidas(int limit) {
        return this.em.createQuery("SELECT r FROM Ranking r", Ranking.class)
                .setMaxResults(limit)
                .getResultList();
    }

    public Ranking save(Ranking instance) throws Exception {
        if (!instance.getPartida().isFinalizada())
            throw new Exception("Somente partidas finalizadas podem entrar no ranking!");
        else if (instance.getJogador() == null)
            throw new Exception("Somente partidas de usuários logados entram no ranking!");

        Object position = this.em
                .createQuery("SELECT COUNT(r) FROM Ranking r WHERE r.partida.pontuacao > :pontuacao")
                .setParameter("pontuacao", instance.getPartida().getPontuacao())
                .getSingleResult();

        instance.setPosicao((Long) position);

        this.em.persist(instance);
        this.em.createQuery(
                "UPDATE Ranking r SET r.posicao = r.posicao + 1 WHERE r.partida.pontuacao < :pontuacao")
                .setParameter("pontuacao", instance.getPartida().getPontuacao())
                .executeUpdate();

        return instance;
    }

}
