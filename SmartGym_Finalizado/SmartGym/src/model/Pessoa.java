package model;

/**
 * Classe abstrata base para pessoas do sistema, como alunos e instrutores.
 *
 * @author __________________
 * @version 1.0
 */
public abstract class Pessoa {

    protected String cpf;
    protected String primeiroNome;
    protected String nomeMeio;
    protected String ultimoNome;
    protected String telefone;

    /**
     * Construtor da classe Pessoa.
     */
    public Pessoa(String cpf, String primeiroNome, String nomeMeio,
                  String ultimoNome, String telefone) {
        this.cpf = cpf;
        this.primeiroNome = primeiroNome;
        this.nomeMeio = nomeMeio;
        this.ultimoNome = ultimoNome;
        this.telefone = telefone;
    }

    public String getCpf() { return cpf; }
    public String getPrimeiroNome() { return primeiroNome; }
    public String getNomeMeio() { return nomeMeio; }
    public String getUltimoNome() { return ultimoNome; }
    public String getTelefone() { return telefone; }
    public abstract String exibirInformacoes();
}
