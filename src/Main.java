import ds.Pair;

import java.util.Scanner;
import java.util.Vector;

public class Main {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int n, d;
        n = sc.nextInt();
        d = sc.nextInt();
        int edges[][] = new int[n][n];
        int x = 0;
        char origin = 'a';
        char destination = 'b';
        Vector<Pair> pairs = new Vector<Pair>();
        for(int i = x; i < n-1; i++){
            x++;
            for(int j = i+1; j < n; j++){
                edges[i][j] = sc.nextInt();
                pairs.add(new Pair(origin,destination));
                destination++;
                if(destination - 'a' == n){
                    origin++;
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
        for(Pair e : pairs){
            System.out.println("("+e.getElement0()+","+e.getElement1()+")");
        }
    }
}