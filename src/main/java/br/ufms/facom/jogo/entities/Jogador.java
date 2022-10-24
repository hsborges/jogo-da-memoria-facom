package br.ufms.facom.jogo.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Entity implementation class for Entity: Jogador
 *
 */
@Entity
@Table(name = "jogadores", indexes = @Index(columnList = "email"))
public class Jogador implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "name", nullable = false)
    private String name;

    @Id
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Lob
    @Column(name = "avatar")
    private String avatar;

    @Column(name = "created_at", nullable = true, columnDefinition = "DEFAULT CURRENT_TIMESTAMP")
    private Date createdAt = new Date();

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "jogador_id")
    private List<Partida> partidas;

    public Jogador() {
        super();
    }

    public Jogador(String name, String email, String password) {
        super();
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public Jogador(String name, String email, String password, String avatar) {
        super();
        this.name = name;
        this.email = email;
        this.password = password;
        this.avatar = avatar;
    }

    public Jogador(String name, String email, String password, String avatar, Date createdAt) {
        super();
        this.name = name;
        this.email = email;
        this.password = password;
        this.avatar = avatar;
        this.createdAt = createdAt;
    }

    public String getAvatar() {
        return avatar;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public List<Partida> getPartidas() {
        return partidas;
    }

    public String getPassword() {
        return password;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPartidas(List<Partida> partidas) {
        this.partidas = partidas;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Jogador [name=" + name + ", email=" + email + ", password=" + password + ", avatar=" + avatar
                + ", createdAt=" + createdAt + ", partidas=" + partidas + "]";
    }
    
    

}
