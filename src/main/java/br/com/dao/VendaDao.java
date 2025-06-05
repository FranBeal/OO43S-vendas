package br.com.dao;

import br.com.vo.RelatorioDeVendasVo;
import br.com.vo.RelatorioFinanceiroVo;
import jakarta.persistence.EntityManager;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class VendaDao {
    private EntityManager em;

    public VendaDao(EntityManager em) {
        this.em = em;
    }

    public BigDecimal retornaValorTotalVendidoEmUmPeriodo(LocalDate dataIni, LocalDate dataFim) {
        String jpql = "select sum(p.valorTotal) from pedido p where p.data between :dataIni and :dataFim";
        BigDecimal total = em.createQuery(jpql, BigDecimal.class)
                .setParameter("dataIni", dataIni)
                .setParameter("dataFim", dataFim)
                .getSingleResult();
        if(total == null){
            return BigDecimal.ZERO;
        }
        return total;
    }

    public List<RelatorioDeVendasVo> relatorioDeVendas() {
        String jpql = "SELECT new br.com.vo.RelatorioDeVendasVo("
                + "produto.nome, "
                + "SUM(item.quantidade), "
                + "MAX(pedido.data)) "
                + "FROM Pedido pedido "
                + "JOIN pedido.itens item "
                + "JOIN item.produto produto "
                + "GROUP BY produto.nome "
                + "ORDER BY SUM(item.quantidade) DESC";
        return em.createQuery(jpql, RelatorioDeVendasVo.class)
                .getResultList();
    }

    public List<RelatorioFinanceiroVo> relatorioFinanceiro() {
        String jpql = "SELECT new br.com.vo.RelatorioFinanceiroVo("
                + "cliente.nome, "
                + "SUM(pedido.valorTotal)) "
                + "FROM Pedido pedido "
                + "JOIN pedido.cliente cliente "
                + "GROUP BY cliente.nome "
                + "ORDER BY SUM(pedido.valorTotal) DESC";
        return em.createQuery(jpql, RelatorioFinanceiroVo.class)
                .getResultList();
    }

}
