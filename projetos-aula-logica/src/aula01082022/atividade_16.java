package aula01082022;

import java.util.Scanner;

public class atividade_16 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

         System.out.println("Informe o valor de A");
        int a = input.nextInt();

        System.out.println("Informe o valor de B");
        int b = input.nextInt();

        System.out.println("Informe o valor de C");
        int c = input.nextInt();

        double raiz = Math.pow(b,2) -4*(a*c);
        if(raiz > 0){
            double x = Math.sqrt(raiz);

            double x1 = (-(b)+ Math.sqrt(raiz))/(2*a);
            System.out.println("Valor do x1: "+x1);

            double x2 = (-(b)- Math.sqrt(raiz))/(2*a);
            System.out.println("Valor do x2: "+x2);
            //Math.sqrt como pegar raiz de um numero
        }
        else if(raiz == 0){
            double x = (-(b))/(2*a);
            System.out.println();
        }
        else{
            System.out.println("Raiz não pode ser menor que zero");
        }

    }
}
