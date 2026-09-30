/**
 * QUESTÃO 05 — Pátio ferroviário com duas saídas (Leste e Oeste).
 *
 * Convenção: frente = Oeste, trás = Leste (usa DequeCircular por baixo,
 * que já implementa as fórmulas de avançar/recuar índice em vetor circular).
 * Capacidade escolhida como 4 ("linha curta"); nunca estoura nesse cenário,
 * mas cheio()/vazio() são tratados de qualquer forma.
 */
public class PatioFerroviario {

    private final DequeCircular<String> linha;

    public PatioFerroviario(int capacidade) {
        this.linha = new DequeCircular<>(capacidade);
    }

    public void entraLeste(String vagao) {
        linha.inserirFim(vagao); // Leste = trás
        System.out.println("  entraLeste(" + vagao + ") -> " + linha);
    }

    public void entraOeste(String vagao) {
        linha.inserirInicio(vagao); // Oeste = frente
        System.out.println("  entraOeste(" + vagao + ") -> " + linha);
    }

    public String saiLeste() {
        if (linha.vazio()) {
            System.out.println("  saiLeste() -> pátio vazio, nenhum vagão para retirar.");
            return null;
        }
        String vagao = linha.removerFim(); // Leste = trás
        System.out.println("  saiLeste() -> retirado " + vagao + " | pátio: " + linha);
        return vagao;
    }

    public String saiOeste() {
        if (linha.vazio()) {
            System.out.println("  saiOeste() -> pátio vazio, nenhum vagão para retirar.");
            return null;
        }
        String vagao = linha.removerInicio(); // Oeste = frente
        System.out.println("  saiOeste() -> retirado " + vagao + " | pátio: " + linha);
        return vagao;
    }

    /** Executa o cenário do enunciado. */
    public static void executarCenario() {
        PatioFerroviario patio = new PatioFerroviario(4);

        patio.entraLeste("V1");
        patio.entraLeste("V2");
        patio.entraOeste("V3");
        patio.saiLeste();
        patio.entraOeste("V4");
        patio.saiOeste();
        patio.entraLeste("V5");
    }
}
