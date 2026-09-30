/**
 * Contrato de uma Pilha (LIFO) genérica.
 * As três questões dependem apenas desta interface, nunca de uma
 * implementação específica — assim PilhaArray e PilhaLigada são
 * intercambiáveis (Program to an interface, not an implementation).
 */
public interface Pilha<T> {

    /** Empilha (push) um elemento no topo. */
    void empilhar(T elemento);

    /** Desempilha (pop) e retorna o elemento do topo. Lança exceção se vazia. */
    T desempilhar();

    /** Retorna o elemento do topo sem removê-lo (peek). Lança exceção se vazia. */
    T topo();

    /** Indica se a pilha está vazia. */
    boolean vazia();

    /** Quantidade de elementos na pilha. */
    int tamanho();
}
