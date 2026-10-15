package br.com.fiap.inovagab.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection = "resultados")
public class Resultado {

    @Id
    private String id;

    private String projetoId;
    private LocalDate data;
    private String descricao;
    private Double investimento;
    private Double retornoFinanceiro;
    private Double lucro;
    private Double aumentoProdutividade;
    private Double roi;

    public Resultado() {
    }

    public Resultado(String id, String projetoId, LocalDate data,
                     String descricao, Double investimento,
                     Double retornoFinanceiro, Double lucro,
                     Double aumentoProdutividade, Double roi) {
        this.id = id;
        this.projetoId = projetoId;
        this.data = data;
        this.descricao = descricao;
        this.investimento = investimento;
        this.retornoFinanceiro = retornoFinanceiro;
        this.lucro = lucro;
        this.aumentoProdutividade = aumentoProdutividade;
        this.roi = roi;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProjetoId() {
        return projetoId;
    }

    public void setProjetoId(String projetoId) {
        this.projetoId = projetoId;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Double getInvestimento() {
        return investimento;
    }

    public void setInvestimento(Double investimento) {
        this.investimento = investimento;
    }

    public Double getRetornoFinanceiro() {
        return retornoFinanceiro;
    }

    public void setRetornoFinanceiro(Double retornoFinanceiro) {
        this.retornoFinanceiro = retornoFinanceiro;
    }

    public Double getLucro() {
        return lucro;
    }

    public void setLucro(Double lucro) {
        this.lucro = lucro;
    }

    public Double getAumentoProdutividade() {
        return aumentoProdutividade;
    }

    public void setAumentoProdutividade(Double aumentoProdutividade) {
        this.aumentoProdutividade = aumentoProdutividade;
    }

    public Double getRoi() {
        return roi;
    }

    public void setRoi(Double roi) {
        this.roi = roi;
    }
}