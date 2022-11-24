
import java.util.ArrayList;
import java.util.List;

public class Heap_generic<E extends Comparable<E> > {
    // heap_min
    private List<E> storage;

    Heap_generic(){
        storage = new ArrayList<>();
    }

    public E get_min(){
        return storage.get(0);
    }

    private void swap(int a, int b){
        E aux = storage.get(a);
        storage.set(a, storage.get(b));
        storage.set(b, aux);
    }

    private void descend(int pos){
        int son = pos * 2;
        if(son >= storage.size()) return;
        if(son+1 < storage.size()){
            if(storage.get(son+1).compareTo(storage.get(son)) > 0){
                son++;
            }
        }
        if(storage.get(son).compareTo(storage.get(pos)) > 0){
            swap(son, pos);
            descend(son);
        }
    }

    public boolean pop(){
        if(storage.size() < 1) return false;
        storage.set(0, storage.get(storage.size()-1));
        storage.remove(storage.size()-1);
        descend(0);
        return true;
    }

    private void ascend(int pos){
        if(pos == 0) return;
        int father = pos/2;
        if(storage.get(father).compareTo(storage.get(pos)) > 0){
            swap(father, pos);
            ascend(father);
        }
    }

    public void insert(E value){
        storage.add(value);
        ascend(storage.size()-1);
    }

    @Override
    public String toString(){
        String my_ret = "";
        for (int i = 0; i < storage.size(); ++i){
            my_ret += i + ": " + storage.get(i).toString() + "\n";
        }
        return my_ret;
    }


    public static void main(String[] args) {
        Heap_generic<Integer> my_heap = new Heap_generic<Integer>();

        my_heap.insert(1);
        my_heap.insert(2);
        my_heap.insert(1);
        my_heap.insert(3);
        my_heap.insert(5);

        System.out.println(my_heap.toString());
    }
}

