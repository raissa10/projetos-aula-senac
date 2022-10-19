package aula01082022;

import java.util.Scanner;

public class atividade_11 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.println("Informe a distancia em km");
        int km = input.nextInt();

        System.out.println("Informe o tempo percorrido em horas?");
        int tempo = input.nextInt();

        int velocidade = km * tempo;
        System.out.println("Velocidade percorrido: "+velocidade);


    }
}
