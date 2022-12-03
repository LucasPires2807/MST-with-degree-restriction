package ds;

import java.util.Map;
import java.util.Vector;
import java.util.HashMap;
import java.util.HashSet;

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
    
    public Graph(){
        super();
        nodesSet = new HashSet<>();
        edges = new Vector<Edge>();
        nodes = new Vector<>();
        nodesDegree = new HashMap<Character,Integer>();
    }

    public Graph(Vector<Edge> e){
        // Colocar nodes
        edges = e;
        nodesSet = new HashSet<>();
        nodes = new Vector<>();
        nodesDegree = new HashMap<Character,Integer>();
        for(Edge i : edges){
            if(!nodesSet.contains(i.getOrigin())){
                nodes.add(i.getOrigin());
                nodesSet.add(i.getOrigin());
            }
            if(!nodesSet.contains(i.getDestination())){
                nodes.add(i.getDestination());
                nodesSet.add(i.getDestination());
            }
        }
    }

    // Maybe unecessary
    // public Graph(int n, int[][] w){
    //     super();
    //     nodes = new Vector<>();
    //     edges = new Vector<>();
    //     nodesSet = new HashSet<>();
    //     weights = w;
    //     int x = 0;
    //     for(int i = x; i < n-1; i++){
    //         x++;
    //         for(int j = i+1; j < n; j++){
    //             edges.add(new Edge((char)(65+i),(char)(65+j),weights[i][j]));
    //         }
    //     }
    // }

    public void connectVertices(Edge e){
       for(Edge i : edges){
           if(i == e) break;
       }
        edges.add(e);
        nodesDegree.replace(e.getOrigin(),nodesDegree.get(e.getOrigin())+1);
        nodesDegree.replace(e.getDestination(),nodesDegree.get(e.getDestination())+1);
        totalCost += e.getWeight();
    }

    public void insertVertex(char v){
        if(nodesSet.contains(v)) return;
        nodes.add(v);
        nodesSet.add(v);
        nodesDegree.put(v,0);
    }

    public int getNodeSize(){
        return nodes.size();
    }

    public int getEdgesSize(){
        return edges.size();
    }

    public Vector<Character> getNodes(){
        return nodes;
    }

    public int getWeight(int x, int y){
        return weights[x][y];
    }

    public Edge getWeightByIndex(int i){
        try{
            return edges.elementAt(i);
        } catch(Exception e){
            System.out.println("Index out of range!");
        }
        return new Edge(); // Without it has an error.
    }

    public Vector<Edge> getEdges(){
        return edges;
    }

    public int getTotalCost(){
        return totalCost;
    }

    public Map<Character,Integer> getNodesDegree(){
        return nodesDegree;
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

        for(Edge e : edges){
            DisjointSet<Character> first = m.get(e.getOrigin());
            DisjointSet<Character> second = m.get(e.getDestination());
            if(ds.find(first) != ds.find(second)){
                g.nodes.add(e.getOrigin());
                g.nodes.add(e.getDestination());
                g.connectVertices(e);
                first.union(second);
            }
        }

        return g;
    }

    private Vector<Edge> edges;
    private Vector<Character> nodes;
    private int[][] weights;
    private HashSet<Character> nodesSet;
    private int totalCost = 0;
    private Map<Character,Integer> nodesDegree;
}
