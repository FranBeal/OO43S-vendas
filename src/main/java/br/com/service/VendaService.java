package br.com.service;

import br.com.dao.VendaDao;
import br.com.vo.RelatorioDeVendasVo;
import br.com.vo.RelatorioFinanceiroVo;
import jakarta.persistence.EntityManager;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class VendaService {
    private VendaDao vendasDAO;

    public VendaService(EntityManager em){
        this.vendasDAO = new VendaDao(em);
    }

    public BigDecimal retornaValorTotalVendido(LocalDate dataIni, LocalDate dataFim){
        return this.vendasDAO.retornaValorTotalVendidoEmUmPeriodo(dataIni, dataFim);
    }

    public List<RelatorioDeVendasVo> retornaRelatorioDeVendas(){
        return this.vendasDAO.relatorioDeVendas();
    }

    public List<RelatorioFinanceiroVo> retornaRelatorioFinanceiro(){
        return this.vendasDAO.relatorioFinanceiro();
    }

}
