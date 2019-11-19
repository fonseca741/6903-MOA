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

    public int h1(int[] configFinal) {
        int i = 0;
        int cont = 0;
        for (Vertice vertice : this.vertices) {
            if (vertice.getValue() != configFinal[i]){
                System.out.println("Passei aqui!");
                cont++;
            }
            i++;
        }
        return cont;
    }

    public int h2() {
        int cont = 0;
        for (int i = 0; i < 15; i++) {
            int posAtual = this.vertices.get(i).getValue();
            int posFuturo = this.vertices.get(i+1).getValue();
            if (posFuturo != posAtual + 1){
                if (posAtual != 0){
                    cont++;
                }
            }
        }
        return cont;
    }
}
