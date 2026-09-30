/**
 * QUESTÃO 02 — Validação de etiquetas HTML simplificadas.
 *
 * Regra: toda etiqueta aberta <nome> deve ser fechada como </nome>,
 * na ordem inversa da abertura — exatamente o comportamento de uma pilha:
 *  - ao encontrar <nome>  -> empilha "nome"
 *  - ao encontrar </nome> -> compara "nome" com o topo da pilha:
 *        * se bater, desempilha (fechamento correto)
 *        * se não bater ou a pilha estiver vazia, é um erro
 * No fim, se sobrar algo na pilha, há etiqueta(s) sem fechamento.
 *
 * Simplificações do enunciado: sem atributos, sem etiquetas autocontidas,
 * e as entradas sempre chegam completas (sem <p ou /p> soltos).
 */
public class ValidadorHtml {

    /**
     * Analisa o texto HTML simplificado e retorna uma mensagem indicando
     * se está bem formado ou qual foi o erro encontrado.
     */
    public static String htmlBemFormado(String texto, Pilha<String> pilha) {
        int i = 0;
        int n = texto.length();

        while (i < n) {
            char c = texto.charAt(i);

            if (c == '<') {
                int fimTag = texto.indexOf('>', i);
                if (fimTag == -1) {
                    return "Erro: etiqueta iniciada em '" + i + "' nunca é fechada com '>'.";
                }

                String conteudo = texto.substring(i + 1, fimTag); // sem os < >

                if (conteudo.startsWith("/")) {
                    // ---- etiqueta de FECHAMENTO ----
                    String nome = conteudo.substring(1);

                    if (pilha.vazia()) {
                        return "Erro: </" + nome + "> encontrada sem etiqueta de abertura correspondente.";
                    }

                    String esperado = pilha.topo();
                    if (!esperado.equals(nome)) {
                        return "Erro: esperava fechar <" + esperado + "> (topo da pilha), "
                                + "mas encontrou </" + nome + ">.";
                    }

                    pilha.desempilhar();
                    System.out.println("  Fechou </" + nome + ">. Pilha: " + pilha);

                } else {
                    // ---- etiqueta de ABERTURA ----
                    pilha.empilhar(conteudo);
                    System.out.println("  Abriu <" + conteudo + ">. Pilha: " + pilha);
                }

                i = fimTag + 1;
            } else {
                i++; // ignora qualquer texto fora de tags
            }
        }

        if (!pilha.vazia()) {
            return "Erro: etiqueta(s) aberta(s) e nunca fechada(s), pilha final: " + pilha;
        }
        return "HTML bem formado.";
    }

    /** Executa a análise dos três trechos do enunciado (a, b, c). */
    public static void executarCenario() {
        String[] rotulos = {"a", "b", "c"};
        String[] trechos = {
            "<pagina><titulo></titulo><p></p></pagina>",
            "<pagina><p></pagina></p>",
            "<lista><item></item><item></item></lista>"
        };

        for (int idx = 0; idx < trechos.length; idx++) {
            System.out.println("\nTrecho " + rotulos[idx] + ") " + trechos[idx]);
            Pilha<String> pilhaDoTrecho = new PilhaLigada<>(); // pode trocar por PilhaArray<>()
            String resultado = htmlBemFormado(trechos[idx], pilhaDoTrecho);
            System.out.println("  => " + resultado);
        }
    }
}
