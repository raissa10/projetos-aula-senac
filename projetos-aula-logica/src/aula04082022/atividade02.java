package aula04082022;

import java.util.Scanner;

public class atividade02 {
    public static void main(String[] args) {
        // Crie um algoritmo que leia um numero
        // e escreva a sequencia decrescente ate zero.
        Scanner teclado = new Scanner(System.in);

        System.out.println("Informe numero:");
        int numero = teclado.nextInt();

        int contador = numero;

        for(int i = 0; i <= contador; i++){
            // System.out.println("Percorrendo a posicao: " + i);

            // numero = numero - 1; => numero--
            System.out.println("Numero:" + numero--);
        }
    }
}
