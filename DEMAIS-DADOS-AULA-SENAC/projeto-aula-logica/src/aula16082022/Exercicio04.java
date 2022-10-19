package aula16082022;

import java.util.Scanner;

public class Exercicio04 {

    public static void main(String[] args) {
        // Faça um algoritmo que determine qual o menor valor existente no vetor do Exercício 02.

        Scanner teclado = new Scanner(System.in);

        int contadorTotal = 3;

        // vetor com 3 posicoes
        int numeros [] = new int[contadorTotal];
        int menor = 0;
        int maior = 0;

        // lendo dados do usuario
        for(int i = 0; i < numeros.length; i++){
            // armazenar os dados do teclado em cada posicao do vetor
            System.out.println("Informe um numero inteiro maior que zero: ");
            numeros [i] = teclado.nextInt();

            if(i == 0){
                menor = numeros[i];
            }

            if(numeros[i] < menor){
                menor = numeros[i];
            }

            // exercicio 05
            if(numeros[i] > maior){
                maior = numeros[i];
            }
        }

        System.out.println("o menor numero e:" + menor);

        // ex 05
        System.out.println("o maior numero e:" + maior);
    }
}
