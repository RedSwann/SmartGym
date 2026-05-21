package utils;

/**
 * Classe utilitária responsável por validações simples usadas no sistema.
 *
 * @author __________________
 * @version 1.0
 */
public class Validacao {

    // --------------------------
    // VALIDAR CPF
    // --------------------------
    /**
     * Valida se o CPF informado possui formato simples aceito pelo sistema.
     *
     * @return resultado da operação
     */
    public static boolean cpfValido(String cpf) {

        // apenas números
        if (!cpf.matches("\\d+")) {
            return false;
        }

        // tamanho
        return cpf.length() == 11;
    }

    // --------------------------
    // VALIDAR NOME
    // --------------------------
    /**
     * Valida se o nome informado possui tamanho e caracteres aceitos.
     *
     * @return resultado da operação
     */
    public static boolean nomeValido(String nome) {

        // restringe o nome em no minimo 2 letras
        if (nome.length() < 2) {
            return false;
        }

        // limita o nome a conter apenas letras e espaço
        return nome.matches("[a-zA-Z ]+");
    }

    // --------------------------
    // VALIDAR TELEFONE
    // --------------------------
    /**
     * Valida se o telefone informado possui formato simples aceito pelo sistema.
     *
     * @return resultado da operação
     */
    public static boolean telefoneValido(String telefone) {

        // limita a apenas números
        if (!telefone.matches("\\d+")) {
            return false;
        }

        return telefone.length() >= 10 &&
                telefone.length() <= 11;
    }

    // --------------------------
    // VALIDAR TEXTO MÍNIMO
    // --------------------------
    /**
     * Verifica se o texto possui o tamanho mínimo informado.
     *
     * @return resultado da operação
     */
    public static boolean tamanhoMinimo(String texto, int min) {

        return texto.length() >= min;
    }
}
