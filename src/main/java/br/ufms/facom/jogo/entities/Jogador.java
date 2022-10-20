package br.ufms.facom.jogo.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Jogador
 *
 */
@Entity
@Table(name = "jogadores", indexes = @Index(columnList = "email"))
public class Jogador implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Lob
    @Column(name = "avatar")
    private String avatar;

    @Column(name = "created_at", nullable = true, columnDefinition = "DEFAULT CURRENT_TIMESTAMP")
    private Date createdAt = new Date();

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

}
