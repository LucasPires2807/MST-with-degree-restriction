package auxiliary;

import ds.Graph;
import ds.Edge;
import ds.DisjointSet;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

/*
 * Simply converts a BitSet, with size n = m(m-1)/2 for any m natural, into a graph
 * in the following way:
 * {(A,B),(A,C),(A,D),...,(B,C),(B,D),...,(last but one, last)}.
 * And then checks if the generated graph is a spanning tree with the given degree
 * respected.
 */
public class Validation {

    public Validation(){/*Purposely empty*/}

    /*
     * The given weight matrix should be in the following form:
     * Edges weights:
     * 5 10 15 2 (<c1-c2> <c1-c3> <c1-c4> <c1-c5>)
     * 21 2 45   (<c2-c3> <c2-c4> <c2-c5>)
     * 53 12     (<c3-c4> <c3-c5>)
     * 13        (<c4-c5>)
     * Forms the matrix:
     *     c1 c2 c3 c4 c5
     *  c1  0  5 10 15  2
     *  c2  0  0 21  2 45
     *  c3  0  0  0 53 12
     *  c4  0  0  0  0 13
     *  c5  0  0  0  0  0
     */
    public Validation(int d, Vector<Edge> e){
        degree = d;
        edges = e;
    }

    /*
     * This method recieves a bitset representing a graph in the following way:
     *  {(A,B),(A,C),(A,D),...,(B,C),(B,D),...,(last but one, last)}
     * If the bitset isn't given in this way, the following algorithm won't
     * behave as expected.
     * The method is simply to check whether a given bitset is representing
     * a spanning tree with a given degree restriction.
     * It uses four auxiliary methods for checking.
     * Complexity: O(E)
     */
    public boolean isBitsetSpanningTree(BitSet b){
        Graph g = new Graph();
        // Converting bitset to graph;
        for(int i = 0; i < b.size(); ++i){
            if(b.get(i)){    // edge in the graph
                g.connectVertices(edges.get(i));
            }
        }
        isValid = isCandidate(g);
        graph = g;
        return isValid;
    }

    public Graph getGraph(){
        if(isValid){
            return graph;
        }
        return new Graph();
    }

    /*
     * This method works with the assumption that the given graph has v vertex and v-1 edges.
     * The goal of the method is to check whether the given graph respect a certain degree
     * and the graph is a spanning tree.
     * 
     * With the above assumpion we have:
     *      The given graph have a cycle iff it has a disconnected vertex.
     * To check it, at the end is needed to check if the quantity of connections of the disjoint set
     * is equal to the graph size, since it occur only when adding an edge does not make a cycle.
     * If it is different, there is a cycle. Thus, is not a candidate.
     * 
     * And for the vertex: for each edge, it will be incremented by one the value that the
     * map is mapping to.
     * If at the map exists a value greater than the degree, the graph doesn't respect the degree.
     * Thus, is not a candidate.
     * 
     * Complexity: O(E).
     */
    private boolean isCandidate(Graph g){
        DisjointSet<Character> ds = new DisjointSet<Character>();
        Map<Character, DisjointSet<Character>> m = new HashMap<Character, DisjointSet<Character>>();
        Map<Character,Integer> adjVertex = new HashMap<Character,Integer>();
        int connections = 0;
        for(char c : g.getNodes()){
            DisjointSet<Character> thisDisjointSet = ds.makeSet(c);
            m.put(c, thisDisjointSet);
        }
        for(Edge e : g.getEdges()){
            DisjointSet<Character> first = m.get(e.getOrigin());
            DisjointSet<Character> second = m.get(e.getDestination());
            if(ds.find(first) != ds.find(second)){
                first.union(second);
                ++connections;
            }
            if(adjVertex.containsKey(e.getOrigin())){ // If true, increments the quantity of adj vertex
                adjVertex.replace(e.getOrigin(), adjVertex.get(e.getOrigin()), adjVertex.get(e.getOrigin())+1);
                if(adjVertex.get(e.getOrigin()) > degree){ // If true, doesn't respect vertex degree restriction
                    return false;
                }
            } else{
                adjVertex.put(e.getOrigin(), 1);      // Initializes the vertex as incident one edge
            }
            if(adjVertex.containsKey(e.getDestination())){  // Same logic as above
                adjVertex.replace(e.getDestination(), adjVertex.get(e.getDestination()), adjVertex.get(e.getDestination())+1);
                if(adjVertex.get(e.getDestination()) > degree){
                    return false;
                }
            } else{
                adjVertex.put(e.getDestination(), 1);
            }
        }
        return connections == g.getNodeSize()-1;
    }

    /*
     * This method works with the assumption that the given graph has v vertex and v-1 edges.
     * The goal of the method is to check whether the given graph respect a certain degree
     * and the graph is a spanning tree.
     * 
     * With the above assumpion we have:
     *      The given graph have a cycle iff it has a disconnected vertex.
     * To check it, at the end is needed to check if the map size is equal to the graph size,
     * since a vertex is added only if an edge is incident to it.
     * If it is different, there is a cycle. Thus, is not a candidate.
     * 
     * And for the vertex: for each edge, it will be incremented by one the value that the
     * map is mapping to.
     * If at the map exists a value greater than the degree, the graph doesn't respect the degree.
     * Thus, is not a candidate.
     * 
     * Complexity: O(E).
     */
    // TODO: Refatorar
    // private boolean isCandidate(Graph g){
    //     Map<Character,Integer> adjVertex = new HashMap<Character,Integer>();
    //     for(Edge e : g.getEdges()){
    //         if(adjVertex.containsKey(e.getOrigin())){ // If true, increments the quantity of adj vertex
    //             adjVertex.replace(e.getOrigin(), adjVertex.get(e.getOrigin()), adjVertex.get(e.getOrigin())+1);
    //             if(adjVertex.get(e.getOrigin()) > degree){ // If true, doesn't respect vertex degree restriction
    //                 return false;
    //             }
    //         } else{
    //             adjVertex.put(e.getOrigin(), 1);      // Initializes the vertex as incident one edge
    //         }
    //         if(adjVertex.containsKey(e.getDestination())){  // Same logic as above
    //             adjVertex.replace(e.getDestination(), adjVertex.get(e.getDestination()), adjVertex.get(e.getDestination())+1);
    //             if(adjVertex.get(e.getDestination()) > degree){
    //                 return false;
    //             }
    //         } else{
    //             adjVertex.put(e.getDestination(), 1);
    //         }
    //     }
    //     return (adjVertex.size() == g.getNodeSize());
    // }

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
     * At the condition of the while loop, it checks whether the given index is at the current range.
     * If it isn't, the boundaries are tightened by incrementing the count and decreasing the upperBound by count
     * so that we have now the next subrange.
     * If it is, does a convertion in a proper way and then returns the edge.
     * 
     * Complexity: O(V).
     */
    // private Edge bitToEdge(BitSet b, int idx){
    //     int upperBound = b.size()-1; // A variable to check if the given i is in a determined range
    //     int count = 0;               // Auxiliary variable to help throught the algorithm
    //     while(upperBound - count <= idx && idx <= upperBound){
    //         upperBound -= ++count;
    //     }
    //     char origin = (char) (65 + vertexQuantity(b.size()) - (count + 1));
    //     char destination = (char) ((int)origin + (idx - upperBound + count ) + 1);
    //     int weight = weights[vertexQuantity(b.size())-(count+1)][(idx - upperBound + count) + 1]; // Check whether it is correct.
    //     return new Edge(origin,destination,weight);
    // }

    /*
     * This method converts the number of edges to the number of nodes in a complete graph.
     * Which means that n should be formed by m(m-1)/2 to any natural m.
     */
    // private int vertexQuantity(int n){
    //     return (int)(1 + Math.sqrt(1+8*n))/2;
    // }

    private int degree;
    private Vector<Edge> edges;
    private Graph graph;
    private boolean isValid;
}
