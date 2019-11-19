import java.util.Scanner;

public class NPuzzle {
    public static int[] configFinal = new int[16];

    public static void doConfigFinal(){
        int j=1;
        for (int i = 0; i < 15; i++){
            configFinal[i] = j;
            j++;
        }
        configFinal[15] = 0;
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int[] entrada = new int[16];
        Grafo grafo = new Grafo();

        for (int i=0; i < 16; i++){
            entrada[i] = scan.nextInt();
        }

        doConfigFinal();
        grafo.criarGrafo(entrada);
        grafo.criarAdj();
        grafo.print();
        grafo.printAdj();

        System.out.println("Heurística 1: " + grafo.h1(configFinal));
    }
}
