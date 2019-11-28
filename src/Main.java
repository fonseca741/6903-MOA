import java.util.ArrayList;
import java.util.Scanner;

class Main {
    public static Grafo configFinal = new Grafo();
    public static ArrayList<Grafo> A = new ArrayList<>();
    public static ArrayList<Grafo> F = new ArrayList<>();

    public static boolean aStar(Grafo inicial){
        A.add(inicial);
        inicial.setPai(null);
        Grafo q;
        while (!A.isEmpty()){
            q = Grafo.findLeastF(A);
            A.remove(q);
            F.add(q);
            if ((q.getH() == 0)) {
                System.out.println(q.getG());
                return true;
            }
            for (Grafo sucessor : q.gerarSucessores()) {
                sucessor.findBetterG(A);

                if (!A.contains(sucessor) && !F.contains(sucessor)) {
                    A.add(sucessor);
                    sucessor.setPai(q);
                }
            }

        }
        return false;
    }

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

        aStar(grafo);
    }
}
