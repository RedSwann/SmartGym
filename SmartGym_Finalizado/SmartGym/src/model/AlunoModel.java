package model;

/**
 * Representa um aluno cadastrado na academia.
 *
 * @author __________________
 * @version 1.0
 */
public class AlunoModel extends Pessoa {

    private String dataNascimento;
    private String email;
    private String dataMatricula;
    private int planoId;

    /**
     * Construtor da classe AlunoModel.
     */
    public AlunoModel(String cpf, String primeiroNome, String nomeMeio, String ultimoNome,
                      String telefone, String email, String dataNascimento,
                      String dataMatricula, int planoId) {

        super(cpf, primeiroNome, nomeMeio, ultimoNome, telefone);

        this.email = email;
        this.dataNascimento = dataNascimento;
        this.dataMatricula = dataMatricula;
        this.planoId = planoId;
    }

    public String getDataNascimento() { return dataNascimento; }
    public String getEmail() { return email; }
    public String getDataMatricula() { return dataMatricula; }
    public int getPlanoId() { return planoId; }

    @Override
    public String exibirInformacoes() {
        return "Aluno: " + primeiroNome + " " + ultimoNome;
    }
}
