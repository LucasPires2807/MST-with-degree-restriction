import java.io.FileReader;
import java.util.Scanner;
import java.util.Vector;

import auxiliary.Partition;
import auxiliary.Validation2;
import auxiliary.Validation;
import auxiliary.SpanningTreeGenerator;
import ds.Edge;
import ds.Graph;
import ds.MyBitSet;
public class Main {

    public static Scanner getScanner(String get_file) throws Exception{
        try {
            Scanner entry_file = new Scanner(new FileReader(get_file));
            return entry_file;
        }catch (Exception e){
            System.out.println("Arquivo de entrada não encontrado, execução encerrada");
        }
        return null;
    }
    public static void main(String[] args){
        System.out.println("Digite o path para o arquivo de entrada");
        Scanner sc = new Scanner(System.in);
        String entry = sc.nextLine();
        try {
            sc = getScanner(entry);
        }catch (Exception e){
            return;
        }
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

        /*Graph g = new Graph(e);
        // MyBitSet bitset = new MyBitSet(g.getEdges().size());
        SpanningTreeGenerator stg = new SpanningTreeGenerator(n, d);
        Validation v = new Validation(n, d, e);

        stg.generateTreesIt();

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
        }*/
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

        Vector<Partition> list = new Vector<Partition>();
        Partition first = new Partition(e);
        list.add(first);
        Validation2 check = new Validation2();
        first.setMst(check.kruskal(n, e, new Vector<Edge>()).get());
        Graph toCheck = first.getMst();
        while(!check.degreesCheck(toCheck, d)){
            list.remove(first);
            check.addPartitions(first,list, n);
            if(list.isEmpty())break;
            first = check.minPart(list);
            toCheck = first.getMst();
        }
        System.out.println("Custo: " + toCheck.getTotalCost());
        System.out.println("Spanning tree:");
        for(Edge i : toCheck.getEdges()){
            System.out.println(i);
        }
    }
}
