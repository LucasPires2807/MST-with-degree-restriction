package ds;

public class DisjointSet <T>{

    public DisjointSet(){/*Empty*/}

    public DisjointSet<T> makeSet(T v){
        size = 0;
        father = this;
        value = v;
        return this;
    }

    public DisjointSet<T> union(DisjointSet<T> other){
        DisjointSet<T> fatherThis = find(this);
        DisjointSet<T> fatherOther = find(other);
        if(fatherThis.size > fatherOther.size){
            other.father = fatherThis;
        }else{
            this.father = fatherOther;
        }
        if(fatherThis.size == fatherOther.size){
            ++fatherOther.size;
        }
        return this.father;
    }

    // ASSUMPTION: Every value is unique at the disjoint set
    public DisjointSet<T> find(DisjointSet<T> ds){
        if(ds != ds.father){
            ds.father = find(ds.father);
        }
        return ds.father;
    }

    private int size;
    private DisjointSet<T> father;
    private T value;

}
