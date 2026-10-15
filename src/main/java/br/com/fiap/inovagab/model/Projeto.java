package br.com.fiap.inovagab.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection = "projetos")
public class Projeto {

    @Id
    private String id;

    private String nome;
    private String descricao;
    private String estrategiaId;
    private String etapa;
    private String status;
    private Double investimento;
    private LocalDate prazo;
    private Double retornoFinanceiro;
    private Double progresso;

    public Projeto() {
    }

    public Projeto(String id, String nome, String descricao, String estrategiaId,
                   String etapa, String status, Double investimento,
                   LocalDate prazo, Double retornoFinanceiro, Double progresso) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.estrategiaId = estrategiaId;
        this.etapa = etapa;
        this.status = status;
        this.investimento = investimento;
        this.prazo = prazo;
        this.retornoFinanceiro = retornoFinanceiro;
        this.progresso = progresso;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getEstrategiaId() {
        return estrategiaId;
    }

    public void setEstrategiaId(String estrategiaId) {
        this.estrategiaId = estrategiaId;
    }

    public String getEtapa() {
        return etapa;
    }

    public void setEtapa(String etapa) {
        this.etapa = etapa;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getInvestimento() {
        return investimento;
    }

    public void setInvestimento(Double investimento) {
        this.investimento = investimento;
    }

    public LocalDate getPrazo() {
        return prazo;
    }

    public void setPrazo(LocalDate prazo) {
        this.prazo = prazo;
    }

    public Double getRetornoFinanceiro() {
        return retornoFinanceiro;
    }

    public void setRetornoFinanceiro(Double retornoFinanceiro) {
        this.retornoFinanceiro = retornoFinanceiro;
    }

    public Double getProgresso() {
        return progresso;
    }

    public void setProgresso(Double progresso) {
        this.progresso = progresso;
    }
}