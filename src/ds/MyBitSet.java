package ds;

public class MyBitSet {
    private boolean []storage;
    private int size;
    private int capacity;

    public MyBitSet(int n){
        this.size = this.capacity = n;
        storage = new boolean[n];
    }

    private void increaseCapacity(int new_capacity){
        if(new_capacity <= this.capacity) return;

        boolean []new_storage = new boolean[new_capacity];
        for (int i = 0; i < size; ++i){
            new_storage[i] = storage[i];
        }
        storage = new_storage;
        capacity = new_capacity;
    }

    public MyBitSet increment(boolean b){
        MyBitSet ret = new MyBitSet(size + 1);
        for (int i = 0; i < this.size; ++i){
            ret.storage[i] = storage[i];
        }
        ret.storage[this.size] = b;
        return  ret;
    }

    public int cardinality(){
        int ret = 0;
        for(int i = 0; i < size; ++i){
            if(storage[i])++ret;
        }
        return ret;
    }

    public int size(){
        return size;
    }

    public boolean get(int pos){
        return storage[pos];
    }

    public void set(int pos, boolean b){
        storage[pos] = b;
    }

    @Override
    public String toString(){
        String ret = "";
        for(int i = 0; i < size; ++i){
            ret = ret + this.storage[i] + " ";
        }
        return  ret;
    }


}
