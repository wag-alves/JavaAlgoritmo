/**
 * QUESTÃO 04 — Histórico limitado de pesquisas (usa DequeCircular por baixo).
 *
 * Convenção: frente = mais antiga, trás = mais recente.
 */
public class HistoricoPesquisas {

    private static final int LIMITE = 5;
    private final DequeCircular<String> historico = new DequeCircular<>(LIMITE);

    public void registrarPesquisa(String termo) {
        if (historico.cheio()) {
            String antiga = historico.removerInicio(); // descarta a mais antiga (frente)
            System.out.println("  Limite atingido, descartando mais antiga: \"" + antiga + "\"");
        }
        historico.inserirFim(termo); // nova pesquisa entra no fim (mais recente)
        System.out.println("  Registrou \"" + termo + "\". Histórico: " + historico);
    }

    public void apagarMaisRecente() {
        if (historico.vazio()) {
            System.out.println("  Histórico vazio: nada para apagar.");
            return;
        }
        String removida = historico.removerFim(); // remove do fim (mais recente)
        System.out.println("  Apagou mais recente: \"" + removida + "\". Histórico: " + historico);
    }

    /** Executa o rastreio pedido no enunciado. */
    public static void executarCenario() {
        HistoricoPesquisas app = new HistoricoPesquisas();

        for (String termo : new String[]{"mapa", "clima", "ônibus", "cinema", "música"}) {
            app.registrarPesquisa(termo);
        }

        System.out.println("\n  -- registrar \"livros\" --");
        app.registrarPesquisa("livros");

        System.out.println("\n  -- apagar mais recente --");
        app.apagarMaisRecente();

        System.out.println("\n  -- registrar \"saúde\" --");
        app.registrarPesquisa("saúde");
    }
}
