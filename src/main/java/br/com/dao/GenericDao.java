package br.com.dao;

import br.com.exception.DataAccessException;
import jakarta.persistence.EntityManager;

import java.util.List;

// Classe genérica para operações CRUD no banco de dados.
// <T>: parametro de tipo genérico, representa um tipo que será definido quando a classe for instanciada ou herdade.
// Esse T que pode ser qualquer letra ou palavra, mas por convenção usa-se T para "Type".
// O <T> indica que essa classe é genérica e pode trabalhar com qualquer tipo de entidade. Isso permite que a mesma
// implementação seja reutilizada para diferentes classes de entidade sem precisar reescrever o código para cada uma.
public abstract class GenericDao<T> {
    protected EntityManager em;
    private Class<T> entityClass;

    public GenericDao(EntityManager em, Class<T> entityClass) {
        this.em = em;
        this.entityClass = entityClass;
    }

    public void cadastrar(T entity) {
        try {
            em.getTransaction().begin();// Inicia a transação.
            em.persist(entity);// Persiste a entidade no banco de dados.
            em.getTransaction().commit();// Confirma a transação.
        }catch (Exception e) {
            em.getTransaction().rollback();// Reverte a transação em caso de erro.

            // Lança uma exceção personalizada com detalhes do erro.
            throw new DataAccessException("Erro ao cadastrar a entidade: " +
                    entity.getClass().getSimpleName(), e);
        }
    }

    public void atualizar(T entity) {
        try {
            em.getTransaction().begin();
            em.merge(entity);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new DataAccessException("Erro ao atualizar a entidade: " +
                    entity.getClass().getSimpleName(), e);
        }
    }

    public void remover(T entity) {
        try {
            em.getTransaction().begin();
            em.remove(entity);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new DataAccessException("Erro ao excluir a entidade: " + entity.getClass().getSimpleName(), e);
        }
    }

    public T buscarPorId(Long id) {
        try {
            return em.find(entityClass, id);// Busca a entidade pelo ID.
        } catch (Exception e) {
            throw new DataAccessException("Erro ao buscar o id: "+ id +
                    " da entidade: "+ entityClass.getSimpleName(), e);
        }
    }

    public List<T> buscarTodos() {
        try {
            // Monta uma consulta JPQL para buscar todas as entidades.
            String jpql = "SELECT e FROM " + entityClass.getSimpleName() + " e";

            return em.createQuery(jpql, entityClass).getResultList();

        } catch (Exception e) {
            throw new DataAccessException("Erro ao buscar todos de: "+
                    entityClass.getSimpleName(), e);
        }
    }
}
