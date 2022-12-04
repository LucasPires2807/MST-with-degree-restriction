package auxiliary;

import java.util.Vector;
import java.util.Stack;
import java.util.Optional;
import ds.MyBitSet;

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

    /**
     * This method creates all the graphs of n nodes that has at most n-1 edges
     *
     * In each iteration, the method call itself to iterate through the
     * representations of the graph with one more edge represented (it may has or not the increased edge),
     * but in case the current graph has more edges then the maximum, it stops this path.
     * When we find a graph that has all the edges represented, we push it into 'MyTrees' vector
     * in case it has the correct number of edges, we do nothing in case otherwise.
     *
     */
    public void generateTreesIt(){
        Stack<MyBitSet> pilha = new Stack<>();

        pilha.push(new MyBitSet(0));
        MyBitSet cur;

        while (!pilha.empty()){
            cur = pilha.peek();
            pilha.pop();

            if(cur.cardinality() > n-1) continue;
            if(cur.size() > (n*n - n)/2) continue;
            if(cur.size() == (n*n - n)/2){
                if(cur.cardinality() == n-1){
                    myTrees.add(cur);
                }
                continue;
            }

            pilha.push(cur.increment(false));
            pilha.push(cur.increment(true));
        }
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

    public int size(){
        return myTrees.size();
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

}
