import java.util.Vector;
import java.util.Scanner;
import ds.Edge;
public class Main {
    public static void main(String[] args){
        // Para leitura
        Scanner sc = new Scanner(System.in);
        int n, d;
        n = sc.nextInt();
        d = sc.nextInt();
        int edges[][] = new int[n][n];
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
    }
}