package ds;

import java.lang.Object;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.Vector;
import java.util.Optional;

public class SpanningTreeGenerator {
    private int n;
    private int d;
    private Vector<MyBitSet> myTrees;

    public SpanningTreeGenerator(int n, int d){
        this.n = n;
        this.d = d;
        myTrees = new Vector<>();
    }

    private BitSet increment(MyBitSet b , boolean a){
        System.out.println("tamanho b: "+b.size());
        BitSet r = new BitSet(b.size()+1);
        System.out.println("tamanho r: "+r.size());
        for(int i = 0; i < b.size(); ++i){
            r.set(i, b.get(i));
        }
        r.set(b.size(), a);
        return r;
    }

    
    public void generateTrees(MyBitSet b){
        if(b.cardinality() > n-1) return;
        if(b.size() > (n*n - n)/2) return;
        if(b.size() == (n*n - n)/2){
            if(b.cardinality() == n-1){
                myTrees.add(b);
            }
            return;
        }
        generateTrees(b.increment(true));
        generateTrees(b.increment( false));
    }

    public Optional<Vector<MyBitSet>> getBitsets(){
        if(myTrees.size() != 0){
            return Optional.of(myTrees);
        }else{
            return Optional.empty();
        }
    }

    public Vector<MyBitSet> get(){
        return myTrees;
    }

    public int size(){
        return myTrees.size();
    }

    @Override
    public String toString(){
        String ret = "";
        for(int i = 0; i < myTrees.size(); ++i){
            ret = ret + myTrees.elementAt(i).toString() + "\n";
        }
        return ret;
    }

    public static void main(String[] args) {
        SpanningTreeGenerator my_gen = new SpanningTreeGenerator(5,5);
        my_gen.generateTrees(new MyBitSet(0));
        System.out.println(my_gen.toString());
    }

}
