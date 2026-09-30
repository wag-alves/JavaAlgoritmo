/**
 * Implementação de Pilha usando um ARRAY (vetor) como estrutura interna.
 *
 * Ideia: mantemos um vetor "dados" e um índice "topo" que aponta para a
 * posição do último elemento inserido. Empilhar = incrementar "topo" e
 * gravar na posição; desempilhar = ler a posição e decrementar "topo".
 * Quando o vetor enche, dobramos sua capacidade (redimensionar).
 */
public class PilhaArray<T> implements Pilha<T> {

    private Object[] dados;      // vetor interno (Object[] pois Java não permite T[] diretamente)
    private int topo;            // índice do elemento no topo; -1 quando vazia
    private static final int CAPACIDADE_INICIAL = 8;

    public PilhaArray() {
        dados = new Object[CAPACIDADE_INICIAL];
        topo = -1;
    }

    @Override
    public void empilhar(T elemento) {
        if (topo + 1 == dados.length) {
            redimensionar();
        }
        dados[++topo] = elemento; // pré-incrementa topo, depois grava
    }

    @Override
    @SuppressWarnings("unchecked")
    public T desempilhar() {
        if (vazia()) {
            throw new RuntimeException("Pilha vazia: não é possível desempilhar.");
        }
        T elemento = (T) dados[topo];
        dados[topo] = null;  // remove a referência para não vazar memória
        topo--;
        return elemento;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T topo() {
        if (vazia()) {
            throw new RuntimeException("Pilha vazia: não há topo.");
        }
        return (T) dados[topo];
    }

    @Override
    public boolean vazia() {
        return topo == -1;
    }

    @Override
    public int tamanho() {
        return topo + 1;
    }

    /** Dobra a capacidade do vetor interno quando ele enche. */
    private void redimensionar() {
        Object[] novo = new Object[dados.length * 2];
        System.arraycopy(dados, 0, novo, 0, dados.length);
        dados = novo;
    }

    /** Mostra a pilha do topo para a base, útil para os "prints" pedidos nas questões. */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[topo -> ");
        for (int i = topo; i >= 0; i--) {
            sb.append(dados[i]);
            if (i > 0) sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }
}
