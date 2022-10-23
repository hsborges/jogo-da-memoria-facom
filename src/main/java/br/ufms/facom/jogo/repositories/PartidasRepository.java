package br.ufms.facom.jogo.repositories;

import java.lang.reflect.InvocationTargetException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.beanutils.BeanUtils;

import br.ufms.facom.jogo.entities.Partida;

@Stateless
public class PartidasRepository {

    @PersistenceContext(unitName = "pu-sqlite")
    private EntityManager em;

    
    public Partida findById(String uuid) {
        return this.em.find(Partida.class, uuid);
    }

    
    public Partida save(Partida instance) {
        Partida partida = this.em.find(Partida.class, instance.getUuid());

        if (partida != null) {
            try {
                BeanUtils.copyProperties(partida, instance);
                this.em.merge(partida);
                return partida;
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
