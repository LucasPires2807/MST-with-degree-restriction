package ds;
import java.lang.Object;
import java.util.Vector;

public class Tree {
    private int father = -1;
    private int root = 0;
    private Vector<Tree> sons;
    private int sonsqtt = 0;
    Tree(int r){
        root = r;
        sons = new Vector<Tree>();
    }
    void addSon(Tree s){
        sons.add(s);
        sonsqtt++;
        s.setFather(root);
    }

    public int getFather() {
        return father;
    }

    public void setFather(int father) {
        this.father = father;
    }

    public int getRoot() {
        return root;
    }
}
