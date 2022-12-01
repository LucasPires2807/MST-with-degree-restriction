import java.util.Vector;
import java.util.Scanner;
import java.util.BitSet;
import ds.Edge;
import ds.Graph;
import ds.SpanningTreeGenerator;
import ds.MyBitSet;
import auxiliary.Validation;
public class Main {


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

        stg.generateTrees();

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
        System.out.println("Spanning tree:");
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
    }
}