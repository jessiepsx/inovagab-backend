package br.com.fiap.inovagab.model;

/**
 * Perfis de acesso do sistema InovaGAB.
 *
 * OPERADOR -> cadastra/consulta suas próprias ideias
 * GESTOR   -> consulta/prioriza/aprova ideias e gerencia projetos
 * LIDER    -> gerencia estratégia, consulta projetos e dashboard
 */
public enum Perfil {
    OPERADOR,
    GESTOR,
    LIDER
}
