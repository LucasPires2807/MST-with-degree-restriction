package auxiliary;

import ds.Graph;
import ds.Edge;
import ds.DisjointSet;
import ds.MyBitSet;
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

    public Validation(int n, int d, Vector<Edge> e){
        nodesToCheck = n;
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
    public boolean isBitsetSpanningTree(MyBitSet b){
        Graph g = new Graph();
        // Converting bitset to graph;
        for(int i = 0; i < b.size(); ++i){
            if(b.get(i)){    // edge in the graph
                g.connectVertices(edges.get(i));
                g.insertVertex(edges.get(i).getOrigin());
                g.insertVertex(edges.get(i).getDestination());
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
        int nodeCount = 0;
        int connections = 0;
      for(char c : g.getNodes()) {
          ++nodeCount;
          DisjointSet<Character> thisDisjointSet = new DisjointSet<>(c);
          m.put(c, thisDisjointSet);
      }
      if(nodeCount != nodesToCheck){
        return false;
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
        return connections == nodesToCheck-1;
    }

    private int degree;
    private Vector<Edge> edges;
    private Graph graph;
    private boolean isValid;
    private int nodesToCheck;
}
