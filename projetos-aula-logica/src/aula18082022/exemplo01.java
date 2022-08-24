package aula18082022;

public class exemplo01 {

    public static void main(String[] args) {

        int matriz [][] = new int[2][3];

        // inicializando matriz indice por indice
        matriz[0][0] = 10;
        matriz[0][1] = 20;
        matriz[0][2] = 30;

        matriz[1][0] = 40;
        matriz[1][1] = 50;
        matriz[1][2] = 60;

        for(int linhas = 0;linhas < matriz.length;linhas++){
            int tamanhoVetor = matriz[linhas].length;
            for(int colunas = 0;colunas < tamanhoVetor;colunas++){
                System.out.println("Elemento:" +
                        " da linha:" + linhas + "" +
                        " na coluna:" + colunas +
                        " é o elemento:" + matriz[linhas][colunas]);
            }

            // nova linha
            System.out.println("Nova linha");
        }
    }
}
