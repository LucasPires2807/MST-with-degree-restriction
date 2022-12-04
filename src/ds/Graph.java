package ds;

import java.util.Map;
import java.util.Vector;
import java.util.HashMap;
import java.util.HashSet;

public class Graph {
    
    public Graph(){
        super();
        nodesSet = new HashSet<>();
        edgesSet = new HashSet<>();
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

    public void connectVertices(Edge e){
        if(edgesSet.contains(e)) return;
        edges.add(e);
        edgesSet.add(e);
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

    public Vector<Character> getNodes(){
        return nodes;
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

    private Vector<Edge> edges;
    private Vector<Character> nodes;
    private HashSet<Character> nodesSet;
    private HashSet<Edge> edgesSet;
    private int totalCost = 0;
    private Map<Character,Integer> nodesDegree;
}
