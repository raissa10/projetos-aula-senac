package aula01082022;

import java.util.Scanner;

public class atividade_18 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        double pi = 3.14;
        System.out.println("Informe o o raio do circulo");
        double r = input.nextDouble();
        r = r/2;
        double VOLUME = 4 * pi * r;
        System.out.println("O valor do volume: "+VOLUME);
    }
}
