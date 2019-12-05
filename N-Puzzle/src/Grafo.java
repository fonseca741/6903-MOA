import java.util.ArrayList;

public class Grafo implements Comparable<Grafo>{
    private ArrayList<Vertice> vertices;
    private Grafo pai;
    private int pos0;
    private int g;
    private int h;
    private int f;

    public Grafo(){}

    public Grafo(Grafo pai, ArrayList<Vertice> vertices, int pos0, int g) {
        this.pai = pai;
        this.vertices = vertices;
        this.pos0 = pos0;
        this.g = g;
    }

    public void setPai(Grafo pai) {
        this.pai = pai;
    }

    public int getG() {
        return g;
    }

    public int getH() {
        return h;
    }

    public void calcF() {
        this.f = this.g + this.h;
    }

    public ArrayList<Vertice> getVertices() {
        return vertices;
    }

    public void criarAdj(){
        int i = 0;
        for (Vertice vertice: this.getVertices()) {
            if (vertice.getPosx() > 0) {
                vertice.getAdj().add(this.vertices.get(i-4));
            }
            if (vertice.getPosx() < 3) {
                vertice.getAdj().add(this.vertices.get(i+4));
            }
            if (vertice.getPosy() > 0) {
                vertice.getAdj().add(this.vertices.get(i-1));
            }
            if (vertice.getPosy() < 3) {
                vertice.getAdj().add(this.vertices.get(i+1));
            }
            i++;
        }
    }

    public void criarGrafo(int[] entrada){
        this.vertices = new ArrayList<>();
        int cont = 0;
        this.g = 0;
        this.h = 0;
        for (int i=0; i < 4; i++){
            for (int j=0; j < 4; j++){
                this.vertices.add(new Vertice( i, j, entrada[cont], cont));
                if (entrada[cont] == 0) {
                    this.pos0 = cont;
                }
                cont++;
            }
        }
        this.criarAdj();
        this.h1();
        this.calcF();
    }

//    Funções utilizadas apenas a título de implementação.
    /*
    public void print(){
        for (Vertice vertice : this.getVertices()){
            System.out.println("Index: " + vertice.getIndex() + ":" + vertice.getValue());
        }
        System.out.println("Posição do 0: " + this.pos0);
    }

    public void printAdj(){
        int i = 0;
        for (Vertice vertice : this.getVertices()) {
            System.out.println("Vertice " + i + ":" + vertice.getValue());
            for (Vertice adjacente : vertice.getAdj()) {
                System.out.println(adjacente.getValue());
            }
            i++;
        }
    }*/

    public void h1() {
        int i = 0;
        int cont = 0;
        for (Vertice vertice : this.vertices) {
            if (vertice.getValue() != Main.configFinal.getVertices().get(i).getValue()){
                cont++;
            }
            i++;
        }
        this.h = cont;
    }

    public void h2() {
        int cont = 0;
        for (int i = 0; i < 15; i++) {
            int valAtual = this.vertices.get(i).getValue();
            int valFuturo = this.vertices.get(i+1).getValue();
            if (valAtual != 0) {
                if (valFuturo != valAtual + 1) {
                    if (valAtual != 15 || valFuturo != 0) {
                        cont++;
                    }
                }
            }
        }
        this.h = cont;
    }

    public void h3(){
        int i = 0;
        int soma = 0;
        int aux;
        for (Vertice vertice : this.vertices) {
            if (vertice.getValue() != Main.configFinal.getVertices().get(i).getValue()){
                aux = ((vertice.getPosx() - Main.configFinal.getVertices().get(i).getPosx()) + (vertice.getPosx() - Main.configFinal.getVertices().get(i).getPosy()));
                if (aux < 0) {
                    aux = aux * -1;
                }
                soma += aux;
            }
            i++;
        }
        this.h =soma;
    }
//
//    public void h4(int h1, int h2, int h3, double p1, double p2, double p3){
//        this.h = p1*h1 + p2*h2 + p3*h3;
//    }

    public void h5(int h1, int h2, int h3){
        if (h1 > h2 && h1 > h3) this.h = h1;
        if (h2 > h1 && h2 > h3) this.h = h2;
        this.h = h3;
    }

    public Grafo copy() {
        ArrayList<Vertice> verticesCopia = new ArrayList<>();
        for (Vertice origem : this.getVertices()) {
            verticesCopia.add(new Vertice(origem.getPosx(), origem.getPosy(), origem.getValue(), origem.getIndex()));
        }
        Grafo copia = new Grafo(this, verticesCopia, this.pos0, this.g+1);
        copia.criarAdj();
        return copia;
    }

    public Grafo copySwap(Vertice adj) {
        Grafo copia = this.copy();
        copia.getVertices().get(this.pos0).setValue(adj.getValue());
        copia.getVertices().get(adj.getIndex()).setValue(0);
        copia.pos0 = adj.getIndex();
        copia.h1();
        copia.calcF();

        return copia;
    }

    public ArrayList<Grafo> gerarSucessores(){
        ArrayList<Grafo> retorno = new ArrayList<>();
        int tamListaAdj = this.getVertices().get(this.pos0).getAdj().size();
        for (int i = 0; i < tamListaAdj; i++) {
            Vertice adj = this.getVertices().get(this.pos0).getAdj().get(i);
            retorno.add(this.copySwap(adj));
        }
        return retorno;
    }

//    public void findBetterG(ArrayList<Grafo> A) {
//        for (Grafo grafoA: A) {
//            if (this.vertices.equals(grafoA.vertices) && this.g < grafoA.g) {
//                A.remove(grafoA);
//            }
//        }
//    }
//
//    public static Grafo findLeastF(ArrayList<Grafo> A){
//        Grafo least = new Grafo();
//        int leastValue = 100;
//        for (Grafo grafo: A) {
//            if ((grafo.f < leastValue)) {
//                leastValue = grafo.f;
//                least = grafo;
//            }
//        }
//        return least;
//    }

    @Override
    public int compareTo(Grafo grafo) {
        if (this.f > grafo.f) {
            return 1;
        } else if (this.f < grafo.f) {
            return -1;
        }else {
            return 0;
        }
    }
}
