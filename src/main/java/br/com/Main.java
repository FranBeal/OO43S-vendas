package br.com;

import br.com.model.Categoria;
import br.com.service.CategoriaService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.List;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static EntityManager em;

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("PostgresPU");
        em = emf.createEntityManager();

        CategoriaService categoriaService = new CategoriaService(em);
        boolean continuar = true;

        while (continuar) {
            System.out.println("----- MENU -----");
            System.out.println("1. Cadastrar Categoria");
            System.out.println("2. Alterar Categoria");
            System.out.println("3. Excluir Categoria");
            System.out.println("4. Consultar Categoria por ID");
            System.out.println("5. Listar todas as Categorias");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");

            int opcao = scanner.nextInt();
            scanner.nextLine(); // Limpar o buffer

            switch (opcao) {
                case 1 -> cadastrarCategoria(categoriaService);
                case 2 -> alterarCategoria(categoriaService);
                case 3 -> excluirCategoria(categoriaService);
                case 4 -> consultarCategoriaPorId(categoriaService);
                case 5 -> listarCategorias(categoriaService);
                case 0 -> continuar = false;
                default -> System.out.println("Opção inválida!");
            }
        }
        em.close();
        emf.close();
        System.out.println("Programa encerrado.");
    }

    private static void cadastrarCategoria(CategoriaService categoriaService) {
        System.out.print("Digite o nome da categoria: ");
        String nome = scanner.nextLine();
        Categoria categoria = new Categoria(nome);
        categoriaService.inserir(categoria);
        System.out.println("Categoria cadastrada com sucesso!");
    }

    private static void alterarCategoria(CategoriaService categoriaService) {
        System.out.print("Digite o ID da categoria a ser alterada: ");
        Long id = scanner.nextLong();
        scanner.nextLine();
        Categoria categoria = categoriaService.buscarCategoriaPorId(id);
        if (categoria != null) {
            System.out.print("Digite o novo nome da categoria: ");
            categoria.setNome(scanner.nextLine());
            categoriaService.alterar(categoria);
            System.out.println("Categoria alterada com sucesso!");
        }else {
            System.out.println("Categoria não encontrada.");
        }
    }

    private static void excluirCategoria(CategoriaService categoriaService) {
        System.out.print("Digite o ID da categoria a ser excluída: ");
        Long id = scanner.nextLong();
        scanner.nextLine();
        Categoria categoria = categoriaService.buscarCategoriaPorId(id);
        if (categoria != null) {
            categoriaService.excluir(categoria);
            System.out.println("Categoria excluída com sucesso!");
        } else {
            System.out.println("Categoria não encontrada.");
        }
    }

    private static void consultarCategoriaPorId(CategoriaService categoriaService) {
        System.out.print("Digite o ID da categoria: ");
        Long id = scanner.nextLong();
        scanner.nextLine();
        Categoria categoria = categoriaService.buscarCategoriaPorId(id);
        System.out.println(categoria != null ? categoria.toString() : "Categoria não encontrada.");
    }

    private static void listarCategorias(CategoriaService categoriaService) {
        List<Categoria> categorias = categoriaService.buscarTodosAsCategorias();
        categorias.forEach(System.out::println);
    }

}