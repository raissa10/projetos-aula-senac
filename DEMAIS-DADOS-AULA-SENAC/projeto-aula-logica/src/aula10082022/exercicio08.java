package aula10082022;

import java.util.Scanner;

public class exercicio08 {

    public static void main(String[] args) {
        // exercicio 08 -
        // Escrever um algoritmo que leia um número n que indica quantos valores
        //devem ser lidos a seguir. Para cada número lido, mostre os valores lidos
        //e o fatorial destes valores.
        Scanner teclado = new Scanner(System.in);

        int numero = 3;

        // 5,6,4
        for (int i = numero; i > 0; i--){
            System.out.println("Informe numero para calcular o Fatorial...");
            int numerolido = teclado.nextInt();
            int fatorial = 1;

            // fatorial do numero "indicado"
            for (int j = numerolido; j > 0; j--){
                fatorial = fatorial * j;
            }

            System.out.println("Fatorial do numero:" + numerolido + " e:" + fatorial);

        }
    }
}
