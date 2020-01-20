import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Grafo {
    public int numVertice;
    public int numMedianas;
    public ArrayList<Vertice> vertices = new ArrayList<>();
    public ArrayList<Vertice> medianas = new ArrayList<>();

    public Grafo(int numVertice, int numMedianas) {
        this.numVertice = numVertice;
        this.numMedianas = numMedianas;
    }

    public void gerarMedianas() {
        List numeros = new ArrayList();

        for (int i = 0; i < numVertice; i++) {
            numeros.add(i);
        }
        Collections.shuffle(numeros);
        for (int i = 0; i < numMedianas; i++) {
            int index = Integer.parseInt(numeros.get(i).toString());
            medianas.add(this.vertices.get(index));
        }
    }
}
