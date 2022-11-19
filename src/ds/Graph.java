package ds;

import java.util.Vector;

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

    // public Graph kruskal(){
    //     Graph a = new Graph();
    //     DisjointSet ds = new DisjointSet<Character>();

    //     for(char node : nodes){
    //         ds.makeSet(node);
    //     }

    //     edges.sort(null); // precisa ordenar pelo valor dos pesos

    //     for(edge e : edges){
    //         if(ds.find(e.origin) != ds.find(e.destination)){
    //             a.nodes.add(e.origin);
    //             a.nodes.add(e.destination);
    //             a.connectVertices(e);
    //             // union both graphs in the ds
    //         }
    //     }

    //     return a;
    // }

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

    private Vector<edge> edges;
    private Vector<Character> nodes;
}