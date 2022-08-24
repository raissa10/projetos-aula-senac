package aula01082022;

import java.util.Scanner;

public class atividade_17 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int x = 5;
        int k = 7;
        int m = 5;

        System.out.println("Informe quantos quilos de café?");
        int cafe = input.nextInt();
        double precoCafe = cafe * x;

        System.out.println("Informe quantos litros de leite?");
        int leite = input.nextInt();
        double precoLeite = leite * k;

        System.out.println("Informe quantos quilos de farinha?");
        int farinha = input.nextInt();
        double precoFarinha = farinha * m;

        int soma = farinha + leite + cafe;

        System.out.println(cafe+" kG de café com preço = "+precoCafe+" Reais");
        System.out.println(leite+" litros de leite com preço = "+precoLeite+" Reais");
        System.out.println(farinha+" KG de farinha com preço = "+precoFarinha+" Reais");
        System.out.println("Preço total dos produtos = "+soma);
    }
}
