package auxiliary;

import ds.DisjointSet;
import ds.Edge;
import ds.Graph;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Vector;

public class Validation2 {
    public static Partition minPart(Vector<Partition> l){
        Partition retorno = new Partition(new Vector<>());
        int min = Integer.MAX_VALUE;
        for(Partition p : l){
            if(p.getMst().getTotalCost()<min){
                retorno = p;
                min = p.getMst().getTotalCost();
            }
        }
        return retorno;
    }
    public static void addPartitions(Partition p, Vector<Partition> l, int nodesqtt){
        Graph mst = p.getMst();
        Vector<Edge> mstEdges = mst.getEdges();
        Vector<Edge> in = p.getIncluded();
        Vector<Edge> open = p.getOpen();
        Vector<Edge> out = p.getExcluded();
        for(Edge e: mstEdges){
            open.remove(e);
            Partition n = new Partition(in,open,out);
            Optional<Graph> g = kruskal(nodesqtt, open, in);
            if(g.isPresent()){
                n.setMst(g.get());
                n.newExcluded(e);
                l.add(n);
            }
            in.add(e);
        }
    }
    public static boolean degreesCheck(Graph g, int d){
        Map<Character,Integer> nodes = g.getNodesDegree();
        for (Map.Entry<Character,Integer> entry : nodes.entrySet()){
            if(entry.getValue()> d)
                return false;
        }
        return true;
    }
    public static Optional<Graph> kruskal(int n, Vector<Edge> edgesOrd, Vector<Edge> toAdd){
        //ordena todas as arestas
        edgesOrd.sort(new EdgeComparator());
        Graph g = new Graph();
        DisjointSet<Character> ds = new DisjointSet<Character>();
        Map<Character, DisjointSet<Character>> m = new HashMap<Character, DisjointSet<Character>>();
        char c = 'A';
        int edgescount = 0;
        int idx = 0;
        //adiciona os nós ao grafo
        for(int i = 0; i < n; i++){
            g.insertVertex(c);
            DisjointSet<Character> thisDisjointSet = ds.makeSet(c);
            m.put(c, thisDisjointSet);
            c++;
        }
        for(Edge e : toAdd){
            DisjointSet<Character> first = m.get(e.getOrigin());
            DisjointSet<Character> second = m.get(e.getDestination());
            if(ds.find(first) != ds.find(second)){
                first.union(second);
                g.connectVertices(edgesOrd.get(idx));
                ++edgescount;
            }else{
                return Optional.empty();
            }
        }
        //adiciona as arestas ao grafo. Se a aresta fosse realizar ciclo, não adiciona.
        //Como está ordenado, vai adicinando as menores
        while(edgescount != n-1){
            if(idx == (n * (n-1))/2) break;
            DisjointSet<Character> first = m.get(edgesOrd.get(idx).getOrigin());
            DisjointSet<Character> second = m.get(edgesOrd.get(idx).getDestination());
            if(ds.find(first) != ds.find(second)){
                first.union(second);
                g.connectVertices(edgesOrd.get(idx));
                ++edgescount;
            }
            idx++;
        }
        if(edgescount != n-1){
            return Optional.empty();
        }else return Optional.of(g);
    }

}
