package ds;

public class MyBitSet {
    private boolean []storage; /// The bits
    private int size;          /// The number of bits
    private int capacity;      /// The capacity of the storage

    /**
     * MyBitSet constructor
     *
     * Creates a MyBitSet with n bits
     * The value of the bits is not defined
     *
     * @param n The number of bits
     */
    public MyBitSet(int n){
        this.size = this.capacity = n;
        storage = new boolean[n];
    }

    /**
     * This method creates a new MyBitSet with the same contents of this
     * then increasses the size in one and sets the new bit to 'b'
     *
     * @param b The value of the new bit
     * @return The new MyBitSet that were created
     */
    public MyBitSet increment(boolean b){
        MyBitSet ret = new MyBitSet(size + 1);
        for (int i = 0; i < this.size; ++i){
            ret.storage[i] = storage[i];
        }
        ret.storage[this.size] = b;
        return  ret;
    }

    /**
     * Method that calculates the number os 'true' bits
     *
     * @return The number of 'true' bits
     */
    public int cardinality(){
        int ret = 0;
        for(int i = 0; i < size; ++i){
            if(storage[i])++ret;
        }
        return ret;
    }

    /**
     * Method to get the size of the MyBitSet
     * @return The number of bits
     */
    public int size(){
        return size;
    }

    /**
     * The method gets the value of the bit in position 'pos'
     *
     * @param pos The position to be accessed
     * @return The value of the bit in position 'pos'
     */
    public boolean get(int pos){
        return storage[pos];
    }

    /**
     * The method sets the value of the bit in position 'pos' to 'b'
     *
     * @param pos The position to be seted
     * @param b The new value of the bit in position 'pos'
     */
    public void set(int pos, boolean b){
        storage[pos] = b;
    }

    /**
     * String with the information of 'this' MyBitSet
     * @return The string with the 'storage' contents, the size of 'this' and the 'cardinality' of 'this'
     */
    @Override
    public String toString(){
        String ret = "bits: ";
        for(int i = 0; i < size; ++i){
            ret = ret + this.storage[i] + " ";
        }
        ret = ret + ",size: " + size + " , cardinality: " + cardinality();
        return  ret;
    }

    // public static void main(String[] args){
    //     MyBitSet a = new MyBitSet(0);
    //     MyBitSet at = a.increment(true);
    //     MyBitSet af = a.increment(false);
    //     MyBitSet att = at.increment(true);
    //     MyBitSet atf = at.increment(false);
    //     MyBitSet aft = af.increment(true);
    //     MyBitSet aff = af.increment(false);

    //     System.out.println(a.toString());
    //     System.out.println(at.toString());
    //     System.out.println(af.toString());
    //     System.out.println(att.toString());
    //     System.out.println(atf.toString());
    //     System.out.println(aft.toString());
    //     System.out.println(aff.toString());
        
    // }




}
