package aula01082022;

import java.util.Scanner;

public class atividade_8 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.println("Informe seu peso");
        double peso = input.nextDouble();

        System.out.println("Informe sua altura");
        double altura = input.nextDouble();

        double imc = peso/Math.pow(altura,2);
        if(imc <= 18.5){
            System.out.println("Parabens! Voce esta no seu peso idea");

        }
        else{
            System.out.println("Voce nao esta na faixa de peso ideal");
        }
    }
}
