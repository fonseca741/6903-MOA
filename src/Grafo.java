import java.util.ArrayList;

public class Grafo {
    private ArrayList<Vertice> vertices = new ArrayList<>();
    private int pos0;
    private int f;
    private int g;
    private int h;

    public Grafo(){}

    public Grafo(ArrayList<Vertice> vertices, int pos0, int f, int g, int h) {
        this.vertices = vertices;
        this.pos0 = pos0;
        this.f = f;
        this.g = g;
        this.h = h;
    }

    public ArrayList<Vertice> getVertices() {
        return vertices;
    }

    public void criarGrafo(int[] entrada){
        int cont = 0;
        this.f = 0;
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

    public void print(){
        int i = 0;
        for (Vertice vertice : this.getVertices()){
            System.out.println("Vertice " + i + ":" + vertice.getValue());
            i++;
        }
        System.out.println(this.pos0);
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
    }

    public int h1() {
        int i = 0;
        int cont = 0;
        for (Vertice vertice : this.vertices) {
            if (vertice.getValue() != NPuzzle.configFinal.getVertices().get(i).getValue()){
                cont++;
            }
            i++;
        }
        return cont;
    }

    public int h2() {
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
        return cont;
    }

    public int h3(){
        int i = 0;
        int soma = 0;
        int aux;
        for (Vertice vertice : this.vertices) {
            if (vertice.getValue() != NPuzzle.configFinal.getVertices().get(i).getValue()){
                aux = ((vertice.getPosx() - NPuzzle.configFinal.getVertices().get(i).getPosx()) + (vertice.getPosx() - NPuzzle.configFinal.getVertices().get(i).getPosy()));
                if (aux < 0) {
                    aux = aux * -1;
                }
                soma += aux;
            }
            i++;
        }
        return soma;
    }

    public double h4(int h1, int h2, int h3, double p1, double p2, double p3){
        return p1*h1 + p2*h2 + p3*h3;
    }

    public int h5(int h1, int h2, int h3){
        if (h1 > h2 && h1 > h3) return h1;
        if (h2 > h1 && h2 > h3) return h2;
        return h3;
    }

    public Grafo copySwap(Vertice vertice){
        Grafo copia = new Grafo(this.vertices, this.pos0, this.f, this.g, this.h);
        copia.getVertices().get(this.pos0).setValue(vertice.getValue());
        copia.getVertices().get(vertice.getIndex()).setValue(0);
        this.pos0 = vertice.getIndex();

        return copia;
    }

    public ArrayList<Grafo> gerarSucessores(Grafo grafo){
        ArrayList<Grafo> retorno = new ArrayList<>();
        int tamListaAdj = grafo.getVertices().get(grafo.pos0).getAdj().size();
        for (int i = 0; i < tamListaAdj; i++) {
            Vertice adj = grafo.getVertices().get(this.pos0).getAdj().get(i);
            retorno.add(this.copySwap(adj));
        }
        return retorno;
    }
}
