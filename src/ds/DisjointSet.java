package ds;

public class DisjointSet <T>{

    DisjointSet(){/*Empty*/}

    void makeSet(T v){
        size = 0;
        father = this;
        value = v;
    }

    DisjointSet<T> union(DisjointSet<T> other){
        DisjointSet<T> fatherThis = find(this);
        DisjointSet<T> fatherOther = find(other);
        if(fatherThis.size > fatherOther.size){
            other.father = fatherThis;
        }else{
            fatherThis = other;
        }
        if(fatherThis.size == fatherOther.size){
            ++fatherOther.size;
        }
        return this.father;
    }

    DisjointSet<T> find(DisjointSet<T> ds){
        if(ds != ds.father){
            ds.father = find(ds.father);
        }
        return ds.father;
    }

    int size;
    DisjointSet<T> father;
    T value;
}
