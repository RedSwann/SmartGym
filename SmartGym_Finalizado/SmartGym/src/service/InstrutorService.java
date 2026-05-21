package service;

import database.Conexao;
import model.InstrutorModel;
import utils.Validacao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

/**
 * Serviço responsável pelas operações de cadastro, consulta, atualização e exclusão de instrutores.
 *
 * @author __________________
 * @version 1.0
 */
public class InstrutorService {

    Scanner sc = new Scanner(System.in);

    /**
     * Cadastra um novo instrutor no banco de dados.
     */
    public void cadastrarInstrutor() {

        String sql = """
            INSERT INTO instrutor
            (
                ins_cpf,
                ins_primeiro_nome,
                ins_nome_meio,
                ins_ultimo_nome,
                ins_telefone,
                ins_especialidade,
                ins_horario_trabalho
            )
            VALUES (?, ?, ?, ?, ?, ?, ?)
        """;

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            System.out.println("\n|--- CADASTRAR INSTRUTOR ---|");

            System.out.print("CPF: ");
            String cpf = sc.nextLine();

            if (!Validacao.cpfValido(cpf)) {
                System.out.println("CPF inválido!");
                return;
            }

            if (cpfExiste(cpf)) {
                System.out.println("CPF já cadastrado!");
                return;
            }

            System.out.print("Primeiro Nome: ");
            String primeiroNome = sc.nextLine();

            if (!Validacao.nomeValido(primeiroNome)) {
                System.out.println("Nome inválido!");
                return;
            }

            System.out.print("Nome do Meio: ");
            String nomeMeio = sc.nextLine();

            System.out.print("Último Nome: ");
            String ultimoNome = sc.nextLine();

            if (!Validacao.nomeValido(ultimoNome)) {
                System.out.println("Último nome inválido!");
                return;
            }

            System.out.print("Telefone: ");
            String telefone = sc.nextLine();

            if (!Validacao.telefoneValido(telefone)) {
                System.out.println("Telefone inválido!");
                return;
            }

            System.out.print("Especialidade: ");
            String especialidade = sc.nextLine();

            System.out.print("Horário de Trabalho: ");
            String horario = sc.nextLine();

            InstrutorModel instrutor = new InstrutorModel(
                    cpf,
                    primeiroNome,
                    nomeMeio,
                    ultimoNome,
                    telefone,
                    especialidade,
                    horario
            );

            stmt.setString(1, instrutor.getCpf());
            stmt.setString(2, instrutor.getPrimeiroNome());
            stmt.setString(3, instrutor.getNomeMeio());
            stmt.setString(4, instrutor.getUltimoNome());
            stmt.setString(5, instrutor.getTelefone());
            stmt.setString(6, instrutor.getEspecialidade());
            stmt.setString(7, instrutor.getHorarioTrabalho());

            stmt.executeUpdate();

            System.out.println("\n" + instrutor.exibirInformacoes() + " cadastrado com sucesso!");

        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    /**
     * Lista os instrutores cadastrados.
     */
    public void listarInstrutores() {

        String sql = """
            SELECT *
            FROM instrutor
            ORDER BY id_instrutor
        """;

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            System.out.println("\n|--- LISTA DE INSTRUTORES ---|\n");

            while (rs.next()) {
                System.out.println(
                        rs.getInt("id_instrutor") + " | " +
                                rs.getString("ins_primeiro_nome") + " " +
                                rs.getString("ins_ultimo_nome") + " | " +
                                rs.getString("ins_cpf") + " | " +
                                rs.getString("ins_telefone") + " | " +
                                rs.getString("ins_especialidade") + " | " +
                                rs.getString("ins_horario_trabalho")
                );
            }

            System.out.println("\nPressione ENTER para continuar...");
            sc.nextLine();

        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    /**
     * Mostra os instrutores cadastrados para auxiliar na escolha do usuário.
     */
    private void mostrarInstrutores() {

        String sql = """
            SELECT *
            FROM instrutor
            ORDER BY id_instrutor
        """;

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            System.out.println("\n|--- LISTA DE INSTRUTORES ---|\n");

            while (rs.next()) {
                System.out.println(
                        rs.getInt("id_instrutor") + " | " +
                                rs.getString("ins_primeiro_nome") + " " +
                                rs.getString("ins_ultimo_nome") + " | " +
                                rs.getString("ins_especialidade")
                );
            }

        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    /**
     * Atualiza os dados de um instrutor cadastrado.
     */
    public void atualizarInstrutor() {

        mostrarInstrutores();

        System.out.print("\nDigite o ID do instrutor: ");
        int id = Integer.parseInt(sc.nextLine());

        String verificarSql = "SELECT * FROM instrutor WHERE id_instrutor = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement verificarStmt = conn.prepareStatement(verificarSql)) {

            verificarStmt.setInt(1, id);

            ResultSet rs = verificarStmt.executeQuery();

            if (!rs.next()) {
                System.out.println("\nInstrutor não encontrado!");
                return;
            }

            System.out.println("\nO que deseja atualizar?");
            System.out.println("1 - Telefone");
            System.out.println("2 - Especialidade");
            System.out.println("3 - Horário");
            System.out.print("Escolha: ");

            int opcao = Integer.parseInt(sc.nextLine());

            switch (opcao) {

                case 1:
                    System.out.print("Novo telefone: ");
                    String telefone = sc.nextLine();

                    if (!Validacao.telefoneValido(telefone)) {
                        System.out.println("Telefone inválido!");
                        return;
                    }

                    try (PreparedStatement stmt = conn.prepareStatement(
                            "UPDATE instrutor SET ins_telefone = ? WHERE id_instrutor = ?")) {

                        stmt.setString(1, telefone);
                        stmt.setInt(2, id);
                        stmt.executeUpdate();

                        System.out.println("\nTelefone atualizado!");
                    }

                    break;

                case 2:
                    System.out.print("Nova especialidade: ");
                    String especialidade = sc.nextLine();

                    try (PreparedStatement stmt = conn.prepareStatement(
                            "UPDATE instrutor SET ins_especialidade = ? WHERE id_instrutor = ?")) {

                        stmt.setString(1, especialidade);
                        stmt.setInt(2, id);
                        stmt.executeUpdate();

                        System.out.println("\nEspecialidade atualizada!");
                    }

                    break;

                case 3:
                    System.out.print("Novo horário: ");
                    String horario = sc.nextLine();

                    try (PreparedStatement stmt = conn.prepareStatement(
                            "UPDATE instrutor SET ins_horario_trabalho = ? WHERE id_instrutor = ?")) {

                        stmt.setString(1, horario);
                        stmt.setInt(2, id);
                        stmt.executeUpdate();

                        System.out.println("\nHorário atualizado!");
                    }

                    break;

                default:
                    System.out.println("Opção inválida!");
            }

        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    /**
     * Executa a operação removerInstrutor.
     */
    public void removerInstrutor() {

        mostrarInstrutores();

        System.out.print("\nDigite o ID do instrutor para remover: ");
        int id = Integer.parseInt(sc.nextLine());

        String verificarSql = "SELECT * FROM instrutor WHERE id_instrutor = ?";
        String deleteSql = "DELETE FROM instrutor WHERE id_instrutor = ?";

        try (Connection conn = Conexao.conectar()) {

            PreparedStatement verificarStmt = conn.prepareStatement(verificarSql);
            verificarStmt.setInt(1, id);

            ResultSet rs = verificarStmt.executeQuery();

            if (!rs.next()) {
                System.out.println("\nInstrutor não encontrado!");
                return;
            }

            PreparedStatement deleteStmt = conn.prepareStatement(deleteSql);
            deleteStmt.setInt(1, id);
            deleteStmt.executeUpdate();

            System.out.println("\nInstrutor removido com sucesso!");

        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    

    /**
     * Verifica se o CPF já está cadastrado no banco de dados.
     *
     * @return resultado da operação
     */
    private boolean cpfExiste(String cpf) {

        String sql = "SELECT * FROM instrutor WHERE ins_cpf = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cpf);

            ResultSet rs = stmt.executeQuery();

            return rs.next();

        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }

        return false;
    }
}
