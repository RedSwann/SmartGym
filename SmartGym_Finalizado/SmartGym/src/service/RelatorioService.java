package service;

import database.Conexao;
import model.RelatorioModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.Scanner;

/**
 * Serviço responsável pela geração e exibição de relatórios do sistema.
 *
 * @author __________________
 * @version 1.0
 */
public class RelatorioService {

    Scanner sc = new Scanner(System.in);

    /**
     * Exibe detalhes de um aluno, incluindo plano, frequência e aulas inscritas.
     */
    public void detalhesAluno() {

        RelatorioModel relatorio = new RelatorioModel(
                "DETALHES DO ALUNO",
                "Exibe plano, visitas, última visita e aulas inscritas"
        );

        mostrarAlunos();

        System.out.print("\nDigite o ID do aluno: ");
        int idAluno = Integer.parseInt(sc.nextLine());

        String sql = """
            SELECT
                a.id_aluno,
                a.aln_primeiro_nome,
                a.aln_ultimo_nome,
                a.aln_data_matricula,
                p.pln_nome,
                p.pln_duracao_meses,
                COUNT(DISTINCT f.frq_id) AS total_visitas,
                MAX(f.frq_data) AS ultima_visita,
                COUNT(DISTINCT i.aul_id) AS total_aulas
            FROM aluno a
            JOIN plano p ON a.pln_id = p.pln_id
            LEFT JOIN frequencia f ON a.id_aluno = f.id_aluno
            LEFT JOIN inscricaoaula i ON a.id_aluno = i.id_aluno
            WHERE a.id_aluno = ?
            GROUP BY
                a.id_aluno,
                a.aln_primeiro_nome,
                a.aln_ultimo_nome,
                a.aln_data_matricula,
                p.pln_nome,
                p.pln_duracao_meses
        """;

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idAluno);

            ResultSet rs = stmt.executeQuery();

            if (!rs.next()) {
                System.out.println("\nAluno não encontrado!");
                return;
            }

            LocalDate matricula =
                    rs.getDate("aln_data_matricula").toLocalDate();

            int duracao =
                    rs.getInt("pln_duracao_meses");

            LocalDate vencimento =
                    matricula.plusMonths(duracao);

            String status;

            if (LocalDate.now().isAfter(vencimento)) {
                status = "VENCIDO";
            } else {
                status = "ATIVO";
            }

            System.out.println(relatorio.exibirCabecalho());

            System.out.println(
                    "Aluno: " +
                            rs.getString("aln_primeiro_nome") + " " +
                            rs.getString("aln_ultimo_nome")
            );

            System.out.println("Plano: " + rs.getString("pln_nome"));
            System.out.println("Data de matrícula: " + matricula);
            System.out.println("Data de vencimento: " + vencimento);
            System.out.println("Status do plano: " + status);
            System.out.println("Total de visitas: " + rs.getInt("total_visitas"));
            System.out.println("Última visita: " + rs.getDate("ultima_visita"));
            System.out.println("Aulas inscritas: " + rs.getInt("total_aulas"));

            listarAulasDoAluno(conn, idAluno);

            System.out.println("\nPressione ENTER para continuar...");
            sc.nextLine();

        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    /**
     * Exibe a ocupação das aulas cadastradas.
     */
    public void ocupacaoAulas() {

        RelatorioModel relatorio = new RelatorioModel(
                "OCUPAÇÃO DAS AULAS",
                "Exibe capacidade, inscritos e vagas restantes"
        );

        String sql = """
            SELECT
                a.aul_id,
                a.aul_nome,
                a.aul_capacidade,
                COUNT(i.id_aluno) AS inscritos
            FROM aula a
            LEFT JOIN inscricaoaula i ON a.aul_id = i.aul_id
            GROUP BY a.aul_id, a.aul_nome, a.aul_capacidade
            ORDER BY a.aul_id
        """;

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            System.out.println(relatorio.exibirCabecalho());

            while (rs.next()) {

                int capacidade = rs.getInt("aul_capacidade");
                int inscritos = rs.getInt("inscritos");
                int vagas = capacidade - inscritos;

                System.out.println(
                        rs.getInt("aul_id") + " | " +
                                rs.getString("aul_nome") + " | " +
                                "Capacidade: " + capacidade + " | " +
                                "Inscritos: " + inscritos + " | " +
                                "Vagas: " + vagas
                );
            }

            System.out.println("\nPressione ENTER para continuar...");
            sc.nextLine();

        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    /**
     * Exibe detalhes de uma aula, incluindo instrutor e alunos inscritos.
     */
    public void detalhesAula() {

        RelatorioModel relatorio = new RelatorioModel(
                "DETALHES DA AULA",
                "Exibe instrutor responsável e alunos inscritos"
        );

        mostrarAulas();

        System.out.print("\nDigite o ID da aula: ");
        int idAula = Integer.parseInt(sc.nextLine());

        String sql = """
            SELECT
                a.aul_nome,
                a.aul_horario,
                a.aul_capacidade,
                i.ins_primeiro_nome,
                i.ins_ultimo_nome
            FROM aula a
            JOIN instrutor i
            ON a.id_instrutor = i.id_instrutor
            WHERE a.aul_id = ?
        """;

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idAula);

            ResultSet rs = stmt.executeQuery();

            if (!rs.next()) {
                System.out.println("\nAula não encontrada!");
                return;
            }

            System.out.println(relatorio.exibirCabecalho());

            System.out.println("Aula: " + rs.getString("aul_nome"));
            System.out.println("Horário: " + rs.getTime("aul_horario"));
            System.out.println("Capacidade: " + rs.getInt("aul_capacidade"));
            System.out.println(
                    "Instrutor: " +
                            rs.getString("ins_primeiro_nome") + " " +
                            rs.getString("ins_ultimo_nome")
            );

            listarAlunosDaAula(conn, idAula);

            System.out.println("\nPressione ENTER para continuar...");
            sc.nextLine();

        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    /**
     * Lista as aulas em que um aluno está inscrito.
     */
    private void listarAulasDoAluno(Connection conn, int idAluno) {

        String sql = """
            SELECT
                a.aul_nome,
                a.aul_horario
            FROM inscricaoaula i
            JOIN aula a ON i.aul_id = a.aul_id
            WHERE i.id_aluno = ?
        """;

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idAluno);

            ResultSet rs = stmt.executeQuery();

            System.out.println("\nAulas inscritas:");

            boolean encontrou = false;

            while (rs.next()) {
                encontrou = true;

                System.out.println(
                        "- " + rs.getString("aul_nome") +
                                " | Horário: " + rs.getTime("aul_horario")
                );
            }

            if (!encontrou) {
                System.out.println("Nenhuma aula inscrita.");
            }

        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    /**
     * Lista os alunos inscritos em uma aula.
     */
    private void listarAlunosDaAula(Connection conn, int idAula) {

        String sql = """
            SELECT
                a.id_aluno,
                a.aln_primeiro_nome,
                a.aln_ultimo_nome
            FROM inscricaoaula i
            JOIN aluno a
            ON i.id_aluno = a.id_aluno
            WHERE i.aul_id = ?
        """;

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idAula);

            ResultSet rs = stmt.executeQuery();

            System.out.println("\nAlunos inscritos:\n");

            boolean encontrou = false;

            while (rs.next()) {
                encontrou = true;

                System.out.println(
                        rs.getInt("id_aluno") + " | " +
                                rs.getString("aln_primeiro_nome") + " " +
                                rs.getString("aln_ultimo_nome")
                );
            }

            if (!encontrou) {
                System.out.println("Nenhum aluno inscrito.");
            }

        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    /**
     * Mostra os alunos cadastrados para auxiliar na escolha do usuário.
     */
    private void mostrarAlunos() {

        String sql = """
            SELECT id_aluno, aln_primeiro_nome, aln_ultimo_nome
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
     * Mostra as aulas cadastradas para ajudar na escolha do usuário.
     */
    private void mostrarAulas() {

        String sql = """
            SELECT
                aul_id,
                aul_nome,
                aul_horario
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
                                rs.getTime("aul_horario")
                );
            }

        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
}
