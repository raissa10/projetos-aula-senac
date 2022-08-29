package aula04082022;

import java.util.Scanner;

public class atividade01 {
    public static void main(String[] args) {
        // crie um algoritmo que leia um numero
        // e escreva a sequencia de 0 ate o numero
        // 10   resultado = 0,1,2,3,4....
        Scanner teclado = new Scanner(System.in);

        System.out.println("Informe um numero:");
        int numero = teclado.nextInt();

        int sequencia = 0;

        int contador = 0;
        int total    = numero;

        while(contador <= total){

            // System.out.println("Contador:" + contador);

            // algoritmo
            System.out.println("Sequencia:" + sequencia);

            sequencia = sequencia + 1;


            contador++;
        }

    }
}
