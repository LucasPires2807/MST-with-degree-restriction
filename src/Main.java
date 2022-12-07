import java.io.FileReader;
import java.io.InputStreamReader;
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
        Scanner sc = new Scanner(System.in);
        System.out.println("Working Directory = " + System.getProperty("user.dir"));
        System.out.println("Digite o path para o arquivo de entrada");
        String entry = sc.nextLine();

        System.out.println("Escolha o algoritmo:\n" + "1: algoritmo ingênuo\n" + "2: algoritmo otimizado");
        int choise = sc.nextInt();
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

        sc.close();

        boolean cont = true;
        Scanner get_type = new Scanner(System.in);
        while (cont){
            switch (choise){
                case 1:{
                    cont = false;
                    Graph g = new Graph(e);
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
                    System.out.println("Solução 1: ");
                    System.out.println("Custo: " + min);
                    System.out.println("Spanning tree:");
                    for(Edge i : g.getEdges()){
                        System.out.println(i);
                    }
                    break;
                }
                case 2:{
                    cont = false;
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
                    System.out.println("Solução 2: ");
                    System.out.println("Custo: " + toCheck.getTotalCost());
                    System.out.println("Spanning tree:");
                    for(Edge i : toCheck.getEdges()){
                        System.out.println(i);
                    }
                }
                break;
                default:{
                    System.out.println("Escolha inválida");
                    System.out.println("Escolha o algoritmo:\n" + "1: algoritmo ingênuo\n" + "2: algoritmo otimizado");
                    choise = get_type.nextInt();

                }
            }
        }




    }
}
