package service;

import database.Conexao;
import model.PlanoModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

/**
 * Serviço responsável pelas operações de cadastro, consulta, atualização e exclusão de planos.
 *
 * @author __________________
 * @version 1.0
 */
public class PlanoService {

    Scanner sc = new Scanner(System.in);

    /**
     * Cadastra um novo plano no banco de dados.
     */
    public void cadastrarPlano() {

        String sql = """
            INSERT INTO plano
            (
                pln_nome,
                pln_descricao,
                pln_valor,
                pln_duracao_meses,
                pln_beneficios
            )
            VALUES (?, ?, ?, ?, ?)
        """;

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            System.out.println("\n|--- CADASTRAR PLANO ---|");

            System.out.print("Nome do plano: ");
            String nome = sc.nextLine();

            System.out.print("Descrição: ");
            String descricao = sc.nextLine();

            System.out.print("Valor mensal: ");
            double valor = Double.parseDouble(sc.nextLine());

            System.out.print("Duração em meses: ");
            int duracao = Integer.parseInt(sc.nextLine());

            System.out.print("Benefícios: ");
            String beneficios = sc.nextLine();

            PlanoModel plano = new PlanoModel(
                    0,
                    nome,
                    descricao,
                    valor,
                    duracao,
                    beneficios
            );

            stmt.setString(1, plano.getNome());
            stmt.setString(2, plano.getDescricao());
            stmt.setDouble(3, plano.getValor());
            stmt.setInt(4, plano.getDuracaoMeses());
            stmt.setString(5, plano.getBeneficios());

            stmt.executeUpdate();

            System.out.println("\nPlano cadastrado com sucesso!");

        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    /**
     * Lista os planos cadastrados.
     */
    public void listarPlanos() {

        String sql = """
            SELECT *
            FROM plano
            ORDER BY pln_id
        """;

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            System.out.println("\n|--- LISTA DE PLANOS ---|\n");

            while (rs.next()) {
                System.out.println(
                        rs.getInt("pln_id") + " | " +
                                rs.getString("pln_nome") + " | R$ " +
                                rs.getDouble("pln_valor") + " | " +
                                rs.getInt("pln_duracao_meses") + " meses | " +
                                rs.getString("pln_beneficios")
                );
            }

            System.out.println("\nPressione ENTER para continuar...");
            sc.nextLine();

        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    /**
     * Executa a operação mostrarPlanos.
     */
    private void mostrarPlanos() {

        String sql = """
            SELECT *
            FROM plano
            ORDER BY pln_id
        """;

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            System.out.println("\n|--- LISTA DE PLANOS ---|\n");

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

    /**
     * Atualiza os dados de um plano cadastrado.
     */
    public void atualizarPlano() {

        mostrarPlanos();

        System.out.print("\nDigite o ID do plano: ");
        int id = Integer.parseInt(sc.nextLine());

        String verificarSql = "SELECT * FROM plano WHERE pln_id = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement verificarStmt = conn.prepareStatement(verificarSql)) {

            verificarStmt.setInt(1, id);

            ResultSet rs = verificarStmt.executeQuery();

            if (!rs.next()) {
                System.out.println("\nPlano não encontrado!");
                return;
            }

            System.out.println("\nO que deseja atualizar?");
            System.out.println("1 - Nome");
            System.out.println("2 - Descrição");
            System.out.println("3 - Valor");
            System.out.println("4 - Duração");
            System.out.println("5 - Benefícios");
            System.out.print("Escolha: ");

            int opcao = Integer.parseInt(sc.nextLine());

            switch (opcao) {

                case 1:
                    System.out.print("Novo nome: ");
                    String nome = sc.nextLine();

                    try (PreparedStatement stmt = conn.prepareStatement(
                            "UPDATE plano SET pln_nome = ? WHERE pln_id = ?")) {

                        stmt.setString(1, nome);
                        stmt.setInt(2, id);
                        stmt.executeUpdate();

                        System.out.println("\nNome atualizado!");
                    }

                    break;

                case 2:
                    System.out.print("Nova descrição: ");
                    String descricao = sc.nextLine();

                    try (PreparedStatement stmt = conn.prepareStatement(
                            "UPDATE plano SET pln_descricao = ? WHERE pln_id = ?")) {

                        stmt.setString(1, descricao);
                        stmt.setInt(2, id);
                        stmt.executeUpdate();

                        System.out.println("\nDescrição atualizada!");
                    }

                    break;

                case 3:
                    System.out.print("Novo valor: ");
                    double valor = Double.parseDouble(sc.nextLine());

                    try (PreparedStatement stmt = conn.prepareStatement(
                            "UPDATE plano SET pln_valor = ? WHERE pln_id = ?")) {

                        stmt.setDouble(1, valor);
                        stmt.setInt(2, id);
                        stmt.executeUpdate();

                        System.out.println("\nValor atualizado!");
                    }

                    break;

                case 4:
                    System.out.print("Nova duração: ");
                    int duracao = Integer.parseInt(sc.nextLine());

                    try (PreparedStatement stmt = conn.prepareStatement(
                            "UPDATE plano SET pln_duracao_meses = ? WHERE pln_id = ?")) {

                        stmt.setInt(1, duracao);
                        stmt.setInt(2, id);
                        stmt.executeUpdate();

                        System.out.println("\nDuração atualizada!");
                    }

                    break;

                case 5:
                    System.out.print("Novos benefícios: ");
                    String beneficios = sc.nextLine();

                    try (PreparedStatement stmt = conn.prepareStatement(
                            "UPDATE plano SET pln_beneficios = ? WHERE pln_id = ?")) {

                        stmt.setString(1, beneficios);
                        stmt.setInt(2, id);
                        stmt.executeUpdate();

                        System.out.println("\nBenefícios atualizados!");
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
     * Executa a operação removerPlano.
     */
    public void removerPlano() {

        mostrarPlanos();

        System.out.print("\nDigite o ID do plano para remover: ");
        int id = Integer.parseInt(sc.nextLine());

        String verificarSql = "SELECT * FROM plano WHERE pln_id = ?";
        String deleteSql = "DELETE FROM plano WHERE pln_id = ?";

        try (Connection conn = Conexao.conectar()) {

            PreparedStatement verificarStmt = conn.prepareStatement(verificarSql);
            verificarStmt.setInt(1, id);

            ResultSet rs = verificarStmt.executeQuery();

            if (!rs.next()) {
                System.out.println("\nPlano não encontrado!");
                return;
            }

            PreparedStatement deleteStmt = conn.prepareStatement(deleteSql);
            deleteStmt.setInt(1, id);
            deleteStmt.executeUpdate();

            System.out.println("\nPlano removido com sucesso!");

        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
}
