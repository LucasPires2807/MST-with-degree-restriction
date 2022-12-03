package auxiliary;

import ds.Edge;

import ds.Graph;

import java.util.Vector;

public class Partition {
    private Vector<Edge> included;
    private Vector<Edge> open;
    private Vector<Edge> excluded;

    private Graph mst;

    public Partition(Vector<Edge> o){
        included = new Vector<Edge>();
        open = o;
        excluded = new Vector<Edge>();
        mst = new Graph();
    }

    public Partition(Vector<Edge>i, Vector<Edge> o, Vector<Edge> e){
        included = i;
        open = o;
        excluded = e;
        mst = new Graph();
    }

    public void setMst(Graph mst) {
        this.mst = mst;
    }

    public Graph getMst() {
        return mst;
    }

    public Vector<Edge> getIncluded(){
        return included;
    }

    public Vector<Edge> getOpen(){
        return open;
    }

    public Vector<Edge> getExcluded(){
        return excluded;
    }

    public void newExcluded(Edge e){
        excluded.add(e);
    }
}
