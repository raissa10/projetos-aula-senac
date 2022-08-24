package aula16082022;

import java.util.Scanner;

public class Exercicio02 {
    // Faça um algoritmo que leia, via teclado, 10 valores do tipo inteiro e os guarde na memória.
    // Após a leitura liste os numeros armazenados.
    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);

        // vetor com 10 posicoes
        int numeros [] = new int[10];

        // lendo dados do usuario
        for(int i = 0; i < numeros.length; i++){
            // System.out.println("posicao: " + i);

            // armazenar os dados do teclado em cada posicao do vetor
            System.out.println("Informe um numero inteiro maior que zero: ");
            numeros [i] = teclado.nextInt();
        }

        System.out.println("Listando numeros armazenados:");

        for(int i = 0; i < numeros.length; i++){
            System.out.println("Numero:" + numeros[i] + " na posicao:" + i);
        }

    }
}
