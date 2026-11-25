# Filas de Prioridade e Heap: guia com as 3 questões

Tudo implementado à mão em Java: sem `StringBuilder`, sem `java.util` (exceto `Scanner`) e com todas as linhas comentadas.

## 1. O que é um heap?

**Fila de prioridade** é uma "fila" em que não sai quem chegou primeiro, e sim quem vence numa comparação (o menor preço, a maior nota, a maior urgência...).

**Heap** é só uma forma de guardar os itens para achar o vencedor rápido. Ele é uma árvore guardada dentro de um vetor comum:

```text
         3             posição 0 (raiz = o vencedor, sempre)
        / \
      8    6           posições 1 e 2

No vetor fica: [3, 8, 6]
```

A regra do heap (o **invariante**) é: todo pai vence seus filhos. Por isso o vencedor está sempre na posição 0.

Para achar parentes pela posição `i`:

| Quem | Fórmula |
| --- | --- |
| filho esquerdo | `2*i + 1` |
| filho direito | `2*i + 2` |
| pai | `(i - 1) / 2` |

Só duas operações mexem no heap:

- **Inserir (subir):** coloca o item no final e o faz subir, trocando com o pai enquanto vencer o pai.
- **Remover (descer):** tira a raiz, põe o último item na raiz e o faz descer, trocando com o filho mais forte, enquanto algum filho vencer ele.

### Exemplo à mão (fila de mínimo, inserindo 8, 3, 6)

- Insere 8: `[8]`
- Insere 3: entra na posição 1, o pai é 8, e 3 vence 8, então troca: `[3, 8]`
- Insere 6: entra na posição 2, o pai é 3, e 6 não vence 3, então fica: `[3, 8, 6]`
- `poll`: tira o 3, o último (6) vai para a raiz: `[6, 8]`. O filho é 8, que não vence 6, então para. O próximo é 6.

### Pilha x fila x fila de prioridade

| Estrutura | Regra de retirada | Ordem (Ana 2, Bruno 5, Carla 5) |
| --- | --- | --- |
| Pilha | Último a entrar sai primeiro | Carla, Bruno, Ana |
| Fila comum | Primeiro a entrar sai primeiro | Ana, Bruno, Carla |
| Fila de prioridade | Sai o item que vence a comparação | Bruno, Carla, Ana |

### Arquivo 1: `Comparador.java`

```java
// "interface" = um contrato: diz QUAL método deve existir, sem dizer como ele funciona
// <T> = "T" é um tipo qualquer (Integer, Pedido...), assim serve para tudo
interface Comparador<T> {
    // Recebe dois itens e responde quem deve sair primeiro:
    //   negativo -> primeiroItem sai antes
    //   zero     -> empate
    //   positivo -> segundoItem sai antes
    int comparar(T primeiroItem, T segundoItem);
} // fim da interface
```

O heap não sabe o que é "melhor". Quem diz isso é o comparador. Trocar o comparador troca a fila de mínimo por máximo, sem mexer no heap.

### Arquivo 2: `Heap.java`

```java
class Heap<T> { // classe do heap; T é o tipo dos itens guardados
    private Object[] itensGuardados;    // o vetor onde a árvore fica guardada
    private int quantidadeDeItens;      // quantos itens existem agora (não é o tamanho do vetor)
    private Comparador<T> comparador;   // a regra que decide quem vence

    // Construtor: roda quando você faz "new Heap<>(...)"
    Heap(int capacidade, Comparador<T> comparador) {
        this.itensGuardados = new Object[capacidade]; // cria o vetor com o tamanho máximo
        this.quantidadeDeItens = 0;                   // começa vazio
        this.comparador = comparador;                 // guarda a regra recebida
    } // fim do construtor

    // Pega o item de uma posição já convertido para o tipo T
    @SuppressWarnings("unchecked") // avisa o Java: "sei que esta conversão é segura"
    private T itemNaPosicao(int posicao) {
        return (T) itensGuardados[posicao]; // (T) converte o Object de volta para T
    } // fim de itemNaPosicao

    // Troca dois itens de lugar no vetor
    private void trocarDePosicao(int posicaoA, int posicaoB) {
        Object itemTemporario = itensGuardados[posicaoA];          // guarda A numa caixa auxiliar
        itensGuardados[posicaoA] = itensGuardados[posicaoB];       // A recebe o que era de B
        itensGuardados[posicaoB] = itemTemporario;                 // B recebe o que era de A
    } // fim de trocarDePosicao

    // SUBIR: usado depois de inserir; o item novo sobe enquanto vencer o pai
    private void subir(int posicaoAtual) {
        while (posicaoAtual > 0) { // posição 0 é a raiz: não tem pai, então para
            int posicaoDoPai = (posicaoAtual - 1) / 2; // fórmula do pai
            // compara o item atual com o pai; se NÃO for negativo, ele não vence
            if (comparador.comparar(itemNaPosicao(posicaoAtual), itemNaPosicao(posicaoDoPai)) >= 0) {
                break; // não vence o pai: o heap já está correto, pode parar
            } // fim do if
            trocarDePosicao(posicaoAtual, posicaoDoPai); // venceu: troca com o pai
            posicaoAtual = posicaoDoPai;                 // agora ele está na posição do pai; repete
        } // fim do while
    } // fim de subir

    // DESCER: usado depois de remover; o item da raiz desce enquanto algum filho vencer
    private void descer(int posicaoAtual) {
        while (true) { // repete até dar break
            int filhoEsquerdo = 2 * posicaoAtual + 1; // fórmula do filho esquerdo
            int filhoDireito = filhoEsquerdo + 1;      // o direito vem logo depois

            if (filhoEsquerdo >= quantidadeDeItens) { // esquerdo fora dos itens = não tem filhos
                break; // sem filhos não há para onde descer
            } // fim do if

            int filhoMaisPrioritario = filhoEsquerdo; // começa supondo que o esquerdo é o mais forte
            // se o direito existe E vence o esquerdo, ele passa a ser o mais forte
            if (filhoDireito < quantidadeDeItens
                    && comparador.comparar(itemNaPosicao(filhoDireito), itemNaPosicao(filhoEsquerdo)) < 0) {
                filhoMaisPrioritario = filhoDireito; // direito é o mais forte
            } // fim do if

            // se o filho mais forte NÃO vence o item atual, o heap já está correto
            if (comparador.comparar(itemNaPosicao(filhoMaisPrioritario), itemNaPosicao(posicaoAtual)) >= 0) {
                break; // para de descer
            } // fim do if
            trocarDePosicao(posicaoAtual, filhoMaisPrioritario); // filho venceu: troca com ele
            posicaoAtual = filhoMaisPrioritario;                 // item desceu; repete a partir daí
        } // fim do while
    } // fim de descer

    // INSERIR: coloca um item novo no heap
    void inserir(T novoItem) {
        if (novoItem == null) {                      // regra do guia: null não é permitido
            throw new NullPointerException();        // "lança" um erro de propósito
        } // fim do if
        if (quantidadeDeItens == itensGuardados.length) { // vetor cheio?
            throw new IllegalStateException();            // regra do guia: erro se passar da capacidade
        } // fim do if
        itensGuardados[quantidadeDeItens] = novoItem; // coloca no primeiro espaço livre (o final)
        subir(quantidadeDeItens);                     // faz ele subir até a posição certa
        quantidadeDeItens++;                          // agora existe 1 item a mais
    } // fim de inserir

    // CONSULTAR (peek): olha o vencedor sem retirar
    T consultarPrioritario() {
        if (quantidadeDeItens == 0) { // heap vazio?
            return null;              // regra do guia: devolve null
        } // fim do if
        return itemNaPosicao(0);      // o vencedor está sempre na raiz (posição 0)
    } // fim de consultarPrioritario

    // REMOVER (poll): retira e devolve o vencedor
    T removerPrioritario() {
        if (quantidadeDeItens == 0) { // heap vazio?
            return null;              // nada para remover
        } // fim do if
        T itemPrioritario = itemNaPosicao(0); // guarda o vencedor para devolver no final
        quantidadeDeItens--;                   // agora há 1 item a menos; o último fica em [quantidadeDeItens]
        itensGuardados[0] = itensGuardados[quantidadeDeItens]; // o último vai para a raiz (tapa o buraco)
        itensGuardados[quantidadeDeItens] = null;               // limpa o lugar antigo do último
        if (quantidadeDeItens > 0) { // se ainda sobrou algo...
            descer(0);               // ...o item da raiz desce até o lugar certo
        } // fim do if
        return itemPrioritario; // devolve o vencedor que foi removido
    } // fim de removerPrioritario

    int tamanho() {                  // quantos itens há
        return quantidadeDeItens;
    } // fim de tamanho

    boolean estaVazio() {            // true se não há nenhum item
        return quantidadeDeItens == 0;
    } // fim de estaVazio
} // fim da classe Heap
```

## Questão 1: Pare e pense (Ana, Bruno e Carla)
Pergunta: Carla pode sair antes de Ana? E antes de Bruno?

Ideia: maior prioridade vence. Se empatar, vence quem chegou antes. Carla (5) tem mais prioridade que Ana (2), então sim. Carla e Bruno empatam em 5, mas Bruno chegou antes, então Carla não sai antes dele.

### Arquivo: `Questao1.java`

```java
import java.util.Scanner; // permite ler o que o usuário digita

// Representa uma solicitação de atendimento
class Pedido {
    String nome;            // nome da pessoa
    int prioridade;         // quanto maior, mais urgente
    int ordemDeChegada;     // 1 = chegou primeiro, 2 = segundo...

    // Construtor: preenche os três campos ao criar um Pedido
    Pedido(String nome, int prioridade, int ordemDeChegada) {
        this.nome = nome;                     // "this.nome" é o campo; "nome" é o parâmetro
        this.prioridade = prioridade;         // guarda a prioridade
        this.ordemDeChegada = ordemDeChegada; // guarda a chegada
    } // fim do construtor
} // fim da classe Pedido

public class Questao1 {
    public static void main(String[] args) { // onde o programa começa
        Scanner leitor = new Scanner(System.in); // cria o leitor do teclado

        // Comparador feito com lambda: "(a, b) -> { ... }" é uma função curta
        // que cumpre o contrato da interface Comparador
        Comparador<Pedido> comparadorDePedidos = (pedidoA, pedidoB) -> {
            if (pedidoA.prioridade != pedidoB.prioridade) { // prioridades diferentes?
                // maior prioridade vence: -1 significa "pedidoA sai antes"
                return pedidoA.prioridade > pedidoB.prioridade ? -1 : 1;
            } // fim do if
            if (pedidoA.ordemDeChegada != pedidoB.ordemDeChegada) { // empate: olha a chegada
                // quem chegou antes (número menor) vence
                return pedidoA.ordemDeChegada < pedidoB.ordemDeChegada ? -1 : 1;
            } // fim do if
            return 0; // empate total
        }; // fim do comparador

        // Lê os três pedidos; a chegada é fixa: Ana 1, Bruno 2, Carla 3
        Pedido ana = new Pedido(leitor.next(), leitor.nextInt(), 1);   // lê nome e prioridade
        Pedido bruno = new Pedido(leitor.next(), leitor.nextInt(), 2);
        Pedido carla = new Pedido(leitor.next(), leitor.nextInt(), 3);

        // comparar(carla, ana) < 0 significa "Carla vence Ana" (sai antes)
        boolean carlaAntesDeAna = comparadorDePedidos.comparar(carla, ana) < 0;
        boolean carlaAntesDeBruno = comparadorDePedidos.comparar(carla, bruno) < 0;

        // ? : é um if curto: se verdadeiro imprime "Sim", senão "Nao"
        System.out.print("Carla sai antes de Ana? ");
        System.out.println(carlaAntesDeAna ? "Sim" : "Nao");
        System.out.print("Carla sai antes de Bruno? ");
        System.out.println(carlaAntesDeBruno ? "Sim" : "Nao");

        // Agora usa o heap de verdade para mostrar a ordem de atendimento
        Heap<Pedido> filaDePrioridade = new Heap<>(3, comparadorDePedidos); // capacidade 3
        filaDePrioridade.inserir(ana);   // heap: [Ana]
        filaDePrioridade.inserir(bruno); // Bruno vence Ana e sobe: [Bruno, Ana]
        filaDePrioridade.inserir(carla); // Carla não vence Bruno (chegou depois): [Bruno, Ana, Carla]

        System.out.print("Ordem de atendimento: ");
        while (!filaDePrioridade.estaVazio()) { // enquanto houver itens
            System.out.print(filaDePrioridade.removerPrioritario().nome); // tira o vencedor e imprime o nome
            System.out.print(" ");                                        // espaço entre nomes
        } // fim do while
        System.out.println(); // quebra de linha

        leitor.close(); // fecha o leitor
    } // fim do main
} // fim da classe Questao1
```

**Entrada:**

```text
Ana 2
Bruno 5
Carla 5
```

**Saída:**

```text
Carla sai antes de Ana? Sim
Carla sai antes de Bruno? Nao
Ordem de atendimento: Bruno Carla Ana
```

## Questão 2: Pratique (fila de mínimo e de máximo)
Pergunta: preços 120, 80, 100 (menor primeiro) e notas 70, 95, 80 (maior primeiro).

Ideia: a única diferença entre mínimo e máximo é o comparador. Em um, o menor vence. No outro, o maior vence.

### Arquivo: `Questao2.java`

```java
import java.util.Scanner; // leitura do teclado

public class Questao2 {
    public static void main(String[] args) {
        Scanner leitor = new Scanner(System.in); // leitor do teclado

         // Fila de MÍNIMO: o menor número vence
         Comparador<Integer> comparadorDeMinimo = (numeroA, numeroB) -> {
             if (numeroA < numeroB) return -1; // A é menor: A sai antes
             if (numeroA > numeroB) return 1; // B é menor: B sai antes
             return 0;                         // iguais: empate
         }; // fim do comparador de mínimo

         // Fila de MÁXIMO: o maior número vence (comparação invertida)
         Comparador<Integer> comparadorDeMaximo = (numeroA, numeroB) -> {
             if (numeroA > numeroB) return -1; // A é maior: A sai antes
             if (numeroA < numeroB) return 1; // B é maior: B sai antes
             return 0;                         // iguais: empate
         }; // fim do comparador de máximo

         // ----- Preços: menor sai primeiro -----
         int quantidadeDePrecos = leitor.nextInt(); // quantos preços serão lidos
         Heap<Integer> filaDePrecos = new Heap<>(quantidadeDePrecos, comparadorDeMinimo); // heap de mínimo
         for (int i = 0; i < quantidadeDePrecos; i++) { // repete uma vez por preço
             filaDePrecos.inserir(leitor.nextInt());    // lê um número e insere no heap
         } // fim do for

         System.out.print("Precos (minimo): ");
         while (!filaDePrecos.estaVazio()) { // enquanto houver preços
             System.out.print(filaDePrecos.removerPrioritario()); // tira o menor e imprime
             System.out.print(" ");                               // espaço
         } // fim do while
         System.out.println(); // quebra de linha

         // ----- Notas: maior sai primeiro -----
         int quantidadeDeNotas = leitor.nextInt(); // quantas notas serão lidas
         Heap<Integer> filaDeNotas = new Heap<>(quantidadeDeNotas, comparadorDeMaximo); // heap de máximo
         for (int i = 0; i < quantidadeDeNotas; i++) { // repete uma vez por nota
             filaDeNotas.inserir(leitor.nextInt());    // lê e insere
         } // fim do for

         System.out.print("Notas (maximo): ");
         while (!filaDeNotas.estaVazio()) { // enquanto houver notas
             System.out.print(filaDeNotas.removerPrioritario()); // tira a maior e imprime
             System.out.print(" ");                              // espaço
         } // fim do while
         System.out.println(); // quebra de linha

        leitor.close(); // fecha o leitor
    } // fim do main
} // fim da classe Questao2
```

**Entrada:**

```text
3
120 80 100
3
70 95 80
```

**Saída:**

```text
Precos (minimo): 80 100 120
Notas (maximo): 95 80 70
```

Passo a passo dos preços (mínimo): insere 120: [120]. Insere 80: sobe e vira [80, 120]. Insere 100: o pai é
80, não sobe, então [80, 120, 100]. Remove 80: o 100 vai para a raiz, vira [100, 120], e o filho 120 não vence, então para. Depois sai 100 e por fim 120.

## Questão 3: Exemplo (inserir 8, 3 e 6 em fila de mínimo)
O que mostrar: peek devolve 3 e mantém 3 itens. poll devolve 3 e deixa 2. O próximo peek devolve 6.

### Arquivo: `Questao3.java`

```java
import java.util.Scanner; // leitura do teclado

public class Questao3 {
    public static void main(String[] args) {
        Scanner leitor = new Scanner(System.in); // leitor do teclado

         // Fila de mínimo: o menor número vence
         Comparador<Integer> comparadorDeMinimo = (numeroA, numeroB) -> {
             if (numeroA < numeroB) return -1; // A menor: A sai antes
             if (numeroA > numeroB) return 1; // B menor: B sai antes
             return 0;                         // iguais
         }; // fim do comparador

         int quantidadeDeNumeros = leitor.nextInt(); // quantos números serão inseridos
         Heap<Integer> filaDeMinimo = new Heap<>(quantidadeDeNumeros, comparadorDeMinimo); // cria o heap
         for (int i = 0; i < quantidadeDeNumeros; i++) { // uma volta por número
             filaDeMinimo.inserir(leitor.nextInt());     // lê e insere (8, depois 3, depois 6)
         } // fim do for

         // peek: só olha o vencedor, não remove
         System.out.print("peek: ");
         System.out.println(filaDeMinimo.consultarPrioritario());   // 3

         // tamanho continua 3, porque peek não remove
         System.out.print("tamanho: ");
         System.out.println(filaDeMinimo.tamanho());                // 3

         // poll: remove e devolve o vencedor
         System.out.print("poll: ");
         System.out.println(filaDeMinimo.removerPrioritario());     // 3

         // agora há 2 itens
         System.out.print("tamanho: ");
         System.out.println(filaDeMinimo.tamanho());                // 2

         // o novo vencedor é 6
         System.out.print("peek: ");
         System.out.println(filaDeMinimo.consultarPrioritario());   // 6

        leitor.close(); // fecha o leitor
    } // fim do main
} // fim da classe Questao3
```

**Entrada:**

```text
3
8 3 6
```

**Saída:**

```text
peek: 3
tamanho: 3
poll: 3
tamanho: 2
peek: 6
```

## Como rodar

Coloque os 5 arquivos na mesma pasta (Comparador.java, Heap.java, Questao1.java, Questao2.java,
Questao3.java) e execute:
```bash
javac *.java
java Questao1
```
