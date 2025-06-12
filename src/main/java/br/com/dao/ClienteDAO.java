package br.com.dao;

import br.com.model.Cliente;
import jakarta.persistence.EntityManager;

import java.util.List;

public class ClienteDAO extends GenericDao<Cliente>{

    private EntityManager em;

    public ClienteDAO(EntityManager em) {
        super(em, Cliente.class);
    }
}
