package menus;

import java.util.Scanner;

/**
 * Menu Aula.
 *
 * @author __________________
 * @version 1.0
 */
public class MenuAula {

    private final Scanner sc = new Scanner(System.in);


    public  int exibirMenuAula(){

        System.out.println("|-------------------------------------------|");
        System.out.println("|                 MENU AULAS                |");
        System.out.println("|-------------------------------------------|");
        System.out.println("|    1 - Cadastrar nova Aula                |");
        System.out.println("|    2 - Listar Aulas                       |");
        System.out.println("|    3 - Atualizar Aula                     |");
        System.out.println("|    4 - Remover Aula                       |");
        System.out.println("|    0 - Voltar                             |");
        System.out.println("|                                           |");
        System.out.println("|                                           |");
        System.out.println("|-------------------------------------------|");
        System.out.print("Selecione uma opção:  ");

        return Integer.parseInt(sc.nextLine());
    }
}
