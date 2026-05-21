import menus.*;
import service.*;

/**
 * Classe principal responsável por iniciar o sistema SmartGym.
 *
 * @author __________________
 * @version 1.0
 */
public class Main {
    /**
     * Método principal que inicia a execução do sistema.
     */
    public static void main(String[] args) {

        MenuInicial menu = new MenuInicial();

        AlunoService alunoService = new AlunoService();
        InstrutorService instrutorService = new InstrutorService();
        PlanoService planoService = new PlanoService();
        AulaService aulaService = new AulaService();
        InscricaoService inscricaoService = new InscricaoService();
        FrequenciaService frequenciaService = new FrequenciaService();
        RelatorioService relatorioService = new RelatorioService();

        int opcao;

        do {
            opcao = menu.exibirmenu();

            switch (opcao) {

                case 1:
                    MenuAluno menuAluno = new MenuAluno();
                    int opcaoAluno;

                    do {
                        opcaoAluno = menuAluno.exibirMenuAluno();

                        switch (opcaoAluno) {
                            case 1:
                                alunoService.cadastrarAluno();
                                break;
                            case 2:
                                alunoService.listarAlunos();
                                break;
                            case 3:
                                alunoService.editarAluno();
                                break;
                            case 4:
                                alunoService.excluirAluno();
                                break;
                            case 0:
                                System.out.println("Voltando...");
                                break;
                            default:
                                System.out.println("Opção inválida!");
                        }

                    } while (opcaoAluno != 0);
                    break;

                case 2:
                    MenuInstrutor menuInstrutor = new MenuInstrutor();
                    int opcaoInstrutor;

                    do {
                        opcaoInstrutor = menuInstrutor.exibirMenuInstrutor();

                        switch (opcaoInstrutor) {
                            case 1:
                                instrutorService.cadastrarInstrutor();
                                break;
                            case 2:
                                instrutorService.listarInstrutores();
                                break;
                            case 3:
                                instrutorService.atualizarInstrutor();
                                break;
                            case 4:
                                instrutorService.removerInstrutor();
                                break;
                            case 0:
                                System.out.println("Voltando...");
                                break;
                            default:
                                System.out.println("Opção inválida!");
                        }

                    } while (opcaoInstrutor != 0);
                    break;

                case 3:
                    MenuPlano menuPlano = new MenuPlano();
                    int opcaoPlano;

                    do {
                        opcaoPlano = menuPlano.exibirMenuPlano();

                        switch (opcaoPlano) {
                            case 1:
                                planoService.cadastrarPlano();
                                break;
                            case 2:
                                planoService.listarPlanos();
                                break;
                            case 3:
                                planoService.atualizarPlano();
                                break;
                            case 4:
                                planoService.removerPlano();
                                break;
                            case 0:
                                System.out.println("Voltando...");
                                break;
                            default:
                                System.out.println("Opção inválida!");
                        }

                    } while (opcaoPlano != 0);
                    break;

                case 4:
                    MenuAula menuAula = new MenuAula();
                    int opcaoAula;

                    do {
                        opcaoAula = menuAula.exibirMenuAula();

                        switch (opcaoAula) {
                            case 1:
                                aulaService.cadastrarAula();
                                break;
                            case 2:
                                aulaService.listarAulas();
                                break;
                            case 3:
                                aulaService.editarAula();
                                break;
                            case 4:
                                aulaService.excluirAula();
                                break;
                            case 0:
                                System.out.println("Voltando...");
                                break;
                            default:
                                System.out.println("Opção inválida!");
                        }

                    } while (opcaoAula != 0);
                    break;

                case 5:
                    MenuInscricao menuInscricao = new MenuInscricao();
                    int opcaoInscricao;

                    do {
                        opcaoInscricao = menuInscricao.exibirMenuInscricao();

                        switch (opcaoInscricao) {
                            case 1:
                                inscricaoService.inscreverAluno();
                                break;
                            case 2:
                                inscricaoService.cancelarInscricao();
                                break;
                            case 3:
                                inscricaoService.listarInscricoes();
                                break;
                            case 4:
                                inscricaoService.listarInscricoesDoAluno();
                                break;
                            case 0:
                                System.out.println("Voltando...");
                                break;
                            default:
                                System.out.println("Opção inválida!");
                        }

                    } while (opcaoInscricao != 0);
                    break;

                case 6:
                    MenuFrequencia menuFrequencia = new MenuFrequencia();
                    int opcaoFrequencia;

                    do {
                        opcaoFrequencia = menuFrequencia.exibirMenuFrequencia();

                        switch (opcaoFrequencia) {
                            case 1:
                                frequenciaService.registrarEntrada();
                                break;
                            case 2:
                                frequenciaService.registrarSaida();
                                break;
                            case 3:
                                frequenciaService.listarFrequencias();
                                break;
                            case 4:
                                frequenciaService.relatorioAluno();
                                break;
                            case 0:
                                System.out.println("Voltando...");
                                break;
                            default:
                                System.out.println("Opção inválida!");
                        }

                    } while (opcaoFrequencia != 0);
                    break;

                case 7:
                    MenuRelatorio menuRelatorio = new MenuRelatorio();
                    int opcaoRelatorio;

                    do {
                        opcaoRelatorio = menuRelatorio.exibirMenuRelatorio();

                        switch (opcaoRelatorio) {
                            case 1:
                                relatorioService.detalhesAluno();
                                break;

                            case 2:
                                relatorioService.ocupacaoAulas();
                                break;

                            case 3:
                                relatorioService.detalhesAula();
                                break;

                            case 0:
                                System.out.println("Voltando...");
                                break;

                            default:
                                System.out.println("Opção inválida!");
                        }

                    } while (opcaoRelatorio != 0);

                    break;

                case 0:
                    System.out.println("Saindo do sistema...");
                    break;

                default:
                    System.out.println("Opção inválida!");
            }

        } while (opcao != 0);
    }
}
