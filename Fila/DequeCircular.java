/**
 * Deque genérico usando VETOR CIRCULAR — permite inserir/remover tanto no
 * início quanto no fim em O(1), sem deslocar elementos.
 *
 * Convenção de índices:
 *  - "inicio" aponta para o primeiro elemento (frente).
 *  - "fim" aponta para o último elemento (trás).
 *  - Para AVANÇAR um índice (ir pra frente): (indice + 1) % capacidade.
 *  - Para RECUAR um índice (ir pra trás): (indice - 1 + capacidade) % capacidade.
 *    Somamos "capacidade" antes do módulo porque em Java o resultado de
 *    "%" com número negativo pode ser negativo (diferente de Python) —
 *    somando capacidade garantimos um valor sempre >= 0 antes do módulo.
 */
public class DequeCircular<T> {

    private final Object[] dados;
    private final int capacidade;
    private int inicio;
    private int fim;
    private int quantidade;

    public DequeCircular(int capacidade) {
        this.capacidade = capacidade;
        this.dados = new Object[capacidade];
        this.inicio = 0;
        this.fim = -1;       // ainda não há elemento nenhum
        this.quantidade = 0;
    }

    public boolean vazio() { return quantidade == 0; }
    public boolean cheio() { return quantidade == capacidade; }
    public int tamanho() { return quantidade; }

    public void inserirFim(T elemento) {
        if (cheio()) {
            throw new RuntimeException("Deque cheio: não é possível inserir \"" + elemento + "\" no fim.");
        }
        fim = (fim + 1) % capacidade;   // avança
        dados[fim] = elemento;
        quantidade++;
    }

    public void inserirInicio(T elemento) {
        if (cheio()) {
            throw new RuntimeException("Deque cheio: não é possível inserir \"" + elemento + "\" no início.");
        }
        inicio = (inicio - 1 + capacidade) % capacidade; // recua
        dados[inicio] = elemento;
        quantidade++;
    }

    @SuppressWarnings("unchecked")
    public T removerInicio() {
        if (vazio()) {
            throw new RuntimeException("Deque vazio: não é possível remover do início.");
        }
        T valor = (T) dados[inicio];
        dados[inicio] = null;
        inicio = (inicio + 1) % capacidade; // avança
        quantidade--;
        return valor;
    }

    @SuppressWarnings("unchecked")
    public T removerFim() {
        if (vazio()) {
            throw new RuntimeException("Deque vazio: não é possível remover do fim.");
        }
        T valor = (T) dados[fim];
        dados[fim] = null;
        fim = (fim - 1 + capacidade) % capacidade; // recua
        quantidade--;
        return valor;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[frente -> ");
        int idx = inicio;
        for (int i = 0; i < quantidade; i++) {
            sb.append(dados[idx]);
            if (i < quantidade - 1) sb.append(", ");
            idx = (idx + 1) % capacidade;
        }
        sb.append(" <- trás]");
        return sb.toString();
    }
}
