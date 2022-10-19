package aula15082022;

public class exemplo01 {
    public static void main(String[] args) {
        System.out.println("Ola..");

        int vetor [] = new int[6];

        // atribuindo valores ao vetor
        vetor[0] = 19;
        vetor[1] = 1;
        vetor[2] = 9;
        vetor[3] = 10;
        vetor[4] = 15;
        vetor[5] = 199;

        System.out.println("Valor do vetor: " + vetor[5]);

        // exemploVetores();

        // video01();
    }

    static void exemploVetores(){
        // vetor inteiro
        int vetor [] = new int[6];

        // atribuindo valores ao vetor
        vetor[0] = 19;
        vetor[1] = 1;
        vetor[2] = 9;
        vetor[3] = 10;
        vetor[4] = 15;
        vetor[5] = 199;

        for(int i = 0;i < vetor.length;i++){
            // System.out.println("Elemento na posicao:" + i + " e o numero:" + vetor[i]);
        }

        for (int w: vetor){
            System.out.println("Elemento: " + w);
        }

        String nomes [] = new String[3];
        nomes[0] = "joao";
        nomes[1] = "maria";
        nomes[2] = "pedro";

        for (String nome: nomes){
            System.out.println("Elemento nome: " + nome);
        }

        int valoresvetor [] = new int[]{10,20,30};
        for (int numeros: valoresvetor){
            System.out.println("Elementos numeros 2: " + numeros);
        }

        int [] valoresvetor2 = {10,20,30,40};
        for (int numeros2: valoresvetor2){
            System.out.println("Elementos numeros 2: " + numeros2);
        }
    }

    static void video01(){
        // video 01 de exemplo:10 min - ok
        // https://www.youtube.com/watch?v=NwIIouSVKN4
        System.out.println("Video 01");
    }

    static void video02(){
        // video 02 - 15 min
        // https://www.youtube.com/watch?v=d3z3FDtVoDw
        System.out.println("Video 02");
    }

    static void video03(){
        // video 03 - atividades
    }

}
