package menus;

import java.util.Scanner;

/**
 * Menu instrutores
 *
 * @author __________________
 * @version 1.0
 */
public class MenuInstrutor {
    private Scanner sc = new Scanner(System.in);
    public  int exibirMenuInstrutor(){

        System.out.println("|-------------------------------------------|");
        System.out.println("|               MENU INSTRUTOR              |");
        System.out.println("|-------------------------------------------|");
        System.out.println("|    1 - Cadastrar novo instrutor           |");
        System.out.println("|    2 - Listar Instrutores                 |");
        System.out.println("|    3 - Atualizar Instrutor                |");
        System.out.println("|    4 - Remover Instrutor                  |");
        System.out.println("|    0 - Voltar                             |");
        System.out.println("|                                           |");
        System.out.println("|                                           |");
        System.out.println("|-------------------------------------------|");
        System.out.print("Selecione uma opção:  ");

        return Integer.parseInt(sc.nextLine());
    }

}
