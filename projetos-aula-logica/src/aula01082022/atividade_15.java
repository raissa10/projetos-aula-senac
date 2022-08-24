package aula01082022;

import java.util.Scanner;

public class atividade_15 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        double vendidos = 0;

        System.out.println("Vendeu quantos carro?");
        int quant = input.nextInt();

        for(int i=1; i<=quant;i++){
            System.out.println("Informe valor do carro "+i+" ?");
            double valorCarro = input.nextDouble();
            vendidos+=(valorCarro * 0.05)+500;
        }

        double salario = (3000.00 + vendidos);
        System.out.println(salario);
    }
}
