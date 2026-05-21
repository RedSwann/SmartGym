package service;

import database.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;

/**
 * Serviço responsável por gerenciar inscrições de alunos em aulas.
 *
 * @author __________________
 * @version 1.0
 */
public class InscricaoService {

    Scanner sc = new Scanner(System.in);

    /**
     * Realiza a inscrição de um aluno em uma aula
     */

    public void inscreverAluno() {

        mostrarAlunos();

        System.out.println("\n|--- INSCREVER ALUNO ---|");

        System.out.print("ID do aluno: ");
        int idAluno = Integer.parseInt(sc.nextLine());

        try (Connection conn = Conexao.conectar()) {

            String alunoSql = "SELECT * FROM aluno WHERE id_aluno = ?";
            PreparedStatement alunoStmt = conn.prepareStatement(alunoSql);
            alunoStmt.setInt(1, idAluno);
            ResultSet alunoRs = alunoStmt.executeQuery();
            //verificacao se existe o aluno cadastrado
            if (!alunoRs.next()) {
                System.out.println("\nAluno não encontrado!");
                return;
            }
            //verificacao se o plano do aluno está ativo
            if (!planoAtivo(conn, idAluno)) {
                return;
            }

            mostrarAulas();

            System.out.print("ID da aula: ");
            int idAula = Integer.parseInt(sc.nextLine());

            String aulaSql = "SELECT * FROM aula WHERE aul_id = ?";
            PreparedStatement aulaStmt = conn.prepareStatement(aulaSql);
            aulaStmt.setInt(1, idAula);
            ResultSet aulaRs = aulaStmt.executeQuery();

            if (!aulaRs.next()) {
                System.out.println("\nAula não encontrada!");
                return;
            }

            int capacidade = aulaRs.getInt("aul_capacidade");

            String qtdSql = """
                SELECT COUNT(*)
                FROM inscricaoaula
                WHERE aul_id = ?
            """;

            PreparedStatement qtdStmt = conn.prepareStatement(qtdSql);
            qtdStmt.setInt(1, idAula);
            ResultSet qtdRs = qtdStmt.executeQuery();
            qtdRs.next();

            int quantidade = qtdRs.getInt(1);

            if (quantidade >= capacidade) {

                System.out.println("\n|--- AULA LOTADA ---|");

                System.out.println("Inscritos: " + quantidade + "/" + capacidade);
                return;
            }

            String verificarSql = """
                SELECT *
                FROM inscricaoaula
                WHERE id_aluno = ?
                AND aul_id = ?
            """;

            PreparedStatement verificarStmt = conn.prepareStatement(verificarSql);
            verificarStmt.setInt(1, idAluno);
            verificarStmt.setInt(2, idAula);
            ResultSet verificarRs = verificarStmt.executeQuery();

            if (verificarRs.next()) {
                System.out.println("\nAluno já inscrito!");
                return;
            }

            LocalTime horarioNovaAula =
                    aulaRs.getObject("aul_horario", LocalTime.class);

            String conflitoSql = """
                SELECT
                    a.aul_nome,
                    a.aul_horario
                FROM inscricaoaula i
                INNER JOIN aula a
                ON i.aul_id = a.aul_id
                WHERE i.id_aluno = ?
            """;

            PreparedStatement conflitoStmt = conn.prepareStatement(conflitoSql);
            conflitoStmt.setInt(1, idAluno);
            ResultSet conflitoRs = conflitoStmt.executeQuery();

            while (conflitoRs.next()) {

                LocalTime horarioExistente =
                        conflitoRs.getObject("aul_horario", LocalTime.class);

                if (horarioExistente.equals(horarioNovaAula)) {
                    System.out.println(
                            "\nConflito de horário com a aula: "
                                    + conflitoRs.getString("aul_nome")
                    );
                    return;
                }
            }

            String insertSql = """
                INSERT INTO inscricaoaula
                (
                    id_aluno,
                    aul_id,
                    ina_data_inscricao
                )
                VALUES (?, ?, ?)
            """;

            PreparedStatement insertStmt = conn.prepareStatement(insertSql);

            insertStmt.setInt(1, idAluno);
            insertStmt.setInt(2, idAula);
            insertStmt.setDate(3, Date.valueOf(LocalDate.now()));

            insertStmt.executeUpdate();

            System.out.println("\nInscrição realizada com sucesso!");

        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    /**
     * Cancela a inscrição de um aluno em uma aula.
     */
    public void cancelarInscricao() {

        listarInscricoes();

        System.out.print("\nID do aluno: ");
        int idAluno = Integer.parseInt(sc.nextLine());

        System.out.print("ID da aula: ");
        int idAula = Integer.parseInt(sc.nextLine());

        String sql = """
            DELETE FROM inscricaoaula
            WHERE id_aluno = ?
            AND aul_id = ?
        """;

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idAluno);
            stmt.setInt(2, idAula);

            int linhas = stmt.executeUpdate();

            if (linhas == 0) {
                System.out.println("\nInscrição não encontrada!");
                return;
            }

            System.out.println("\nInscrição cancelada!");

        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    /**
     * Lista todas as inscrições cadastradas.
     */
    public void listarInscricoes() {

        String sql = """
            SELECT
                al.id_aluno,
                al.aln_primeiro_nome,
                al.aln_ultimo_nome,
                au.aul_id,
                au.aul_nome,
                i.ina_data_inscricao
            FROM inscricaoaula i
            INNER JOIN aluno al
            ON i.id_aluno = al.id_aluno
            INNER JOIN aula au
            ON i.aul_id = au.aul_id
            ORDER BY au.aul_nome
        """;

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            System.out.println("\n|--- LISTA DE INSCRIÇÕES ---|\n");

            while (rs.next()) {
                System.out.println(
                        rs.getInt("id_aluno") + " | " +
                                rs.getString("aln_primeiro_nome") + " " +
                                rs.getString("aln_ultimo_nome") + " | " +
                                rs.getInt("aul_id") + " | " +
                                rs.getString("aul_nome") + " | " +
                                rs.getDate("ina_data_inscricao")
                );
            }

            System.out.println("\nPressione ENTER para continuar...");
            sc.nextLine();

        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    /**
     * Lista as inscrições de um aluno específico.
     */
    public void listarInscricoesDoAluno() {

        mostrarAlunos();

        System.out.print("\nDigite o ID do aluno: ");
        int idAluno = Integer.parseInt(sc.nextLine());

        String sql = """
            SELECT
                al.id_aluno,
                al.aln_primeiro_nome,
                al.aln_ultimo_nome,
                au.aul_id,
                au.aul_nome,
                au.aul_horario,
                i.ina_data_inscricao
            FROM inscricaoaula i
            INNER JOIN aluno al
            ON i.id_aluno = al.id_aluno
            INNER JOIN aula au
            ON i.aul_id = au.aul_id
            WHERE al.id_aluno = ?
            ORDER BY au.aul_horario
        """;

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idAluno);

            ResultSet rs = stmt.executeQuery();

            System.out.println("\n|--- INSCRIÇÕES DO ALUNO ---|\n");

            boolean encontrou = false;

            while (rs.next()) {
                encontrou = true;

                System.out.println(
                        rs.getInt("id_aluno") + " | " +
                                rs.getString("aln_primeiro_nome") + " " +
                                rs.getString("aln_ultimo_nome") + " | Aula: " +
                                rs.getInt("aul_id") + " - " +
                                rs.getString("aul_nome") + " | Horário: " +
                                rs.getObject("aul_horario", LocalTime.class) +
                                " | Data inscrição: " +
                                rs.getDate("ina_data_inscricao")
                );
            }

            if (!encontrou) {
                System.out.println("Esse aluno não possui inscrições.");
            }

            System.out.println("\nPressione ENTER para continuar...");
            sc.nextLine();

        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    /**
     * Verifica se o plano do aluno está ativo.
     *
     * @return resultado da operação
     */
    private boolean planoAtivo(Connection conn, int idAluno) {

        String sql = """
            SELECT 
                a.aln_data_matricula,
                p.pln_duracao_meses
            FROM aluno a
            JOIN plano p ON a.pln_id = p.pln_id
            WHERE a.id_aluno = ?
        """;

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idAluno);
            ResultSet rs = stmt.executeQuery();

            if (!rs.next()) {
                System.out.println("Aluno não encontrado!");
                return false;
            }

            LocalDate dataMatricula =
                    rs.getDate("aln_data_matricula").toLocalDate();

            int duracaoMeses =
                    rs.getInt("pln_duracao_meses");

            LocalDate vencimento =
                    dataMatricula.plusMonths(duracaoMeses);

            if (LocalDate.now().isAfter(vencimento)) {
                System.out.println("\nPlano vencido!");
                System.out.println("Data de vencimento: " + vencimento);
                return false;
            }

            return true;

        } catch (Exception e) {
            System.out.println("Erro ao verificar plano: " + e.getMessage());
            return false;
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

            System.out.println("\n|--- AULAS ---|\n");

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
}
