package service;

import database.Conexao;
import model.AlunoModel;
import utils.Validacao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Date;
import java.util.Scanner;

/**
 * Serviço responsável pelas operações de cadastro, consulta, atualização e exclusão de alunos.
 *
 * @author __________________
 * @version 1.0
 */
public class AlunoService {

    Scanner sc = new Scanner(System.in);

    /**
     * Cadastra um novo aluno no banco de dados.
     */
    public void cadastrarAluno() {

        listarPlanos();

        String sql = """
            INSERT INTO aluno
            (
                aln_cpf,
                aln_primeiro_nome,
                aln_nome_meio,
                aln_ultimo_nome,
                aln_datanascimento,
                aln_telefone,
                aln_email,
                aln_data_matricula,
                pln_id
            )
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            System.out.println("\n|--- CADASTRAR ALUNO ---|");

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

            System.out.print("Data de nascimento (AAAA-MM-DD): ");
            String dataNascimento = sc.nextLine();

            System.out.print("Telefone: ");
            String telefone = sc.nextLine();

            if (!Validacao.telefoneValido(telefone)) {
                System.out.println("Telefone inválido!");
                return;
            }

            System.out.print("Email: ");
            String email = sc.nextLine();

            System.out.print("Data matrícula (AAAA-MM-DD): ");
            String dataMatricula = sc.nextLine();

            System.out.print("ID do plano: ");
            int planoId = Integer.parseInt(sc.nextLine());

            AlunoModel aluno = new AlunoModel(
                    cpf,
                    primeiroNome,
                    nomeMeio,
                    ultimoNome,
                    telefone,
                    email,
                    dataNascimento,
                    dataMatricula,
                    planoId
            );

            stmt.setString(1, aluno.getCpf());
            stmt.setString(2, aluno.getPrimeiroNome());
            stmt.setString(3, aluno.getNomeMeio());
            stmt.setString(4, aluno.getUltimoNome());
            stmt.setDate(5, Date.valueOf(aluno.getDataNascimento()));
            stmt.setString(6, aluno.getTelefone());
            stmt.setString(7, aluno.getEmail());
            stmt.setDate(8, Date.valueOf(aluno.getDataMatricula()));
            stmt.setInt(9, aluno.getPlanoId());

            stmt.executeUpdate();

            System.out.println("\n" + aluno.exibirInformacoes() + " cadastrado com sucesso!");

        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    /**
     * Lista os alunos cadastrados
     */
    public void listarAlunos() {

        String sql = """
            SELECT
                a.*,
                p.pln_nome
            FROM aluno a
            INNER JOIN plano p
            ON a.pln_id = p.pln_id
            ORDER BY a.id_aluno
        """;

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            System.out.println("\n|--- LISTA DE ALUNOS ---|\n");

            while (rs.next()) {
                System.out.println(
                        rs.getInt("id_aluno") + " | " +
                                rs.getString("aln_primeiro_nome") + " " +
                                rs.getString("aln_ultimo_nome") + " | " +
                                rs.getString("aln_cpf") + " | " +
                                rs.getString("aln_telefone") + " | " +
                                rs.getString("aln_email") + " | " +
                                rs.getString("pln_nome")
                );
            }

            System.out.println("\nPressione ENTER para continuar...");
            sc.nextLine();

        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    /**
     * Mostra os alunos cadastrados para ajudar na escolha do usuário
     */
    private void mostrarAlunos() {

        String sql = """
            SELECT
                a.*,
                p.pln_nome
            FROM aluno a
            INNER JOIN plano p
            ON a.pln_id = p.pln_id
            ORDER BY a.id_aluno
        """;

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            System.out.println("\n|--- LISTA DE ALUNOS ---|\n");

            while (rs.next()) {
                System.out.println(
                        rs.getInt("id_aluno") + " | " +
                                rs.getString("aln_primeiro_nome") + " " +
                                rs.getString("aln_ultimo_nome") + " | " +
                                rs.getString("aln_cpf") + " | " +
                                rs.getString("pln_nome")
                );
            }

        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    /**
     * Executa a operação editarAluno.
     */
    public void editarAluno() {

        mostrarAlunos();

        System.out.print("\nDigite o ID do aluno: ");
        int id = Integer.parseInt(sc.nextLine());

        String verificarSql =
                "SELECT * FROM aluno WHERE id_aluno = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement verificarStmt =
                     conn.prepareStatement(verificarSql)) {

            verificarStmt.setInt(1, id);

            ResultSet rs = verificarStmt.executeQuery();

            if (!rs.next()) {
                System.out.println("\nAluno não encontrado!");
                return;
            }

            System.out.println("\nO que deseja atualizar?");
            System.out.println("1 - Telefone");
            System.out.println("2 - Email");
            System.out.println("3 - Plano");
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

                    try (PreparedStatement stmt =
                                 conn.prepareStatement(
                                         "UPDATE aluno SET aln_telefone = ? WHERE id_aluno = ?"
                                 )) {

                        stmt.setString(1, telefone);
                        stmt.setInt(2, id);
                        stmt.executeUpdate();

                        System.out.println("\nTelefone atualizado!");
                    }

                    break;

                case 2:
                    System.out.print("Novo email: ");
                    String email = sc.nextLine();

                    try (PreparedStatement stmt =
                                 conn.prepareStatement(
                                         "UPDATE aluno SET aln_email = ? WHERE id_aluno = ?"
                                 )) {

                        stmt.setString(1, email);
                        stmt.setInt(2, id);
                        stmt.executeUpdate();

                        System.out.println("\nEmail atualizado!");
                    }

                    break;

                case 3:
                    listarPlanos();

                    System.out.print("Novo ID do plano: ");
                    int planoId = Integer.parseInt(sc.nextLine());

                    try (PreparedStatement stmt =
                                 conn.prepareStatement(
                                         "UPDATE aluno SET pln_id = ? WHERE id_aluno = ?"
                                 )) {

                        stmt.setInt(1, planoId);
                        stmt.setInt(2, id);
                        stmt.executeUpdate();

                        System.out.println("\nPlano atualizado!");
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
     * Exclui um aluno cadastrado.
     */
    public void excluirAluno() {

        mostrarAlunos();

        System.out.print("\nDigite o ID do aluno para remover: ");
        int id = Integer.parseInt(sc.nextLine());

        String verificarSql =
                "SELECT * FROM aluno WHERE id_aluno = ?";

        String deleteSql =
                "DELETE FROM aluno WHERE id_aluno = ?";

        try (Connection conn = Conexao.conectar()) {

            PreparedStatement verificarStmt =
                    conn.prepareStatement(verificarSql);

            verificarStmt.setInt(1, id);

            ResultSet rs = verificarStmt.executeQuery();

            if (!rs.next()) {
                System.out.println("\nAluno não encontrado!");
                return;
            }

            PreparedStatement deleteStmt =
                    conn.prepareStatement(deleteSql);

            deleteStmt.setInt(1, id);
            deleteStmt.executeUpdate();

            System.out.println("\nAluno removido com sucesso!");

        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    /**
     * Verifica se o cpf já está cadastrado.
     */
    private boolean cpfExiste(String cpf) {

        String sql =
                "SELECT * FROM aluno WHERE aln_cpf = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt =
                     conn.prepareStatement(sql)) {

            stmt.setString(1, cpf);

            ResultSet rs = stmt.executeQuery();

            return rs.next();

        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }

        return false;
    }

    /**
     * Lista os planos cadastrados.
     */
    private void listarPlanos() {

        String sql = """
            SELECT *
            FROM plano
            ORDER BY pln_id
        """;

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            System.out.println("\n|--- PLANOS DISPONÍVEIS ---|\n");

            while (rs.next()) {
                System.out.println(
                        rs.getInt("pln_id") + " | " +
                                rs.getString("pln_nome") + " | R$ " +
                                rs.getDouble("pln_valor")
                );
            }

        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
}
