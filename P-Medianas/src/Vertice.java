public class Vertice {
    public int capacidadeMax;
    public int capacidadeAtual;
    public int demanda;
    public int posx;
    public int posy;
    public double distancia;

    public Vertice(int capacidadeMax, int capacidadeAtual, int demanda, int posx, int posy, double distancia) {
        this.capacidadeMax = capacidadeMax;
        this.capacidadeAtual = capacidadeAtual;
        this.demanda = demanda;
        this.posx = posx;
        this.posy = posy;
        this.distancia = distancia;
    }

    public double calcularDistancia(Vertice vertice) {
        return Math.sqrt(((this.posx - vertice.posx)*(this.posx - vertice.posx)) + ((this.posy - vertice.posy)*(this.posy - vertice.posy)));
    }

    public boolean coordIguais(Vertice vertice) {
        if (this.posy == vertice.posy && this.posx == vertice.posx) {
            return true;
        }
        return false;
    }
}
