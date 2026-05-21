package service;

import database.Conexao;
import model.AulaModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalTime;
import java.util.Scanner;

/**
 * Serviço responsável pelas operações de cadastro, consulta, atualização e exclusão de aulas.
 *
 * @author __________________
 * @version 1.0
 */
public class AulaService {

    Scanner sc = new Scanner(System.in);

    /**
     * Cadastra uma nova aula no banco de dados.
     */
    public void cadastrarAula() {

        mostrarInstrutores();
    
        String sql = """
            INSERT INTO aula
            (
                aul_nome,
                aul_descricao,
                aul_capacidade,
                aul_horario,
                aul_duracao,
                id_instrutor
            )
            VALUES (?, ?, ?, ?, ?, ?)
        """;

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            System.out.println("\n|--- CADASTRAR AULA ---|");

            System.out.print("Nome da aula: ");
            String nome = sc.nextLine();

            System.out.print("Descrição: ");
            String descricao = sc.nextLine();

            System.out.print("Capacidade máxima: ");
            int capacidade = Integer.parseInt(sc.nextLine());

            System.out.print("Horário (HH:mm): ");
            String horario = sc.nextLine();

            System.out.print("Duração em minutos: ");
            int duracao = Integer.parseInt(sc.nextLine());

            System.out.print("ID do instrutor: ");
            int idInstrutor = Integer.parseInt(sc.nextLine());

            AulaModel aula = new AulaModel(
                    0,
                    nome,
                    descricao,
                    horario,
                    capacidade,
                    duracao,
                    idInstrutor
            );

            stmt.setString(1, aula.getNome());
            stmt.setString(2, aula.getDescricao());
            stmt.setInt(3, aula.getCapacidade());
            stmt.setObject(4, LocalTime.parse(aula.getHorario()));
            stmt.setInt(5, aula.getDuracao());
            stmt.setInt(6, aula.getIdInstrutor());

            stmt.executeUpdate();

            System.out.println("\nAula cadastrada com sucesso!");

        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    /**
     * Lista as aulas cadastradas.
     */
    public void listarAulas() {

        String sql = """
            SELECT
                a.*,
                i.ins_primeiro_nome,
                i.ins_ultimo_nome
            FROM aula a
            INNER JOIN instrutor i
            ON a.id_instrutor = i.id_instrutor
            ORDER BY a.aul_id
        """;

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            System.out.println("\n|--- LISTA DE AULAS ---|\n");

            while (rs.next()) {
                System.out.println(
                        rs.getInt("aul_id") + " | " +
                                rs.getString("aul_nome") + " | " +
                                rs.getString("aul_descricao") + " | " +
                                rs.getObject("aul_horario", LocalTime.class) + " | " +
                                rs.getInt("aul_duracao") + " min | " +
                                "Capacidade: " + rs.getInt("aul_capacidade") + " | " +
                                rs.getString("ins_primeiro_nome") + " " +
                                rs.getString("ins_ultimo_nome")
                );
            }

            System.out.println("\nPressione ENTER para continuar...");
            sc.nextLine();

        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    /**
     * Mostra as aulas cadastradas para auxiliar na escolha do usuário.
     */
    private void mostrarAulas() {

        String sql = """
            SELECT *
            FROM aula
            ORDER BY aul_id
        """;

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            System.out.println("\n|--- LISTA DE AULAS ---|\n");

            while (rs.next()) {
                System.out.println(
                        rs.getInt("aul_id") + " | " +
                                rs.getString("aul_nome") + " | " +
                                rs.getObject("aul_horario", LocalTime.class)
                );
            }

        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    /**
     * Executa a operação editarAula.
     */
    public void editarAula() {

        mostrarAulas();

        System.out.print("\nDigite o ID da aula: ");
        int id = Integer.parseInt(sc.nextLine());

        String verificarSql = "SELECT * FROM aula WHERE aul_id = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement verificarStmt = conn.prepareStatement(verificarSql)) {

            verificarStmt.setInt(1, id);

            ResultSet rs = verificarStmt.executeQuery();

            if (!rs.next()) {
                System.out.println("\nAula não encontrada!");
                return;
            }

            System.out.println("\nO que deseja atualizar?");
            System.out.println("1 - Nome");
            System.out.println("2 - Horário");
            System.out.println("3 - Capacidade");
            System.out.println("4 - Duração");
            System.out.println("5 - Descrição");
            System.out.println("6 - Instrutor");
            System.out.print("Escolha: ");

            int opcao = Integer.parseInt(sc.nextLine());

            switch (opcao) {

                case 1:
                    System.out.print("Novo nome: ");
                    String nome = sc.nextLine();

                    try (PreparedStatement stmt = conn.prepareStatement(
                            "UPDATE aula SET aul_nome = ? WHERE aul_id = ?")) {

                        stmt.setString(1, nome);
                        stmt.setInt(2, id);
                        stmt.executeUpdate();

                        System.out.println("\nNome atualizado!");
                    }
                    break;

                case 2:
                    System.out.print("Novo horário (HH:mm): ");
                    LocalTime horario = LocalTime.parse(sc.nextLine());

                    try (PreparedStatement stmt = conn.prepareStatement(
                            "UPDATE aula SET aul_horario = ? WHERE aul_id = ?")) {

                        stmt.setObject(1, horario);
                        stmt.setInt(2, id);
                        stmt.executeUpdate();

                        System.out.println("\nHorário atualizado!");
                    }
                    break;

                case 3:
                    System.out.print("Nova capacidade: ");
                    int capacidade = Integer.parseInt(sc.nextLine());

                    try (PreparedStatement stmt = conn.prepareStatement(
                            "UPDATE aula SET aul_capacidade = ? WHERE aul_id = ?")) {

                        stmt.setInt(1, capacidade);
                        stmt.setInt(2, id);
                        stmt.executeUpdate();

                        System.out.println("\nCapacidade atualizada!");
                    }
                    break;

                case 4:
                    System.out.print("Nova duração em minutos: ");
                    int duracao = Integer.parseInt(sc.nextLine());

                    try (PreparedStatement stmt = conn.prepareStatement(
                            "UPDATE aula SET aul_duracao = ? WHERE aul_id = ?")) {

                        stmt.setInt(1, duracao);
                        stmt.setInt(2, id);
                        stmt.executeUpdate();

                        System.out.println("\nDuração atualizada!");
                    }
                    break;

                case 5:
                    System.out.print("Nova descrição: ");
                    String descricao = sc.nextLine();

                    try (PreparedStatement stmt = conn.prepareStatement(
                            "UPDATE aula SET aul_descricao = ? WHERE aul_id = ?")) {

                        stmt.setString(1, descricao);
                        stmt.setInt(2, id);
                        stmt.executeUpdate();

                        System.out.println("\nDescrição atualizada!");
                    }
                    break;

                case 6:
                    mostrarInstrutores();

                    System.out.print("Novo ID do instrutor: ");
                    int idInstrutor = Integer.parseInt(sc.nextLine());

                    try (PreparedStatement stmt = conn.prepareStatement(
                            "UPDATE aula SET id_instrutor = ? WHERE aul_id = ?")) {

                        stmt.setInt(1, idInstrutor);
                        stmt.setInt(2, id);
                        stmt.executeUpdate();

                        System.out.println("\nInstrutor atualizado!");
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
     * Exclui uma aula cadastrada.
     */
    public void excluirAula() {

        mostrarAulas();

        System.out.print("\nDigite o ID da aula para remover: ");
        int id = Integer.parseInt(sc.nextLine());

        String verificarSql = "SELECT * FROM aula WHERE aul_id = ?";
        String deleteSql = "DELETE FROM aula WHERE aul_id = ?";

        try (Connection conn = Conexao.conectar()) {

            PreparedStatement verificarStmt = conn.prepareStatement(verificarSql);
            verificarStmt.setInt(1, id);

            ResultSet rs = verificarStmt.executeQuery();

            if (!rs.next()) {
                System.out.println("\nAula não encontrada!");
                return;
            }

            PreparedStatement deleteStmt = conn.prepareStatement(deleteSql);
            deleteStmt.setInt(1, id);
            deleteStmt.executeUpdate();

            System.out.println("\nAula removida com sucesso!");

        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    /**
     * Mostra os instrutores cadastrados para ajudar na escolha do usuário.
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
}
