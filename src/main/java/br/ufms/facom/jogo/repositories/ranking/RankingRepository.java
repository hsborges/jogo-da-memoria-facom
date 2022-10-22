package br.ufms.facom.jogo.repositories.ranking;

import javax.ejb.Local;

import br.ufms.facom.jogo.entities.Partida;
import br.ufms.facom.jogo.entities.Ranking;

@Local
public interface RankingRepository {
    Ranking findById(String uuid);
    Ranking findByPartida(Partida partida);
    Ranking save(Ranking instance) throws Exception;
}
