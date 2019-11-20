import java.util.Scanner;

public class NPuzzle {
    public static Grafo configFinal = new Grafo();

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int[] entrada = new int[16];
        int[] padrao = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,0};
        Grafo grafo = new Grafo();
        configFinal.criarGrafo(padrao);

        for (int i=0; i < 16; i++){
            entrada[i] = scan.nextInt();
        }

        grafo.criarGrafo(entrada);
        grafo.criarAdj();
        grafo.print();
        grafo.printAdj();

        System.out.println("Heurística 1: " + grafo.h1());
        System.out.println("Heurística 2: " + grafo.h2());
        System.out.println("Heuristica 3: " + grafo.h3());
    }
}
