import java.util.*;

public class Main {

    public static PriorityQueue<Grafo> populacao = new PriorityQueue<>();

    public static void gerarPopulacaoInicial(Grafo grafoInicial) {

        grafoInicial.gerarMedianas();
        grafoInicial.attCapacidadeAndDistancia();
        grafoInicial.calcularFitness();
        populacao.add(grafoInicial);

        for (int i = 1; i < 7.5 * Math.log(grafoInicial.medianas.size()); i++) {
            Grafo aux = grafoInicial.copy();
            aux.gerarMedianas();
            aux.attCapacidadeAndDistancia();
            aux.calcularFitness();
            populacao.add(aux);
        }
    }

    public static void main(String[] args) {
        int posx, posy, capacidadeMax, demanda;
        Scanner scan = new Scanner(System.in);

        Grafo grafoInicial = new Grafo(scan.nextInt(), scan.nextInt());

        for (int i = 0; i < grafoInicial.numVertice; i++) {
            posx = scan.nextInt();
            posy = scan.nextInt();
            capacidadeMax = scan.nextInt();
            demanda = scan.nextInt();
            int capacidadeAtual = capacidadeMax - demanda;
            grafoInicial.vertices.add(new Vertice(capacidadeMax, capacidadeAtual, demanda, posx, posy, 0));
        }

        gerarPopulacaoInicial(grafoInicial);

        for (Grafo g : populacao) {
            g.printFitness();
        }
    }
}
