package auxiliary;

import java.util.Comparator;

import ds.Edge;

public class EdgeComparator implements Comparator {

    public EdgeComparator(){}

    public int compare(Object a1, Object b2){
        Edge a = (Edge) a1;
        Edge b = (Edge) b2;
        if(a.getWeight() == b.getWeight()) return 0;
        if(a.getWeight() > b.getWeight()) return 1;
        else return -1;
    }
}
