package br.ufms.facom.jogo.repositories.partidas;

import javax.ejb.Local;

import br.ufms.facom.jogo.entities.Partida;

@Local
public interface PartidasRepository {
    Partida findById(String uuid);
    Partida save(Partida instance);
}
