package model;

/**
 * Representa uma aula coletiva cadastrada no sistema.
 *
 * @author __________________
 * @version 1.0
 */
public class AulaModel {

    private int aulaId;
    private String nome;
    private String descricao;
    private String horario;
    private int capacidade;
    private int duracao;
    private int idInstrutor;

    /**
     * Construtor da classe AulaModel.
     */
    public AulaModel(
            int aulaId,
            String nome,
            String descricao,
            String horario,
            int capacidade,
            int duracao,
            int idInstrutor
    ) {
        this.aulaId = aulaId;
        this.nome = nome;
        this.descricao = descricao;
        this.horario = horario;
        this.capacidade = capacidade;
        this.duracao = duracao;
        this.idInstrutor = idInstrutor;
    }


    public int getAulaId() {
        return aulaId;
    }
    public String getNome() {
        return nome;
    }
    public String getDescricao() {
        return descricao;
    }
    public String getHorario() {
        return horario;
    }
    public int getCapacidade() {
        return capacidade;
    }
    public int getDuracao() {
        return duracao;
    }
    public int getIdInstrutor() {
        return idInstrutor;
    }
    public String exibirInformacoes() {

        return nome
                + " | "
                + horario
                + " | "
                + duracao
                + " min";
    }
}
