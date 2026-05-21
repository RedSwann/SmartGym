package menus;

import java.util.Scanner;

/**
 * Menu relatórios
 *
 * @author __________________
 * @version 1.0
 */
public class MenuRelatorio {
    private Scanner sc = new Scanner(System.in);
    public int exibirMenuRelatorio() {

        System.out.println("|-------------------------------------------|");
        System.out.println("|              MENU RELATÓRIOS              |");
        System.out.println("|-------------------------------------------|");
        System.out.println("|    1 - Detalhes do aluno                  |");
        System.out.println("|    2 - Ocupação das aulas                 |");
        System.out.println("|    3 - Detalhes da aula                   |");
        System.out.println("|    0 - Voltar                             |");
        System.out.println("|-------------------------------------------|");
        System.out.print("Selecione uma opção: ");

        return Integer.parseInt(sc.nextLine());
    }
}
