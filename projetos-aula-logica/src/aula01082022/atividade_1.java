package aula01082022;

import java.util.Scanner;

public class atividade_1 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        double assinatura = 17.90;
        double interurbanos = 34.29;

        System.out.println("Chamadas para o celular");
        int chamadasCelular = input.nextInt();
        //quantidade de chamadas pelo celular

        System.out.println("Impulso do telefone");
        int impulsoTelefone = input.nextInt();
        //impulsos do telefone

        double impulsos = 0;

        if(impulsoTelefone > 90){
            impulsos = (impulsoTelefone - 90)*0.04;
        }

        double impulsoCelular = chamadasCelular * 0.20;

        double pagarConta = assinatura + impulsos + impulsoCelular + interurbanos;
        System.out.println("Valor da conta a pagar: "+pagarConta);
    }
}
