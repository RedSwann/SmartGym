package service;

import database.Conexao;
import model.FrequenciaModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;

/**
 * Serviço responsável pelo controle de frequência dos alunos.
 *
 * @author __________________
 * @version 1.0
 */
public class FrequenciaService {

    Scanner sc = new Scanner(System.in);

    /**
     * Registra a entrada de um aluno na academia.
     */
    public void registrarEntrada() {

        mostrarAlunos();

        System.out.print("\nDigite o ID do aluno: ");
        int idAluno = Integer.parseInt(sc.nextLine());

        System.out.print("Data da entrada (AAAA-MM-DD): ");
        String data = sc.nextLine();

        System.out.print("Hora da entrada (HH:mm): ");
        String horaEntrada = sc.nextLine();

        FrequenciaModel frequencia = new FrequenciaModel(
                0,
                data,
                horaEntrada,
                null,
                idAluno
        );

        String verificarSql = "SELECT * FROM aluno WHERE id_aluno = ?";

        String insertSql = """
            INSERT INTO frequencia
            (
                frq_data,
                frq_hora_entrada,
                id_aluno
            )
            VALUES (?, ?, ?)
        """;

        try (Connection conn = Conexao.conectar()) {

            PreparedStatement verificarStmt =
                    conn.prepareStatement(verificarSql);

            verificarStmt.setInt(1, frequencia.getIdAluno());

            ResultSet rs = verificarStmt.executeQuery();

            if (!rs.next()) {
                System.out.println("\nAluno não encontrado!");
                return;
            }

            PreparedStatement insertStmt =
                    conn.prepareStatement(insertSql);

            insertStmt.setDate(
                    1,
                    Date.valueOf(LocalDate.parse(frequencia.getData()))
            );

            insertStmt.setTime(
                    2,
                    Time.valueOf(LocalTime.parse(frequencia.getHoraEntrada()))
            );

            insertStmt.setInt(3, frequencia.getIdAluno());

            insertStmt.executeUpdate();

            System.out.println("\n" + frequencia.exibirInformacoes());
            System.out.println("Entrada registrada com sucesso!");

        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    /**
     * Executa a operação registrarSaida.
     */
    public void registrarSaida() {

        mostrarAlunos();

        System.out.print("\nDigite o ID do aluno: ");
        int idAluno = Integer.parseInt(sc.nextLine());

        System.out.print("Hora da saída (HH:mm): ");
        String horaSaida = sc.nextLine();

        String buscarSql = """
            SELECT *
            FROM frequencia
            WHERE id_aluno = ?
            AND frq_hora_saida IS NULL
            ORDER BY frq_id DESC
            LIMIT 1
        """;

        String updateSql = """
            UPDATE frequencia
            SET frq_hora_saida = ?
            WHERE frq_id = ?
        """;

        try (Connection conn = Conexao.conectar()) {

            PreparedStatement buscarStmt =
                    conn.prepareStatement(buscarSql);

            buscarStmt.setInt(1, idAluno);

            ResultSet rs = buscarStmt.executeQuery();

            if (!rs.next()) {
                System.out.println("\nNenhuma entrada aberta encontrada!");
                return;
            }

            int idFrequencia = rs.getInt("frq_id");

            PreparedStatement updateStmt =
                    conn.prepareStatement(updateSql);

            updateStmt.setTime(
                    1,
                    Time.valueOf(LocalTime.parse(horaSaida))
            );

            updateStmt.setInt(2, idFrequencia);

            updateStmt.executeUpdate();

            System.out.println("\nSaída registrada com sucesso!");

        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    /**
     * Lista os registros de frequência cadastrados.
     */
    public void listarFrequencias() {

        String sql = """
            SELECT
                f.frq_id,
                f.frq_data,
                f.frq_hora_entrada,
                f.frq_hora_saida,
                a.id_aluno,
                a.aln_primeiro_nome,
                a.aln_ultimo_nome
            FROM frequencia f
            INNER JOIN aluno a
            ON f.id_aluno = a.id_aluno
            ORDER BY f.frq_id
        """;

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            System.out.println("\n|--- LISTA DE FREQUÊNCIAS ---|\n");

            while (rs.next()) {
                System.out.println(
                        rs.getInt("frq_id") + " | " +
                                rs.getDate("frq_data") + " | " +
                                rs.getTime("frq_hora_entrada") + " | " +
                                rs.getTime("frq_hora_saida") + " | " +
                                rs.getInt("id_aluno") + " | " +
                                rs.getString("aln_primeiro_nome") + " " +
                                rs.getString("aln_ultimo_nome")
                );
            }

            System.out.println("\nPressione ENTER para continuar...");
            sc.nextLine();

        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    /**
     * Executa a operação relatorioAluno.
     */
    public void relatorioAluno() {

        mostrarAlunos();

        System.out.print("\nDigite o ID do aluno: ");
        int idAluno = Integer.parseInt(sc.nextLine());

        String sql = """
            SELECT
                a.id_aluno,
                a.aln_primeiro_nome,
                a.aln_ultimo_nome,
                COUNT(f.frq_id) AS total_visitas,
                MAX(f.frq_data) AS ultima_visita
            FROM aluno a
            LEFT JOIN frequencia f
            ON a.id_aluno = f.id_aluno
            WHERE a.id_aluno = ?
            GROUP BY
                a.id_aluno,
                a.aln_primeiro_nome,
                a.aln_ultimo_nome
        """;

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idAluno);

            ResultSet rs = stmt.executeQuery();

            if (!rs.next()) {
                System.out.println("\nAluno não encontrado!");
                return;
            }

            System.out.println("\n|--- RELATÓRIO DE FREQUÊNCIA ---|\n");
            System.out.println("Aluno: " +
                    rs.getString("aln_primeiro_nome") + " " +
                    rs.getString("aln_ultimo_nome"));
            System.out.println("Total de visitas: " +
                    rs.getInt("total_visitas"));
            System.out.println("Última visita: " +
                    rs.getDate("ultima_visita"));

            System.out.println("\nPressione ENTER para continuar...");
            sc.nextLine();

        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    /**
     * Mostra os alunos cadastrados para auxiliar na escolha do usuário.
     */
    private void mostrarAlunos() {

        String sql = """
            SELECT *
            FROM aluno
            ORDER BY id_aluno
        """;

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            System.out.println("\n|--- ALUNOS ---|\n");

            while (rs.next()) {
                System.out.println(
                        rs.getInt("id_aluno") + " | " +
                                rs.getString("aln_primeiro_nome") + " " +
                                rs.getString("aln_ultimo_nome")
                );
            }

        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
}
