package menus;

import java.util.Scanner;

/**
 * Menu frequencia
 *
 * @author __________________
 * @version 1.0
 */
public class MenuFrequencia {
    private Scanner sc = new Scanner(System.in);
    public int exibirMenuFrequencia() {


        System.out.println("|-------------------------------------------|");
        System.out.println("|              MENU FREQUÊNCIA              |");
        System.out.println("|-------------------------------------------|");
        System.out.println("|    1 - Registrar entrada                  |");
        System.out.println("|    2 - Registrar saída                    |");
        System.out.println("|    3 - Listar frequências                 |");
        System.out.println("|    4 - Relatório de frequência do aluno   |");
        System.out.println("|    0 - Voltar                             |");
        System.out.println("|-------------------------------------------|");
        System.out.print("Selecione uma opção: ");

        return Integer.parseInt(sc.nextLine());
    }
}
