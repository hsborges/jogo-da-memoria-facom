package br.ufms.facom.jogo.repositories.ranking;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import br.ufms.facom.jogo.entities.Partida;
import br.ufms.facom.jogo.entities.Ranking;

@Stateless
public class RankingRepositoryImpl implements RankingRepository {

    private EntityManager em = Persistence.createEntityManagerFactory("pu-sqlite").createEntityManager();

    @Override
    public Ranking findById(String uuid) {
        return this.em.find(Ranking.class, uuid);
    }

    @Override
    public Ranking findByPartida(Partida partida) {
        return this.findById(partida.getUuid());
    }

    @Override
    public Ranking save(Ranking instance) throws Exception {
        if (!instance.getPartida().isFinalizada())
            throw new Exception("Somente partidas finalizadas podem entrar no ranking!");
        else if (instance.getJogador() == null)
            throw new Exception("Somente partidas de usuários logados entram no ranking!");

        Object position = this.em
                .createQuery("SELECT COUNT(r) FROM Ranking r WHERE r.partida.pontuacao > :pontuacao")
                .setParameter("pontuacao", instance.getPartida().getPontuacao())
                .getSingleResult();

        System.out.println(position);

        instance.setPosicao((Long) position);

        this.em.getTransaction().begin();
        this.em.persist(instance);
        this.em.createQuery(
                "UPDATE Ranking r SET r.posicao = r.posicao + 1 WHERE r.partida.pontuacao < :pontuacao")
                .setParameter("pontuacao", instance.getPartida().getPontuacao())
                .executeUpdate();
        this.em.getTransaction().commit();

        return instance;
    }

}
