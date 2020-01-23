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

    public boolean equals(Vertice vertice) {
        if (this.posx == vertice.posx && this.posy == vertice.posy) {
            return true;
        }
        return false;
    }

    public Vertice deepCopy() {
        double distanciaCopia = this.distancia;
        Vertice copia = new Vertice(this.capacidadeMax, this.capacidadeAtual, this.demanda, this.posx, this.posy, distanciaCopia);
        return copia;
    }
}
