package aula18082022;

import java.util.Scanner;

public class exemplo02 {
    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);

        // inicializando matriz completa
        int matriz[][] = {
            {10,20,30},
            {40,50,60}
        };

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

        // LENDO DADOS DA MATRIZ VIA TECLADO
        int matrizA [][] = new int[2][3];
        for(int linhas = 0;linhas < matrizA.length;linhas++) {
            int tamanhoVetor = matrizA[linhas].length;
            for (int colunas = 0; colunas < tamanhoVetor; colunas++) {
                System.out.println("Informe o elemento:" +
                        " da linha:" + linhas + "" +
                        " na coluna:" + colunas);
                matrizA[linhas][colunas] = teclado.nextInt();
            }
        }

        System.out.println("Elementos informados pelo usuario da MatrizA:");
        for(int linhas = 0;linhas < matrizA.length;linhas++) {
            int tamanhoVetor = matrizA[linhas].length;
            for (int colunas = 0; colunas < tamanhoVetor; colunas++) {
                System.out.println("Elemento:" +
                        " da linha:" + linhas + "" +
                        " na coluna:" + colunas +
                        " é o elemento:" + matrizA[linhas][colunas]);
            }
        }
    }
}
