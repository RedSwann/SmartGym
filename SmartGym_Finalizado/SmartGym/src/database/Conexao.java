package database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Classe responsável por realizar a conexão com o banco de dados PostgreSQL.
 *
 * @author __________________
 * @version 1.0
 */
public class Conexao {

    private static final String URL =
            "jdbc:postgresql://localhost:5432/SmartGym";

    private static final String USUARIO = "postgres";
    private static final String SENHA = "12345";

    /**
     * Realiza a conexão com o banco de dados.
     */
    public static Connection conectar(){

        try{
            Connection conn =
                    DriverManager.getConnection(URL, USUARIO, SENHA);

            return conn;
        } catch (SQLException e) {
            System.out.println("Erro ao conectar");
            e.printStackTrace();
            throw new RuntimeException("Erro na conexão com banco");
        }
    }
}
