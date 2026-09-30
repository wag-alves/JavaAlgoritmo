/**
 * QUESTÃO 01 — Desfazer alterações em um editor de desenho.
 *
 * Cada ação vira uma String empilhada. "Desfazer" é simplesmente
 * desempilhar a ação mais recente — é o caso de uso clássico de pilha.
 */
public class EditorDesenho {

    private final Pilha<String> acoes;

    public EditorDesenho(Pilha<String> pilha) {
        this.acoes = pilha;
    }

    /** Registra uma nova ação no topo da pilha. */
    public void registrarAcao(String acao) {
        acoes.empilhar(acao);
        System.out.println("  Ação registrada: \"" + acao + "\"");
        System.out.println("  Pilha depois: " + acoes);
    }

    /** Desfaz a ação mais recente (topo da pilha), se houver. */
    public void desfazer() {
        System.out.println("  Pilha antes do desfazer: " + acoes);
        if (acoes.vazia()) {
            System.out.println("  Nada para desfazer (pilha vazia).");
            return;
        }
        String desfeita = acoes.desempilhar();
        System.out.println("  Desfazendo: \"" + desfeita + "\"");
        System.out.println("  Pilha depois do desfazer: " + acoes);
    }

    /** Executa o cenário do enunciado: 4 ações e depois 3 desfazeres. */
    public static void executarCenario(Pilha<String> pilha) {
        EditorDesenho editor = new EditorDesenho(pilha);

        System.out.println("-- Registrando as 4 ações --");
        editor.registrarAcao("criar círculo");
        editor.registrarAcao("pintar de azul");
        editor.registrarAcao("mover para direita");
        editor.registrarAcao("aumentar tamanho");

        System.out.println("\n-- Pressionando \"Desfazer\" 3 vezes --");
        for (int i = 1; i <= 3; i++) {
            System.out.println("\nDesfazer #" + i + ":");
            editor.desfazer();
        }
    }
}
