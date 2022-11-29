package ds;

import java.lang.Object;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.Vector;

public class SpanningTreeGenerator {
    private int n;
    private int d;
    private Vector<BitSet> myTrees;

    public SpanningTreeGenerator(int n, int d){
        this.n = n;
        this.d = d;
        myTrees = new Vector<>();
    }

    private BitSet increment(BitSet b , boolean a){
        BitSet r = new BitSet(b.length()+1);
        for(int i = 0; i < b.length(); ++i){
            r.set(i, b.get(i));
        }
        r.set(b.length(), a);
        return r;
    }

    public void generateTrees(BitSet b){
        if(b.cardinality() > n-1) return;
        if(b.length() > (n*n - n)/2) return;
        if(b.length() == (n*n - n)/2){
            if(b.cardinality() == n-1){
                myTrees.add(b);
            }
            return;
        }
        generateTrees(increment(b, true));
        generateTrees(increment(b, false));
    }

}
