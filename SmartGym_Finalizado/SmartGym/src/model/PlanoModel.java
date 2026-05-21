package model;

/**
 * Representa um plano de assinatura da academia.
 *
 * @author __________________
 * @version 1.0
 */
public class PlanoModel {

    private int planoId;
    private String nome;
    private String descricao;
    private double valor;
    private int duracaoMeses;
    private String beneficios;

    /**
     * Construtor da classe PlanoModel.
     */
    public PlanoModel(
            int planoId,
            String nome,
            String descricao,
            double valor,
            int duracaoMeses,
            String beneficios
    ) {
        this.planoId = planoId;
        this.nome = nome;
        this.descricao = descricao;
        this.valor = valor;
        this.duracaoMeses = duracaoMeses;
        this.beneficios = beneficios;
    }

    public int getPlanoId() {
        return planoId;
    }
    public String getNome() {
        return nome;
    }
    public String getDescricao() {
        return descricao;
    }
    public double getValor() {
        return valor;
    }
    public int getDuracaoMeses() {
        return duracaoMeses;
    }
    public String getBeneficios() {
        return beneficios;
    }
    public String exibirInformacoes() {
        return nome + " | R$ " + valor + " | " + duracaoMeses + " meses";
    }
}
