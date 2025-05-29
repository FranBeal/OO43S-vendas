package br.com.dao;

import br.com.model.Categoria;
import jakarta.persistence.EntityManager;

import java.util.List;

// Classe DAO (Data Access Object) para gerenciar as operações de
// persistência da entidade Categoria.
public class CategoriaDao {
    // Instância de EntityManager, usada para realizar as
    // operações no banco de dados.
    private EntityManager em;

    // Construtor que recebe um EntityManager como parâmetro.
    public CategoriaDao(EntityManager em) {
        this.em = em;
    }

    public void cadastrar(Categoria categoria) {
        this.em.getTransaction().begin(); // Inicia uma transação.
        this.em.persist(categoria);  // Persiste a entidade no banco de dados.
        this.em.getTransaction().commit(); // Finaliza a transação e salva as alterações.
    }

    // Metodo para atualizar uma categoria existente no banco de dados.
    public void atualizar(Categoria categoria) {
        this.em.getTransaction().begin();// Inicia uma transação.
        this.em.merge(categoria);// Atualiza a entidade no banco de dados.
        this.em.getTransaction().commit();// Finaliza a transação e salva as alterações.
    }

    // Metodo para remover uma categoria do banco de dados.
    public void remover(Categoria categoria) {
        this.em.getTransaction().begin();// Inicia uma transação.
        this.em.remove(categoria);// Remove a entidade do banco de dados.
        this.em.getTransaction().commit();// Finaliza a transação e salva as alterações.
    }

    // Metodo para buscar uma categoria pelo seu ID.
    public Categoria buscarPorId(Long id) {
        return this.em.find(Categoria.class, id);
    }

    // Metodo para buscar todas as categorias no banco de dados.
    public List<Categoria> buscarTodos() {
        String jpql = "SELECT c FROM Categoria c";
        return this.em.createQuery(jpql, Categoria.class).getResultList();
    }

    // Metodo para buscar categorias pelo nome.
    public List<Categoria> buscarPorNome(String nome) {
        String jpql = "SELECT c FROM Categoria c WHERE c.nome = :nome";
        return em.createQuery(jpql, Categoria.class)
                .setParameter("nome", nome)
                .getResultList();
    }
}
