/**
 * QUESTÃO 02 — Fila circular da impressora.
 *
 * Vetor de tamanho fixo (capacidade). "início" aponta pro próximo a sair,
 * "fim" aponta pra próxima posição livre. Os índices andam em círculo
 * com "% capacidade", então uma posição liberada por imprimir() é
 * reaproveitada automaticamente quando fim voltar a passar por ela —
 * sem nunca deslocar elementos.
 */
public class FilaCircularImpressora {

    private final String[] buffer;
    private final int capacidade;
    private int inicio;
    private int fim;
    private int quantidade;

    public FilaCircularImpressora(int capacidade) {
        this.capacidade = capacidade;
        this.buffer = new String[capacidade];
        this.inicio = 0;
        this.fim = 0;
        this.quantidade = 0;
    }

    public void enfileirar(String trabalho) {
        if (quantidade == capacidade) {
            throw new RuntimeException("Buffer cheio: não é possível enfileirar \"" + trabalho + "\".");
        }
        buffer[fim] = trabalho;
        fim = (fim + 1) % capacidade; // avança e "dá a volta" ao chegar no fim do vetor
        quantidade++;
    }

    /** Corresponde ao imprimir() do enunciado. */
    public String desenfileirar() {
        if (quantidade == 0) {
            throw new RuntimeException("Buffer vazio: não há trabalho para imprimir.");
        }
        String trabalho = buffer[inicio];
        inicio = (inicio + 1) % capacidade; // libera a posição sem deslocar nada
        quantidade--;
        return trabalho;
    }

    public boolean vazia() { return quantidade == 0; }
    public boolean cheia() { return quantidade == capacidade; }

    /** Ajuda a acompanhar o rastro pedido na questão (vetor físico + índices). */
    public void mostrarEstado() {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < capacidade; i++) {
            sb.append(buffer[i] == null ? "_" : buffer[i]);
            if (i < capacidade - 1) sb.append(",");
        }
        sb.append("]");
        System.out.println("  vetor=" + sb + " início=" + inicio + " fim=" + fim + " qtd=" + quantidade);
    }

    /** Executa o cenário do enunciado. */
    public static void executarCenario() {
        FilaCircularImpressora fila = new FilaCircularImpressora(4);

        fila.enfileirar("A"); fila.mostrarEstado();
        fila.enfileirar("B"); fila.mostrarEstado();
        fila.enfileirar("C"); fila.mostrarEstado();
        System.out.println("  imprimir() -> " + fila.desenfileirar()); fila.mostrarEstado();
        fila.enfileirar("D"); fila.mostrarEstado();
        fila.enfileirar("E"); fila.mostrarEstado();
        System.out.println("  imprimir() -> " + fila.desenfileirar()); fila.mostrarEstado();
        fila.enfileirar("F"); fila.mostrarEstado();
    }
}
