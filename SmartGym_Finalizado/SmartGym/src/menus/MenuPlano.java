package menus;

import java.util.Scanner;

/**
 * Menu planos
 *
 * @author __________________
 * @version 1.0
 */
public class MenuPlano {
    private Scanner sc = new Scanner(System.in);
    public  int exibirMenuPlano(){

        System.out.println("|-------------------------------------------|");
        System.out.println("|                MENU PLANOS                |");
        System.out.println("|-------------------------------------------|");
        System.out.println("|    1 - Cadastrar novo Plano               |");
        System.out.println("|    2 - Listar Planos                      |");
        System.out.println("|    3 - Atualizar Planos                   |");
        System.out.println("|    4 - Remover Plano                      |");
        System.out.println("|    0 - Voltar                             |");
        System.out.println("|                                           |");
        System.out.println("|                                           |");
        System.out.println("|-------------------------------------------|");
        System.out.print("Selecione uma opção:  ");

        return Integer.parseInt(sc.nextLine());
    }
}
