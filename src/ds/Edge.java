package ds;

public class Edge {

    public Edge(){/*Currently empty*/}

    public Edge(char o, char d, int w){
        origin = o;
        destination = d;
        weight = w;
    }

    public char getOrigin(){
        return origin;
    }

    public char getDestination(){
        return destination;
    }

    public int getWeight(){
        return weight;
    }

    @Override
    public String toString() {
        return ("[" + (int)(origin - 65) + "," + (int)(destination - 65) + "]" + " = " + weight);
    }

    private char origin;
    private char destination;
    private int weight;
}
