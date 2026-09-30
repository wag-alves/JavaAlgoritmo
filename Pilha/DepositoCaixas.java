/**
 * QUESTÃO 03 — Retirada de caixas em um depósito.
 *
 * Só é possível mexer no topo da pilha, então para tirar uma caixa do meio:
 *   Fase 1 (retirada temporária): desempilha da pilha principal e empilha
 *            na auxiliar até encontrar o código procurado.
 *   Fase 2 (remoção): a caixa procurada é descartada (não volta a nenhuma pilha).
 *   Fase 3 (reconstrução): devolve as caixas da auxiliar para a principal.
 *
 * Por que a ordem final é preservada? Porque a caixa passa pela pilha
 * auxiliar duas vezes (uma indo, uma voltando) e cada passagem por uma
 * pilha inverte a ordem — duas inversões cancelam uma a outra.
 */
public class DepositoCaixas {

    /**
     * Remove a caixa "codigo" da pilha "principal", usando "auxiliar" como
     * apoio. Se a caixa não existir, a pilha principal termina exatamente
     * como começou.
     */
    public static void retirarCaixa(Pilha<String> principal, String codigo, Pilha<String> auxiliar) {
        System.out.println("  Pilha principal antes: " + principal);

        // ---- Fase 1: retirada temporária ----
        boolean encontrada = false;
        while (!principal.vazia()) {
            String atual = principal.desempilhar();
            if (atual.equals(codigo)) {
                encontrada = true;
                break; // achou: não empilha esta na auxiliar, ela some aqui
            }
            auxiliar.empilhar(atual);
        }
        System.out.println("  Após retirada temporária (procurando \"" + codigo + "\"):");
        System.out.println("    principal = " + principal);
        System.out.println("    auxiliar  = " + auxiliar);

        if (!encontrada) {
            // Caixa não existe: desfaz tudo, devolvendo a auxiliar para a principal
            System.out.println("  Caixa \"" + codigo + "\" não encontrada. Restaurando pilha original...");
            while (!auxiliar.vazia()) {
                principal.empilhar(auxiliar.desempilhar());
            }
            System.out.println("  Pilha principal restaurada: " + principal);
            return;
        }

        // ---- Fase 2: remoção ----
        System.out.println("  Caixa \"" + codigo + "\" removida (descartada).");

        // ---- Fase 3: reconstrução ----
        while (!auxiliar.vazia()) {
            principal.empilhar(auxiliar.desempilhar());
        }
        System.out.println("  Após reconstrução: " + principal);
    }

    /** Executa o cenário do enunciado: depósito A,B,C,D,E (A na base), remove C. */
    public static void executarCenario(Pilha<String> principal, Pilha<String> auxiliar) {
        // Empilha da base para o topo: A, B, C, D, E (E fica no topo)
        for (String caixa : new String[]{"A", "B", "C", "D", "E"}) {
            principal.empilhar(caixa);
        }

        retirarCaixa(principal, "C", auxiliar);
    }
}
