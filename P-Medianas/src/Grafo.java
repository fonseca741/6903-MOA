import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Grafo implements Comparable<Grafo>{
    public int numVertice;
    public int numMedianas;
    public List<Vertice> vertices = new ArrayList<>();
    public List<Vertice> medianas = new ArrayList<>();
    public double fitness;



    public Grafo(int numVertice, int numMedianas) {
        this.numVertice = numVertice;
        this.numMedianas = numMedianas;
    }

    public Grafo(int numVertice, int numMedianas, List<Vertice> vertices, List<Vertice> medianas, double fitness) {
        this.numVertice = numVertice;
        this.numMedianas = numMedianas;
        this.vertices = vertices;
        this.medianas = medianas;
        this.fitness = fitness;
    }

    public void gerarMedianas() {
        List numeros = new ArrayList();

        for (int i = 0; i < this.numVertice; i++) {
            numeros.add(i);
        }
        Collections.shuffle(numeros);
        this.medianas.clear();
        for (int i = 0; i < this.numMedianas; i++) {
            int index = Integer.parseInt(numeros.get(i).toString());
            this.medianas.add(this.vertices.get(index));
        }
    }

    public void calcularFitness() {
        double somaFinal = 0;
        for (Vertice v : this.vertices) {
            somaFinal = somaFinal + v.distancia;
        }
        this.fitness = somaFinal;
    }

    public void printFitness() {
        System.out.println("Fitness: " + this.fitness + "  Qntd medianas: " + this.medianas.size());
    }

    public Grafo deepCopy() {
        List<Vertice> verticesCopia = new ArrayList<>();
        List<Vertice> medianaCopia = new ArrayList<>();

        for (Vertice origem:this.vertices) {
            verticesCopia.add(new Vertice(origem.capacidadeMax, origem.capacidadeAtual, origem.demanda, origem.posx, origem.posy, origem.distancia));
        }
        for (Vertice origem:this.medianas) {
            medianaCopia.add(new Vertice(origem.capacidadeMax, origem.capacidadeAtual, origem.demanda, origem.posx, origem.posy, origem.distancia));
        }

        Grafo copia = new Grafo(this.numVertice, this.numMedianas, verticesCopia, medianaCopia, this.fitness);

        return copia;
    }

    public void attCapacidadeAndDistancia() {
        double distanciaAtual;
        int medianaIndice = 0;
        double distanciaMin;
        for (Vertice comum : this.vertices) {
            distanciaMin = 99999999;
            int indice = 0;
            for (Vertice mediana : this.medianas) {
                distanciaAtual = comum.calcularDistancia(mediana);
                if (distanciaAtual < distanciaMin && comum.demanda < mediana.capacidadeAtual) {
                    distanciaMin = distanciaAtual;
                    medianaIndice = indice;
                    comum.distancia = distanciaMin;
                }
                indice++;
            }
            this.medianas.get(medianaIndice).capacidadeAtual -= comum.demanda;
        }
    }

    @Override
    public int compareTo(Grafo grafo) {
        if (this.fitness > grafo.fitness) {
            return 1;
        } else if (this.fitness < grafo.fitness) {
            return -1;
        }else {
            return 0;
        }
    }
}
