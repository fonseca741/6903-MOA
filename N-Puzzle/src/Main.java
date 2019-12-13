import java.util.*;

public class Main {

    public static String stringFormat(ArrayList<Vertice> input) {
        String retorno = "";
        for (Vertice vertice : input) {
            retorno = retorno.concat(Integer.toString(vertice.getValue()));
        }
        return retorno;
    }

    public static Grafo configFinal = new Grafo();
    public static PriorityQueue<Grafo> heap = new PriorityQueue<>();
    public static Map<String, Grafo> A = new HashMap<>();
    public static Map<String, Grafo> F = new HashMap<>();

    public static boolean aStar(Grafo inicial){
        heap.add(inicial);
        A.put(stringFormat(inicial.getVertices()), inicial);
        inicial.setPai(null);
        Grafo q;
        while (!A.isEmpty()){
            q = heap.poll();
            A.remove(stringFormat(q.getVertices()));
            F.put(stringFormat(q.getVertices()), q);
            if ((q.getH() == 0)) {
                System.out.println(q.getG());
                return true;
            }
            for (Grafo sucessor : q.gerarSucessores()) {
                Grafo grafoA = A.get(stringFormat(sucessor.getVertices()));
                if (grafoA != null && sucessor.getG() < grafoA.getG()) {
                    heap.remove(grafoA);
                    A.remove(stringFormat(grafoA.getVertices()));
                }

                if (!A.containsKey(stringFormat(sucessor.getVertices())) && !F.containsKey(stringFormat(sucessor.getVertices()))) {
                    heap.add(sucessor);
                    A.put(stringFormat(sucessor.getVertices()), sucessor);
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
