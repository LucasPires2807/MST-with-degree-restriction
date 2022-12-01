package ds;

import java.lang.Object;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.Vector;
import java.util.Optional;

public class SpanningTreeGenerator {
    private int n;                    /// The number of nodes of the trees
    private int d;                    /// The maximum degree
    private Vector<MyBitSet> myTrees; ///Vector to store the tree candidates

    /**
     * SpanningTreeGenerator constructor
     *
     * The method creates an generator of spanning trees of n nodes and d maximum degree
     *
     * @param n The number of nodes
     * @param d Maximum degree
     */
    public SpanningTreeGenerator(int n, int d){
        this.n = n;
        this.d = d;
        myTrees = new Vector<>();
    }

//    private BitSet increment(MyBitSet b , boolean a){
//        System.out.println("tamanho b: "+b.size());
//        BitSet r = new BitSet(b.size()+1);
//        System.out.println("tamanho r: "+r.size());
//        for(int i = 0; i < b.size(); ++i){
//            r.set(i, b.get(i));
//        }
//        r.set(b.size(), a);
//        return r;
//    }

    /**
     * This method creates all the graphs of n nodes that has at most n-1 edges
     *
     * In each iteration, the method call itself to iterate through the
     * representations of the graph with one more edge represented (it may has or not the increased edge),
     * but in case the current graph has more edges then the maximum, it stops this execution path.
     * When we find a graph that has all the edges represented, we push it into 'MyTrees' vector
     * in case it has the correct number of edges, we do nothing in case otherwise.
     *
     * @param b the MyBitSet that represents the current graph.
     */
    private void generateTrees(MyBitSet b){
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

    /**
     * This method creates all the graphs of n nodes that has at most n-1 edges in case they where not created before
     *
     * In case 'MyTrees' was not nenerated, the method calls a private recursive method of same name,
     * otherwise, it does nothing
     */
    public void generateTrees(){
        if(myTrees.isEmpty()) generateTrees(new MyBitSet(0));
    }

    /**
     * A method to get myTrees if it was calculated
     * @return The optional of myTrees in case it were calculated, an empty Optional otherwise
     */
    public Optional<Vector<MyBitSet>> getBitsets(){
        if(myTrees.size() != 0){
            return Optional.of(myTrees);
        }else{
            return Optional.empty();
        }
    }

    /**
     * String with the SpannigTreeGenerator information
     * @return The string with the 'MyTrees' contents
     */
    @Override
    public String toString(){
        String ret = "n = " + n + ", d = " + d + ", myTrees' contents:\n";
        for(int i = 0; i < myTrees.size(); ++i){
            ret = ret + myTrees.elementAt(i).toString() + "\n";
        }
        return ret;
    }

     public static void main(String[] args) {
         SpanningTreeGenerator my_gen = new SpanningTreeGenerator(4,5);
         my_gen.generateTrees();
         System.out.println(my_gen.toString());
     }

}
