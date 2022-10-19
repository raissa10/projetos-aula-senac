package aula15082022;

import java.util.Scanner;

public class atividade01 {
    // Desenvolva um algoritmo que leia 10 nomes , armazene num vetor de strings
    // depois percorra esse vetor mostrando os nomes na tela;

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);

        int contadorTotal = 10;
        String nomes [] = new String[contadorTotal];

        for(int i = 0; i < nomes.length; i++){
            System.out.println("Informe o nome da posicao:" + i);
            nomes[i] = teclado.next();
        }

        // mostrando nomes na tela
        System.out.printf("Listando nomes:");
        for(int i = 0; i < nomes.length; i++) {
            System.out.println(nomes[i]);
        }
    }
}
