package br.ufms.facom.jogo.entities;

import java.io.Serializable;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: Ranking
 *
 */
@Entity
@Table(name = "ranking")
public class Ranking implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = "posicao", nullable = false)
    private Long posicao;

    @JoinColumn(name = "jogador_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Jogador jogador;
    
    @Id
    @JoinColumn(name = "partida_id")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Partida partida;

    public Ranking() {
        super();
    }

    public Ranking(Jogador jogador, Partida partida) {
        super();
        this.jogador = jogador;
        this.partida = partida;
    }

    public Jogador getJogador() {
        return jogador;
    }

    public Partida getPartida() {
        return partida;
    }

    public Long getPosicao() {
        return posicao;
    }

    public void setJogador(Jogador jogador) {
        this.jogador = jogador;
    }

    public void setPartida(Partida partida) {
        this.partida = partida;
    }

    public void setPosicao(Long posicao) {
        this.posicao = posicao;
    }

}
