package model;

/**
 * Representa as informações básicas de um relatório.
 *
 * @author __________________
 * @version 1.0
 */
public class RelatorioModel {

    private String titulo;
    private String descricao;

    /**
     * Construtor da classe RelatorioModel.
     */
    public RelatorioModel(String titulo, String descricao) {
        this.titulo = titulo;
        this.descricao = descricao;
    }

    public String getTitulo() {
        return titulo;
    }
    public String getDescricao() {
        return descricao;
    }
    public String exibirCabecalho() {
        return "\n|--- " + titulo + " ---|\n" + descricao + "\n";
    }
}
