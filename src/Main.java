import java.util.Vector;
import java.util.Scanner;
import java.util.BitSet;
import ds.Edge;
import ds.Graph;
import ds.SpanningTreeGenerator;
import ds.MyBitSet;
import auxiliary.Validation;
import auxiliary.Partition;
public class Main {
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


    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int n, d;
        n = sc.nextInt();
        d = sc.nextInt();
        int edges[][] = new int[n][n]; // TODO Final ver se precisa tirar
        int x = 0;
        char origin = 'A';
        char destination = 'B';
        Vector<Edge> e = new Vector<Edge>();
        for(int i = x; i < n-1; i++){
            x++;
            for(int j = i+1; j < n; j++){
                edges[i][j] = sc.nextInt();
                e.add(new Edge(origin, destination, edges[i][j]));
                ++destination;
                if(destination - 'A' == n){
                    ++origin;
                    destination = (char) (origin+1);
                }
            }
        }

        Graph g = new Graph(e);
        MyBitSet bitset = new MyBitSet(0);
        SpanningTreeGenerator stg = new SpanningTreeGenerator(n, d);
        Validation v = new Validation(n, d, e);

        System.out.println("t1");
        stg.generateTreesIt();
        System.out.println("t2");

        Vector<MyBitSet> bs = stg.getBitsets().get();
        Vector<Graph> graphs = new Vector<Graph>();

        int min = Integer.MAX_VALUE;

       for(MyBitSet b : bs){
           if(v.isBitsetSpanningTree(b)){
               graphs.add(v.getGraph());
           }
       }
        for(Graph i : graphs){
            int value = i.getTotalCost();
            if(min > value){
                min = value;
                g = i;
            }
        }
       System.out.println("Custo: " + min);
       System.out.print("Vertices: {0");
       for(int i = 1; i < n; ++i){
        System.out.print(", " + ((i == n-1) ? i + "}" : i));
       }
       System.out.println();
       for(Edge i : g.getEdges()){
           System.out.println(i);
       }
        /*
        Com isso, o exemplo:
        5 2
        5 10 15 2
        21 2 45
        53 12
        13
        Será armazenado na matriz edge da seguinte forma:
           c1 c2 c3 c4 c5
        c1  0  5 10 15  2
        c2  0  0 21  2 45
        c3  0  0  0 53 12
        c4  0  0  0  0 13
        c5  0  0  0  0  0
         */
        sc.close();
        
        //part 2
        Vector<Partition> list = new Vector<Partition>();
        Partition first = new Partition(e);
        list.add(first);
        Graph toCheck = kruskal(n, e, new Vector<Edge>()).get();
        while(!degreesCheck(toCheck, d)){

        }
    }
}
