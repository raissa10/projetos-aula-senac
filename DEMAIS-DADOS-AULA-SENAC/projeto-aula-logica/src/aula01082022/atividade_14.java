package aula01082022;

import java.util.Scanner;

public class atividade_14 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
       // char x = input.next();//System.out.println(x);

        System.out.println("Informe o valor");
        int valor = input.nextInt();

        if(valor > 0){
            System.out.println("O valor é positivo");
        }
        else if(valor == 0){
            System.out.println("O valor é nulo");
        }
        else if(valor < 0){
            System.out.println("O valor é negativo");
        }
    }
}
