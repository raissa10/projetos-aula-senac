package aula01082022;

import java.util.Scanner;

public class atividade_4 {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.println("Informe as notas do aluno");
        double nota1 = input.nextDouble();
        double nota2 = input.nextDouble();
        double nota3 = input.nextDouble();

        double media = (nota1 + nota2 + nota3)/3;
        if(media >= 7){
            System.out.println("Aprovado");
        }
        else{
            System.out.println("Reprovado");
        }
    }
}
