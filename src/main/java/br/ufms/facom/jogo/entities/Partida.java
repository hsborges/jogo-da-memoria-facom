package br.ufms.facom.jogo.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Entity implementation class for Entity: Partida
 *
 */
@Entity
@Table(name = "partidas")
public class Partida implements Serializable {
	private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "uuid", nullable = false)
    private String uuid;

    @JsonIgnore
    @JoinColumn(name = "jogador_id")
	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	private Jogador jogador;

	@Column(name = "acertos", nullable = false)
    private Long acertos;
	
	@JsonProperty("ordem_acertos")
	@Column(name = "ordem_acertos", nullable = false)
    private String ordemAcertos;

	@Column(name = "tempo", nullable = false)
	private Long tempo;

    @Column(name = "jogadas", nullable = false)
    private Long jogadas;

    @Column(name = "pontuacao", nullable = false)
    private Long pontuacao;

	public Partida() {
		super();
	}

    public Partida(String uuid, Jogador jogador, Long tempo, Long acertos, String ordemAcertos, Long jogadas, Long pontuacao) {
        super();
        this.uuid = uuid;
        this.jogador = jogador;
        this.tempo = tempo;
        this.jogadas = jogadas;
        this.acertos = acertos;
        this.ordemAcertos = ordemAcertos;
        this.pontuacao = pontuacao;
    }

    public Long getAcertos() {
        return acertos;
    }

    public Long getJogadas() {
        return jogadas;
    }

    public Jogador getJogador() {
        return jogador;
    }

	public String getOrdemAcertos() {
        return ordemAcertos;
    }


	public Long getPontuacao() {
        return pontuacao;
    }

    public Long getTempo() {
        return tempo;
    }

    public String getUuid() {
        return uuid;
    }

    public boolean isFinalizada() {
        return this.acertos == 12;
    }



    public void setAcertos(Long acertos) {
        this.acertos = acertos;
    }

    public void setJogadas(Long jogadas) {
        this.jogadas = jogadas;
    }

    public void setJogador(Jogador jogador) {
        this.jogador = jogador;
    }

    public void setOrdemAcertos(String ordemAcertos) {
        this.ordemAcertos = ordemAcertos;
    }

    public void setPontuacao(Long pontuacao) {
        this.pontuacao = pontuacao;
    }

    public void setTempo(Long tempo) {
        this.tempo = tempo;
    }
    
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public String toString() {
        return "Partida [uuid=" + uuid + ", jogador=" + jogador + ", acertos=" + acertos + ", tempo=" + tempo
                + ", jogadas=" + jogadas + ", pontuacao=" + pontuacao + "]";
    }
}
