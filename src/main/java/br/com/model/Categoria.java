package br.com.model;

import jakarta.persistence.*;

@Entity // Define que a classe é uma entidade JPA, ou seja, será mapeada para uma tabela no banco de dados.
@Table(name = "categorias")
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    // Construtor padrão sem parâmetros.
    // Necessário para que o JPA possa instanciar a classe ao recuperar dados do BD.
    public Categoria() {}

    public Categoria(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        // StringBuilder é utilizado para construir a string de forma eficiente.
        StringBuilder sb = new StringBuilder();
        sb.append("Categoria{")
                .append("id=").append(id)
                .append(", nome=").append(nome)
                .append('}');
        return sb.toString();
    }

}
