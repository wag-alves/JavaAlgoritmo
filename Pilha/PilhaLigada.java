/**
 * Implementação de Pilha usando LISTA LIGADA (encadeada).
 *
 * Ideia: cada elemento vira um "No" que aponta para o nó abaixo dele.
 * A referência "topo" sempre aponta para o nó mais recente.
 * Empilhar = criar nó novo apontando para o topo atual e virar o novo topo.
 * Desempilhar = ler o valor do topo e "andar" para o próximo nó da pilha.
 * Não precisa de redimensionamento: cresce e encolhe nó a nó.
 */
public class PilhaLigada<T> implements Pilha<T> {

    /** Nó interno da lista ligada. Privado: detalhe de implementação. */
    private static class No<T> {
        T valor;
        No<T> proximo;

        No(T valor, No<T> proximo) {
            this.valor = valor;
            this.proximo = proximo;
        }
    }

    private No<T> topo;   // referência para o nó do topo; null quando vazia
    private int tamanho;  // contador auxiliar, evita percorrer a lista para saber o tamanho

    public PilhaLigada() {
        this.topo = null;
        this.tamanho = 0;
    }

    @Override
    public void empilhar(T elemento) {
        // o novo nó já nasce apontando para o antigo topo
        topo = new No<>(elemento, topo);
        tamanho++;
    }

    @Override
    public T desempilhar() {
        if (vazia()) {
            throw new RuntimeException("Pilha vazia: não é possível desempilhar.");
        }
        T valor = topo.valor;
        topo = topo.proximo; // "descarta" o nó antigo, o próximo vira o topo
        tamanho--;
        return valor;
    }

    @Override
    public T topo() {
        if (vazia()) {
            throw new RuntimeException("Pilha vazia: não há topo.");
        }
        return topo.valor;
    }

    @Override
    public boolean vazia() {
        return topo == null;
    }

    @Override
    public int tamanho() {
        return tamanho;
    }

    /** Mostra a pilha do topo para a base, útil para os "prints" pedidos nas questões. */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[topo -> ");
        No<T> atual = topo;
        while (atual != null) {
            sb.append(atual.valor);
            if (atual.proximo != null) sb.append(", ");
            atual = atual.proximo;
        }
        sb.append("]");
        return sb.toString();
    }
}
