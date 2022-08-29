package aula01082022;

import java.util.Scanner;

public class atividade_5 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.println("Informe sua idade");
        int idade = input.nextInt();

        if(idade >= 18){
            System.out.println("JA POSSO TIRAR A CARTEIRA DE MOTORISTA");
        }

        System.out.println("AINDA NÃO POSSO TIRAR A CARTEIRA DE MOTORISTA,");
    }
}
