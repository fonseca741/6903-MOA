import java.util.ArrayList;

public class Grafo {
    private ArrayList<Vertice> vertices = new ArrayList<>();

    public ArrayList<Vertice> getVertices() {
        return vertices;
    }

    public void criarGrafo(int[] entrada){
        int cont = 0;
        for (int i=0; i < 4; i++){
            for (int j=0; j < 4; j++){
                this.vertices.add(new Vertice( i, j, entrada[cont]));
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
                soma += aux;
            }
            i++;
        }
        return soma;
    }

    public int h4(int h1, int h2, int h3, int p1, int p2, int p3){
        return p1*h1 + p2*h2 + p3*h3;
    }
//
//    public int h5(int h1, int h2, int h3){
//        if (h1 > h2 && h1 > h3) return h1;
//        if (h1 > h2 && h1 < h3) return h3;
//        if (h)
//    }
}
