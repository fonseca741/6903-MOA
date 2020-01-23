import java.util.*;
import java.util.stream.Collectors;

public class Main {

    public static ArrayList<Grafo> populacaoInicial = new ArrayList<>();
    public static ArrayList<Grafo> novaGeracao = new ArrayList<>();
    public static ArrayList<Grafo> reprodutores = new ArrayList<>();

    public static void gerarPopulacaoInicial(Grafo grafoInicial) {

        grafoInicial.gerarMedianas();
        grafoInicial.attCapacidadeAndDistancia();
        grafoInicial.calcularFitness();
        populacaoInicial.add(grafoInicial);

        for (int i = 1; i < 7.5 * Math.log(grafoInicial.numMedianas); i++) {
            Grafo aux = grafoInicial.deepCopy();
            aux.gerarMedianas();
            aux.attCapacidadeAndDistancia();
            aux.calcularFitness();
            populacaoInicial.add(aux);
        }
        Collections.sort(populacaoInicial);
    }

    public static void selecao(ArrayList<Grafo> populacao) {
        reprodutores.clear();
        Random gerador = new Random();
        ArrayList<Integer> reprodutoresIndice = new ArrayList<>();

        for (int i = 0; i < 2; i++) {
            int aux = gerador.nextInt(populacao.size());
            if (!reprodutoresIndice.contains(aux)) {
                reprodutoresIndice.add(aux);
            } else {
                i--;
            }
        }
        for (Integer i:reprodutoresIndice) {
            reprodutores.add(populacao.get(i).deepCopy());
        }
//        reprodutores.add(populacao.get(0));
//        reprodutores.add(populacao.get(1));
    }

    public static Grafo cruzamento(Grafo pai, Grafo mae) {
        Grafo paiCopia = pai;
        Grafo maeCopia = mae;
        Grafo filho = pai.deepCopy();
        filho.medianas.clear();
        filho.fitness = 0;
        //adiciona a interseção dos 2
        for (Vertice medianaPai : paiCopia.medianas) {
            for (Vertice medianaMae : maeCopia.medianas) {
                if (medianaPai.equals(medianaMae)) {
                    filho.medianas.add(medianaMae.deepCopy());
//                    paiCopia.medianas = paiCopia.medianas.stream().filter(item -> !(item.equals(medianaMae))).collect(Collectors.toList());
//                    maeCopia.medianas = maeCopia.medianas.stream().filter(item -> !(item.equals(medianaPai))).collect(Collectors.toList());
                }
            }
        }

        System.out.println("Num medianas Pai: " + paiCopia.medianas.size());
        System.out.println("Num medianas filho: " + filho.medianas.size());
        Random gerador = new Random();
        int randomico;
        List<Integer> escolhidos = new ArrayList<>();
        escolhidos.clear();
        while (filho.medianas.size() < paiCopia.numMedianas / 2) {
            randomico = gerador.nextInt(paiCopia.medianas.size() - 1);
            if (!escolhidos.contains(randomico)) {
                filho.medianas.add(paiCopia.medianas.get(randomico).deepCopy());
            }
            escolhidos.add(randomico);
        }
        escolhidos.clear();
        while (filho.medianas.size() < maeCopia.numMedianas) {
            randomico = gerador.nextInt(maeCopia.medianas.size()-1);
            if (!escolhidos.contains(randomico)) {
                filho.medianas.add(maeCopia.medianas.get(randomico).deepCopy());
            }
            escolhidos.add(randomico);
        }

        filho.attCapacidadeAndDistancia();
        filho.calcularFitness();

        return filho.deepCopy();
    }
    //Escolhe 2 reprodutores aleatórios

    //TODO: ARRUMAR A MUTAÇÃO, ERRO DE REFERÊNCIA
    public static void gerarMutacao(ArrayList<Grafo> populacao) {
        Random gerador = new Random();
        int num1 = gerador.nextInt(populacao.size());
        int num2 = gerador.nextInt(populacao.get(1).numVertice);
        boolean existe = false;

        populacao.get(num1).medianas.remove(populacao.get(num1).medianas.size() - 1);
        int i = 0;
        while (i < 15) {
            for (Vertice v : populacao.get(num1).medianas) {
                if (v.equals(populacao.get(num1).vertices.get(num2))) {
                    existe = true;
                }
            }
            if (!existe) {
                populacao.get(num1).medianas.add(populacao.get(num1).vertices.get(num2));
                populacao.get(num1).attCapacidadeAndDistancia();
                populacao.get(num1).calcularFitness();
                break;
            }
            i++;
        }
    }

    public static void attPopulcao(Grafo teste) {
        teste.attCapacidadeAndDistancia();
        teste.calcularFitness();
        novaGeracao.clear();
        for (int i = 0; i < populacaoInicial.size() - 2; i++) {
            novaGeracao.add(populacaoInicial.get(i).deepCopy());
        }
        if (populacaoInicial.get(populacaoInicial.size() - 1).fitness > teste.fitness) {
            novaGeracao.add(teste.deepCopy());
        } else {
            novaGeracao.add(populacaoInicial.get(populacaoInicial.size() - 1).deepCopy());
        }

    }

    public static void algGenetico(Grafo grafoInicial) {
        int i = 0;
        Grafo filho;

        gerarPopulacaoInicial(grafoInicial);
        System.out.println("------------ANTES--------------");
        for (Grafo g : populacaoInicial) {
            g.printFitness();
        }
        while (i < 500) {
            selecao(populacaoInicial);
            filho = cruzamento(reprodutores.get(0), reprodutores.get(1));
//            gerarMutacao(populacaoInicial);
            attPopulcao(filho);
            Collections.sort(populacaoInicial);
            i++;
        }
    }

    public static void main(String[] args) {
        int posx, posy, capacidadeMax, demanda;
        Scanner scan = new Scanner(System.in);

        Grafo grafoInicial = new Grafo(scan.nextInt(), scan.nextInt());

        for (int i = 0; i < grafoInicial.numVertice; i++) {
            posx = scan.nextInt();
            posy = scan.nextInt();
            capacidadeMax = scan.nextInt();
            demanda = scan.nextInt();
            int capacidadeAtual = capacidadeMax - demanda;
            grafoInicial.vertices.add(new Vertice(capacidadeMax, capacidadeAtual, demanda, posx, posy, 0));
        }

        algGenetico(grafoInicial);
        System.out.println("------------DEPOIS--------------");
        for (Grafo g : novaGeracao) {
            g.printFitness();
        }
//        gerarPopulacaoInicial(grafoInicial);
//        for (Grafo g : populacaoInicial) {
//            g.printFitness();
//        }
//        System.out.println("----------------REPRODUTORES-------------------");
//        selecao(populacaoInicial);
//        for (Grafo g : reprodutores) {
//            g.printFitness();
//        }
//        Grafo filho = cruzamento(reprodutores.get(0), reprodutores.get(1));
//        System.out.println("---------------FILHO-------------------");
//        filho.printFitness();
    }
}
