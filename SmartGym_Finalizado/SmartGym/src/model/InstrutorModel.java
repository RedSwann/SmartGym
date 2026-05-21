package model;

/**
 * Representa um instrutor cadastrado na academia.
 *
 * @author __________________
 * @version 1.0
 */
public class InstrutorModel extends Pessoa {

    private String especialidade;
    private String horarioTrabalho;

    /**
     * Construtor da classe InstrutorModel.
     */
    public InstrutorModel(String cpf, String primeiroNome, String nomeMeio,
                          String ultimoNome, String telefone,
                          String especialidade, String horarioTrabalho) {

        super(cpf, primeiroNome, nomeMeio, ultimoNome, telefone);

        this.especialidade = especialidade;
        this.horarioTrabalho = horarioTrabalho;
    }

    public String getEspecialidade() { return especialidade; }
    public String getHorarioTrabalho() { return horarioTrabalho; }

    @Override
    public String exibirInformacoes() {
        return "Instrutor: " + primeiroNome + " " + ultimoNome;
    }
}
