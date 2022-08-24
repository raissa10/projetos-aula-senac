package aula01082022;

import java.util.Scanner;

public class atividade_13 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.println("Informe o primerio valor");
        double celsius = input.nextInt();

        double Farenheidt = 5 * (celsius - 32)/9;
        System.out.println("Temperatura celsius em Farenheidt: "+Farenheidt);

        double kelvin = celsius + 273;
        System.out.println("Temperatura celsius em Farenheidt: "+celsius);

    }
}
