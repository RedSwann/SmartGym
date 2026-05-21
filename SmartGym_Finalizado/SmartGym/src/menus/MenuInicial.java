package menus;

import java.util.Scanner;

/**
 * Menu inicial do sistema SmartGym.
 *
 * @author __________________
 * @version 1.0
 */
public class MenuInicial {
    public int exibirmenu(){
        Scanner sc = new Scanner(System.in);

        System.out.println("|-------------------------------------------|");
        System.out.println("|     Sistema De Gerenciamento Academia     |");
        System.out.println("|-------------------------------------------|");
        System.out.println("|    1 - Alunos                             |");
        System.out.println("|    2 - Instrutores                        |");
        System.out.println("|    3 - Planos                             |");
        System.out.println("|    4 - Criar Aula                         |");
        System.out.println("|    5 - Inscrições                         |");
        System.out.println("|    6 - Frequencia                         |");
        System.out.println("|    7 - Relatórios                         |");
        System.out.println("|    0 - Sair                               |");
        System.out.println("|-------------------------------------------|");
        System.out.print("Selecione uma das opções: ");
        return Integer.parseInt(sc.nextLine());
    }
}
