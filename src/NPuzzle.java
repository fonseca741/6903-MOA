import java.util.Scanner;

public class NPuzzle {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int[] entrada = new int[16];
        Grafo grafo = new Grafo();

        for (int i=0; i < 16; i++){
            entrada[i] = scan.nextInt();
        }

        grafo.criarGrafo(entrada);
        grafo.criarAdj();
    }
}
