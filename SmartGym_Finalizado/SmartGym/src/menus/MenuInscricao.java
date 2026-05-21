package menus;

import java.util.Scanner;

/**
 * Menu inscrições
 *
 * @author __________________
 * @version 1.0
 */
public class MenuInscricao {
    private Scanner sc = new Scanner(System.in);
    public  int exibirMenuInscricao(){

        System.out.println("|-------------------------------------------|");
        System.out.println("|              MENU INSCRIÇÕES              |");
        System.out.println("|-------------------------------------------|");
        System.out.println("|    1 - Inscrever Aluno em Aula            |");
        System.out.println("|    2 - Cancelar Inscrições do Aluno       |");
        System.out.println("|    3 - Listar Alunos de uma Aula          |");
        System.out.println("|    4 - Listar Inscrições do Aluno         |");
        System.out.println("|    0 - Voltar                             |");
        System.out.println("|                                           |");
        System.out.println("|                                           |");
        System.out.println("|-------------------------------------------|");
        System.out.print("Selecione uma opção:  ");

        return Integer.parseInt(sc.nextLine());
    }

}
