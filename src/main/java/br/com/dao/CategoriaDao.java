package br.com.dao;

import br.com.exception.DataAccessException;
import br.com.model.Categoria;
import jakarta.persistence.EntityManager;

import java.util.List;

// Classe DAO (Data Access Object) para gerenciar as operações de
// persistência da entidade Categoria.
public class CategoriaDao extends GenericDao<Categoria>{
    // Instância de EntityManager, usada para realizar as
    // operações no banco de dados.
    private EntityManager em;

    // Construtor que recebe um EntityManager como parâmetro.
    public CategoriaDao(EntityManager em) {
        super(em, Categoria.class);
    }

    // Metodo para buscar categorias pelo nome.
    public List<Categoria> buscarPorNome(String nome) {
        try{
            String jpql = "SELECT c FROM Categoria c WHERE c.nome = :nome";
            return em.createQuery(jpql, Categoria.class)
                .setParameter("nome", nome)
                .getResultList();
        } catch (Exception e) {
            throw new DataAccessException("Erro ao buscar categorias por nome: " +
                    nome, e);
        }
    }
}
