package aula16082022;

import java.util.Scanner;

public class Exercicio03 {

    public static void main(String[] args) {
        // Amplie o exercício anterior emitindo um relatório
        // com todos os números pares que você leu.

        Scanner teclado = new Scanner(System.in);

        int contadorTotal = 3;

        // vetor com 3 posicoes
        int numeros [] = new int[contadorTotal];
        int pares [] = new int[contadorTotal];

        int contadorPar = 0;

        // lendo dados do usuario
        for(int i = 0; i < numeros.length; i++){
            // armazenar os dados do teclado em cada posicao do vetor
            System.out.println("Informe um numero inteiro maior que zero: ");
            numeros [i] = teclado.nextInt();

            // se o resto for igual a zero, e par
            if(numeros[i] % 2 == 0){
                pares[contadorPar] = numeros[i];
                contadorPar++;
            }
        }

        System.out.println("Listando numeros pares:");
        for(int i = 0; i < contadorPar; i++){
            System.out.println("Numero:" + pares[i] + " na posicao:" + i);
        }

    }
}
