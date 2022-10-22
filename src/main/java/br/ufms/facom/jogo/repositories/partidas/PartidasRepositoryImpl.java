package br.ufms.facom.jogo.repositories.partidas;

import java.lang.reflect.InvocationTargetException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import org.apache.commons.beanutils.BeanUtils;

import br.ufms.facom.jogo.entities.Partida;

@Stateless
public class PartidasRepositoryImpl implements PartidasRepository {

    private EntityManager em = Persistence.createEntityManagerFactory("pu-sqlite").createEntityManager();

    @Override
    public Partida findById(String uuid) {
        return this.em.find(Partida.class, uuid);
    }

    @Override
    public Partida save(Partida instance) {

        Partida partida = this.em.find(Partida.class, instance.getUuid());

        if (partida != null) {
            try {
                BeanUtils.copyProperties(partida, instance);
                this.em.getTransaction().begin();
                this.em.merge(partida);
                this.em.getTransaction().commit();

                return partida;
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        
        
        this.em.getTransaction().begin();
        this.em.persist(instance);
        this.em.getTransaction().commit();

        return instance;
    }

}
