package br.ufms.facom.jogo.repositories;

import java.lang.reflect.InvocationTargetException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.beanutils.BeanUtils;

import br.ufms.facom.jogo.entities.Jogador;

@Stateless
public class JogadorRepository {

    @PersistenceContext(unitName = "pu-sqlite")
    private EntityManager em;

    public Jogador findById(String email) {
        return this.em.find(Jogador.class, email);
    }

    public boolean hasJogador(String email) {
        Long count = (Long) this.em.createQuery("SELECT COUNT(j) FROM Jogador j WHERE j.email = :email")
                .setParameter("email", email)
                .getSingleResult();

        return count > 0;
    }

    public Jogador save(Jogador instance) {
        Jogador jogador = this.findById(instance.getEmail());

        if (jogador != null) {
            try {
                BeanUtils.copyProperties(jogador, instance);
                this.em.merge(jogador);
                return jogador;
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
                return null;
            }
        } else {
            this.em.persist(instance);
            return instance;
        }
    }
}
