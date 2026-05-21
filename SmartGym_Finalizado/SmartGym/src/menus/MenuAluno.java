package menus;

import java.util.Scanner;

/**
 * Menu do aluno
 * @author __________________
 * @version 1.0
 */
public class MenuAluno {
    private Scanner sc = new Scanner(System.in);

    public int exibirMenuAluno() {

        System.out.println("|-------------------------------------------|");
        System.out.println("|                 MENU ALUNO                |");
        System.out.println("|-------------------------------------------|");
        System.out.println("|    1 - Cadastrar novo aluno               |");
        System.out.println("|    2 - Listar alunos                      |");
        System.out.println("|    3 - Atualizar aluno                    |");
        System.out.println("|    4 - Remover aluno                      |");
        System.out.println("|    0 - Voltar                             |");
        System.out.println("|-------------------------------------------|");

        System.out.print("Selecione uma opção: ");

        return Integer.parseInt(sc.nextLine());
    }
}
