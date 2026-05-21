package model;

/**
 * Representa um registro de frequência de um aluno.
 *
 * @author __________________
 * @version 1.0
 */
public class FrequenciaModel {

    private int frequenciaId;
    private String data;
    private String horaEntrada;
    private String horaSaida;
    private int idAluno;

    /**
     * Construtor da classe FrequenciaModel.
     */
    public FrequenciaModel(
            int frequenciaId,
            String data,
            String horaEntrada,
            String horaSaida,
            int idAluno
    ) {
        this.frequenciaId = frequenciaId;
        this.data = data;
        this.horaEntrada = horaEntrada;
        this.horaSaida = horaSaida;
        this.idAluno = idAluno;
    }


    public int getFrequenciaId() {
        return frequenciaId;
    }
    public String getData() {
        return data;
    }
    public String getHoraEntrada() {
        return horaEntrada;
    }
    public String getHoraSaida() {
        return horaSaida;
    }
    public int getIdAluno() {
        return idAluno;
    }
    public String exibirInformacoes() {
        return "Frequência do aluno ID " + idAluno +
                " | Data: " + data +
                " | Entrada: " + horaEntrada;
    }
}
