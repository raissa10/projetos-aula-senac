package aula01082022;

import java.util.Scanner;

public class atividade_7 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.println("Informe o valor");
        int valor = input.nextInt();

        if((valor % 2)==0){
            System.out.println("Par");
        }
        else{
            System.out.println("Impar");
        }
    }
}
