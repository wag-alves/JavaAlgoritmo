/**
 * Executa as três questões duas vezes cada: uma vez usando PilhaArray
 * e outra usando PilhaLigada, para mostrar que a lógica de negócio
 * (EditorDesenho, ValidadorHtml, DepositoCaixas) não depende de qual
 * implementação de Pilha está por trás — só da interface.
 */
public class Main {

    public static void main(String[] args) {

        System.out.println("=====================================================");
        System.out.println("QUESTÃO 01 — Editor de desenho (com PilhaArray)");
        System.out.println("=====================================================");
        EditorDesenho.executarCenario(new PilhaArray<>());

        System.out.println("\n=====================================================");
        System.out.println("QUESTÃO 01 — Editor de desenho (com PilhaLigada)");
        System.out.println("=====================================================");
        EditorDesenho.executarCenario(new PilhaLigada<>());

        System.out.println("\n=====================================================");
        System.out.println("QUESTÃO 02 — Validador de HTML");
        System.out.println("=====================================================");
        ValidadorHtml.executarCenario(); // internamente já demonstra com PilhaLigada

        System.out.println("\n=====================================================");
        System.out.println("QUESTÃO 03 — Depósito de caixas (com PilhaArray)");
        System.out.println("=====================================================");
        DepositoCaixas.executarCenario(new PilhaArray<>(), new PilhaArray<>());

        System.out.println("\n=====================================================");
        System.out.println("QUESTÃO 03 — Depósito de caixas (com PilhaLigada)");
        System.out.println("=====================================================");
        DepositoCaixas.executarCenario(new PilhaLigada<>(), new PilhaLigada<>());
    }
}
