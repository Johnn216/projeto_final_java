package model;

import jakarta.persistence.*;
import java.util.List;

public class Repositorio {

    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("LavanderiaPU");

    public void salvar(ItemLavanderia item) throws Exception {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            // Se o item já tem ID, o Hibernate atualiza. Se não tem, ele cria.
            if (item.getId() == null) {
                em.persist(item);
            } else {
                em.merge(item); // Atualiza dados existentes
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    // --- NOVO MÉTODO: BUSCAR UM SÓ ---
    public ItemLavanderia buscarPorId(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            return em.find(ItemLavanderia.class, id);
        } finally {
            em.close();
        }
    }

    // --- NOVO MÉTODO: EXCLUIR ---
    public void excluir(Long id) throws Exception {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            // Primeiro busca o item para garantir que existe
            ItemLavanderia item = em.find(ItemLavanderia.class, id);
            if (item != null) {
                em.remove(item); // Remove do banco
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public List<ItemLavanderia> listarTodos() {
        EntityManager em = emf.createEntityManager();
        try {
            return em.createQuery("FROM ItemLavanderia", ItemLavanderia.class).getResultList();
        } finally {
            em.close();
        }
    }
}