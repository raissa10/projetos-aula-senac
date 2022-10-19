package aula08082022;

import java.util.Scanner;

public class Exercicio09 {

    public static void main(String[] args) {
        // Crie um algoritmo que receba vários números inteiros e positivos

        // e imprima o produto dos números ímpares digitados e a soma dos pares.

        // O algoritmo encerra quando o zero ou um número negativo é digitado.
        Scanner teclado = new Scanner(System.in);

        // Usar while ou |do while |?

        // variaveis
        int somapar = 0;
        int produtoimpar = 1;
        int contador = 0;
        // algoritmo

        int controle = 1;
        // Teste
        // numeros digitados: 4,5,9,6,8,0 =>para
        // soma pares = 18
        // produto = 5 * 9 => 45

        while (contador < controle){

            System.out.println("Passando aqui... contador na posicao: " + contador);

            System.out.println("Informe o numero: ");
            int numeroControle = teclado.nextInt();

            if(numeroControle % 2 == 0){
                // conta da soma
                somapar = somapar + numeroControle;

                System.out.println("Soma na posicao:" + contador + " =>" + somapar);
            } else {
                // produto dos impares
                produtoimpar = produtoimpar * numeroControle;

                System.out.println("Produto na posicao:" + contador + " =>" + produtoimpar);
            }

            // continuando a repeticao
            controle++;


            // parando a repeticao
            // usar break  ou mudar o contador para parar
            // Numero zero           ou     negativo
            if((numeroControle == 0) || (numeroControle < 0)){
                contador = controle;

                // Regra
                // enquanto contador < controle => continua
                // while (contador < controle){
                // contador = 9

                // controle
                // 9
                // para

                // usando break
                // break;
            }

            contador++;
        }

        // resultado
        System.out.println("Soma dos numeros pares:" + somapar);
        System.out.println("Produto dos numeros impares:" + produtoimpar);
    }
}
