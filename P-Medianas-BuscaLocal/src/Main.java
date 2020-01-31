import java.util.*;
import java.util.stream.Collectors;

public class Main {

    public static ArrayList<Grafo> populacaoInicial = new ArrayList<>();
    public static ArrayList<Grafo> populacaoAux = new ArrayList<>();
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

    public static void selecao(ArrayList<Grafo> populacao) { // SELECIONA 2 REPRODUTORES ALEATÓRIOS
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
//        reprodutores.add(populacao.get(0).deepCopy());
//        reprodutores.add(populacao.get(1).deepCopy());
    }

    public static Grafo cruzamento(Grafo pai, Grafo mae) {
        Grafo filho = pai.deepCopy();
        filho.medianas.clear();
        filho.fitness = 0;
        for (Vertice medianaPai : pai.medianas) { //MANTÉM AS MEDIANAS EM COMUM DOS PAIS NOS FILHOS
            for (Vertice medianaMae : mae.medianas) {
                if (medianaPai.equals(medianaMae)) {
                    filho.medianas.add(medianaMae);
                    pai.medianas = pai.medianas.stream().filter(item -> !(item.equals(medianaMae))).collect(Collectors.toList());
                    mae.medianas = mae.medianas.stream().filter(item -> !(item.equals(medianaPai))).collect(Collectors.toList());
                }
            }
        }
        Random gerador = new Random();
        int randomico;
        List<Integer> escolhidos = new ArrayList<>();
        escolhidos.clear();
        while (filho.medianas.size() < pai.numMedianas / 2) {
            randomico = gerador.nextInt(pai.medianas.size());
            if (!escolhidos.contains(randomico)) {
                filho.medianas.add(pai.medianas.get(randomico).deepCopy());
            }
            escolhidos.add(randomico);
        }
        escolhidos.clear();
        while (filho.medianas.size() < mae.numMedianas) {
            randomico = gerador.nextInt(mae.medianas.size());
            if (!escolhidos.contains(randomico)) {
                filho.medianas.add(mae.medianas.get(randomico).deepCopy());
                escolhidos.add(randomico);
            }
        }
        filho.resetarCapacidadeAndDistancia();
        filho.attCapacidadeAndDistancia();
        filho.calcularFitness();
        return filho.deepCopy();
    }

    public static ArrayList<Grafo> gerarMutacao() { //GERA MUTAÇÃO EM 10% DA POPULAÇÃO
        Random gerador = new Random();
        ArrayList<Integer> escolhidos = new ArrayList<>();
        ArrayList<Grafo> retorno = new ArrayList<>();
        boolean existe = false;
        populacaoAux.clear();
        for (Grafo g : populacaoInicial) {
            populacaoAux.add(g.deepCopy());
        }
        int qntdMutacoes = (int) (0.1 * populacaoInicial.size());
        for (int i = 0; i < qntdMutacoes; i++) {
            int elementoEscolhido = gerador.nextInt(populacaoAux.size());

            if (!escolhidos.contains(elementoEscolhido)) {
                int verticeEscolhido = gerador.nextInt(populacaoInicial.get(1).vertices.size());
                Vertice novaMediana = populacaoAux.get(elementoEscolhido).vertices.get(verticeEscolhido).deepCopy();

                for (Vertice v : populacaoAux.get(elementoEscolhido).medianas) {
                    if (v.equals(novaMediana)) {
                        existe = true;
                    }
                }
                if (!existe) {
                    populacaoAux.get(elementoEscolhido).medianas.remove(populacaoAux.get(elementoEscolhido).medianas.size()-1);
                    populacaoAux.get(elementoEscolhido).medianas.add(novaMediana);
                    escolhidos.add(elementoEscolhido);
                } else {
                    i--;
                }
            } else {
                i--;
            }
            existe = false;
        }
        for (int i : escolhidos) {
            Grafo mutado = populacaoAux.get(i);
            mutado.resetarCapacidadeAndDistancia();
            mutado.attCapacidadeAndDistancia();
            mutado.calcularFitness();
            retorno.add(mutado.deepCopy());
        }
        return retorno;
    }

    public static Grafo buscaLocal(Grafo filhoAntigo){
        Grafo filhoCopia = filhoAntigo.deepCopy();
        Random gerador = new Random();
        int indiceVertice;
        boolean existe = false;

        for (int i = 0; i < filhoAntigo.numMedianas; i++) {
            indiceVertice = gerador.nextInt(filhoAntigo.vertices.size());
            Vertice novaMediana = filhoCopia.vertices.get(indiceVertice).deepCopy();
            for (Vertice v : filhoCopia.medianas) {
                if (novaMediana.equals(v)) {
                    existe = true;
                }
            }
            if (!existe) {
                filhoCopia.medianas.remove(i);
                filhoCopia.medianas.add(novaMediana);
                filhoCopia.resetarCapacidadeAndDistancia();
                filhoCopia.attCapacidadeAndDistancia();
                filhoCopia.calcularFitness();
                if (filhoCopia.fitness < filhoAntigo.fitness) {
                    filhoAntigo = filhoCopia.deepCopy();
                    i = 0;
                } else {
                    filhoCopia = filhoAntigo.deepCopy();
                }
            } else {
                i--;
            }
            existe = false;
        }
        return filhoAntigo.deepCopy();
    }

    public static void attPopulacaoMut(ArrayList<Grafo> mutados) {
        populacaoAux.clear();
        for (Grafo g : mutados) {
            for (int i = 0; i < populacaoInicial.size() - 1; i++) {
                populacaoAux.add(novaGeracao.get(i).deepCopy());
            }
            if (novaGeracao.get(populacaoInicial.size() - 1).fitness >= g.fitness) {
                populacaoAux.add(g.deepCopy());
            } else {
                populacaoAux.add(novaGeracao.get(populacaoInicial.size() - 1).deepCopy());
            }
            Collections.sort(populacaoAux);
            novaGeracao.clear();
            for (Grafo m : populacaoAux) {
                novaGeracao.add(m.deepCopy());
            }
        }
    }

    public static void attPopulacaoFilho(Grafo filho) {
        populacaoAux.clear();
        for (int i = 0; i < populacaoInicial.size() - 1; i++) {
            populacaoAux.add(novaGeracao.get(i).deepCopy());
        }
        if (novaGeracao.get(populacaoInicial.size() - 1).fitness >= filho.fitness) {
            populacaoAux.add(filho.deepCopy());
        } else {
            populacaoAux.add(novaGeracao.get(populacaoInicial.size() - 1).deepCopy());
        }
        Collections.sort(populacaoAux);
        novaGeracao.clear();
        for (Grafo g : populacaoAux) {
            novaGeracao.add(g.deepCopy());
        }
    }

    public static void attPopulacaoInicial() {
        populacaoInicial.clear();
        for (Grafo g : novaGeracao) {
            populacaoInicial.add(g.deepCopy());
        }
    }

    public static void algGenetico(Grafo grafoInicial) {
        int i = 0;
        Grafo filho;
        Grafo filhoBusca;
        ArrayList<Grafo> mutados;
        gerarPopulacaoInicial(grafoInicial);
        System.out.println("---------POPULAÇÃO INICIAL----------");
        for (Grafo g : populacaoInicial) {
            g.printFitness();
        }
        System.out.println("------------------------------------");
        while (i < 700) {
            i++;
            selecao(populacaoInicial);
            filho = cruzamento(reprodutores.get(0), reprodutores.get(1));
            mutados = gerarMutacao();
            novaGeracao.clear();
            for (Grafo g : populacaoInicial) {
                novaGeracao.add(g.deepCopy());
            }
            attPopulacaoMut(mutados);
            filhoBusca = buscaLocal(filho); //comente essa linha para desativar a busca local e troque o parametro da função abaixo para 'filho'
            attPopulacaoFilho(filhoBusca); //caso não for executar com busca local, alterar o parametro de 'filhoBusca' para 'filho'
            attPopulacaoInicial();
            mutados.clear();
            Collections.sort(populacaoInicial);
            populacaoInicial.get(0).printFitness();
        }
        Collections.sort(populacaoInicial);
        Collections.sort(novaGeracao);
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
            grafoInicial.vertices.add(new Vertice(capacidadeMax, capacidadeMax, demanda, posx, posy, 0));
        }

        algGenetico(grafoInicial);
        System.out.println("----------MELHOR SOLUÇAO ENCONTRADA----------");
        novaGeracao.get(0).printFitness();
    }
}
