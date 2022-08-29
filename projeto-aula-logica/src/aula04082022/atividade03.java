package aula04082022;

import java.util.Scanner;

public class atividade03 {
    public static void main(String[] args) {
        // Crie um algoritmo que receba 2 numeros
        // e multiplica o numero1 pelo numero2 atraves de somas repetidas
        // Exemplo: 2 e 3 => 2 + 2 + 2 => resultado => 6
        Scanner teclado = new Scanner(System.in);
        System.out.println("Informe o primeiro numero:");
        int numero1 = teclado.nextInt();

        System.out.println("Informe o segundo numero:");
        int numero2 = teclado.nextInt();

        int resultado = 0;

        int contador = numero2;

        for(int i = 0; i < contador; i++){
            System.out.println("Posicao: " + i);

            // Calculo
            // acumulador de somas
            resultado = resultado + numero1;

            System.out.println("Somando valor de resultado:" + resultado);
        }

        System.out.println("Resultado: " + resultado);
    }
}
