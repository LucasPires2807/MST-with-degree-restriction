package ds;

import java.util.Map;
import java.util.Set;
import java.util.HashSet;
import java.util.Vector;
import java.util.BitSet;
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

    Graph(int d){
        degree = d;
    }

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
        Graph g = new Graph();
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
                g.nodes.add(e.origin);
                g.nodes.add(e.destination);
                g.connectVertices(e);
                first.union(second);
            }
        }

        return g;
    }

    /*
     * This method recieves a bitset representing a graph in the following way:
     *  {(A,B),(A,C),(A,D),...,(B,C),(B,D),...,(last but one, last)}
     * If the bitset isn't given in this way, the following algorithm won't
     * behave as expected.
     * The method is simply to check wethear a given bitset is representing
     * a spanning tree with a given degree restriction.
     * It uses four auxiliary methos for checking.
     */
    public boolean isBitsetSpanningTree(BitSet b){
        Graph g = new Graph();
        // Converting bitset to graph;
        for(int i = 0; i < b.size(); ++i){
            if(b.get(i) == true){    // edge in the graph
                edge e = bitToEdge(b, i);
                g.connectVertices(e);
            }
        }

        return !hasCycle(g) && respectDegree(g);
    }

    /*
     * This method will be called only when the given graph has v vertex and v-1 edges.
     * Working with this assumption, we have that:
     *      The given graph with the assumption have a cycle iff it has a disconnected vertex.
     * To check wethear the given graph has a cycle, we check for a disconnected vertex.
     * To do so, we check for each graph at the vertex if it has any incident edge.
     * If any edge isn't incident to every edge, then, the given graph has a cycle.
     * Thus, it isn't a spanning tree.
     */
    private boolean hasCycle(Graph g){
        char higherChar = (char) (65 + g.nodes.size()-1);
        Set<Character> nodes = new HashSet<Character>();
        // Check if the current letter is incident to any edge.
        for(char c = 'A'; c < higherChar; ++c){
            for(edge e : g.edges){
                if(c == e.origin || c == e.destination){
                    nodes.add(c);
                }
            }
        }
        return (g.nodes.size() == nodes.size());
    }

    /*
     * This method will be called only when the given graph has v vertex and v-1 edges,
     * no cycle - and consequently, no disconnected vertex.
     * Working with this assumption, we check how many edges are incident to every vertex.
     * If any graph has a number of incident edges higher than the deegre that the spanning
     * tree must respect, then the graph is not the one that we are looking for.
     */
    private boolean respectDegree(Graph g){
        char higherChar = (char) (65 + g.nodes.size()-1);
        Map<Character,Integer> vertexDegree = new HashMap<Character,Integer>(g.nodes.size());
        for(char node : g.nodes){
            vertexDegree.put(node,0);
        }
        for(edge e : edges){
            vertexDegree.replace(e.origin, vertexDegree.get(e.origin), vertexDegree.get(e.origin)+1);
            vertexDegree.replace(e.destination, vertexDegree.get(e.destination), vertexDegree.get(e.destination)+1);
        }
        for(Map.Entry<Character,Integer> entry : vertexDegree.entrySet()){
            if(entry.getValue() > degree){
                return false;
            }
        }
        return true;
    }

    /*
     * This method logic works as follows:
     * As an exemple, we have a complete graph with the edges A, B, C and D.
     * Each edge at the vector of edges is at the following order:
     *      (A,B), (A,C), (A,D), (B,C), (B,D), (C,D)
     * Note that there's always a subrange that the letter, from A to the previous of the last letter,
     * is the first at the pair at the subrange.
     * 
     * The method starts by taking the last pair, witch is always (previous of the last letter, last letter),
     * that can be denoted as [upperBound - count, upperbound] closed interval, with upperBound = b.size()-1 = 5
     * and count = 0.
     * 
     * At the condition of the while loop, it checks wethear the given index is at the current range.
     * If it isn't, the boundaries are tightened by incrementing the count and decreasing the upperBound by count
     * so that we have now the next subrange.
     * If it is, does a convertion in a proper way and then returns the edge.
     */
    private edge bitToEdge(BitSet b, int idx){
        int upperBound = b.size()-1; // A variable to check if the given i is in a determined range
        int count = 0;               // Auxiliary variable to help throught the algorithm
        while(upperBound - count <= idx && idx <= upperBound){
            upperBound -= ++count;
        }
        char origin = (char) (65 + nodes.size() - (count + 1));
        char destination = (char) ((int)origin + (idx - upperBound + count ) + 1);
        return new edge(origin,destination,0); // Necessário ajeitar o weight
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

    private Vector<edge> edges;
    private Vector<Character> nodes;
    private int degree;
}