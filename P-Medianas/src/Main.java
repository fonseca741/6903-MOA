import java.util.*;

public class Main {

    public static void main(String[] args) {
        int posx, posy, capacidade, demanda;
        Scanner scan = new Scanner(System.in);

        Grafo grafo = new Grafo(scan.nextInt(), scan.nextInt());

        for (int i = 0; i < grafo.numVertice; i++) {
            posx = scan.nextInt();
            posy = scan.nextInt();
            capacidade = scan.nextInt();
            demanda = scan.nextInt();
            grafo.vertices.add(new Vertice(capacidade, demanda, posx, posy));
        }

        grafo.gerarMedianas();
    }
}
