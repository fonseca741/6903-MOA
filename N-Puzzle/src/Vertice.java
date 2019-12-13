import java.util.ArrayList;

public class Vertice {
    private ArrayList<Vertice> adj;
    private int posx;
    private int posy;
    private int value;
    private int index;

    public Vertice(){}

    public Vertice(int posx, int posy, int value, int index) {
        adj = new ArrayList<>();
        this.posx = posx;
        this.posy = posy;
        this.value = value;
        this.index = index;
    }

    public ArrayList<Vertice> getAdj() {
        return adj;
    }

    public int getPosx() {
        return posx;
    }

    public int getPosy() {
        return posy;
    }

    public int getValue() {
        return value;
    }

    public int getIndex() {
        return index;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
