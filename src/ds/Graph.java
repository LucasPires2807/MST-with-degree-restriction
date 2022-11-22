package ds;

import java.util.Map;
import java.util.Vector;
import java.util.HashMap;

/*
 * A quantidade de partições que serão geradas é a quantidade de arestas que o grafo terá no final n-1, sendo n a quantidade de
 * vértices.
 * 
 * Em um bitset, true significa que existe aquela aresta no grafo e false seria que não existe aquela aresta no grafo.
 * A forma de guardar no bitset ficará mais clara na segunda parte do exemplo.
 * 
 * Ex: (1,0,1,0,0,1) para o grafo (A,B), (A,C), (A,D), (B,C), (B,D), (C,D)
 *     Temos que existem as arestas (A,B), (A,D), (C,D) no grafo
 * 
 * Pode-se ser usado um vector de bitset para salvar todas as combinações de arestas possíveis em um grafo completo.
 * A forma de gerar todas as combinações será feita da seguinte forma:
 *    Na primeira coluna metade será true e metade será false;
 *    Na segunda coluna metade da metade será true, metade da metade será false e assim se segue até completar a segunda coluna;
 *    .
 *    .
 *    .
 *    Na n-ésima coluna será 2^n true, 2^n false e assim se segue até completar a n-ésima coluna.
 * 
 * Após gerar todas as combinações, pode-se filtrar para que no vector tenha apenas aqueles bitsets que tenham cardinalidade igual
 * a n-1.
 * 
 * Após gerar todas as combinações que possuem n-1 arestas, ainda precisamos saber se existe algum ciclo ou se é conexo. COMO?????
 * Provavelmente terá que formar todos esses grafos e checar se isso acontece.
 * 
 * Voltemos para o mesmo exemplo:
 * Converter do bitset para o vector de arestas basta seguir na mesma lógica que tem na entrada do programa:
 *    <valor de n> <valor de d>
 *    <custo c1---c2> <custo c1---c3> <custo c1---c4> … <custo c1---cn>
 *    <custo c2---c3> <custo c2---c4> … <custo c2---cn>
 *    <custo c3---c4> … <custo c3---cn>
 *    …<
 *    custo c<n-1>-cn>
 * Olhando de uma forma unidimensional desse formato.
 * Talvez seja necessário usar uma forma de acessar o valor da aresta a partir de seus nós. Um map dentro da classe das arestas?
 * 
 */

public class Graph {
    

    Graph(){/*Currently empty*/}

    public Graph connectVertices(edge e){
        for(edge i : edges){
            if(i == e)
                return this;
        }
        edges.add(e);
        return this;
    }

    public void insertVertex(char v){
        nodes.add(v);
    }

    // Usar para todas as combinações possíveis

    public Graph kruskal(){
        Graph a = new Graph();
        DisjointSet<Character> ds = new DisjointSet<Character>();
        Map<Character, DisjointSet<Character>> m = new HashMap<Character, DisjointSet<Character>>();

        for(char node : nodes){
            DisjointSet<Character> thisDisjointSet = ds.makeSet(node);
            m.put(node, thisDisjointSet);
        }

        edges.sort(null); // precisa ordenar pelo valor dos pesos

        for(edge e : edges){
            DisjointSet<Character> first = m.get(e.origin);
            DisjointSet<Character> second = m.get(e.destination);
            if(ds.find(first) != ds.find(second)){
                a.nodes.add(e.origin);
                a.nodes.add(e.destination);
                a.connectVertices(e);
                first.union(second);
            }
        }

        return a;
    }

    public class edge{
        edge(char o, char d, int w){
            origin = o;
            destination = d;
            weight = w;
        }
        private char origin;
        private char destination;
        private int weight;
    };

    // Refazer a parte do node para que seja possível usar o algoritmo prim
    // Um nó precisa guardar em um nó quantos outros nós estão conectados

    private Vector<edge> edges;
    private Vector<Character> nodes;
}